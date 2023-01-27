package Gajbe_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Gajbe_Tab.GajbeTab;

public class PonistiPretraguGajbiKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		GajbeTab.getInstance().getTFPretragaGajbi().clear();
		GajbeTab.getInstance().updateTabeleGajbi();
		GajbeTab.getInstance().getBPonisitPretraguGajbi().setDisable(true);
		GajbeTab.getInstance().getBDodajGajbu().requestFocus();		
		GajbeTab.getInstance().getRBSveGajbeObracun().setSelected(true);
	}

}
