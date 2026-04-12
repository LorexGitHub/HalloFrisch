package de.unims.sopra25.view.einkaeufer;

import de.unims.sopra25.controller.einkaeufer.ProduktBoxController;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Klasse fuer eine ProduktBox in der ProduktUebersicht des Einkaeufers
 *
 * @author Irina Hoppe, Johannes Schick
 */
public class ProduktBoxView extends ProduktBoxViewVorlage {

    //Verwaltender Controller
    private ProduktBoxController boxController;
    
    /**
     * Konstruktor.
     * Erstellt eine ProduktBox fuer den Einkaeufer
     *
     * @param produkt, boxController
     */
    public ProduktBoxView(Produkt produkt, ProduktBoxController boxController) {
        super(produkt);
        this.boxController= boxController;
        baueProduktZeile(getBildButton(), getName(), getBestandAnzahl(), getBestandLabel(), getNachbestelltAnzahl(),
                getNachbestelltLabel(), getPreis(), getAnzahlField(), getHinzufuegeButton());
    }

    /**
     * Setzt eine Produktzeile mit den dazugehoerigen Komponenten zusammen
     * @param bildButton, name, bestandLabel, bestandAnzahl, nachbestelltLabel, nachbestelltAnzahl, preis, anzahl, hinzufuegen
     */
    @Override
    public void baueProduktZeile(Button bildButton, Label name, Label bestandLabel, Label bestandAnzahl,
            Label nachbestelltLabel, Label nachbestelltAnzahl, Label preis, TextField anzahl, Button hinzufuegen) {
        VBox bestandLabels = new VBox(bestandLabel, nachbestelltLabel);
        bestandLabels.setAlignment(Pos.CENTER);
        VBox bestandEinkaeufer = new VBox(bestandAnzahl, nachbestelltAnzahl);
        bestandEinkaeufer.setAlignment(Pos.CENTER);
        getChildren().addAll(bildButton, name, bestandEinkaeufer, bestandLabels, anzahl, hinzufuegen);
    }

    /**
     * Zeigt neue nachbestellte Anzahl an.
     * @param anzahl neue Anzahl an nachbestellten Exemplaren
     */
    public void setNachbestelltAnzahl(int anzahl) {
        this.getNachbestelltAnzahl().setText(Integer.toString(anzahl));
    }

    /**
     * Zeigt neuen Bestand an.
     * @param anzahl neuer Bestand
     */
    public void setBestandAnzahl(int anzahl) {
        this.getBestandAnzahl().setText(Integer.toString(anzahl));
    }

    /**
     * Gibt den Verwalter der View aus.
     * @return Controller
     */
    public ProduktBoxController getBoxController(){
        return this.boxController;
    }
}
