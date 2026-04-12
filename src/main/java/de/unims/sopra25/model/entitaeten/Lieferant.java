package de.unims.sopra25.model.entitaeten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasse, die einen Lieferant repräsentiert.
 * Ein Lieferant ist ein Nutzer und besteht aus Nutzername und Passwort.
 * 
 * @author Frederik Fluegge
 */
public class Lieferant extends Nutzer {

    // Alle Bestellungen, die noch nicht zugewiesen wurden
    private ArrayList<K_Bestellung> bestellungen;

    /**
     * Konstruktor des Lieferants
     * 
     * @param nutzername Nutzername des Lieferants
     * @param passwort   Passwort des Lieferants
     */
    public Lieferant(String nutzername, String passwort) {
        super(nutzername, passwort);
        this.bestellungen = new ArrayList<>();
    }

    /**
     * Weist dem Lieferanten eine Bestellung zu
     * 
     * @param bestellung ist eine Bestellung, die der Lieferant bearbeiten muss
     */
    public void addBestellung(K_Bestellung bestellung) {
        this.bestellungen.add(bestellung);
        bestellung.setLieferant(this);
    }

    /**
     * nimmt dem Lieferanten eine Referenz auf eine Bestellung;
     * Wird genutzt sowohl nach Auslieferung als auch nach Änderung des Lieferanten
     * 
     * @param bestellung ist die zu entfernende Bestellung
     */
    public void removeBestellung(K_Bestellung bestellung) {
        this.bestellungen.remove(bestellung);
    }

    /**
     * Gibt alle Bestellungen zurück, die noch nicht zugewiesen wurden
     * 
     * @return List
     */
    public List<K_Bestellung> getBestellungen() {
        return Collections.unmodifiableList(this.bestellungen);
    }

}
