package Podesavanja_Tab;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class IzmeniPodesavanjaKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Izmena podešavanja");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		}
				
		PodesavanjaTab.getInstance().SetEditEnable();
		PodesavanjaTab.getInstance().getBSacuvaj().setDisable(false);
		PodesavanjaTab.getInstance().getBIzmeni().setDisable(true);
		PodesavanjaTab.getInstance().getBOdustani().setDisable(false);

		PodesavanjaTab.getInstance().getTfIme().requestFocus();
		
	}

}
