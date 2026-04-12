package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;

/**
 * Klasse, die einen Fehler repraesentiert
 * Enthaelt einen error, der serialisiiert wird
 * 
 * @author David Metzdorf
 */
public class Fehler implements Serializable {

    // Fehler, der gelogged werden soll
    private String error;

    /**
     * Konstruktor einer Fehler
     * 
     * @param error Fehler, der gelogged werden soll
     */
    public Fehler(String error) {
        this.error = error;
    }

    /**
     * Gibt den Fehler zurueck
     * 
     * @return Fehler
     */
    public String getError() {
        return error;
    }

}
