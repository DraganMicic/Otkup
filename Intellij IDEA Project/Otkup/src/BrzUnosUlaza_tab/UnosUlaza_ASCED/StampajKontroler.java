package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import BrzUnosUlaza_tab.PRINT.POSPrintUlaz;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StampajKontroler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		POSPrintUlaz.getInstance().stampajUlazNaPOS(BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem());
		BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BrzUnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBDodaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}
	
	
	
}
