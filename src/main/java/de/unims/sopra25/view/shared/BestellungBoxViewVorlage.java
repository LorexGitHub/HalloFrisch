package de.unims.sopra25.view.shared;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import de.unims.sopra25.model.entitaeten.K_Bestellung;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Klasse die eine BestellungsBox fuer die BestellungsUebersicht darstellt
 * 
 * @author Irina Hoppe
 */
public abstract class BestellungBoxViewVorlage extends HBox {

	protected K_Bestellung bestellung;

	// horizontale und vertikale Abstaende
	private final int H_GAP = 10;
	private final int V_GAP = 10;

	// Labels
	private Label datum;
	private Label zahlungsmittel;
	private Label wunschliefertagLabel;
	private Label wunschliefertag;
	private VBox tag;
	protected Label status;

	// Buttons
	protected Button nummer;
	protected Button annehmen;
	protected Button ausliefern;

	/**
	 * Konstruktor
	 * 
	 * @param bestellung Bestellung des Kunden
	 */
	public BestellungBoxViewVorlage(K_Bestellung bestellung) {
		this.bestellung = bestellung;
		initialisiereBestellungBox();
	}

	/**
	 * Initialisiere eine Zeile in der BestellungsUebersicht
	 */
	private void initialisiereBestellungBox() {
		// Styling
		setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));
		setSpacing(40);

		// Nummer der Bestellung
		nummer = new Button(Integer.toString(bestellung.getId()));
		nummer.setPrefWidth(38);
		nummer.setPrefHeight(38);
		nummer.setBackground(null);

		// Datum der Bestellung
		datum = new Label(bestellung.getDateString());
		datum.setPrefWidth(190);

		// Status der Bestellung
		K_Bestellung.Status state = bestellung.getStatus();
		status = new Label(bestellung.getStatus().toString());
		status.setPrefWidth(190);

		// Farbe ändern je nach Status
		switch (state) {
			case K_Bestellung.Status.OFFEN:
				status.setStyle("-fx-text-fill: gray");
				break;
			case K_Bestellung.Status.VORBEREITET:
				status.setStyle("-fx-text-fill: orange");
				break;
			case K_Bestellung.Status.AUSGELIEFERT:
				status.setStyle("-fx-text-fill: green");
				break;
			default:
				break;
		}

		// Zahlungsmittel
		zahlungsmittel = new Label(bestellung.getZahlungsmittel().toString());
		zahlungsmittel.setPrefWidth(200);

		// Wunschliefertag
		wunschliefertagLabel = new Label("Wunschliefertag:");
		wunschliefertag = new Label(bestellung.getWunschliefertagString());

		// Tag der Bestellung
		tag = new VBox(wunschliefertagLabel, wunschliefertag);
		tag.setPrefWidth(200);
		
		// Annehmen Button
		annehmen = new Button("Annehmen");
		annehmen.setPrefWidth(140);
		ausliefern = new Button("Ausliefern");
		ausliefern.setPrefWidth(140);
		// Styling
		setAlignment(Pos.CENTER_LEFT);

		baueBestellungZeile(nummer, datum, status, zahlungsmittel, tag, annehmen, ausliefern);
	}

	/**
	 * abstrakte Methode zum bauen einer Bestellungsbox je nach Unterklasseninstanz
	 * 
	 * @param nummer, datum, status, zahlungsmittel, tag, annehmen
	 */
	public abstract void baueBestellungZeile(Button nummer, Label datum, Label status, Label zahlungsmittel, VBox tag,
			Button annehmen, Button ausliefern);

	/**
	 * Gib Button mit BestellID zurueck der auf die Bestellungdetailseite fuehrt
	 * 
	 * @return Button nummer
	 */
	public Button getNummerButton() {
		return nummer;
	}

	/**
	 * gibt Label mit Status zurück
	 * 
	 * @return Label
	 */
	public Label getStatusLabel() {
		return status;
	}

	/**
	 * gibt K_Bestellung zum setzen der Bottombar zurueck
	 * 
	 * @return K_Bestellung
	 */
	public K_Bestellung getBestellung() {
		return bestellung;
	}
}
