package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.geschaeftsfuehrer.ProduktDetailView;

/**
 * Klasse, die die ProduktDetail Anzeige kontrolliert
 * 
 * @author Lukas Tilgner, Frederik Fluegge
 */
public class ProduktDetailController {

	// View des Controllers
	private final ProduktDetailView view;

	// Anzuzeigendes Produkt
	private final Produkt produkt;

	/**
	 * Konstruktor fuer den ProduktDetailController
	 * Initilisiert die View sowie das Produkt des Controllers
	 *
	 * @param produkt anzuzeigendes Produkt
	 */
	public ProduktDetailController(Produkt produkt) {
		this.produkt = produkt;

		this.view = new ProduktDetailView(produkt);
	}

	/**
	 * Getter für die View
	 *
	 * @return view produktDetailView
	 */
	public ProduktDetailView getView() {
		return view;
	}
}
