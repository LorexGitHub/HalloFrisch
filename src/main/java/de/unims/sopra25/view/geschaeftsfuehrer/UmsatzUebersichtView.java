package de.unims.sopra25.view.geschaeftsfuehrer;

import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Klasse, die die Szene UmsatzUebersicht zeichnet
 * 
 * @author Frederik Fluegge
 */
public class UmsatzUebersichtView extends BorderPane{

	// Graph
	private LineChart linechart;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	private XYChart.Series serie;

	// TextFields
	private TextField jahr ;

	// Buttons
	private Button ok;

	// Labels
	private Label jahrText;

	// Box
	private VBox leftYear;

	/**
	 * Konstruktor, der die UmsatzUebersicht initialisiert
	 */
	public UmsatzUebersichtView() {
		initialisiereChart();
	}

	/**
	 * Methode, die den Graphen initialisiert
	 */
	private void initialisiereChart() {
		serie = new XYChart.Series();

		// Button
		ok = new Button("OK");
		ok.setTextFill(Color.color(0 ,  1  ,0));

		// Label
		jahrText = new Label("Gib ein Jahr ein :");


		// Label of the axis
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Monat");
		yAxis.setLabel("Euro");

		// Graph wird erzeugt
		linechart = new LineChart(xAxis, yAxis);
		this.setCenter(linechart);

		// Textfield
		jahr = new TextField();
		jahr.textProperty().addListener((obvervable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*\\.?\\d{0,2}")) {
				final String updatedValue = newValue.replace(',', '.')
						.replaceAll("(\\d*\\.?\\d{0,2}).*", "$1");
				Platform.runLater(() -> {
					jahr.setText(updatedValue);
				});
			}
		});

		// Set Box in Center of this View
		leftYear = new VBox();
		leftYear.getChildren().addAll(jahrText , jahr , ok);

		this.setLeft(leftYear);

	}

	/**
	 * Gibt die Tabelle des Umsatzes zurueck 
	 * @return linechart
	 */
	public LineChart getChart() {
		return linechart;
	}

	/**
	 * Gibt den Datensatz des Graphen zurueck 
	 * @return serie
	 */
	public XYChart.Series getSerie() {
		return serie;
	}

	/**
	 * Gibt das Textfield für das Jahr zurueck
	 * @return jahr
	 */
	public TextField getTexfield(){
		return jahr;
	}
	/**
	 * Gibt den ok Button zurueck
	 * @return ok
	 */
	public Button getOkButton(){
		return ok;
	}
}
