package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.kunde.ProduktDetailView;
import javafx.scene.control.TextField;

/**
 * Klasse, die die ProduktDetail Ansicht kontrolliert
 * 
 * @author Frederik Fluegge
 */
public class ProduktDetailController {

    // Stage des Controllers
    private KundeStageController kundeStage;

    // View des Controllers
    private ProduktDetailView view;

    // Anzuzeigendes Produkt
    private Produkt produkt;

    // Eingeloggter Kunde
    private Kunde kunde;

    /**
     * Konstruktor fuer den ProduktDetailController
     * Initialisiert die view, attribe und die Event Handler des Controllers
     * @param kunde   eingeloggter Kunde
     * @param produkt anzuzeigendes Produkt
     */
    public ProduktDetailController(Kunde kunde, Produkt produkt, KundeStageController kundeStage) {
        this.kundeStage = kundeStage;
        this.kunde = kunde;
        this.produkt = produkt;

        this.view = new ProduktDetailView(produkt);

        setUpVerfuegbarkeit();
        setEventHandler();
    }

    /**
     * Lässt in der View die entsprechende
     * Verfügbarkeit anzeigen.
     */
    private void setUpVerfuegbarkeit() {
        if (produkt.getBestand() >= 30) {
            this.view.setVerfuegbar();
        } else if (produkt.getBestand() > 0) {
            this.view.setWenigVerfuegbar();
        } else {
            this.view.setNichtVerfuegbar();
        }
    }

    /**
     * Initialisiert die Event Handler
     */
    private void setEventHandler() {
        TextField text = view.getAnzahl();

        // Stellt sicher, dass nur Nummern eingegeben werden
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                text.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Hinzufuegen Button wurde gedrueckt; Fuegt ein Produkt dem Warenkorb hinzu
        view.getHinzufuegen().setOnAction(event -> {
            if (text.getText().isEmpty()) {
                text.setText("0");
            }
            int anzahl = Integer.parseInt(view.getAnzahl().getText());
            kunde.getWarenkorb().addProdukt(produkt, anzahl);
            text.setText("1");
            view.successMessage();
        });

        // Zurueck-zu-Home Button wurde gedrueckt; Navigiert zu der Produktuebersicht
        view.getZurueckHome().setOnAction(event -> {
            kundeStage.switchToProduktUebersichtView();
        });
    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return ProduktDetailView
     */
    public ProduktDetailView getView() {
        return view;
    }
}
