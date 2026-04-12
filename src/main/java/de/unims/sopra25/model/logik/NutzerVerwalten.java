package de.unims.sopra25.model.logik;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import de.unims.sopra25.model.entitaeten.Einkaeufer;
import de.unims.sopra25.model.entitaeten.Geschaeftsfuehrer;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Lieferant;
import de.unims.sopra25.model.entitaeten.Nutzer;
import de.unims.sopra25.model.logik.PasswordHashing.CryptographicallyHashedPassword;

/**
 * Logikklasse um Nutzer des Systems zu verwalten
 * 
 * @author Leonhard Heß
 */
public class NutzerVerwalten {

	// Definitionen von Nutzertypen
	public enum NutzerTyp {
		KUNDE,
		GESCHAEFTSFUEHRER,
		LIEFERANT,
		EINKAEUFER,
		NUTZER;
	}

	// Maps die verschiedene Typen von Nutzern speichern
	HashMap<String, Kunde> kunden;
	HashMap<String, Geschaeftsfuehrer> geschaeftsfuehrer;
	HashMap<String, Einkaeufer> einkaeufer;
	HashMap<String, Lieferant> lieferanten;

	// Eingeloggter Nutzer
	Nutzer loggedIn;

	/**
	 * Konstruktor der NutzerVerwalten Klasse.
	 * Lädt die HashMaps für Kunden, Geschäftsführer, Einkäufer und Lieferanten aus
	 * der Serialisierung
	 */
	public NutzerVerwalten() {
		// Maps aus Serialisierung laden
		Serialisierung serialisierung = Serialisierung.getSerialisierung();
		this.kunden = serialisierung.getKunden();
		this.geschaeftsfuehrer = serialisierung.getGeschaeftsfuehrer();
		this.einkaeufer = serialisierung.getEinkaeufer();
		this.lieferanten = serialisierung.getLieferanten();
	}

	/**
	 * Login Methode für das System. Nur ein einziger Nutzer darf Eingeloggt sein.
	 * 
	 * @param nutzername Nutzername des Nutzers
	 * @param passwort   Passwort des Nutzers (Klartext)
	 * @return NutzerTyp falls Login valide ist, null sonst.
	 *
	 * @pre Es ist kein Nutzer eingeloggt
	 * @post Falls Login erfolgreich: Ein Nutzer ist eingeloggt.
	 *
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public NutzerTyp login(String nutzername, String passwort)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		assert loggedIn == null : "Login ausgeführt, obwohl Nutzer eingeloggt ist!";

		// Falls keine Geschaeftsfuehrer existieren, so kann man sich mit
		// Name: "g" ; Pass: ""
		if(getGeschaeftsfuehrerAnzahl()==0 &&
				nutzername.equals("g") &&
				passwort.equals("")) {
			loggedIn = new Geschaeftsfuehrer("g", "");
			return NutzerTyp.GESCHAEFTSFUEHRER;
		}
		
		// Nutzer finden
		Nutzer nutzer = getNutzer(nutzername);

		// Falls Nutzer nicht existiert, gib null zurueck
		if (nutzer == null)
			return null;

		// Passworthashes vergleichen
		PasswordHashing hasher = new PasswordHashing();
		CryptographicallyHashedPassword hashedPW = hasher.new CryptographicallyHashedPassword(nutzer.getPasswort());
		if (hasher.validate(hashedPW, passwort.toCharArray())) {
			// Logge Kunden ein
			loggedIn = nutzer;

			// Falls Passwort korrekt ist, gebe Nutzertyp zurueck
			if (nutzer instanceof Kunde)
				return NutzerTyp.KUNDE;
			if (nutzer instanceof Einkaeufer)
				return NutzerTyp.EINKAEUFER;
			if (nutzer instanceof Geschaeftsfuehrer)
				return NutzerTyp.GESCHAEFTSFUEHRER;
			if (nutzer instanceof Lieferant)
				return NutzerTyp.LIEFERANT;
			assert false : "Nutzer hat unbekannten Typ!";
		}

		// Falls Passwort falsch ist, gib null zurueck
		return null;
	}

	/**
	 * Loggt den aktuell angemeldeten Nutzer aus.
	 * Ruft write-methode der Serialisierung auf, um alle zu speichernden Listen zu
	 * speichern.
	 * 
	 * @pre Ein Nutzer ist eingeloggt
	 */
	public void logOut() {
		assert loggedIn != null : "Logout ausgeführt obwohl keiner eingeloggt ist!";
		loggedIn = null;

		Serialisierung.getSerialisierung().write();
	}

