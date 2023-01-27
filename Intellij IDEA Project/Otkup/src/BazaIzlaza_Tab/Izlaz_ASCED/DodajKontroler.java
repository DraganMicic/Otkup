package BazaIzlaza_Tab.Izlaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Izlaz;
import BazaIzlaza_Tab.BazaIzlazaTab;

public class DodajKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		
		BazaIzlazaTab.getInstance().setUnosNovog(true);
	
		int poslednjaSifra = 0;	
		for(Izlaz i : Firma.getInstance().getTrenutnaGodina().getIzlazi()) {   //pravim preporuku sifre
			if(i.getSifra() > poslednjaSifra)
				poslednjaSifra = i.getSifra();
		}
		
		BazaIzlazaTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));
				
		BazaIzlazaTab.getInstance().getUnosHB().setDisable(false);   //podesavam prikaz
		BazaIzlazaTab.getInstance().getBDodaj().setDisable(true);		 
		BazaIzlazaTab.getInstance().getBSacuvaj().setDisable(false);
		BazaIzlazaTab.getInstance().getBPonisti().setDisable(false);		

		BazaIzlazaTab.getInstance().getTfNaziv().requestFocus();
	}

}
