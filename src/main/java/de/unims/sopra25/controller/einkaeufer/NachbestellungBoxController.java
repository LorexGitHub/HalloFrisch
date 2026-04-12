package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.model.logik.E_BestellungenVerwalten;
import de.unims.sopra25.view.einkaeufer.NachbestellungBoxView;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

/**
 * Klasse die die NachbestellungBoxView kontrolliert
 *
 * @author David Metzdorf
 */
public class NachbestellungBoxController {

    // Stage des Controllers
    private EinkaeuferStageController einkaeuferStage;

    // View des Controllers
    private NachbestellungBoxView view;

    // Ausgewaehlte Bestellung
    private E_Bestellung bestellung;

    // Logik für die Verwaltung von Bestellungen des Einkaeufers
    private E_BestellungenVerwalten verwalter;

    // Abstaende
    protected int EMPTY = 5;
    protected int ALMOST_EMPTY = 30;

    /**
     * Konstruktor fuer den NachbestellungBoxController
     * Initialisiert die View, alle attribute und die Event Handler des Controllers
     * Setzt außerdem die Farbe der View
     *
     * @param bestellung ausgewaehlte Bestellung
     */
    public NachbestellungBoxController(EinkaeuferStageController einkaeuferStage, E_Bestellung bestellung,
            E_BestellungenVerwalten verwalter) {
        this.bestellung = bestellung;
        this.einkaeuferStage = einkaeuferStage;
        this.verwalter = verwalter;

        this.view = new NachbestellungBoxView(bestellung);

        setColor();
        initializeEventHandler();
    }

    /**
     * Initialisiert alle Eventhandler
     */
    private void initializeEventHandler() {

        /*
         * Damit die Anzeige aktuelle bei der änderung einer Nachbestellungen ist,
         * wird bei jeder Action das Fenster wieder geladen mit
         * einkaeuferStage.switchToNachbestellungUeberischt()
         */

        // Hinzufuegen Button wurde gedrueckt; Fuegt die Anzahl an Produkten zur Nachbestellung hinzu
        view.getHinzufuegeButton().setOnAction(event -> {
            int valueOfField = Integer.parseInt(view.getAnzahlField().getText());
            int newCount = bestellung.getAnzahl() + valueOfField;
            bestellung.setAnzahl(newCount);
            einkaeuferStage.switchToNachbestellungUeberischt();
        });

        // Enferne Button wurde gedrueckt; Entfernt Nachbestellungen
        view.getEntferneButton().setOnAction(event -> {
            int valueOfField = Integer.parseInt(view.getAnzahlField().getText());
            int newCount = bestellung.getAnzahl() - valueOfField;

            if (newCount <= 0) {
                verwalter.purgeBestellung(bestellung);
            } else {
                bestellung.setAnzahl(newCount);
            }

            einkaeuferStage.switchToNachbestellungUeberischt();
        });

        // Stellt sicher, dass nur Zahlen eingegeben werden können
        view.getAnzahlField().textProperty().addListener((observable, oldValue, newValue) -> {
            // Prüfe, dass nur Nummern eingegeben werden
            if (!newValue.matches("\\d*")) {
                view.getAnzahlField().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Enter Taste wurde auf der Tastatur gedrueckt; Aktualisiert Anzahl der Nachbestellung mit der Anzahl im Feld
        view.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                int valueOfField = Integer.parseInt(view.getAnzahlField().getText());

                if (valueOfField <= 0) {
                    verwalter.purgeBestellung(bestellung);
                } else {
                    bestellung.setAnzahl(valueOfField);
                }

                einkaeuferStage.switchToNachbestellungUeberischt();
            }
        });

        // Bestaetige Button wurde gedrueckt; Entfernt die Nachbestellung aus der NachbestellungUebersicht
        view.getBestaetigeButton().setOnAction(event -> {
            verwalter.removeBestellung(bestellung);
            einkaeuferStage.switchToNachbestellungUeberischt();
        });

    }

    /**
     * Methode fürs Design.
     * Stellt die Anzahlen Bestand und nachbestellt
     * entsprechend in Farben dar.
     */
    private void setColor() {
        Label bestand = view.getBestandAnzahl();
        Label nachbestellt = view.getNachbestelltAnzahl();

        // Färbt Bestand ein
        int anzahlBestand = Integer.parseInt(bestand.getText()) + Integer.parseInt(nachbestellt.getText());
        if (anzahlBestand < EMPTY) {
            view.setBackground(Background.fill(new Color(1, 0.4, 0.4, 1)));
        } else if (anzahlBestand < ALMOST_EMPTY) {
            view.setBackground(Background.fill(new Color(1, 1, 0.4, 1)));
        } else {
            view.setBackground(Background.EMPTY);
        }

    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return NachbestellungBoxView
     */
    public NachbestellungBoxView getView() {
        return view;
    }

}
