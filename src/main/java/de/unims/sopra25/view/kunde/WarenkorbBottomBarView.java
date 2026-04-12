package de.unims.sopra25.view.kunde;

import de.unims.sopra25.model.logik.FehlerVerwalten;
import de.unims.sopra25.view.shared.BottomBarViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Klasse, die einen WarenkorbBottomBar Ansicht repraesentiert
 * 
 * @author David Metzdorf, Florian Gerdes
 */
public class WarenkorbBottomBarView extends BottomBarViewVorlage {

    // Eigene Buttons
    private Button orderButton;

	// Bestellung losgeben einmal Falsch
	private boolean failedOnce;

    // Gesamtpreis Label
    private Label fullPriceOfOrderLabel;

	/**
	 * Konstruktor, der alle Nodes initialisiert 
	 * @param fullPriceOfOrder Text Anzeige des Gesamtpreises
	 */
	public WarenkorbBottomBarView(String fullPriceOfOrder) {
		super();

		// Fuegt Bestell-Button hinzu
		orderButton = new Button("Bestellung tätigen");
		getChildren().add(orderButton);

		// Initialisiere Features die geshared sind
		baueBottomBar(shippingDateLabel, paymentDeciderLabel,
				shippingDateDay, shippingDateMonth, shippingDateYear,
				payWithCash, payWithCard);

        // initialisiere eigne Features
        fullPriceOfOrderLabel = new Label(fullPriceOfOrder);
        VBox fullPriceBox = new VBox();
        Region region = new Region();
        region.setPrefHeight(15);
        fullPriceBox.getChildren().addAll(region, new Text("Gesamtpreis:"),fullPriceOfOrderLabel);
        fullPriceBox.setAlignment(Pos.CENTER);

        // Fügt den Gesamtpreis Label hinzu
        getChildren().add(fullPriceBox);

		failedOnce = false;
	}

    /**
     * Baut eine BottomBar für den Kunden
     * Benutzt werden dabei nur Parameter, die für den Kunden relevant sind.
     *
     * @param shippingDateLabel, paymentDeciderLabel, shippingDay,
     *                           shippingMonth, shippingYear, payWithCash,
     *                           payWithCard
     */
    @Override
    public HBox baueBottomBar(Label shippingDateLabel, Label paymentDeciderLabel,
            TextField shippingDay, TextField shippingMonth, TextField shippingYear,
            RadioButton payWithCash, RadioButton payWithCard) {

        // Box mit Eingabe für Tag, Monat und Jahr
        HBox shippingHBox = new HBox();
        shippingHBox.getChildren().addAll(shippingDay, shippingMonth, shippingYear);
        VBox shippingVBox = new VBox();
        shippingVBox.getChildren().addAll(shippingDateLabel, shippingHBox);
        shippingVBox.setAlignment(Pos.CENTER);

        // Box mit Zwei Zahlungsmethoden
        HBox paymentHBox = new HBox();
        payWithCard.setSelected(false);
        payWithCash.setSelected(false);
        paymentHBox.getChildren().addAll(payWithCash, payWithCard);
        VBox paymentVBox = new VBox();
        Region region = new Region();
        region.setPrefHeight(20);
        paymentVBox.getChildren().addAll(region, paymentDeciderLabel, paymentHBox);
        
        
        // Fügt Bestell-Button und Beschriebene Box die der Kunde verändern hinzu
        getChildren().addAll(shippingVBox, paymentVBox);
        return this;
    }

	/**
	 * Soll Fehler anzeigen, dass Bestellung fehlgeschlagen ist
	 */
	public void bestellungFailed() {
		if (failedOnce) {
			return;
		}

		failedOnce = true;
		Text errorMessage = new Text("Bestellung Fehlgeschlagen!");
		errorMessage.setStyle("-fx-fill: red");

        getChildren().add(errorMessage);
        // Fehler anlegen
        FehlerVerwalten fehler = new FehlerVerwalten();
        fehler.addFehler("Bestellung tätigen fehlgeschlagen");
    }

    /**
     * Getter für Bestell-Button
     * 
     * @return Bestell-Button
     */
    public Button getBestellButton() {
        return orderButton;
    }

    /**
     * Getter für das Label, welches den
     * Gesamtpreis des Warenkorbs anzeigt.
     * 
     * @return Label fullPriceofOrder
     */
    public Label getFullPriceOfOrderLabel() {
        return fullPriceOfOrderLabel;
    }
}
