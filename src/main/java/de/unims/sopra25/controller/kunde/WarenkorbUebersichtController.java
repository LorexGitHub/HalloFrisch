package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.WarenkorbProduktAssoz;
import de.unims.sopra25.view.kunde.WarenkorbProduktBoxView;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Klasse, die die WarenkorbUebersicht Ansicht kontrolliert
 * 
 * @author Frederik Fluegge
 */
public class WarenkorbUebersichtController {

    // Stage des Controllers
    private KundeStageController kundeStage;

    // View des ControllersÍ
    private ScrollPane view;

    // Zu erzeugende Tabelle
    private VBox produktTabelle;

    // Liste an Produkten im Warenkorb
    private List<WarenkorbProduktAssoz> warenkorbProdukteAssoz;

    /**
     * Konstruktor fuer den WarenkorbUebersichtController
     * Initialisiert die view, attribute und erzeugt die ProduktTabelle innerhalb
     * des Warenkorbs
     *
     * @param kundeStage aktueller Controller des Kunden-Fensters
     * @param kunde      aktuell eingeloggter Kunde
     */
    public WarenkorbUebersichtController(KundeStageController kundeStage, Kunde kunde,
            WarenkorbBottomBarController bottomBarController) {
        this.kundeStage = kundeStage;
        this.warenkorbProdukteAssoz = kunde.getWarenkorb().getProdukte();

        view = new ScrollPane();

        initialisiereWarenkorbAnsicht(bottomBarController);
    }

    /**
     * Initilaisiert die Warenkorb Ansicht
     * 
     * @param bottomBarController
     */
    private void initialisiereWarenkorbAnsicht(WarenkorbBottomBarController bottomBarController) {
        
        // Wenn Warenkorb leer, zeige einen Hinweis an
        if (warenkorbProdukteAssoz.isEmpty()) {
            Label label = new Label(
                    "Warenkorb leer. \nFüge Produkte über die Produktübersicht oder Detailansicht hinzu.");
            view.setContent(label);
        }

        // Sonst: Fülle Ansicht
        else {
            produktTabelle = new VBox();
            initialisiereProduktTabelle(warenkorbProdukteAssoz, bottomBarController);
        }
    }

    /**
     * Initialisiert die ProduktTabelle, indem eine VBox von ProduktBoxen erstellt
     * wird.
     *
     * @param warenkorbProdukteAssoz Liste mit WarenkorbProdukten, welche angezeigt
     *                               werden sollen
     */
    private void initialisiereProduktTabelle(List<WarenkorbProduktAssoz> warenkorbProdukteAssoz,
            WarenkorbBottomBarController bottomBarController) {
        produktTabelle = new VBox();
        // erzeuge HBox mit gewuenschten Produktdaten je Produkt
        warenkorbProdukteAssoz.forEach((produktAssoz) -> {
            WarenkorbProduktBoxController warenkorbProduktBoxController = new WarenkorbProduktBoxController(
                    produktAssoz, this, bottomBarController);
            produktTabelle.getChildren().add(warenkorbProduktBoxController.getView());
        });
        view.setContent(produktTabelle);
    }

    /**
     * Gibt die View des Controllers zurueck
     *
     * @return Warenkorb-Pane
     */
    public ScrollPane getView() {
        return view;
    }

    /**
     * Entfernt eine uebergebende Produkt-Box
     * aus der Tabelle.
     *
     * @param box zu entfernende Produkt-Box
     */
    public void removeProduktBox(WarenkorbProduktBoxView box) {
        this.produktTabelle.getChildren().remove(box);
    }

    /**
     * Gibt die Stage des Controller zurueck
     *
     * @return KundeStageController
     */
    public KundeStageController getKundenStage() {
        return kundeStage;
    }
}
