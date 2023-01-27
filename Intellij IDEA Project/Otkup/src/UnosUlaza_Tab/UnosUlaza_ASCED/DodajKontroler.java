package UnosUlaza_Tab.UnosUlaza_ASCED;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Gajba;
import model.UnosUlaza;
import UnosUlaza_Tab.UnosUlazaTab;

public class DodajKontroler  implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		UnosUlazaTab.getInstance().setUnosNovog(true);
		
		int poslednjaSifra = 0;	
		for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {   //pravim preporuku sifre
			if(uu.getSifra() > poslednjaSifra)
				poslednjaSifra = uu.getSifra();
		}
		if(poslednjaSifra !=0) {
			UnosUlaza poslednji = Firma.getInstance().getTrenutnaGodina().UnosUlazaSaSifrom(poslednjaSifra);		
			LocalDate poslednjiDatum = poslednji.getDatum();
			UnosUlazaTab.getInstance().getDpDatum().setValue(poslednjiDatum);
			Gajba poslednjagajba = poslednji.getGajba();
			UnosUlazaTab.getInstance().getCbGajba().getSelectionModel().select(poslednjagajba);
		}
		
		UnosUlazaTab.getInstance().getTfIzlazGajbe().setEditable(true);
		UnosUlazaTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));
				
		UnosUlazaTab.getInstance().getUnosFP().setDisable(false);   //podesavam prikaz
		UnosUlazaTab.getInstance().getBDodaj().setDisable(true);		 
		UnosUlazaTab.getInstance().getBSacuvaj().setDisable(false);
		UnosUlazaTab.getInstance().getBPonisti().setDisable(false);		
		UnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(false);

		UnosUlazaTab.getInstance().getDpDatum().requestFocus();
	
	}

}

