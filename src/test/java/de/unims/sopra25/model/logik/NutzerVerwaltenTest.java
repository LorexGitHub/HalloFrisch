package de.unims.sopra25.model.logik;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.logik.NutzerVerwalten.NutzerTyp;

/**
 * Testklasse welche NutzerVerwalten testet
 * @author Leonhard Heß
 */
class NutzerVerwaltenTest {

	NutzerVerwalten nv;

	@BeforeEach
	void setUp() throws Exception {
        Serialisierung.getSerialisierung().reset();
		// Beispieldaten zum Testen
		nv = new NutzerVerwalten();
	}

	/**
	 * Testet ob die Login-Funktion funktioniert
	 */
	@Test
	void testLogin() {
		// Fuege Nutzer hinzu
		try {
			nv.addKunde("max321", "passwort", "Musterstraße 1", "Max", "Mustermann");
			nv.addGeschaeftsfuehrer("admin", "admin");
			nv.addLieferant("liefer1", "123§$456.7:8");
			nv.addEinkaeufer("kauf1", "pass1");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erstellen von Nutzern gibt Exception!");
		}

		// Uebrepruefe Logins
		try {
			assertEquals(NutzerTyp.KUNDE, nv.login("max321", "passwort"), "Kunde kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.LIEFERANT, nv.login("liefer1", "123§$456.7:8"), "Lieferant kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.GESCHAEFTSFUEHRER, nv.login("admin", "admin"), "Geschäftsführer kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.EINKAEUFER, nv.login("kauf1", "pass1"), "Einkäufer kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(null, nv.login("max321", ""), "Kunde kann sich mit falschem Passwort einloggen!");
			assertEquals(null, nv.login("liefer1", "ASDLAJDLJASLDJALSDJASLJDALJDLASJDLASJDLASJDLASJLD"), "Lieferant kann sich mit falschem Passwort einloggen!");
			assertEquals(null, nv.login("admin", "sus"), "Geschäftsführer kann sich mit falschem Passwort einloggen!");
			assertEquals(null, nv.login("kauf1", "pass12"), "Einkäufer kann sich mit falschem Passwort einloggen!");
			assertEquals(null, nv.login("ichExistiereNicht", ""), "Nonexistene Nutzer können mit falschem Passwort einloggen!");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Einloggen von Nutzern gibt Exception!");
		}

		// Zwei Logins sollten Assertion triggern
		try {
			nv.login("max321", "passwort");
			nv.login("liefer1", "123§$456.7:8");
			fail("Es können sich 2 Nutzer gleichzeitig einloggen!");
		} catch (AssertionError e) {
		} catch (Exception e) {
			e.printStackTrace();
			fail("Einloggen von Nutzern gibt Exception!");
		}

		// Teste ob
		assertEquals("max321", nv.getLoggedIn().getNutzername(), "Falscher Nutzer eingeloggt.");
		nv.logOut();

		// LogOut ueberpruefen
		try {
			nv.logOut();
			fail("Ausloggen möglich obwohl nicht eingeloggt!");
		} catch (AssertionError e) {}
	}

	/**
	 * Testet ob die Verschiedenen Funktionen die Nutzer hinzufuegen
	 * korrekt funktionieren
	 */
	@Test
	void testAddNutzer() {
		// Fuege Nutzer hinzu
		try {
			nv.addKunde("max321", "passwort", "Musterstraße 1", "Max", "Mustermann");
			nv.addGeschaeftsfuehrer("admin", "admin");
			nv.addLieferant("liefer1", "123§$456.7:8");
			nv.addEinkaeufer("kauf1", "pass1");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erstellen von Nutzern gibt Exception!");
		}

		// Ueberpruefe ob Nutzer angelegt wurden
		assertTrue(nv.getNutzer("max321") != null, "Kunde nicht korrekt angelegt!");
		assertTrue(nv.getNutzer("admin") != null, "Geschäftsführer nicht korrekt angelegt!");
		assertTrue(nv.getNutzer("liefer1") != null, "Lieferant nicht korrekt angelegt!");
		assertTrue(nv.getNutzer("kauf1") != null, "Einkäufer nicht korrekt angelegt!");

		// Ueberpruefe ob Nutzer mit gleichem Namen angelegt werden koennen
		try {
			assertFalse(nv.addKunde("max321", "passwort", "Musterstraße 1", "Max", "Mustermann"),
					"Kunde mit vergebenem Nutzernamen erstellt!");
			assertFalse(nv.addGeschaeftsfuehrer("liefer1", "admin"),
					"Geschäftsführer mit vergebenem Nutzernamen erstellt!");;
			assertFalse(nv.addLieferant("admin", "123§$456.7:8"),
					"Lieferant mit vergebenem Nutzernamen erstellt!");;
			assertFalse(nv.addEinkaeufer("kauf1", "pass1"),
					"Einkäufer mit vergebenem Nutzernamen erstellt!");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erstellen von Nutzern gibt Exception!");
		}

		// Ueberpruefe ob Attribute des Kunden stimmen
		assertEquals("Max", ((Kunde) nv.getNutzer("max321")).getVorname(), "Kunde Vorname inkorrekt"); // Kunde Vorname
		assertEquals("Mustermann", ((Kunde) nv.getNutzer("max321")).getNachname(), "Kunde Nachname inkorrekt"); // Kunde Nachname
		assertEquals("Musterstraße 1", ((Kunde) nv.getNutzer("max321")).getAdresse(), "Kunde Adresse inkorrekt"); // Kunde Adresse

		// Uebrepruefe Logins
		try {
			assertEquals(NutzerTyp.KUNDE, nv.login("max321", "passwort"), "Kunde kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.LIEFERANT, nv.login("liefer1", "123§$456.7:8"), "Lieferant kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.GESCHAEFTSFUEHRER, nv.login("admin", "admin"), "Geschäftsführer kann sich nicht einloggen!");
			nv.logOut();
			assertEquals(NutzerTyp.EINKAEUFER, nv.login("kauf1", "pass1"), "Einkäufer kann sich nicht einloggen!");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Einloggen von Nutzern gibt Exception!");
		}


	}

	/**
	 * Testet ob die verschiedenen getAnzahl Funktionen funktionieren
	 */
	@Test
	void testGetAnzahl() {
		// Maps sind zu Beginn leer.
		assertEquals(0, nv.getKundenAnzahl(), "NutzerVerwalten wird mit Kunden initialisiert!");
		assertEquals(0, nv.getEinkaeuferAnzahl(), "NutzerVerwalten wird mit Einkäufern initialisiert!");
		assertEquals(0, nv.getGeschaeftsfuehrerAnzahl(), "NutzerVerwalten wird mit Geschäftsführern initialisiert!");
		assertEquals(0, nv.getLieferantenAnzahl(), "NutzerVerwalten wird mit Lieferanten initialisiert!");
		assertEquals(0, nv.getNutzerAnzahl(), "NutzerVerwalten wird mit Nutzern initialisiert!");

		// Fuege verschiedene Nutzer hinzu
		try {
			// 3 Kunden
			nv.addKunde("max321", "passwort", "Musterstraße 1", "Max", "Mustermann");
			nv.addKunde("max123", "passwort2", "Musterstraße 4", "Maxima", "Bauer");
			nv.addKunde("max111", "passwort3", "Musterstraße 32", "Maximilian", "Schneider");

			// 1 Geschaeftsfuehrer
			nv.addGeschaeftsfuehrer("admin", "admin");

			// 2 Lieferanten
			nv.addLieferant("liefer1", "123§$456.7:8");
			nv.addLieferant("liefer2", "p455w0r7");

			// 4 Einkaeufer
			nv.addEinkaeufer("kauf1", "pass1");
			nv.addEinkaeufer("kauf2", "pass2");
			nv.addEinkaeufer("kauf3", "pass3");
			nv.addEinkaeufer("kauf4", "pass4");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erstellen von Nutzern gibt Exception!");
		}

		// Ueberpruefe Anzahl
		assertEquals(3, nv.getKundenAnzahl(), "NutzerVerwalten zählt Kunden falsch!");
		assertEquals(4, nv.getEinkaeuferAnzahl(), "NutzerVerwalten zählt Einkäufer falsch!");
		assertEquals(1, nv.getGeschaeftsfuehrerAnzahl(), "NutzerVerwalten zählt Geschäftsführer falsch!");
		assertEquals(2, nv.getLieferantenAnzahl(), "NutzerVerwalten zählt Lieferanten falsch!");
		assertEquals(10, nv.getNutzerAnzahl(), "NutzerVerwalten zählt Nutzer falsch!");
	}

}
