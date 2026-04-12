package de.unims.sopra25.controller.kunde;

import java.math.BigInteger;

import de.unims.sopra25.model.entitaeten.WarenkorbProduktAssoz;
import de.unims.sopra25.view.kunde.WarenkorbProduktBoxView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;

/**
 * Klasse, die die WarenkorbProduktBox Ansicht kontrolliert
 * 
 * @author Johannes Schick
 */
public class WarenkorbProduktBoxController {

    // View des Controllers
    private WarenkorbProduktBoxView view;

    // Repraesentierte Assoziation
    private WarenkorbProduktAssoz produktAssoz;

    // notwendige Controller fuer diesen Controllers
    private WarenkorbUebersichtController warenkorbController;
    private WarenkorbBottomBarController bottomBarController;

    /**
     * Konstruktor fuer den WarenkorbProduktBoxController
     * Initialisiert die view, attribute und Event Handler des Controllers
     * 
     * @param produkt             ZTu repraesentierendes Produkt
     * @param warenkorbController Controller, welcher die gesamte Warenkorb-Ansicht
     *                            verwaltet
     * @param bottomBarController Controller, welcher die Bottom-Bar verwaltet.
     */
    public WarenkorbProduktBoxController(WarenkorbProduktAssoz produkt,
            WarenkorbUebersichtController warenkorbController, WarenkorbBottomBarController bottomBarController) {
        this.produktAssoz = produkt;
        this.warenkorbController = warenkorbController;
        this.bottomBarController = bottomBarController;

        this.view = new WarenkorbProduktBoxView(produkt);
        view.getAnzahlField().setText(String.valueOf(produktAssoz.getAnzahl()));

        initialisiereEventHandler();
    }

    /**
     * Initialisiert alle Event Handlert
     */
    private void initialisiereEventHandler() {
        TextInputControl textField = view.getAnzahlField();

        // Kontrolliert Eingaben in das Anzahl Textfeld
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Prüfe, dass nur Nummern eingegeben werden
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            // Verändere Textfarbe, wenn neue Anzahl
            if (textField.getText().isEmpty() || Integer.parseInt(textField.getText()) == produktAssoz.getAnzahl()) {
                view.getAnzahlField().setStyle("-fx-text-fill: black");
            }
            // Reset Textfarbe, wenn alte Anzahl
            else {
                view.getAnzahlField().setStyle("-fx-text-fill: orange");
            }
        });

        // Aktualisiere Button wurde gerdrueckt; Aktualisiert die Anzahl des Produkts im Warenkorb
        view.getAktualisieren().setOnAction(event -> {
            BigInteger anzahlBIG = new BigInteger(view.getAnzahlField().getText());
            int max = Integer.MAX_VALUE;
            BigInteger maxINT = BigInteger.valueOf(max);
            int anzahl;
            if(anzahlBIG.compareTo(maxINT) == 1){
                anzahl = Integer.MAX_VALUE;
            }else {
                anzahl = Integer.parseInt(view.getAnzahlField().getText());
            }
            if (anzahl <= 0) {
                warenkorbController.removeProduktBox(view);
                return;
            }
            produktAssoz.setAnzahl(anzahl);
            // Farbe zurücksetzen
            view.getAnzahlField().setStyle("-fx-text-fill: black");
            view.getAnzahlField().setStyle("-fx-text-fill: black");
            // aktuallisiere BottomBar
            bottomBarController.aktualisiere();
        });

        // Loeschen Button wurde gedreuckt; Entfernt ein Produkt aus dem Warenkorb
        view.getLoeschen().setOnAction(event -> {
            produktAssoz.getWarenkorb().removeProdukt(produktAssoz.getProdukt());
            warenkorbController.removeProduktBox(view);
            // aktuallisiere BottomBar
            bottomBarController.aktualisiere();
        });

        // Bild Button des Produkts wurde gedrueckt; Navigiert zur Produktdetail Ansicht
        view.getBildButton().setOnAction(event -> {
            warenkorbController.getKundenStage().switchToProduktDetailView(produktAssoz.getProdukt());
        });

        // Enter Taste auf der Tastatur wurde gedrueckt; Aktualisiert das ausgewaehlte Produkt
        view.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                view.getAktualisieren().arm();
                view.getAktualisieren().fire();
            }
        });

    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return WarenkorbProduktBoxView
     */
    public WarenkorbProduktBoxView getView() {
        return view;
    }
}
