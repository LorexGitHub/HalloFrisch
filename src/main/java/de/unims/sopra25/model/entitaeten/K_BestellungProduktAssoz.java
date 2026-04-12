package de.unims.sopra25.model.entitaeten;

import de.unims.sopra25.model.logik.FehlerVerwalten;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Klasse, die eine Assoziation zwischen Bestellung und Produkt repräsentiert
 * enthält eine Bestellung, ein Produkt, sowie Name, Preis und Anzahl des
 * Produktes in der Bestellung
 *
 * @author Irina Hoppe, David Metzdorf, Johannes Schick
 */
public class K_BestellungProduktAssoz implements Serializable {

    // Bestellung die ein Produkt enthält
    private final K_Bestellung bestellung;

    // Produkt das in einer Bestellung enthalten ist
    private Produkt produkt;

    // Anzahl der Artikel pro Produkt
    private final int anzahl;

    // Preis des einzelnen Artikels zum Zeitpunkt der Bestellung
    private final int preis;

    // Name des Produkts zum Zeitpunkt der Bestellung
    private final String name;

    // Adresse des Kunden zum Zeitpunkt der Bestellung
    private final String adresse;

    /**
     * Konstruktor einer K_BestellungProdukt Assoziation.
     * Beim Konstruieren wird eine WarenkorbProduktAssoz umgewandelt.
     * Insbesondere wird im Konstruktor der Bestand des Produktes aktualisert.
     *
     * @param produktAssoz Assoziation zwischen Produkten im Warenkorb und
     *                     Warenkorb, durch den die Bestellung folgt
     * @param b            Bestellung, die Produkt enthält
     */
    public K_BestellungProduktAssoz(WarenkorbProduktAssoz produktAssoz, K_Bestellung b) {
        this.bestellung = b;
        this.produkt = produktAssoz.getProdukt();
        this.anzahl = produktAssoz.getAnzahl();
        this.name = produkt.getName();
        this.preis = produkt.getPreis();
        this.adresse = produktAssoz.getWarenkorb().getKunde().getAdresse();
       
        //Bestand des Produktes aktualisieren
        produkt.removeBestand(produktAssoz.getAnzahl());
    }

    /**
     * Gibt Bestellung der Assoziation zurück
     *
     * @return Bestellung der Assoziation
     */
    public K_Bestellung getBestellung() {
        return this.bestellung;
    }

    /**
     * Gibt Produkt der Assoziation zurück.
     * Kann null sein!
     *
     * @return Produkt der Assoziation
     */
    public Produkt getProdukt() {
        return this.produkt;
    }

    /**
     * Gibt den Namen des Produkts zurück.
     *
     * @return Name des Produkts
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gibt den Preis des Produkts zurück
     *
     * @return Preis des Produkts
     */
    public int getPreis() {
        return this.preis;
    }

    /**
     * Gibt den Anzahl des Produkts zurück
     *
     * @return Anzahl des Produkts
     */
    public int getAnzahl() {
        return this.anzahl;
    }

    /**
     * gibt die Adresse einer Bestellung
     *
     * @return adresse
     */
    public String getAdresse() {
        return this.adresse;
    }

    /**
     * Gibt den Preis des Produkts als String aus.
     * Beispiel: 1245 -> "12,45 €"
     *
     * @return Preis als String
     */
    public String getPreisString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
        DecimalFormat format = new DecimalFormat("#,##0.00 €", symbols);
        return format.format((double) this.getPreis() / 100);
    }

    /**
     * TODO IST DIESE METHODE ÜBERHAUPT RELEVANT?????
     * Getter-Methode für Produktbeschreibung
     *
     * @return gibt die Produktbeschreibung zurück oder eine Fehlermeldung
     */
    public String getBeschreibung() {
        if (this.produkt != null) {
            return this.produkt.getBeschreibung();
        } else {
            Fehler error = new Fehler("Keine Produktbeschreibung bei Assoziation von Bestellungs-ID "
                    + this.bestellung.getId() + " mit Produkt " + this.produkt.getName());
            FehlerVerwalten fV = new FehlerVerwalten();
            fV.addFehler(error.getError());
            return "Beschreibung nicht gefunden.";
        }

    }

    /**
     * Getter-Methode für den Speicherpfad des Produktbildes
     *
     * @return gibt den Speicherpfad des Produktbildes zurück oder eine
     *         Fehlermeldung
     *         TODO: Standard-Bild ausgeben, wenn Bild nicht mehr vorhanden
     */
    public String getBild() {
        if (this.produkt != null) {
            return this.produkt.getBild();
        } else {
            return "file:./speicher/icons/error.jpg";
        }
    }

    /**
     * Entfernt die Assoziation zum Produkt.
     * Methode ist zum Löschen eines Produktes relevant.
     */
    public void removeProduktAssoz() {
        this.produkt = null;
    }
}
