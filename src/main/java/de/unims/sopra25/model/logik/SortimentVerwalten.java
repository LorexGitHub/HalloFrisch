package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.min;

/**
 * Die Klasse SortimentVerwalten ist die Logikklasse, die
 * Methoden zur Verwaltung der Produktliste implementiert und
 * ist primär von der Geschäftsführer-Entität genutzt.
 * 
 * @author Florian Gerdes
 */
public class SortimentVerwalten {
    // Liste aller Produkte
    private ObservableList<Produkt> produktlist;

    /**
     * Konstruktor der Verwaltungsklasse SortimentVerwalten
     */
    public SortimentVerwalten() {
        Serialisierung ser = Serialisierung.getSerialisierung();
        this.produktlist = FXCollections.observableList(ser.getProdukte());
    }

    /**
     * Getter-Methode für die verwaltete Produktliste
     * 
     * @return gibt die Produktliste zurück
     */
    public ObservableList<Produkt> getProduktlist() {
        return this.produktlist;
    }

    /**
     * Methode zum Anlegen eines neuen Produktes
     * 
     * @param produkt ist das neu anzulegende Produkt
     *                zu erledigen: ProdukteSerialisieren nach Anlegen des Produktes
     *                implementieren
     */
    public void neuesProdukt(Produkt produkt) throws IllegalArgumentException {

        for (int i = 0; i < this.produktlist.size(); i++) {
            Produkt temp = produktlist.get(i);
            if (temp.getName().equals(produkt.getName())) {
                throw new IllegalArgumentException("Produktname existiert bereits.");
            }
        }
        this.produktlist.add(produkt);
    }

    /**
     * Methode zum Entfernen eines Produktes aus dem Sortiment
     * 
     * @param produkt ist das aus der Produktliste zu entfernende Produkt
     *                TODO: Sicheres Entfernen des Produkts von allen Verweisen
     *                zu erledigen: ProdukteSerialisieren nach dem Löschen des
     *                Produktes implementieren
     *                TODO: Sicheres Entfernen des Produkts von allen Verweisen
     * @pre produkt in this.produktlist
     */
    public void deleteProdukt(Produkt produkt) {
        assert this.produktlist.contains(produkt) : "Vorbedingung von deleteProdukt verletzt";
        Warenkorb warenkorb;
        HashMap<String, Kunde> kundeHashMap = Serialisierung.getSerialisierung().getKunden();
        ArrayList<E_Bestellung> nachbestellungen = Serialisierung.getSerialisierung().getEBestellungen();

        // Produkt aus allen Kategorien löschen
        for (Kategorie k : new ArrayList<>(produkt.getKategorien())) {
            produkt.removeKategorie(k);
        }

        for (Kunde k : kundeHashMap.values()) {
            // Produkt aus allen Warenkörben entfernen
            warenkorb = k.getWarenkorb();
            for (WarenkorbProduktAssoz produktAssoz : new ArrayList<>(k.getWarenkorb().getProdukte())) {
                if (produktAssoz.getProdukt() == produkt) {
                    warenkorb.removeProdukt(produkt);
                }
            }

            // Produktassoziationen aus allen Bestellungen entfernen
            for (K_Bestellung bestellung : k.getBestellungen()) {
                for (K_BestellungProduktAssoz assoz : bestellung.getProdukte()) {
                    if (assoz.getProdukt() == produkt) {
                        assoz.removeProduktAssoz();
                    }
                }
            }
        }

        // E_Bestellungen löschen, die das Produkt enthalten
        for (E_Bestellung bestellung : produkt.getNachbestellungen()) {
            nachbestellungen.remove(bestellung);
        }

        // Produkt aus Produktliste entfernen
        this.produktlist.remove(produkt);
    }

}
