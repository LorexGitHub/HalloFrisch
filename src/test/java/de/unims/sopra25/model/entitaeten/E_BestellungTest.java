package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Klasse zum Testen von E_Bestellung
 * @author Johannes Schick
 */
public class E_BestellungTest {
    private E_Bestellung bestellung;
    private Produkt produkt;

    /**
     * Konstruiert ein Produkt und eine Bestellung.
     */
    @BeforeEach
    void initialize(){
        produkt= new Produkt("Testprodukt", 199, "Testbeschreibung", "./Testpfad");
        bestellung = new E_Bestellung(produkt , 20, 1);

    }

    /**
     * Testet, ob der Konstruktor und die
     * Getter-Methoden korrekt funktionieren
     */
    @Test
    void testKonstruktor(){
        assertEquals(20, bestellung.getAnzahl());
        assertEquals(produkt, bestellung.getProdukt());
        assertEquals(1, bestellung.getId());

        //Prüfe, ob Datum auf heute gesetzt
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        assertEquals(format.format(Date.from(Instant.now())),
            format.format(bestellung.getDatum()));

        //Prüfe, ob Nachbestellung Produkt hinzugefügt
        assertTrue(produkt.getNachbestellungen().contains(bestellung));
    }

    /**
     * Testet, ob setAnzahl korrekt funktioniert.
     */
    @Test
    void testSetAnzahl(){
        bestellung.setAnzahl(40);
        assertEquals(40, bestellung.getAnzahl());
    }
}
