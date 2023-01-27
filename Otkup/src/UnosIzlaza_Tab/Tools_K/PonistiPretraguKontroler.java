package UnosIzlaza_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class PonistiPretraguKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		UnosIzlazaTab.getInstance().getDPPocetniPretraga().setValue(null);
		UnosIzlazaTab.getInstance().getDPKrajnjiPretraga().setValue(null);
		UnosIzlazaTab.getInstance().updateCBProizvodjacPretraga();
		UnosIzlazaTab.getInstance().updateCBIzlazPretraga();
		UnosIzlazaTab.getInstance().updateTabele();
		UnosIzlazaTab.getInstance().getBPonisitPretragu().setDisable(true);
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();
		UnosIzlazaTab.getInstance().getRBSviObracun().setSelected(true);
	}


}
