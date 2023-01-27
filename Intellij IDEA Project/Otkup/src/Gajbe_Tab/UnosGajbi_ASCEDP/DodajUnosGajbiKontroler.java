package Gajbe_Tab.UnosGajbi_ASCEDP;

import java.time.LocalDate;

import Gajbe_Tab.Tools_K.PonisitiPretraguUnosaGajbi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Gajba;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;

public class DodajUnosGajbiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		GajbeTab.getInstance().setUnosUnosaGajbi(true);
		
		int poslednjaSifra = 0;	
		for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {   //pravim preporuku sifre
			if(ug.getSifra() > poslednjaSifra)
				poslednjaSifra = ug.getSifra();
		}
		
		if(poslednjaSifra !=0) {
			UnosGajbi poslednji = Firma.getInstance().getTrenutnaGodina().UnosGajbiSaSifrom(poslednjaSifra);		
			LocalDate poslednjiDatum = poslednji.getDatum();
			Gajba poslednjagajba = poslednji.getGajba();
			GajbeTab.getInstance().getDpDatumUnosaGajbi().setValue(poslednjiDatum);
			GajbeTab.getInstance().getCbGajba().setValue(poslednjagajba);
		}
		
		GajbeTab.getInstance().getTfSifraUnosaGajbi().setText(String.valueOf(poslednjaSifra+1));
				
		GajbeTab.getInstance().getUnosUnosaGajbiFP().setDisable(false);   //podesavam prikaz
		GajbeTab.getInstance().getBDodajUnosGajbe().setDisable(true);		 
		GajbeTab.getInstance().getBSacuvajUnosGajbe().setDisable(false);
		GajbeTab.getInstance().getBPonistiUnosGajbe().setDisable(false);			
		GajbeTab.getInstance().getCbPrevoznik().setDisable(false);
		GajbeTab.getInstance().getCbProizvodjac().setDisable(false);
		GajbeTab.getInstance().getBSacuvajIStampajUnsoGajbe().setDisable(false);
		new PonisitiPretraguUnosaGajbi().handle(event);

		GajbeTab.getInstance().getDpDatumUnosaGajbi().requestFocus();
	
	}

}