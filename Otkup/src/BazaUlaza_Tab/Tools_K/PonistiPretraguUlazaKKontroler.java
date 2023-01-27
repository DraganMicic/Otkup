package BazaUlaza_Tab.Tools_K;

import BazaUlaza_Tab.BazaUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonistiPretraguUlazaKKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		BazaUlazaTab.getInstance().getTfPretragaUlaza().clear();
		BazaUlazaTab.getInstance().updateTabeleUlaza();
		BazaUlazaTab.getInstance().getBPonistiPretraguUlaza().setDisable(true);
		BazaUlazaTab.getInstance().getBDodajUlaz().requestFocus();	
		BazaUlazaTab.getInstance().getRBSviUlaziSpisak().setSelected(true);
	}
}
