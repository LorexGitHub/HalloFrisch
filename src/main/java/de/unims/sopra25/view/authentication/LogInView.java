package de.unims.sopra25.view.authentication;

import de.unims.sopra25.model.logik.FehlerVerwalten;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Klasse, die die Szene LogInView zeichnet
 * 
 * @author David Metzdorf, Lukas Tilgner
 */
public class LogInView extends GridPane {
    // Abstände
    private final int H_GAP = 10;
    private final int V_GAP = 10;

    // Bild
    Image logoImage;
    ImageView logoImageView;

    // TextFields
    private TextField usernameField;
    private PasswordField passwordField;

	// Buttons
	private Button loginButton;
	private Button registerButton;

	// Boolean zum merken ob schon einmal ein LogIn fehlgeschlagen ist
	private boolean failedLogInOnce = false;

	/**
	 * Konstruktor der Klasse LogInView
	 * Erzeugt ein LogInView inklusive der Buttons und Eingabefelder
	 */
	public LogInView() {
		super();
		this.setHgap(H_GAP);
		this.setVgap(V_GAP);
		this.setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

        // Logo Bild
        logoImage = new Image("LogoMitText.png");
        logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(400);
        logoImageView.setPreserveRatio(true);

        // Center Bild
        HBox logoBox = new HBox();
        logoBox.getChildren().add(logoImageView);
        logoBox.setAlignment(Pos.CENTER);
        addRow(0, logoBox);

        // Fields
        Pane fieldPane = initializeFieldPane();
        addRow(2, fieldPane);

        // Buttons
        Pane buttonPane = initializeButtonPane();
        addRow(4, buttonPane);

        setAlignment(Pos.CENTER);
    }

    // Zeigt eine Nachricht an, falls die Anmeldung fehlgeschlagen ist
    public void logInFailed() {
        if (failedLogInOnce) {
            return;
        }
		
		failedLogInOnce = true;
		Text errorMessage = new Text("Login fehlgeschlagen");
		errorMessage.setStyle("-fx-fill: red");

        HBox errorBox = new HBox();
        errorBox.setAlignment(Pos.CENTER);
        errorBox.getChildren().add(errorMessage);
        addRow(3, errorBox);
        // Fehler anlegen
        FehlerVerwalten fehler = new FehlerVerwalten();
        fehler.addFehler("LogIn fehlgeschlagen");
    }

	/**
	 * Initialisiert das Button Panel mit den Buttons Login und Registrieren
	 * 
	 * @return buttonPane Rueckgabe der visuellen Darstellung der Buttons
	 */
	private Pane initializeButtonPane() {
		loginButton = new Button("Login");
		registerButton = new Button("Registrieren");

		HBox buttonPane = new HBox();
		buttonPane.setSpacing(H_GAP);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(loginButton, registerButton);

		return buttonPane;
	}

    // Initialisiert die Eingabefelder für Nutzernamen und Passwort
    private Pane initializeFieldPane() {
        usernameField = new TextField();
        passwordField = new PasswordField();

        usernameField.setPromptText("Benutzername");
        passwordField.setPromptText("Passwort");
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        usernameField.setMaxWidth(300);
        passwordField.setMaxWidth(300);

        VBox fieldPane = new VBox();
        fieldPane.setSpacing(H_GAP);
        fieldPane.setAlignment(Pos.CENTER);
        fieldPane.getChildren().addAll(usernameField, passwordField);
		
        return fieldPane;
    }

    /**
     * Getter für Login button.
     * 
     * @return Login Button
     */
    public Button getLoginButton() {
        return loginButton;
    }

    /**
     * Getter für RegistrierenButton.
     * 
     * @return Registrieren Button
     */
    public Button getRegisterButton() {
        return registerButton;
    }

    /**
     * Getter für Eingabefeld des Nutzernamens
     *
     * @return TextField für username
     */
    public TextField getUsernameField() {
        return usernameField;
    }

    /**
     * Getter für Eingabefeld des Passworts
     *
     * @return TextField für Passwort
     */
    public TextField getPasswordField() {
        return passwordField;
    }

    /**
     * Getter for the logo image.
     *
     * @return Image fuer logo.
     */
    public Image getImage() {
        return logoImage;
    }

    /**
     * Getter für die ImageView des Logos
     *
     * @return ImageView für das Logo
     */
    public ImageView getImageView() {
        return logoImageView;
    }

}