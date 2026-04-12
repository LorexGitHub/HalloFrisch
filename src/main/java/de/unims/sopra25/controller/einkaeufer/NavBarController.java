package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.controller.authentication.LogInController;
import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.logik.KategorienVerwalten;
import de.unims.sopra25.view.einkaeufer.NavBarView;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Verwaltet die Suche und leitet Kunden zu anderen Views/Szenen, bei Knopfdruck
 * 
 * @author Constantin Meininghaus, Johannes Schick
 */
public class NavBarController {

    // Stage des Controllers
    private EinkaeuferStageController einkaeuferStage;

    // View des Controllers
    private NavBarView view;

    // Produktuebersicht, in der gesucht wird
    private ProduktUebersichtController sucheController;

    // Kategorien für die Suche
    private ObservableList<Kategorie> kategorien;

    /**
     * Konstruktor fuer den NavBar Controller
     * Initialisiert die view und die Event Handler des Controllers 
     * 
     * @param einkaeuferStage die uebernommene KundenStage
     */
    public NavBarController(EinkaeuferStageController einkaeuferStage, ProduktUebersichtController sucheController) {
        this.einkaeuferStage = einkaeuferStage;
        this.sucheController = sucheController;
        this.kategorien = new KategorienVerwalten().getKategorien();

        this.view = new NavBarView(kategorien);

        initializeEventHandler();
    }

    /**
     * Initialisiert alle Eventhandler
     */
    private void initializeEventHandler() {

        // Es wird etwas in das TextFeld der Suche eingegeben; Sucht nach dem Produkt, das eingegeben wurde
        TextField textField = view.getSearchTextfield();
        view.getSearchTextfield().textProperty().addListener((observable, oldValue, newValue) -> {
            sucheController.suche(newValue);
        });

        // Filterlogik
        view.getFilterBox().getSelectionModel().selectedItemProperty().addListener((observable -> {
            sucheController.filter(view.getFilterBox().getSelectionModel().getSelectedItem());
            sucheController.suche(textField.getText());
        }));

        // Suche Button wurde gedrueckt; Navigiert zu der Produktuebersicht
        view.getStartSearchButton().setOnAction((event) -> {
            einkaeuferStage.switchToProduktUebersicht();
        });

        // Wenn Enter-Taste gerueckt wird, kann gesucht werden
        view.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                view.getStartSearchButton().arm();
                view.getStartSearchButton().fire();
            }
        });

        // Logo Button wurde gedrueck; Navigiert zu der Produktuebersicht
        view.getLogoButton().setOnAction(event -> {
            einkaeuferStage.switchToProduktUebersicht();
            resetFilter();
        });

        // LogOut Button wurde gedrueckt; Loggt den Nutzer azs
        view.getLogOutButton().setOnAction(event -> {
            AuthenticationStageController authC = new AuthenticationStageController();
            LogInController loginController = new LogInController(authC);
            einkaeuferStage.close();
        });

    }

    /**
     * Setzt Filter zurück
     */
    private void resetFilter(){
        view.getFilterBox().getSelectionModel().select(0);
        view.getSearchTextfield().setText("");
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return NavBarView
     */
    public NavBarView getView() {
        return view;
    }
}
