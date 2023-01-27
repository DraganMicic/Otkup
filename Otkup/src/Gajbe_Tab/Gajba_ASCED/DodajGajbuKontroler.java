package Gajbe_Tab.Gajba_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Gajba;
import Gajbe_Tab.GajbeTab;

public class DodajGajbuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		GajbeTab.getInstance().setUnosNoveGajbe(true);;
		
		int poslednjaSifra = 0;	
		for(Gajba g : Firma.getInstance().getTrenutnaGodina().getGajbe()) {   //pravim preporuku sifre
			if(g.getSifra() > poslednjaSifra)
				poslednjaSifra = g.getSifra();
		}
		
		GajbeTab.getInstance().getTfSifraGajbe().setText(String.valueOf(poslednjaSifra+1));
				
		GajbeTab.getInstance().getUnosGajbiFP().setDisable(false);   //podesavam prikaz
		GajbeTab.getInstance().getBDodajGajbu().setDisable(true);		 
		GajbeTab.getInstance().getBSacuvajGajbu().setDisable(false);
		GajbeTab.getInstance().getBPonistiGajbu().setDisable(false);	
		GajbeTab.getInstance().getTfNazivGajbe().requestFocus();
	
	}

}