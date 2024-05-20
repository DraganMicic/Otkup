package Gajbe_Tab.UnosGajbi_ASCEDP;

import Gajbe_Tab.Tools_K.PonisitiPretraguUnosaGajbi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Gajbe_Tab.GajbeTab;

public class PonistiUnosGajbiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		GajbeTab.getInstance().setUnosUnosaGajbi(false);
		GajbeTab.getInstance().setIzmenaUnosaGajbi(false);
		
		GajbeTab.getInstance().SetUnosZaduzenjaDisable();   //podesavam prikaz
		GajbeTab.getInstance().getBDodajUnosGajbe().setDisable(false);
		GajbeTab.getInstance().getBPonistiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBSacuvajUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBIzmeniUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBObrisiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().clearSelection();
		GajbeTab.getInstance().ocitiPoljaZaUnosUnosaGajbi();
		GajbeTab.getInstance().updateCBPrevoznik();
		GajbeTab.getInstance().updateCBProizvodjac();
		GajbeTab.getInstance().getBDodajUnosGajbe().requestFocus();		
		GajbeTab.getInstance().getCbPrevoznik().setDisable(false);
		GajbeTab.getInstance().getCbProizvodjac().setDisable(false);
		GajbeTab.getInstance().getLp1().setDisable(false);
		//GajbeTab.getInstance().getLp2().setDisable(false);
		GajbeTab.getInstance().getBSacuvajIStampajUnsoGajbe().setDisable(true);
		GajbeTab.getInstance().getBStampajUnosGajbe().setDisable(true);
		new PonisitiPretraguUnosaGajbi().handle(event);
		
		
	}
}