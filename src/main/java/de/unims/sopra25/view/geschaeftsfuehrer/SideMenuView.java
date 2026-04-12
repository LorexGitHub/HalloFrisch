package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.view.shared.SideMenuViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Klasse, die die Szene SideMenuView fuer den Geschaeftsfuehrer zeichnet
 *
 * @author Frederik Fluegge
 */
public class SideMenuView extends SideMenuViewVorlage {

	// Icons Paths
	private Image umsatzImage;
	private Image bestsellerImage;
	private Image nutzungImage;
	private Image fehlerLogsImage;
	private Image registrierenImage;

	// JavaFX Image
	private ImageView umsatzBild;
	private ImageView bestsellerBild;
	private ImageView nutzungBild;
	private ImageView fehlerLogsBild;
	private ImageView registrierenBild;

	// Buttons
	private Button umsatzButton;
	private Button bestsellerButton;
	private Button nutzungButton;
	private Button fehlerLogsButton;
	private Button registrierenButton;

	/**
	 * Konstruktor initialisiert alle Nodes des SideMenus
	 */
	public SideMenuView() {
		super();
		baueSideMenu();
	}

    /**
     * Baut eine SideMenu für den Geschäftsführer
     * Benutzt werden dabei nur Parameter, die für den Geschaeftsfuehrer relevant
     * sind.
     *
     * @return VBox
     */
    @Override
    public VBox baueSideMenu() {

        // Icons Paths
        umsatzImage = new Image("file:./speicher/icons/umsatzuebersicht.png");
        bestsellerImage = new Image("file:./speicher/icons/bestseller.png");
        nutzungImage = new Image("file:./speicher/icons/nutzerstatistik.png");
        fehlerLogsImage = new Image("file:./speicher/icons/fehlerlogs.png");
        registrierenImage = new Image("file:./speicher/icons/nutzerregistrieren.png");

        // JavaFX Image
        umsatzBild = new ImageView(umsatzImage);
        bestsellerBild = new ImageView(bestsellerImage);
        nutzungBild = new ImageView(nutzungImage);
        fehlerLogsBild = new ImageView(fehlerLogsImage);
        registrierenBild = new ImageView(registrierenImage);

        // Styling
        umsatzBild.setFitHeight(40);
        umsatzBild.setPreserveRatio(true);
        bestsellerBild.setFitHeight(40);
        bestsellerBild.setPreserveRatio(true);
        nutzungBild.setFitHeight(40);
        nutzungBild.setPreserveRatio(true);
        fehlerLogsBild.setFitHeight(40);
        fehlerLogsBild.setPreserveRatio(true);
        registrierenBild.setFitHeight(40);
        registrierenBild.setPreserveRatio(true);
        setSpacing(10);
        setAlignment(Pos.CENTER);

        // Buttons
        umsatzButton = new Button();
        bestsellerButton = new Button();
        nutzungButton = new Button();
        fehlerLogsButton = new Button();
        registrierenButton = new Button();

        // Icon auf dem Button laden
        umsatzButton.setGraphic(umsatzBild);
        bestsellerButton.setGraphic(bestsellerBild);
        nutzungButton.setGraphic(nutzungBild);
        fehlerLogsButton.setGraphic(fehlerLogsBild);
        registrierenButton.setGraphic(registrierenBild);

        // Button Background löschen
        umsatzButton.setBackground(null);
        bestsellerButton.setBackground(null);
        nutzungButton.setBackground(null);
        fehlerLogsButton.setBackground(null);
        registrierenButton.setBackground(null);

        // ToolTipps
        umsatzButton.setTooltip(new Tooltip("Umsatzsstatistik"));
        bestsellerButton.setTooltip(new Tooltip("Bestseller"));
        nutzungButton.setTooltip(new Tooltip("Nutzungsstatistik"));
        fehlerLogsButton.setTooltip(new Tooltip("FehlerLogs"));
        registrierenButton.setTooltip(new Tooltip("Neuer Nutzer registrieren"));

        // Alignment
        setSpacing(10);
        setAlignment(Pos.CENTER);

        Region spacer = new Region();
        spacer.setPrefWidth(10);
        HBox button1Box = new HBox();
        button1Box.getChildren().addAll(getProduktUebersichtButton(), spacer);
        HBox button2Box = new HBox();
        button2Box.getChildren().addAll(umsatzButton, spacer);
        HBox button3Box = new HBox();
        button3Box.getChildren().addAll(bestsellerButton, spacer);
        HBox button4Box = new HBox();
        button4Box.getChildren().addAll(nutzungButton, spacer);
        HBox button5Box = new HBox();
        button5Box.getChildren().addAll(fehlerLogsButton, spacer);
        HBox button6Box = new HBox();
        button6Box.getChildren().addAll(registrierenButton, spacer);

        getChildren().addAll(button1Box, button2Box, button3Box, button4Box, button5Box, button6Box);

        return this;
    }

	/**
	 * Gibt den UmsatzButton zurueck.
	 * @return umsatzButton Umsatztaste
	 */
	public Button getUmsatzButton() {
		return umsatzButton;
	}

	/**
	 * Gibt den BestsellerButtonn zurueck.
	 * @return bestsellerButton BestsellerTaste
	 */
	public Button getBestsellerButton() {
		return bestsellerButton;
	}

	/**
	 * Gibt den NutzungButton zurueck.
	 * @return nutzungButton Nutzungstaste
	 */
	public Button getNutzungButton() {
		return nutzungButton;
	}

	/**
	 * Gibt den FehlerLogsButton zurueck.
	 * @return fehlerLogsButton FehlerLogTaste
	 */
	public Button getFehlerLogsButton() {
		return fehlerLogsButton;
	}

	/**
	 * Gibt den RegistrierenButton zurueck.
	 * @return registrierenButton RegistrierenTaste
	 */
	public Button getRegistrierenButton() {
		return registrierenButton;
	}

}
