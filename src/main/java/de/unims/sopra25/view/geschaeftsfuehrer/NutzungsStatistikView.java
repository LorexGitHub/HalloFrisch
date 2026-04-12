package de.unims.sopra25.view.geschaeftsfuehrer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Klasse, die die Szene NutzungsStatistikView zeichnet
 * 
 * @author Frederik Fluegge , Atilla Alaoglu
 */
public class NutzungsStatistikView extends BorderPane {
	// Die Anzaeige der Nutzerzahlen (variabel)
	private Label anzahlKunden;
	private Label anzahlGeschaeftsfuehrer;
	private Label anzahlLieferanden;
	private Label anzahlEinkaeufer;
	private Label anzahlGesamt;
	// Feste Label für die Ansicht
	private Label anzahlTextKunden;
	private Label anzahlTextGeschaeftsfuehrer;
	private Label anzahlTextLieferant;
	private Label anzahlTextEinkaufer;
	private Label anzahlTextGesamt;
	// VBox für die Position
	private VBox topKunden;
	private VBox leftLieferant;
	private VBox centerGeschaeftsfuehrer;
	private VBox rightEinkaeufer;
	private VBox bottumGesamt;

	/**
	 * Konstruktor der die NutzungsStatistikView intialisiert
	 * @param anzahlKunden, anzahlGeschaeftsfuehrer, anzahlLieferanden, anzahlEinkaeufer
	 */
	public NutzungsStatistikView(Integer anzahlKunden, Integer anzahlGeschaeftsfuehrer, Integer anzahlLieferanden,
			Integer anzahlEinkaeufer) {
		initialisiereView(anzahlKunden, anzahlGeschaeftsfuehrer, anzahlLieferanden, anzahlEinkaeufer);
	}

	/**
	 * Intialisiert die View der Nutzungsstatistik und füllt diese
	 * @param anzahlKunden, anzahlGeschaeftsfuehrer, anzahlLieferanden, anzahlEinkaeufer
	 */
	private void initialisiereView(Integer anzahlKunden, Integer anzahlGeschaeftsfuehrer, Integer anzahlLieferanden,
			Integer anzahlEinkaeufer) {
		setAnzahl(anzahlKunden, anzahlGeschaeftsfuehrer, anzahlLieferanden, anzahlEinkaeufer);
		setText();
		setVBox();
		setBorderPane();
	}

	/**
	 * Setzt die Anzahlen der Nutzer 
	 * @param anzahlKunden, anzahlGeschaeftsfuehrer, anzahlLieferanden, anzahlEinkaeufer
	 */
	public void setAnzahl(Integer anzahlKunden, Integer anzahlGeschaeftsfuehrer, Integer anzahlLieferanden,
			Integer anzahlEinkaeufer) {
		Integer anzahlGesamt = anzahlKunden + anzahlGeschaeftsfuehrer + anzahlLieferanden + anzahlEinkaeufer;

		// Setzung der Übergebenen Nutzungsstatistiken
		this.anzahlKunden = new Label(anzahlKunden.toString());
		this.anzahlGeschaeftsfuehrer = new Label(anzahlGeschaeftsfuehrer.toString());
		this.anzahlLieferanden = new Label(anzahlLieferanden.toString());
		this.anzahlEinkaeufer = new Label(anzahlEinkaeufer.toString());
		this.anzahlGesamt = new Label(anzahlGesamt.toString());
	}

	/**
	 * Setzt den Textinhalts der Labels
	 */
	private void setText() {
		// Text um die Zahlen zu den Nutzern einzuordnen
		this.anzahlTextKunden = new Label("Anzahl an Kunden :");
		this.anzahlTextGeschaeftsfuehrer = new Label("Anzahl an Geschäftsführer :");
		this.anzahlTextLieferant = new Label("Anzahl an Lieferanten :");
		this.anzahlTextEinkaufer = new Label("Anzahl an Einkäufer :");
		this.anzahlTextGesamt = new Label("Anzahl an Nutzer :");
	}

	/**
	 * Setzt die VBoxen
	 */
	private void setVBox() {
		// Setzung der VBoxen
		this.topKunden = new VBox();
		this.leftLieferant = new VBox();
		this.centerGeschaeftsfuehrer = new VBox();
		this.rightEinkaeufer = new VBox();
		this.bottumGesamt = new VBox();
		// Füllung der VBoxen
		this.leftLieferant.getChildren().addAll(this.anzahlTextLieferant, this.anzahlLieferanden);
		this.centerGeschaeftsfuehrer.getChildren().addAll(this.anzahlTextKunden, this.anzahlKunden,
				this.anzahlTextGeschaeftsfuehrer, this.anzahlGeschaeftsfuehrer, this.anzahlTextGesamt,
				this.anzahlGesamt);
		this.rightEinkaeufer.getChildren().addAll(this.anzahlTextEinkaufer, this.anzahlEinkaeufer);
		// Position
		this.centerGeschaeftsfuehrer.setAlignment(Pos.CENTER);
		this.leftLieferant.setAlignment(Pos.CENTER);
		this.rightEinkaeufer.setAlignment(Pos.CENTER);
	}

	/**
	 * Setz die BorderPane
	 */
	private void setBorderPane() {
		// Füllt die BorderPane mit den VBoxen;
		this.setTop(topKunden);
		this.setLeft(leftLieferant);
		this.setCenter(centerGeschaeftsfuehrer);
		this.setRight(rightEinkaeufer);
		this.setBottom(bottumGesamt);
	}
}
