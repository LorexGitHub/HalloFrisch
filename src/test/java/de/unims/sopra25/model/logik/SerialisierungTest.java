package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasse zum Testen der Klasse Serialisierung
 * @author Johannes Schick
 */
public class SerialisierungTest {

    private Serialisierung serialisierung;

    /**
     * Initialisiert eine Serialisierung
     */
    @BeforeEach
    void initialize(){
        serialisierung= Serialisierung.getSerialisierung();
        serialisierung.reset();
    }

    /**
     * Testet, ob das Resetten aller Listen funktioniert
     */
    @Test
    void testReset(){
        assertTrue(serialisierung.getEBestellungen().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getEinkaeufer().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getKunden().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getGeschaeftsfuehrer().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getLieferanten().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getFehler().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getOffeneKBestellungen().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getKategorien().isEmpty(), "Liste nicht zurückgesetzt.");
        assertTrue(serialisierung.getProdukte().isEmpty(), "Liste nicht zurückgesetzt.");
    }

    /**
     *
     */
    @Test
    @Disabled
    void readWrite() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Produkt neuesProdukt;
        //Erstellt neue Verwalter und prüft direkt, ob diese die leeren Listen der Serialisierung übernehmen.
        SortimentVerwalten sortimentVerwalten= new SortimentVerwalten();
        assertTrue(sortimentVerwalten.getProduktlist().isEmpty(), "SortimentVerwalten sollte leere Liste von Serialisierung übernehmen.");
        E_BestellungenVerwalten eBestellungenVerwalten= new E_BestellungenVerwalten();
        assertTrue(eBestellungenVerwalten.getBestellungen().isEmpty(), "E_BestellungVerwalten sollte leere Liste von Serialisierung übernehmen.");
        K_BestellungenVerwalten kBestellungenVerwalten= new K_BestellungenVerwalten();
        assertTrue(kBestellungenVerwalten.getOffeneBestellungen().isEmpty(), "K_BestellungVerwalten sollte leere Liste von Serialisierung übernehmen.");
        KategorienVerwalten kategorienVerwalten= new KategorienVerwalten();
        assertTrue(kategorienVerwalten.getKategorien().isEmpty(), "KategorienVerwalten sollte leere Liste von Serialisierung übernehmen.");
        NutzerVerwalten nutzerVerwalten= new NutzerVerwalten();

        //Fügt ein Produkt und zwei Kategorien hinzu
        neuesProdukt= new Produkt("Testname", 199, "Testbeschreibung", "testbild.png");
        kategorienVerwalten.addKategorie("Kategoriename");
        neuesProdukt.addKategorie(kategorienVerwalten.getKategorien().getFirst());
        kategorienVerwalten.addKategorie("Zweite Kategorie");
        sortimentVerwalten.neuesProdukt(neuesProdukt);
        //Fügt neue Nutzer hinzu
        nutzerVerwalten.addEinkaeufer("testnutzername", "testpasswort");
        nutzerVerwalten.addKunde("nutzernameKunde", "kundenPasswort", "Adresse", "Vorname", "Nachname");
        //Verändert den Warenkorb
        Kunde neuerKunde= (Kunde) nutzerVerwalten.getNutzer("nutzernameKunde");
        neuerKunde.getWarenkorb().addProdukt(neuesProdukt, 10);
        //Fügt neue Bestellungen hinzu
        eBestellungenVerwalten.addBestellung(neuesProdukt, 20);
        kBestellungenVerwalten.newBestellung(K_Bestellung.Zahlungsmittel.BAR, neuerKunde.getWarenkorb());
        //Verändert den Warenkorb (erneut)
        neuerKunde.getWarenkorb().addProdukt(neuesProdukt, 5);

