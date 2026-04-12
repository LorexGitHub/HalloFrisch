package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * @author Florian Gerdes
 * Testklasse für die Logikklasse ListeFiltern
 */
public class ListeFilternTest {
    private final ObservableList<Produkt> productlist = FXCollections.observableArrayList();
    private final ArrayList <Kategorie>katset = new ArrayList<>();
    private final ObservableList<K_Bestellung> bestList = FXCollections.observableArrayList();
    ArrayList <Kategorie>wrongset = new ArrayList<>();
    Produkt birne = new Produkt("Birne", 300, "birnen sind lecker", "./testbirne.png", wrongset) ;
    Produkt apfel = new Produkt("Apfel", 200, "Das ist eine beschreibung", "./test.png", wrongset);
    Kunde max = new Kunde("Max123", "1234", "Musterstraße", "Max", "Mustermann");
    K_Bestellung kbest = new K_Bestellung(1, K_Bestellung.Zahlungsmittel.BAR , new Warenkorb(max));
    K_Bestellung kbest2 = new K_Bestellung(2, K_Bestellung.Zahlungsmittel.BAR , new Warenkorb(max));
    /**
     * initializer für alle Tests
     */
    @BeforeEach
    void init () {
        wrongset.add(new Kategorie("Gemüse"));
        if (!this.katset.isEmpty()){
            this.katset.remove(new Kategorie("Obst"));
        }
        this.katset.add(new Kategorie("Obst"));
        this.productlist.add(birne);
        this.productlist.add(apfel);
        bestList.add(kbest);
        bestList.add(kbest2);
        kbest2.setVorbereitet();
    }

    /**
     * Testmethode für ListeFilten, in der nach einem Produkt-Objekt
     * mit dem Namen Birne gefiltert wird.
     */
    @Test
  public void testProduktListeBirneFiltern() {
        wrongset.add(new Kategorie("Gemüse"));
        String text = "Birne";
        List<Produkt> prodList = ListeFiltern.produktListeFiltern(this.productlist, text, this.katset);
        List<Produkt> expected = new ArrayList<>();
        expected.add(birne);
        assertIterableEquals(prodList, expected);
    }

    /**
     * Testmethode für ProduktListeFiltern, in der nach einem Apfel gefiltert wird.
     */
    @Test
    public void testProduktListeApfelFiltern() {
        String text = "Apfel";
        List<Produkt> expected = new ArrayList<>();
        expected.add(apfel);
        List<Produkt> calc = ListeFiltern.produktListeFiltern(this.productlist, text, this.katset);
        assertIterableEquals(calc, expected);
    }

    /**
     * Testmethode für bestellListeFiltern-Methode. Es wird nach
     * einer "1" gefiltert mit offenem Status.
     */
    @Test
    public void testBestellListeFiltern() {
        K_Bestellung.Status status = K_Bestellung.Status.OFFEN;
        String input = "1";
        List<K_Bestellung> bestliste = ListeFiltern.bestListeFiltern(bestList,input, status);
        List<K_Bestellung> expected = new ArrayList<>();
        expected.add(kbest);
        //expected.add(kbest2); (ausgeblendet, damit Test läuft, kbest2 wird gefiltert.
        assertIterableEquals(expected, bestliste);
    }

    /**
     * Testmethode für EBestellListeFiltern,
     * es wird nach einer ID mit einer 2 darin gefiltert.
     */
    @Test
    public void eBestListeFilternTest() {
        Produkt birne = new Produkt("Birne", 300, "birnen sind lecker", "./testbirne.png", wrongset) ;
        Produkt apfel = new Produkt("Apfel", 200, "Das ist eine beschreibung", "./test.png", wrongset);
        E_Bestellung e = new E_Bestellung(birne, 20, 3);
        E_Bestellung e2 = new E_Bestellung(apfel, 10, 2);
        ObservableList<E_Bestellung> elist = FXCollections.observableArrayList();
        elist.add(e);
        elist.add(e2);
        List<E_Bestellung> filtered = ListeFiltern.EBestListeFiltern(elist, "Apfel");
        ObservableList<E_Bestellung> expected = FXCollections.observableArrayList();
        expected.add(e2);
        assertEquals(filtered.getFirst().getProdukt().getName(), expected.getFirst().getProdukt().getName(), "Liste wurde nicht richtig gefiltert");
    }
}
