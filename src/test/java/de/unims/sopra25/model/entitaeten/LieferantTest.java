package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testet die Klasse Lieferant
 * @author Johannes Schick
 */
public class LieferantTest {
    private Lieferant lieferant;
    private Kunde ku;
    private K_Bestellung k;
@BeforeEach
void init() {
    ku = new Kunde("", "","", "","");
    lieferant = new Lieferant("Testlieferant", "Testpasswort");
    k = new K_Bestellung(1, K_Bestellung.Zahlungsmittel.BAR, ku.getWarenkorb());
}
    /**
     * Testet den Konstruktor
     */
    @Test
    void testKonstruktor(){
        assertEquals("Testlieferant", lieferant.getNutzername());
        assertEquals("Testpasswort", lieferant.getPasswort());
    }

    /**
     * Testet, ob dem Lieferanten eine Bestellung zugewiesen werden kann
     */
    @Test
    void testAddBestellung(){
        lieferant.addBestellung(k);
        assertTrue(lieferant.getBestellungen().contains(k), "Bestellung wurde nicht dem Lieferanten zugewiesen");
    }

    /**
     * Testet, ob dem Lieferanten eine Referenz auf eine Bestellung genommen werden kann.
     */
    @Test
    void testRemoveBestellung(){
        lieferant.addBestellung(k);
        assertTrue(lieferant.getBestellungen().contains(k), "Bestellung in remove-Test wurde nicht hinzugefügt");
        lieferant.removeBestellung(k);
        assertFalse(lieferant.getBestellungen().contains(k), "Bestellung wurde nicht entfernt");
    }
}
