package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.model.entitaeten.Einkaeufer;
import de.unims.sopra25.model.logik.E_BestellungenVerwalten;
import de.unims.sopra25.model.logik.KategorienVerwalten;
import de.unims.sopra25.model.logik.Serialisierung;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Klasse die fuer das Packet view.einkaeufer die JavaFX stage kontrolliert
 * enthaelt stage, scene und Grenzklasse aus dem Package, sowie dazugehoerige
 * Steuerungklassen
 * 
 * @author Frederik Flügge
 * @author Lukas Tilgner
 */
public class EinkaeuferStageController {

    // Feste Fenster Groesse
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_WIDTH = 1000;

    // Eingeloggter Einkäufer
    private final Einkaeufer einkaeufer;

    // Stage die Angezeigt
    private final Stage stage;

    // Scene die auf der Stage ist
    private Scene scene;
    private BorderPane view;

    // Controller, die die Unterseiten für Top und Left laden
    private NavBarController navBarController;
    private SideMenuController sideMenuController;

    // Controller, die die Unterseiten für Center laden
    private ProduktUebersichtController produktUebersichtController;
    private NachbestellungUebersichtController nachbestellungController;

    // Logik für die Bestellungen des Einkäufers
    private E_BestellungenVerwalten verwalter;

    /**
     * Konstruktor, der das Standard-Frame (Produktübersicht) startet
     * 
     * @param einkaeufer eingeloggter einkaeufer
     */
    public EinkaeuferStageController(Einkaeufer einkaeufer) {
        this.einkaeufer = einkaeufer;
        this.verwalter = new E_BestellungenVerwalten();

        // Controller initialisieren
        this.produktUebersichtController = new ProduktUebersichtController(this);
        this.navBarController = new NavBarController(this, produktUebersichtController);
        this.sideMenuController = new SideMenuController(this);
        this.nachbestellungController = new NachbestellungUebersichtController(this, verwalter);

        // Szene und Stage initialisieren
        this.view = new BorderPane();
        this.stage = new Stage();
        this.scene = new Scene(view);
        view.setTop(navBarController.getView());
        view.setLeft(sideMenuController.getView());
        stage.setResizable(false);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        scene.getStylesheets().add("styling1.css");
        
        // Default-Scene
        switchToProduktUebersicht();
        controlStageClose();
    }

    /**
     * Setzt die Szene der Stage zu ProduktUebersicht
     */
    public void switchToProduktUebersicht() {
        produktUebersichtController.aktualisiere();
        view.setCenter(produktUebersichtController.getView());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Setzt die Szene der Stage zu NachbestellungUebersicht
     */
    public void switchToNachbestellungUeberischt() {
        nachbestellungController = new NachbestellungUebersichtController(this, verwalter);
        
        view.setCenter(nachbestellungController.getView());
        stage.setScene(scene);
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
    protected void controlStageClose() {

        stage.setOnCloseRequest(event -> {
            Serialisierung ser = Serialisierung.getSerialisierung();
            ser.write();
        });
    }
}
