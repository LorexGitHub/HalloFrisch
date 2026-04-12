package de.unims.sopra25.model.logik;

import de.unims.sopra25.model.entitaeten.E_Bestellung;
import de.unims.sopra25.model.entitaeten.K_Bestellung;
import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.entitaeten.Produkt;
import de.unims.sopra25.view.einkaeufer.ProduktBoxView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;

import java.util.*;

/**
 * Die Klasse ListeFiltern beinhaltet static Methoden, mit derer Hilfe alle
 * möglichen im Bestellsystem auftretenen Listen gefiltert werden können.
 *
 * @author Florian Gerdes
 */
public class ListeFiltern {

    // --------------------NEUE SUCHLOGIK------------------------------\\

    /**
     * Sortiert eine ProduktÜbersicht nach Bestand.
     * Verändert direkt die Filtered List
     *
     * @param originaleListe Liste bestehend aus ProduktBoxViews
     */
    public static void einkaeuferSortiereProdukte(FilteredList<ProduktBoxView> originaleListe) {
        originaleListe.getSource().sort(new ProduktBoxBestandComparator());
    }

    /**
     * Methode zum Suchen in ProduktBoxViews.
     * Gibt true aus, wenn ProduktBox ein Produkt mit text enthält und
     * das Produkt in der Kategorie ist.
     *
     * @param produkt   Zu überprüfendes Produkt
     * @param text      Zu suchender Text
     * @param kategorie Filter für Kategorie, null für alle
     * @return produkt in kategorie und produkt enthält text → true
     */
    public static boolean produktSuche(Produkt produkt, String text, Kategorie kategorie) {
        // Kategorie-Filter
        if (kategorie != null && !produkt.getKategorien().contains(kategorie)) {
            return false;
        }
        // Wenn nichts eingegeben zeige alles
        if (text == null || text.isEmpty()) {
            return true;
        }
        // Ignoriere Groß- und Kleinschreibung
        String textL = text.toLowerCase();
        // Lade allen Text aus Produkt
        String produktText = produkt.getAlleTexte().toLowerCase();
        // Gibt true aus, wenn die Suche in produktText enthalten ist
        return produktText.contains(textL);
    }

    /**
     * Methode zum Suchen in ProduktBoxViews.
     * Gibt true aus, wenn ProduktBox ein Produkt mit text enthält und
     * das Produkt in der Kategorie ist.
     * @param produkt Zu überprüfendes Produkt
     * @param text Zu suchender Text
     * @param kategorie Filter
     * @param bestand geringster Bestand, der okay ist
     * @return produkt in kategorie und produkt enthält text → true
     */
    public static boolean produktSuche(Produkt produkt, String text, Kategorie kategorie, int bestand) {
        //Bestand-Filter
        if (bestand > produkt.getBestand()) {
            return false;
        }
        return produktSuche(produkt, text, kategorie);
    }

    /**
     * Methode zum Suchen in ProduktBoxViews.
     * Gibt true aus, wenn ProduktBox ein Produkt mit text enthält und
     * das Produkt in der Kategorie ist.
     * @param produkt Zu überprüfendes Produkt
     * @param text Zu suchender Text
     * @param kategorie Filter
     * @param bestand minimaler Bestand, der okay ist
     * @param minPreis minimaler Preis
     * @param maxPreis maximaler Preis
     * @return produkt in kategorie und produkt enthält text → true
     */
    public static boolean produktSuche(Produkt produkt, String text, Kategorie kategorie, int bestand, int minPreis, int maxPreis) {
        if(produkt.getPreis()< minPreis || produkt.getPreis()> maxPreis){
            return false;
        }
        return produktSuche(produkt, text, kategorie, bestand);
    }

