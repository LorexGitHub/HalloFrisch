package de.unims.sopra25.controller.authentication;

import de.unims.sopra25.controller.einkaeufer.EinkaeuferStageController;
import de.unims.sopra25.controller.geschaeftsfuehrer.GeschaeftsfuehrerStageController;
import de.unims.sopra25.controller.kunde.KundeStageController;
import de.unims.sopra25.controller.lieferant.LieferantStageController;
import de.unims.sopra25.model.entitaeten.Einkaeufer;
import de.unims.sopra25.model.entitaeten.Geschaeftsfuehrer;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Lieferant;
import de.unims.sopra25.model.logik.NutzerVerwalten;
import de.unims.sopra25.view.authentication.LogInView;
import javafx.scene.input.KeyCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Klasse welche das LogIn-View kontrolliert
 * 
 * @author Frederik Flügge, David Metzdorf
 */
public class LogInController {

    // Stage des Controllers
    private AuthenticationStageController authStage;

    // View des Controllers
    private LogInView view;

    // Logik für die Verwaltung von Nutzern
    private NutzerVerwalten nutzerVerwalten;

    /**
     * Konstruktor fuer den LogIn Controller
     * Initialisiert die view, stage und Event Handler des Controllers
     * 
     * @param authStage Stage des Controllers
     */
    public LogInController(AuthenticationStageController authStage) {
        this.authStage = authStage;

        this.view = new LogInView();

        initializeEventHandler(view);
    }

    /**
     * Initialisiert alle Eventhandler
     * 
     * @param logIn LogInView
     */
    private void initializeEventHandler(LogInView logIn) {
        
        // Enter Taste wird gedrueckt; Loggt den Nutzer ein
        logIn.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                logIn.getLoginButton().arm();
                logIn.getLoginButton().fire();
            }
        });

        // LogIn Button wurde gedrueckt; Loggt den Nutzer ein
        logIn.getLoginButton().setOnAction((event) -> {
            // Daten aus Felder
            String username = logIn.getUsernameField().getText();
            String password = logIn.getPasswordField().getText();

            nutzerVerwalten = new NutzerVerwalten();
            NutzerVerwalten.NutzerTyp nutzerTyp = null;
            try {
                nutzerTyp = nutzerVerwalten.login(username, password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            if (nutzerTyp == null) {
                view.logInFailed();
                return;
            }

            // Falls kein Nutzer mit username und password gefunden wurde,
            // dann ist nutzerTyp = null
            switch (nutzerTyp) {
                case NutzerVerwalten.NutzerTyp.KUNDE:
                    Kunde kunde = (Kunde) nutzerVerwalten.getLoggedIn();
                    KundeStageController kundeStage = new KundeStageController(kunde);
                    authStage.close();
                    break;
                case NutzerVerwalten.NutzerTyp.EINKAEUFER:
                    Einkaeufer einkaeufer = (Einkaeufer) nutzerVerwalten.getLoggedIn();
                    EinkaeuferStageController einkaeuferStage = new EinkaeuferStageController(einkaeufer);
                    authStage.close();
                    break;
                case NutzerVerwalten.NutzerTyp.LIEFERANT:
                    Lieferant lieferant = (Lieferant) nutzerVerwalten.getLoggedIn();
                    LieferantStageController lieferantStage = new LieferantStageController(lieferant);
                    authStage.close();
                    break;
                case NutzerVerwalten.NutzerTyp.GESCHAEFTSFUEHRER:
                    Geschaeftsfuehrer geschaeftsfuehrer = (Geschaeftsfuehrer) nutzerVerwalten.getLoggedIn();
                    GeschaeftsfuehrerStageController geschaeftsfuehrerStage = new GeschaeftsfuehrerStageController(
                            geschaeftsfuehrer);
                    authStage.close();
                    break;
                default:
                    view.logInFailed();
                    return;
            }
        });

        // Registrieren Button wurde gedrueckt; Navigiert zu der RegistrierenView
        logIn.getRegisterButton().setOnAction((event) -> {
            authStage.switchToRegistrierenView();
        });
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return LogInView
     */
    public LogInView getView() {
        return view;
    }
}
