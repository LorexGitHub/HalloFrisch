package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Locale;
import java.util.*;

/**
 * Klasse die ein Produkt repraesentiert.
 * Ein Produkt besteht aus: Name, Preis, Beschreibung, Bild, Kategorien,
 * Nachbestellungen und Bestand.
 *
 * @author Leonhard Heß
 * @author Johannes Schick
 */
public class Produkt implements Serializable {

	// Name des Produkts
	private String name;

	// Preis des Produkts (Cent)
	private int preis;

	// Beschreibung des Produkts
	private String beschreibung;

	// Vorrätige Anzahl des Produkts minus offene Bestellungen
	private int bestand;

    // Tatsächliche Anzahl des Produkts im Lager
    private int lagerBestand;

	// Dateipfad des Produktbilds
	private String bild;

	// Kategorien in die das Produkt gehört
	private ArrayList<Kategorie> kategorien;

	// Nachbestellungen für das Produkt
	private ArrayList<E_Bestellung> nachbestellungen;

	/**
	 * Konstruktor des Produkts.
	 * Fügt das Produkt automatisch allen Kategorien hinzu (wechselseitige Assoz.).
	 *
	 * @param name         Name des Produkts
	 * @param preis        Preis des Produkts (Cent)
	 * @param beschreibung Beschreibung des Produkts
	 * @param bild         Dateipfad des Produktbilds
	 * @param kategorien   Kategorien in die das Produkt gehört
	 */
	public Produkt(String name, int preis, String beschreibung, String bild, ArrayList<Kategorie> kategorien) {
		this(name, preis, beschreibung, bild);
		// Kategorien hinzufügen (wechselseitig)
		for (Kategorie k : kategorien) {
			this.addKategorie(k);
		}
	}

	/**
	 * Konstruktor des Produkts.
	 * Fügt das Produkt der Kategorie hinzu (wechselseitige Assoz.).
	 *
	 * @param name         Name des Produkts
	 * @param preis        Preis des Produkts (Cent)
	 * @param beschreibung Beschreibung des Produkts
	 * @param bild         Dateipfad des Produktbilds
	 * @param kategorie    Kategorie, in die das Produkt gehört
	 */
	public Produkt(String name, int preis, String beschreibung, String bild, Kategorie kategorie) {
		this(name, preis, beschreibung, bild);
		this.addKategorie(kategorie);
	}

	/**
	 * Konstruktor des Produkts.
	 * Das Produkt ist hierbei (zunächst) in keiner Kategorie.
	 *
	 * @param name         Name des Produkts
	 * @param preis        Preis des Produkts (Cent)
	 * @param beschreibung Beschreibung des Produkts
	 * @param bild         Dateipfad des Produktbilds
	 */
	public Produkt(String name, int preis, String beschreibung, String bild) {
		this.name = name;
		this.preis = preis;
		this.beschreibung = beschreibung;
		this.bild = bild;
		this.kategorien = new ArrayList<>();
		this.bestand = 0;
        this.lagerBestand = 0;
		this.nachbestellungen = new ArrayList<>();
	}

	/**
	 * Gibt den Namen des Produkts zurück.
	 *
	 * @return Name des Produkts
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen des Produkts.
	 *
	 * @param name neuer Name des Produkts.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt den Preis des Produkts zurück.
	 *
	 * @return Preis des Produkts
	 */
	public int getPreis() {
		return preis;
	}

