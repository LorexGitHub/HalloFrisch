package de.unims.sopra25.view.kunde;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.shared.BestellungBoxViewVorlage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Klasse fuer eine Bestellungszeile in der BestellungsUebersicht eines Kunden
 * 
 * @author Irina Hoppe
 */
public class BestellungBoxView extends BestellungBoxViewVorlage {

	public BestellungBoxView(K_Bestellung bestellung) {
		super(bestellung);
	}

	/**
	 * Methode zum bauen einer Bestellungsbox fuer den Kunden
	 * @param nummer, datum, status, zahlungsmittel, tag, annehmen, ausliefern
	 */
	@Override
	public void baueBestellungZeile(Button nummer, Label datum, Label status, Label zahlungsmittel, VBox tag,
			Button annehmen, Button ausliefern) {
		// fuege nur relevante Elemente hinzu

		setPrefWidth(962);
		setSpacing(150);
		getChildren().addAll(nummer, datum, status);
	}
}
