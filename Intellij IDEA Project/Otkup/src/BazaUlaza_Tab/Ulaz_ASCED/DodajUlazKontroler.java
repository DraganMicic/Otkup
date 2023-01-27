package BazaUlaza_Tab.Ulaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;
import UnosUlaza_Tab.UnosUlazaTab;


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
				
		BazaUlazaTab.getInstance().getUnosUlazaHB().setDisable(false);   //podesavam prikaz
		BazaUlazaTab.getInstance().getBDodajUlaz().setDisable(true);		 
		BazaUlazaTab.getInstance().getBSacuvajUlaz().setDisable(false);
		BazaUlazaTab.getInstance().getBPonistiUlaz().setDisable(false);		

		BazaUlazaTab.getInstance().getTfNazivUlaza().requestFocus();
	
		UnosUlazaTab.getInstance().updateTabele();
		UnosUlazaTab.getInstance().updateCBUlaz();
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().updateCbUlaz();
	}
}
