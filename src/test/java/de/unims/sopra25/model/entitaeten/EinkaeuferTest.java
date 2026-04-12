package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testet die Entität Einkaeufer
 * @author Johannes Schick
 */
public class EinkaeuferTest {

    /**
     * Testet den Konstruktor
     */
    @Test
    void testKonstruktor(){
        Einkaeufer einkaeufer= new Einkaeufer("Testeinkäufer", "Testpasswort");
        assertEquals("Testeinkäufer", einkaeufer.getNutzername());
        assertEquals("Testpasswort", einkaeufer.getPasswort());
    }



}
