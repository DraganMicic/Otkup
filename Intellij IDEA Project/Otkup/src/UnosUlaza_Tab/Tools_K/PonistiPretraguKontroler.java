package UnosUlaza_Tab.Tools_K;

import UnosUlaza_Tab.UnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonistiPretraguKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		UnosUlazaTab.getInstance().getDPkrajnjiPretraga().setValue(null);
		UnosUlazaTab.getInstance().getDPPocetniPretraga().setValue(null);
		UnosUlazaTab.getInstance().updateCBProizvodjacPretraga();
		UnosUlazaTab.getInstance().updateCBUlazPretraga();
		UnosUlazaTab.getInstance().updateCBPrevoznikPretraga();
		UnosUlazaTab.getInstance().updateTabele();
		UnosUlazaTab.getInstance().getBPonistiPretragu().setDisable(true);
		UnosUlazaTab.getInstance().getBDodaj().requestFocus();		
		UnosUlazaTab.getInstance().getRBSviObracun().setSelected(true);
	}

}
