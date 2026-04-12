package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.controller.authentication.LogInController;
import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.logik.KategorienVerwalten;
import de.unims.sopra25.view.kunde.NavBarView;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TextField;

/**
 * Klasse, die die NavBar Ansicht kontrolliert
 * 
 * @author Constantin Meininghaus, Johannes Schick
 */
public class NavBarController {

    // Stage des Controllers
    private KundeStageController kundeStage;

    // View des Controllers
    private NavBarView view;

    // Stage, in der zu suchen ist
    private ProduktUebersichtController sucheController;

    // Liste an Kategorien, nach denen gesucht werden kann
    private ObservableList<Kategorie> kategorien;

    /**
     * Initialisiert die view, kundeStage, initialisiert neue NavBarView und
     * Eventhandler
     * 
     * @param kundeStage die uebernommene KundenStage
     */
    public NavBarController(KundeStageController kundeStage, ProduktUebersichtController sucheController) {
        this.kundeStage = kundeStage;
        this.kategorien = new KategorienVerwalten().getKategorien();
        this.sucheController = sucheController;

        view = new NavBarView(kategorien);

        initializeEventHandler();
    }

    /**
     * Initialisiert die Event Handler 
     */
    private void initializeEventHandler() {
        view.setLieferadresse(kundeStage.getKunde().getAdresse());

        // Warenkorb Button wurde gedrueckt; Navigiert zu der Warenkorb Ansicht
        view.getWarenkorbButton().setOnAction((event) -> {
            kundeStage.switchToWarenkorbView();
        });

        // Suchlogik wird bei Eingabe ins Textfeld gestartet
        TextField textField = view.getSearchTextfield();
        view.getSearchTextfield().textProperty().addListener((observable, oldValue, newValue) -> {
            sucheController.suche(newValue);
        });

        // Filterlogik fuer die Kategorie
        view.getFilterBox().getSelectionModel().selectedItemProperty().addListener((observable -> {
            sucheController.filter(view.getFilterBox().getSelectionModel().getSelectedItem());
            sucheController.suche(textField.getText());
        }));

        // Filterlogik fuer den Bestand
        view.getFilterBestand().getSelectionModel().selectedItemProperty().addListener((observable -> {
            if (view.getFilterBestand().getSelectionModel().getSelectedItem().equals("Alle")) {
                sucheController.filter(0);
            } else {
                sucheController.filter(1);
            }
            sucheController.suche(textField.getText());
        }));

        // Filterlogik fuer den MinPreis
        view.getMinPreis().textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = formatString(newValue);
            if (newValue.isEmpty()) {
                sucheController.setMinPreis(0);
            } else {
                sucheController.setMinPreis((int) (Float.parseFloat(newValue) * 100));
            }
            sucheController.suche(textField.getText());
            view.getMinPreis().setText(newValue);
        });

        // Filterlogik fuer den MaxPreis
        view.getMaxPreis().textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = formatString(newValue);
            if (newValue.isEmpty()) {
                sucheController.setMaxPreis(Integer.MAX_VALUE);
            } else {
                sucheController.setMaxPreis((int) (Float.parseFloat(newValue) * 100));
            }
            sucheController.suche(textField.getText());
            view.getMaxPreis().setText(newValue);
        });

        // Suche-Button wurde gedrueckt; Navigiert zu der Produktuebersicht
        view.getStartSearchButton().setOnAction((event) -> {
            kundeStage.switchToProduktUebersichtView();
        });

        // Profil-Button wurde gedrueckt; Navigiert zu der Profilansicht
        view.getProfilButton().setOnAction((event) -> {
            kundeStage.switchToProfilView();
        });

        // Logo-Button wurde gedrueckt; Navigiert zu der Produktuebersicht
        view.getLogoButton().setOnAction(event -> {
            this.resetFilter();
            kundeStage.switchToProduktUebersichtView();
        });

        // Logout-Button wurde gedrueckt; Loggt den Kunden aus
        view.getLogOutButton().setOnAction(event -> {
            AuthenticationStageController authenticationStageController = new AuthenticationStageController();
            LogInController logInController = new LogInController(authenticationStageController);
            kundeStage.close();
        });

        // Warenkorb-Button wurde gedrueckt; Navigiert zu der Warenkorb Ansicht
        view.getWarenkorbButton().setOnAction((event) -> {
            kundeStage.switchToWarenkorbView();
        });

        // Halte Menü offen, wenn in ComboBox Veränderungen
        view.getFilterBox().setOnAction(Event::consume);
        view.getFilterBestand().setOnAction(Event::consume);

    }

    /**
     * Formatiert einen String, sodass er nur zwei Nachkommestellen haben kann
     * @param text Uebergebener String
     * @return String formatierter String
     */
    private String formatString(String text) {
        if (!text.matches("\\d*\\.?\\d{0,2}")) {
            return text.replace(',', '.')
                    .replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
        }
        return text;
    }

    /**
     * Setzt Filter zurück
     */
    private void resetFilter() {
        view.getMaxPreis().setText("");
        view.getMinPreis().setText("");
        view.getFilterBestand().getSelectionModel().select(0);
        view.getFilterBox().getSelectionModel().select(0);
        view.getSearchTextfield().setText("");
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return view
     */
    public NavBarView getView() {
        return view;
    }
}
