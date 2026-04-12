package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasse zum Testen der Klasse
 * KategorienVerwalten
 * @author Johannes Schick
 */
public class KategorienVerwaltenTest {

    static KategorienVerwalten verwalter;

    /**
     * Initialisiert einen Verwalter
     */
    @BeforeAll
    static void initialise(){
        verwalter = new KategorienVerwalten();

    }

    /**
     * Testet die Methoden
     * getKategorien()
     * addKategorien()
     * removeKategorien()
     */
    @Test
    void testGetSetKategorien(){
        ObservableList<Kategorie> listCopy = FXCollections.observableArrayList(verwalter.getKategorien());

        //Neue Kategorie hinzufügen
        verwalter.addKategorie("Neue Kategorie");
        listCopy.add(verwalter.getKategorien().getLast());
        assertIterableEquals(listCopy, verwalter.getKategorien());

        //Kategorie entfernen
        listCopy.remove(verwalter.getKategorien().getLast());
        verwalter.removeKategorie(verwalter.getKategorien().getLast());
        assertIterableEquals(listCopy, verwalter.getKategorien());

        //Testet removeKategorie(String: ...)
        verwalter.addKategorie("Noch eine Kategorie");
        listCopy.add(verwalter.getKategorien().getLast());
        assertEquals(listCopy, verwalter.getKategorien());
        listCopy.remove(verwalter.getKategorien().getLast());
        verwalter.removeKategorie("Noch eine Kategorie");
        assertEquals(listCopy, verwalter.getKategorien());
    }

    /**
     * Testet, ob die remove-Methode die Produkte
     * korrekt aus der Kategorie entfernt, bevor diese gelöscht wird.
     */
    @Test
    void testKategorie(){
        //Prüft, ob Name korrekt gesetzt
        Kategorie kategorie;
        String name= "Neue Kategorie";
        verwalter.addKategorie(name);
        kategorie= verwalter.getKategorien().getLast();
        assertEquals(name, verwalter.getKategorien().getLast().getName());

        //
        //Prüft, ob Kategorien korrekt entfernt werden
        //
        Produkt testprodukt= new Produkt("Testprodukt", 199,
        "Testbeschreibung", "Testpfad", kategorie);

        //Assoziationen zwischen Produkt und Kategorie?
        assertTrue(kategorie.getProdukte().contains(testprodukt));
        assertTrue(testprodukt.getKategorien().contains(kategorie));

        verwalter.removeKategorie(kategorie);
        //Kategorie-Assoziation aus Produkt entfernt?
        assertFalse(testprodukt.getKategorien().contains(kategorie));

    }

}
