package de.unims.sopra25.model.logik;

import java.util.HashMap;

import de.unims.sopra25.model.entitaeten.Einkaeufer;
import de.unims.sopra25.model.entitaeten.Geschaeftsfuehrer;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Lieferant;

/**
 * Klasse zum Berechnen und Ausgeben von
 * Statistiken bzgl. Nutzer und Nutzung des
 * Systems.
 * 
 * @author Johannes Schick
 * @author Leonhard Heß
 */
public class NutzungsStatistik {
	
	// Maps die verschiedene Typen von Nutzern speichern
	HashMap<String, Kunde> kunden;
	HashMap<String, Geschaeftsfuehrer> geschaeftsfuehrer;
	HashMap<String, Einkaeufer> einkaeufer;
	HashMap<String, Lieferant> lieferanten;
	
    /**
     * Konstruktor fuer die Nutzungsstatistik
     */
    public NutzungsStatistik(){
    	// Maps aus Serialisierung laden
    	Serialisierung serialisierung = Serialisierung.getSerialisierung();
        kunden = serialisierung.getKunden();
        geschaeftsfuehrer = serialisierung.getGeschaeftsfuehrer();
        einkaeufer = serialisierung.getEinkaeufer();
        lieferanten = serialisierung.getLieferanten();
    }


    /**
     * Gibt die Anzahl aller Kunden aus
     * @return Anzahl Kunden
     */
    public int getAnzahlKunden(){
        return kunden.size();
    }

    /**
     * Gibt die Anzahl aller Auslieferer aus
     * @return Anzahl Auslieferer
     */
    public int getAnzahlAuslieferer(){
        return lieferanten.size();
    }

    /**
     * Gibt die Anzahl aller Geschaeftsfuehrer aus
     * @return Anzahl Geschaeftsfuehrer
     */
    public int getAnzahlGeschaeftsfuehrer(){
        return geschaeftsfuehrer.size();
    }

    /**
     * Gibt die Anzahl aller Einkaeufer aus
     * @return Anzahl Einkaeufer
     */
    public int getAnzahlEinkaeufer(){
        return einkaeufer.size();
    }

    /**
     * Gibt die Anzahl aller Nutzer aus.
     * @return Anzahl Nutzer
     */
    public int getAnzahlNutzer(){
        return getAnzahlAuslieferer()+
        		getAnzahlEinkaeufer()+
        		getAnzahlGeschaeftsfuehrer()+
        		getAnzahlKunden();
    }
}