	/*
	 * ========================================================
	 * Getter für Nutzer
	 * ========================================================
	 */

	/**
	 * Gibt den eingeloggten Nutzer zurueck.
	 * 
	 * @return Eingeloggter Nutzer
	 */
	public Nutzer getLoggedIn() {
		return loggedIn;
	}

	/**
	 * Gibt die Anzahl der Nutzer im System zurück
	 * 
	 * @return Anzahl der Nutzer
	 */
	public int getNutzerAnzahl() {
		return getEinkaeuferAnzahl()
				+ getKundenAnzahl()
				+ getLieferantenAnzahl()
				+ getGeschaeftsfuehrerAnzahl();
	}

	/**
	 * Gibt die Anzahl der Kunden im System zurück
	 * 
	 * @return Anzahl der Kunden
	 */
	public int getKundenAnzahl() {
		return kunden.size();
	}

	/**
	 * Gibt die Anzahl der Lieferanten im System zurück
	 * 
	 * @return Anzahl der Lieferanten
	 */
	public int getLieferantenAnzahl() {
		return lieferanten.size();
	}

	/**
	 * Gibt die Anzahl der Geschaeftsfuehrer im System zurück
	 * 
	 * @return Anzahl der Geschaeftsfuehrer
	 */
	public int getGeschaeftsfuehrerAnzahl() {
		return geschaeftsfuehrer.size();
	}

	/**
	 * Gibt die Anzahl der Einkaeufer im System zurück
	 * 
	 * @return Anzahl der Einkaeufer
	 */
	public int getEinkaeuferAnzahl() {
		return einkaeufer.size();
	}

	/**
	 * Gibt den Nutzer, falls dieser existiert, zurueck. null sonst.
	 * 
	 * @param nutzername Nutzername des Nutzers
	 * @return Nutzer, falls dieser existiert, sonst null
	 */
	public Nutzer getNutzer(String nutzername) {
		// Jede Map durchsuchen
		if (kunden.containsKey(nutzername))
			return kunden.get(nutzername);
		if (geschaeftsfuehrer.containsKey(nutzername))
			return geschaeftsfuehrer.get(nutzername);
		if (lieferanten.containsKey(nutzername))
			return lieferanten.get(nutzername);
		if (einkaeufer.containsKey(nutzername))
			return einkaeufer.get(nutzername);

		// Falls nichts gefunden wurde, existiert der Nutzer nicht
		// in dem Fall gib null zurueck
		return null;
	}

	/*
	 *
	 * ========================================================
	 * Nutzer hinzufügen
	 * ========================================================
	 *
	 */

