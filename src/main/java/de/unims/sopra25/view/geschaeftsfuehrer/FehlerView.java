package de.unims.sopra25.view.geschaeftsfuehrer;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Klasse, die die Szene FehlerView zeichnet
 * 
 * @author Frederik Fluegge
 */
public class FehlerView extends BorderPane {
	// Tabellen Teile setzen
	private ListView<String> table;
	private HBox titelBox;
	private Label titel;

	/**
	 * Konstruktor der Klasse FehlerView 
	 * @param fehler
	 */
	public FehlerView(ObservableList<String> fehler) {

		initialisiereTable(fehler);
	}

	/**
	 * Konstruiert die Tabelle aus Fehler Logs 
	 * @param fehler
	 */
	private void initialisiereTable(ObservableList<String> fehler) {
		table = new ListView<String>(fehler);
		titel = new Label("Fehlerberichte");
		titelBox = new HBox(titel);

		titel.setTextFill(Color.color(1, 0, 0));
		titelBox.setAlignment(Pos.BASELINE_CENTER);
		this.setTop(titelBox);
		this.setCenter(table);
	}

}
