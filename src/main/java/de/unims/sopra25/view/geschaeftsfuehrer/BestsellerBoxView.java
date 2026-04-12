package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Klasse, die die BestsellerBoxView des Geschaeftsfuehrers
 * 
 * @author Leonhard Heß, Lukas Tilgner
 */
public class BestsellerBoxView extends ProduktBoxViewVorlage {

	private Label verkauftLabel;
	private Label verkauftAnzahl;

	/**
	 * Konstruktor der Klasse BestsellerBoxView
	 * @param produkt Dem Konstruktor wird ein Produkt uebergeben
	 */
	public BestsellerBoxView(Produkt produkt) {
		super(produkt);
		verkauftLabel = new Label();
		baueProduktZeile(getBildButton(), getName(), getBestandAnzahl(), getBestandLabel(), getNachbestelltAnzahl(),
				getNachbestelltLabel(), getPreis(), getAnzahlField(), getHinzufuegeButton());
	}

	/**
	 * Setzt eine Produktzeile mit den dazugehoerigen Komponenten zusammen
	 * @param bildButton, name, bestandLabel, bestandAnzahl, nachbestelltLabel, nachbestelltAnzahl, preis, anzahl, hinzufuegen
	 */
	@Override
	public void baueProduktZeile(Button bildButton, Label name, Label bestandLabel, Label bestandAnzahl,
			Label nachbestelltLabel, Label nachbestelltAnzahl, Label preis, TextField anzahl, Button hinzufuegen) {
		verkauftLabel.setText("Verkauft: ");
		verkauftAnzahl = bestandAnzahl;
		getChildren().addAll(bildButton, name, verkauftLabel, verkauftAnzahl);
	}

	/**
	 * Getter Methode fuer VerkauftLabel
	 * @return Label fuer Verkauft
	 */
	public Label getVerkauftLabel() {
		return verkauftLabel;
	}

	/**
	 * Getter Methode fuer Verkauft Anzahl anzeige 
	 * @return Label fuer Verkauft Anzahl
	 */
	public Label getVerkauftAnzahl() {
		return verkauftAnzahl;
	}
}
