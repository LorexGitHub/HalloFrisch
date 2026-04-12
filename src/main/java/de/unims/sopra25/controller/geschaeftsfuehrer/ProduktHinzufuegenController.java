package de.unims.sopra25.controller.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.KategorienVerwalten;
import de.unims.sopra25.model.logik.SortimentVerwalten;
import de.unims.sopra25.view.geschaeftsfuehrer.ProduktHinzufuegenView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Klasse, die Inputs vom ProduktHinzufuegenView kontrolliert
 *
 * @author Frederik Fluegge
 */
public class ProduktHinzufuegenController {

    // Stage des Controllers
    private GeschaeftsfuehrerStageController stage;

    // View des Controllers
    private ProduktHinzufuegenView view;

    // Produktuebersicht der Controllers
    private ProduktUebersichtController produktUebersichtController;

    // NavBar des Controllers
    private NavBarController navBarController;

    // Logik fuer die Verwaltung der Kategorien
    private KategorienVerwalten kategorienVerwalten;

    // Logik fuer die Verwaltung des Sortiments
    private SortimentVerwalten sortimentVerwalten;

    /**
     * Konstruktor fuer den ProduktHinzufuegenController
     *
     * @param stage                       Stage des Controllers
     * @param produktUebersichtController Produktuebersicht des Controllers
     * @param navBarController            NavBar des Controllers
     */
    public ProduktHinzufuegenController(GeschaeftsfuehrerStageController stage,
            ProduktUebersichtController produktUebersichtController, NavBarController navBarController) {
        this.stage = stage;
        this.navBarController = navBarController;
        this.produktUebersichtController = produktUebersichtController;

        this.kategorienVerwalten = new KategorienVerwalten();
        this.sortimentVerwalten = new SortimentVerwalten();

        this.view = new ProduktHinzufuegenView(kategorienVerwalten.getKategorien());

        initialisiereEventHandler();
    }

    /**
     * 
     * Handelt das Event, wenn der Erstelle Butten gedrückt wird
     */
    private void initialisiereEventHandler() {

        // Stellt sicher, dass beim Preis Input Field nur Nummern eingegeben werden
        // können
        view.getPreisTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
                newValue = newValue.replace(',', '.')
                        .replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
                view.getPreisTextField().setText(newValue);

            }
        });

        // Erstellen Button wurde gedrückt
        view.getErstellenButton().setOnAction(event -> {
            if (view.getNameTextField().getText().isEmpty()
                    || view.getBildTextField().getText().isEmpty()
                    || view.getPreisTextField().getText().isEmpty()
                    || view.getBeschreibungTextField().getText().isEmpty()) {
                // Error Message
                view.inputFailed();
            } else {
                String name = view.getNameTextField().getText();
                String beschreibung = view.getBeschreibungTextField().getText();
                String bild = view.getBildTextField().getText();
                ArrayList<Kategorie> kategorien = getSelectedKategorien();

                String neuerPreis = view.getPreisTextField().getText();
                int preis = (int) (Float.parseFloat(neuerPreis) * 100);

                String fileName = null;
                try (InputStream in = new URL(bild).openStream()) {
                    fileName = "file:./speicher/icons/" + name + ".jpg";
                    Path path = Paths.get("speicher/icons/" + name + ".jpg");
                    Files.copy(in, path);
                } catch (IOException e) {
                    view.inputFailed();
                    System.out.println("Speichern ist fehlgeschlagen");
                    return;
                }
                try {
                    // Fuege neues Produkt hinzu
                    Produkt produkt = new Produkt(name, preis, beschreibung, fileName, kategorien);
                    sortimentVerwalten.neuesProdukt(produkt);
                    // Clear Input Fields
                    view.getNameTextField().clear();
                    view.getPreisTextField().clear();
                    view.getBeschreibungTextField().clear();
                    view.getBildTextField().clear();
                    deselectKategorien();

                    // Lade Produktuebersicht neu
                    produktUebersichtController.addProduktBoxToProduktTabelle(produkt);

                    view.inputSucced("Das Produkt wurde erfolgreich angelegt");
                }
                // Produktname bereits vorhanden
                // TODO: Bessere Fehlermeldung anzeigen, wenn ein Produkt mit falschen inputs
                // versucht wurde anzulegen
                catch (Exception e) {
                    view.inputFailed();
                }
            }
        });
        // Neue Kategorie hinzufügen
        view.getNeueKategorieButton().setOnAction(actionEvent -> {
            if (view.getNeueKategorieText().getText().isEmpty()) {
                view.inputFailed();
            } else {
                try {
                    kategorienVerwalten.addKategorie(view.getNeueKategorieText().getText());
                    view.inputSucced("Kategorie erfolgreich angelegt");

                    // Aktualisiere Ansicht
                    view.getNeueKategorieText().clear();
                    view.kategorieHinzufuegen(kategorienVerwalten.getKategorien().getLast());
                    navBarController.getView().addKategorieFilter(kategorienVerwalten.getKategorien().getLast());
                } catch (IllegalArgumentException e) {
                    view.inputFailed();
                }
            }
        });

        view.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                view.getErstellenButton().arm();
                view.getErstellenButton().fire();
            }

        });

    }

    /**
     * Gibt alle ausgewählten Kategorien zurück.
     * 
     * @return Liste von ausgewählten Kategorien
     */
    private ArrayList<Kategorie> getSelectedKategorien() {
        ArrayList<Kategorie> kategorien = new ArrayList<>();
        CustomMenuItem customMenuItem;
        CheckBox checkBox;

        for (MenuItem item : view.getKategorieMenu().getItems()) {
            customMenuItem = (CustomMenuItem) item;
            checkBox = (CheckBox) customMenuItem.getContent();
            if (checkBox.isSelected()) {
                kategorien.add(kategorienVerwalten.getKategorieByName(checkBox.getText()));
            }
        }
        return kategorien;
    }

    /**
     * Deselektiert alle Kategorien.
     */
    private void deselectKategorien() {
        CustomMenuItem customMenuItem;
        CheckBox checkBox;
        for (MenuItem item : view.getKategorieMenu().getItems()) {
            customMenuItem = (CustomMenuItem) item;
            checkBox = (CheckBox) customMenuItem.getContent();
            checkBox.setSelected(false);
        }
    }

    /**
     * Getter Methode für die View des Controllers
     *
     * @return view ProduktHinzufuegenView
     */
    public ProduktHinzufuegenView getView() {
        return view;
    }
}
