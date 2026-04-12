package de.unims.sopra25.view.einkaeufer;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Klasse die eine NachbestellungBox Anzeige repraesentiert
 *
 * @author David Metzdorf
 */
public class NachbestellungBoxView extends ProduktBoxViewVorlage {

    // ausgewaehlte Bestellung
    private E_Bestellung bestellung;

    // entferne Button
    private Button entferne;

    // bestaetige Button
    private Button bestaetige;
    
    private Region spacer;

    /**
     * Konstruktor eines NachbestellungBox Anzeige
     * @param bestellung ausgewaehlte Bestellung
     */
    public NachbestellungBoxView(E_Bestellung bestellung) {
        super(bestellung.getProdukt());
        this.bestellung = bestellung;

        // Produktzeile repraesentiert eine NachbestellungBox
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
        // Namen der Bestellung und Produkt als VBox
        Label bestellungNameLabel = new Label("Bestellung: #" + bestellung.getId());
        spacer = new Region();
        spacer.setPrefHeight(5);
        bestellungNameLabel.setPrefWidth(250);
        bestellungNameLabel.setAlignment(Pos.CENTER);
        VBox nameBox = new VBox();
        nameBox.setAlignment(Pos.CENTER);
        nameBox.getChildren().addAll(spacer, bestellungNameLabel, name);

        // Hinzufuege und Entferne Box
        entferne = new Button("-"); // hinzufuege ist "+"
        entferne.setPrefWidth(38);
        entferne.setAlignment(Pos.CENTER);
        entferne.getStyleClass().add("round-button");
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(hinzufuegen, entferne);

        bestaetige = new Button("Bestätige");
        bestaetige.setPrefWidth(700);
        bestaetige.setPrefHeight(50);
        bestaetige.setAlignment(Pos.CENTER);

        anzahl.setPrefHeight(50);
        anzahl.setText("1");

        // Setze die Ansicht zusammen
        HBox topPart = new HBox();
        topPart.getChildren().addAll(nameBox, getInfoBox(), anzahl, buttonBox);

        VBox rightPart = new VBox();
        rightPart.getChildren().addAll(topPart, bestaetige);

        getChildren().addAll(bildButton, rightPart);
    }

    /**
     * Gibt eine HBox mit Infos zu einer Bestellung zurueck. Die HBox besteht
     * aus einer VBox mit Preis und Nachbestellt-Label und einer VBox mit
     * Bestand und Nachbestellt-Label.
     *
     * @return HBox mit Infos zu einer Bestellung
     */
    private HBox getInfoBox() {
        HBox infoBox = new HBox();
        
        spacer = new Region();
        
        // Nachbestellt und Preis als VBox
        VBox orderedAndPrice = new VBox();
        Label dateLabel = new Label(bestellung.getDateString());
        dateLabel.setAlignment(Pos.CENTER);
        Label bestellungNachbestellt = new Label("Nachbestellt: " + bestellung.getAnzahl());
        bestellungNachbestellt.setAlignment(Pos.CENTER);
        orderedAndPrice.getChildren().addAll(dateLabel, bestellungNachbestellt);

        // Bestellt und Nachbestellt als VBox
        VBox availabilityBox = new VBox();
        getBestandLabel().setPrefWidth(200);
        getBestandLabel().setText("Bestand: " + getBestandAnzahl().getText());
        getNachbestelltLabel().setAlignment(Pos.CENTER);
        getNachbestelltLabel().setText("insgesamt Nachbestellt: " + getNachbestelltAnzahl().getText());
        availabilityBox.getChildren().addAll(getBestandLabel(), getNachbestelltLabel());

        infoBox.getChildren().addAll(orderedAndPrice, availabilityBox);

        return infoBox;
    }

    /**
     * Getter fuer die dazugehoerige Nachbestellung
     * @return E_Bestellung die diese NachbestellungsBox gehoert
     */
    public E_Bestellung getBestellung() {
        return bestellung;
    }

    /**
     * Getter fuer Entferne Button
     * @return Entferne Button
     */
    public Button getEntferneButton() {
        return entferne;
    }

    /**
     * Getter fuer Bestaetige Button
     * @return Bestaetige Button
     */
    public Button getBestaetigeButton() {
        return bestaetige;
    }

}
