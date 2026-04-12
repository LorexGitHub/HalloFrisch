package de.unims.sopra25.model.entitaeten;

/**
 * Klasse, die einen Einkäufer repräsentiert.
 * Ein Einkäufer ist ein Nutzer und besteht aus Nutzername und Passwort.
 * 
 * @author Frederik Fluegge
 */
public class Einkaeufer extends Nutzer {

	/**
	 * Konstruktor des Einkäufers
	 * 
	 * @param nutzername Nutzername des Einkäufers
	 * @param passwort   Passwort des Einkäufers
	 */
	public Einkaeufer(String nutzername, String passwort) {
		super(nutzername, passwort);
	}

}
