package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.E_BestellungenVerwalten;
import de.unims.sopra25.view.einkaeufer.ProduktBoxView;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

/**
 * Klasse zum Erstellen und Verwalten
 * von ProduktBoxView für Einkäufer.
 *
 * @author Johannes Schick
 */
public class ProduktBoxController {

    // Stage des Controllers
    private ProduktUebersichtController uebersichtController;

    // View des Controllers
    private ProduktBoxView view;

    // Produkt, für das die Box erstellt wird
    private Produkt produkt;

    // Logik fuer die Verwaltung der Bestellungen des Eninkaeufers
    private E_BestellungenVerwalten verwalter;

    // Abstaende
    protected int EMPTY = 5;
    protected int ALMOST_EMPTY = 30;

    /**
     * Konstruktor fuer ProduktBoxController
     * Initialisiert die View, attribute und die Event Handler des Controllers
     * Setzt außerdem die Farbe der View
     *
     * @param produkt    Anzuzeigendes Produkt
     * @param controller Controller für die Übersicht
     */
    public ProduktBoxController(Produkt produkt, ProduktUebersichtController controller) {
        this.produkt = produkt;
        this.uebersichtController = controller;
        this.verwalter = new E_BestellungenVerwalten();

        this.view = new ProduktBoxView(produkt, this);

        setColor();
        initializeEventHandler();
    }

    /**
     * Initialisiert alle Eventhandler
     */
    private void initializeEventHandler() {
        // Textfeld Anzahl
        TextInputControl textField = view.getAnzahlField();

        // Stellt sicher, dass nur Nummern eingegeben werden
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Prüfe, dass nur Nummern eingegeben werden
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Hinzufuege Button wurde gedrueckt; Fuegt eine Anzahl an Produkt zu der Nachbestellung hinzu
        view.getHinzufuegeButton().setOnAction(event -> {
            if (!textField.getText().isEmpty()) {
                int anzahl = Integer.parseInt(view.getAnzahlField().getText());
                verwalter.addBestellung(produkt, anzahl);
                // Aktualisiert Anzeige
                view.setNachbestelltAnzahl(produkt.getAnzahlNachbestellteExemplare());
                textField.clear();
                setColor();
                uebersichtController.sortiere();
            }
        });

        // Enter Button wurde auf der Tastatur gedrueckt; Fuegt eine Anzahl an Produkt zu der Nachbestellung hinzu
        view.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                view.getHinzufuegeButton().arm();
                view.getHinzufuegeButton().fire();
            }
        });

    }

    /**
     * Methode aktualisiert die Bestandsanzeige und
     * stellt die Anzahlen Bestand und nachbestellt
     * entsprechend in Farben dar.
     */
    protected void setColor() {
        Label bestand = view.getBestandAnzahl();
        Label nachbestellt = view.getNachbestelltAnzahl();
        bestand.setText(String.valueOf(produkt.getBestand()));
        nachbestellt.setText(String.valueOf(produkt.getAnzahlNachbestellteExemplare()));

        // Färbt Bestand ein
        int anzahlBestand = Integer.parseInt(bestand.getText()) + Integer.parseInt(nachbestellt.getText());
        if (anzahlBestand < EMPTY) {
            view.setBackground(Background.fill(new Color(1, 0.4, 0.4, 1)));
        } else if (anzahlBestand < ALMOST_EMPTY) {
            view.setBackground(Background.fill(new Color(1, 1, 0.4, 1)));
        } else {
            view.setBackground(Background.EMPTY);
        }

        // Färbt Nachbestellung ein
        if (nachbestellt.getText().equals("0")) {
            nachbestellt.setTextFill(Color.BLACK);
            return;
        }
        nachbestellt.setTextFill(Color.GREEN);

    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return ProduktBoxView 
     */
    public ProduktBoxView getView() {
        return this.view;
    }
}
