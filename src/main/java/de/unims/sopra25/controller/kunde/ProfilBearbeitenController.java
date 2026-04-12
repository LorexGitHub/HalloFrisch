package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.logik.NutzerVerwalten;
import de.unims.sopra25.view.kunde.ProfilBearbeitenView;

/**
 * Klasse, die die ProfilBearbeiten Ansicht kontrolliert
 * 
 * @author Lukas Tilgner, Constantin Meininghaus
 */
public class ProfilBearbeitenController {

    // Stage des Controllers
    private KundeStageController kundeStage;

    // View des Controllers
    private ProfilBearbeitenView view;

    // Eingeloggter Kunde
    private Kunde kunde;

    // Logik für die Verwaltung der Nutzer
    private NutzerVerwalten nutzerVerwalten;

    // Nutzerdaten, die angezeigt werden
    private String nachname;
    private String vorname;
    private String adresse;

    /**
     * Konstruktor fuer den ProfilBearbeitenController
     * Initialisiert die view, attribute und Event Handler des Controllers
     * Zeigt außerdem die aktuellen Kudendaten an
     * 
     * @param kundeStage Stage des Controllers
     */
    public ProfilBearbeitenController(KundeStageController kundeStage, Kunde kunde) {
        this.kundeStage = kundeStage;
        this.kunde = kunde;

        this.view = new ProfilBearbeitenView();

        initializeEventHandlers();
        zeigeKundenDaten();
    }

    /**
     * Initialisiert die Event Handler
     */
    public void initializeEventHandlers() {

        // Save Button wurde gedrueckt; Setzt die Kundendaten neu
        view.getSaveButton().setOnAction((event) -> {
            // Setze Kundendaten
            nutzerVerwalten = new NutzerVerwalten();
            if (!view.getAdresseField().getText().isBlank()) {
                kunde.setAdresse(view.getAdresseField().getText());
                view.getAdresseField().clear();
                view.setMessageText("Daten erfolgreich übernommen");
            }
            if (!view.getNachnameField().getText().isBlank()) {
                kunde.setNachname(view.getNachnameField().getText());
                view.getNachnameField().clear();
                view.setMessageText("Daten erfolgreich übernommen");
            }
            if (!view.getVornameField().getText().isBlank()) {
                kunde.setVorname(view.getVornameField().getText());
                view.getVornameField().clear();
                view.setMessageText("Daten erfolgreich übernommen");
            }

            // Aktualisieren der Anzeige
            view.getSaveButton().setDisable(true);
            kundeStage.reloadNavBar();
            zeigeKundenDaten();

        });

        // Setzt den save button auf disabled, wenn das Nachname Feld noch nicht
        // ausgefuellt ist
        view.getNachnameField().textProperty().addListener((observable, oldValue, newValue) -> {
            view.getSaveButton().setDisable(false);
            view.setMessageText("");
        });

        // Setzt den save button auf disabled, wenn das Vorname Feld noch nicht
        // ausgefuellt ist
        view.getVornameField().textProperty().addListener((observable, oldValue, newValue) -> {
            view.getSaveButton().setDisable(false);
            view.setMessageText("");
        });

        // Setzt den save button auf disabled, wenn das Adresse Feld noch nicht
        // ausgefuellt ist
        view.getAdresseField().textProperty().addListener((observable, oldValue, newValue) -> {
            view.getSaveButton().setDisable(false);
            view.setMessageText("");
        });

    }

    /**
     * Uebernimmt die aktuellen Kundendaten (Vorname, Nachname, Adresse) und zeigt
     * diese an
     */
    private void zeigeKundenDaten() {
        vorname = kunde.getVorname();
        nachname = kunde.getNachname();
        adresse = kunde.getAdresse();

        view.initializeInfoText(vorname, nachname, adresse);

    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return ProfilBearbeitenView
     */
    public ProfilBearbeitenView getView() {
        return view;
    }
}
