package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.controller.shared.ProduktUebersichtControllerVorlage;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.ListeFiltern;
import de.unims.sopra25.view.geschaeftsfuehrer.ProduktBoxView;

/**
 * Die Klasse, die die ProduktUebersicht kontrolliert
 * 
 * @author Atilla Alaoglu, Frederik Fluegge, Irina Hoppe, Pia Maitzen, Johannes
 *         Schick
 */
public class ProduktUebersichtController extends ProduktUebersichtControllerVorlage<ProduktBoxView> {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	/**
	 * Konstruktor der Klasse ProduktUebersichtController
	 * 
	 * @param stage
	 */
	public ProduktUebersichtController(GeschaeftsfuehrerStageController stage) {
		super();
		this.stage = stage;
		initialisiereProduktBoxen();
	}

	/**
	 * Methode zum Hinzufuegen eines neuen Produktes.
	 * 
	 * @param produkt neues Produkt
	 */
	public void addProdukt(Produkt produkt) {
		addProduktBoxToProduktTabelle(produkt);
	}

	/**
	 * Methode um produktBox zur produktTabelle hunzuzufuegen
	 * 
	 * @param produkt
	 */
	@Override
	protected void addProduktBoxToProduktTabelle(Produkt produkt) {
		ProduktBoxController produktBoxController = new ProduktBoxController(produkt, this);
		produktBoxen.add(produktBoxController.getView());
	}

	/**
	 * Suche Methode
	 * 
	 * @param text
	 */
	@Override
	public void suche(String text) {
		filteredProduktBoxen.setPredicate(boxView -> ListeFiltern.produktSuche(boxView.getProdukt(), text, kategorie));
	}

	/**
	 * Methode zur Anzeige der view
	 */
	@Override
	public void zeigeDieseView() {
		stage.switchToProduktUebersichtView();
	}

	/**
	 * Methode um produktBox aus einer produktTabelle zu entfernen
	 * 
	 * @param produktBoxController
	 */
	public void removeProduktBoxFromProduktTabelle(ProduktBoxController produktBoxController) {
		produktBoxen.remove(produktBoxController.getView());
	}

	/**
	 * Gibt die Stage des Controllers zurueck
	 * 
	 * @return stage GeschaeftsfuehrerStageController
	 */
	public GeschaeftsfuehrerStageController getStage() {
		return stage;
	}
}
