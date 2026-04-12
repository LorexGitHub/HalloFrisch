package de.unims.sopra25.view.kunde;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Klasse, die ein ProfilBearbeiten Fenster repraesentiert
 * Es ist moeglich den Vor- und Nachnamen, die Addresse (Postleitzahl, Stadt,
 * Straße, HausNr.)
 * und das Passwort zu aendern. Es gibt einen "Zurueck" und einen "Uebernehmen"
 * Button. Für die
 * Aenderung des Passworts muss das alte Passwort eingegeben werden.
 * 
 * @author Lukas Tilgner, Constantin Meininghaus
 */
public class ProfilBearbeitenView extends GridPane {

	// Abstaende
	private final int H_GAP = 10;
	private final int V_GAP = 10;

	// Buttons
	private Button saveButton;

	// TextFields
	private TextField vornameField;
	private TextField nachnameField;
	private TextField adresseField;

	// Text für Informationen
	private Text messageText;
	private Text infoText = new Text();

	// Box für anzeigen der Message
	private HBox messageBox = new HBox();
	private HBox infoBox = new HBox();

	/**
	 * Konstruktor, die ProfilBearbeitenView initialisiert
	 */
	public ProfilBearbeitenView() {
		initialisiereProfilBearbeitenView();
	}

	/**
	 * Initialisiert die ProfilBearbeitenView
	 * @return GridPane
	 */
	private GridPane initialisiereProfilBearbeitenView() {
		// Styling
		setHgap(H_GAP);
		setVgap(V_GAP);
		setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

		// initialisiert buttons
		Pane buttonPane = initializeButtonPane();
		addRow(2, buttonPane);
		setHgrow(buttonPane, Priority.ALWAYS);

		// initialisiert fields
		Pane fieldPane = initializeFieldPane();
		addRow(1, fieldPane);
		setHgrow(fieldPane, Priority.ALWAYS);

        // initialisiert Bestätigung
        createMessage();

        // Uebernehmen Button ausgegraut
        saveButton.setDisable(true);

		return this;
	}

	/**
	 * Erzeugt ein buttonPanel mit dem Button saveButton 
	 * @return buttonPane mit saveButton
	 */
	private Pane initializeButtonPane() {
		// prepare buttons
		saveButton = new Button("Änderungen Übernehmen");

		// set up button pane
		VBox buttonPane = new VBox();
		buttonPane.setSpacing(H_GAP);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(saveButton);

		return buttonPane;
	}

	/**
	 * Erzeugt ein Pane, dass aus ein Label und Barbeitungfeld besteht 
	 * @param labelString Labelinhalt, field Bearbeitungsfeld
	 * @return pane mit beschrifteten Label und Bearbeitungfeld
	 */
	private Pane fieldWithLabel(String labelString, TextField field) {
		// set up label
		Label label = new Label(labelString);
		label.setPrefWidth(150);
		label.setAlignment(Pos.BASELINE_RIGHT);

		// set up pane
		HBox pane = new HBox();
		pane.setSpacing(H_GAP);
		pane.getChildren().addAll(label, field);
		HBox.setHgrow(field, Priority.ALWAYS);

		return pane;
	}

	/**
	 * Erzeugt das fieldPanes das aus Panes mit Daten von Vorname, Nachname und
	 * Adresse besteht. 
	 * @return fieldPane mit Panes für die Daten
	 */
	private Pane initializeFieldPane() {
		// Erzeugen der Bearbeitungsfelder
		vornameField = new TextField();
		nachnameField = new TextField();
		adresseField = new TextField();

        //Prompt Texte setzen
        vornameField.setPromptText("Neuer Vorname");
        nachnameField.setPromptText("Neuer Nachname");
        adresseField.setPromptText("Neue Adresse");

		// Zusammenfügen der FieldPanes
		VBox fieldPane = new VBox();
		fieldPane.setSpacing(V_GAP);
		fieldPane.getChildren().addAll(
				fieldWithLabel("Vorname: ", vornameField),
				fieldWithLabel("Nachname:", nachnameField),
				fieldWithLabel("Adresse:", adresseField));

		return fieldPane;
	}

	/**
	 * Zeigt die Nachricht der erfolgreichen Speicherung der neuen Kundendaten
	 */
	public void createMessage() {

		// Message Text wird gestylt
		messageText = new Text();
		messageText.setText("");
		messageText.setStyle("-fx-fill: green");

        // Positionierung der MessageBox
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(messageText);
        addRow(3, messageBox);
    }

	/**
	 * Zeigt die aktuellen Daten des Kunden (Name, Vorname, Adresse) 
	 * @param vorname, nachname, adresse Kundendaten
	 */
	public void initializeInfoText(String vorname, String nachname, String adresse) {

		// Message Text wird gestylt
		infoText.setText(" Aktueller Vorname:     " + vorname + "\n\n Aktueller Nachname:  " + nachname + "\n\n Aktuelle Adresse:        " + adresse);

		// Positionierung der MessageBox
		if(!infoBox.getChildren().contains(infoText)) {
			infoBox.setAlignment(Pos.CENTER);
			infoBox.getChildren().add(infoText);
			addRow(4, infoBox);
		}
	}

	/**
	 * Getter Methode fuer Speicher Button
	 * @return saveButton der View
	 */
	public Button getSaveButton() {
		return saveButton;
	}

	/**
	 * Getter Methode fuer vornameFeld
	 * @return vornameField der View
	 */
	public TextField getVornameField() {
		return vornameField;
	}

	/**
	 * Getter Methode fuer nachnameFeld 
	 * @return nachnameField der View
	 */
	public TextField getNachnameField() {
		return nachnameField;
	}

	/**
	 * Getter Methode fuer adresseFeld 
	 * @return adresseField der View
	 */
	public TextField getAdresseField() {
		return adresseField;
	}

	/**
	 * Setter Methode fuer messageText
	 * @param message neuer Message-Text als String
	 */
	public void setMessageText(String message) {
		messageText.setText(message);
	}
}
