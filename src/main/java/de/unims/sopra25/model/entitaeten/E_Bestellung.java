package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Klasse die eine Nachbestellung Repräsentiert.
 * Eine Nachbestellung besteht aus Produkt, Anzahl, Datum und Id
 * 
 * @author Leonhard Heß
 */
public class E_Bestellung implements Serializable {

	// Produkt welches in der Nachbestellung bestellt wurde
	private Produkt produkt;

	// Anzahl des bestellten Produkts
	private int anzahl;

	// Datum an dem die Bestellung erzeugt wurde
	private Date datum;

	// Id mit der die Bestellung identifiziert werden kann.
	private int id;

	public E_Bestellung(Produkt produkt, int anzahl, int id) {
		// Beidseitige beziehung zwischen Nachbestellung und Produkt erstellen
		this.produkt = produkt;
		produkt.addNachbestellung(this);
		this.anzahl = anzahl;
		this.id = id;
		datum = Date.from(Instant.now()); // Datum auf Jetzt setzen
	}

	/**
	 * Gibt die Anzahl des bestellten Produkts zurück.
	 * 
	 * @return Anzahl des bestellten Produkts
	 */
	public int getAnzahl() {
		return anzahl;
	}

	/**
	 * Setzt die Anzahl des bestellten Produkts.
	 * 
	 * @param anzahl Anzahl des bestellten Produkts
	 */
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}

	/**
	 * Gibt das Produkt was bestellt wurde zurück.
	 * 
	 * @return Produkt das bestellt wurde
	 */
	public Produkt getProdukt() {
		return produkt;
	}

	/**
	 * Gibt den Zeitpunkt zurück an dem die Bestellung aufgegeben wurde
	 * 
	 * @return Zeitpunkt der Bestellung
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * Gibt die Id der Bestellung zurück
	 * 
	 * @return Id der Bestellung
	 */
	public int getId() {
		return id;
	}

    /**
     * Gibt das Datum als String formatiert zurueck
     * @return String
     */
    public String getDateString () {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.datum.getTime());
    }
}
