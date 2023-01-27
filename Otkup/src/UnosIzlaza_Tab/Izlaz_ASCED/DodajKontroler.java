package UnosIzlaza_Tab.Izlaz_ASCED;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Izlaz;
import model.UnosIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class DodajKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		UnosIzlazaTab.getInstance().setUnosNovog(true);
		
		int poslednjaSifra = 0;	
		
		for(UnosIzlaza ui : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()) {   //pravim preporuku sifre
			if(ui.getSifra() > poslednjaSifra)
				poslednjaSifra = ui.getSifra();			
		}
		
		if(poslednjaSifra !=0) {
			UnosIzlaza poslednji = Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().get(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().size()-1);		
			LocalDate poslednjiDatum = poslednji.getDatum();
			Izlaz poslednjiIzlaz = poslednji.getIzlaz();
			UnosIzlazaTab.getInstance().getDpDatum().setValue(poslednjiDatum);
			UnosIzlazaTab.getInstance().getCbIzlaz().setValue(poslednjiIzlaz);
		}
		
		
		
		
		UnosIzlazaTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));
				
		UnosIzlazaTab.getInstance().getUnosFp().setDisable(false);   //podesavam prikaz
		UnosIzlazaTab.getInstance().getBDodaj().setDisable(true);		 
		UnosIzlazaTab.getInstance().getBSacuvaj().setDisable(false);
		UnosIzlazaTab.getInstance().getBPonisti().setDisable(false);		

		UnosIzlazaTab.getInstance().getDatePicker().requestFocus();
	
	}

}
