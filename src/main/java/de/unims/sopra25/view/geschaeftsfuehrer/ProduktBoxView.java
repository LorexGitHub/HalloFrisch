package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Klasse fuer eine ProduktBox in der ProduktUebersicht des Geschaeftsfuehrer
 * 
 * @author Irina Hoppe
 */
public class ProduktBoxView extends ProduktBoxViewVorlage {

	private Button bearbeiten;

	/**
	 * Konstruktor
	 * Erstellt eine ProduktBox fuer den Geschaeftsfuehrer
	 * @param produkt
	 */
	public ProduktBoxView(Produkt produkt) {
		super(produkt);
		baueProduktZeile(getBildButton(), getName(), getBestandAnzahl(), getBestandLabel(), getNachbestelltAnzahl(),
				getNachbestelltLabel(), getPreis(), getAnzahlField(), getHinzufuegeButton());
	}

	/**
	 * Setzt eine Produktzeile mit den dazugehoerigen Komponenten zusammen
	 * @param bildButton, name, bestandLabel, bestandAnzahl, nachbestelltLabel, nachbestelltAnzahl, preis, anzahl, hinzufuegen
	 */
	@Override
	public void baueProduktZeile(Button bildButton, Label name, Label bestandLabel, Label bestandAnzahl, Label nachbestelltLabel,
			Label nachbestelltAnzahl, Label preis, TextField anzahl, Button hinzufuegen) {
		// diese view hat einen weiteren Button
		bearbeiten = new Button("Bearbeiten");
		bearbeiten.setPrefWidth(100);
		bearbeiten.setAlignment(Pos.CENTER);
		getName().setPrefWidth(100);
		setSpacing(5);
		HBox bestandBox = new HBox();
		getBestandAnzahl().setPrefWidth(20);
		getBestandAnzahl().setPrefWidth(20);
		bestandBox.getChildren().addAll(bestandAnzahl, bestandLabel);
		bestandBox.setSpacing(1);
		bestandBox.setAlignment(Pos.CENTER);
		getChildren().addAll(bildButton, name, bestandBox, preis, bearbeiten);
	}

	/**
	 * Gib Button zum bearbeiten des Produkts zurueck 
	 * @return bearbeiten Button
	 */
	public Button getBearbeitenButton() {
		return bearbeiten;
	}
}
