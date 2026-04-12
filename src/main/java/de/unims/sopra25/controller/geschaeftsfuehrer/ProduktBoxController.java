package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.geschaeftsfuehrer.ProduktBoxView;

/**
 * Klasse, die die produktBox Anzeige kontrolliert
 * 
 * @author Lukas Tilgner, Frederik Fluegge
 */
public class ProduktBoxController {

	// View des Controllers
	private ProduktBoxView view;

	// Anzuzeigendes Produkt
	private Produkt produkt;

	// Produktuebersicht des Ccntrollers
	private ProduktUebersichtController controller;

	/**
	 * Konstruktor der Klasse ProduktBoxController
	 * 
	 * @param produkt Anzuzeigendes Produkt
	 * @param controller Produktuebersicht des Ccntrollers
	 */
	public ProduktBoxController(Produkt produkt, ProduktUebersichtController controller) {
		this.controller = controller;
		this.produkt = produkt;

		this.view = new ProduktBoxView(produkt);

		initialisiereEventHandler();
	}

	/**
	 * Initlaisiert die Event Handler
	 */
	private void initialisiereEventHandler() {
		// Wechsel in ProduktDetailView
		view.getBildButton().setOnAction(event -> {
			controller.getStage().switchToProduktDetailView(produkt);
		});

		// Wechsel in ProduktAnpassenlView
		view.getBearbeitenButton().setOnAction(event -> {
			controller.getStage().switchToProduktAnpassenlView(produkt, this);
		});
	}

	/**
	 * Aktualisiert das Produkt, wenn es bearbeitet wurde
	 */
	public void aktualisiereProdukt() {
		view.getPreis().setText(produkt.getPreisString());
	}

	/**
	 * Getter Methode des ProduktBoxController
	 * 
	 * @return ProduktBoxView
	 */
	public ProduktBoxView getView() {
		return this.view;
	}
}
