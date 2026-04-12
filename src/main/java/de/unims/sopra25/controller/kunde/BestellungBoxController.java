package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.view.kunde.BestellungBoxView;

public class BestellungBoxController {

	// Stage des Controllers
	private KundeStageController kundeStage;

	// View des Controllers
	private BestellungBoxView view;

	// Kundenbestellung der Box
	private K_Bestellung bestellung;

	// Eingeloggter Kunde
	private Kunde kunde;

	/**
	 * Konstruktor des BestellungBoxController
	 * Initialisiert die view, alle attribute und die Event Handler des Controllers
	 * 
	 * @param kundeStage Stage des Controllers
	 * @param kunde      Aktuell eingeloggter Kunden
	 * @param bestellung Kundenbestellung, die von BestellungBoxView anzeigt wird
	 */
	public BestellungBoxController(KundeStageController kundeStage, Kunde kunde, K_Bestellung bestellung) {
		this.kundeStage = kundeStage;
		this.kunde = kunde;
		this.bestellung = bestellung;

		this.view = new BestellungBoxView(bestellung);

		initializeEventHandler();
	}

	/**
	 * Initialisiert alle Event Handler
	 */
	public void initializeEventHandler() {

		// Nummer auf der Bestellung wurde gedureckt; Navigiert in die BestellungsDetailView
		view.getNummerButton().setOnAction(event -> {
			kundeStage.switchToBestellungDetailView(bestellung);
		});

		// Bestellung wird angeglickt; Bottom Bar wird geoeffnet
		view.setOnMouseClicked(event -> {
			kundeStage.getBestellungUebersichtController().setNewSelectedBestellung(view);
		});

	}

	/**
	 * Gibt die View des Controllers zurueck
	 * 
	 * @return BestellungBoxView
	 */
	public BestellungBoxView getView() {
		return view;
	}

}
