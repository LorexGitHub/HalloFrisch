package de.unims.sopra25.model.logik;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.model.entitaeten.Warenkorb;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Die Klasse K_BestellungenVerwalten verwaltet die Kundenbestellungen
 * hierbei kann sie alle offenen Bestellungen ausgeben, neue Bestellungen
 * erstellen oder den Status
 * von Kundenbestellungen ändern
 * 
 * @author Pia Maitzen
 */
public class K_BestellungenVerwalten {

	// Liste aller offenen Bestellungen
	private ObservableList<K_Bestellung> offeneBestellungen;

	/**
	 * Konstruktor der Klasse K_BestellungenVerwalten
	 * offeneBestellungen wird aus der serialisierten Datei geladen
	 */
	public K_BestellungenVerwalten() {
		Serialisierung serialisierung = Serialisierung.getSerialisierung();
		this.offeneBestellungen = FXCollections.observableList(serialisierung.getOffeneKBestellungen());
	}

	/**
	 * Die Methode NewBestellung erzeugt eine neue Kundenbestellung mit zugehörigen
	 * Attributen.
	 * Diese Methode berücksichtigt Bestellungen, bei denen ein Wunschliefertag
	 * angegeben wurde
	 * 
	 * @param wunschliefertag Gewünschtes Zustelldatum
	 * @param zahlungsmittel  vom Kunden angegebene Zahlungsart
	 * @param warenkorb       Warenkorb, in dem die Bestellung getätigt wird.
	 *                        In der Methode wird die id aus der Serialisierung
	 *                        entnommen und für die neue Bestellung festgelegt.
	 *                        Die neue Bestellung wird der Listen für die offenen
	 *                        Bestellungen hinzugefügt
	 */
	public void newBestellung(Calendar wunschliefertag, K_Bestellung.Zahlungsmittel zahlungsmittel,
			Warenkorb warenkorb) {

		int id = Serialisierung.getSerialisierung().getNeueKBestellId();

		K_Bestellung neueBestellung = new K_Bestellung(id, wunschliefertag, zahlungsmittel, warenkorb);
		this.offeneBestellungen.add(neueBestellung);
		warenkorb.leeren();
	}

	public void newBestellung(K_Bestellung.Zahlungsmittel zahlungsmittel, Warenkorb warenkorb) {
		this.newBestellung(null, zahlungsmittel, warenkorb);
		warenkorb.leeren();
	}

	/**
	 * Die Methode getOffeneBestellungen gibt eine Liste aller offenen
	 * Kundenbestellungen wieder
	 * 
	 * @return Liste aller Kundenbestellungen mit dem Status "offen"
	 */
	public ObservableList<K_Bestellung> getOffeneBestellungen() {
		return this.offeneBestellungen;
	}

	/**
	 * Die Methode nextStatus setzt den Status einer Bestellung auf den jeweils
	 * nächsten
	 * 
	 * @param bestellung Kundenbestellung, dessen Status verändert wird
	 * @throws IllegalStateException Exception, die auftritt, falls Bestellung
	 *                               bereits Status AUSGELIEFERT besitzt
	 */
	public void nextStatus(K_Bestellung bestellung) throws IllegalStateException {
		if (K_Bestellung.Status.OFFEN == (bestellung.getStatus())) {
			bestellung.setVorbereitet();
			offeneBestellungen.remove(bestellung);
		} else if (K_Bestellung.Status.VORBEREITET == (bestellung.getStatus())) {
			bestellung.setAusgeliefert();
			bestellung.getLieferant().removeBestellung(bestellung);
		} else if (K_Bestellung.Status.AUSGELIEFERT == (bestellung.getStatus())) {
			throw new IllegalStateException("Die Bestellung wurde bereits ausgeliefert");
		}
	}

}
