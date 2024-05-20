package Proizvodjac_Tab.Proizvodjac_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Proizvodjac_Tab.ProizvodjaciTab;

public class PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		ProizvodjaciTab.getInstance().setUnosNovog(false);
		ProizvodjaciTab.getInstance().setIzmenaUToku(false);
		
		ProizvodjaciTab.getInstance().SetUnosDisable();    //podesavam prikaz
		ProizvodjaciTab.getInstance().getBDodaj().setDisable(false);
		ProizvodjaciTab.getInstance().getBPonisti().setDisable(true);
		ProizvodjaciTab.getInstance().getBSacuvaj().setDisable(true);
		ProizvodjaciTab.getInstance().getBIzmeni().setDisable(true);
		ProizvodjaciTab.getInstance().getBObrisi().setDisable(true);
		ProizvodjaciTab.getInstance().getTabela().getSelectionModel().clearSelection();
		ProizvodjaciTab.getInstance().ocistiPoljaZaUnos();
		ProizvodjaciTab.getInstance().getBDodaj().requestFocus();	
		ProizvodjaciTab.getInstance().getRBsviObracun().setSelected(true);
		ProizvodjaciTab.getInstance().getRBsviSpisak().setSelected(true);
		
	}

}
