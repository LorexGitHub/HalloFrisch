package de.unims.sopra25.view.kunde;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_BestellungProduktAssoz;
import de.unims.sopra25.model.logik.FehlerVerwalten;
import de.unims.sopra25.view.shared.BottomBarViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * BottomBar bei Bestellung Uebersicht
 *
 * @author David Metzdorf
 */
public class BestellungBottomBarView extends BottomBarViewVorlage {

	// ausgewaehlte Bestellung
	K_Bestellung bestellung;

    // Bestaetige Aenderung Button
    Button saveChanges;

    // Errormessage
    Text errorMessage;

    // Bestätigung
    Text bestaetigung;

    /**
     * Erzeugt eine BestellungBottomBar anzeige
     * 
     * @param bestellung ausgewählte Bestellung
     */
    public BestellungBottomBarView(K_Bestellung bestellung) {
        super();

        this.bestellung = bestellung;

        baueBottomBar(shippingDateLabel, paymentDeciderLabel,
                shippingDateDay, shippingDateMonth, shippingDateYear,
                payWithCash, payWithCard);

        // initialisiere Errormessage, wird nur angezeigt, wenn benoetigt
        errorMessage = new Text("Änderung Fehlgeschlagen!");
        errorMessage.setStyle("-fx-fill: red");

        // initialisiere bestaetigungsnachricht, wird angezeigt, wenn aenderung
        // vorgenommen wurde
        bestaetigung = new Text("Änderung erfolgreich!");
        bestaetigung.setStyle("-fx-fill: green");
    }

    /**
     * Baut eine BottomBar für den Kunden
     * Benutzt werden dabei nur Parameter, die für den Kunden relevant sind.
     *
     * @param shippingDateLabel, paymentDeciderLabel, shippingDay, shippingMonth,
     *                           shippingYear, payWithCash, payWithCard
     */
    @Override
    public HBox baueBottomBar(
            Label shippingDateLabel, Label paymentDeciderLabel,
            TextField shippingDay, TextField shippingMonth, TextField shippingYear,
            RadioButton payWithCash, RadioButton payWithCard) {
        if (bestellung.getStatus() == K_Bestellung.Status.AUSGELIEFERT) {
            buildCompleteBottomBar();
            return this;
        }

        // Speichern-Button
        saveChanges = new Button("Änderung Bestätigen");

        int curDay = 0;
        int curMonth = 0;
        int curYear = 0;
        // Get wunschliefertag Daten von Bestellung
        Calendar wunschliefertag = bestellung.getWunschliefertag();
        try {
            curDay = wunschliefertag.get(Calendar.DAY_OF_MONTH);
            curMonth = wunschliefertag.get(Calendar.MONTH) + 1;
            curYear = wunschliefertag.get(Calendar.YEAR);
        } catch (NullPointerException e) {
            FehlerVerwalten fv = new FehlerVerwalten();
            fv.addFehler(
                    "Bestellung " + Integer.toString(this.bestellung.getId()) + " hat noch keinen Wunschliefertag");
        }

        // setze Daten
        if (curDay == 0 || curMonth == 0 || curYear == 0) {
            shippingDay.setText("");
            shippingMonth.setText("");
            shippingYear.setText("");
        } else {
            shippingDay.setText(String.valueOf(curDay));
            shippingMonth.setText(String.valueOf(curMonth));
            shippingYear.setText(String.valueOf(curYear));
        }
        // Wunschliefertag Box
        HBox shippingDateHBox = new HBox();
        shippingDateHBox.getChildren().addAll(shippingDay, shippingMonth, shippingYear);
        VBox shippingDateVBox = new VBox();
        shippingDateVBox.getChildren().addAll(shippingDateLabel, shippingDateHBox);

        // Zahlungsmittel Box
        HBox paymentMethodHBox = new HBox();
        if (bestellung.getZahlungsmittel() == K_Bestellung.Zahlungsmittel.BAR) {
            payWithCash.setSelected(true);
        } else if (bestellung.getZahlungsmittel() == K_Bestellung.Zahlungsmittel.KARTE) {
            payWithCard.setSelected(true);
        }
        paymentMethodHBox.getChildren().addAll(payWithCash, payWithCard);
        VBox paymentMethodVBox = new VBox();
        Region regionPayment = new Region();
        regionPayment.setPrefHeight(15);
        paymentMethodVBox.getChildren().addAll(regionPayment, paymentDeciderLabel, paymentMethodHBox);

        // Summe
        Label summeLabel = new Label("Gesamtpreis: ");
        int sum = 0;
        for (K_BestellungProduktAssoz assoz : bestellung.getProdukte()) {
            sum += assoz.getPreis() * assoz.getAnzahl();
        }
        Label summeValueLabel = new Label(getPreisString(sum));
        VBox summeVBox = new VBox();
        Region regionSum = new Region();
        regionSum.setPrefHeight(15);
        summeVBox.getChildren().addAll(regionSum, summeLabel, summeValueLabel);
        
        shippingDateVBox.setAlignment(Pos.CENTER);
        paymentMethodVBox.setAlignment(Pos.CENTER);
        summeVBox.setAlignment(Pos.CENTER);
        getChildren().addAll(saveChanges, shippingDateVBox, paymentMethodVBox, summeVBox);

		return this;
	}

    /**
     * Getter für Änderungspeichern Button
     * 
     * @return Änderungspeichern Button
     */
    public Button getSaveButton() {
        return saveChanges;
    }

    /**
     * Erzeugt eine BottomBar für eine Bestellung die schon ausgeliefert wurde
     */
    private void buildCompleteBottomBar() {
        Label idLabel = new Label("Bestell Nr. " + bestellung.getId());
        Label shippingDateLabel = new Label("Auslieferungsdatum: " + bestellung.getDatum());
        Label paymentMethodLabel = new Label("Zahlungsmittel: " + bestellung.getZahlungsmittel());

        setAlignment(Pos.CENTER);
        getChildren().addAll(idLabel, shippingDateLabel, paymentMethodLabel);
    }

    /**
     * Fehlermedlung, wenn änderung nicht gespeichert werden konnte
     */
    public void saveChangesFailed() {

        getChildren().add(errorMessage);
        // Fehler anlegen
        FehlerVerwalten fehler = new FehlerVerwalten();
        fehler.addFehler("Bestellung " + bestellung.getId() + " bearbeiten fehlgeschlagen");
    }

    /**
     * entfernt die vorherige Errormessage, falls Bestellung aktualisiert wird
     */
    public void removeErrorMessage() {

        getChildren().remove(errorMessage);
    }

    /**
     * zeigt Bestaetigungsnachricht
     */
    public void zeigeBestaetigung() {

        getChildren().add(bestaetigung);
    }

    /**
     * entfernt die vorherige Bestaetigungsmessage
     */
    public void removeBestaetigungMessage() {

        getChildren().remove(bestaetigung);
    }

    /**
     * Getter für Summe der Preise von Produkte im Warenkorb
     * 
     * @return gesamt Preis als int
     */
    private static String getPreisString(int i) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
        DecimalFormat format = new DecimalFormat("#,##0.00 €", symbols);
        return format.format((double) i / 100);
    }
}
