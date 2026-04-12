package de.unims.sopra25.controller.kunde;

import de.unims.sopra25.controller.shared.ProduktUebersichtControllerVorlage;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.ListeFiltern;
import de.unims.sopra25.view.kunde.ProduktUebersichtProduktBoxView;
import javafx.scene.control.ListView;

/**
 * Klasse, die die ProdutUbersicht Ansicht kontrolliert
 * 
 * @author Irina Hoppe, Pia Maitzen, Johannes Schick
 */
public class ProduktUebersichtController extends ProduktUebersichtControllerVorlage<ProduktUebersichtProduktBoxView> {

    // stageController des Kunden
    private KundeStageController kundeStage;

    // Eingeloggter Kunde
    private Kunde kunde;
    
    // Filtereigenschaften
    private int bestand;
    private int minPreis;
    private int maxPreis;

    /**
     * Kosntruktor, fuer den ProduktUeberischtController
     * Initialisiert die attribute und die Produktboxen des Controllers
     * 
     * @param kundeStage KundeStageController
     * @param kunde Kunde, dessen ProduktUebersicht angezeigt wird
     */
    public ProduktUebersichtController(KundeStageController kundeStage, Kunde kunde) {
        super();
        this.kundeStage = kundeStage;
        this.kunde = kunde;

        initialisiereProduktBoxen();
        resetFilter();
    }

    /**
     * Methode zur Umsetzung der Suchfunktion
     */
    @Override
    public void suche(String text) {
        filteredProduktBoxen.setPredicate(boxView -> ListeFiltern.produktSuche(boxView.getProdukt(), text, kategorie,
                bestand, minPreis, maxPreis));
    }

    /**
     * Gibt die View des Controllers zurück
     *
     * @return ProduktUebersichtView
     */
    public ListView<ProduktUebersichtProduktBoxView> getView() {
        return view;
    }

    /**
     * Gibt die Stage des Controllers zurück
     * 
     * @return KundeStageController
     */
    public KundeStageController getStage() {
        return kundeStage;
    }

    /**
     * Erstellt ProduktBoxController fuer ein Produkt und fuegt eine ProduktBox der ProduktUebersicht hinzu
     * 
     * @param produkt Produkt
     */
    @Override
    protected void addProduktBoxToProduktTabelle(Produkt produkt) {
        ProduktUebersichtProduktBoxController produktUebersichtBoxController = new ProduktUebersichtProduktBoxController(
                kunde, produkt, this);
        produktBoxen.add(produktUebersichtBoxController.getView());
    }

    /**
     * Zeigt die View des Controllers
     */
    @Override
    public void zeigeDieseView() {
        kundeStage.switchToProduktUebersichtView();
    }

    /**
     * Setzt Filter für Bestand
     * 
     * @param bestand Bestand, welchen Produkte mind. haben müssen.
     */
    public void filter(int bestand) {
        if (bestand <= 0) {
            bestand = Integer.MIN_VALUE;
        }
        this.bestand = bestand;
    }

    /**
     * Setzt den geringsten Preis, den ein Produkt haben muss
     * 
     * @param minPreis minimaler Preis
     */
    public void setMinPreis(int minPreis) {
        this.minPreis = minPreis;
    }

    /**
     * Setzt den höchsten Preis, den ein Produkt haben darf
     * 
     * @param maxPreis maximaler Preis
     */
    public void setMaxPreis(int maxPreis) {
        this.maxPreis = maxPreis;
    }

    /**
     * Setzt alle Filter zurück
     */
    public void resetFilter() {
        this.maxPreis = Integer.MAX_VALUE;
        this.minPreis = 0;
        this.bestand = Integer.MIN_VALUE;
        this.kategorie = null;
    }
}
