package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

/**
 * Klasse, die die Szene ProduktDetailView zeichnet
 * @author Frederik Fluegge
 */
public class ProduktDetailView extends HBox{

	// Anzuzeigende Elemente des Produkts
	private final Label name;
	private final Label beschreibung;
	private final Label preis;
	private final ImageView bild;

	// Im Linke Teil werden Name und Bild angezeigt
	private final VBox links;
	// Im rechten Teil die übrigen Informationen
	private final VBox rechts;
	// Anzeige von allen Kategorien
	private HBox kategorien;

	/**
	 * Konstruktor der Klasse ProduktDetailView
	 * @param produkt
	 */
	public ProduktDetailView(Produkt produkt){
		super(30);
		links = new VBox(10);
		rechts = new VBox(20);

        // Lade Informationen aus Produkt
        name = new Label(produkt.getName());
        beschreibung = new Label(produkt.getBeschreibung());
        preis = new Label(produkt.getPreisString());
        bild = new ImageView(new Image(produkt.getBild()));
        bild.setFitWidth(200);
        bild.setFitHeight(200);
        initKategorien(produkt);

		// Anzeigen
		setLayout();
		links.getChildren().setAll(bild, kategorien);
		rechts.getChildren().setAll(name, beschreibung, preis);
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
	 * Methode zum designen des Fensters
	 */
	private void setLayout() {
		name.setFont(Font.font("Arial", FontWeight.BOLD, 70));
		beschreibung.setFont(Font.font("Arial", 30));
		preis.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		preis.setTextFill(Color.GREEN);
		kategorien.setAlignment(Pos.CENTER);
	}
}
