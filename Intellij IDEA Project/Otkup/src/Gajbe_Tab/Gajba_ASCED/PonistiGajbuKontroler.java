package Gajbe_Tab.Gajba_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Gajbe_Tab.GajbeTab;

public class PonistiGajbuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		GajbeTab.getInstance().setUnosNoveGajbe(false);
		GajbeTab.getInstance().setIzmenaGajbeUToku(false);
		
		GajbeTab.getInstance().SetUnosGajbeDisable();   //podesavam prikaz
		GajbeTab.getInstance().getBDodajGajbu().setDisable(false);
		GajbeTab.getInstance().getBPonistiGajbu().setDisable(true);
		GajbeTab.getInstance().getBSacuvajGajbu().setDisable(true);
		GajbeTab.getInstance().getBIzmeniGajbu().setDisable(true);
		GajbeTab.getInstance().getBObrisiGajbu().setDisable(true);
		GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().clearSelection();
		GajbeTab.getInstance().ocistiPoljaZaUnosGajbe();
		GajbeTab.getInstance().getBDodajGajbu().requestFocus();		
	}
}