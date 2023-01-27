package Proizvodjac_Tab;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonistiPretraguKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		ProizvodjaciTab.getInstance().getTfPretraga1().clear(); 
		ProizvodjaciTab.getInstance().getTfPretraga2().clear();
		ProizvodjaciTab.getInstance().getTfPretraga3().clear();		
		ProizvodjaciTab.getInstance().updateTabele();					
		ProizvodjaciTab.getInstance().getBPonistiPretragu().setDisable(true);		
		ProizvodjaciTab.getInstance().getBDodaj().requestFocus();
		ProizvodjaciTab.getInstance().getRBsviObracun().setSelected(true);
		ProizvodjaciTab.getInstance().getRBsviSpisak().setSelected(true);
	}

}
