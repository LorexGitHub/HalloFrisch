package de.unims.sopra25.view.authentication;

import de.unims.sopra25.model.logik.FehlerVerwalten;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Klasse die ein Registrierungsfenster repraesentiert und visuell darstellt
 * 
 * @author David Metzdorf, Lukas Tilgner
 */
public class RegistrierenView extends GridPane {

	// Wert für den inneren horizontalen Abstand zum Inhalt eines Feldes
	private final int H_GAP = 10;
	// Wert für den inneren vertikalen Abstand zum Inhalt eines Feldes
	private final int V_GAP = 10;

	// Abbrechen-Taste
	private Button abbruchButton;
	// Registrieren-Taste
	private Button registerButton;

	// Zustimmungscheckbox und Text
	private CheckBox agbCheckBox;
	private Label agbLabel1;
	private Label agbLabel2;
	private Label agbLabel3;

	// Benutzernamen Eingabefeld
	private TextField usernameField;
	// Passwort Eingabefeld
	private PasswordField passwordField;
	// Passwort wiederholen Eingabefeld
	private PasswordField secondPasswordField;

	// Kundendaten Eingabefelder
	private TextField surnameField;
	private TextField familynameField;
	private TextField addressField;

	// Text fuer die Fehlernachricht
	private Text errorMessage;

	/**
	 * Konstruktor der Klasse RegistrierenView
	 * Erzeugt ein RegistrierenView inklusive der Buttons und Eingabefelder
	 */
	public RegistrierenView() {
		super();
		this.setHgap(H_GAP);
		this.setVgap(V_GAP);
		this.setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

		// Buttons
		Pane buttonPane = initializeButtonPane();
		addRow(4, buttonPane);
		setHgrow(buttonPane, Priority.ALWAYS);

		// AGB
		Pane agbPane = initializeAGBPane();
		addRow(3, agbPane);
		setHgrow(buttonPane, Priority.ALWAYS);

		// Fields
		Pane fieldPane = initializeFieldPane();
		addRow(1, fieldPane);
		setHgrow(fieldPane, Priority.ALWAYS);
	}


	/**
	 * Zeigt eine Nachricht an, falls die Registrierung fehlgeschlagen ist
	 */
	public void registerFailed(String error) {
		if(errorMessage != null) {
			errorMessage.setText(error);
			return;
		} else {
			errorMessage = new Text(error);
		}

		errorMessage.setStyle("-fx-fill: red");

		HBox errorBox = new HBox();
		errorBox.setAlignment(Pos.CENTER);
		errorBox.getChildren().add(errorMessage);
		addRow(2, errorBox);
		//Fehler anlegen
		FehlerVerwalten fehler = new FehlerVerwalten();
		fehler.addFehler("Registrierung fehlgeschlagen");
	}

	/**
	 * Initialisiert das AGB Panel mit dem dazugehoerigen Text und Labels
	 * 
	 * @return agbBox Rueckgabe der visuellen Darstellung des Fensters
	 */
	private Pane initializeAGBPane() {
		// Erstelle Elemente
		agbCheckBox = new CheckBox();
		agbLabel1 = new Label(" Ich stimme den");
		agbLabel2 = new Label(" AGB ");
		agbLabel3 = new Label("zu!");

		// Label2 einfaerben
		agbLabel2.setTextFill(Color.DARKBLUE);
		agbLabel2.setBackground(Background.fill(Color.LIGHTBLUE));

		// Erstelle Box und gib Box Elemente
		HBox agbBox = new HBox();
		agbBox.setAlignment(Pos.BASELINE_LEFT);
		agbBox.getChildren().addAll(agbCheckBox, agbLabel1, agbLabel2, agbLabel3);

		return agbBox;
	}

	/**
	 * Initialisiert das Button Panel mit den Buttons Abbruch und Registrieren
	 * 
	 * @return buttonPane
	 */
	private Pane initializeButtonPane() {
		abbruchButton = new Button("Abbruch");
		registerButton = new Button("Registrieren");

		// Registrieren-Button deaktiviert bis den AGB zugestimmt wurde
		registerButton.setDisable(true);

		HBox buttonPane = new HBox();
		buttonPane.setSpacing(H_GAP);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(abbruchButton, registerButton);

		return buttonPane;
	}

	/**
	 * Initialisiert die Panel mit entsprechenden Eingabefelder 
	 * @return fieldPane
	 */
	private Pane initializeFieldPane() {
		surnameField = new TextField();
		familynameField = new TextField();
		addressField = new TextField();
		usernameField = new TextField();
		passwordField = new PasswordField();
		secondPasswordField = new PasswordField();

		surnameField.setPromptText("Vorname");
		familynameField.setPromptText("Nachname");
		addressField.setPromptText("Adresse");
		usernameField.setPromptText("Benutzername");
		passwordField.setPromptText("Passwort");
		secondPasswordField.setPromptText("Passwort wiederholen");

		surnameField.setEditable(true);
		familynameField.setEditable(true);
		addressField.setEditable(true);
		usernameField.setEditable(true);
		passwordField.setEditable(true);
		secondPasswordField.setEditable(true);

		VBox fieldPane = new VBox();
		fieldPane.setSpacing(H_GAP);
		fieldPane.getChildren().addAll(surnameField, familynameField, addressField,
				usernameField, passwordField, secondPasswordField);
		return fieldPane;
	}

	/**
	 * getter-Methode für Abbruch Taste 
	 * @return abbruchButton
	 */
	public Button getAbbruchButton() {
		return abbruchButton;
	}

	/**
	 * getter-Methode für Registrieren Taste
	 * @return registerButton
	 */
	public Button getRegisterButton() {
		return registerButton;
	}

	/**
	 * getter-Methode für Nutzernameneingabefeld 
	 * @return usernameField
	 */
	public TextField getUsernameField() {
		return usernameField;
	}

	/**
	 * getter-Methode für Passworteingabefeld 
	 * @return passwordField
	 */
	public TextField getPasswordField() {
		return passwordField;
	}

	/**
	 * getter-Methode für zweites Passworteingabefeld 
	 * @return secondPasswordField
	 */
	public TextField getSecondPasswordField() {
		return secondPasswordField;
	}

	/**
	 * getter-Methode für Vornameeingabefeld 
	 * @return surnameField
	 */
	public TextField getSurnameField() {
		return surnameField;
	}

	/**
	 * getter-Methode für Nachnameeingabefeld
	 * @return familynameField
	 */
	public TextField getFamilynameField() {
		return familynameField;
	}

	/**
	 * getter-Methode für Adresseingabefeld
	 * @return addressField
	 */
	public TextField getAddressField() {
		return addressField;
	}

	/**
	 * Gibt den hervorgehobenen Teil des
	 * AGB-Labels zurueck
	 * @return agbLabel2 Hervorgehobenen Teil des AGB-Labels
	 */
	public Label getAGBLabel() {
		return agbLabel2;
	}

	/**
	 * Gibt die Checkbox fuer die Zustimmung der AGB zurueck
	 * @return agbCheckBox Checkbox fuer die Zustimmung der AGB
	 */
	public CheckBox getAGBCheckbox() {
		return agbCheckBox;
	}
}
