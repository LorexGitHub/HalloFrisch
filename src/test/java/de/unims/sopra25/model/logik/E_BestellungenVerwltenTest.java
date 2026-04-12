package de.unims.sopra25.model.logik;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.model.entitaeten.Produkt;

/**
 * Testklasse die E_BestellungenVerwalten testet
 * @author Leonhard Heß
 */
class E_BestellungenVerwltenTest {

	E_BestellungenVerwalten ebv;
	Produkt p1;
	Produkt p2;


	@BeforeEach
	void setUp() throws Exception {
        Serialisierung.getSerialisierung().reset();
		// Beispieldaten
		ebv = new E_BestellungenVerwalten();
		p1 = new Produkt("Schoki", 1000, "Lecker Schoki", "schoki.png");
		p2 = new Produkt("Apfel", 1000, "Lecker Apfel", "apfel.png");
	}

	@Test
	void testInit() {
		// Neue Liste an E_Bestellungen sollte leer sein
		assertEquals(0, ebv.getBestellungen().size());
	}

	@Test
	void testAddProdukt() {
		// Fuege ein Paar Nachbestellungen hinzu
		E_Bestellung b1 = ebv.addBestellung(p1, 100);
		E_Bestellung b2 = ebv.addBestellung(p1, 200);
		E_Bestellung b3 = ebv.addBestellung(p2, 50);

		// Schaue ob Nachbestellungen aufgegeben wurden.
		assertEquals(300, p1.getAnzahlNachbestellteExemplare(), "Inkorrekte Anzahl nachbestellter Artikel (p1)");
		assertEquals(50, p2.getAnzahlNachbestellteExemplare(), "Inkorrekte Anzahl nachbestellter Artikel (p2)");
		assertEquals(3, ebv.getBestellungen().size(), "Inkorrekte Anzahl Nachbestellungen");
		assertTrue(ebv.getBestellungen().contains(b1), "Bestellung 1 nicht gefunden");
		assertTrue(ebv.getBestellungen().contains(b2), "Bestellung 2 nicht gefunden");
		assertTrue(ebv.getBestellungen().contains(b3), "Bestellung 3 nicht gefunden");

		// Schaue ob Beziehungen korrekt gesetzt wurden
		// Produkt 1
		assertEquals(2, p1.getNachbestellungen().size(), "Inkorrekte Anzahl Nachbestellungen (p1)");
		assertTrue(p1.getNachbestellungen().contains(b1), "Bestellung 1 nicht gefunden (p1)");
		assertTrue(p1.getNachbestellungen().contains(b2), "Bestellung 2 nicht gefunden (p1)");
		for(E_Bestellung b : p1.getNachbestellungen()) {
			assertEquals(p1, b.getProdukt(), "Nachbestellung ist Produkt nicht zugewiesen (p1)");
		}
		// Produkt 2
		assertEquals(1, p2.getNachbestellungen().size(), "Inkorrekte Anzahl Nachbestellungen (p2)");
		assertTrue(p2.getNachbestellungen().contains(b3), "Bestellung 3 nicht gefunden (p2)");
		for(E_Bestellung b : p2.getNachbestellungen()) {
			assertEquals(p2, b.getProdukt(), "Nachbestellung ist Produkt nicht zugewiesen (p2)");
		}
	}

	@Test
	void testRemoveBestellung() {
		// Fuege ein Paar Nachbestellungen hinzu
		E_Bestellung b1 = ebv.addBestellung(p1, 100);
		ebv.addBestellung(p1, 200);

		// Entferne eine Nachbestellung
		ebv.removeBestellung(b1);

		// Produkt hat nun 100 Artikel und 1 Nachbestellung von 200 Artikeln
		assertEquals(200, p1.getAnzahlNachbestellteExemplare(), "Inkorrekte Anzahl nachbestellter Artikel");
		assertEquals(100, p1.getBestand(), "removeBestellung fügt Bestellte Examplare nicht hinzu");
		assertEquals(1, p1.getNachbestellungen().size(), "Inkorrekte Anzahl Nachbestellungen");

		// Entfernen einer nicht vorhandenen Nachbestellung triggert AssertionError
		boolean assertsEnabled = false;
		assert assertsEnabled = true;
		if(assertsEnabled) {
			try {
				ebv.removeBestellung(b1);
				fail("Doppeltes Entfernen löst keinen AssertionError aus");
			} catch (AssertionError e) {}
		}
	}

	@Test
	void testPurgeBestellung() {
		// Fuege ein Paar Nachbestellungen hinzu
		E_Bestellung b1 = ebv.addBestellung(p1, 100);
		ebv.addBestellung(p1, 200);

		// Entferne eine Nachbestellung
		ebv.purgeBestellung(b1);

		// Produkt hat nun 100 Artikel und 1 Nachbestellung von 200 Artikeln
		assertEquals(200, p1.getAnzahlNachbestellteExemplare(), "Inkorrekte Anzahl nachbestellter Artikel");
		assertEquals(0, p1.getBestand(), "purgeBestellung fügt Bestellte Examplare hinzu");
		assertEquals(1, p1.getNachbestellungen().size(), "Inkorrekte Anzahl Nachbestellungen");

		// Entfernen einer nicht vorhandenen Nachbestellung triggert AssertionError
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Setzt assertsEnabled auf true falls Assertions an sind
		if(assertsEnabled) {
			try {
				ebv.removeBestellung(b1);
				fail("Doppeltes Entfernen löst keinen AssertionError aus");
			} catch (AssertionError e) {}

			// Zustand sollte sich nicht geaendert haben
			assertEquals(200, p1.getAnzahlNachbestellteExemplare(), "Inkorrekte Anzahl nachbestellter Artikel (AE)");
			assertEquals(0, p1.getBestand(), "purgeBestellung fügt Bestellte Examplare hinzu (AE)");
			assertEquals(1, p1.getNachbestellungen().size(), "Inkorrekte Anzahl Nachbestellungen (AE)");
		}
	}

}
