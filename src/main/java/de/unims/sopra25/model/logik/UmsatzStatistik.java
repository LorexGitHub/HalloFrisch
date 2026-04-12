package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.K_BestellungProduktAssoz;
import de.unims.sopra25.model.entitaeten.Kunde;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Klasse zur Ausgabe von Umsatzstatistiken
 * 
 * @author Johannes Schick
 * @author Leonhard Heß
 */
public class UmsatzStatistik {

	// Alle Bestellungen
	private ArrayList<K_Bestellung> bestellungen;

	/**
	 * Konstruktor fuer die Klasse UmsatzStatistik
	 */
	public UmsatzStatistik() {
		// Hole Liste an k_bestellungen aus den Kunden
		Map<String, Kunde> kunden = Serialisierung.getSerialisierung().getKunden();
		bestellungen = new ArrayList<K_Bestellung>();
		for (Kunde k : kunden.values()) {
			bestellungen.addAll(k.getBestellungen());
		}

	}

	/**
	 * Gibt Umsatz aus dem gegebenen Jahr aus, aufgeteilt
	 * in die 12 Monate.
	 * 
	 * @param jahr Jahr aus dem der Umsatz berechnet werden soll
	 * @return Umsatz aus dem Zeitraum (Cent)
	 */
	public ArrayList<Long> getUmsatzVon(int jahr) {
		ArrayList<Long> umsatzList = new ArrayList<Long>();
		Calendar c = new GregorianCalendar(jahr, 0, 1, 0, 0);

		Instant i1;
		Instant i2;
		i2 = c.toInstant();

		// Geht alle 12 Monate durch und berechnet dort
		// den Umsatz.
		for (int i = 0; i < 12; i++) {
			i1 = i2;
			c.add(Calendar.MONTH, 1);
			i2 = c.toInstant();

			Date ab = Date.from(i1);
			Date bis = Date.from(i2);

			long umsatz = 0;
			for (K_Bestellung kb : bestellungen) {
				if ((ab == null || ab.before(kb.getDatum())) &&
						(bis == null || bis.after(kb.getDatum()))) {
					for (K_BestellungProduktAssoz kba : kb.getProdukte()) {
						umsatz += kba.getPreis() * kba.getAnzahl();
					}
				}
			}
			umsatzList.addLast(umsatz);
		}
		return umsatzList;
	}

	/**
	 * Gibt den gesamten Umsatz aus.
	 * 
	 * @return Gesamten Umsatz (Cent)
	 */
	public long getGesamtumsatz() {
		long umsatz = 0;
		for (K_Bestellung kb : bestellungen) {
			for (K_BestellungProduktAssoz kba : kb.getProdukte()) {
				umsatz += kba.getPreis() * kba.getAnzahl();
			}
		}
		return umsatz;
	}
}