        //Prüfe, ob Änderungen in Serialisierungslisten
        assertEquals(1, serialisierung.getProdukte().size(), "Es sollte ein Produkt existieren.");
        assertTrue(serialisierung.getProdukte().contains(neuesProdukt), "Falsches Produkt gespeichert.");
        assertEquals(2, serialisierung.getKategorien().size(), "Es sollten zwei Kategorien existieren.");
        assertEquals("Kategoriename", serialisierung.getKategorien().getFirst().getName(), "Erste Kategorie falsch.");
        assertEquals("Zweite Kategorie", serialisierung.getKategorien().getLast().getName(), "Zweite Kategorie falsch");
        assertEquals(1, serialisierung.getEinkaeufer().size(), "Es sollte ein Einkäufer existieren");
        assertEquals(1, serialisierung.getKunden().size(), "Es sollte ein Kunde exisitieren");
        assertTrue(serialisierung.getEinkaeufer().containsKey("testnutzername"), "Einkäufer falsch angelegt.");
        assertTrue(serialisierung.getKunden().containsKey("nutzernameKunde"), "Kunde falsch angelegt.");
        assertEquals(1, serialisierung.getOffeneKBestellungen().size(), "Es sollte eine offene Bestellung vorhanden sein.");
        assertEquals(10, serialisierung.getOffeneKBestellungen().getFirst().getProdukte().getFirst().getAnzahl(), "In der Bestellung sollten 10 Exemplare des Produkts liegen.");
        assertEquals(neuesProdukt, serialisierung.getOffeneKBestellungen().getFirst().getProdukte().getFirst().getProdukt(), "In der Bestellung sollte das Produkt sein.");
        assertEquals(neuesProdukt, serialisierung.getKunden().get("nutzernameKunde").getWarenkorb().getProdukte().getFirst().getProdukt(), "Im Warenkorb sollten 5 Exemplare des Produktes liegen.");
        assertEquals(5, serialisierung.getKunden().get("nutzernameKunde").getWarenkorb().getProdukte().getFirst().getAnzahl(), "Im Warenkorb sollten 5 Exemplare des Produktes liegen.");
        assertEquals(20, neuesProdukt.getAnzahlNachbestellteExemplare());
        //Schreibe Serialisierung, lade Serialisierung
        Serialisierung.getSerialisierung().write();
        Serialisierung.read();
        serialisierung= Serialisierung.getSerialisierung();

        //Überprüfe, ob Laden funktioniert hat
        assertEquals(1, serialisierung.getProdukte().size(), "Es sollte ein Produkt existieren.");
        neuesProdukt= serialisierung.getProdukte().getFirst();
        assertTrue(serialisierung.getProdukte().contains(neuesProdukt), "Falsches Produkt gespeichert.");
        assertEquals(2, serialisierung.getKategorien().size(), "Es sollten zwei Kategorien existieren.");
        assertEquals("Kategoriename", serialisierung.getKategorien().getFirst().getName(), "Erste Kategorie falsch.");
        assertEquals("Zweite Kategorie", serialisierung.getKategorien().getLast().getName(), "Zweite Kategorie falsch");
        assertEquals(1, serialisierung.getEinkaeufer().size(), "Es sollte ein Einkäufer existieren");
        assertEquals(1, serialisierung.getKunden().size(), "Es sollte ein Kunde exisitieren");
        assertTrue(serialisierung.getEinkaeufer().containsKey("testnutzername"), "Einkäufer falsch angelegt.");
        assertTrue(serialisierung.getKunden().containsKey("nutzernameKunde"), "Kunde falsch angelegt.");
        assertEquals(1, serialisierung.getOffeneKBestellungen().size(), "Es sollte eine offene Bestellung vorhanden sein.");
        assertEquals(10, serialisierung.getOffeneKBestellungen().getFirst().getProdukte().getFirst().getAnzahl(), "In der Bestellung sollten 10 Exemplare des Produkts liegen.");
        assertEquals(neuesProdukt, serialisierung.getOffeneKBestellungen().getFirst().getProdukte().getFirst().getProdukt(), "In der Bestellung sollte das Produkt sein.");
        assertEquals(neuesProdukt, serialisierung.getKunden().get("nutzernameKunde").getWarenkorb().getProdukte().getFirst().getProdukt(), "Im Warenkorb sollten 5 Exemplare des Produktes liegen.");
        assertEquals(5, serialisierung.getKunden().get("nutzernameKunde").getWarenkorb().getProdukte().getFirst().getAnzahl(), "Im Warenkorb sollten 5 Exemplare des Produktes liegen.");
        assertEquals(20, neuesProdukt.getAnzahlNachbestellteExemplare());
    }
}
