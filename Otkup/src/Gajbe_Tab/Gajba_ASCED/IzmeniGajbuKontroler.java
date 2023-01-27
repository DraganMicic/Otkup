package Gajbe_Tab.Gajba_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Gajba;
import Gajbe_Tab.GajbeTab;

public class IzmeniGajbuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		GajbeTab.getInstance().setIzmenaGajbeUToku(true);
		
		Gajba g = GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		GajbeTab.getInstance().getTfSifraGajbe().setText(String.valueOf(g.getSifra()));   //prepisujem njegove podatke 
		GajbeTab.getInstance().getTfNazivGajbe().setText(g.getNaziv());
		GajbeTab.getInstance().getTfTezinaGajbe().setText(String.valueOf(g.getTezina()));
		GajbeTab.getInstance().getTfUkupnoNaRaspolaganjuGajbi().setText(String.valueOf(g.getUkupnoNaRaspolaganju()));
		
		GajbeTab.getInstance().getUnosGajbiFP().setDisable(false);     //podesavam prikaz 
		GajbeTab.getInstance().getBObrisiGajbu().setDisable(true);
		GajbeTab.getInstance().getBSacuvajGajbu().setDisable(false);
		GajbeTab.getInstance().getBIzmeniGajbu().setDisable(true);

		GajbeTab.getInstance().getTfSifraGajbe().requestFocus();
	}
}