    /**
     * Methode zum Filtern von Bestellungen.
     * @param bestellung zu prüfende Bestellung
     * @param status Status, welche die Bestellung haben muss;
     *               null, wenn kein Filter
     * @param erfuellbar true, wenn Bestellung erfuellbar sein muss
     * @return true, wenn Status der Bestellung gleich status bzw. status null
     * und Bestellung erfuellbar (wenn erfuellbar==true)
     */
    public static boolean bestellungSuche(K_Bestellung bestellung, K_Bestellung.Status status, boolean erfuellbar){
        //Bestellung muss erfüllbar sein
        if(erfuellbar && bestellungSuche(bestellung, status)){
            return bestellung.istErfuellbar() && bestellung.getStatus() == K_Bestellung.Status.OFFEN || bestellung.getStatus() == K_Bestellung.Status.VORBEREITET;
        }
        //Sonst Suchlogik ohne boolean erfuellbar
        return bestellungSuche(bestellung, status);
    }

    /**
     * Methode zum Filtern von Bestellungen.
     * @param bestellung zu prüfende Bestellung
     * @param status Status, welche die Bestellung haben muss;
     *               null, wenn kein Filter
     * @return true, wenn Status der Bestellung gleich status bzw. status null
     */
    public static boolean bestellungSuche(K_Bestellung bestellung, K_Bestellung.Status status){
        return status==null || bestellung.getStatus()==status;
    }

    /**
     * Klasse zum Vergleichen zweier Produktboxen.
     * Verglichen wird die Summe aus Bestand und Nachbestellungen.
     */
    public static class ProduktBoxBestandComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            // Caste zu ProduktBoxen
            ProduktBoxView b1 = (ProduktBoxView) n1;
            ProduktBoxView b2 = (ProduktBoxView) n2;
            int anzahl1 = (Integer.parseInt(b1.getBestandAnzahl().getText()))
                    + (Integer.parseInt(b1.getNachbestelltAnzahl().getText()));
            int anzahl2 = (Integer.parseInt(b2.getBestandAnzahl().getText()))
                    + (Integer.parseInt(b2.getNachbestelltAnzahl().getText()));
            return anzahl1 - anzahl2;
        }
    }

    // --------------------ALTE SUCHLOGIK------------------------------\\
    /**
     * Methode zum Filtern einer Produktliste nach Textkriterien,
     * ist für Kunden und Geschäftsführer gebraucht
     *
     * @param produktListe ist die zu filternde Liste
     * @param text         ist der Text, nach dem gefiltert werden soll
     * @param katSet       sind die Kategorien, nach denen gefiltert werden soll
     */
    public static List<Produkt> produktListeFiltern(ObservableList<Produkt> produktListe, String text,
            ArrayList<Kategorie> katSet) {
        return produktListe.stream()
                .filter(pred -> pred.getName().contains(text) || checkProduktKategorien(pred, katSet)).toList();
    }

    private static boolean checkProduktKategorien(Produkt produkt, ArrayList<Kategorie> katSet) {
        Iterator<Kategorie> it = katSet.iterator();
        for (int a = 0; a < katSet.size(); a++) {
            if (produkt.getKategorien().contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Filtert eine Liste von Bestellungen nach einer eingegebenen Bestellnummer
     *
     * @param bestListe ist die zu filternde Liste
     * @param BestNr    ist die eingegebene BestellNr
     * @param status    ist der Bestellstatus, nach dem Gefiltert wird
     */
    public static List<K_Bestellung> bestListeFiltern(ObservableList<K_Bestellung> bestListe, String BestNr,
            K_Bestellung.Status status) {
        return bestListe.stream().filter(fbestellungen -> fbestellungen.getStatus().equals(status)
                || Integer.toString(fbestellungen.getId()).contains(BestNr)).toList();
    }

    /**
     * Filtert eine Liste von Nachbestellungen nach einer eingegebenen Bestellnummer
     *
     * @param bestListe ist die zu filternde Liste
     * @param text      ist die Eingabe, nach der gefiltert wird
     */
    public static List<E_Bestellung> EBestListeFiltern(ObservableList<E_Bestellung> bestListe, String text) {

        return bestListe.stream().filter(name -> name.getProdukt().getName().contains(text)).toList();
    }
}
