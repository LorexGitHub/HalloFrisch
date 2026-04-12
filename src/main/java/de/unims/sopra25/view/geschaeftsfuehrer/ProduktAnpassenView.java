package de.unims.sopra25.view.geschaeftsfuehrer;

import java.util.concurrent.TimeUnit;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Klasse, die die Szene ProduktAnpassen zeichnet
 *
 * @author Frederik Fluegge
 */
public class ProduktAnpassenView extends HBox {

	// Labels
	private final Label name;
	private final Label beschreibung;
	private final Label hinweis;

	// TextFields
	private final TextField preis;

	// Bild
	private final ImageView bild;

	// Buttons
	private Button bestaetigen;
	private Button loeschen;
	private Button loeschenAbbrehcen;
	private Button loeschenBestaetigen;
	private Button zurueckZurHomePage;

	// Im Linke Teil werden Name und Bild angezeigt
	private final VBox links;
	// Im rechten Teil die übrigen Informationen
	private final VBox rechts;
	// Anzeige von allen Kategorien
	private HBox kategorien;

	// Error / Success Message
	private Text notifyMessage;

    /**
     * Konstruktor der Klasse ProduktAnpassenView 
     * @param produkt Produkt, das angepasst wird
     */
    public ProduktAnpassenView(Produkt produkt) {
        super(30);
        links = new VBox(10);
        rechts = new VBox(20);

        // Lade Informationen aus Produkt
        name = new Label(produkt.getName());
        beschreibung = new Label(produkt.getBeschreibung());
        preis = new TextField();
        preis.setPromptText("Hier neuen Preis eintragen");
        bild = new ImageView(new Image(produkt.getBild()));
        bild.setFitWidth(200);
        bild.setFitHeight(200);
        initKategorien(produkt);
        bild.setFitHeight(200);
        bild.setFitWidth(200);
        bestaetigen = new Button("Änderungen bestätigen");
        loeschen = new Button("Produkt aus Sortiment löschen");
        zurueckZurHomePage = new Button("Zurück zur Home Page");

		// Content des Pop Up Windows
		loeschenAbbrehcen = new Button("Abbrechen");
		loeschenBestaetigen = new Button("Löschen Bestätigen");
		hinweis = new Label("Sind Sie sich sicher, dass sie das Produkt löschen möchten?");
		notifyMessage = new Text();

		// Anzeigen
		setLayout();
		links.getChildren().setAll(bild, kategorien);
		rechts.getChildren().setAll(name, beschreibung, preis, bestaetigen, loeschen, zurueckZurHomePage);
		this.getChildren().setAll(links, rechts);

	}

	/**
	 * Erstellt Labels für die Kategorien
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
		kategorien.setAlignment(Pos.CENTER);
	}

	/**
	 * Oeffnet ein PopUp zum Bestaetigen des Loeschens
	 */
	public void openPopUp() {
		rechts.getChildren().addAll(hinweis, loeschenAbbrehcen, loeschenBestaetigen);
		this.getChildren().setAll(links, rechts);
	}

	/**
	 * Oeffnet ein PopUp zum Bestaetigen des Loeschens
	 */
	public void closePopUp() {
		rechts.getChildren().clear();
		rechts.getChildren().setAll(name, beschreibung, preis, bestaetigen, loeschen, zurueckZurHomePage);
		this.getChildren().setAll(links, rechts);
	}

	/**
	 * Gibt eine Meldung zurueck, wenn das Produkt erfolgreich geloescht wurde
	 */
	public void deleteSuccess() {

		notifyMessage
		.setText("Das Produkt wurde erfolgreich gelöscht. Sie können jetzt zur Home Page zurück navigieren.");
		notifyMessage.setStyle("-fx-fill: green");

		HBox successBox = new HBox();
		successBox.getChildren().add(notifyMessage);
		this.getChildren().add(successBox);

	}

	/**
	 * Gibt eine Meldung zurück, wenn der Preis des Produkts erfolgreich angepasst wurde
	 */
	public void editSuccess() {

		System.out.println("TEST");
		notifyMessage.setText(
				"Der Preis des Produkts wurde erfolgreich geändert. Sie können jetzt zur Home Page zurück navigieren.");
		notifyMessage.setStyle("-fx-fill: green");

		HBox successBox = new HBox();
		successBox.getChildren().add(notifyMessage);
		getChildren().add(successBox);

	}

	/**
	 * Gibt den Button zum Bestaetigen der Änderungen zurueck 
	 * @return bestaetigen
	 */
	public Button getBestaetigenButton() {
		return bestaetigen;
	}

	/**
	 * Gibt den Button zum Loeschen des Produkts zurueck 
	 * @return loeschen
	 */
	public Button getLoeschenButton() {
		return loeschen;
	}

	/**
	 * Gibt das TextField zum Aendern des Preises zurueck 
	 * @return preis
	 */
	public TextField getPreisField() {
		return preis;
	}

	/**
	 * Gibt den Button zum Loeschen des Produkts zurueck 
	 * @return loeschenBestaetigen
	 */
	public Button getLoeschenBestaetigenButton() {
		return loeschenBestaetigen;
	}

	/**
	 * Gibt den Button zum Bestaetigen des Loeschens des Produkts zurueck 
	 * @return loeschenAbbrehcen
	 */
	public Button getLoeschenAbbrechenButton() {
		return loeschenAbbrehcen;
	}

	/**
	 * Gibt den Button zurueck, mit dem zur HomePage zuruecknavigiert werden kann 
	 * @return zurueckZurHomePage
	 */
	public Button getZurueckButton() {
		return zurueckZurHomePage;
	}
}
