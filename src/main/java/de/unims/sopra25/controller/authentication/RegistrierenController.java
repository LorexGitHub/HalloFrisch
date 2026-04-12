package de.unims.sopra25.controller.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import de.unims.sopra25.model.logik.NutzerVerwalten;
import de.unims.sopra25.view.authentication.RegistrierenView;

/**
 * Klasse welche das Regristrieren-View kontrolliert
 * 
 * @author Frederik Flügge
 */
public class RegistrierenController {

    // Stage des Controllers
    private AuthenticationStageController authStage;

    // View des Controllers
    private RegistrierenView view;

    // Logik für die Verwaltung von Nutzern
    private NutzerVerwalten nutzerVerwalten;

    /**
     * Konstruktor fuer den Registrieren Controller
     * Initialisiert die view, stage und Event Handler des Controllers
     * 
     * @param authStage Stage des Controllers
     */
    public RegistrierenController(AuthenticationStageController authStage) {
        this.authStage = authStage;

        this.view = new RegistrierenView();

        initializeEventHandler(view);
    }

    /**
     * Initialisiert alle Eventhandler
     * 
     * @param register RegistrierenView
     */
    private void initializeEventHandler(RegistrierenView register) {

        // Abbrechen Button wurde gedrueckt; Navigiert zurueck zu der LogInView
        register.getAbbruchButton().setOnAction((event) -> {
            authStage.switchToLogInView();
        });

        // CheckBox wurde gedrueckt; Registrieren Button kann nun gedrueckt werden
        register.getAGBCheckbox().setOnAction((event) -> {
            register.getRegisterButton().setDisable(!register.getAGBCheckbox().isSelected());
            event.consume();
        });

        // AGB Label wurde gedrueckt; Öffnet die AGB View
        register.getAGBLabel().setOnMouseClicked((event) -> {
            authStage.getAGBPopupStage().show();
            event.consume();
        });

        // Registrieren Button wurde gedrueckt; Registriert den Kunden
        register.getRegisterButton().setOnAction((event) -> {
            // Daten aus Felder
            String surname = register.getSurnameField().getText();
            String familyname = register.getFamilynameField().getText();
            String address = register.getAddressField().getText();
            String username = register.getUsernameField().getText();
            String password = register.getPasswordField().getText();
            String secondPassword = register.getSecondPasswordField().getText();

            // Pruefen ob alle Felder ausgefuellt sind
            if (surname.isBlank() ||
                    familyname.isBlank() ||
                    address.isBlank() ||
                    username.isBlank() ||
                    password.isBlank() ||
                    secondPassword.isBlank()) {
                register.registerFailed("Es müssen alle Felder ausgefüllt sein!");
                return;
            }

            // Pruefen ob beide Passwoerter übereinstimmen
            if (!password.equals(secondPassword)) {
                register.registerFailed("Die Passwörter unterscheiden sich!");
                return;
            }

            // Anbindung mit Logik
            nutzerVerwalten = new NutzerVerwalten();
            Boolean result = false;
            try {
                result = nutzerVerwalten.addKunde(username, password, address, surname, familyname);
                // Falls result = false: Name vergeben
                if (!result) {
                    register.registerFailed("Nutzername ist bereits vergeben!");
                    return;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            // Falls Exception gecatched wurde
            if (!result) {
                register.registerFailed("Registrierung fehlgeschlagen!");
                return;
            }

            // Registrierung ist erfolgreich gewesen
            authStage.switchToLogInView();
        });

    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return RegistrierenView
     */
    public RegistrierenView getView() {
        return view;
    }

}
