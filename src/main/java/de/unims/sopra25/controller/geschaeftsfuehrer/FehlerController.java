package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.logik.FehlerVerwalten;
import de.unims.sopra25.view.geschaeftsfuehrer.FehlerView;

/**
 * Klasse, die die Inputs vom FehlerView handhabt
 * 
 * @author Frederik Fluegge
 */
public class FehlerController {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	// View des Controllers
	private FehlerView view;

	// Verwaltung der Fehler
	private FehlerVerwalten fehler;

	/**
	 * Konstruktor 
	 * @param stage Stage des Geschaeftsfuehrers
	 */
	public FehlerController(GeschaeftsfuehrerStageController stage) {
		this.stage = stage;
		fehler = new FehlerVerwalten();
		view = new FehlerView(fehler.getFehlerListeString());
	}

	/**
	 * Gibt die View des FehlerControllers zurueck
	 * 
	 * @return view FehlerView zurueck
	 */
	public FehlerView getView() {
		return view;
	}
}
