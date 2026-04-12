package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_BestellungProduktAssoz;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse zur Berechnung und Ausgabe
 * von Statistiken bzg. Bestseller
 * 
 * @author Johannes Schick
 * @author Leonhard Heß
 */
public class BestsellerStatistik {

    // Kann zum Filtern eingestellt werden
    private Date startDate;
    private Date endDate;

    // Liste aller Produkte
    private ArrayList<Produkt> produkte;

    // Liste aller Bestellungen, um herauszufinden, welches Produkt am Meisten
    // bestellt wurde
    private ArrayList<K_Bestellung> bestellungen;

    /**
     * Klasse, welche die Verkaufszahlen von 2 Produkten
     * vergleichen kann
     */
    private class ProduktVerkaufVergleicher implements Comparator<Produkt> {

        // HashMap die Verkaufszahlen cached
        private HashMap<Produkt, Integer> verkaufszahlen;

        /**
         * Konstruktor des Vergleichers. Initialisiert die Verkaufszahl-HashMap
         */
        ProduktVerkaufVergleicher() {
            verkaufszahlen = new HashMap<Produkt, Integer>();
        }

        @Override
        /**
         * Gibt differenz der Verkaufszahlen zurueck
         * 
         * @return Verkaufzahl p2 - Verkaufszahl p1
         */
        public int compare(Produkt p1, Produkt p2) {
            // Falls Verkaufszahlen nicht vorliegen werden sie geholt.
            if (!verkaufszahlen.containsKey(p1))
                verkaufszahlen.put(p1, getVerkaeufe(p1));
            if (!verkaufszahlen.containsKey(p2))
                verkaufszahlen.put(p2, getVerkaeufe(p2));

            // Gib Differenz zurueck
            return verkaufszahlen.get(p2) - verkaufszahlen.get(p1);
        }
    }

    /**
     * Konstruktor. Laedt eine Liste an K_Bestellungen indem
     * durch die Kunden-HashMap durchiteriert wird.
     */
    public BestsellerStatistik() {
        initDates();

        // Hole Liste an k_bestellungen aus den Kunden
        Map<String, Kunde> kunden = Serialisierung.getSerialisierung().getKunden();
        bestellungen = new ArrayList<K_Bestellung>();
        for (Kunde k : kunden.values()) {
            bestellungen.addAll(k.getBestellungen());
        }

        // Hole Liste an Prodkten
        produkte = Serialisierung.getSerialisierung().getProdukte();
    }

    /**
     * Setzt startDate und endDate auf null
     * Dates die auf null gesetzt werden, werden als
     * "unendlich weit in der Vergangenheit" bzw.
     * "unendlich weit in der Zukunft" gewertet.
     */
    public void initDates() {
        startDate = null;
        endDate = null;
    }

    /**
     * Gibt Anzahl an Verkaeufen zwischen startDate
     * und endDate aus.
     * 
     * @param p Produkt fuer welches die Statistik berechnet werden soll
     */
    public int getVerkaeufe(Produkt p) {
        int verkauft = 0;
        for (K_Bestellung kb : bestellungen) {
            // Wir zaehlen eine Bestellung nur, wenn ihr Datum zwischen
            // startDate und endDate liegt
            if ((startDate == null || startDate.before(kb.getDatum())) &&
                    (endDate == null || endDate.after(kb.getDatum()))) {
                // Für alle ProduktAssoz...
                for (K_BestellungProduktAssoz kbpAssoz : kb.getProdukte()) {
                    // ...schaue ob diese fuer das gesuchte Produkt sind...
                    if (kbpAssoz.getProdukt() == p) {
                        // ... und addiere die Anzahl, falls ja.
                        verkauft += kbpAssoz.getAnzahl();
                    }
                }
            }
        }
        return verkauft;
    }

    /**
     * Gibt alle Produkte, sortiert nach Anzahl an Verkäufen
     * innerhalb des spezifizierten Zeitraums aus, aus.
     * Hierbei ist das erste Element das am meisten verkaufteste.
     * 
     * @return Liste an Produkten, welche nach Verkaufszahl sortiert ist.
     */
    public ArrayList<Produkt> getBestseller() {
        @SuppressWarnings("unchecked")
        ArrayList<Produkt> bestseller = (ArrayList<Produkt>) produkte.clone(); // Produktliste klonen zum sortieren
        ProduktVerkaufVergleicher pv = new ProduktVerkaufVergleicher(); // Vergleicher erzeugen
        bestseller.sort(pv); // Mit Vergleicher sortieren
        return bestseller;
    }

    /**
     * Gibt eine Liste von Produkten aus, sortiert nach
     * Anzahl der Verkaeufe innerhalb des spezifizierten
     * Zeitraums.
     * 
     * @param anzahl: Anzahl der Produkte in der ausgegebenen Liste
     * @return sortierte Liste mit anzahl-vielen Produkten
     */
    public List<Produkt> getBestseller(int anzahl) {
        return getBestseller().subList(0, anzahl);
    }

    /**
     * Setzt das EndDatum was fuer Berechnungen
     * verwendet wird.
     * null ist unendlich weit in der Zukunft.
     * 
     * @param endDate Neues EndDatum
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Setzt das StartDatum was fuer Berechnungen
     * verwendet wird
     * null ist unendlich weit in der Vergangenheit.
     * 
     * @param startDate Neues StartDatum
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gibt das aktuelle EndDatum zurueck.
     * null ist unendlich weit in der Zukunft.
     * 
     * @return Aktuelles EndDatum
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gibt das aktuelle StartDatum zurueck.
     * null ist unendlich weit in der Vergangenheit.
     * 
     * @return Aktuelles Startdatum
     */
    public Date getStartDate() {
        return startDate;
    }

}
