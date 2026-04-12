package de.unims.sopra25.controller.geschaeftsfuehrer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.unims.sopra25.model.logik.UmsatzStatistik;
import de.unims.sopra25.view.geschaeftsfuehrer.UmsatzUebersichtView;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;

/**
 * Klasse, die die UmsatzUebersicht Anzeige kontrolliert und die Daten auf die
 * Chart projiziert
 * 
 * @author Atilla Alaoglu
 */
public class UmsatzController {

	// Stage des Controllers
	private GeschaeftsfuehrerStageController stage;

	// View des Controllers
	private UmsatzUebersichtView view;

	// Statistik
	private UmsatzStatistik statistik;

	// Attribute explizit fuer die Darstellung der Umsatzstatistik
	private Calendar calendar;
	private int jahr;
	private String label;
	private Long umsatz;
	private String umsatzString;
	private String textUmsatz;
	private List<String> monate;

	/**
	 * Konstruktor der Klasse UmsatzController
	 * 
	 * @param stage Stage des Geschaeftsfuehrers
	 */
	public UmsatzController(GeschaeftsfuehrerStageController stage) {
		this.stage = stage;

		this.view = new UmsatzUebersichtView();
		this.statistik = new UmsatzStatistik();

		this.calendar = new GregorianCalendar();
		this.jahr = calendar.get(Calendar.YEAR);

		initialisiereView(statistik.getUmsatzVon(jahr), statistik.getGesamtumsatz());
	}

	/**
	 * Initialisiert die view und setzt die Werte in die Chart
	 * 
	 * @param jahresUmsatz
	 * @param umsatz
	 */
	private void initialisiereView(ArrayList<Long> jahresUmsatz, Long umsatz) {
		this.umsatz = umsatz / 100;
		textUmsatz = "Gesamtumsatz : ";
		umsatzString = this.umsatz.toString();

		initialisiereMonate();
		view.getChart().getData().clear();
		view.getSerie().getData().clear();

		// Test exponentieller Graph
		if (jahresUmsatz != null) {
			for (int i = 0; i < monate.size(); i++) {
				view.getSerie().getData().add(new XYChart.Data(monate.get(i), jahresUmsatz.get(i) / 100));
			}
		} else {
			for (int i = 0; i < monate.size(); i++) {
				view.getSerie().getData().add(new XYChart.Data(monate.get(i), 0));
			}
		}

		label = textUmsatz + umsatzString + " €";
		view.getSerie().setName(label);

		view.getOkButton().setOnAction((event) -> {
			int jahr = Integer.parseInt(view.getTexfield().getText());
			initialisiereView(statistik.getUmsatzVon(jahr), statistik.getGesamtumsatz());
		});

		view.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				view.getOkButton().arm();
				view.getOkButton().fire();
			}
		});
		view.getChart().getData().add(view.getSerie());
	}

	/**
	 * Fuellt eine Jahresliste mit Monaten
	 */
	private void initialisiereMonate() {
		monate = new ArrayList();

		monate.add("Januar");
		monate.add("Februar");
		monate.add("März");
		monate.add("April");
		monate.add("Mai");
		monate.add("Juni");
		monate.add("Juli");
		monate.add("August");
		monate.add("September");
		monate.add("Oktober");
		monate.add("November");
		monate.add("Dezember");
	}

	/**
	 * Getter Methode fuer die view
	 * 
	 * @return view
	 */
	public UmsatzUebersichtView getView() {
		return view;
	}
}
