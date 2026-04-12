package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

/**
 * Klasse, die eine Kundenbestellung repraesentiert
 * Enthaelt id, wunschliefertag, zahlungsmittel, status, datum und kunde
 *
 * @author Irina Hoppe, David Metzdorf
 */
public class K_Bestellung implements Serializable {

	/**
	 * enum Status ist der Status einer K_Bestellung.
	 * Er wird von K_BestellungenVerwalten geändert
	 */
	public enum Status {
		OFFEN("Offen"),
		VORBEREITET("Vorbereitet"),
		AUSGELIEFERT("Ausgeliefert");

		// gibt Status einen Namen
		private final String name;

		private Status(String s) {
			name = s;
		}

		/**
		 * gibt Status als String
		 */
		public String toString() {
			return this.name;
		}
	}

	/**
	 * enum Zahlungsmittel stellt die möglichen Zahlungsmethoden eines
	 * Kunden zur Verfügung
	 */
	public enum Zahlungsmittel {
		BAR("Bar"),
		KARTE("Karte");

		// gibt Zahlungsmittel einen Namen
		private final String name;

		private Zahlungsmittel(String s) {
			name = s;
		}

		/**
		 * gibt Zahlungsmittel als String
		 */
		public String toString() {
			return this.name;
		}
	}

	// ID der Bestellung
	int id;

	// Wunschliefertag der Bestellung vom Kunden angegeben
	private Calendar wunschliefertag;

	// Vom Kunden angegebenes Zahlungsmittel (Bar oder Karte)
	private Zahlungsmittel zahlungsmittel;

    // Aktuller Status der Bestellung
	private Status status;

	// Datum an dem Bestellung getaetigt wurde
	private Date datum;

	// Kunde der Bestellung getaetigt hat
	private final Kunde kunde;

    // Zugewiesener Lieferant
    private Lieferant lieferant;

    // Gesamtkosten der Bestellung
    private int preis;

	// Produkte die in Bestellung enthalten sind: K_BestellungProduktAssoziation n-m
	// Dieses Attribut is nur lesbar mit getter
	private final ArrayList<K_BestellungProduktAssoz> produkte;

	/**
	 * Konstruktor der K_Bestellung
	 * 
	 * @param id              eindeutige id der Bestellung
	 * @param wunschliefertag vom Kunden gewünschtes Zustelldatum
	 * @param zahlungsmittel  vom Kunden gewähltes Zahlungsmittel
	 * @param warenkorb,      in welchem die Bestellung getätigt wurde
	 */
	public K_Bestellung(int id, Calendar wunschliefertag, Zahlungsmittel zahlungsmittel,
			Warenkorb warenkorb) {
        //Wandle Warenkorb um
        ArrayList<K_BestellungProduktAssoz> ArrList = new ArrayList<>();
        List<WarenkorbProduktAssoz> alteAssoz = warenkorb.getProdukte();
        for(WarenkorbProduktAssoz assoz : alteAssoz){
            ArrList.add(new K_BestellungProduktAssoz(assoz, this));

        }
        warenkorb.leeren();

		this.id = id;
		this.wunschliefertag = wunschliefertag;
		this.zahlungsmittel = zahlungsmittel;
		this.datum = Date.from(Instant.now());
		this.kunde = warenkorb.getKunde();
		this.produkte = ArrList;
        this.status = Status.OFFEN;
        kunde.addBestellung(this);
        berechnePreis();
	}

    /**
     * Berechnet und setzt den Gesamtpreis der Bestellung
     */
    private void berechnePreis(){
        preis= 0;
        for(K_BestellungProduktAssoz assoz: produkte){
            preis+= assoz.getAnzahl() * assoz.getPreis();
        }
    }

    /**
     * Konstruktor ohne angegebenen Wunschliefertag
     * @param id ID der Bestellung
     * @param zahlungsmittel angegebenes Zahlungsmittel
     * @param warenkorb der Warenkorb des Kunden
     */
    public K_Bestellung(int id, Zahlungsmittel zahlungsmittel, Warenkorb warenkorb) {
        this(id, null, zahlungsmittel, warenkorb);
    }

	/**
	 * Konstruktor ohne angegebenen Wunschliefertag und mit Clock
	 * um Zeit der Bestellung zu setzen. (Hilft beim Testen)
	 * 
	 * @param id             ID der Bestellung
	 * @param zahlungsmittel angegebenes Zahlungsmittel
	 * @param warenkorb      der Warenkorb des Kunden
	 * @param clock          Uhr die zur Berechnung der Bestellzeit verwendet
	 */
	public K_Bestellung(int id, Zahlungsmittel zahlungsmittel, Warenkorb warenkorb, Clock clock) {
		this(id, null, zahlungsmittel, warenkorb);
		this.datum = Date.from(Instant.now(clock));
	}

