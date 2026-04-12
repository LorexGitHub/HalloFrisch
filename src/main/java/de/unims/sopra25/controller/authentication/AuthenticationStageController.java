package de.unims.sopra25.controller.authentication;

import de.unims.sopra25.model.logik.Serialisierung;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Klasse die fuer das Packet view.authentication die JavaFX stage kontrolliert
 * enthaelt stage, scene und Grenzklasse aus dem Package, sowie dazugehoerige
 * Steuerungklassen
 * 
 * @author Leonhard Heß
 */
public class AuthenticationStageController {

    // Fenster Groesse
    private final int WINDOW_HEIGHT = 500;
    private final int WINDOW_WIDTH = 600;

    // Stage die Angezeigt
    private final Stage stage;
    private final Stage agbPopupStage;

    // Scene die auf der Stage ist
    private Scene scene;
    private Scene agbPopupScene;

    // Steuerungsklassen, die die jeweiligen Grenzklassen erzeugen
    private LogInController logInController;
    private RegistrierenController registrierenController;
    private AGBController agbController;

    /**
     * Konstruktor, der das Standard-Frame (LogIn) startet 
     */
    public AuthenticationStageController() {
        
        // setzte Parameter fuer main stage
        this.stage = new Stage();
        stage.setHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        stage.setTitle("Hallo Frisch");
        stage.setResizable(false);
        stage.getIcons().add(new Image("Logo.png"));

        // setzte Parameter fuer AGB PopUp
        this.agbPopupStage = new Stage();
        agbPopupStage.setHeight(400);
        agbPopupStage.setWidth(400);
        agbPopupStage.setTitle("AGB");
        agbPopupStage.setResizable(false);

        initAGBPopupStage();

        // Default-Scene
        switchToLogInView();
        controlStageClose();
    }

    /**
     * Initialisiert das AGB-Popup
     */
    private void initAGBPopupStage() {
        agbController = new AGBController(this);
        agbPopupScene = new Scene(agbController.getView());

        agbPopupStage.setScene(agbPopupScene);
        agbPopupStage.setOnCloseRequest((event) -> {
            agbPopupStage.hide();
            event.consume();
        });
    }

    /**
     * Wechselt die Stage auf LoginView
     */
    public void switchToLogInView() {
        logInController = new LogInController(this);
        scene = new Scene(logInController.getView());

        stage.setScene(scene);
        scene.getStylesheets().add("styling1.css");

        stage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    /**
     * Wechselt die Stage auf RegistrierenView
     */
    public void switchToRegistrierenView() {
        registrierenController = new RegistrierenController(this);

        scene = new Scene(registrierenController.getView());

        scene.getStylesheets().add("styling1.css");
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    /**
     * Methode, die die Stage schließt und die Daten serialisiert
     */
    public void close() {
        stage.close();
        Serialisierung ser = Serialisierung.getSerialisierung();
        ser.write();
    }

    /**
     * Methode, die die Daten serialisiert, wenn das Programm geschlossen wird
     */
    public void controlStageClose() {

        stage.setOnCloseRequest(event -> {
            Serialisierung ser = Serialisierung.getSerialisierung();
            ser.write();
        });
    }

    /**
     * Gibt die Primaere Stage zurueck.
     * 
     * @return Primaere Stage
     */
    protected Stage getAGBPopupStage() {
        return agbPopupStage;
    }
}
