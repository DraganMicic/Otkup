package BazaUlaza_Tab.Ulaz_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;


public class DodajUlazKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setUnosNovogUlaza(true);
	
		int poslednjaSifra = 0;	
		for(Ulaz u : Firma.getInstance().getTrenutnaGodina().getUlazi()) {   //pravim preporuku sifre
			if(u.getSifra() > poslednjaSifra)
				poslednjaSifra = u.getSifra();
		}
		
		BazaUlazaTab.getInstance().getTfSifraUlaza().setText(String.valueOf(poslednjaSifra+1));
				
		BazaUlazaTab.getInstance().SetUnosUlazaEnable();   //podesavam prikaz
		BazaUlazaTab.getInstance().getBDodajUlaz().setDisable(true);		 
		BazaUlazaTab.getInstance().getBSacuvajUlaz().setDisable(false);
		BazaUlazaTab.getInstance().getBPonistiUlaz().setDisable(false);		

		BazaUlazaTab.getInstance().getTfNazivUlaza().requestFocus();
	
		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBUlaz();
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().updateCbUlaz();
	}
}
