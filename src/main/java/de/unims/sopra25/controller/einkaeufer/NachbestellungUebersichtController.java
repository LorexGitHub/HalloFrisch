package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.model.logik.E_BestellungenVerwalten;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Klasse, die die NachbestellungUebersicht Ansicht kontrolliert
 * 
 * @author David Metzdorf
 */
public class NachbestellungUebersichtController {

	// Stage des Controllers
	private EinkaeuferStageController einkaeugerStage;

	// View des Controllers
	private ScrollPane view;

	// Logik zu Verwaltung der Bestelungen des Einkaeufers
	private E_BestellungenVerwalten verwalter;

	// Liste von Nachbestellungen des Einkaeufers
	private ObservableList<E_Bestellung> bestellungen;
	

	/**
	 * Konstruktor fuer den NachbestellungUeberischtController
	 * Initialisiert die view, alle attribute und die Event Handler des Controllers
	 * 
	 * @param einkaeugerStage Stage des Controllers
	 */
	public NachbestellungUebersichtController(EinkaeuferStageController einkaeugerStage,
			E_BestellungenVerwalten verwalter) {
		this.einkaeugerStage = einkaeugerStage;
		this.verwalter = verwalter;
		this.bestellungen = verwalter.getBestellungen();

		this.view = new ScrollPane();

		initializeNachbestellungTabelle();
	}

	/**
	 * Initialisiert eine Tabelle aus Nachbestellungen
	 */
	private void initializeNachbestellungTabelle() {
		VBox table = new VBox();

		if (!bestellungen.isEmpty()) {
			for (E_Bestellung bestellung : bestellungen) {
				NachbestellungBoxController nachbestellungBoxController = new NachbestellungBoxController(
						einkaeugerStage, bestellung, verwalter);
				table.getChildren().add(nachbestellungBoxController.getView());
			}
			view.setContent(table);
		} else {
			Label label = new Label(
					"Keine Nachbestellungen im System. \n Gib eine Nachbestellung auf, damit diese hier angezeigt wird.");
			table.getChildren().add(label);
			view.setContent(table);
		}
	}

	/**
	 * Gibt die Stage des Controllers zurueck
	 * 
	 * @return Stage des Controllers
	 */
	public EinkaeuferStageController getStageController() {
		return einkaeugerStage;
	}

	/**
	 * Gibt die View des Controllers zurueck
	 * 
	 * @return ScrollPane 
	 */
	public ScrollPane getView() {
		return view;
	}

}
