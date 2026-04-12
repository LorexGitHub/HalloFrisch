package de.unims.sopra25.controller.geschaeftsfuehrer;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import de.unims.sopra25.model.logik.NutzerVerwalten;
import de.unims.sopra25.view.geschaeftsfuehrer.NutzerRegistrierenView;

/**
 * Klasse, die Inputs vom NutzerRegistrierenView handlet
 *
 * @author Frederik Fluegge
 */
public class NutzerRegistrierenController {

	// Stage des Controllers
    private GeschaeftsfuehrerStageController stage;
    
    // View des Controllers
    private NutzerRegistrierenView view;
    
    // Logik des Controllers
    private NutzerVerwalten nutzerVerwalten;

    /**
     * Konstruktor
     *
     * @param stage Stage des Geschaeftsfuehrers
     */
    public NutzerRegistrierenController(GeschaeftsfuehrerStageController stage) {
        this.stage = stage;
        view = new NutzerRegistrierenView();

        nutzerVerwalten = new NutzerVerwalten();
        initialisiereEventHandler();
    }

    /**
     * Initialisiert die Event Handler
     */
    public void initialisiereEventHandler() {
        // Registrieren Button wird gedrückt: Neuer Nutzer wird angelegt
        view.getRegistrierenButton().setOnAction(event -> {

            // Falsche Eingaben abfangen
            if (!view.getPasswordTextField().getText().equals(view.getSecondPasswordTextField().getText())) {
                view.inputFailed("Die gewählten Passwörter stimmen nicht überein.");
            } else if (view.getUsernamTextField().getText().isEmpty() || view.getPasswordTextField().getText().isEmpty()
                    || view.getRadioButtonsToggleGroup().getSelectedToggle() == null) {

                // Error Message
                view.inputFailed("Die Felder wurden nicht richtig ausgefülllt.");

            } else {
                String radioButtoData = view.getRadioButtonsToggleGroup().getSelectedToggle().getUserData().toString();
                String username = view.getUsernamTextField().getText();
                String password = view.getPasswordTextField().getText();
                boolean angelegt = false;

                try {
                    // Lege Nutzer an, dessen Rolle ausewählt wurde
                    switch (radioButtoData) {
                        case "Geschäftsführer":
                            angelegt = nutzerVerwalten.addGeschaeftsfuehrer(username, password);
                            break;
                        case "Lieferant":
                            angelegt = nutzerVerwalten.addLieferant(username, password);
                            break;
                        case "Einkäufer":
                            angelegt = nutzerVerwalten.addEinkaeufer(username, password);
                            break;
                    }
                    if (angelegt) {
                        // Reset Input Fields
                        view.getUsernamTextField().clear();
                        view.getPasswordTextField().clear();
                        view.getSecondPasswordTextField().clear();
                        view.getRadioButtonsToggleGroup().getSelectedToggle().setSelected(false);
                        // Success Message
                        view.inputSucced();
                    }
                    // Nutzername existiert bereits
                    else {
                        view.inputFailed("Der Nutzername existiert bereits.");
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Getter-Methode für die View des Controllers
     *
     * @return view NutzerRegistrierenView
     */
    public NutzerRegistrierenView getView() {
        return view;
    }
}
