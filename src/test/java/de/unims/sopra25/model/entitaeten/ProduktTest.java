package de.unims.sopra25.model.entitaeten;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Johannes Schick
 */
public class ProduktTest {
    static Produkt produkt1;

    static String beschreibung1;
    static int preis1;
    static String name1;
    static ArrayList<Kategorie> set;
    static String path1;
    static ArrayList<Kategorie> katset2;

    /**
     * Initialisiert ein Produkt
     */
    @BeforeEach
    void initialisiere(){
        set = new ArrayList<>();
        katset2 = new ArrayList<>();
        set.add(new Kategorie("Testkategorie"));
        beschreibung1 = "Testbeschreibung";
        preis1 = 199;
        name1 = "Testname";
        path1 = "./testpfad.img";

        produkt1= new Produkt(name1, preis1,
            beschreibung1, path1, katset2);

    }

    /**
     * Testet die Getter-Methoden nach Initialisierung
     * des Produkts
     */
    @Test

    void testGetInitialisierung(){
        assertEquals(name1, produkt1.getName());
        assertEquals(preis1, produkt1.getPreis());
        assertEquals(beschreibung1, produkt1.getBeschreibung());
        assertEquals(0, produkt1.getBestand());
        assertEquals(path1, produkt1.getBild());
        assertEquals(katset2, produkt1.getKategorien());
        assertEquals(new ArrayList<E_Bestellung>(), produkt1.getNachbestellungen());
    }

    /**
     * Testet setPreis
     */
    @ParameterizedTest
    @ValueSource(ints= {0, Integer.MAX_VALUE})
    void testSetPreis(int preis){
        produkt1.setPreis(preis);
        assertEquals(preis, produkt1.getPreis());
    }

    /**
     * Testet getPreisString
     */
    @Test
    void testGetPreisString(){
        assertEquals("1,99 \u20ac", produkt1.getPreisString());
    }

    /**
     * Testet setName()
     */
    @Test
    void testSetName(){
        produkt1.setName("Äpfel");
        assertEquals("Äpfel", produkt1.getName());
    }

    /**
     * Testet setBeschreibung()
     */
    @Test
    void testSetBeschreibung(){
        produkt1.setBeschreibung("Neue Beschreibung");
        assertEquals("Neue Beschreibung", produkt1.getBeschreibung());
    }

    /**
     * Testet setBestand()
     */
    @Test
    void testSetBestand(){
        produkt1.setBestand(20);
        assertEquals(20, produkt1.getBestand());
    }

    /**
     * Testet addBestand()
     */
    @Test
    void testAddBestand(){
        int bestandPre= produkt1.getBestand();
        produkt1.addBestand(20);
        assertEquals(bestandPre + 20, produkt1.getBestand());
    }

    /**
     * Testet setBild()
     */
    @Test
    void testSetBild(){
        produkt1.setBild("./neuerPfad.img");
        assertEquals("./neuerPfad.img", produkt1.getBild());
    }

    /**
     * Testet addKategorie und removeKategorie.
     * Insbesondere auch die Assoziationen zwischen Kategorie
     * und Produkt.
      */
    @Test

    void testAddRemoveKategorie(){

        Kategorie neueKategorie = set.get(0);
        //addKategorie
        produkt1.addKategorie(neueKategorie);
        assertIterableEquals(set, produkt1.getKategorien());
        //Assoziationen korrekt?
        assertTrue(produkt1.getKategorien().contains(neueKategorie));
        // Assoziationen korrekt?
        assertTrue(neueKategorie.getProdukte().contains(produkt1));
        // removeKategorie
        produkt1.removeKategorie(neueKategorie);
        set.remove(neueKategorie);
        assertIterableEquals(set, produkt1.getKategorien());
        //Assoziationen entfernt?
        assertFalse(produkt1.getKategorien().contains(neueKategorie));
        assertFalse(neueKategorie.getProdukte().contains(produkt1));

    }

    /**
     * Testet addNachbestellungen und removeNachbestellungen
     */
    @Test
    @Disabled
    void testAddNachbestellungen(){
        int bestandPre= produkt1.getBestand();

        //Überprüfe add
        E_Bestellung nachbestellung= new E_Bestellung(produkt1, 10, 1); // Fuegt sich selbst den Nachbestellungen hinzu
        assertEquals(10, produkt1.getAnzahlNachbestellteExemplare());
        assertEquals(1,  produkt1.getNachbestellungen().size());
        assertEquals(nachbestellung, produkt1.getNachbestellungen().getFirst());

        //Überprüfe remove
        set.remove(nachbestellung);
        produkt1.removeNachbestellung(nachbestellung);
        assertIterableEquals(set, produkt1.getNachbestellungen());

        //Überprüfe, ob Bestand aktualisiert
        assertEquals(bestandPre + 10, produkt1.getBestand());

    }

}
