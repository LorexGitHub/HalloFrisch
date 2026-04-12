package de.unims.sopra25.model.logik;

import java.util.ArrayList;
import java.util.List;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasse zum Verwalten der Kategorien
 * 
 * @author Johannes Schick
 */
public class KategorienVerwalten {

    // Speichert alle Kategorien
    private ObservableList<Kategorie> kategorien;

    /**
     * Konstruktor.
     * Lädt die gespeicherten Kategorien aus der Datei.
     */
    public KategorienVerwalten() {
        Serialisierung serialisierung = Serialisierung.getSerialisierung();
        this.kategorien = FXCollections.observableList(serialisierung.getKategorien());
    }

    /**
     * Getter-Methode Kategorien
     */
    public ObservableList<Kategorie> getKategorien() {
        return kategorien;
    }

    /**
     * Getter-Methode, die eine Kategorie anhand des Namens zurückgibt
     * 
     * @param kategorieName Name der gesuchten Kategorie
     * @return Kategorie
     */
    public Kategorie getKategorieByName(String kategorieName) {
        for (Kategorie k : kategorien) {
            if (k.getName().equals(kategorieName)) {
                return k;
            }
        }
        return null;
    }

    /**
     * Fügt eine Kategorie hinzu.
     * 
     * @param name Name der zu erstellenden Kategorie
     * @throws IllegalArgumentException Es gibt bereits eine Kategorie mit gleichem
     *                                  Namen.
     */
    public void addKategorie(String name) throws IllegalArgumentException {
        for (Kategorie k : kategorien) {
            if (k.getName().equals(name)) {
                throw new IllegalArgumentException("Kategorie bereits vorhanden");
            }
        }
        kategorien.add(new Kategorie(name));
    }

    /**
     * Entfernt die übergebene Kategorie.
     * Dabei werden alle Produkte aus der Kategorie entfernt.
     * 
     * @param kategorie Zu entfernende Kategorie
     * @pre kategorie ist in this.kategorien
     */
    public void removeKategorie(Kategorie kategorie) {
        // Vorbedingung prüfen
        assert this.kategorien.contains(kategorie) : "Vorbedingung von removeKategorie verletzt";

        // Entfernt Referenz von allen Produkten, die in der Kategorie waren
        List<Produkt> temp = new ArrayList<>();
        for (Produkt p : kategorie.getProdukte()) {
            temp.add(p);
        }
        for (Produkt p : temp) {
            p.removeKategorie(kategorie);
        }

        this.kategorien.remove(kategorie);
    }

    /**
     * Entfernt die Kategorie mit dem übergebenen Namen.
     * 
     * @param name Name der zu entfernenden Kategorie
     * @pre Es gibt eine Kategorie mit dem Namen
     */
    public void removeKategorie(String name) {
        // Suche nach Kategorie
        for (Kategorie k : kategorien) {
            if (k.getName().equals(name)) {
                // Entferne Kategorie
                this.removeKategorie(k);
                return;
            }
        }
        // Vorbedingung
        assert false : "Vorbedingung von removeKategorie verletzt";
    }

}
