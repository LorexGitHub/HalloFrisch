package de.unims.sopra25.view.kunde;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Klasse zum Anzeigen eines Produkts
 * in Detailansicht mit Name, Bild,
 * Beschreibung, Kategorien, Preis.
 * Zudem wird ein Textfeld Anzahl und ein
 * Button zum Hinzufügen des Produktes zum
 * Warenkorb (mit der gewünschten Anzahl)
 * bereitgestellt
 *
 * @author Johannes Schick
 */
public class ProduktDetailView extends HBox {

	// Anzuzeigende Elemente des Produkts
	private final Label name;
	private final Label beschreibung;
	private final Label preis;
	private final Label anzahlLabel;
	private final ImageView bild;
	private final Circle verfuegbarkeitKreis;
	private final Label verfuegbarkeitBeschreibung;

	// Elemente zum Hinzufuegen in Warenkorb
	private final TextField anzahlField;
	private final Button hinzufuegen;
	private final Button zurueckHome;


	// Im Linke Teil werden Name und Bild angezeigt
	private final VBox links;
	// Im rechten Teil die übrigen Informationen
	private final VBox rechts;
	// Anzeige Verfügbarkeit
	private final HBox verfuegbarkeit;
	// Anzeige von allen Kategorien
	private HBox kategorien;
	// Anzeige vom Anzahl Input
	private HBox anzahl;

	/**
	 * Konstruktor laedt alle Informationen aus
	 * produkt, die dem Kunden angezeigt werden
	 * sollen.
	 * @param produkt anzuzeigendes Produkt
	 */
	public ProduktDetailView(Produkt produkt) {
		super(30);
		links = new VBox(10);
		rechts = new VBox(20);
		verfuegbarkeit = new HBox(4);
		verfuegbarkeitKreis = new Circle(3);
		verfuegbarkeitBeschreibung = new Label();

        // Lade Informationen aus Produkt
        name = new Label(produkt.getName());
        beschreibung = new Label(produkt.getBeschreibung());
        preis = new Label(produkt.getPreisString());
        anzahlLabel = new Label("Anzahl:");
        bild = new ImageView(new Image(produkt.getBild(), true));
        bild.setFitHeight(200);
        bild.setFitWidth(200);

		initKategorien(produkt);

		// Warenkorb-Funktionalitaet
		anzahlField = new TextField("1");
		hinzufuegen = new Button("Zum Warenkorb hinzufügen");
		zurueckHome = new Button("Zurück zur Home Page");

		// Anzeigen
		setLayout();
		verfuegbarkeit.getChildren().addAll(verfuegbarkeitKreis, verfuegbarkeitBeschreibung);
		links.getChildren().setAll(bild, kategorien);
		rechts.getChildren().setAll(name, beschreibung, preis, verfuegbarkeit, anzahl, hinzufuegen, zurueckHome);
		this.getChildren().setAll(links, rechts);
	}

	/**
	 * Erstellt Labels fuer die Kategorien
	 * @param produkt anzuzeigendes Produkt
	 */
	private void initKategorien(Produkt produkt) {
		Label label;
		kategorien = new HBox(5);
		for (Kategorie kategorie : produkt.getKategorien()) {
			label = new Label(kategorie.getName());
			CornerRadii corn = new CornerRadii(5);
			Background background = new Background(new BackgroundFill(Color.ORANGE, corn, Insets.EMPTY));
			label.setBackground(background);
			label.setPadding(new Insets(2, 8, 2, 8));

			kategorien.getChildren().add(label);
		}
	}

	/**
	 * Methode zum Designen des Fensters
	 */
	private void setLayout() {
		name.setFont(Font.font("Arial", FontWeight.BOLD, 70));
		beschreibung.setFont(Font.font("Arial", 30));
		preis.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		preis.setTextFill(Color.GREEN);
		kategorien.setAlignment(Pos.CENTER);
		anzahl = new HBox(anzahlLabel, anzahlField);
		anzahl.setSpacing(20);
		anzahl.setAlignment(Pos.CENTER_LEFT);
		anzahlField.setMaxWidth(40);
		verfuegbarkeit.setAlignment(Pos.CENTER_LEFT);


		bild.setPreserveRatio(false);
		bild.setFitHeight(300.0);
		bild.setFitWidth(300.0);

	}

	/**
	 * Zeigt an, dass Produkt verfuegbar ist.
	 */
	public void setVerfuegbar() {
		verfuegbarkeitBeschreibung.setText("Auf Lager");
		verfuegbarkeitBeschreibung.setTextFill(Color.GREEN);
		verfuegbarkeitKreis.setFill(Color.GREEN);
	}

	/**
	 * Zeigt an, dass nur wenige Exemplare verfuegbar sind.
	 */
	public void setWenigVerfuegbar() {
		verfuegbarkeitBeschreibung.setText("Wenige auf Lager");
		verfuegbarkeitBeschreibung.setTextFill(Color.ORANGE);
		verfuegbarkeitKreis.setFill(Color.ORANGE);
	}

	/**
	 * Zeigt an, dass aktuell nicht vorraetig ist.
	 */
	public void setNichtVerfuegbar() {
		verfuegbarkeitBeschreibung.setText("Nicht auf Lager, längere Lieferzeit");
		verfuegbarkeitBeschreibung.setTextFill(Color.RED);
		verfuegbarkeitKreis.setFill(Color.RED);

	}

	/**
	 * Message, die gezeigt wird, wenn ein Produkt erfolgreich zum Warenkorb hinzugefuegt wurde
	 */
	public void successMessage(){
		Text successMessage = new Text("Produkt erfolgreich zum Warenkorb hinzugefügt.");
		successMessage.setStyle("-fx-fill: green");

		rechts.getChildren().add(successMessage);
		this.getChildren().setAll(links, rechts);
	}

	/**
	 * Gibt Textlabel des Namens aus
	 * @return name label
	 */
	public Label getName() {
		return name;
	}

	/**
	 * Gibt Textlabel der Beschreibung aus
	 * @return beschreibung label
	 */
	public Label getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Gibt Label des Preises aus
	 * @return preis label
	 */
	public Label getPreis() {
		return preis;
	}

	/**
	 * Gibt die Bildanzeige aus
	 * @return bild Bildanzeige
	 */
	public ImageView getBild() {
		return bild;
	}

	/**
	 * Gibt Textfeld aus, mit welchem der
	 * Kunde die Anzahl fuer den Warenkorb
	 * festlegen kann.
	 * @return anzahlField textfeld Anzahl
	 */
	public TextField getAnzahl() {
		return anzahlField;
	}

	/**
	 * Gibt Button aus, mit welchem das Produkt
	 * in den Warenkorb gelegt wird.
	 * @return hinzufuegen button Hinzufuegen
	 */
	public Button getHinzufuegen() {
		return hinzufuegen;
	}

	/**
	 * Gibt Button aus, mit welchem das Produkt
	 * in den Warenkorb gelegt wird.
	 * @return zurueckHome button Hinzufuegen
	 */
	public Button getZurueckHome() {
		return zurueckHome;
	}

	/**
	 * Gibt linken Teilbildschirm aus.
	 * In dem Teil sind Name und Bild.
	 * @return links Teilbildschirm
	 */
	public VBox getLinks() {
		return links;
	}

	/**
	 * Gibt rechten Teilbildschirm aus.
	 * In dem Teil sind Beschreibung, Kategorien,
	 * Preis und Warenkorb-Elemente.
	 * @return rechts Teilbildschirm
	 */
	public VBox getRechts() {
		return rechts;
	}
}
