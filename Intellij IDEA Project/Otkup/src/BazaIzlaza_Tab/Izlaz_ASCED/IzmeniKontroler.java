package BazaIzlaza_Tab.Izlaz_ASCED;

import BazaUlaza_Tab.BazaUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Izlaz;
import BazaIzlaza_Tab.BazaIzlazaTab;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		BazaIzlazaTab.getInstance().setIzmenaUToku(true); 
		
		Izlaz i = BazaIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		BazaIzlazaTab.getInstance().getTfSifra().setText(String.valueOf(i.getSifra()));   //prepisujem njegove podatke 
		BazaIzlazaTab.getInstance().getTfNaziv().setText(i.getNaziv());
		BazaIzlazaTab.getInstance().getTfOpis().setText(i.getOpis());
		BazaIzlazaTab.getInstance().getTfCenaPoKom().setText(String.valueOf(i.getCenaPoKomadu()));
		BazaIzlazaTab.getInstance().getCbJedMere().setValue(Firma.getInstance().stringToJedinicaMere(i.getJedinicaMere()));
		
		BazaIzlazaTab.getInstance().SetUnosEnable();     //podesavam prikaz
		BazaIzlazaTab.getInstance().getBObrisi().setDisable(true);
		BazaIzlazaTab.getInstance().getBSacuvaj().setDisable(false);
		BazaIzlazaTab.getInstance().getBIzmeni().setDisable(true);

		BazaIzlazaTab.getInstance().getTfSifra().requestFocus();
		
	}
}