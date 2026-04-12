package de.unims.sopra25.model.entitaeten;

import java.io.Serializable;
import java.util.*;

/**
 * Klasse, die eine Kategorie repräsentiert.
 * Eine Kategorie besteht aus einer Namen einem Set an Produkten
 * 
 * @author Atilla Alaoglu
 */
public class Kategorie implements Serializable {

	// Name der Kategorie
	private String name;

	// Menge an Produkten, welche zu der Kategorie gehören.
	private ArrayList<Produkt> produkte;

	/**
	 * Konstruiert eine Kategorie
	 * 
	 * @param name ist der Name der Kategorie
	 */
	public Kategorie(String name) {
		this.name = name;
		produkte = new ArrayList<>();
	}

	/**
	 * Gibt den Name der Kategorie zurück.
	 * 
	 * @return Name der Kategorie
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzt den Name der Kategorie.
	 * 
	 * @param name Name der Kategorie
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Menge der Produkte in dieser Kategorie zurück
	 * 
	 * @return Menge der Produkte in dieser Kategorie (Read-only)
	 */
	public List<Produkt> getProdukte() {
		return Collections.unmodifiableList(produkte);
	}

	/**
	 * Fügt der Kategorie ein Produkt hinzu
	 * 
	 * @param produkt Produkt im Sortiment
	 */
	public void addProdukt(Produkt produkt) {
		if (!produkte.contains(produkt)) {
			produkte.add(produkt);
		} else {
			System.out.println("Produkt hat bereits diese Kategorie");
		}
		// Falls das Produkt nicht dieser Kategorie zugeordnet ist,
		// ordne es dieser Kategorie zu.
		if (!produkt.getKategorien().contains(this)) {
			produkt.addKategorie(this);
		}

	}

	/**
	 * Entfernt aus der Kategorie ein Produkt
	 * 
	 * @param produkt Produkt im Sortiment
	 */
	public void removeProdukt(Produkt produkt) {
		produkte.remove(produkt);

		// Falls das Produkt dieser Kategorie zugeordnet ist,
		// entferne es aus dieser Kategorie.
		if (produkt.getKategorien().contains(this)) {
			produkt.removeKategorie(this);
		}
	}

	/**
	 * Gibt den Kategorienamen aus.
	 * 
	 * @return Name der Kategorie
	 */
	@Override
	public String toString() {
		return name;
	}
}
