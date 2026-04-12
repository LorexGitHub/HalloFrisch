package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.controller.shared.ProduktUebersichtControllerVorlage;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.BestsellerStatistik;
import de.unims.sopra25.model.logik.ListeFiltern;
import de.unims.sopra25.view.geschaeftsfuehrer.BestsellerBoxView;
import javafx.collections.FXCollections;

/**
 * Klasse, die die BestsellerView des Geschaeftsfuehrers steuert
 *
 * @author Leonhard Heß, Lukas Tilgner, Johannes Schick
 */
public class BestsellerController extends ProduktUebersichtControllerVorlage<BestsellerBoxView> {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	// Statistik
	private BestsellerStatistik bestsellerStatistik;

	/**
	 * Konstruktor für BestesellerController
	 * Erstellt eine neue BestsellerStatistik
	 *
	 * @param stage GeschaeftsfuehererStageController wird uebergeben
	 */
	public BestsellerController(GeschaeftsfuehrerStageController stage) {
		this.stage = stage;
		this.bestsellerStatistik = new BestsellerStatistik();
		//Erstelle Produktboxen
		this.produkte = FXCollections.observableArrayList(bestsellerStatistik.getBestseller());
		initialisiereProduktBoxen();
	}

	/**
	 * Durchsucht einen String
	 * @param text
	 */
	@Override
	public void suche(String text){
		filteredProduktBoxen.setPredicate(boxView -> ListeFiltern.produktSuche(boxView.getProdukt(), text, kategorie));
	}

	/**
	 * Wechselt zu BestsellerView
	 */
	@Override
	public void zeigeDieseView() {
		stage.switchToBestsellerView();
	}

	/**
	 * Fuegt ein Produkt der Produkttabelle hinzu
	 * @param produkt
	 */
	@Override
	protected void addProduktBoxToProduktTabelle(Produkt produkt) {
		BestsellerBoxView box = new BestsellerBoxView(produkt);
		box.getVerkauftAnzahl().setText(String.valueOf(bestsellerStatistik.getVerkaeufe(produkt)));
		produktBoxen.add(box);
	}
}
