package Gajbe_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Gajbe_Tab.PRINT.PrintIzvestajUnosaGajbi;
import Gajbe_Tab.GajbeTab;

public class StampajIzvestajUnosaGajbiKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		if(GajbeTab.getInstance().getRBproizvodjacIzvestaj().isSelected()) {
			if(GajbeTab.getInstance().getCBProizvodjacIzvestaj().getSelectionModel().getSelectedItem() == null) {
				Alert a = new Alert(AlertType.ERROR, "Proizvođač nije selektovan");
				a.show();
				return;	
			}
			PrintIzvestajUnosaGajbi.getInstance().stamapjIzvestajUnosaGajbi(null, GajbeTab.getInstance().getCBProizvodjacIzvestaj().getSelectionModel().getSelectedItem());
			
			
		}else if(GajbeTab.getInstance().getRBPrevoznikIzvestaj().isSelected()) {
			if(GajbeTab.getInstance().getCBPrevoznikIzvestaj().getSelectionModel().getSelectedItem() == null) {
				Alert a = new Alert(AlertType.ERROR, "Prevoznik nije selektovan");
				a.show();
				return;	
			}
			PrintIzvestajUnosaGajbi.getInstance().stamapjIzvestajUnosaGajbi(GajbeTab.getInstance().getCBPrevoznikIzvestaj().getSelectionModel().getSelectedItem(), null);
			
		}
		
	}

}
