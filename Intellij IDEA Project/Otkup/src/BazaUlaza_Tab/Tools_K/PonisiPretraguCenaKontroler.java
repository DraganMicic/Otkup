package BazaUlaza_Tab.Tools_K;

import BazaUlaza_Tab.BazaUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonisiPretraguCenaKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		BazaUlazaTab.getInstance().getTfPretragaCena().clear();
		BazaUlazaTab.getInstance().getDpPretragaCena().setValue(null);
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().getBPonistiPretraguCena().setDisable(true);
		BazaUlazaTab.getInstance().getBDodajCenu().requestFocus();
		BazaUlazaTab.getInstance().getRBSveCene().setSelected(true);
		
	}

}
