package de.unims.sopra25.model.entitaeten;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.io.Serializable;

/**
 * Klasse, die einen Warenkorb eines Kunden repraesentiert.
 * Ein Warenkorb besteht aus Kunden und eine Liste von Produkten.
 *
 * @author David Metzdorf
 */
public class Warenkorb implements Serializable {

    // Kunde, den der Warenkorb gehoert
    private Kunde kunde;

    // Liste von Produkten im Warenkorb
    private ArrayList<WarenkorbProduktAssoz> produkte;

    /**
     * Konstruktor des Warenkorbs
     *
     * @param kunde Kunde, den der Warenkorb gehoert
     */
    public Warenkorb(Kunde kunde) {
        this.kunde = kunde;
        this.produkte = new ArrayList<WarenkorbProduktAssoz>();
    }

    /**
     * Fuegt ein Produkt zum Warenkorb hinzu.
     * 
     * @param anzahl  ist die Anzahl des gegebenen Produktes, die hinzugefügt werden
     *                soll
     * @param produkt Produkt, das hinzugefuegt werden soll
     */
    public void addProdukt(Produkt produkt, int anzahl) {
        ;
        for (int i = 0; i < produkte.size(); i++) {
            WarenkorbProduktAssoz currElement = produkte.get(i);
            if (produkt == currElement.getProdukt()) {
                currElement.setAnzahl(currElement.getAnzahl() + anzahl);
                if (currElement.getAnzahl() <= 0) {
                    produkte.remove(i);
                }
                return;
            }
        }
        WarenkorbProduktAssoz prodAssoz = new WarenkorbProduktAssoz(this, produkt, anzahl);
        produkte.add(prodAssoz);
    }

    /**
     * Entfernt ein Produkt aus dem Warenkorb
     * 
     * @param produkt Produkt, das entfernt werden soll
     */

    public void removeProdukt(Produkt produkt) {
        for (int i = 0; i < produkte.size(); i++) {
            WarenkorbProduktAssoz currElement = produkte.get(i);
            if (produkt == currElement.getProdukt()) {
                this.produkte.remove(i);
            }
        }
    }

    /**
     * Entfernt die gegebene ProduktAssoz aus dem Warenkorb
     * 
     * @param produktAssoz zu entfernende ProduktAssoz
     */
    public void removeProdukt(WarenkorbProduktAssoz produktAssoz) {
        this.produkte.remove(produktAssoz);
    }

    /**
     * Methode leert den Warenkorb.
     */
    public void leeren() {
        this.produkte.clear();
    }

    /**
     * Gibt den Kunden, den der Warenkorb gehoert, zurueck.
     *
     * @return Kunde, den der Warenkorb gehoert
     */
    public Kunde getKunde() {
        return kunde;
    }

    /**
     * Gibt die Produkte aus der Produkt Liste
     *
     * @return Produkte im Warenkorb
     */
    public List<WarenkorbProduktAssoz> getProdukte() {
        return Collections.unmodifiableList(produkte);
    }

}
