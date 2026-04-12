package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasse welche Nachbestellungen von Einkaeufern verwaltet
 * 
 * @author Leonhard Heß
 */
public class E_BestellungenVerwalten {

	// Liste die alle Bestellungen speichert
	private ObservableList<E_Bestellung> bestellungen;

	/**
	 * Konstruktor fuer die Klasse E_BestellungenVerwalten
	 * kann das Laden eines vorheringen Zustands ueberspringen
	 */
	public E_BestellungenVerwalten() {
		Serialisierung ser = Serialisierung.getSerialisierung();
		bestellungen = FXCollections.observableList(ser.getEBestellungen());
	}

	/**
	 * Gibt eine Liste aller Nachbestellungen zurueck.
	 * 
	 * @return Liste aller Nachbestellungen
	 */
	public ObservableList<E_Bestellung> getBestellungen() {
		return FXCollections.unmodifiableObservableList(bestellungen);
	}

	/**
	 * Erstellt eine neue Nachbestellung fuer 'anzahl' viel 'produkt'
	 * 
	 * @param produkt Produkt welches nachbestellt wird
	 * @param anzahl  Wie viel von dem Produkt nachbestellt wird
	 * @return Erstellte Bestellung
	 */
	public E_Bestellung addBestellung(Produkt produkt, int anzahl) {
		// Bestellung erstellen. ID ist von der aktuellen Zeit abhängig.
		E_Bestellung bestellung = new E_Bestellung(produkt, anzahl,
				Serialisierung.getSerialisierung().getNeueEBestellId());

		// Bestellung hinten an die Bestellungen anfuegen.
		bestellungen.add(bestellung);

		return bestellung;
	}

	/**
	 * Entfernt die uebergebene Bestellung aus den Nachbestellungen
	 * Die Anzahl der Produkte in der Nachbestellung wird dem entsprechenden
	 * Produkt hinzugefuegt
	 * 
	 * @param bestellung Bestellung die entfernt werden soll
	 */
	public void removeBestellung(E_Bestellung bestellung) {
		bestellungen.remove(bestellung);
		bestellung.getProdukt().removeNachbestellung(bestellung);
	}

	/**
	 * Entfernt die uebergebene Bestellung aus den Nachbestellungen
	 * Die Anzahl der Produkte in der Nachbestellung wird dem entsprechenden
	 * NICHT(!) Produkt hinzugefuegt
	 */
	public void purgeBestellung(E_Bestellung bestellung) {
		bestellung.setAnzahl(0);
		removeBestellung(bestellung);
	}
}
