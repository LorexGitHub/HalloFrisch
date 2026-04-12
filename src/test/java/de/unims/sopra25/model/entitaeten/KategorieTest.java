package de.unims.sopra25.model.entitaeten;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testklasse fuer Kategorie
 * @author Leonhard Heß
 */
class KategorieTest {

	// Kategorien und Produkte zum Testen
	Kategorie k1;
	Produkt p1;
	Produkt p2;
	Produkt p3;
	
	@BeforeEach
	void setUp() throws Exception {
		// Testdaten erstellen
		k1 = new Kategorie("Kategorie-1");
		p1 = new Produkt("Produkt-1", 100, "Das erste Produkt", "Produkt-1.png");
		p2 = new Produkt("Produkt-2", 200, "Das zweite Produkt", "Produkt-2.png");
		p3 = new Produkt("Produkt-3", 300, "Das dritte Produkt", "Produkt-3.png");
	}

	@Test
	void testGetUndSetName() {
		// Ueberpruefe ob Name vom Konstrukter erhalten bleibt
		assertEquals("Kategorie-1", k1.getName(), "getName gibt nicht den Namen aus Konstruktor");
		
		// Ueberpruefe ob setName() den Namen wirklich veraendert
		k1.setName("Kategorie-eins");
		assertEquals("Kategorie-eins", k1.getName(), "setName setzt Namen nicht richtig");
	}
	
	@Test
	void testGetUndAddProdukte() {
		// Zu Beginn sind keine Produkte in dieser Kategorie
		assertEquals(0, k1.getProdukte().size(), "Leere Kategorie ist nicht leer");
		
		// Fuege 3 verschiedene Produkte hinzu
		k1.addProdukt(p1);
		k1.addProdukt(p2);
		k1.addProdukt(p3);
		
		// Teste ob die 3 Produkte hinzugefuegt wurden
		assertEquals(3, k1.getProdukte().size(), "Kategorie mit 3 Produkten enthält nicht 3 Produkte");
		assertTrue(k1.getProdukte().contains(p1), "Kategorie enthält hinzugefügtes Produkt nicht (p1)");
		assertTrue(k1.getProdukte().contains(p2), "Kategorie enthält hinzugefügtes Produkt nicht (p2)");
		assertTrue(k1.getProdukte().contains(p3), "Kategorie enthält hinzugefügtes Produkt nicht (p3)");
		
		// Teste ob die Kategorie der Profukte auf k1 gesetzt wurde
		assertTrue(p1.getKategorien().contains(k1), "Produkt ist Kategorie nicht zugeteilt (p1)");
		assertTrue(p2.getKategorien().contains(k1), "Produkt ist Kategorie nicht zugeteilt (p2)");
		assertTrue(p3.getKategorien().contains(k1), "Produkt ist Kategorie nicht zugeteilt (p3)");
		
		// Produkte koennen nicht 2-mal hinzugefuegt werden.
		k1.addProdukt(p1);
		assertEquals(3, k1.getProdukte().size(), "Produkte koennen doppelt hinzugefuegt werden");
	}
	
	@Test
	void testRemoveProdukte() {
		// Fuege Produkte zum entfernen hinzu
		k1.addProdukt(p1);
		k1.addProdukt(p2);
		k1.addProdukt(p3);
		
		// Entferne ein Produkt
		k1.removeProdukt(p1);
		
		// Produkt sollte nun entfernt sein
		assertEquals(2, k1.getProdukte().size(), "Kategorie mit 2 Produkten enthält nicht 2 Produkte");
		assertFalse(k1.getProdukte().contains(p1), "Kategorie enthält entferntes Produkt");
		assertTrue(k1.getProdukte().contains(p2), "Kategorie enthält hinzugefügtes Produkt nicht (p2)");
		assertTrue(k1.getProdukte().contains(p3), "Kategorie enthält hinzugefügtes Produkt nicht (p3)");
		
		// Das entfernte Produkt sollte dieser Kategorie nicht mehr zugewiesen sein
		assertFalse(p1.getKategorien().contains(k1), "Entferntes Produkt ist Kategorie zugeteilt");
		
		// Doppeltes entfernen aendert nichts
		k1.removeProdukt(p1);
		assertEquals(2, k1.getProdukte().size(), "Produkte koennen doppelt entfernt werden");
		assertFalse(k1.getProdukte().contains(p1), "Kategorie enthält doppelt entferntes Produkt");
		assertFalse(p1.getKategorien().contains(k1), "Doppelt entferntes Produkt ist Kategorie zugeteilt");
	}

}
