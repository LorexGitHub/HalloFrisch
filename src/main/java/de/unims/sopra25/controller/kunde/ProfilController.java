package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.controller.authentication.LogInController;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.view.kunde.ProfilView;

/**
 * Klasse, die die Profil Ansicht kontrolliert
 * 
 * @author Lukas Tilgner, Constantin Meininghaus
 */
public class ProfilController {

    // View des Controllers
    private ProfilView view;

    // Stage des Controllers
    private KundeStageController kundeStage;

    /**
     * Konstruktor fuer den ProfilController
     * Initialisiert die view, stage und Event Handler des Controllers
     * 
     * @param kundeStage Stage des Controllers
     * @param kunde      eingeloggter Kunde
     */
    public ProfilController(KundeStageController kundeStage, Kunde kunde) {
        this.kundeStage = kundeStage;

        view = new ProfilView();

        initializeEventHandler(view);

    }

    /**
     * Initialisiert die Event Hanlder
     */
    private void initializeEventHandler(ProfilView view) {

        // Profil bearbeiten Button wurde gedrueckt; Navigiert zur Profil bearbeiten Ansicht
        view.getProfilBearbeitenButton().setOnAction((event) -> {
            kundeStage.switchToProfilBearbeitenView();
        });

        // Log-Out Button wurde gedrueckt; Loggt den Kunden auss
        view.getLogOutButton().setOnAction(event -> {
            AuthenticationStageController authenticationStageController = new AuthenticationStageController();
            LogInController logInController = new LogInController(authenticationStageController);
            kundeStage.close();
        });
        view.getBestellungEinsehenButton().setOnAction((event) -> {

            kundeStage.switchToBestellungUebersichtView();

        });

    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return die ProfilView
     */
    public ProfilView getView() {
        return view;
    }
}
