package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.view.einkaeufer.SideMenuView;

/**
 * Kontrolliert ein SideMenuView
 */
public class SideMenuController {

    // Stage des Controllers
    private EinkaeuferStageController einkaeuferStage;

    // View des Controllers
    private SideMenuView view;

    /**
     * Konstruktor fuer den SideMenuController
     * Initialisiert die View, stage und die Event Handler des Controllers
     * 
     * @param einkaeuferStage Stage des Controllers
     */
    public SideMenuController(EinkaeuferStageController einkaeuferStage) {
        this.einkaeuferStage = einkaeuferStage;

        this.view = new SideMenuView();

        initializeEventHandler();
    }

    /**
     * Initialisiert die Event-Handler, um zwischen den Views wechseln zu können.
     */
    private void initializeEventHandler() {

        // Nachbestellung Button wurde gedrueckt; Navigiert zu den Nachbestellungen
        view.getNachbestellungButton().setOnAction(event -> {
            einkaeuferStage.switchToNachbestellungUeberischt();
        });

        // ProduktUebersichtButton wurde gedrueck; Navigiert zu der ProduktUebersicht
        view.getProduktUebersichtButton().setOnAction((event) -> {
            einkaeuferStage.switchToProduktUebersicht();
        });
    }


    /**
     * Gibt die View des Controllers zurueck
     *
     * @return SideMenuView
     */
    public SideMenuView getView() {
        return view;
    }


}
