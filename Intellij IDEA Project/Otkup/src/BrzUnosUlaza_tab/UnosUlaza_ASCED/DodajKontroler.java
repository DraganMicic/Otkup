package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Gajba;
import model.UnosUlaza;

import java.time.LocalDate;

public class DodajKontroler  implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BrzUnosUlazaTab.getInstance().setUnosNovog(true);
		
		int poslednjaSifra = 0;	
		for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {   //pravim preporuku sifre
			if(uu.getSifra() > poslednjaSifra)
				poslednjaSifra = uu.getSifra();
		}
		if(poslednjaSifra !=0) {
			UnosUlaza poslednji = Firma.getInstance().getTrenutnaGodina().UnosUlazaSaSifrom(poslednjaSifra);		
			LocalDate poslednjiDatum = poslednji.getDatum();
			BrzUnosUlazaTab.getInstance().getDpDatum().setValue(poslednjiDatum);
			Gajba poslednjagajba = poslednji.getGajba();
			BrzUnosUlazaTab.getInstance().getCbGajba().getSelectionModel().select(poslednjagajba);
		}

		BrzUnosUlazaTab.getInstance().SetUnosEnable();
		BrzUnosUlazaTab.getInstance().getTfSifra().setText(String.valueOf(poslednjaSifra+1));

		BrzUnosUlazaTab.getInstance().getBDodaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBPonisti().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getDpDatum().requestFocus();
	
	}
}

