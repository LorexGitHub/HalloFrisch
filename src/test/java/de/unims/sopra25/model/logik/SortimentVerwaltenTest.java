package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.FXPermission;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Florian Gerdes
 * Testklasse für die Logikklasse SortimentVerwalten
 */
public class SortimentVerwaltenTest {
    private ObservableList<Produkt> produktlist = FXCollections.observableArrayList();
    private SortimentVerwalten verwalten;
    private ArrayList<Kategorie> katset = new ArrayList<>();
    Produkt birne = new Produkt("Birne", 300, "birnen sind lecker", "./testbirne.png", katset) ;
    Produkt apfel = new Produkt("Apfel", 200, "Das ist eine beschreibung", "./test.png", katset);
    Produkt orange = new Produkt("Orange", 200, "Das ist eine beschreibung", "./test.png", katset);

    /**
     * Initialisierungsmehtode für alle Tests
     */
    @BeforeEach
    void init(){
        Serialisierung.getSerialisierung().reset();
        katset.add(new Kategorie("Obst"));
        this.produktlist.add(birne);
        this.produktlist.add(apfel);
        verwalten = new SortimentVerwalten();
        verwalten.neuesProdukt(birne);
        verwalten.neuesProdukt(apfel);
    }

    /**
     * Test für getter-Methode für Atrribut Produktlist
     * der Logikklasse SortimentVerwalten.
     */
    @Test
    @Disabled
    public void testGetProduktlist() {
        assertIterableEquals(this.produktlist, verwalten.getProduktlist(), "Produktliste wird nicht richtig gegriffen");
    }

    /**
     * Testmethode für neues Produkt einfügen,
     * es werden für Apfel und Birne getestet, dass diese schon vorhanden sind,
     * und eine Orange neu hinzugefügt.
     */
    @Test
    @Disabled
    public void testNeuesProdukt(){
        ObservableList<Produkt> testlist = verwalten.getProduktlist();
        verwalten.neuesProdukt(orange);
        this.produktlist.add(orange);
        assertThrows(IllegalArgumentException.class, () -> verwalten.neuesProdukt(birne));
        assertThrows(IllegalArgumentException.class, () -> verwalten.neuesProdukt(apfel));
        assertIterableEquals(produktlist, testlist, "Orange Einfügen hat nicht funktioniert");
    }

    /**
     * Testmethode für deleteProdukt-Methode.
     * Das Produkt Birne wird aus der Produkliste gelöscht und
     * danach geprüft, ob dieses noch in der Liste zu finden ist.
     */
    @Test
    public void testDeleteProdukt(){
        verwalten.deleteProdukt(birne);
        assertFalse(verwalten.getProduktlist().contains(birne), "Birne wurde nicht aus der Produktliste gelöscht");
    }


}
