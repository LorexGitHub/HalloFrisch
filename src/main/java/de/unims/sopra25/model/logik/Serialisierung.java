package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.*;
import de.unims.sopra25.model.entitaeten.K_Bestellung.Zahlungsmittel;

import java.io.*;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Die Klasse serialisiert alle zu serialisierenden Entitäten.
 * Dazu gehören Produkte, Nutzer, offene Bestellungen, E_Bestellungen,
 * Kategorien und Fehler.
 * Intern wird ein Objekt dieser Klasse serialisiert, welches Referenzen
 * auf alle zu serialisierenden Listen hat.
 * Sollte nur von Logikklassen verwendet werden,
 * daher Paket-Sichtbarkeit.
 * Verwendet das Singleton-Muster.
 * 
 * @author Pia Maitzen
 * @author Johannes Schick
 * @author Leonhard Heß
 */
public class Serialisierung implements Serializable {
    // Singleton-Referenz
    private static Serialisierung serialisierung;
    // Dateipfad
    private static final String PATH = "./speicher/serialisierung.ser";

    // Zu serialisierende Listen
    private ArrayList<Produkt> produkte;
    private HashMap<String, Kunde> kunden;
    private HashMap<String, Lieferant> lieferanten;
    private HashMap<String, Geschaeftsfuehrer> geschaeftsfuehrer;
    private HashMap<String, Einkaeufer> einkaeufer;
    private ArrayList<K_Bestellung> offeneKBestellungen;
    private ArrayList<E_Bestellung> eBestellungen;
    private ArrayList<Kategorie> kategorien;
    private ArrayList<Fehler> fehler;

    // Speichert die höchsten vorhandenen ids
    private int k_bestellId;
    private int e_bestellId;

    /**
     * Konstruktor initialisiert alle Listen.
     * WICHTIG: Darf nur aufgerufen werden, wenn keine
     * Serialisierung gespeichert ist (da sonst Datenverlust).
     * Daher (und wegen Singleton) private.
     */
    private Serialisierung() {
        reset();
    }

    /**
     * Setzt alle gespeicherten Listen zurück.
     * Methode ist insbesondere für Tests relevant.
     */
    void reset() {
        produkte = new ArrayList<>();
        kunden = new HashMap<>();
        lieferanten = new HashMap<>();
        geschaeftsfuehrer = new HashMap<>();
        einkaeufer = new HashMap<>();
        offeneKBestellungen = new ArrayList<>();
        eBestellungen = new ArrayList<>();
        kategorien = new ArrayList<>();
        fehler = new ArrayList<>();

        k_bestellId = 0;
        e_bestellId = 0;
    }

    /**
     * Gibt den Singleton aus. Wenn noch keine Serialisierung erstellt wurde,
     * wird diese aus der Datei serialisiert.
     * 
     * @return Serialisierung (Singleton)
     */
    public static Serialisierung getSerialisierung() {
        if (serialisierung == null) {
            read();
        }
        return serialisierung;
    }

    /**
     * Lädt den gespeicherten Serialisierer.
     * Dabei wird die Singleton-Referenz neu gesetzt.
     * Falls noch kein Serialisierer gespeichert,
     * wird neue Serialisierung erstellt.
     */
    @SuppressWarnings("unchecked")
    public static void read() {
        // Versuche Datei zu öffnen
        try (FileInputStream fileIn = new FileInputStream(PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // Caste zu SerialisierungsObjekt
            serialisierung = (Serialisierung) in.readObject();
        }
        // Datei nicht gefunden
        catch (FileNotFoundException ex) {
            System.err.println("Keine Datei gefunden. Beim ersten Start des Programms normal.");
            serialisierung= new Serialisierung();
        }
        // Konnte Datei nicht laden
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Fehler beim Laden.");
            e.printStackTrace();
            System.exit(-1);
        }
        // Fehler beim Casten
        catch (ClassCastException e) {
            System.err.println("Fehlerhafte Datei: Keine ArrayList mit GroceryStocks gespeichert");
            System.exit(-2);
        }
    }

