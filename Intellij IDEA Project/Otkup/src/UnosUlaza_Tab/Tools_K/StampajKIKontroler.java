package UnosUlaza_Tab.Tools_K;

import UnosUlaza_Tab.UnosUlazaTab;
import UnosUlaza_Tab.UnosUlaza_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import UnosUlaza_Tab.PRINT.POSPrintFertodiPapiric;
import UnosUlaza_Tab.PRINT.POSPrintKartonIdentifikacije;
import UnosUlaza_Tab.PRINT.POSPrintMikerPapiric;

public class StampajKIKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		
		if(UnosUlazaTab.getInstance().getRBKartonIndentifikacije().isSelected()) {
			if(UnosUlazaTab.getInstance().getTabela().getSelectionModel().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR, "Unos nije selektovan");
				a.show();
				return;	
			}
			POSPrintKartonIdentifikacije.getInstance().stampajKartonIdentifikacije(UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem());
			PonistiKontroler po  = new PonistiKontroler();
			po.handle(event);
		}
		
		if(UnosUlazaTab.getInstance().getRBMikerPapiric().isSelected()) {
			POSPrintMikerPapiric.getInstance().stampajMikerPapiric();
		}
		
		if(UnosUlazaTab.getInstance().getRBFertodiPapiric().isSelected()) {
			POSPrintFertodiPapiric.getInstance().stampajFertodiPapiric();
		}
		
		
	}

}
