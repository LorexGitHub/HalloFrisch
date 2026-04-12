package de.unims.sopra25.controller.authentication;

import de.unims.sopra25.view.authentication.AGBView;

/**
 * Klasse welche das AGB-Popup kontrolliert
 * 
 * @author Frederik Flügge
 */
public class AGBController {

    // Stage des Controllers
    private AuthenticationStageController authStage;

    // View des Controllers
    private AGBView view;

    /**
     * Konstruktor fuer den AGB-Popup controller
     * Initialisiert die view, attribute und Event Handler des Controllers
     * 
     * @param authStage Stage des Controllers
     */
    public AGBController(AuthenticationStageController authStage) {
        this.authStage = authStage;

        this.view = new AGBView();

        initEventHandlers(view);
    }

    /**
     * Erstellt alle benoetigten EventHandler
     */
    private void initEventHandlers(AGBView agbView) {

        // Schiessen Button wurde gedrueckt; Schließt die AGB View
        agbView.getSchliessenButton().setOnAction((e) -> {
            authStage.getAGBPopupStage().hide();
            e.consume();
        });

    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return AGBView
     */
    public AGBView getView() {
        return view;
    }

}
