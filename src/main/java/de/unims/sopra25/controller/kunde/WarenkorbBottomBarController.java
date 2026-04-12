package de.unims.sopra25.controller.kunde;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Warenkorb;
import de.unims.sopra25.model.entitaeten.WarenkorbProduktAssoz;
import de.unims.sopra25.model.logik.K_BestellungenVerwalten;
import de.unims.sopra25.view.kunde.WarenkorbBottomBarView;

/**
 * Klasse, die die WarenkorbBottomBar Ansicht kontrolliert
 * 
 * @author David Metzdorf, Florian Gerdes, Johannes Schick
 */
public class WarenkorbBottomBarController {

    // Stage des Controllers
    private KundeStageController kundeStage;

    // View des Controllers
    private WarenkorbBottomBarView view;

    // Warenkorb des eingeloggten Kunden
    private Warenkorb warenkorb;

    // Gesamter Preis des Warenkorbs
    private String fullPrice;

    /**
     * Konstruktor fuer den WarenkorbBottomBarController
     * Initialisiert die view, attribute und Event Handler des Controllers
     * 
     * @param warenkorb Warenkorb des eingeloggten Kunden
     */
    public WarenkorbBottomBarController(Warenkorb warenkorb, KundeStageController kundeStage) {
        this.warenkorb = warenkorb;
        this.kundeStage = kundeStage;
        this.fullPrice = getFullPrice();

        this.view = new WarenkorbBottomBarView(fullPrice);

        initializeEventHandler();
    }

    /**
     * Methode zum Aktualisieren der BottomBar,
     * wenn Änderungen im Warenkorb vorgenommen werden.
     */
    public void aktualisiere() {
        this.view.getFullPriceOfOrderLabel().setText(getFullPrice());
    }

    /**
     * Getter für Summe der Preise von Produkte im Warenkorb
     * 
     * @return gesamt Preis als int
     */
    private static String getPreisString(int i) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
        DecimalFormat format = new DecimalFormat("#,##0.00 €", symbols);
        return format.format((double) i / 100);
    }

    /**
     * Gibt den gesamten Preis des Warenkorbs zurueck
     * 
     * @return String Gesamter Preis des Warenkorbs
     */
    private String getFullPrice() {
        int sum = 0;
        for (WarenkorbProduktAssoz assoz : warenkorb.getProdukte()) {
            sum += assoz.getAnzahl() * assoz.getProdukt().getPreis();
        }
        return getPreisString(sum);
    }

    /**
     * Initialisiert die Event Hanlder
     */
    private void initializeEventHandler() {

        // Bestell Button wurde gedrueckt; Fuehrt eine Bestellung aus
        view.getBestellButton().setOnAction(event -> {
            // testet ob der Warenkorb leer ist
            if (warenkorb.getProdukte().isEmpty()) {
                return;
            }

            K_BestellungenVerwalten bestellungVerwalten = new K_BestellungenVerwalten();

            // Daten aus view getten
            String day = view.getShippingDateDay().getText();
            String month = view.getShippingDateMonth().getText();
            String year = view.getShippingDateYear().getText();
            boolean payWithCash = view.getPayWithCash().isSelected();
            boolean payWithCard = view.getPayWithCard().isSelected();

            // Prüfe ob Datum leer gelassen wurde
            if (day.isEmpty() || month.isEmpty() || year.isEmpty()) {
                // prüfe Zahlungsmethode
                if (payWithCash) {
                    bestellungVerwalten.newBestellung(K_Bestellung.Zahlungsmittel.BAR, warenkorb);
                    kundeStage.switchToBestellungDetailView(bestellungVerwalten.getOffeneBestellungen().getLast());
                    return;
                } else if (payWithCard) {
                    bestellungVerwalten.newBestellung(K_Bestellung.Zahlungsmittel.KARTE, warenkorb);
                    kundeStage.switchToBestellungDetailView(bestellungVerwalten.getOffeneBestellungen().getLast());

                    return;
                } else {
                    view.bestellungFailed();
                    return;
                }
            }

            int dayAsInt = 0;
            int monthAsInt = 0;
            int yearAsInt = 0;

            // Erzeuge neuen Wunschliefertag
            try {
                dayAsInt = Integer.parseInt(day);
                monthAsInt = Integer.parseInt(month);
                yearAsInt = Integer.parseInt(year);
            } catch (NumberFormatException e) {
                view.bestellungFailed();
                return;
            }


            Calendar newWunschliefertag = new GregorianCalendar(yearAsInt, monthAsInt -1, dayAsInt);
            Calendar today = GregorianCalendar.getInstance();
            //teste ob das Datum stimmt 
            if(yearAsInt != newWunschliefertag.get(Calendar.YEAR) || monthAsInt-1 != newWunschliefertag.get(Calendar.MONTH) || dayAsInt != newWunschliefertag.get(Calendar.DAY_OF_MONTH) ){
            	view.bestellungFailed();
                return;
            }

            if(!newWunschliefertag.after(today)){
            	view.bestellungFailed();
                return;
            }

            // Prüfe Zahlungsmethode
            if (payWithCash) {
                bestellungVerwalten.newBestellung(newWunschliefertag, K_Bestellung.Zahlungsmittel.BAR, warenkorb);
                kundeStage.switchToBestellungDetailView(bestellungVerwalten.getOffeneBestellungen().getLast());
                return;
            } else if (payWithCard) {
                bestellungVerwalten.newBestellung(newWunschliefertag, K_Bestellung.Zahlungsmittel.KARTE, warenkorb);
                kundeStage.switchToBestellungDetailView(bestellungVerwalten.getOffeneBestellungen().getLast());
                return;
            } else {
                view.bestellungFailed();
                return;
            }

        });

        // Pay with Cash Checkbox wurde ausgewaehlt; Markiert die Checkbox als ausgewaehlt
        view.getPayWithCash().setOnAction(event -> {
            view.getPayWithCard().setSelected(false);
            view.getPayWithCash().setSelected(true);
        });

        // Pay with car Checkbox wurde ausgewaehlt; Markiert die Checkbox als ausgewaehlt
        view.getPayWithCard().setOnAction(event -> {
            view.getPayWithCash().setSelected(false);
            view.getPayWithCard().setSelected(true);
        });
    }

    /**
     * Gibt die View des Controllers zurueck
     * 
     * @return WarenkorbBottomBarView
     */
    public WarenkorbBottomBarView getView() {
        return this.view;
    }
}
