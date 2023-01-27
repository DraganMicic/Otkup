package UnosUlaza_Tab.UnosUlaza_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UnosUlaza;
import UnosUlaza_Tab.UnosUlazaTab;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		UnosUlazaTab.getInstance().setIzmenaUToku(true); 
		
		UnosUlaza uu = UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		UnosUlazaTab.getInstance().getTfSifra().setText(String.valueOf(uu.getSifra()));   //prepisujem njegove podatke 	
		UnosUlazaTab.getInstance().getDpDatum().setValue(uu.getDatum());
		UnosUlazaTab.getInstance().getCbProizvodjac().setValue(uu.getProizvodjac());
		UnosUlazaTab.getInstance().getCbIUlaz().setValue(uu.getUlaz());
		UnosUlazaTab.getInstance().getTfUlazGajbe().setText(String.valueOf(uu.getGajbe()));
		if(uu.getUnosGajbi() != null) {
			UnosUlazaTab.getInstance().getTfIzlazGajbe().setText(String.valueOf(uu.getUnosGajbi().getGajbeIzlaz()));
		}else {
			UnosUlazaTab.getInstance().getTfIzlazGajbe().setText(String.valueOf(uu.getGajbe()));
		}
		UnosUlazaTab.getInstance().getCbGajba().setValue(uu.getGajba());
		UnosUlazaTab.getInstance().getTfKolicinaBruto().setText(String.valueOf(uu.getKolicinaBruto()));
		UnosUlazaTab.getInstance().getTfKolicnaNeto().setText(String.valueOf(uu.getKolicinaNeto()));
		UnosUlazaTab.getInstance().getCbPrevoznik().setValue(uu.getPrevoznik());
				
		UnosUlazaTab.getInstance().getUnosFP().setDisable(false);     //podesavam prikaz 
		UnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosUlazaTab.getInstance().getBSacuvaj().setDisable(false);
		UnosUlazaTab.getInstance().getBIzmeni().setDisable(true);	
		UnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(false);
		UnosUlazaTab.getInstance().getBStampaj().setDisable(true);

		UnosUlazaTab.getInstance().getTfSifra().requestFocus();
	}
}
