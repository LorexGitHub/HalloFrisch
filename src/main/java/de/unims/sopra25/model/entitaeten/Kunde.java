package de.unims.sopra25.model.entitaeten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasse, die einen Kunden repraesentiert.
 * Eine Kunde besteht aus Nutzername, Passwort, Adresse, Vorname, Nachname
 * und Warenkorb.
 *
 * @author David Metzdorf
 */
public class Kunde extends Nutzer {

    // Name des Kunden
    private String adresse;

    // Vorname des Kunden
    private String vorname;

    // Nachname des Kunden
    private String nachname;

    // Warenkorb des Kunden
    private Warenkorb warenkorb;

    // Eigene Bestellungen des Kundens
    private ArrayList<K_Bestellung> eigeneBestellungen;

    /**
     * Konstruktor des Kunden
     * 
     * @param nutzername Nutzername des Kunden
     * @param passwort   gehashtes Passwort des Kunden
     * @param adresse    Adresse des Kunden
     * @param vorname    Vorname des Kunden
     * @param nachname   Nachname des Kunden
     */
    public Kunde(String nutzername, String passwort,
            String adresse, String vorname, String nachname) {
        super(nutzername, passwort);
        this.adresse = adresse;
        this.vorname = vorname;
        this.nachname = nachname;
        this.warenkorb = new Warenkorb(this);
        this.eigeneBestellungen = new ArrayList<>();
    }

    /**
     * Gibt die Adresse des Kunden zurueck.
     *
     * @return Adresse des Kunden
     */

    public String getAdresse() {
        return adresse;
    }

    /**
     * Fügt dem Kunden eine aufgegebene Bestellung hinzu.
     * 
     * @param k ist eine Bestellung, die der Kunde aufgegeben hat
     */
    public void addBestellung(K_Bestellung k) {
        eigeneBestellungen.add(k);
    }

    public List<K_Bestellung> getBestellungen() {
        return Collections.unmodifiableList(eigeneBestellungen);
    }

    /**
     * Setzt die Adresse des Kunden.
     *
     * @param adresse Adresse des Kunden
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Gibt den Vornamen des Kunden zurueck.
     *
     * @return Vorname des Kunden
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Kunden.
     * 
     * @param vorname Vornamen des Kunden
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * Gibt den Nachnamen des Kunden zurueck.
     *
     * @return Nachname des Kunden
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen des Kunden.
     *
     * @param nachname Nachname des Kunden
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * Gibt den Warenkorb des Kunden zurueck.
     *
     * @return Warenkorb des Kunden
     */
    public Warenkorb getWarenkorb() {
        return warenkorb;
    }

}
