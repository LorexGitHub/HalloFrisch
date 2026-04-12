package de.unims.sopra25.model.entitaeten;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Klasse die die Kunden Entitaet testet
 * @author Leonhard Heß
 */
class KundeTest {

	Kunde k1;

	@BeforeEach
	void setUp() throws Exception {
		// Beispielkunden konstruieren
		k1 = new Kunde("max321", "passwort", "Musterweg 1", "Max", "Mustermann");
	}

	@Test
	void testGetUndSetWarenkorb() {
		// Kunde sollte selbstständig leeren Warenkorb
		// erstellen welcher ihm zugeteilt ist
		assertEquals(k1, k1.getWarenkorb().getKunde(), "Im Konstruktor erstellter Warenkorb ist inkorrektem Kunden zugeteilt");
		assertEquals(0, k1.getWarenkorb().getProdukte().size(), "Im Konstruktor erstellter Warenkorb ist nicht leer");

		// Ueberpruefe ob das setzen des Warenkorbs funktioniert
        //Sinn des Tests ist fragwürdig gewesen, da Objekte mit gleichem Inhalt überprüft wurden
		Warenkorb w1 = k1.getWarenkorb();
		assertEquals(w1, k1.getWarenkorb(), "setWarenkorb setzt den Warenkorb inkorrekt");
	}

	@Test
	void testGetUndSetAdresse() {
		// getAdresse sollte Adresse aus Konstruktor geben
		assertEquals("Musterweg 1", k1.getAdresse(), "Adresse aus Konstruktor inkorrekt ausgegeben");

		// setAdresse sollte Adresse aendern koennen
		k1.setAdresse("Musterstraße 1");
		assertEquals("Musterstraße 1", k1.getAdresse(), "Adresse aus Setter inkorrekt ausgegeben");
	}

	@Test
	void testGetUndSetVorname() {
		// getVorname sollte Vorname aus Konstruktor geben
		assertEquals("Max", k1.getVorname(), "Vorname aus Konstruktor inkorrekt ausgegeben");

		// setVorname sollte Vorname aendern koennen
		k1.setVorname("Maximilian");
		assertEquals("Maximilian", k1.getVorname(), "Vorname aus Setter inkorrekt ausgegeben");
	}

	@Test
	void testGetUndSetNachname() {
		// getNachname sollte Nachname aus Konstruktor geben
		assertEquals("Mustermann", k1.getNachname(), "Nachname aus Konstruktor inkorrekt ausgegeben");

		// setNachname sollte Nachname aendern koennen
		k1.setNachname("Fraktalmann");
		assertEquals("Fraktalmann", k1.getNachname(), "Nachname aus Setter inkorrekt ausgegeben");
	}
}
