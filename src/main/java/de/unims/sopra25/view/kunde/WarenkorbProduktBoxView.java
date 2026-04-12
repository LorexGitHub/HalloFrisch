package de.unims.sopra25.view.kunde;

import de.unims.sopra25.model.entitaeten.WarenkorbProduktAssoz;
import de.unims.sopra25.view.shared.ProduktBoxViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Klasse die eine WarenkorbProduktBox repräsentiert
 * 
 * @author Johannes Schick
 */
public class WarenkorbProduktBoxView extends ProduktBoxViewVorlage {

    // Buttons
    private Button aktualisieren;
    private Button loeschen;
    
    private Region spacer;

	/**
	 * Konstruktor, der eine ProduktBoxVorlage initialsiert
	 * @param produkt
	 */
	public WarenkorbProduktBoxView(WarenkorbProduktAssoz produkt) {
		super(produkt.getProdukt());
		baueProduktZeile(getBildButton(), getName(), getBestandAnzahl(), getBestandLabel(), getNachbestelltAnzahl(),
				getNachbestelltLabel(), getPreis(), getAnzahlField(), getHinzufuegeButton());
	}

    @Override
    public void baueProduktZeile(Button bildButton, Label name, Label bestandLabel, Label bestandAnzahl,
            Label nachbestelltLabel, Label nachbestelltAnzahl, Label preis, TextField anzahl, Button hinzufuegen) {
        // diese View hat zwei weitere Buttons
		// Spacing of the elements
		Region spacer = new Region();
		spacer.setPrefWidth(30);
    	aktualisieren = new Button("aktualisieren");
        loeschen = new Button("entfernen");
        getChildren().addAll(bildButton, name, spacer, preis, anzahl, aktualisieren, loeschen);
    }

	/**
	 * Getter Methode fuer aktualisieren-Button 
	 * @return aktualisieren Button
	 */
	public Button getAktualisieren() {
		return aktualisieren;
	}

	/**
	 * Getter Methode fuer loeschen-Button 
	 * @return loeschen Button
	 */
	public Button getLoeschen() {
		return loeschen;
	}
}
