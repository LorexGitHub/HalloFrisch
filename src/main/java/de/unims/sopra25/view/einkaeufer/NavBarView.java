package de.unims.sopra25.view.einkaeufer;

import java.util.List;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.view.shared.NavBarViewVorlage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Klasse, die die Szene NavBarView für den Einkaeufer zeichnet
 * 
 * @author Frederik Fluegge, Constantin
 */
public class NavBarView extends NavBarViewVorlage<Kategorie>{

    /**
     * Konstuktor, der alle Nodes der NavBar initialisiert
     * @param kategorieList Liste an Kategorien, die im Filter angezeigt werden sollen
     */
    public NavBarView(List<Kategorie> kategorieList){
        super();

        // baue filter
        filter.getStyleClass().add("button");
        //filter.getStyleClass().add("menu");
        filter.getStyleClass().add("combo-box-style");
        filter.getItems().add(new Kategorie("Alle"));
        filter.getSelectionModel().select(0);
        for (Kategorie k : kategorieList) {
            filter.getItems().add(k);
        }

        baueNavBar(logoButton, search, startSearchButton, filter,  logOutButton);
    }

     /**
     * Baut eine NavBar für den Einkaeufer
     * Benutzt werden dabei nur Parameter, die für den Einkaeufer relevant sind.
     * @param logo, lieferadresse, search, filter, warenkorb, profil
     * @return HBox
     */
    @Override
    public HBox baueNavBar(Button logo, TextField search, Button startSearch, ComboBox filter, Button logOutButton) {
        Region spacer = new Region();
        spacer.setPrefWidth(70);
        getChildren().addAll(logo, spacer, search, startSearch, filter,logOutButton);
        
        return this;
    }
}
