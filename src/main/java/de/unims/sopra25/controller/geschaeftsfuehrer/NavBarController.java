package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.controller.authentication.LogInController;
import de.unims.sopra25.controller.shared.ProduktUebersichtControllerVorlage;
import de.unims.sopra25.view.geschaeftsfuehrer.NavBarView;
import de.unims.sopra25.model.logik.KategorienVerwalten;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Erstellt und verwaltet die NavBar des Geschaeftsfuehrers.
 * 
 * @author Johannes Schick
 */
public class NavBarController {

    // Stage des Controllers
    private GeschaeftsfuehrerStageController stage;

    // View des Controllers
    private NavBarView view;

    // Controller für die Sucheingaben
    private ProduktUebersichtControllerVorlage<?> produktUebersichtController;

    /**
     * setzt stage, initialisiert neue NavBarView und Eventhandler
     * 
     * @param stage                       die uebernommene KundenStage
     * @param produktUebersichtController Controller für Produktübersicht (für Suche
     *                                    wichtig)
     */
    public NavBarController(GeschaeftsfuehrerStageController stage,
            ProduktUebersichtController produktUebersichtController) {
        this.stage = stage;
        this.produktUebersichtController = produktUebersichtController;
        view = new NavBarView(new KategorienVerwalten().getKategorien());
        initializeEventHandler(view);
    }

    /**
     * Listener der Buttons warten auf Events
     * 
     * @param view ist NavBar
     */
    private void initializeEventHandler(NavBarView view) {

        view.getLogoButton().setOnAction(event -> {
            stage.switchToProduktUebersichtView();
        });

        view.getLogOutButton().setOnAction(event -> {
            AuthenticationStageController authC = new AuthenticationStageController();
            LogInController logIn = new LogInController(authC);
            stage.close();
        });

        // Suchlogik wird bei Eingabe ins Textfeld gestartet
        TextField textField = view.getSearchTextfield();
        view.getSearchTextfield().textProperty().addListener((observable, oldValue, newValue) -> {
            produktUebersichtController.suche(newValue);
        });

        // Filterlogik
        view.getFilterBox().getSelectionModel().selectedItemProperty().addListener((observable -> {
            produktUebersichtController.filter(view.getFilterBox().getSelectionModel().getSelectedItem());
            produktUebersichtController.suche(textField.getText());
        }));

        // Wenn Suche-Button gedrückt, wechsle zu Produktübersicht
        view.getStartSearchButton().setOnAction((event) -> {
            produktUebersichtController.zeigeDieseView();
        });

        // Behandle Enter-Taste wie Klicken auf Suchen
        view.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                view.getStartSearchButton().arm();
                view.getStartSearchButton().fire();
            }
        });
    }

    /**
     * Getter-Methode der NavBarView
     * 
     * @return view
     */
    public NavBarView getView() {
        return view;
    }

    /**
     * Methode sollte immer dann aufgerufen werden, wenn
     * die Produktübersicht angezeigt wird bzw. die Umsatzübersicht
     * angezeigt wird.
     * 
     * @param controller neuer Controller, auf welchem gesucht werden soll
     */
    public void setSearchViewController(ProduktUebersichtControllerVorlage<?> controller) {
        this.produktUebersichtController = controller;
    }
}
