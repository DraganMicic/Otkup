package BrzUnosUlaza_tab.Tols_K;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import BrzUnosUlaza_tab.PRINT.POSPrintKartonIdentifikacije;
import BrzUnosUlaza_tab.PRINT.POSPrintObeleživač;
import BrzUnosUlaza_tab.UnosUlaza_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StampajKIKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {

		if(BrzUnosUlazaTab.getInstance().getCbObeleživač().getSelectionModel().getSelectedItem().equals("Karton identifikacije")){
			if(BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR, "Unos nije selektovan. Izaberite neki od unosa ulaza klikom na odgovarajući red u tabeli");
				a.show();
				return;
			}
			POSPrintKartonIdentifikacije.getInstance().stampajKartonIdentifikacije(BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem());
			PonistiKontroler po  = new PonistiKontroler();
			po.handle(event);

		}else {
			POSPrintObeleživač.getInstance().stampajObeleživač(BrzUnosUlazaTab.getInstance().getCbObeleživač().getSelectionModel().getSelectedItem()   );
		}
	}
}
