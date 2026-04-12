package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testet die Klasse Geschaeftsfuehrer
 * @author Johannes Schick
 */
public class GeschaeftsfuehrerTest {

    /**
     * Testet den Konstruktor.
     */
    @Test
    void test(){
        Geschaeftsfuehrer geschaeftsfuehrer= new Geschaeftsfuehrer("Testgeschäftsführer", "Testpasswort");
        assertEquals("Testgeschäftsführer", geschaeftsfuehrer.getNutzername());
        assertEquals("Testpasswort", geschaeftsfuehrer.getPasswort());
    }
}
