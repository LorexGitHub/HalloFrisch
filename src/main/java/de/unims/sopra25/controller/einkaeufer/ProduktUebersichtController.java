package de.unims.sopra25.controller.einkaeufer;

import de.unims.sopra25.controller.shared.ProduktUebersichtControllerVorlage;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.ListeFiltern;
import de.unims.sopra25.view.einkaeufer.ProduktBoxView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * Klasse, die die ProduktUebersichtView kontrolliert
 * 
 * @author Irina Hoppe, Johannes Schick, Pia Maitzen
 */
public class ProduktUebersichtController extends ProduktUebersichtControllerVorlage<ProduktBoxView> {

    // Stage des Controllers
    private EinkaeuferStageController einkaeuferStage;

    /**
     * Konstruktor fuer den ProduktUebersichtController
     * Initialisiert die stage und die Produktboxen und sortiert diese
     * 
     * @param einkaeuferStage Controller der EinkäuferStage
     */
    public ProduktUebersichtController(EinkaeuferStageController einkaeuferStage) {
        super();
        this.einkaeuferStage = einkaeuferStage;

        initialisiereProduktBoxen();

        sortiere();
    }

    /**
     * Aktualisiert die Bestandsanzeige aller Produktboxen.
     */
    public void aktualisiere(){
        for(ProduktBoxView boxView: produktBoxen){
            boxView.getBoxController().setColor();
        }
    }

    /**
     * Sortiert alle ProduktBoxen nach der Anzahl an Bestaenden und Nachbestellungen
     */
    public void sortiere() {
        ListeFiltern.einkaeuferSortiereProdukte(filteredProduktBoxen);
    }

    /**
     * Erstellt ProduktBoxController fuer ein Produkt und fuegt eine ProduktBox der
     * ProduktUebersicht hinzu
     * 
     * @param produkt Produkt
     */
    @Override
    protected void addProduktBoxToProduktTabelle(Produkt produkt) {
        ProduktBoxController produktBoxController = new ProduktBoxController(produkt, this);
        produktBoxen.add(produktBoxController.getView());
    }

    /**
     * Methode zur Umsetzung der Suchfunktion.
     * @param text Anzuwendender Text-Filter
     */
    @Override
    public void suche(String text) {
        filteredProduktBoxen.setPredicate(boxView -> ListeFiltern.produktSuche(boxView.getProdukt(), text, kategorie));
    }

    /**
     * Zeigt die View des Controllers
     */
    @Override
    public void zeigeDieseView() {
        einkaeuferStage.switchToProduktUebersicht();
    }

}