	/**
	 * Setzt den Preis des Produkts.
	 *
	 * @param preis Preis des Produkts.
	 */
	public void setPreis(int preis) {
		this.preis = preis;
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
	 * Gibt die Beschreibung des Produkts zurück.
	 *
	 * @return Beschreibung des Produkts
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Setzt die Beschreibung des Produkts.
	 *
	 * @param beschreibung Beschreibung des Produkts.
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * Gibt den Bestand des Produkts zurück.
	 *
	 * @return Bestand des Produkts
	 */
	public int getBestand() {
		return bestand;
	}

	/**
	 * Setzt den Bestand des Produkts.
	 *
	 * @param bestand Bestand des Produkts
	 */
	public void setBestand(int bestand) {
		this.bestand = bestand;
	}

    /**
     * Gibt den Lagerbestand des Produkts zurück.
     *
     * @return Lagerbestand des Produkts.
     */
    public int getLagerBestand(){
        return lagerBestand;
    }

	/**
	 * Addiert dem Bestand den neuen zusätzlichen Bestand.
     * Wird sowohl dem tatsächlichen Bestand, als auch dem mit
     * den Bestellungen verrechnetem Bestand
	 *
	 * @param zusaetzlicherBestand: Zu addierender Bestand
	 */
	public void addBestand(int zusaetzlicherBestand) {
		this.bestand += zusaetzlicherBestand;
        this.lagerBestand += zusaetzlicherBestand;
	}

    /**
     * Entfernt dem Bestand den neuen zusätzlichen Bestand.
     * Sollte aufgerufen werden, sobald eine Bestellung aufgegeben wird.
     *
     * @param zusaetzlicherBestand: Zu entfernender Bestand
     */
    public void removeBestand(int zusaetzlicherBestand) {
        this.bestand -= zusaetzlicherBestand;
    }

    /**
     * Addiert dem Lager-Bestand den neuen zusätzlichen Bestand.
     *
     * @param zusaetzlicherBestand: Zu addierender Bestand
     */
    public void addLagerBestand(int zusaetzlicherBestand) {
        this.lagerBestand += zusaetzlicherBestand;
    }

    /**
     * Entfernt dem Lager-Bestand den neuen zusätzlichen Bestand.
     * Sollte aufgerufen werden, sobald eine Bestellung bearbeitet wird.
     *
     * @param zusaetzlicherBestand: Zu addierender Bestand
     */
    public void removeLagerBestand(int zusaetzlicherBestand) {
        this.lagerBestand -= zusaetzlicherBestand;
    }

	/**
	 * Gibt den Pfad des Produktbilds zurück.
	 *
	 * @return Pfad des Produktbilds
	 */
	public String getBild() {
		return bild;
	}

	/**
	 * Setzt den Pfad des Produktbilds.
	 *
	 * @param bild Pfad des Produktbilds
	 */
	public void setBild(String bild) {
		this.bild = bild;
	}

	/**
	 * Gibt die Kategorien in denen sich das Produkt befindet zurück.
	 *
	 * @return Kategorien in denen sich das Produkt befindet (Read-only)
	 */
	public List<Kategorie> getKategorien() {
		return Collections.unmodifiableList(kategorien);
	}

	/**
	 * Fügt das Produkt einer Kategorie hinzu.
	 *
	 * @param kategorie Kategorie der das Produkt hinzugefügt werden soll
	 */
	public void addKategorie(Kategorie kategorie) {
		if (!kategorien.contains(kategorie)) {
			kategorien.add(kategorie);
		}

		// Falls dieses Produkt nicht in der Kategorie ist,
		// füge es der Kategorie hinzu.
		if (!kategorie.getProdukte().contains(this)) {
			kategorie.addProdukt(this);
		}
	}

	/**
	 * Entfernt das Produkt aus einer Kategorie.
	 *
	 * @param kategorie Kategorie aus der das Produkt entfernt werden soll
	 */
	public void removeKategorie(Kategorie kategorie) {
		kategorien.remove(kategorie);

		// Falls dieses Produkt in der Kategorie ist,
		// entferne es aus der Kategorie.
		if (kategorie.getProdukte().contains(this)) {
			kategorie.removeProdukt(this);
		}
	}

	/**
	 * Gibt die Nachbestellungen des Produkts zurück.
	 *
	 * @return Nachbestellungen fuer das Produkt befindet (Read-only)
	 */
	public List<E_Bestellung> getNachbestellungen() {
		return Collections.unmodifiableList(nachbestellungen);
	}

	/**
	 * Fügt die Nachbestellung einer Menge von Nachbestellungen hinzu.
	 *
	 * @param nachbestellung Nachbestellung des Produktes
	 * @pre Die Nachbestellung ist fuer dieses Produkt bestimmt.
	 */
	protected void addNachbestellung(E_Bestellung nachbestellung) {
		assert nachbestellung.getProdukt().equals(this) : "Produkt: Übergebene Nachbestellung"
				+ " ist nicht für dieses Produkt bestimmt!";
		nachbestellungen.add(nachbestellung);
	}

	/**
	 * Entfernt die Nachbestellung aus einer Menge von Nachbestellungen,
	 * fügt gleichzeitig die Anzahl aus der Nachbestellung dem Bestand
	 * hinzu.
	 *
	 * @param nachbestellung Nachbestellung des Produktes
	 * @pre nachbestellung in nachbestellungen
	 */
	public void removeNachbestellung(E_Bestellung nachbestellung) {
		assert this.nachbestellungen.contains(nachbestellung) : "Vorbedingung removeNachbestellung nicht erfüllt";

		addBestand(nachbestellung.getAnzahl());
		nachbestellungen.remove(nachbestellung);
	}

	/**
	 * Gibt aus, wie viele Exemplare des Produkts nachbestellt wurden.
	 * Summiert die Anzahl aus allen Nachbestellungen für das Produkt auf.
	 *
	 * @return Anzahl der nachbestellten Exemplare
	 */
	public int getAnzahlNachbestellteExemplare() {
		int nachbestellteExemplare = 0;
		for (E_Bestellung nachbestellung : this.nachbestellungen) {
			nachbestellteExemplare += nachbestellung.getAnzahl();
		}
		return nachbestellteExemplare;
	}

	/**
	 * Erstellt einen String aus allen textuellen Beschreibungen
	 * des Produkts. Insbesondere: Name, Beschreibung, Kategorien
	 *
	 * @return Text mit allen Beschreibungen des Produktes
	 */
	public String getAlleTexte() {
		StringBuilder ausgabe = new StringBuilder(this.name + " " + this.beschreibung);
		for (Kategorie k : kategorien) {
			ausgabe.append(" ").append(k.getName());
		}
		return ausgabe.toString();
	}

}
