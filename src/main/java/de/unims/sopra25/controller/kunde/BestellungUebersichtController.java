package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.logik.K_BestellungenVerwalten;
import de.unims.sopra25.view.kunde.BestellungBoxView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Klasse, die die BestellungUebersicht Ansicht kontrolliert
 * 
 * @author Frederik Fluegge
 */
public class BestellungUebersichtController {

	// Stage des Controllers
	private KundeStageController kundeStage;

	// View des Controllers
	private ScrollPane view;

	// Eingeloggter Kunde
	private Kunde kunde;

	// Logik zur Verwaltung der Kundenbestellungen
	private K_BestellungenVerwalten verwalter;

	// Vertikale Tabelle mit allen Bestellungen
	private VBox bestellungsTabelle;

	// Ausgewaehlte Bestellungsbox innerhalb der Bestellungstabelle
	private BestellungBoxView selectedBestellung;

	// Alle Bestellungen des Kunden
	private ObservableList<K_Bestellung> bestellungen;

	/**
	 * Konstruktor fuer den BestellungUebersichtController
	 * Initialisiert die View, all attribute und die Tabelle an Bestellungen
	 * 
	 * @param kundeStage fuer den Kunden
	 * @param kunde      aktuell eingeloggter Kunde
	 * @param verwalter  der K_Bestellungen
	 */
	public BestellungUebersichtController(KundeStageController kundeStage, Kunde kunde,
			K_BestellungenVerwalten verwalter) {
		this.kundeStage = kundeStage;
		this.kunde = kunde;
		this.verwalter = verwalter;

		this.bestellungen = FXCollections.observableList(kunde.getBestellungen());
		this.verwalter = new K_BestellungenVerwalten();
		this.view = new ScrollPane();

		initialisiereBestellungsTabelle(bestellungen, kunde);
	}

	/**
	 * Initialisiere Tabelle mit Bestellungsboxen des Kunden
	 * 
	 * @param bestellungen Alle Bestellungen des Kunden
	 */
	public void initialisiereBestellungsTabelle(ObservableList<K_Bestellung> bestellungen, Kunde kunde) {
		bestellungsTabelle = new VBox();
		// Erzeuge HBox mit gewuenschten Bestellungsdaten je Bestellungen
		bestellungen.forEach((bestellung) -> {
			BestellungBoxController boxController = new BestellungBoxController(kundeStage, kunde, bestellung);
			bestellungsTabelle.getChildren().add(boxController.getView());
		});
		view.setContent(bestellungsTabelle);
	}

	/**
	 * Setzt die BottomBar fuer die aktuell ausgewaehlte Bestellung
	 * 
	 * @param newSelected Ausgewaehlte Bestellung
	 */
	public void setNewSelectedBestellung(BestellungBoxView newSelected) {
		if (selectedBestellung != null) {
			selectedBestellung.setStyle("-fx-background-color : #FFFFFF");
		}
		newSelected.setStyle("-fx-background-color :rgba(255, 208, 80, 0.55)");	
		selectedBestellung = newSelected;
		kundeStage.setBestellungBottomBar(selectedBestellung.getBestellung());
	}

	/**
	 * Gibt die View des Controllers zurück
	 * 
	 * @return BestellungUebersichtView
	 */
	public ScrollPane getView() {
		return view;
	}

}