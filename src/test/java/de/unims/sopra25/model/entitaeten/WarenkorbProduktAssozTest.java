package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Klasse zum Testen von WarenkorbProduktAssoz
 * @author Johannes Schick
 */
public class WarenkorbProduktAssozTest {

    /**
     * Testet Setter und Getter
     */
    @Test
    void test(){
        Warenkorb warenkorb = new Warenkorb(null);
        Produkt produkt = new Produkt("Testprodukt", 199, "Testbeschreibung", "testbild");

        WarenkorbProduktAssoz assoz= new WarenkorbProduktAssoz(warenkorb, produkt, 20);

        assertEquals(produkt, assoz.getProdukt(), "Falsches Produkt");
        assertEquals(warenkorb, assoz.getWarenkorb(), "Falscher Warenkorb");
        assertEquals(20, assoz.getAnzahl(), "Falsche Anzahl");

        assoz.setAnzahl(10);
        assertEquals(10, assoz.getAnzahl(), "Anzahl wurde nicht geändert");
    }
}
