package de.unims.sopra25.view.lieferant;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.shared.BestellungBoxViewVorlage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Klasse fuer eine Bestellungszeile in der BestellungsUebersicht eines
 * Lieferanten
 * 
 * @author Irina Hoppe
 */
public class BestellungBoxView extends BestellungBoxViewVorlage {

    public BestellungBoxView(K_Bestellung bestellung) {
        super(bestellung);
    }

    /**
     * Methode zum bauen einer Bestellungsbox fuer den Lieferanten
     * 
     * @param nummer         Nummer der Bestellung
     * @param datum          Datum der Bestellung
     * @param status         Status der Bestellung
     * @param zahlungsmittel Zahlungsmittel der Bestellung
     * @param tag            Gewuenschter Tag an den die Bestellung bei den Kunden
     *                       ankommen soll
     * @param annehmen       Bestellungsauftrag annehme Button
     */
    @Override
    public void baueBestellungZeile(Button nummer, Label datum, Label status, Label zahlungsmittel, VBox tag,
            Button annehmen, Button ausliefern) {
        int ordinal = this.bestellung.getStatus().ordinal();
        // ordinal ist fuer: 0 = offen, 1 = angenommen, 2 = ausgeliefert
        switch (ordinal) {
            case 0:
                getChildren().addAll(nummer, datum, status, tag, annehmen);
                erfuellbar();
                break;
            case 1:
                getChildren().addAll(nummer, datum, status, tag, ausliefern);
                break;
            case 2:
                getChildren().addAll(nummer, datum, status, tag);
                break;
        }
    }

    /**
     * Methode überprüft, ob Bestellung erfüllbar ist.
     * Wenn nicht, wird annehmen Button deaktiviert.
     */
    public void erfuellbar() {
        if (bestellung.getStatus() == K_Bestellung.Status.OFFEN && !bestellung.istErfuellbar()) {
            annehmen.setDisable(true);
        }
    }

    /**
     * Gib Button zum Annehmen einer Bestellung zurueck
     * 
     * @return annehmen Button
     */
    public Button getAnnehmenButton() {
        return annehmen;
    }

    public Button getAusliefernButton() {
        return ausliefern;
    }

    public void removeAusliefernButton() {
        this.getChildren().remove(ausliefern);
    }

    /**
     * löscht den Annehmenbutton
     */
    public void removeAnnehmenButton() {
        this.getChildren().remove(annehmen);
    }

    public void addAusliefernButton() {
        this.getChildren().add(ausliefern);
    }
}
