package BazaUlaza_Tab.Ulaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class IzmeniUlazKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setIzmenaUlazaUToku(true); 
		
		Ulaz u = BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		BazaUlazaTab.getInstance().getTfSifraUlaza().setText(String.valueOf(u.getSifra()));
		BazaUlazaTab.getInstance().getTfNazivUlaza().setText(u.getNaziv());
		BazaUlazaTab.getInstance().getTfOpisUlaza().setText(u.getOpis());
		
		BazaUlazaTab.getInstance().getUnosUlazaHB().setDisable(false);     //podesavam prikaz 
		BazaUlazaTab.getInstance().getBObrisiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBSacuvajUlaz().setDisable(false);
		BazaUlazaTab.getInstance().getBIzmeniUlaz().setDisable(true);	
		
		UnosUlazaTab.getInstance().updateTabele();
		UnosUlazaTab.getInstance().updateCBUlaz();
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().updateCbUlaz();

		BazaUlazaTab.getInstance().getTfSifraUlaza().requestFocus();
	}
}