package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.model.logik.FehlerVerwalten;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Klasse, die die Szene NutzerRegistrierenView zeichnet
 *
 * @author Frederik Fluegge
 */
public class NutzerRegistrierenView extends GridPane {

	// Abstaende
	private final int H_GAP = 10;
	private final int V_GAP = 10;

	// Buttons
	private Button registerButton;

	// TextFields
	private TextField usernameField;
	private PasswordField passwordField;
	private PasswordField secondPasswordField;

	// Group für die Radio Buttons
	private ToggleGroup radioButtons;

	// Radio Buttons (Check Boxen)
	private RadioButton einkaeuferBox;
	private RadioButton lieferantenBox;
	private RadioButton geschaeftsfuehrerBox;

	// Error / Success Message
	private Text notifyMessage;

	/**
	 * Konstruktor der Klasse RegistrierenView
	 * Erzeugt ein RegistrierenView inklusive der Buttons und Eingabefelder
	 */
	public NutzerRegistrierenView() {
		initialisiereNutzerRegistrieren();
	}

	/**
	 * Initialisiert die View.
	 */
	private void initialisiereNutzerRegistrieren() {

		// Alignment
		this.setHgap(H_GAP);
		this.setVgap(V_GAP);
		this.setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

		// Error / Success Message
		notifyMessage = new Text();

		// Buttons
		Pane buttonPane = initializeButtonPane();
		addRow(3, buttonPane);
		setHgrow(buttonPane, Priority.ALWAYS);

		// Fields
		Pane fieldPane = initializeFieldPane();
		addRow(1, fieldPane);
		setHgrow(fieldPane, Priority.ALWAYS);

		Pane radioButtonPane = initalizeRadioButtonPane();
		addRow(1, radioButtonPane);
		setHgrow(radioButtonPane, Priority.ALWAYS);

	}


	/**
	 * Initialisiert das Button Panel mit den Buttons Abbruch und Registrieren
	 * @return buttonPane
	 */
	private Pane initializeButtonPane() {
		registerButton = new Button("Registrieren");

		HBox buttonPane = new HBox();
		buttonPane.setSpacing(H_GAP);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(registerButton);

		return buttonPane;
	}

	/**
	 * Initialisiert die Eingabefelder
	 * @return fieldPane
	 */
	private Pane initializeFieldPane() {
		usernameField = new TextField();
		passwordField = new PasswordField();
		secondPasswordField = new PasswordField();

		usernameField.setPromptText("Benutzername");
		passwordField.setPromptText("Passwort");
		secondPasswordField.setPromptText("Passwort wiederholen");

		usernameField.setEditable(true);
		passwordField.setEditable(true);
		secondPasswordField.setEditable(true);

		VBox fieldPane = new VBox();
		fieldPane.setSpacing(H_GAP);
		fieldPane.getChildren().addAll(
				usernameField, passwordField, secondPasswordField);
		return fieldPane;
	}

	/**
	 * Initialisiert die Check Boxen
	 * @return radioButtonPane
	 */
	private Pane initalizeRadioButtonPane() {

		radioButtons = new ToggleGroup();

		einkaeuferBox = new RadioButton("Einkäufer");
		einkaeuferBox.setToggleGroup(radioButtons);
		einkaeuferBox.setUserData("Einkäufer");

		geschaeftsfuehrerBox = new RadioButton("Geschäftsführer");
		geschaeftsfuehrerBox.setToggleGroup(radioButtons);
		geschaeftsfuehrerBox.setUserData("Geschäftsführer");

		lieferantenBox = new RadioButton("Lieferant");
		lieferantenBox.setToggleGroup(radioButtons);
		lieferantenBox.setUserData("Lieferant");

		VBox radioButtonPane = new VBox();
		radioButtonPane.setSpacing(H_GAP);
		radioButtonPane.getChildren().addAll(einkaeuferBox, geschaeftsfuehrerBox, lieferantenBox);

		return radioButtonPane;
	}
	
    /**
     * View, die gezeigt wird, wenn ein Input field nicht ausgefuellt wurde
     */
    public void inputFailed(String text) {
        notifyMessage.setText(text);
        notifyMessage.setStyle("-fx-fill: red");

		HBox errorBox = new HBox();
		errorBox.getChildren().add(notifyMessage);
		getChildren().add(errorBox);
		//Fehler anlegen
		FehlerVerwalten fehler = new FehlerVerwalten();
		fehler.addFehler("Nutzerregistrierung ausgeführt von Geschäftsführer fehlgeschlagen");
	}

	/**
	 * View, die gezeigt wird, wenn der Nutzer erfolgreich angelegt wurde
	 */
	public void inputSucced() {
		notifyMessage.setText("Der Nutzer wurde erfolgreich registriert");
		notifyMessage.setStyle("-fx-fill: green");

		HBox successBox = new HBox();
		successBox.getChildren().add(notifyMessage);
		getChildren().add(successBox);
	}

	/**
	 * Getter Methode für den Nutzer-Registrieren Button
	 * @return registerButton
	 */
	public Button getRegistrierenButton() {
		return registerButton;
	}

	/**
	 * Getter Methode für das Username TextField
	 * @return usernameField
	 */
	public TextField getUsernamTextField() {
		return usernameField;
	}

	/**
	 * Getter Methode für das Passwort TextField
	 * @return passwordField
	 */
	public TextField getPasswordTextField() {
		return passwordField;
	}

	/**
	 * Getter Methode für das Second Passwort TextField
	 * @return secondPasswordField
	 */
	public TextField getSecondPasswordTextField() {
		return secondPasswordField;
	}

	/**
	 * Getter Methode für die Gruppe an Radio Boxen (Check Boxen)
	 * @return radioButtons
	 */
	public ToggleGroup getRadioButtonsToggleGroup() {
		return radioButtons;
	}

}
