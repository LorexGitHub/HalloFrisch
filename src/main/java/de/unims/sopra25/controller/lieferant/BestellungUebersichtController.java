package de.unims.sopra25.controller.lieferant;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Lieferant;
import de.unims.sopra25.model.logik.K_BestellungenVerwalten;
import de.unims.sopra25.model.logik.ListeFiltern;
import de.unims.sopra25.view.lieferant.BestellungBoxView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

/**
 * Beobachtet ein Fenster der BestellungUebersicht
 * @author Irina Hoppe, Pia Maitzen, Johannes Schick
 */
public class BestellungUebersichtController {

	// View des Controllers
	private ListView<BestellungBoxView> view;

	// Stage des Controllers
	private LieferantStageController stage;

	// Logik des Controllers
	private K_BestellungenVerwalten verwalter;

	// Eingeloggter Lieferant
	private Lieferant lieferant;

	// Liste mit allen Bestellungen fuer den Lieferanten
	private ObservableList<BestellungBoxView> bestellungsTabelle;
	
    // Gefilterte Liste
    private FilteredList<BestellungBoxView> filteredList;

	/**
	 * Konstruktor der Klasse BestellungUebersichtController
	 * @param stage fuer den Lieferanten
	 * @param lieferant aktuell eingeloggter Lieferant
	 * @param verwalter der K_Bestellungen
	 */
	public BestellungUebersichtController (LieferantStageController stage, Lieferant lieferant, K_BestellungenVerwalten verwalter) {
		this.stage = stage;
		this.lieferant = lieferant;
		this.verwalter = verwalter;
		this.verwalter = new K_BestellungenVerwalten();

		//alle K_Bestellungen des Lieferanten
		ObservableList<K_Bestellung> lieferantBestellungen = FXCollections.observableList(lieferant.getBestellungen());
		//alle offenen K_Bestellungen
		ObservableList<K_Bestellung> offeneBestellungen = verwalter.getOffeneBestellungen();
		//alle K_Bestellungen, die dem Lieferanten angezeigt werden
		ObservableList<K_Bestellung> bestellungen = FXCollections.concat(lieferantBestellungen, offeneBestellungen);

		initialisiereView(bestellungen, lieferant);
        initialisiereEventHandler();

	}

	/**
	 * Initialisiere Tabelle mit Bestellungsboxen des Lieferanten
	 * @param bestellungen
	 * @param lieferant
	 */
	public void initialisiereView(ObservableList<K_Bestellung> bestellungen, Lieferant lieferant) {
		bestellungsTabelle = FXCollections.observableArrayList();
		// erzeuge HBox mit gewuenschten Bestellungsdaten je Bestellungen
		bestellungen.forEach(bestellung -> {
			BestellungBoxController boxController = new BestellungBoxController(stage, lieferant, bestellung, this);
			bestellungsTabelle.add(boxController.getView());
		});
        filteredList = new FilteredList<>(bestellungsTabelle);
        this.view = new ListView<>(filteredList);
	}
    //Öffne
    private void initialisiereEventHandler(){
        view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Button button = view.getSelectionModel().getSelectedItem().getNummerButton();
                button.arm();
                button.fire();
            }
        });
    }

    /**
     * Aktualisiert, ob Bestellungen angenommen werden können.
     */
    public void aktualisiereErfuellbarkeit(){
        for(BestellungBoxView boxView: bestellungsTabelle){
            boxView.erfuellbar();
        }
    }

    /**
     * Setzt den Filter.
     * @param text String-Repräsentation eines K_Bestellung-Status
     *             oder "Alle" oder "Erfüllbar"
     */
    public void setFilter(String text){
        final K_Bestellung.Status status;
        final boolean bool;
        // "Alle": Filter zurücksetzen
        if(text.equals("Alle")){
            status= null;
            bool= false;
        }
        // "Erfüllbar": Nur erfüllbare Bestellungen
        else if(text.equals("Erfüllbar")){
            status = null;
            bool= true;
        }
        // Sonst: Nur Bestellungen des angegebenen Status.
        else {
            bool = false;
            status= K_Bestellung.Status.valueOf(text.toUpperCase());
        }
        filteredList.setPredicate(boxView -> ListeFiltern.bestellungSuche(boxView.getBestellung(), status, bool));
    }

	/**
	 * Gibt die view des Controllers zurueck
	 * @return view BestellungUebersichtView
	 */
	public ListView<BestellungBoxView> getView() {
		return view;
	}
}
