package UnosIzlaza_Tab.Izlaz_ASCED;

import UnosUlaza_Tab.UnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UnosIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		UnosIzlazaTab.getInstance().setIzmenaUToku(true); 
		
		UnosIzlaza ui = UnosIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		UnosIzlazaTab.getInstance().getTfSifra().setText(String.valueOf(ui.getSifra()));   //prepisujem njegove podatke 
		UnosIzlazaTab.getInstance().getDatePicker().setValue(ui.getDatum());
		UnosIzlazaTab.getInstance().getCbProizvodjac().setValue(ui.getProizvodjac());
		UnosIzlazaTab.getInstance().getCbIzlaz().setValue(ui.getIzlaz());
		UnosIzlazaTab.getInstance().getTfBrOtpremnice().setText(String.valueOf(ui.getBrojOtpremnice()));
		UnosIzlazaTab.getInstance().getTfKolicina().setText(String.valueOf(ui.getKolicina()));
		
		UnosIzlazaTab.getInstance().getUnosFp().setDisable(false);     //podesavam prikaz
		UnosIzlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosIzlazaTab.getInstance().getBSacuvaj().setDisable(false);
		UnosIzlazaTab.getInstance().getBIzmeni().setDisable(true);

		UnosUlazaTab.getInstance().getTfSifra().requestFocus();
	}
}