	/**
	 * Fuegt einen Kunden dem System hinzu. Gibt true zurueck falls erfolgreich.
	 * 
	 * @param nutzername Nutzername vom Kunden
	 * @param passwort   Passwort vom Kunden
	 * @param adresse    Adresse vom Kunden
	 * @param vorname    Vorname vom Kunden
	 * @param nachname   Nachname vom Kunden
	 * @return true falls der Kunde dem System hinzugefuegt wurde
	 *
	 * @post getKundenAnzahl() ist bei Erfolgreichem hizufuegen um 1 erhoeht
	 *
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean addKunde(String nutzername, String passwort, String adresse, String vorname, String nachname)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int anzahlPre = getKundenAnzahl();

		// Check ob Nutzer mit diesem Namen bereits existiert
		if (getNutzer(nutzername) != null) {
			return false; // Nutzer mit diesem Namen existiert bereits
		}

		// Kunden erstellen und hinzufuegen
		Kunde kunde = new Kunde(nutzername, hash(passwort), adresse, vorname, nachname);
		kunden.put(nutzername, kunde);

		assert getKundenAnzahl() == anzahlPre + 1 : "Anzahl Kunden nicht erhöht, trotz erfolgreichem Hinzufügen!";
		return true;
	}

	/**
	 * Fuegt einen Einkaeufer dem System hinzu. Gibt true zurueck falls erfolgreich.
	 * 
	 * @param nutzername Nutzername vom Einkaeufer
	 * @param passwort   Passwort vom Einkaeufer
	 * @return true falls der Einkaeufer dem System hinzugefuegt wurde
	 *
	 * @post getEinkaeuferAnzahl() ist bei Erfolgreichem hizufuegen um 1 erhoeht
	 *
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean addEinkaeufer(String nutzername, String passwort)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int anzahlPre = getEinkaeuferAnzahl();

		// Check ob Nutzer mit diesem Namen bereits existiert
		if (getNutzer(nutzername) != null) {
			return false; // Nutzer mit diesem Namen existiert bereits
		}

		// Einkaeufer erstellen und hinzufuegen
		Einkaeufer einkaeufer = new Einkaeufer(nutzername, hash(passwort));
		this.einkaeufer.put(nutzername, einkaeufer);

		assert getEinkaeuferAnzahl() == anzahlPre + 1
				: "Anzahl Einkäufer nicht erhöht, trotz erfolgreichem Hinzufügen!";
		return true;
	}

	/**
	 * Fuegt einen Geschaeftsfuehrer dem System hinzu. Gibt true zurueck falls
	 * erfolgreich.
	 * 
	 * @param nutzername Nutzername vom Geschaeftsfuehrer
	 * @param passwort   Passwort vom Geschaeftsfuehrer
	 * @return true falls der Geschaeftsfuehrer dem System hinzugefuegt wurde
	 *
	 * @post getGeschaeftsfuehrerAnzahl() ist bei Erfolgreichem hizufuegen um 1
	 *       erhoeht
	 *
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean addGeschaeftsfuehrer(String nutzername, String passwort)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int anzahlPre = getGeschaeftsfuehrerAnzahl();

		// Check ob Nutzer mit diesem Namen bereits existiert
		if (getNutzer(nutzername) != null) {
			return false; // Nutzer mit diesem Namen existiert bereits
		}

		// Geschaeftsfuehrer erstellen und hinzufuegen
		Geschaeftsfuehrer geschaeftsfuehrer = new Geschaeftsfuehrer(nutzername, hash(passwort));
		this.geschaeftsfuehrer.put(nutzername, geschaeftsfuehrer);

		assert getGeschaeftsfuehrerAnzahl() == anzahlPre + 1
				: "Anzahl Geschäftsführer nicht erhöht, trotz erfolgreichem Hinzufügen!";
		return true;
	}

	/**
	 * Fuegt einen Lieferant dem System hinzu. Gibt true zurueck falls erfolgreich.
	 * 
	 * @param nutzername Nutzername vom Lieferant
	 * @param passwort   Passwort vom Lieferant
	 * @return true falls der Lieferant dem System hinzugefuegt wurde
	 *
	 * @post getLieferantenAnzahl() ist bei Erfolgreichem hizufuegen um 1 erhoeht
	 *
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean addLieferant(String nutzername, String passwort)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int anzahlPre = getLieferantenAnzahl();

		// Check ob Nutzer mit diesem Namen bereits existiert
		if (getNutzer(nutzername) != null) {
			return false; // Nutzer mit diesem Namen existiert bereits
		}

		// Lieferant erstellen und hinzufuegen
		Lieferant lieferant = new Lieferant(nutzername, hash(passwort));
		this.lieferanten.put(nutzername, lieferant);

		assert getLieferantenAnzahl() == anzahlPre + 1
				: "Anzahl Lieferanten nicht erhöht, trotz erfolgreichem Hinzufügen!";
		return true;
	}

	/**
	 * Hashed den Eingegebenen String mit der Hashing Klasse
	 * welche aus dem Learnweb kopiert wurde.
	 * 
	 * @return Hash des Passworts
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	private String hash(String eingabe) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PasswordHashing hasher = new PasswordHashing();
		CryptographicallyHashedPassword hash = hasher.hashSecurely(eingabe.toCharArray());
		return hash.toString();
	}
}
