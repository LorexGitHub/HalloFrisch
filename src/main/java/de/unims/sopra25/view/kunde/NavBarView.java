package de.unims.sopra25.view.kunde;

import java.util.List;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.view.shared.NavBarViewVorlage;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * Klasse, die die Szene NavBarView fuer den Kunden zeichnet
 *
 * @author Frederik Fluegge
 */
public class NavBarView extends NavBarViewVorlage<Kategorie> {
    private Label lieferadresse;
    private Button warenkorbButton;
    private Button profilButton;
    private MenuButton menu;
    private CustomMenuItem kategorieMenu;
    private CustomMenuItem verfuegbarkeitMenu;
    private CustomMenuItem preisFilter;
    private HBox preisFilterBox;
    private TextField minPreis;
    private TextField maxPreis;
    private ComboBox<String> filterBestand;


    /**
     * Konstuktor, der alle Nodes der NavBar initialisiert
     */
    public NavBarView(List<Kategorie> kategorieList) {
        super();
        profilButton = new Button("Profil");
        warenkorbButton = new Button("Warenkorb");
        lieferadresse = new Label("Keine Adresse hinterlegt");

		// baue filter
		baueFilterMenu(kategorieList);
        baueNavBar(logoButton, search, startSearchButton, filterBestand, logOutButton);
    }

    /**
     * Baut eine NavBar für den Kunden
     * Benutzt werden dabei nur Parameter, die für den Kunden relevant sind.
     *
     * @param logo, lieferadresse, search, filter, warenkorb, profil
     * @return HBox
     */
    @Override
    public HBox baueNavBar(Button logo, TextField search, Button startSearch, ComboBox filter,
            Button logOutButton) {

        getChildren().addAll(logo, lieferadresse, search, startSearch, menu, warenkorbButton, profilButton);
        return this;
    }
    public Label getLieferadresse() {
        return lieferadresse;
    }
    public Button getWarenkorbButton() {
        return warenkorbButton;
    }
    public Button getProfilButton() {
        return profilButton;
    }
    public void setLieferadresse(String adresse){
        lieferadresse.setPrefWidth(80);
        lieferadresse.setPrefHeight(50);
        Tooltip lieferadresseTooltip = new Tooltip(adresse);
        lieferadresse.setTooltip(lieferadresseTooltip);
        Tooltip.install(lieferadresse, lieferadresseTooltip);
        lieferadresse.setText(adresse);
    }

    private void baueFilterMenu(List<Kategorie> kategorieList){
        Label label;
        menu = new MenuButton("Filter");
        menu.getStyleClass().add("menu");
        menu.getStyleClass().add("button");
        filterBestand= new ComboBox<>();

		// Preis-Filter
		minPreis= new TextField();
		maxPreis= new TextField();
		minPreis.setPromptText("min Preis");
		maxPreis.setPromptText("max Preis");
		minPreis.setMaxWidth(40);
		maxPreis.setMaxWidth(40);
		preisFilterBox= new HBox();
		preisFilterBox.getChildren().addAll(new Label("von"), minPreis, new Label("bis"), maxPreis);
		preisFilterBox.setAlignment(Pos.BASELINE_CENTER);
		preisFilter = new CustomMenuItem(preisFilterBox);

		// Kategorien-Filter
		filter.getItems().add(new Kategorie("Alle"));
		filter.getSelectionModel().select(0);
		for(Kategorie k: kategorieList){
			filter.getItems().add(k);
		}
		label= new Label("Kategorie: ", filter);
		label.setContentDisplay(ContentDisplay.RIGHT);
		kategorieMenu= new CustomMenuItem(label);

		// Verfuegbarkeits-Menu
		filterBestand.getItems().add("Alle");
		filterBestand.getSelectionModel().select(0);
		filterBestand.getItems().add("Auf Lager");
		label= new Label("Verfügbarkeit: ", filterBestand);
		label.setContentDisplay(ContentDisplay.RIGHT);
		verfuegbarkeitMenu= new CustomMenuItem(label);

		// Dem Menue hinzufuegen
		menu.getItems().addAll(preisFilter, kategorieMenu, verfuegbarkeitMenu);
	}

	/**
	 * Getter Methode fuer menu
	 * @return menu
	 */
	public MenuButton getMenu() {
		return menu;
	}

	/**
	 * Getter Methode fuer filterBestand
	 * @return filterBestand
	 */
	public ComboBox<String> getFilterBestand() {
		return filterBestand;
	}

	/**
	 * Getter Methode fuer kategorieMenu
	 * @return kategorieMenu
	 */
	public CustomMenuItem getKategorieMenu() {
		return kategorieMenu;
	}

	/**
	 * Getter Methode fuer minPreis
	 * @return minPreis
	 */
	public TextField getMinPreis() {
		return minPreis;
	}

	/**
	 * Getter Methode fuer maxPreis
	 * @return maxPreis
	 */
	public TextField getMaxPreis() {
		return maxPreis;
	}

	/**
	 * Getter Methode fuer verfuegbarkeitMenu
	 * @return verfuegbarkeitMenu
	 */
	public CustomMenuItem getVerfuegbarkeitMenu() {
		return verfuegbarkeitMenu;
	}
}
