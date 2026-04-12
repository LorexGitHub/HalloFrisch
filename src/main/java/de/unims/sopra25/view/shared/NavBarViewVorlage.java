package de.unims.sopra25.view.shared;

import de.unims.sopra25.model.entitaeten.Kategorie;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Abstrakte Klasse, die die Struktur der Szene NavBarView vorgibt.
 *
 * @author Frederik Fluegge
 */
public abstract class NavBarViewVorlage<T> extends HBox {

	// Image
	protected Image logo;
	protected ImageView logoImage;
	// TextField
	protected TextField search;
	// Buttons
	protected Button startSearchButton;
	protected Button logoButton;
	protected Button logOutButton;
	// ComboBox
	protected ComboBox<T> filter;

	/**
	 * Konstruktor der die NavBar mit allen Nodes initialisiert
	 */
	public NavBarViewVorlage() {
		initialisiereNavBar();
	}

	/**
	 * Initialisiert die graphische Darstellung der NavBar mit den dazugehoerigen
	 * Komponenten
	 */
	private void initialisiereNavBar() {

		// Programm Logo mit Button
		logo = new Image("Logo.png");
		logoImage = new ImageView(logo);
		logoImage.setFitWidth(100.0);
		logoImage.setPreserveRatio(true);
		logoButton = new Button();
		logoButton.getStyleClass().add("clear-button");
		logoButton.setGraphic(logoImage);
		logoButton.setBackground(null);

		// Programm LogOut mit Button
		logOutButton = new Button("LogOut");

		// Suchleiste
		search = new TextField();

		// Suchleiste
		search = new TextField();
		search.setPrefWidth(340);
		search.setPromptText("Suche...");

		// Search Button
		startSearchButton = new Button("Suche");

		// Filter Button
		filter = new ComboBox<T>();

		// Styling
		setSpacing(20);
		setAlignment(Pos.CENTER);
	}

	/**
	 * Abstrakte Methode, die von den einzelnen Implementierungen der verschiedenen
	 * Akteuren benutzt wird.
	 * Die konkreten Methoden koönnen dann entscheiden, welche Komponenten sie
	 * anzeigen möchten
	 *
	 * @param logoButton   Logo des Unternehmens
	 * @param search       Suchleiste
	 * @param startSearch  Suche initializiere Button
	 * @param filter       Filter ComboBox
	 * @param logOutButton Logout Button
	 * @return HBox NavBar Anzeige
	 */
	public abstract HBox baueNavBar(Button logoButton, TextField search, Button startSearch,
			ComboBox filter, Button logOutButton);

	/**
	 * Getter fuer searchTextField
	 * 
	 * @return search
	 */
	public TextField getSearchTextfield() {
		return search;
	}

	/**
	 * Getter fuer startSearchButton
	 * 
	 * @return startSearchButton Button
	 */
	public Button getStartSearchButton() {
		return startSearchButton;
	}

	/**
	 * Getter fuer filterButton
	 * 
	 * @return filter Button
	 */
	public ComboBox<T> getFilterBox() {
		return filter;
	}

	/**
	 * Getter für Logo-Button
	 *
	 * @return logo Button
	 */
	public Button getLogoButton() {
		return logoButton;
	}

	/**
	 * Getter fuer logOutButton
	 * 
	 * @return logOutButton
	 */
	public Button getLogOutButton() {
		return logOutButton;
	}
}
