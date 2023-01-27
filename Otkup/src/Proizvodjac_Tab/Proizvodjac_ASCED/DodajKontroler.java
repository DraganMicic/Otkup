package Proizvodjac_Tab.Proizvodjac_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Proizvodjac;
import Proizvodjac_Tab.ProizvodjaciTab;

public class DodajKontroler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		
		ProizvodjaciTab.getInstance().setUnosNovog(true);
	
		int poslednjaSifra = 0;	
		for(Proizvodjac p : Firma.getInstance().getTrenutnaGodina().getProizvodjaci()) {   //pravim preporuku sifre
			if(p.getSifra() > poslednjaSifra)
				poslednjaSifra = p.getSifra();
		}
		
		ProizvodjaciTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));
		ProizvodjaciTab.getInstance().getCbPodrazumevaniPrevoznik().getSelectionModel().selectFirst();
				
		ProizvodjaciTab.getInstance().getUnosFP().setDisable(false);   //podesavam prikaz
		ProizvodjaciTab.getInstance().getBDodaj().setDisable(true);		 
		ProizvodjaciTab.getInstance().getBSacuvaj().setDisable(false);
		ProizvodjaciTab.getInstance().getBPonisti().setDisable(false);		

		ProizvodjaciTab.getInstance().getTfIme().requestFocus();
	}

}
