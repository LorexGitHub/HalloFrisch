package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.Fehler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Klasse zum Testen der Klasse
 * FehlerVerwalten
 * @author Johannes Schick
 */

public class FehlerVerwaltenTest {
    private static FehlerVerwalten verwalter;

    /**
     * Initialisiert einen Verwalter
     */
    @BeforeAll
    static void initialize(){
    	Serialisierung.getSerialisierung().reset();
        verwalter= new FehlerVerwalten();
    }

    /**
     * Testet das Hinzufügen und Entfernen von Fehlern.
     */
    @Test
    void testGetSetFehler(){
        Fehler neuerFehler;
        ObservableList<Fehler> localList= FXCollections.observableList(verwalter.getFehler());
        ObservableList<Fehler> pre= FXCollections.observableList(verwalter.getFehler());

        //Test addFehler
        verwalter.addFehler("Programm abgestürzt");
        neuerFehler= verwalter.getFehler().getLast();
        localList.add(neuerFehler);
        assertEquals(localList, verwalter.getFehler());
        assertEquals("Programm abgestürzt", neuerFehler.getError());

        //Test removeFehler
        localList.remove(neuerFehler);
        verwalter.removeFehler(neuerFehler);
        assertEquals(pre, verwalter.getFehler());
    }
    
    /**
     * testet getFehlerListeString
     */
    @Test
    void testGetFehlerListeString () {
    	ObservableList<String> localList= FXCollections.observableArrayList();
    	verwalter.addFehler("Programm abgestürzt");
    	localList.add("Programm abgestürzt");
    	verwalter.addFehler("Login fehlgeschlagen");
    	localList.add("Login fehlgeschlagen");
    	
    	assertEquals(localList, verwalter.getFehlerListeString());
    	
    }
}
