package BazaUlaza_Tab.Cena_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.CenaUlaza;
import model.Firma;
import BazaUlaza_Tab.BazaUlazaTab;

public class DodajCenuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setUnosNoveCene(true);
	
		int poslednjaSifra = 0;	
		for(CenaUlaza cu : Firma.getInstance().getTrenutnaGodina().getCeneUlaza()) {   //pravim preporuku sifre
			if(cu.getSifra() > poslednjaSifra)
				poslednjaSifra = cu.getSifra();
		}
		
		BazaUlazaTab.getInstance().getTfSifraCene().setText(String.valueOf(poslednjaSifra+1));
				
		BazaUlazaTab.getInstance().SetUnosCenaEnable();  //podesavam prikaz
		BazaUlazaTab.getInstance().getBDodajCenu().setDisable(true);		 
		BazaUlazaTab.getInstance().getBSacuvajCenu().setDisable(false);
		BazaUlazaTab.getInstance().getBPonistiCenu().setDisable(false);		

		BazaUlazaTab.getInstance().getTfSifraCene().requestFocus();
	}
}