package Prevoz_Tab.Prevoznik_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Prevoz_Tab.PrevozTab;

public class PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		PrevozTab.getInstance().setUnosNovog(false);
		PrevozTab.getInstance().setIzmenaUToku(false);
		
		PrevozTab.getInstance().getUnosHB().setDisable(true);    //podesavam prikaz
		PrevozTab.getInstance().getBDodaj().setDisable(false);
		PrevozTab.getInstance().getBPonisti().setDisable(true);
		PrevozTab.getInstance().getBSacuvaj().setDisable(true);
		PrevozTab.getInstance().getBIzmeni().setDisable(true);
		PrevozTab.getInstance().getBObrisi().setDisable(true);
		PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().clearSelection();
		PrevozTab.getInstance().ocistiPoljaZaUnos();
		PrevozTab.getInstance().getBDodaj().requestFocus();	
		PrevozTab.getInstance().getRBSviObracun().setSelected(true);
	}

}

