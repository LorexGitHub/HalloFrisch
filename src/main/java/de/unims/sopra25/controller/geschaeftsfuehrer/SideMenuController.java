package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.view.geschaeftsfuehrer.SideMenuView;

/**
 * Klasse, die die Stage des SideMenu kontrolliert
 * 
 * @author Atilla Alaoglu
 */
public class SideMenuController {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	// View des Controllers
	private SideMenuView view;

	/**
	 * Konstruktor, der die View initialisiert
	 * 
	 * @param stage
	 */
	public SideMenuController(GeschaeftsfuehrerStageController stage) {
		this.stage = stage;

		view = new SideMenuView();

		initializeEventHandler();
	}

	/**
	 * Die Methode steuert die SideMenuButtons und switch damit die Views im
	 * StageController
	 */
	public void initializeEventHandler() {
		produktButtonEvent();
		umsatzButtonEvent();
		bestsellerButtonEvent();
		nutzungsButtonEvent();
		fehlerLogButtonEvent();
		neuerNutzerButtonEvent();
	}

	/**
	 * Die Methode steuert die ProduktÜbersichtButton und switch damit die Views im
	 * StageController
	 */
	public void produktButtonEvent() {
		view.getProduktUebersichtButton().setOnAction((event) -> {
			stage.switchToProduktUebersichtView();
		});
	}

	/**
	 * Die Methode steuert die UmsatzButton und switch damit die Views im
	 * StageController
	 */
	public void umsatzButtonEvent() {
		view.getUmsatzButton().setOnAction((event) -> {
			stage.switchToUmsatzUebersichtView();
		});
	}

	/**
	 * Die Methode steuert die BestsellerButton und switch damit die Views im
	 * StageController
	 */
	public void bestsellerButtonEvent() {
		view.getBestsellerButton().setOnAction((event) -> {
			stage.switchToBestsellerView();
		});
	}

	/**
	 * Die Methode steuert die NutzungsButton und switch damit die Views im
	 * StageController
	 */
	public void nutzungsButtonEvent() {
		view.getNutzungButton().setOnAction((event) -> {
			stage.switchToNutzungsstatistikView();
		});
	}

	/**
	 * Die Methode steuert die FehlerLogButton und switch damit die Views im
	 * StageController
	 */
	public void fehlerLogButtonEvent() {
		view.getFehlerLogsButton().setOnAction((event) -> {
			stage.switchToFehlerView();
		});
	}

	/**
	 * Die Methode steuert die RegistrierenButton und switch damit die Views im
	 * StageController
	 */
	public void neuerNutzerButtonEvent() {
		view.getRegistrierenButton().setOnAction((event) -> {
			stage.switchToNutzerRegistrierenView();
		});
	}

	/**
	 * Gibt die SideMenuView zurueck
	 * 
	 * @return view SideMenuView
	 */
	public SideMenuView getView() {
		return view;
	}
}
