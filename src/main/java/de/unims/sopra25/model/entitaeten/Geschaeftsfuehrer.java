package de.unims.sopra25.model.entitaeten;

/**
 * Klasse, die einen Geschäftsführer repräsentiert.
 * Ein Geschäftsführer ist ein Nutzer und besteht aus Nutzername und Passwort.
 * 
 * @author Frederik Fluegge
 */
public class Geschaeftsfuehrer extends Nutzer {

	/**
	 * Konstrukor des Geschäftsführers
	 * 
	 * @param nutzername Nutzername des Geschäftsführers
	 * @param passwort   Passwort des Geschäftsführers
	 */
	public Geschaeftsfuehrer(String nutzername, String passwort) {
		super(nutzername, passwort);
	}

}
