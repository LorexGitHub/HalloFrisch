package de.unims.sopra25.view.shared;

import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Klasse die eine ProduktBox fuer ein Produkt in der ProduktUebersicht
 * darstellt
 * Erstellt werden hierbei Elemente in der ProduktBox,
 * die mindestens von 2 Unterklassen implementiert werden.
 *
 * @author Irina Hoppe
 */
public abstract class ProduktBoxViewVorlage extends HBox {

	// Abstaende
	private final int H_GAP = 10;
	private final int V_GAP = 10;

	// Image Path
	private Image image;

	// Images
	private ImageView imageView;

	// Labels
	private Label name;
	private Label preis;
	private Label bestandLabel;
	private Label bestandAnzahl;
	private Label nachbestelltLabel;
	private Label nachbestelltAnzahl;

	// TextFields
	private TextField anzahl;

	// Buttons
	private Button hinzufuegen;
	private Button bildButton;

	// Repraesentiertes Produkt
	private Produkt produkt;

	/**
	 * Konstruktor, der eine ProduktBoxVorlage initialsiert
	 * @param produkt
	 */
	public ProduktBoxViewVorlage(Produkt produkt) {
		this.produkt = produkt;
		initialisiereProduktZeilen();
	}

	/**
	 * Initialisiert die graphische Darstellung der Produktzeile mit den dazugehoerigen Komponenten
	 */
	public void initialisiereProduktZeilen() {
		
		// Stlying
		setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));
		setSpacing(30);
		setAlignment(Pos.CENTER_LEFT);

		// bild des Produkts
		image = new Image(produkt.getBild());
		imageView = new ImageView(image);
		imageView.setPreserveRatio(false);

		imageView.setFitHeight(100.0);
		imageView.setFitWidth(100.0);

		bildButton = new Button();
		bildButton.getStyleClass().add("clear-button");
		bildButton.setGraphic(imageView);

		// name des Products
		name = new Label(produkt.getName());
		name.setPrefWidth(300);
		name.setAlignment(Pos.CENTER);

		// Bestand des Produkts
		bestandLabel = new Label("Bestand:");
		bestandLabel.setPrefWidth(100);
		bestandLabel.setAlignment(Pos.CENTER);
		bestandAnzahl = new Label(Integer.toString(produkt.getBestand()));
		bestandAnzahl.setPrefWidth(50);
		bestandAnzahl.setAlignment(Pos.CENTER);

		// Anzahl der Nachbestellung
		nachbestelltLabel = new Label("Nachbestellt:");
		nachbestelltLabel.setPrefWidth(100);
		nachbestelltAnzahl = new Label(Integer.toString(produkt.getAnzahlNachbestellteExemplare()));
		nachbestelltAnzahl.setPrefWidth(50);

		// preis des Produkts
		preis = new Label(produkt.getPreisString());
		preis.setPrefWidth(50);
		preis.setAlignment(Pos.CENTER);

		// anzahl des Produkts
		anzahl = new TextField();
		anzahl.setPrefWidth(50);
		anzahl.setAlignment(Pos.CENTER);

		// hinzufuegenButton des Produkts
		hinzufuegen = new Button("+");
		hinzufuegen.setPrefWidth(38);
		hinzufuegen.setAlignment(Pos.CENTER);
		hinzufuegen.getStyleClass().add("round-button");

	}

	/**
	 * Abstrakte Methode um eine Produktzeile zu bauen
	 * @param bildButton, name, bestandLabel, bestandAnzahl, nachbestelltLabel, nachbestelltAnzahl,
	 *              preis, anzahl, hinzufuegen
	 */
	public abstract void baueProduktZeile(Button bildButton, Label name, Label bestandLabel, Label bestandAnzahl,
			Label nachbestelltLabel, Label nachbestelltAnzahl, Label preis, TextField anzahl, Button hinzufuegen);

	/**
	 * Gibt das Label mit dem Namen des Produkts zurueck
	 * @return name Label fuer Namen des Produkts
	 */
	public Label getName() {
		return name;
	}

	/**
	 * Getter für das Preis-Label
	 * @return preis Label fuer Preis des Produkts
	 */
	public Label getPreis() {
		return preis;
	}

	/**
	 * Getter fuer das Bestand Label
	 * @return bestandLabel Label fuer Bestand des Produkts
	 */
	public Label getBestandLabel() {
		return bestandLabel;
	}

	/**
	 * Getter fuer Bestand Label
	 * @return bestandAnzahl Label fuer Bestand
	 */
	public Label getBestandAnzahl() {
		return bestandAnzahl;
	}

	/**
	 * Getter fuer Nachbestell Label
	 * @return nachbestelltLabel;
	 */
	public Label getNachbestelltLabel() {
		return nachbestelltLabel;
	}

	/**
	 * Gibt das Label aus, welches die Anzahl an
	 * nachbestellten Exemplaren anzeigt
	 * @return nachbestelltAnzahl Label fuer nachbestellt
	 */
	public Label getNachbestelltAnzahl() {
		return nachbestelltAnzahl;
	}

	/**
	 * Getter fuer Anzahl Feld
	 * @return anzahl Textfield fuer Anzahl
	 */
	public TextField getAnzahlField() {
		return anzahl;
	}

	/**
	 * Getter fuer Hinzufuege Button
	 * @return hinzufuegen Button fuerHinzufuege
	 */
	public Button getHinzufuegeButton() {
		return hinzufuegen;
	}

	/**
	 * Bild des Produkts mit Button
	 * @return bildButton Button auf Bild
	 */
	public Button getBildButton() {
		return bildButton;
	}

	/**
	 * Getter fuer Produkt, welches diese Box gehoert
	 * @return produkt
	 */
	public Produkt getProdukt() {
		return produkt;
	}
}
