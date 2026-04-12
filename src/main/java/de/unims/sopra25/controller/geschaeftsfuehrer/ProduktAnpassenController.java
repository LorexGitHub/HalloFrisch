package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.SortimentVerwalten;
import de.unims.sopra25.view.geschaeftsfuehrer.ProduktAnpassenView;

/**
 * Klasse, die die Inputs von ProduktAnpassenView kontrolliert
 * 
 * @author Frederik Fluegge
 */
public class ProduktAnpassenController {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	// View des Controllers
	private ProduktAnpassenView view;

	// Zu Bearbeitendes Produkt
	private Produkt produkt;

	// Zum loeschen eines Produkts aus dem Sortiment
	private SortimentVerwalten sortimentVerwalten;

	// ProduktBoxController des ProduktAnpassenControllers
	private ProduktBoxController produktBoxController;

	// ProduktUebersichtController des ProduktAnpassenControllers
	private ProduktUebersichtController produktUebersichtController;

	/**
	 * Konstruktor der Klasse ProduktAnpassenController
	 * 
	 * @param stage   Stage des Controllers
	 * @param produkt Zu Bearbeitendes Produkt
	 */
	public ProduktAnpassenController(GeschaeftsfuehrerStageController stage, Produkt produkt,
			ProduktBoxController produktBoxController, ProduktUebersichtController produktUebersichtController) {
		this.stage = stage;
		this.produkt = produkt;
		this.produktBoxController = produktBoxController;
		this.produktUebersichtController = produktUebersichtController;

		sortimentVerwalten = new SortimentVerwalten();

		view = new ProduktAnpassenView(produkt);

		initialisiereEventHandler();
	}

	/**
	 * Initialisiert die Event Handler des Bestaetigen und Loeschen Button
	 */
	private void initialisiereEventHandler() {

		// Stellt sicher, dass beim Preis Input Field nur Nummern eingegeben werden koennen
		view.getPreisField().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
				newValue = newValue.replace(',', '.')
						.replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
				view.getPreisField().setText(newValue);
			}
		});

		// Bestaetigen Button wurde gedrueckt
		view.getBestaetigenButton().setOnAction(event -> {
			String neuerPreis = view.getPreisField().getText();

			produkt.setPreis((int) (Float.parseFloat(neuerPreis) * 100));
			produktBoxController.aktualisiereProdukt();
			view.editSuccess();
		});

		// Loeschen Button wurde gedrueckt
		view.getLoeschenButton().setOnAction(event -> {
			view.openPopUp();
		});

		// Zweiter Loeschen Button wurde gedrueckt
		view.getLoeschenBestaetigenButton().setOnAction(event -> {
			view.closePopUp();

			view.getPreisField().setDisable(true);
			view.getBestaetigenButton().setDisable(true);
			view.getLoeschenButton().setDisable(true);

			produktUebersichtController.removeProduktBoxFromProduktTabelle(produktBoxController);
			sortimentVerwalten.deleteProdukt(produkt);

			view.deleteSuccess();
		});

		// Loeschen Abbrechhen Button wurde gedrueckt
		view.getLoeschenAbbrechenButton().setOnAction(event -> {
			view.closePopUp();
		});

		// Zurueck Button wurde gedrueckt
		view.getZurueckButton().setOnAction(event -> {
			stage.switchToProduktUebersichtView();
		});
	}

	/**
	 * Gibt die View des Controllers zurueck
	 * 
	 * @return view ProduktAnpassenView
	 */
	public ProduktAnpassenView getView() {
		return view;
	}
}
