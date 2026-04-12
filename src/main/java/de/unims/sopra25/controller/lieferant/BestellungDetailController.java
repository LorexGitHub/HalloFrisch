package de.unims.sopra25.controller.lieferant;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.lieferant.BestellungBoxView;
import de.unims.sopra25.view.lieferant.BestellungDetailView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * erzeugt eine BestellungDetailView fuer den Lieferanten
 * @author Irina Hoppe , Pia Maitzen, Johannes Schick
 */
public class BestellungDetailController {

	// Stage des Controllers
    private LieferantStageController lieferantStage;

    // View des Controllers
    private BestellungDetailView view;

    // Aktuelle Kundenbestellung
    private K_Bestellung bestellung;

    // Buttons
    private Button annehmenButton;
    private Button ausliefernButton;

    // Labels
    private Label statusLabel;

    /**
     * Konstruktor erstellt das Fenster und plottet
     * die Produkte der zu Betrachtenden Bestellung
     * @param lieferantStage aktueller Controller des Kunden-Fensters
     * @param bestellung  aktuell betrachtete Bestellung
     */
    public BestellungDetailController(LieferantStageController lieferantStage, K_Bestellung bestellung, BestellungBoxView boxView) {
        this.lieferantStage = lieferantStage;
        this.bestellung= bestellung;
        this.statusLabel= new Label();

        this.view = new BestellungDetailView(bestellung);
        
        initButtons();
        initEventHandler(boxView);
    }

    /**
     * Zeigt die Buttons für den Lieferanten an.
     */
    public void initButtons(){
        annehmenButton = new Button("Annehmen");
        ausliefernButton = new Button("Ausgeliefert");

        view.getAnsicht().getChildren().add(statusLabel);

        int ordinal = this.bestellung.getStatus().ordinal();
        
        switch(ordinal)
        {
            case 0:
                view.getAnsicht().getChildren().add(annehmenButton);
                if(!bestellung.istErfuellbar()){
                    annehmenButton.setDisable(true);
                }
                break;
            case 1:
                annehmen();
                break;
            case 2:
                ausliefern();
                break;
        }
    }

    /**
     * Ändert die Ansicht, wenn der Auftrag angenommen
     * wurde.
     */
    private void annehmen(){
        statusLabel.setText("Bestellung angenommen");
        view.getAnsicht().getChildren().add(ausliefernButton);
        statusLabel.setTextFill(Color.ORANGE);
    }

    /**
     * Ändert die Ansicht, wenn die Bestellung ausgeliefert
     * wurde.
     */
    private void ausliefern(){
        statusLabel.setText("Bestellung angenommen und ausgeliefert.");
        statusLabel.setTextFill(Color.GREEN);
    }

    /**
     * Erstellt die Event-Handler.
     */
    private void initEventHandler(BestellungBoxView boxView){
        //Annehmen-Button
        // HINWEIS: Das tatsächliche Annehmen der Bestellung in der Logik wird in
        // BestellungBoxController gesteuert.
        annehmenButton.setOnAction(event -> {
            view.getAnsicht().getChildren().remove(annehmenButton);
            annehmen();
            boxView.getAnnehmenButton().arm();
            boxView.getAnnehmenButton().fire();
        });
        //Ausliefern-Button
        // HINWEIS: Das tatsächliche Ausliefern der Bestellung in der Logik wird in
        // BestellungBoxController gesteuert.
        ausliefernButton.setOnAction(event -> {
            view.getAnsicht().getChildren().remove(ausliefernButton);
            ausliefern();
            boxView.getAusliefernButton().arm();
            boxView.getAusliefernButton().fire();
        });
    }

	/**
	 * Gibt das erstellte Bestelldetail-Fenster zurueck
	 *
	 * @return view BestellungDetailView
	 */
	public BestellungDetailView getView() {
		return view;
	}

	/**
	 * Gibt den Stage-Controller des Lieferanten aus
	 *
	 * @return lieferantStage LieferantStageController
	 */
	public LieferantStageController getLieferantStage() {
		return lieferantStage;
	}	
}
