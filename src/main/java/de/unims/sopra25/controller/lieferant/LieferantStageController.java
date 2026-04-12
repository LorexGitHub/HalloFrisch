package de.unims.sopra25.controller.lieferant;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Lieferant;
import de.unims.sopra25.model.logik.K_BestellungenVerwalten;
import de.unims.sopra25.model.logik.Serialisierung;
import javafx.scene.Scene;
import de.unims.sopra25.view.lieferant.BestellungBoxView;
import de.unims.sopra25.view.lieferant.BestellungDetailView;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Steuert Stage des Lieferanten
 * @author Irina Hoppe, Pia Maitzen
 */
public class LieferantStageController {
	
	// Fenster Groesse
	private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_WIDTH = 1000;

	// Eingeloggter Lieferant
	private Lieferant lieferant;

	// BestellungsVerwalter
	protected K_BestellungenVerwalten verwalter;

    // Stage, die angezeigt wird
	private final Stage stage; 

	// Scene die auf der Stage ist
	private Scene scene;
	private BorderPane lieferantenView;

	// Controller, der die Unterseite für Top lädt
	private NavBarController navBarController;

	// Controller, der die Unterseiten für Center laden
	private BestellungUebersichtController bestellungUebersichtController;
	private BestellungDetailController bestellungDetailController;

	/**
	 * Konstruktor, der das Standard-Frame (Bestellungübersicht) startet 
	 * @param lieferant eingeloggter Lieferant
	 */
	public LieferantStageController (Lieferant lieferant) {
		this.lieferant = lieferant;
		this.verwalter = new K_BestellungenVerwalten();

		// Initialisiere Controller
		bestellungUebersichtController = new BestellungUebersichtController(this, lieferant, verwalter);
        navBarController = new NavBarController(this, bestellungUebersichtController);

		// Stage und Scene initialisieren
		lieferantenView = new BorderPane();
		stage = new Stage(); 
		stage.setHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
		scene = new Scene(lieferantenView);
		scene.getStylesheets().add("styling1.css");
		lieferantenView.setTop(this.navBarController.getView());

		// Default Page
		switchToBestellungUebersichtView();
        controlStageClose();
	}

	/**
	 * zeigt Bestellungsuebersicht auf Lieferantenstage
	 * default View des Lieferanten
	 */
	public void switchToBestellungUebersichtView () {
		//lade neue Scene
		ListView<BestellungBoxView> bestellungUebersichtView = bestellungUebersichtController.getView();
        bestellungUebersichtView.getSelectionModel().clearSelection();

		lieferantenView.setCenter(bestellungUebersichtController.getView());

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * zeigt Bestellungdetailansicht auf Lieferantenstage
	 * @param bestellung Bestellungs von der die Detail view gezeigt werden soll
	 */
	public void switchToBestellungDetailView(K_Bestellung bestellung, BestellungBoxView boxView) {
		bestellungDetailController = new BestellungDetailController(this, bestellung, boxView);
		BestellungDetailView bestellungDetailView = bestellungDetailController.getView();

		lieferantenView.setCenter(bestellungDetailController.getView());

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Überwacht das schliessen des Fensters und Serializiert die Daten
	 */
    protected void controlStageClose() {
        stage.setOnCloseRequest(event -> {
            Serialisierung ser = Serialisierung.getSerialisierung();
            ser.write();
        });
    }

	/**
	 * schließt die Lieferantenstage
	 */
	public void close() {
		stage.close();
        Serialisierung ser = Serialisierung.getSerialisierung();
		ser.write();
	}

}
