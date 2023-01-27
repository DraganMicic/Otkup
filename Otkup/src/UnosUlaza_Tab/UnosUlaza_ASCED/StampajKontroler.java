package UnosUlaza_Tab.UnosUlaza_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosUlaza_Tab.PRINT.POSPrintUlaz;
import UnosUlaza_Tab.UnosUlazaTab;

public class StampajKontroler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		POSPrintUlaz.getInstance().stampajUlazNaPOS(UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem());	
		UnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		UnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		UnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		UnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosUlazaTab.getInstance().getBDodaj().setDisable(false);
	}
	
	
	
}
