package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;

/**
 * Klasse, die eine Assoziation zwischen Warenkorb und Produkt repraesentiert.
 * Eine WarenkorbProduktAssoz enthält zusätzlich die Anzahl der Produkute im
 * Warenkorb
 *
 * @author David Metzdorf
 */
public class WarenkorbProduktAssoz implements Serializable {

    // Warenkorb, der das Produkt beinhaltet
    private Warenkorb warenkorb;

    // Produkt, das sich im Warenkorb befindet
    private Produkt produkt;

    // Anzahl des Produkts im Warenkorb
    private int anzahl;

    /**
     * Konstruktor der Klasse K_WarenkorbProduktAssoz
     *
     * @param warenkorb Warenkorb, der das Produkt beinhaltet
     * @param produkt   Produkt, das sich im Warenkorb befindet
     * @param anzahl    Anzahl des Produkts im Warenkorb
     */
    public WarenkorbProduktAssoz(Warenkorb warenkorb, Produkt produkt, int anzahl) {
        this.warenkorb = warenkorb;
        this.produkt = produkt;
        this.anzahl = anzahl;
    }

    /**
     * Gibt den Warenkorb der Assoziation zurueck
     *
     * @return Warenkorb der Assoziation
     */
    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

    /**
     * Gibt das Produkt der Assoziation zurueck
     *
     * @return Produkt der Assoziation
     */
    public Produkt getProdukt() {
        return produkt;
    }

    /**
     * Gibt die Anzahl des Produkts im Warenkorb zurueck
     *
     * @return Anzahl des Produkts im Warenkorb
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     * Setzt die Anzahl des Produkts im Warenkorb
     * 
     * @param anzahl Anzahl des Produkts im Warenkorb
     */
    public void setAnzahl(int anzahl) {
        if (anzahl <= 0) {
            this.warenkorb.removeProdukt(this);
        }
        this.anzahl = anzahl;
    }

}
