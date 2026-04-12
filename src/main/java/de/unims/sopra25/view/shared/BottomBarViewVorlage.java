package de.unims.sopra25.view.shared;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Abstrakte Klasse, die die Struktur der Szene BottomBarView vorgibt.
 *
 * @author David Metzdorf
 */
public abstract class BottomBarViewVorlage extends HBox {

    // Label
    protected Label shippingDateLabel;
    protected Label paymentDeciderLabel;

    // TextFields
    protected TextField shippingDateDay;
    protected TextField shippingDateMonth;
    protected TextField shippingDateYear;

    // RadioButtons
    protected RadioButton payWithCash;
    protected RadioButton payWithCard;

    /**
     * Konstruktor die die BottomBar mit allen Nodes initialisiert.
     * @author David Metzdorf, Florian Gerdes
     */
    public BottomBarViewVorlage() {
        initializeBottomBar();
    }

    /**
     * initialisiert die graphische Darstellung der BottomBar
     */
    private void initializeBottomBar() {

        shippingDateLabel = new Label("Lieferzeitpunkt:");
        paymentDeciderLabel = new Label("Zahlungsmethode:");

        shippingDateDay = new TextField();
        shippingDateDay.textProperty().addListener((obvervable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
                final String updatedValue = newValue.replace(',', '.')
                        .replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
                Platform.runLater(() -> {
                    shippingDateDay.setText(updatedValue);
                });
            }
        });
        shippingDateDay.setPromptText("Liefertag");

        shippingDateMonth = new TextField();
        shippingDateMonth.textProperty().addListener((obvervable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
                final String updatedValue = newValue.replace(',', '.')
                        .replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
                Platform.runLater(() -> {
                    shippingDateMonth.setText(updatedValue);
                });
            }
        });
        shippingDateMonth.setPromptText("Liefermonat");

        shippingDateYear = new TextField();
        shippingDateYear.textProperty().addListener((obvervable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
                final String updatedValue = newValue.replace(',', '.')
                        .replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
                Platform.runLater(() -> {
                    shippingDateYear.setText(updatedValue);
                });
            }
        });
        shippingDateYear.setPromptText("Lieferjahr");

        payWithCash = new RadioButton("Bar");
        payWithCard = new RadioButton("Karte");

        setSpacing(20);
        setAlignment(Pos.BOTTOM_CENTER);
    }

    /**
     * Abstrakte Methode, die von den einzelnen Implementierungen der verschiedenen
     * Akteuren benutzt wird.
     * Die konkreten Methoden koönnen dann entscheiden, welche Komponenten sie
     * anzeigen möchten
     *
     * @return HBox BottomBar anzeige
     */
    public abstract HBox baueBottomBar(
            Label shippingDateLabel, Label paymentDeciderLabel,
            TextField shippingDay, TextField shippingMonth, TextField shippingYear,
            RadioButton payWithCash, RadioButton payWithCard);

    /**
     * Getter fuer Lieferzeit Label
     * @return shippingDateLabel Lieferzeit Label
     */
    public Label getShippingDateLabel() {
        return shippingDateLabel;
    }

    /**
     * Getter fuer Zahlungsmethode Label
     * @return paymentDeciderLabel Zahlungsmethode Label
     */
    public Label getPaymentDeciderLabel() {
        return paymentDeciderLabel;
    }

    /**
     * Getter fuer Lieferzeittag Textfeld
     * @return shippingDateDay Lieferzeittag Textfeld
     */
    public TextField getShippingDateDay() {
        return shippingDateDay;
    }

    /**
     * Getter fuer Lieferzeitmonat Textfeld
     * @return shippingDateMonth Lieferzeitmonat Textfeld
     */
    public TextField getShippingDateMonth() {
        return shippingDateMonth;
    }

    /**
     * Getter fuer Lieferzeitjahr Textfeld
     * @return shippingDateYear Lieferzeitjahr Textfeld
     */
    public TextField getShippingDateYear() {
        return shippingDateYear;
    }

    /**
     * Getter fuer Barzahl-RadioButton
     * @return payWithCash Barzahl-RadioButton
     */
    public RadioButton getPayWithCash() {
        return payWithCash;
    }

    /**
     * Getter fuer Kartenzahl-RadioButton
     * @return payWithCard Kartenzahl-RadioButton
     */
    public RadioButton getPayWithCard() {
        return payWithCard;
    }

}
