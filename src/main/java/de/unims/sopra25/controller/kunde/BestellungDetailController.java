package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.kunde.BestellungDetailView;

/**
 * Klasse, die die BestellungDetail Ansicht kontrolliert
 * 
 * @author Constantin Meininghaus
 */
public class BestellungDetailController {

    // Stage des Controllers
    private final KundeStageController kundeStage;

    // View des Controllers
    private BestellungDetailView view;

    /**
     * Konstruktor fuer den BestellungDetailController 
     * Initialisiert die view des Controllers und plottet die Produkte der zu betrachtenden Bestellung
     * 
     * @param kundeStage Stage des Controllers
     * @param bestellung Aktuell betrachtete Bestellung
     */
    public BestellungDetailController(KundeStageController kundeStage, K_Bestellung bestellung) {
        this.kundeStage = kundeStage;

        kundeStage.setBestellungBottomBar(bestellung);
        view = new BestellungDetailView(bestellung);
    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return BestellungDetailView
     */
    public BestellungDetailView getView() {
        return view;
    }

    /**
     * Gibt die Stage des Controllers zurueck
     *
     * @return KundeStageController
     */
    public KundeStageController getKundenStage() {
        return kundeStage;
    }
}
