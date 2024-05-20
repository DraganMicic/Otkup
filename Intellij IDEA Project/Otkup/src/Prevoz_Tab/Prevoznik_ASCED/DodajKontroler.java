package Prevoz_Tab.Prevoznik_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Prevoznik;
import Prevoz_Tab.PrevozTab;

public class DodajKontroler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		
		PrevozTab.getInstance().setUnosNovog(true);
	
		int poslednjaSifra = 0;	
		for(Prevoznik p : Firma.getInstance().getTrenutnaGodina().getPrevoznici()) {   //pravim preporuku sifre
			if(p.getSifra() > poslednjaSifra)
				poslednjaSifra = p.getSifra();
		}
		
		PrevozTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));
				
		PrevozTab.getInstance().SetuUnosEnable();   //podesavam prikaz
		PrevozTab.getInstance().getBDodaj().setDisable(true);		 
		PrevozTab.getInstance().getBSacuvaj().setDisable(false);
		PrevozTab.getInstance().getBPonisti().setDisable(false);		

		PrevozTab.getInstance().getTfIme().requestFocus();
	}

}

