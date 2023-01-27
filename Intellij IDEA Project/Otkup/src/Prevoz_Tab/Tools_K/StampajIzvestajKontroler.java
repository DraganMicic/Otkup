package Prevoz_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Prevoznik;
import Prevoz_Tab.PRINT.PrintIzvestajPrevoznika;
import Prevoz_Tab.PrevozTab;

public class StampajIzvestajKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		if(PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem() == null) {
			Alert a = new Alert(AlertType.ERROR, "Prevoznik nije selektovan");
			a.show();
			return;	
		}
		Prevoznik pre = PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem();
		PrintIzvestajPrevoznika.getInstance().StampajIzvestajPrevoznika(pre);
		
	}

}
