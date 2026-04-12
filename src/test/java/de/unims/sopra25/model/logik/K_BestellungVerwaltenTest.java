package de.unims.sopra25.model.logik;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kunde;
import de.unims.sopra25.model.entitaeten.Warenkorb;
import javafx.collections.ObservableList;

/**
* Klasse K_BestellungVerwaltenTest realisiert Tests bezüglich K_BEstellungVerwalten
* Hierbei wird jede Methode getestet
* @author Pia Maitzen
* @version 1.0
*/
@Disabled
public class K_BestellungVerwaltenTest {
	private K_BestellungenVerwalten verwalter;
	private Warenkorb warenkorb;
	private Kunde kunde;

	/**
	 * vor jedem Test wird eine Bestellung mit der Methode newBestellung erstellt
	 */
	 @BeforeEach
	void setUp() {
		Serialisierung.getSerialisierung().reset();
		verwalter = new K_BestellungenVerwalten();
		kunde = new Kunde("textnutzer", "passwort", "teststraße", "max", "mustermann");
		warenkorb = new Warenkorb(kunde);
		Calendar wunschliefertag = new GregorianCalendar();


		verwalter.newBestellung(wunschliefertag , K_Bestellung.Zahlungsmittel.BAR, warenkorb);
	 }

	/**
	* getOffeneBestellungenTest testet die Methode getOffeneBestellungen
	* hierbei wird getestet, dass die Liste offeneBestellungen nicht leer ist, da zuvor ein Element mit dem Status "OFFEN" erstellt wurde
	*/
	@Test
	public void getOffeneBestellungenTest() {

		ObservableList<K_Bestellung> offeneBestellungen = verwalter.getOffeneBestellungen();
		assertFalse(offeneBestellungen.isEmpty());
	}

	/**
	* nextStatusTest testete die Funktion nextStatus
	* hierbei wird der Status der zuvor erstellten Bestellung geändert
	* dann wird angenommen, dass die Liste offeneBestellungen leer ist, da das Element beim ändern des Status aus der Liste entfernt wird
	*/
	@Test
	public void nextStatusTest() {

		verwalter.nextStatus(verwalter.getOffeneBestellungen().get(0));
		ObservableList<K_Bestellung> offeneBestellungen = verwalter.getOffeneBestellungen();
		assertTrue(offeneBestellungen.isEmpty());


	}
}




