package de.unims.sopra25.view.lieferant;

import java.util.ArrayList;
import java.util.List;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.shared.NavBarViewVorlage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.lang.String;

/**
 * Klasse, die die Szene NavBarView für den Lieferanten zeichnet
 *
 * @author Frederik Fluegge, Constantin Meininghaus
 */
public class NavBarView extends NavBarViewVorlage<String> {

	private Region spacer;
    /**
     * Konstruktor, der alle Nodes der NavBar initialisiert
     */
    public NavBarView(ArrayList<K_Bestellung.Status> listeStati){
        super();
        setSpacing(54);
        spacer = new Region();
        spacer.setPrefWidth(480);
        //baue Filter
        filter.getStyleClass().add("button");
        filter.getStyleClass().add("combo-box-style");
        filter.getItems().add("Alle");
        filter.getSelectionModel().select(0);
        filter.getItems().add("Erfüllbar");
   
        for (K_Bestellung.Status s : listeStati) {

        	filter.getItems().add(s.toString()); //erkennt er hier enum?
        }
        baueNavBar(logoButton, search, startSearchButton, filter, logOutButton);
    }

    /**
     * Baut eine NavBar für den Lieferanten
     * Benutzt werden dabei nur Parameter, die für den Lieferanten relevant sind.
     * @param logo, lieferadresse, search, filter, warenkorb, profil
     * @return HBox eine HBox die die Navbar repraesentiert
     */
    @Override
    public HBox baueNavBar(Button logo,TextField search, Button startSearch, ComboBox filter,
            Button logOutButton) {
        getChildren().addAll(logo, spacer, filter,logOutButton);
        return this;
    }

}
