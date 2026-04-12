package de.unims.sopra25.view.kunde;

import javafx.scene.layout.GridPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Klasse, die ein Profilfenster für den Kunden zeichnet.
 * 
 * @author Lukas Tilgner, Constantin Meininghaus
 */
public class ProfilView extends GridPane {
	// Abstaende
	private final int H_GAP = 30;
	private final int V_GAP = 10;

	// Buttons
	private Button profilBearbeitenButton;
	private Button bestellungEinsehenButton;
	private Button logOutButton;

	/**
	 * Konstruktor, der ein LogInView initialisiert
	 */
	public ProfilView() {
		initialisiereProfilView();
	}

	/**
	 * Initialisiert die ProfilView
	 * @return GridPane der View
	 */
	private GridPane initialisiereProfilView() {
		// Buttons
		Pane buttonPane = initializeButtonPane();
		addRow(2, buttonPane);
		setHgrow(buttonPane, Priority.ALWAYS);

		// Styling
		setHgap(H_GAP);
		setVgap(V_GAP);
		setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

		// Rueckgabe ProfilView
		return this;
	}

	/**
	 * Initialisiert die Buttons in ProfilView 
	 * @return buttonPane der View
	 */
	private Pane initializeButtonPane() {
		profilBearbeitenButton = new Button("Profil bearbeiten");
		bestellungEinsehenButton = new Button("Bestellungen einsehen");
		logOutButton = new Button("Ausloggen");

		// Button- und Schriftgroeße einstellen
		profilBearbeitenButton.setPrefWidth(300);
		profilBearbeitenButton.setPrefHeight(75);
		profilBearbeitenButton.setStyle("-fx-font-size:20");
		bestellungEinsehenButton.setPrefWidth(300);
		bestellungEinsehenButton.setPrefHeight(75);
		bestellungEinsehenButton.setStyle("-fx-font-size:20");
		logOutButton.setPrefWidth(300);
		logOutButton.setPrefHeight(75);
		logOutButton.setStyle("-fx-font-size:20");

		VBox buttonPane = new VBox();
		buttonPane.setSpacing(H_GAP);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(profilBearbeitenButton, bestellungEinsehenButton, logOutButton);

		return buttonPane;
	}

	/**
	 * Gibt den ProfilBearbeitenButton zurueck. 
	 * @return profilBearbeitenButton
	 */
	public Button getProfilBearbeitenButton() {
		return profilBearbeitenButton;
	}

	/**
	 * Gibt den BestellungEinsehenButton zurueck. 
	 * @return bestellungEinsehenButton
	 */
	public Button getBestellungEinsehenButton() {
		return bestellungEinsehenButton;
	}

	/**
	 * Gibt den getLogOutButton zurueck. 
	 * @return logOutButton
	 */
	public Button getLogOutButton() {
		return logOutButton;
	}
}