package de.unims.sopra25.controller.shared;

import de.unims.sopra25.controller.einkaeufer.ProduktUebersichtController;
import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.logik.SortimentVerwalten;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.VBox;

/**
 * abstrakte Klasse als Vorlage fuer die ProduktUebersichtController
 * @author Irina Hoppe, Pia Maitzen
 */
public abstract class ProduktUebersichtControllerVorlage<T> {


	protected ListView<T> view;

	// ProduktTabelle, die der View des Controllers hinzugefuegt wird
	protected VBox produktTabelle;

	//ProduktListe
	protected ObservableList<Produkt> produkte;

    // Kategorie zum Filtern. Wenn null: Kein Filter
    protected Kategorie kategorie;
    //Alle ProduktBoxen
    protected ObservableList<T> produktBoxen;
    //Produktboxen, auf welche die Filter angewendet werden
    protected FilteredList<T> filteredProduktBoxen;

	/**
	 * Konstruktor, der den ProduktUeberischtController initialisiert
	 */
	public ProduktUebersichtControllerVorlage() {


		// Holt das Sortiment aus dem Backend
		this.produkte = new SortimentVerwalten().getProduktlist();
		// Erzeugt die View des Controllers

	}

	/**
	 * Initialisiert die ProduktBoxen
	 */
	protected void initialisiereProduktBoxen() {
        produktBoxen = FXCollections.observableArrayList();
		produkte.forEach(this::addProduktBoxToProduktTabelle);
        filteredProduktBoxen = new FilteredList<>(produktBoxen);
        view = new ListView<>(filteredProduktBoxen);
        view.setSelectionModel(new ProduktUebersichtController.NoSelectionModel<>());
	}


	/**
     * Gibt die ProduktUebersichtView zurueck
     *
     * @return ScrollPane
     */
	public ListView<T> getView() {
		return view;
	}

    /**
     * Reduziert die Produktübersicht auf Produkte
     * der gegebenen Kategorie.
     * Kategorie auf null setzen, um Filter zurückzusetzen.
     * @param kategorie Kategorie, welche angezeigt werden soll
     */
    public void filter(Kategorie kategorie){
        if(kategorie.getName().contains("Alle")){
            kategorie= null;
        }
        this.kategorie = kategorie;
    }

	/**
	 * abstrakte Methode zum hinzufuegen einer ProduktBox je nach Nutzer
	 * @param produkt
	 */
	protected abstract void addProduktBoxToProduktTabelle (Produkt produkt);


    /**
     * Methode zur Umsetzung der Suchfunktion.
     * @param text Anzuwendender Text-Filter
     */
    public abstract void suche(String text);

    /**
     * Methode zum Wechseln in diese Ansicht
     */
    public abstract void zeigeDieseView();

    /**
     * Lässt ListView nichts auswählen
     * @param <T>
     */
    public static class NoSelectionModel<T> extends MultipleSelectionModel<T> {

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
        }

        @Override
        public void selectAll() {
        }

        @Override
        public void selectFirst() {
        }

        @Override
        public void selectLast() {
        }

        @Override
        public void clearAndSelect(int index) {
        }

        @Override
        public void select(int index) {
        }

        @Override
        public void select(T obj) {
        }

        @Override
        public void clearSelection(int index) {
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void selectPrevious() {
        }

        @Override
        public void selectNext() {
        }
    }
}
