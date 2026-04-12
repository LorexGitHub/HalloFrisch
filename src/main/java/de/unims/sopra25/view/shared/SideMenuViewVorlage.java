package de.unims.sopra25.view.shared;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Abstrakte Klasse, die die Struktur der Szene SideMenuView vorgibt.
 * Ein SideMenuView ist eine VBox
 *
 * @author Frederik Fluegge
 */
public abstract class SideMenuViewVorlage extends VBox {

	// Icon Paths
	Image nachbestellungImage;

	// Image Views
	ImageView nachbestellungImageView;

	// Buttons
	Button nachbestellungButton;

	/**
	 * Konstruktor, der das SideMenu mit allen Nodes initialisiert
	 */
	public SideMenuViewVorlage() {
		initialisiereSideMenu();
	}

	/**
	 * Initialisiert das Side Menu
	 */
	private void initialisiereSideMenu() {
		// Image handling
		nachbestellungImage = new Image("file:./speicher/icons/produktuebersicht.png");
		nachbestellungImageView = new ImageView(nachbestellungImage);

		// Styling
		nachbestellungImageView.setFitHeight(40);
		nachbestellungImageView.setPreserveRatio(true);
		setSpacing(10);
		setAlignment(Pos.CENTER);

		// Button mit Bild
		nachbestellungButton = new Button();
		nachbestellungButton.setBackground(null);
		nachbestellungButton.setTooltip(new Tooltip("Produkt Übersicht"));
		nachbestellungButton.setGraphic(nachbestellungImageView);
	}

	/**
	 * Abstrakte Methode, die von den konkreten Implementierungen der verschiedenen
	 * Akteuren benutzt wird.
	 * Die konkreten Methoden koennen entscheiden, welche Komponenten sie anzeigen
	 * Baut ein SideMenu
	 * @return VBox
	 */
	public abstract VBox baueSideMenu();

	/**
	 * Gibt den ProduktUebersichtButton zurück.
	 * @return nachbestellungButton ProduktUebersichtTaste
	 */
	public Button getProduktUebersichtButton() {
		return nachbestellungButton;
	}
}
