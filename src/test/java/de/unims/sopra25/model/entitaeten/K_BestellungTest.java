package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testet die Klasse K_Bestellung
 * @author Johannes Schick
 * @author Leonhard Heß
 */
public class K_BestellungTest {

    private K_Bestellung bestellung;
    private Kunde kunde;
    private Calendar wunschliefertag;
    private Calendar neuerWunschliefertag;
    private Date erstelldatum;
    private Produkt p1;

    /**
     * Erstellt eine K_Bestellung
     */
    @BeforeEach
    void setUp(){
        wunschliefertag= new GregorianCalendar(2025, 2, 28);
        neuerWunschliefertag = new GregorianCalendar (2025, 4, 25);
        p1 = new Produkt("Testprodukt", 199, "Beschreibung", "./Testbild.png");
        kunde = new Kunde("nutzername", "passwort", "Testadrese", "Vorname", "Nachname");
        Warenkorb warenkorb= kunde.getWarenkorb();
        warenkorb.addProdukt(p1, 12);
        bestellung= new K_Bestellung(1, wunschliefertag, K_Bestellung.Zahlungsmittel.BAR, warenkorb);
    }

    /**
     * Testet den Konstruktor
     */
    @Test
    void testKonstruktor(){
        assertEquals(kunde, bestellung.getKunde(), "Bestellung mit falschen Kunden verbunden");
        assertEquals(1, bestellung.getId(), "Falsch Id gespeichert.");
        assertEquals(wunschliefertag, bestellung.getWunschliefertag(), "Falsches Datum initialisiert.");
    }

    /**
     * Testet, ob die Veränderung des Status funktioniert.
     */
    @Test
    void testStatus(){
        assertEquals(K_Bestellung.Status.OFFEN, bestellung.getStatus(), "Status falsch initialisiert.");

        bestellung.setAusgeliefert();
        assertEquals(K_Bestellung.Status.OFFEN, bestellung.getStatus(), "Status darf nicht auf ausgeliefert geändert werden.");

        bestellung.setVorbereitet();
        assertEquals(K_Bestellung.Status.VORBEREITET, bestellung.getStatus(), "Status nicht auf Vorbereitet gestellt.");

        bestellung.setAusgeliefert();
        assertEquals(K_Bestellung.Status.AUSGELIEFERT, bestellung.getStatus(), "Status muss ausgeliefert sein.");

        bestellung.setVorbereitet();
        assertEquals(K_Bestellung.Status.AUSGELIEFERT, bestellung.getStatus(), "Status darf nicht auf vorbereitet geändert werden.");
    }
    
    /**
     * Testet die Methode toString im Enum Status
     */
    @Test
    void testStatusToString () {
    	assertEquals(K_Bestellung.Status.OFFEN.toString(), "Offen");
    	assertEquals(K_Bestellung.Status.VORBEREITET.toString(), "Vorbereitet");
    	assertEquals(K_Bestellung.Status.AUSGELIEFERT.toString(), "Ausgeliefert");
    }

    /**
     * Testet die getter und setter von Wunschliefertag.
     */
    @Test
    void testWunschliefertag(){
        assertEquals(wunschliefertag, bestellung.getWunschliefertag(), "Falscher Wunschliefertag initialisiert.");
        bestellung.setWunschliefertag(neuerWunschliefertag);
        assertEquals(neuerWunschliefertag, bestellung.getWunschliefertag(), "Falscher Wunschliefertag gesetzt.");
    }

    /**
     * Testet die getter und setter von Zahlungsmittel.
     */
    @Test
    void testZahlungsmittel(){
        assertEquals(K_Bestellung.Zahlungsmittel.BAR, bestellung.getZahlungsmittel(), "Falsches Zahlungsmittel initialisiert.");
        bestellung.setZahlungsmittel(K_Bestellung.Zahlungsmittel.KARTE);
        assertEquals(K_Bestellung.Zahlungsmittel.KARTE, bestellung.getZahlungsmittel(), "Falsches Zahlungsmittel gesetzt.");
    }
    
    /**
     * Testet die Methode toString im Enum ZAhlungsmittel
     */
    @Test
    void testZahlungmittelToString () {
    	assertEquals(K_Bestellung.Zahlungsmittel.BAR.toString(), "Bar");
    	assertEquals(K_Bestellung.Zahlungsmittel.KARTE.toString(), "Karte");
    }

    /**
     * Testet, ob aus der WarenKorbProduktAssoziation Produkte in Bestellung übergeben werden
     */
    @Test
    void testProduktAssoz(){
    	// Teste Anzahl Bestellte Produkte
        assertEquals(1, bestellung.getProdukte().size());

        // Teste ob Assoziation korrekt konstruiert wurde
        assertEquals(p1, bestellung.getProdukte().getFirst().getProdukt(),
        		"Produktassoziation verweist auf das falsche Produkt");
        assertEquals(p1.getPreis(), bestellung.getProdukte().getFirst().getPreis(),
        		"Produktassoziation gibt inkorrekten Preis");
        assertEquals(p1.getName(), bestellung.getProdukte().getFirst().getName(),
        		"Produktassoziation gibt inkorrekten Namen");
        assertEquals(p1.getBeschreibung(), bestellung.getProdukte().getFirst().getBeschreibung(),
        		"Produktassoziation gibt inkorrekten Namen");
        assertEquals(12, bestellung.getProdukte().getFirst().getAnzahl(),
        		"Produktassoziation gibt inkorrekte Anzahl");
        assertEquals(bestellung, bestellung.getProdukte().getFirst().getBestellung(),
        		"Produktassoziation gibt inkorrekte Bestellung");

        // Teste ob Name und Preis gespeichert werden
        int alterP1Preis = p1.getPreis();
        String alterP1Name = p1.getName();
        p1.setName("Festprodukt");
        p1.setPreis(9999999);
        assertEquals(alterP1Preis, bestellung.getProdukte().getFirst().getPreis(),
        		"Produktassoziation gibt inkorrekten Preis");
        assertEquals(alterP1Name, bestellung.getProdukte().getFirst().getName(),
        		"Produktassoziation gibt inkorrekten Namen");
    }
    @Test
    void testAendereLieferant() {
        Lieferant l1 = new Lieferant("","");
        Lieferant l2 = new Lieferant("","");
        bestellung.setLieferant(l1);
        bestellung.aendereLieferant(l2);
        assertEquals(l2, bestellung.getLieferant(), "Lieferant wurde nicht geändert");
    }

    /**
     * Testet, ob der Lieferant einer Bestellung gegetted werden kann
     */
    @Test
    void testGetLieferant() {
        Lieferant l1 = new Lieferant("","");
        bestellung.setLieferant(l1);
        Lieferant l2 = bestellung.getLieferant();
        assertEquals(l2, l1, "Getter funktioniert nicht");
    }
    
    /**
     * testet getStatusList
     */
    @Test
    void testGetStatusList () {
    	assertEquals(K_Bestellung.getStatusList().size(), 3) ;
    	assertTrue (K_Bestellung.getStatusList().contains(K_Bestellung.Status.OFFEN));
    	assertTrue (K_Bestellung.getStatusList().contains(K_Bestellung.Status.VORBEREITET));
    	assertTrue (K_Bestellung.getStatusList().contains(K_Bestellung.Status.AUSGELIEFERT));
    }
    
    /**
     * testet getWunschliefertagString
     */
    @Test
    void testGetWunschliefertagString () {
    	bestellung.setWunschliefertag(neuerWunschliefertag);
    	//Erwartetes Datum im Mai, weil Januar=0
    	assertEquals(bestellung.getWunschliefertagString(), "25/05/2025");
    }

}
