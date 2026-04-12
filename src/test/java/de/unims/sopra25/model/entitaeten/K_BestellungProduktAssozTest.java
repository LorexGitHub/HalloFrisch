package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Klasse zum Testen von K_BestellungProduktAssoz
 * @author Johannes Schick
 */
public class K_BestellungProduktAssozTest {

    /**
     * Testet die getter-Methoden
     */
    @Test
    void testAll(){
        Kunde kunde= new Kunde("nutzername", "passwort", "Adresse", "Vorname", "Nachname");
        Warenkorb warenkorb = kunde.getWarenkorb();
        Produkt produkt= new Produkt("Testprodukt", 199, "Testbeschreibung", "testbild.png");
        warenkorb.addProdukt(produkt, 12);
        WarenkorbProduktAssoz warenkorbProduktAssoz= warenkorb.getProdukte().iterator().next();

        K_BestellungProduktAssoz assoz = new K_BestellungProduktAssoz(warenkorbProduktAssoz, null);

        assertEquals(produkt, assoz.getProdukt(), "Produktassoziation falsch initialisiert.");
        assertEquals(produkt.getName(), assoz.getName(), "Falscher Name gespeichert.");
        assertEquals(produkt.getPreis(), assoz.getPreis(), "Falscher Preis gespeichert");
        assertEquals(warenkorbProduktAssoz.getAnzahl(), assoz.getAnzahl());
    }
}
