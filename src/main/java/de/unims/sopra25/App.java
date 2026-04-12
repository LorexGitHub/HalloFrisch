package de.unims.sopra25;
import de.unims.sopra25.controller.authentication.AuthenticationStageController;
import de.unims.sopra25.model.logik.Serialisierung;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Klasse, die das Programm startet
 */
public class App extends Application {
    public static void main(String[] args) {
        for(String arg : args) {
        	if(arg.equals("--gib-beispiele")) {
        		Serialisierung.getSerialisierung().initBeispielDaten();
        	}
        }
    	launch(args);
    }

	/**
	 * Start Methode
	 * @param stage
	 */
	@Override
	public void start(Stage stage) {
		new AuthenticationStageController();
	}
}
