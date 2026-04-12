package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Geschaeftsfuehrer;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.Serialisierung;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Klasse, die die Stage des Geschaeftsfuehrers handhabt
 *
 * @author Frederik Fluegge, Atilla Alaoglu
 */
public class GeschaeftsfuehrerStageController {

	// Stage des Controllers
	private final Stage stage;

	// Eingeloggter Geschaeftsfuehrer
	private final Geschaeftsfuehrer geschaeftsfuehrer;

	// Scene die auf der Stage ist
	private final Scene scene;
	private final BorderPane geschaeftsfuehrerView;

	// Fenster Groesse
	private final int WINDOW_HEIGHT = 800;
	private final int WINDOW_WIDTH = 1000;

	// Controller, die die Unterseiten für Top, Left und Right laden
	private final NavBarController navBarController;
	private final SideMenuController sideMenuController;
	private ProduktHinzufuegenController produktHinzufuegenController;

	// Controller, die die Unterseiten für Center laden
	private UmsatzController umsatzController;
	private FehlerController fehlerController;
	private NutzerRegistrierenController nutzerRegistrierenController;
	private NutzungsstatistikController nutzungsstatistikController;
	private BestsellerController bestsellerController;
	private ProduktUebersichtController produktUebersichtController;
	private ProduktDetailController produktDetailController;
	private ProduktAnpassenController produktAnpassenController;

	/**
	 * Konstruktor, der das Standard-Frame (Produktuebersicht) startet
	 *
	 * @param geschaeftsfuehrer eingeloggter Geschäftsführer
	 */
	public GeschaeftsfuehrerStageController(Geschaeftsfuehrer geschaeftsfuehrer) {
		this.geschaeftsfuehrer = geschaeftsfuehrer;

		// Controller initialisieren
		this.produktUebersichtController = new ProduktUebersichtController(this);
		this.sideMenuController = new SideMenuController(this);
		this.navBarController = new NavBarController(this, produktUebersichtController);
		this.umsatzController = new UmsatzController(this);
		this.nutzerRegistrierenController = new NutzerRegistrierenController(this);
		this.bestsellerController = new BestsellerController(this);

		// Szene und Stage initialisieren
		geschaeftsfuehrerView = new BorderPane();
		stage = new Stage();
		scene = new Scene(geschaeftsfuehrerView);
		geschaeftsfuehrerView.setTop(navBarController.getView());
		geschaeftsfuehrerView.setLeft(sideMenuController.getView());
		stage.setHeight(WINDOW_HEIGHT);
		stage.setWidth(WINDOW_WIDTH);
		stage.setResizable(false);
		scene.getStylesheets().add("styling1.css");

		switchToProduktUebersichtView();
		controlStageClose();
	}

	/**
	 * Setzt die Scene von der Stage zu FehlerView
	 */
	public void switchToFehlerView() {
		fehlerController = new FehlerController(this);

		geschaeftsfuehrerView.setCenter(fehlerController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu NutzerRegistrierenView
	 */
	public void switchToNutzerRegistrierenView() {

		geschaeftsfuehrerView.setCenter(nutzerRegistrierenController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu NutzungsStatistikView
	 */
	public void switchToNutzungsstatistikView() {
		nutzungsstatistikController = new NutzungsstatistikController(this);

		geschaeftsfuehrerView.setCenter(nutzungsstatistikController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu ProduktDetailView
	 * 
	 * @param produkt
	 */
	public void switchToProduktDetailView(Produkt produkt) {
		produktDetailController = new ProduktDetailController(produkt);

		geschaeftsfuehrerView.setCenter(produktDetailController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu ProduktAnpassenlView
	 * 
	 * @param produkt, produktBoxController
	 */
	public void switchToProduktAnpassenlView(Produkt produkt, ProduktBoxController produktBoxController) {
		produktAnpassenController = new ProduktAnpassenController(this, produkt, produktBoxController,
				produktUebersichtController);

		geschaeftsfuehrerView.setCenter(produktAnpassenController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu ProduktUebersichtView
	 */
	public void switchToProduktUebersichtView() {

		produktHinzufuegenController = new ProduktHinzufuegenController(this, produktUebersichtController,
				navBarController);

		navBarController.setSearchViewController(produktUebersichtController);

		geschaeftsfuehrerView.setCenter(produktUebersichtController.getView());
		geschaeftsfuehrerView.setRight(produktHinzufuegenController.getView());

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu BestsellerView
	 */
	public void switchToBestsellerView() {

		navBarController.setSearchViewController(bestsellerController);

		geschaeftsfuehrerView.setCenter(bestsellerController.getView());
		geschaeftsfuehrerView.setRight(null);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Setzt die Scene von der Stage zu UmsatzUebersichtView
	 */
	public void switchToUmsatzUebersichtView() {

		geschaeftsfuehrerView.setCenter(umsatzController.getView());
		geschaeftsfuehrerView.setRight(null);

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
