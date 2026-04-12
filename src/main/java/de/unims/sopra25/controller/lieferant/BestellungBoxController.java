package de.unims.sopra25.controller.lieferant;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_BestellungProduktAssoz;
import de.unims.sopra25.model.entitaeten.Lieferant;
import de.unims.sopra25.view.lieferant.BestellungBoxView;

/**
 * Beobachtet eine BestellungBoxView
 * @author Irina Hoppe, Pia Maitzen
 */
public class BestellungBoxController {

	// Stage des Controllers
	private LieferantStageController stage;
    //Controller der BestellUebersicht
    private BestellungUebersichtController uebersichtController;

	// View des Controllers
	private BestellungBoxView view;

	// Bestellung der Box
	private K_Bestellung bestellung;

	// Eingeloggter Lieferant
	private Lieferant lieferant;

	/**
	 * Konstruktor der Klasse BestellungBoxController
	 * @param stage fuer den Lieferanten
	 * @param lieferant aktuell eingeloggter Lieferant
	 * @param bestellung K_Bestellung, die von BestellungBoxView anzeigt wird
	 */
	public BestellungBoxController (LieferantStageController stage, Lieferant lieferant, K_Bestellung bestellung, BestellungUebersichtController uebersichtController) {
		this.stage = stage;
		this.lieferant = lieferant;
		this.bestellung = bestellung;
		this.view = new BestellungBoxView(bestellung);
        this.uebersichtController= uebersichtController;

		initializeEventHandler();
	}

	/**
	 * initialisiere Eventhandler fuer die Buttons "Annehmen" und "Nummer"
	 */
	public void initializeEventHandler() {
		// Weise aktuellem Lieferanten Bestellung zu
		view.getAnnehmenButton().setOnAction(event -> {
			lieferant.addBestellung(bestellung);
			view.removeAnnehmenButton();
			stage.verwalter.nextStatus(bestellung);
			view.addAusliefernButton();
			view.getStatusLabel().setText(bestellung.getStatus().toString());
			view.getStatusLabel().setStyle("-fx-text-fill: orange");
            uebersichtController.aktualisiereErfuellbarkeit();
		});
		// Wechsel in BestellungDetailView
		view.getNummerButton().setOnAction(event -> {
			stage.switchToBestellungDetailView(bestellung, view);
		});
		view.getAusliefernButton().setOnAction(event -> {
			view.removeAusliefernButton();
			stage.verwalter.nextStatus(bestellung);
			view.getStatusLabel().setText(bestellung.getStatus().toString());
			view.getStatusLabel().setStyle("-fx-text-fill: green");
		});
	}
	/**
	 * Gibt die aktuelle view zurueck
	 * @return view BestellungBoxView
	 */
	public BestellungBoxView getView() {
		return view;
	}
}
