package UnosUlaza_Tab.Tools_K;

import UnosUlaza_Tab.PRINT.POSPrintObeleživač;
import UnosUlaza_Tab.UnosUlazaTab;
import UnosUlaza_Tab.UnosUlaza_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import UnosUlaza_Tab.PRINT.POSPrintKartonIdentifikacije;

public class StampajKIKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {

		if(UnosUlazaTab.getInstance().getCbObeleživač().getSelectionModel().getSelectedItem().equals("Karton identifikacije")){
			if(UnosUlazaTab.getInstance().getTabela().getSelectionModel().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR, "Unos nije selektovan. Izaberite neki od unosa ulaza klikom na odgovarajući red u tabeli");
				a.show();
				return;
			}
			POSPrintKartonIdentifikacije.getInstance().stampajKartonIdentifikacije(UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem());
			PonistiKontroler po  = new PonistiKontroler();
			po.handle(event);

		}else {
			POSPrintObeleživač.getInstance().stampajObeleživač(UnosUlazaTab.getInstance().getCbObeleživač().getSelectionModel().getSelectedItem()   );
		}
		
	}

}
