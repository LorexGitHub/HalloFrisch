package de.unims.sopra25.model.logik;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.Einkaeufer;
import de.unims.sopra25.model.entitaeten.Geschaeftsfuehrer;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Lieferant;

/**
 * Klasse welche NutzungsStatistik testet
 * @author Leonhard Heß
 */
class NutzungsStatistikTest {

	@BeforeEach
	void setUp() throws Exception {
		Serialisierung ser = Serialisierung.getSerialisierung();
		ser.reset();

		// Fuege 4 Kunden hinzu
		ser.getKunden().put("k1", new Kunde("k1", null, null, null, null));
		ser.getKunden().put("k2", new Kunde("k2", null, null, null, null));
		ser.getKunden().put("k3", new Kunde("k3", null, null, null, null));
		ser.getKunden().put("k4", new Kunde("k4", null, null, null, null));

		// Fuege 3 Lieferanten hinzu
		ser.getLieferanten().put("l1", new Lieferant("l1", null));
		ser.getLieferanten().put("l2", new Lieferant("l2", null));
		ser.getLieferanten().put("l3", new Lieferant("l3", null));

		// Fuege 2 Geschaeftsfuehrer hinzu
		ser.getGeschaeftsfuehrer().put("g1", new Geschaeftsfuehrer("g1", null));
		ser.getGeschaeftsfuehrer().put("g2", new Geschaeftsfuehrer("g2", null));

		// Fuege 1 Einkaeufer hinzu
		ser.getEinkaeufer().put("e1", new Einkaeufer("e1", null));
	}

	@Test
	void testGetAnzahlKunden() {
		// Teste ob die Anzahl der Kunden korrekt ist
		assertEquals(4, new NutzungsStatistik().getAnzahlKunden(), "Anzahl Kunden inkorrekt");
	}

	@Test
	void testGetAnzahlAuslieferer() {
		// Teste ob die Anzahl der Kunden korrekt ist
		assertEquals(3, new NutzungsStatistik().getAnzahlAuslieferer(), "Anzahl Auslieferer inkorrekt");
	}

	@Test
	void testGetAnzahlGeschaeftsfuehrer() {
		// Teste ob die Anzahl der Kunden korrekt ist
		assertEquals(2, new NutzungsStatistik().getAnzahlGeschaeftsfuehrer(), "Anzahl Geschäftsführer inkorrekt");
	}

	@Test
	void testGetAnzahlEinkaeufer() {
		// Teste ob die Anzahl der Kunden korrekt ist
		assertEquals(1, new NutzungsStatistik().getAnzahlEinkaeufer(), "Anzahl Einkäufer inkorrekt");
	}

	@Test
	void testGetAnzahlNutzer() {
		// Teste ob die Anzahl der Kunden korrekt ist
		assertEquals(10, new NutzungsStatistik().getAnzahlNutzer(), "Anzahl Nutzer inkorrekt");
	}

}
