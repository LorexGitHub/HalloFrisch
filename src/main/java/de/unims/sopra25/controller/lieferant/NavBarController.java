package de.unims.sopra25.controller.lieferant;

import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.lieferant.NavBarView;

/**
 * Klasse, die die NavBar Ansicht kontrolliert
 */
public class NavBarController {

    // View des Controllers
    private NavBarView navBarView;

    // Stage des Controllers
    private LieferantStageController lieferantenStage;

    // Controller der Bestelluebersicht, auf welchem
    // der Filter angewendet wird
    private BestellungUebersichtController filterController;

    /**
     * Konstruktor fuer den NavBarController
     * Initialisiert die view, stage und Event Handler des Controllers
     * 
     * @param lieferantenStage Stage des Lieferaten
     */
    public NavBarController(LieferantStageController lieferantenStage,
            BestellungUebersichtController uebersichtController) {
        this.lieferantenStage = lieferantenStage;
        this.filterController = uebersichtController;

        navBarView = new NavBarView(K_Bestellung.getStatusList());

        initializeEventHandler(navBarView);
    }

    /**
     * Intitialisiert die Event Handler
     * 
     * @param view view des Controllers
     */
    private void initializeEventHandler(NavBarView view) {

        // Logo Button wurde gedrueckt; Navigiert zur Bestellungubersicht
        navBarView.getLogoButton().setOnAction(event -> {
            lieferantenStage.switchToBestellungUebersichtView();
        });

        // Log-Out Button wurde gedrueckt; Loggt den Lieferanten aus
        navBarView.getLogOutButton().setOnAction(event -> {
            AuthenticationStageController authenticationStageController = new AuthenticationStageController();
            lieferantenStage.close();
        });
        //Filter-Logik
        navBarView.getFilterBox().setOnAction(actionEvent -> {
            filterController.setFilter(navBarView.getFilterBox().getSelectionModel().getSelectedItem());
        });
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return NavBarView
     */
    public NavBarView getView() {
        return navBarView;
    }
}