	/**
	 * Gibt ID der Bestellung zurück
	 *
	 * @return ID der Bestellung
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setzt den Status von Offen auf Vorbereitet
	 */
	public void setVorbereitet() {
		if (status != Status.OFFEN) {
			return;
		}
		this.status = Status.VORBEREITET;
        for(K_BestellungProduktAssoz assoz: produkte){
            assoz.getProdukt().removeLagerBestand(assoz.getAnzahl());
        }
	}

	/**
	 * Setzt den Status von vorbereitet auf Ausgeliefert
	 */
	public void setAusgeliefert() {
		if (status != Status.VORBEREITET) {
			return;
		}
		this.status = Status.AUSGELIEFERT;
	}

	/**
	 * Gibt vom Kunden angegebenen Wunschliefertag der Bestellung zurück
	 *
	 * @return Wunschliefertag der Bestellung
	 */
	public Calendar getWunschliefertag() {
		return this.wunschliefertag;
	}

	/**
	 * Setze neuen Wunschliefertag der Bestellung
	 *
	 * @param wunschliefertag den Wunschliefertag der Bestellung
	 */
	public void setWunschliefertag(Calendar wunschliefertag) {
		this.wunschliefertag = wunschliefertag;
	}

	/**
	 * Gibt vom Kunden angegebenes Zahlungsmittel für die Bestellung zurück
	 *
	 * @return Zahlungsmittel der Bestellung
	 */
	public Zahlungsmittel getZahlungsmittel() {
		return this.zahlungsmittel;
	}

	/**
	 * Setze neues zahlungsmittel für die Bestellung
	 *
	 * @param zahlungsmittel Setze Zahlungsmittel für die Bestellung
	 */
	public void setZahlungsmittel(Zahlungsmittel zahlungsmittel) {
		this.zahlungsmittel = zahlungsmittel;
	}

	/**
	 * Gibt vom Status der Bestellung zurück
	 *
	 * @return Status der Bestellung
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * Gibt Datum der Bestellung zurück
	 * 
	 * @return Datum der Bestellung
	 */
	public Date getDatum() {
		return this.datum;
	}

	/**
	 * Gibt Kunden der Bestellung zurück
	 *
	 * @return Kunde der Bestellung
	 */
	public Kunde getKunde() {
		return this.kunde;
	}

	/**
	 * Gibt Produkte der Bestellung zurück
	 * 
	 * @return Produkte der Bestellung
	 */
	public List<K_BestellungProduktAssoz> getProdukte() {
		return Collections.unmodifiableList(this.produkte);
	}

	/**
	 * Weist einer Bestellung genau einen Lieferanten zu
	 * 
	 * @param lieferant ist der Lieferant, der die Bestellung bearbeitet
	 */
	public void setLieferant(Lieferant lieferant) {
		if (this.lieferant == null) {
			this.lieferant = lieferant;
		} else {
			System.out.println("Bestellung hat bereits Lieferanten zugewiesen");
		}
	}

	/**
	 * Ändert den Lieferanten einer Bestellung;
	 * Entfernt den Verweis vom alten Lieferanten und fügt ihn dem Neuen zu
	 * 
	 * @param lieferant ist der neue Lieferant für die Bestellung
	 */
	public void aendereLieferant(Lieferant lieferant) {
		if (this.lieferant == null) {
			setLieferant(lieferant);
		} else {
			this.lieferant.removeBestellung(this);
			this.lieferant = lieferant;
			lieferant.addBestellung(this);
		}
	}

	/**
	 * Getter-methode für den aktuellen/finalen Lieferanten einer Bestellung
	 * 
	 * @return gibt den Lieferanten der Bestellung aus
	 */
	public Lieferant getLieferant() {
		return this.lieferant;
	}

	/**
	 * Erstellt eine Liste aus den verschiedenen moeglichen Stati
	 * 
	 * @return stati
	 */
	public static ArrayList<Status> getStatusList() {
		ArrayList<Status> stati = new ArrayList<Status>();
		stati.add(Status.OFFEN);
		stati.add(Status.VORBEREITET);
		stati.add(Status.AUSGELIEFERT);
		return stati;
	}

    /**
     * Gibt das Datum als String formatiert zurueck
     * @return String
     */
    public String getDateString () {
    	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
    	return formatter.format(this.datum.getTime());
    }

	/**
	 * gibt das Wunschlieferdatum als String formatiert zurueck
	 * falls kein Wunschlieferdatum angegeben wurde, bleibt der String leer
	 * 
	 * @return String
	 */
	public String getWunschliefertagString() {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String wunschliefertagString = "";
		if (this.wunschliefertag != null) {
			wunschliefertagString = formatter.format(this.wunschliefertag.getTime());
		}
		return wunschliefertagString;
	}

    /**
     * Methode überprüft, ob Bestellung erfüllbar ist.
     * @return true, wenn erfüllbar
     */
    public boolean istErfuellbar(){
        for(K_BestellungProduktAssoz assoz: produkte){
            if(assoz.getProdukt() == null || assoz.getProdukt().getLagerBestand() < assoz.getAnzahl()){
                return false;
            }
        }
        return true;
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
        return format.format((double) preis / 100);
    }

}
