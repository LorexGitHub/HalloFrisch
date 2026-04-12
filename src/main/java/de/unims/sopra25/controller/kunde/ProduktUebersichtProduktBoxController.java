package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.entitaeten.Warenkorb;
import de.unims.sopra25.view.kunde.ProduktUebersichtProduktBoxView;
import javafx.scene.control.TextInputControl;

/**
 * Klasse, die die ProduktBox Ansicht kontrolliert
 * 
 * @author David Metzdorf, Irina Hoppe
 */
public class ProduktUebersichtProduktBoxController {
    
    // View des Controllers
    private ProduktUebersichtProduktBoxView view;

    // Warenkorb des Kundens
    private Warenkorb warenkorb;

    // Anzuzeigendes Produkt
    private Produkt produkt;

    // Produktuebersicht der Produktbox
    private ProduktUebersichtController produktUebersichtController;

    /**
     * Konstruktor fuer den ProduktUebersichtProduktBoxController
     * Initialisiert die view, attribute und Event Handler des Controllers
     * 
     * @param kunde, produkt, produktUebersichtController
     */
    public ProduktUebersichtProduktBoxController(Kunde kunde, Produkt produkt, ProduktUebersichtController produktUebersichtController) {
        this.produkt = produkt;
        this.produktUebersichtController = produktUebersichtController;
        this.warenkorb = kunde.getWarenkorb();

        this.view = new ProduktUebersichtProduktBoxView(produkt);
        this.view.getAnzahlField().setText("1");

        initializeEventHandler();
    }

    public void initializeEventHandler() {
        // Textfeld Anzahl
        TextInputControl textField = view.getAnzahlField();

        // Stellt sicher, dass nur Nummern eingegeben werden
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Prüfe, dass nur Nummern eingegeben werden
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            //Prüfe , ob nur nullen im Feld stehen
            if(newValue.matches("00*")){
                textField.setText("1");
            }
        });

        // Hinzufuegen Button wurde gedrueckt; Fuegt das Produkt dem Warenkorb hinzu
        view.getHinzufuegeButton().setOnAction(event -> {
            //Prüfft ob der Wert im Textfeld valid ist.
            if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) != 0) {
                int anzahl = Integer.parseInt(view.getAnzahlField().getText());
                warenkorb.addProdukt(produkt, anzahl);
            }
            view.getAnzahlField().setText("1"); // leere TextFeld nach hinzufuegen
        });

        // Bild des Produkts wurde gedrueckt; Navigiert zur Produktdetail Ansicht
        view.getBildButton().setOnAction(event -> {
            produktUebersichtController.getStage().switchToProduktDetailView(produkt);
        });
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return ProduktUebersichtProduktBoxView
     */
    public ProduktUebersichtProduktBoxView getView() {
        return this.view;
    }
}
