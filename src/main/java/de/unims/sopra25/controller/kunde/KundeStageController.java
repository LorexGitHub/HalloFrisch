package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.K_BestellungenVerwalten;
import de.unims.sopra25.model.logik.Serialisierung;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Klasse KundeStage managed die Stage Kunde
 * 
 * @author Frederik Flügge
 */
public class KundeStageController {

    // Fenster Groesse
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_WIDTH = 1000;

    // Eingeloggter Kunde
    private Kunde kunde;

    // Logik für die Bestellungen des Kundens
    protected K_BestellungenVerwalten verwalter;

    // Stage, die angezeigt wird
    private final Stage stage;

    // Scene die auf der Stage ist
    private Scene scene;
    private BorderPane kundeView;

    // Controller, die die Unterseiten für Top und Bottom laden
    private NavBarController navBarController;
    private WarenkorbBottomBarController warenkorbBottomBarController;
    private BestellungBottomBarController bestellungBottomBarController;

    // Controller, die die Unterseiten für Center laden
    private BestellungDetailController bestellungDetailController;
    private BestellungUebersichtController bestellungUebersichtController;
    private BestellungBottomBarController bottomBarController;
    private ProduktDetailController produktDetailController;
    private ProduktUebersichtController produktUebersichtController;
    private ProfilBearbeitenController profilBearbeitenController;
    private ProfilController profilController;
    private WarenkorbUebersichtController warenkorbController;

    /**
     * Konstruktor, der das Standard-Frame (Produktübersicht) startet
     * 
     * @param kunde eingeloggter Kunde
     */
    public KundeStageController(Kunde kunde) {
        this.kunde = kunde;
        verwalter = new K_BestellungenVerwalten();

        // Controller Initialisieren
        produktUebersichtController = new ProduktUebersichtController(this, kunde);
        navBarController = new NavBarController(this, produktUebersichtController);
        bestellungUebersichtController = new BestellungUebersichtController(this, kunde, verwalter);
        profilController = new ProfilController(this, kunde);
        profilBearbeitenController = new ProfilBearbeitenController(this, kunde);

        // Stage und Scene initialisieren
        kundeView = new BorderPane();
        stage = new Stage();
        stage.setHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        stage.setResizable(false);
        scene = new Scene(kundeView);
        kundeView.setTop(navBarController.getView());
        scene.getStylesheets().add("styling1.css");

        // Default Page
        switchToProduktUebersichtView();
        controlStageClose();
    }

    /**
     * Wechselt Szene zu Produktuebersicht
     */
    public void switchToProduktUebersichtView() {

        kundeView.setCenter(produktUebersichtController.getView());
        kundeView.setBottom(null);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechselt Szene zu Warenkorbansicht
     */
    public void switchToWarenkorbView() {
        warenkorbBottomBarController = new WarenkorbBottomBarController(kunde.getWarenkorb(), this);
        warenkorbController = new WarenkorbUebersichtController(this, kunde, warenkorbBottomBarController);

        kundeView.setBottom(warenkorbBottomBarController.getView());
        kundeView.setCenter(warenkorbController.getView());

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Wechselt zur ProduktDetailScene
     * 
     * @param produkt anzuzeigendes Produkt
     */
    public void switchToProduktDetailView(Produkt produkt) {
        produktDetailController = new ProduktDetailController(kunde, produkt, this);

        kundeView.setCenter(produktDetailController.getView());
        kundeView.setBottom(null);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechselt Szene zu Profilansicht
     */
    public void switchToProfilView() {

        kundeView.setCenter(profilController.getView());
        kundeView.setBottom(null);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechselt Szene zu BestellungDetailView
     * 
     * @param bestellung ist die Angeklickte Betsellung die gezeigt werden soll
     */
    public void switchToBestellungDetailView(K_Bestellung bestellung) {
        bestellungDetailController = new BestellungDetailController(this, bestellung);
        bestellungBottomBarController = new BestellungBottomBarController(bestellung);

        kundeView.setCenter(bestellungDetailController.getView());
        kundeView.setBottom(bestellungBottomBarController.getView());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechselt zu ProfilBearbeitenAnsicht
     */
    public void switchToProfilBearbeitenView() {

        kundeView.setCenter(profilBearbeitenController.getView());
        kundeView.setBottom(null);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wechsle zur BestellungUebersichtView des Kunden
     */
    public void switchToBestellungUebersichtView() {
        bestellungUebersichtController = new BestellungUebersichtController(this, kunde, verwalter);

        kundeView.setCenter(bestellungUebersichtController.getView());
        kundeView.setBottom(null);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * gibt den Bestellunguebersichtcontroller zurueck, damit der
     * bestellungboxcontroller darauf zugreifen kann
     * 
     * @return bestellungUebersichtController
     */
    public BestellungUebersichtController getBestellungUebersichtController() {
        return bestellungUebersichtController;
    }

    /**
     * setzt die BestellungBottomBar
     * wird von der BestellungsUebersicht aufgerufen
     * 
     * @param bestellung
     */
    public void setBestellungBottomBar(K_Bestellung bestellung) {
        bottomBarController = new BestellungBottomBarController(bestellung);
        kundeView.setBottom(bottomBarController.getView());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Reloads the Navbar
     */
    public void reloadNavBar() {
        NavBarController nbc = new NavBarController(this, produktUebersichtController);
        kundeView.setTop(nbc.getView());
    }

    /**
     * Methode, die die Daten serialisiert, wenn das Programm geschlossen wird
     */
    void controlStageClose() {
        stage.setOnCloseRequest(event -> {
            Serialisierung ser = Serialisierung.getSerialisierung();
            ser.write();
        });
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
     * Gibt den eingeloggten Kunden zurueck
     * 
     * @return Kunde
     */
    public Kunde getKunde() {
        return kunde;
    }
}
