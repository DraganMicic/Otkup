package Gajbe_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Gajbe_Tab.GajbeTab;

public class PonisitiPretraguUnosaGajbi implements EventHandler<ActionEvent> {


	public void handle(ActionEvent event) {
		GajbeTab.getInstance().updateCBGajbaPretraga();
		GajbeTab.getInstance().updateCBPrevoznikPretraga();
		GajbeTab.getInstance().updateCBProizvodjacPretraga();
		GajbeTab.getInstance().getRBprevoznik().setSelected(false);
		GajbeTab.getInstance().getRBproizvodjac().setSelected(false);
		GajbeTab.getInstance().getCBGajbaPretraga().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getCBprevoznikPretraga().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getCBproizvodjacPretraga().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getBPonistiPretragu().setDisable(true);
		GajbeTab.getInstance().updateTabeleUnosaGajbi();	
		GajbeTab.getInstance().getLp1().setText("");
		GajbeTab.getInstance().getRBSviUnosiGajbiObracun().setSelected(true);


		
	}

}
