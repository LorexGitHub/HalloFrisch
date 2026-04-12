package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testet die Klasse Fehler
 * @author Johannes Schick
 */
public class FehlerTest {

    /**
     * Testet den Konstruktor und die getter.
     */
    @Test
    void test(){
        Fehler fehler= new Fehler("Testproblem");
        assertEquals("Testproblem", fehler.getError());
    }
}
