package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.logik.NutzungsStatistik;
import de.unims.sopra25.view.geschaeftsfuehrer.NutzungsStatistikView;

/**
 * Klasse, die die Inputs von NutzungsstatikstikView kontrolliert
 */
public class NutzungsstatistikController {
	
    // Stage des Controllers
    private GeschaeftsfuehrerStageController stage;
    
    // View des Controllers
    private NutzungsStatistikView view;
    
    // Statistik
    private NutzungsStatistik statistik;

    /**
     * Konstruktor, der die View erzeugt
     * 
     * @param stage
     */
    public NutzungsstatistikController(GeschaeftsfuehrerStageController stage) {
        this.stage = stage;

        // Holt sich die Statistik aus dem Backend
        statistik = new NutzungsStatistik();

        view = new NutzungsStatistikView(statistik.getAnzahlKunden(), statistik.getAnzahlGeschaeftsfuehrer(),
                statistik.getAnzahlAuslieferer(), statistik.getAnzahlEinkaeufer());
    }

    /**
     * Getter Methode fuer die View des Controllers
     * 
     * @return view NutzungsStatistikView
     */
    public NutzungsStatistikView getView() {
        return view;
    }
}
