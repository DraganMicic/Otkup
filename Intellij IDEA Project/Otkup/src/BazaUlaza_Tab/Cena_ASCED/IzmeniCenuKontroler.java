package BazaUlaza_Tab.Cena_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.CenaUlaza;
import BazaUlaza_Tab.BazaUlazaTab;

public class IzmeniCenuKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setIzmenaCeneUToku(true); 
		
		CenaUlaza cu = BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		BazaUlazaTab.getInstance().getTfSifraCene().setText(String.valueOf(cu.getSifra()));
		BazaUlazaTab.getInstance().getCbUlaz().setValue(cu.getUlaz());
		BazaUlazaTab.getInstance().getTfCenaBezPrevoza().setText(String.valueOf(cu.getCenaBezPrevoza()));
		BazaUlazaTab.getInstance().getTfCenaSaPrevozom().setText(String.valueOf(cu.getCenaSaPrevozom()));
		BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().setValue(cu.getPocetakVazenja());
		BazaUlazaTab.getInstance().getDpKrajVazenjaCene().setValue(cu.getKrajVazenja());
	
		BazaUlazaTab.getInstance().getUnosiCena1HB().setDisable(false);     //podesavam prikaz 
		BazaUlazaTab.getInstance().getUnosiCena2HB().setDisable(false);
		BazaUlazaTab.getInstance().getBObrisiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBSacuvajCenu().setDisable(false);
		BazaUlazaTab.getInstance().getBIzmeniCenu().setDisable(true);

		BazaUlazaTab.getInstance().getTfSifraCene().requestFocus();
	}
}