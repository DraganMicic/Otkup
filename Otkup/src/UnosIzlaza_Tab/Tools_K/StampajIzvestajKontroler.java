package UnosIzlaza_Tab.Tools_K;

import UnosUlaza_Tab.UnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import UnosIzlaza_Tab.PRINT.PrintIIzveštajUnosaIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class StampajIzvestajKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		if(UnosIzlazaTab.getInstance().getDPPocetniIzvestaj().getValue()==null) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali početni datum.");
			UnosIzlazaTab.getInstance().getDPPocetniIzvestaj().requestFocus();
			a.show();
			return;	
		}
		
		if(UnosIzlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()==null) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali krajnji datum.");
			UnosIzlazaTab.getInstance().getDPKrajnjiIzvestaj().requestFocus();
			a.show();
			return;	
		}
		
		
		PrintIIzveštajUnosaIzlaza.getInstance().stampajIzveštajIzlaza(UnosIzlazaTab.getInstance().getDPPocetniIzvestaj().getValue(),UnosIzlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue());
		UnosIzlazaTab.getInstance().getDPKrajnjiIzvestaj().setValue(null);
		UnosIzlazaTab.getInstance().getDPPocetniIzvestaj().setValue(null);
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();
	
	}

}
