package Gajbe_Tab.UnosGajbi_ASCEDP;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;

public class IzmeniUnosGajbiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		GajbeTab.getInstance().setIzmenaUnosaGajbi(true);
		
		UnosGajbi ug = GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		GajbeTab.getInstance().getTfSifraUnosaGajbi().setText(String.valueOf(ug.getSifra()));
		GajbeTab.getInstance().getDpDatumUnosaGajbi().setValue(ug.getDatum());
		GajbeTab.getInstance().getCbProizvodjac().setValue(ug.getProizvodjac());
		GajbeTab.getInstance().getCbPrevoznik().setValue(ug.getPrevoznik());
		GajbeTab.getInstance().getCbGajba().setValue(ug.getGajba());
		GajbeTab.getInstance().getTfUlazGajbi().setText(String.valueOf(ug.getGajbeUlaz()));
		GajbeTab.getInstance().getTfIzlazGajbi().setText(String.valueOf(ug.getGajbeIzlaz()));
		
		if(ug.getPrevoznik()==null)
			GajbeTab.getInstance().getCbPrevoznik().setDisable(true);
		
		if(ug.getProizvodjac() == null)
			GajbeTab.getInstance().getCbProizvodjac().setDisable(true);
		
		GajbeTab.getInstance().SetUnosZaduzenjaEnable();     //podesavam prikaz
		GajbeTab.getInstance().getBObrisiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBSacuvajUnosGajbe().setDisable(false);
		GajbeTab.getInstance().getBIzmeniUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBSacuvajIStampajUnsoGajbe().setDisable(false);
		GajbeTab.getInstance().getBStampajUnosGajbe().setDisable(true);

		GajbeTab.getInstance().getTfSifraUnosaGajbi().requestFocus();
	}
}
