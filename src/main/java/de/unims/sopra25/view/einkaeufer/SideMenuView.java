package de.unims.sopra25.view.einkaeufer;

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
 * Klasse, die die Szene SideMenuView für den Einkaeufer zeichnet und
 * Side Menu für den Geschaeftsfuehrer angezeigt
 *
 * @author David Metzdorf
 */
public class SideMenuView extends SideMenuViewVorlage {

    // Icon Paths
    Image nachbestellungImage;

    // Image Views
    ImageView nachbestellungImageView;

    // Buttons
    Button nachbestellungButton;

    /**
     * Konstruktor initialisiert alle Nodes des SideMenus
     */
    public SideMenuView() {
        super();
        baueSideMenu();
    }

    /**
     * Baut eine SideMenu für den Geschäftsführer
     * Benutzt werden dabei nur Parameter, die für den Einkäufer relevant
     * sind.
     * @return VBox
     */
    @Override
    public VBox baueSideMenu() {
        // Image handling
        nachbestellungImage = new Image("file:./speicher/icons/nachbestliste.png");
        nachbestellungImageView = new ImageView(nachbestellungImage);

        // Styling
        nachbestellungImageView.setFitHeight(40);
        nachbestellungImageView.setPreserveRatio(true);
        setSpacing(10);
        setAlignment(Pos.CENTER);

        // Button mit Bild
        nachbestellungButton = new Button();
        nachbestellungButton.getStyleClass().add("round-button");
        nachbestellungButton.setTooltip(new Tooltip("Nachbestellungen Übersicht"));
        nachbestellungButton.setGraphic(nachbestellungImageView);

        // ProduktUebersicht Box
        HBox produktUebersichtBox = new HBox();
        Region regionProduktUeberischt = new Region();
        regionProduktUeberischt.setPrefWidth(9);
        getProduktUebersichtButton().getStyleClass().add("round-button");
        produktUebersichtBox.getChildren().addAll(getProduktUebersichtButton(), regionProduktUeberischt);


        // Nachbestellung Box
        HBox nachbestellungBox = new HBox();
        Region regionNachbestellung = new Region();
        regionNachbestellung.setPrefWidth(9);
        nachbestellungBox.getChildren().addAll(nachbestellungButton, regionNachbestellung);

        getChildren().addAll(produktUebersichtBox, nachbestellungBox);
        return this;
    }

    /**
     * Getter-Methode fuer Nachbestellung Button
     * @return nachbestellungButton
     */
    public Button getNachbestellungButton() {
        return nachbestellungButton;
    }
}
