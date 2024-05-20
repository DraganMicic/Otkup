package UnosIzlaza_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class PonistiPretraguKontroler1 implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		UnosIzlazaTab.getInstance().getDPPocetniPretraga().setValue(null);
		UnosIzlazaTab.getInstance().getDPKrajnjiPretraga().setValue(null);

		if(UnosIzlazaTab.getInstance().getBPonisitPretragu2().isDisable()){
			UnosIzlazaTab.getInstance().updateTabele();
			UnosIzlazaTab.getInstance().getRBSviObracun().setSelected(true);
		}

		UnosIzlazaTab.getInstance().getBPonisitPretragu2().setDisable(true);


	}


}
