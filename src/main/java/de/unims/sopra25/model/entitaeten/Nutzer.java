package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;

/**
 * Klasse, die einen Nutzer repräsentiert.
 * Ein Nutzer besteht aus Nutzername und Passwort.
 * 
 * @author Frederik Fluegge
 */
public abstract class Nutzer implements Serializable {

	// Nutzername des Nutzername
	private String nutzername;

	// Passwort des Nutzers
	private String passwort;

	/**
	 * Konstruktor des Nutzers
	 * 
	 * @param nutzername Nutzername des Nutzers
	 * @param passwort   gehashtes Passwort des Nutzers
	 */
	public Nutzer(String nutzername, String passwort) {
		this.nutzername = nutzername;
		this.passwort = passwort;
	}

	/**
	 * Setzt den Nutzername des Nutzers.
	 * 
	 * @param nutzername Nutzername des Nutzers
	 */
	private void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}

	/**
	 * Gibt den Nutzernamen des Nutzers zurück.
	 * 
	 * @return Nutzername des Nutzers
	 */
	public String getNutzername() {
		return nutzername;
	}

	/**
	 * Setzt das Passwort der Nutzers.
	 * 
	 * @param passwort Passwort der Nutzers
	 */
	private void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	/**
	 * Gibt das Passwort des Nutzers zurück.
	 * 
	 * @return Passwort der Nutzers
	 */
	public String getPasswort() {
		return passwort;
	}

}
