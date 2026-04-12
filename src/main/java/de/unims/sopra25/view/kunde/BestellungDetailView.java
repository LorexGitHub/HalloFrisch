package de.unims.sopra25.view.kunde;

import java.util.List;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_BestellungProduktAssoz;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Die Klasse Bestelldetailview zeigt eine Liste (getätigte Bestellung) an
 * Produkten inkl. Anzahl und damaligen Preis an
 * 
 * @author Constantin Meininghaus
 */
public class BestellungDetailView extends ScrollPane {

	// Imagestyling Konstante
	private final int MAX_IMAGE_HEIGHT = 100;

	// VBox
	private VBox produktTabelle;
	private VBox ansicht;

	// Label
	private Label adresseLabel;

	public BestellungDetailView(K_Bestellung bestellung) {
		List<K_BestellungProduktAssoz> bestellungProdukteAssoz = bestellung.getProdukte();
		setPrefWidth(962);
		produktTabelle = new VBox(10);
		adresseLabel = new Label("Lieferadresse: " + bestellungProdukteAssoz.getFirst().getAdresse());
		ansicht = new VBox();
		ansicht.setAlignment(Pos.CENTER);
		ansicht.getChildren().addAll(adresseLabel, produktTabelle);

		// Loop erzeugt HBoxen mit gewuenschten Produktdaten
		for (K_BestellungProduktAssoz produktAssoz : bestellungProdukteAssoz) {

			// erzeuge Produktbox
			HBox produktBox = new HBox(180);
			produktBox.setPrefWidth(962);
			produktBox.setPrefHeight(MAX_IMAGE_HEIGHT);

			ImageView imageView = null;
			// Produktbild imageView
			try {
				Image image = new Image(produktAssoz.getBild());
				imageView = new ImageView(image);
				imageView.setPreserveRatio(false);
				imageView.setFitHeight(MAX_IMAGE_HEIGHT);
				imageView.setFitWidth(MAX_IMAGE_HEIGHT);
			} catch (NullPointerException e) {
				System.out.println("Image not available");
				Image image = new Image("file:./speicher/icons/error.jpg");
				imageView = new ImageView(image);
			}


			// Erzeugen der Labels
			Label name = new Label(produktAssoz.getName());
			name.setPrefWidth(200);
            VBox preisBox = new VBox();
            Label preisLabel = new Label("Preis:");
			Label preis = new Label(produktAssoz.getPreisString());
            Region regionPreis = new Region();
            regionPreis.setPrefHeight(15);
            preis.setPrefWidth(200);
            preisBox.getChildren().addAll(regionPreis, preisLabel, preis);
			
            VBox anzahlBox = new VBox();
            Label anzahlLabel = new Label("Anzahl:");
			Label anzahl = new Label(String.valueOf(produktAssoz.getAnzahl()));
            Region regionAnzahl = new Region();
            regionAnzahl.setPrefHeight(15);
            anzahl.setPrefWidth(200);
            anzahlBox.getChildren().addAll(regionAnzahl, anzahlLabel, anzahl);

			// Styling der Label
            Region regionBox1 = new Region();
            regionBox1.setPrefWidth(2);
            Region regionBox2 = new Region();
            regionBox2.setPrefWidth(2);
			produktBox.getChildren().addAll(imageView, name, preisBox, anzahlBox);
			produktBox.setAlignment(Pos.CENTER);
			produktTabelle.getChildren().add(produktBox);
		}

		setContent(ansicht);
	}
}
