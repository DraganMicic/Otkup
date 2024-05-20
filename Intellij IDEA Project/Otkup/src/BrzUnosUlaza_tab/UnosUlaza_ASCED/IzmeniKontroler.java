package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UnosUlaza;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		BrzUnosUlazaTab.getInstance().setIzmenaUToku(true);
		
		UnosUlaza uu = BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca

		BrzUnosUlazaTab.getInstance().getDpDatum().setValue(uu.getDatum());  //prepisujem njegove podatke
		BrzUnosUlazaTab.getInstance().getCbProizvodjac().setValue(uu.getProizvodjac());
		BrzUnosUlazaTab.getInstance().getCbIUlaz().setValue(uu.getUlaz());
		BrzUnosUlazaTab.getInstance().getTfUlazGajbe().setText(String.valueOf(uu.getGajbe()));
		BrzUnosUlazaTab.getInstance().getTfSifra().setText(String.valueOf(uu.getSifra()));

		if(uu.getUnosGajbi() != null) {
			BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().setText(String.valueOf(uu.getUnosGajbi().getGajbeIzlaz()));
		}else {
			BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().setText(String.valueOf(uu.getGajbe()));
		}
		BrzUnosUlazaTab.getInstance().getCbGajba().setValue(uu.getGajba());
		BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().setText(String.valueOf(uu.getKolicinaBruto()));
		BrzUnosUlazaTab.getInstance().getTfKolicnaNeto().setText(String.valueOf(uu.getKolicinaNeto()));
		BrzUnosUlazaTab.getInstance().getCbPrevoznik().setValue(uu.getPrevoznik());

		BrzUnosUlazaTab.getInstance().SetUnosEnable();     //podesavam prikaz
		BrzUnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBStampaj().setDisable(true);

		BrzUnosUlazaTab.getInstance().getDpDatum().requestFocus();
	}
}
