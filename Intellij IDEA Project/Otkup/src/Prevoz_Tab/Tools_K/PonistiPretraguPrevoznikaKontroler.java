package Prevoz_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Prevoz_Tab.PrevozTab;

public class PonistiPretraguPrevoznikaKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		PrevozTab.getInstance().getTfPretragaPrevoznika().clear();
		PrevozTab.getInstance().updateTabelePrevoznika();
		PrevozTab.getInstance().updateTabelePrevoza();
		PrevozTab.getInstance().getBPonistiPretraguPrevoznika().setDisable(true);
		PrevozTab.getInstance().getDPKrajnjiPretraga().setValue(null);
		PrevozTab.getInstance().getDPPocetniPretraga().setValue(null);
		PrevozTab.getInstance().getBDodaj().requestFocus();
		PrevozTab.getInstance().getRBSviObracun().setSelected(true);
		PrevozTab.getInstance().getRBsviPrevozniciSpisak().setSelected(true);
	
	}
	
}
