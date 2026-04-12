package de.unims.sopra25.model.logik;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.entitaeten.K_Bestellung.Zahlungsmittel;

/**
 * Klasse die die UmsatzStatistik testet
 * @author Leonhard Heß
 */
class UmsatzStatistikTest {

	UmsatzStatistik us;
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
				  Instant.parse("2001-12-31T14:00:00Z"),
				  ZoneOffset.UTC);
		Clock c2 = Clock.fixed( 
				  Instant.parse("2001-01-01T12:00:00Z"),
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
		us = new UmsatzStatistik();
	}

	@Test
	void testGetUmsatzVon() {
		// #### Teste diverse Zeitraeume ####
		// Zeitraum ohne Einkaeufe
		ArrayList<Long> umsatzArr;
		umsatzArr = us.getUmsatzVon(0);
		assertEquals(12, umsatzArr.size(), "Jahr hat nicht 12 Monate");
		for(long umsatzMonat : umsatzArr) {
			assertEquals(0, umsatzMonat, "Jahr ohne Umsatz hat Umsatz");
		}
		
		// Zeitraeume mit Einkaeufen
		umsatzArr = us.getUmsatzVon(2001);
		assertEquals(12, umsatzArr.get(0), "Umsatz Index 0 falsch!");
		assertEquals(0, umsatzArr.get(1), "Umsatz Index 1 falsch!");
		assertEquals(0, umsatzArr.get(2), "Umsatz Index 2 falsch!");
		assertEquals(0, umsatzArr.get(3), "Umsatz Index 3 falsch!");
		assertEquals(0, umsatzArr.get(4), "Umsatz Index 4 falsch!");
		assertEquals(0, umsatzArr.get(5), "Umsatz Index 5 falsch!");
		assertEquals(0, umsatzArr.get(6), "Umsatz Index 6 falsch!");
		assertEquals(0, umsatzArr.get(7), "Umsatz Index 7 falsch!");
		assertEquals(45, umsatzArr.get(8), "Umsatz Index 8 falsch!");
		assertEquals(0, umsatzArr.get(9), "Umsatz Index 9 falsch!");
		assertEquals(0, umsatzArr.get(10), "Umsatz Index 10 falsch!");
		assertEquals(29, umsatzArr.get(11), "Umsatz Index 11 falsch!");
	}

	@Test
	void testGetGesamtumsatz() {
		// Teste ob Umsatz korrekt ist
		assertEquals(86, us.getGesamtumsatz(), "Gesamtumsatz falsch berechnet");
		
		// Preise bei Produkten aendern generiert
		// keinen extra Profit
		p1.setPreis(50000000);
		assertEquals(86, us.getGesamtumsatz(), "Gesamtumsatz ändert sich bei Preisänderung");
	}

}
