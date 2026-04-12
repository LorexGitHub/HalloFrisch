package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.Fehler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasse zum Verwalten der Fehler
 * 
 * @author Johannes Schick
 */
public class FehlerVerwalten {

    // Liste mit allen vorhandenen Fehlern
    private ObservableList<Fehler> fehler;

    /**
     * Konstruktor für FehlerVerwalten. Lädt alle bisher gespeicherten
     * Fehler.
     */
    public FehlerVerwalten() {
        Serialisierung serialisierung = Serialisierung.getSerialisierung();
        this.fehler = FXCollections.observableList(serialisierung.getFehler());
    }

    /**
     * Gibt alle vorhandenen Fehler aus
     * 
     * @return Liste von Fehlern
     */
    public ObservableList<Fehler> getFehler() {
        return fehler;
    }

    /**
     * Fügt einen neuen Fehler hinzu. Aus dem String wird
     * ein neues Fehler-Objekt erzeugt, welche (später)
     * serialisiert werden.
     * 
     * @param fehlerBeschreibung Fehlerausgabe
     */
    public void addFehler(String fehlerBeschreibung) {
        Fehler neuerFehler = new Fehler(fehlerBeschreibung);
        this.fehler.add(neuerFehler);
    }

    /**
     * Entfernt den Fehler.
     * 
     * @param fehler Zu entfernender Fehler
     */
    public void removeFehler(Fehler fehler) {
        this.fehler.remove(fehler);
    }
    
    /**
     * gibt die Fehler als Strings in einer Liste zurück
     * wird fuer die Fehlerlogs des Geschaeftsfueherers genutzt
     * @return Liste der Fehler
     */
    public ObservableList<String> getFehlerListeString (){
    	ObservableList<String> fehlerStrings = FXCollections.observableArrayList();
    	fehler.forEach(fehler -> {
    		fehlerStrings.add(fehler.getError());
    	});
    	return fehlerStrings;
    }

}
