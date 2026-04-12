package de.unims.sopra25.controller.kunde;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.view.kunde.BestellungBottomBarView;

/**
 * Verwaltet Bestellung, s.d. der Kunde die Daten der Bestellung ändern kann
 * 
 * @author David Metzdorf, Florian Gerdes
 */
public class BestellungBottomBarController {

    // View des Controllers
    BestellungBottomBarView view;

    // Ausgewaehlte Bestellung
    K_Bestellung bestellung;

    /**
     * Konstruktor für den BottomBarController
     * Initialisiert die view, bestellung und Event Hanlder der Controller
     * 
     * @param bestellung ausgewählte Bestellung
     */
    public BestellungBottomBarController(K_Bestellung bestellung) {
        this.bestellung = bestellung;

        this.view = new BestellungBottomBarView(bestellung);

        // Bei ausgelieferten Bestellungen gibt es nur die Anzeige und kein controller
        if (bestellung.getStatus() == K_Bestellung.Status.AUSGELIEFERT) {
            return;
        }

        initializeEventHandler();
    }

    /**
     * Initialisiert die Event Hanlder
     */
    private void initializeEventHandler() {

        // Save Button wurde gedrueckt; Speichert die Bezahlungsmethode und das
        // Lieferdatum
        view.getSaveButton().setOnAction(event -> {
            view.removeErrorMessage();
            view.removeBestaetigungMessage();
            changePaymentMethod();
            changeShippingDate();
        });

        // pay with card Checkbox wurde ausgewaehlt; Setzt die Bezahlungsmethode auf
        // Kartenzahlung
        view.getPayWithCard().setOnAction(event -> {
            view.getPayWithCash().setSelected(false);
        });

        // pay with cash Checkbox wurde ausgewaehlt; Setzt die Bezahlungsmethode auf
        // Barzahlung
        view.getPayWithCash().setOnAction(event -> {
            view.getPayWithCard().setSelected(false);
        });
    }

    /**
     * Aendert die Zahlungsmethode abhängig davon, was der Kunde auswaehlt
     */
    private void changePaymentMethod() {
        boolean cashSelected = view.getPayWithCash().isSelected();
        boolean cardSelected = view.getPayWithCard().isSelected();

        if (cardSelected) {
            bestellung.setZahlungsmittel(K_Bestellung.Zahlungsmittel.KARTE);
            view.getPayWithCash().setSelected(false);
        } else if (cashSelected) {
            bestellung.setZahlungsmittel(K_Bestellung.Zahlungsmittel.BAR);
            view.getPayWithCard().setSelected(false);
        }
    }

    /**
     * Aendert das Wunschlieferadtum abhängig davon, was der Kunde auswaehlt
     */
    private void changeShippingDate() {
        // Holt Aenderungen
        String newDay = view.getShippingDateDay().getText();
        String newMonth = view.getShippingDateMonth().getText();
        String newYear = view.getShippingDateYear().getText();

        // eErsetzt den wunschliefertag durch Aenderungen
        int newDayAsInt = 0;
        int newMonthAsInt = 0;
        int newYearAsInt = 0;

        // Wandelt Strings in int
        try {
            newDayAsInt = Integer.parseInt(newDay);
            newMonthAsInt = Integer.parseInt(newMonth);
            newYearAsInt = Integer.parseInt(newYear);
        } catch (NumberFormatException e) {
            if (newDay.equals("") && newMonth.equals("") && newYear.equals("")) {
                bestellung.setWunschliefertag(null);
                view.zeigeBestaetigung();
                return;
            }

            view.saveChangesFailed();
        }

        // GregorianCalendar setzt Januar als 0
        Calendar newCalendar = new GregorianCalendar(newYearAsInt, newMonthAsInt - 1, newDayAsInt);
        Calendar today = GregorianCalendar.getInstance();
        if (newYearAsInt != newCalendar.get(Calendar.YEAR) || newMonthAsInt - 1 != newCalendar.get(Calendar.MONTH)
                || newDayAsInt != newCalendar.get(Calendar.DAY_OF_MONTH)) {
            view.saveChangesFailed();
            return;
        }
        if (!newCalendar.after(today)) {
            view.saveChangesFailed();
            return;
        }

        bestellung.setWunschliefertag(newCalendar);
        view.zeigeBestaetigung();
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return BestellungBottomBarView
     */
    public BestellungBottomBarView getView() {
        return view;
    }
}
