package de.unims.sopra25.view.geschaeftsfuehrer;

import de.unims.sopra25.model.entitaeten.Kategorie;
import de.unims.sopra25.model.logik.FehlerVerwalten;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Klasse, die die Szene ProduktHinzufuegen zeichnet
 *
 * @author Frederik Fluegge
 */
public class ProduktHinzufuegenView extends VBox {

	// Abstaende
	private final int H_GAP = 10;
	private final int V_GAP = 10;

	// TextFields
	private TextField bild;
	private TextField name;
	private TextField preis;
	private TextField beschreibung;

	// Buttons
	private Button erstellen;

    // Menü für die Kategorien
    private MenuButton kategorieMenu;
    private TextField neueKategorieText;
    private Button neueKategorieButton;

	// Error / Success Message
	private Text notifyMessage;

    /**
     * Konstruktor, der die ProduktHinzufuegen erstellt
     */
    public ProduktHinzufuegenView(List<Kategorie> kategorien) {
        initialisiereProduktHinzufuegen(kategorien);
    }

    /**
     * Initialisiert eine ProduktHinzufuegen
     */
    private void initialisiereProduktHinzufuegen(List<Kategorie> kategorien) {
        // Stlying
        setPadding(new Insets(V_GAP, H_GAP, V_GAP, H_GAP));

		// Bild
		bild = new TextField();
		bild.setPromptText("Bild URL");

		// Name
		name = new TextField();
		name.setPromptText("Name des Produktes");

		// Preis
		preis = new TextField();
		preis.setPromptText("Preis des Produktes");

		// Beschreibung
		beschreibung = new TextField();
		beschreibung.setPromptText("Beschreibung des Produktes eingeben");

		// Button
		erstellen = new Button("Produkt erstellen");

		// Error / Success Message
		notifyMessage = new Text();

        initalizeKategorieMenu(kategorien);
        neueKategorieText= new TextField();
        neueKategorieText.setPromptText("Name");
        neueKategorieButton= new Button("Kategorie hinzufügen");
        HBox kategorieBox= new HBox(neueKategorieText, neueKategorieButton);
        kategorieBox.setPadding(new Insets(20, 0 ,20, 0));

        getChildren().addAll(bild, name, preis, beschreibung, kategorieMenu, erstellen, kategorieBox);
    }

    /**
     * Initialisiert das Kategorie-Menu
     */
    private void initalizeKategorieMenu(List<Kategorie> kategorien) {
        kategorieMenu= new MenuButton("Kategorien");
        kategorieMenu.getStyleClass().add("button");
        kategorieMenu.getStyleClass().add("menu");
        for(Kategorie k: kategorien){
            kategorieHinzufuegen(k);
        }
    }

    /**
     * Fügt der Auswahlliste eine Kategorie hinzu
     * @param k Hinzuzufügende Kategorie
     */
    public void kategorieHinzufuegen(Kategorie k){
        CheckBox box = new CheckBox(k.getName());
        CustomMenuItem item = new CustomMenuItem(box);
        item.setHideOnClick(false);
        kategorieMenu.getItems().add(item);
    }

	/**
	 * View, die gezeigt wird, wenn ein Input field nicht ausgefuellt wurde
	 */
	public void inputFailed() {
		notifyMessage.setText("Die Felder wurden nicht richtig ausgefüllt");
		notifyMessage.setStyle("-fx-fill: red");

		HBox errorBox = new HBox();
		errorBox.getChildren().add(notifyMessage);
		getChildren().add(errorBox);
		//Fehler anlegen
		FehlerVerwalten fehler = new FehlerVerwalten();
		fehler.addFehler("Produkt anlegen fehlgeschlagen");
	}

    /**
     * View, die gezeigt wird, wenn ein Produkt erfolgreich hinzugefügt wurde
     */
    public void inputSucced(String text) {
        notifyMessage.setText(text);
        notifyMessage.setStyle("-fx-fill: green");

		HBox successBox = new HBox();
		successBox.getChildren().add(notifyMessage);
		getChildren().add(successBox);
	}

    /**
     * Gibt den Button zum Erstellen eines Produkts zurück
     *
     * @return Button zum Erstellen eines Produkts
     */
    public Button getErstellenButton() {
        return erstellen;
    }

    /**
     * Gibt den Input des TextFields für die Bild-URL zurück
     *
     * @return TextFields für die Bild-URL
     */
    public TextField getBildTextField() {
        return bild;
    }

    /**
     * Gibt den Input des TextFields für den Bild-Namen zurück
     *
     * @return TextFields für die Bild-Namen
     */
    public TextField getNameTextField() {
        return name;
    }

    /**
     * Gibt den Input des TextFields für den Bild-Preis zurück
     *
     * @return TextFields für die Bild-Preis
     */
    public TextField getPreisTextField() {
        return preis;
    }

    /**
     * Gibt den Input des TextFields für den Bild-Beschreibung zurück
     *
     * @return TextFields für die Bild-Beschreibung
     */
    public TextField getBeschreibungTextField() {
        return beschreibung;
    }

    /**
     * Getter Methode für das Kategorie-Menu
     *
     * @return TextField
     */
    public MenuButton getKategorieMenu() {
        return kategorieMenu;
    }

    /**
     * Getter Methode für Kategorie-Anlegen-Button
     * @return Button Kategorie anlegen
     */
    public Button getNeueKategorieButton() {
        return neueKategorieButton;
    }

    /**
     * Getter Methode für Kategorie-Name-Feld
     * @return TextField Kategorie
     */
    public TextField getNeueKategorieText() {
        return neueKategorieText;
    }
}
