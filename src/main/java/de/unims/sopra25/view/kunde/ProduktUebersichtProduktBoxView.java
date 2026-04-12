package de.unims.sopra25.view.kunde;

import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import de.unims.sopra25.model.entitaeten.Produkt;

/**
 * Klasse fuer eine Produktbox in der ProduktUebersicht des Kunden
 * 
 * @author Irina Hoppe
 */
public class ProduktUebersichtProduktBoxView extends ProduktBoxViewVorlage {

	/**
	 * Konstruktor, der eine ProduktBox erstellt
	 * @param produkt
	 */
	public ProduktUebersichtProduktBoxView(Produkt produkt) {
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
			Label nachbestelltAnzahl ,Label preis , TextField anzahl, Button hinzufuegen) {
		getChildren().addAll(bildButton, name, preis, anzahl, hinzufuegen);
	}
}
