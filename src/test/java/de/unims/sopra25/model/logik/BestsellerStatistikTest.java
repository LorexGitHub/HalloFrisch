package de.unims.sopra25.model.logik;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_Bestellung.Zahlungsmittel;

/**
 * Klasse welche BestsellerStatistik testet
 * @author Leonhard Heß
 */
class BestsellerStatistikTest {
	
	BestsellerStatistik bs;
	Produkt p1;
	Produkt p2;
	Produkt p3;
	
	@BeforeEach
	void setUp() throws Exception {
		// Alte Daten entfernen
		Serialisierung ser = Serialisierung.getSerialisierung();
		ser.reset();
		
		// Kunden erstellen
		Kunde testKunde1 = new Kunde("kunde", "pass", "str 123", "Kun", "Deh");
		Kunde testKunde2 = new Kunde("kundin", "passwd", "str 321", "Kun", "Dihn");
		Kunde testKunde3 = new Kunde("niemand", "p", "nirgends", "Nhie", "Mahnd");
		ser.getKunden().put("kunde", testKunde1);
		ser.getKunden().put("kundin", testKunde2);
		ser.getKunden().put("niemand", testKunde3);
		
		// Beispielprodukte generieren
		p1 = new Produkt("p1", 1, "p1 besch.", "p1.png");
		p2 = new Produkt("p2", 2, "p2 besch.", "p2.png");
		p3 = new Produkt("p3", 3, "p3 besch.", "p3.png");
		ser.getProdukte().add(p1);
		ser.getProdukte().add(p2);
		ser.getProdukte().add(p3);
		
		
		// Testzeiten erstellen
		Clock c1 = Clock.fixed( 
				  Instant.parse("2017-11-01T00:00:00Z"),
				  ZoneOffset.UTC);
		Clock c2 = Clock.fixed( 
				  Instant.parse("2020-01-01T00:00:00Z"),
				  ZoneOffset.UTC);
		Clock c3 = Clock.fixed( 
				  Instant.parse("2001-09-11T08:00:00Z"),
				  ZoneOffset.UTC);
		
		// Testbestellungen erstellen
		testKunde1.getWarenkorb().addProdukt(p1, 2);
		testKunde1.getWarenkorb().addProdukt(p2, 3);
		testKunde1.getWarenkorb().addProdukt(p3, 7);
		K_Bestellung b1 = new K_Bestellung(0, Zahlungsmittel.BAR, testKunde1.getWarenkorb(), c1);
		testKunde1.getWarenkorb().addProdukt(p1, 12);
		K_Bestellung b2 = new K_Bestellung(0, Zahlungsmittel.BAR, testKunde1.getWarenkorb(), c2);
		testKunde2.getWarenkorb().addProdukt(p3, 15);
		K_Bestellung b3 = new K_Bestellung(0, Zahlungsmittel.BAR, testKunde2.getWarenkorb(), c3);
		
		// Logik erstellen
		bs = new BestsellerStatistik();
	}

	@Test
	void testBestsellerStatistik() {
		// Sollte mit Daten = null initialisiert werden
		assertEquals(null, bs.getEndDate());
		assertEquals(null, bs.getStartDate());
	}

	@Test
	void testInitDates() {
		// Daten aendern
		bs.setEndDate(Date.from(Instant.now()));
		bs.setStartDate(Date.from(Instant.now()));
		
		// Daten initialisieren
		bs.initDates();
		
		// Daten sollten wieder null sein
		assertEquals(null, bs.getEndDate(), "EndDate nicht korrekt initialisiert");
		assertEquals(null, bs.getStartDate(), "StartDate nicht korrekt initialisiert");
	}

	@Test
	void testGetVerkaeufe() {
		// Nach initialisierung: Zeigt alle Verkaeufe an
		assertEquals(14, bs.getVerkaeufe(p1), "Inkorrekte Anzahl Verkaeufe (p1)");
		assertEquals(3, bs.getVerkaeufe(p2), "Inkorrekte Anzahl Verkaeufe (p2)");
		assertEquals(22, bs.getVerkaeufe(p3), "Inkorrekte Anzahl Verkaeufe (p3)");
		
		// Teste mit gesetzten Daten
		bs.setStartDate(Date.from(Instant.parse("2010-11-01T00:00:00Z")));
		bs.setEndDate(Date.from(Instant.parse("2018-11-01T00:00:00Z")));
		assertEquals(2, bs.getVerkaeufe(p1), "Inkorrekte Anzahl Verkaeufe (p1) (DatenGesetzt)");
		assertEquals(3, bs.getVerkaeufe(p2), "Inkorrekte Anzahl Verkaeufe (p2) (DatenGesetzt)");
		assertEquals(7, bs.getVerkaeufe(p3), "Inkorrekte Anzahl Verkaeufe (p3) (DatenGesetzt)");
		
	}

	@Test
	void testGetBestseller() {
		// Teste ohne gesetzte Daten
		assertEquals(3, bs.getBestseller().size(), "Bestseller enthält falsche Anzahl an Produkten");
		assertEquals(p3, bs.getBestseller().get(0), "p3 an falscher Position");
		assertEquals(p1, bs.getBestseller().get(1), "p1 an falscher Position");
		assertEquals(p2, bs.getBestseller().get(2), "p2 an falscher Position");
		
		// Teste mit gesetzten Daten
		bs.setStartDate(Date.from(Instant.parse("2010-11-01T00:00:00Z")));
		bs.setEndDate(Date.from(Instant.parse("2018-11-01T00:00:00Z")));
		assertEquals(p3, bs.getBestseller().get(0), "p3 an falscher Position (DatenGesetzt)");
		assertEquals(p2, bs.getBestseller().get(1), "p2 an falscher Position (DatenGesetzt)");
		assertEquals(p1, bs.getBestseller().get(2), "p1 an falscher Position (DatenGesetzt)");
	}

	@Test
	void testGetBestsellerInt() {
		// Teste ohne gesetzte Daten
		assertEquals(2, bs.getBestseller(2).size(), "Top-2 Bestseller enthält falsche Anzahl an Produkten");
		assertEquals(p3, bs.getBestseller().get(0), "p3 an falscher Position");
		assertEquals(p1, bs.getBestseller().get(1), "p1 an falscher Position");

		// Teste mit gesetzten Daten
		bs.setStartDate(Date.from(Instant.parse("2010-11-01T00:00:00Z")));
		bs.setEndDate(Date.from(Instant.parse("2018-11-01T00:00:00Z")));
		assertEquals(2, bs.getBestseller(2).size(), "Top-2 Bestseller enthält falsche Anzahl an Produkten (DatenGesetzt)");
		assertEquals(p3, bs.getBestseller().get(0), "p3 an falscher Position (DatenGesetzt)");
		assertEquals(p2, bs.getBestseller().get(1), "p2 an falscher Position (DatenGesetzt)");
	}

	@Test
	void testSetEndDate() {
		// Teste ob EndDatum korrekt gesetzt wird
		Date d = Date.from(Instant.now());
		bs.setEndDate(d);
		assertEquals(d, bs.getEndDate());
	}

	@Test
	void testSetStartDate() {
		// Teste ob StartDatum korrekt gesetzt wird
		Date d = Date.from(Instant.now());
		bs.setStartDate(d);
		assertEquals(d, bs.getStartDate());
	}

}