    /**
     * Serialisiert sich selber.
     * In die Datei wird also ein Objekt des Typs Serialisierung geschrieben.
     */
    public void write() {

        // Versuche Datei zu speichern
        try {
            FileOutputStream fileOut = new FileOutputStream(PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
        }
        // Fehler abfangen
        catch (FileNotFoundException e) {
            System.err.println("Konnte nicht in Datei schreiben.");
            e.printStackTrace();
            System.exit(-4);
        } catch (IOException e) {
            System.err.println("Fehler beim Serialisieren.");
            e.printStackTrace();
            System.exit(-5);
        }
    }

    /**
     * Generiert Beispieldaten
     */
    public void initBeispielDaten() {
    	reset();

    	// Beispielnutzer
    	NutzerVerwalten nv = new NutzerVerwalten();
    	try {
    		// Fuege 4 Beispielnutzer hinzu
			nv.addEinkaeufer("e", "");
			nv.addKunde("k", "", "Musterstraße 1", "Max", "Mustermann");
			nv.addLieferant("l", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	nv.getKundenAnzahl();
    	
    	// Beispielprodukte werden generiert
    	SortimentVerwalten sv = new SortimentVerwalten();
    	sv.neuesProdukt(new Produkt("Apfel", 99, "Ein leckerer saftiger Apfel. Schmeckt geil und so.", "file:./speicher/icons/Apfel.jpg"));
    	sv.neuesProdukt(new Produkt("Banane", 149, "Lang, krumm und bei Affen beliebt.", "file:./speicher/icons/banane.jpg"));
    	sv.neuesProdukt(new Produkt("Trauben", 49, "Ein Bündel Trauben, perfekt zum snacken.", "file:./speicher/icons/traube.jpg"));
    	sv.neuesProdukt(new Produkt("Wassermelone", 199, "Perfekt für den Sommer!", "file:./speicher/icons/Wassermelone.jpg"));
    	
    	int counter = 0;
    	for(Produkt p : sv.getProduktlist()) {
    		p.addBestand(counter);
    		counter = (counter + 3)*5;
    	}
    	
    	// Beispielbestellungen generieren
    	K_BestellungenVerwalten kbv = new K_BestellungenVerwalten();
    	Kunde kunde = kunden.values().iterator().next();
    	
    	// Kunde hat 100 Wassermelonen im Januar gekauft
    	Clock c = Clock.fixed( 
				  Instant.parse("2025-01-03T00:00:00Z"),
				  ZoneOffset.UTC);
    	kunde.getWarenkorb().addProdukt(sv.getProduktlist().get(3), 100); // 100 Wassermelonen
    	K_Bestellung kb = new K_Bestellung(-1, Zahlungsmittel.BAR, kunde.getWarenkorb(), c);
    	kb.setLieferant(lieferanten.values().iterator().next());
    	kb.setVorbereitet();
    	kb.setAusgeliefert();
    	
    	// Nicht erfuellbare Bestellung
    	kunde.getWarenkorb().addProdukt(sv.getProduktlist().get(2), 20); // 20 Trauben
    	kunde.getWarenkorb().addProdukt(sv.getProduktlist().get(0), 5); // 5 Aepfel
    	kbv.newBestellung(Zahlungsmittel.KARTE, kunde.getWarenkorb());
    	
    	// Beispielkategorien erstellen
    	KategorienVerwalten ktv = new KategorienVerwalten();
    	ktv.addKategorie("Obst");
    	ktv.addKategorie("Getränke");
    	ktv.addKategorie("Milchprodukte");
    	for(Produkt p : sv.getProduktlist()) {
    		p.addKategorie(kategorien.get(0));
    	}
    }

    /**
     * Gibt eine Liste von allen gespeicherten
     * Produkten aus.
     * 
     * @return Liste von Produkten
     */
    ArrayList<Produkt> getProdukte() {
        return produkte;
    }

    /**
     * Gibt eine Map von allen gespeicherten
     * Kunden aus.
     * 
     * @return Map von Kunden
     */
    HashMap<String, Kunde> getKunden() {
        return kunden;
    }

    /**
     * Gibt eine Map von allen gespeicherten
     * Lieferanten aus.
     * 
     * @return Map von Lieferanten
     */
    HashMap<String, Lieferant> getLieferanten() {
        return lieferanten;
    }

    /**
     * Gibt eine Map von allen gespeicherten
     * Geschaeftsfuehrern aus.
     * 
     * @return Map von Geschaeftsfuehrern
     */
    HashMap<String, Geschaeftsfuehrer> getGeschaeftsfuehrer() {
        return geschaeftsfuehrer;
    }

    /**
     * Gibt eine Map von allen gespeicherten
     * Einkaeufern aus.
     * 
     * @return Map von Einkaeufern
     */
    HashMap<String, Einkaeufer> getEinkaeufer() {
        return einkaeufer;
    }

    /**
     * Gibt eine Liste von allen OFFENEN(!)
     * K_Bestellungen aus.
     * 
     * @return Liste von offenen K_Bestellungen
     */
    ArrayList<K_Bestellung> getOffeneKBestellungen() {
        return offeneKBestellungen;
    }

    /**
     * Gibt eine Liste von allen gespeicherten
     * E_Bestellungen aus.
     * 
     * @return Liste von E_Bestellungen
     */
    ArrayList<E_Bestellung> getEBestellungen() {
        return eBestellungen;
    }

    /**
     * Gibt eine Liste von allen gespeicherten
     * Kategorien aus.
     * 
     * @return Liste von Kategorien
     */
    ArrayList<Kategorie> getKategorien() {
        return kategorien;
    }

    /**
     * Gibt eine Liste von allen gespeicherten
     * Fehlern aus.
     * 
     * @return Liste von Fehlern
     */
    ArrayList<Fehler> getFehler() {
        return fehler;
    }

    /**
     * Gibt eine neue K_BestellId aus.
     * 
     * @return neue höchste KBestellID
     */

    public int getNeueKBestellId() {
        this.k_bestellId++;
        return k_bestellId;
    }

    /**
     * Gibt eine neue EBestellId aus.
     * 
     * @return neue höchste EBestellId
     */
    public int getNeueEBestellId() {
        this.e_bestellId++;
        return e_bestellId;
    }
}
