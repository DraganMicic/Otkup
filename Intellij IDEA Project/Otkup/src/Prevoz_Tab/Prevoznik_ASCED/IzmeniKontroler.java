package Prevoz_Tab.Prevoznik_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Prevoznik;
import Prevoz_Tab.PrevozTab;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		PrevozTab.getInstance().setIzmenaUToku(true); 
		
		Prevoznik p = PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		PrevozTab.getInstance().getTfSifra().setText(String.valueOf(p.getSifra()));   //prepisujem njegove podatke 
		PrevozTab.getInstance().getTfIme().setText(p.getIme());
		PrevozTab.getInstance().getTfPrezime().setText(p.getPrezime());
		PrevozTab.getInstance().getTfOpis().setText(p.getOpis());
		PrevozTab.getInstance().getTfCenaPoKg().setText(String.valueOf(p.getCenaPoKg()));
		
		PrevozTab.getInstance().SetuUnosEnable();     //podesavam prikaz
		PrevozTab.getInstance().getBObrisi().setDisable(true);
		PrevozTab.getInstance().getBSacuvaj().setDisable(false);
		PrevozTab.getInstance().getBIzmeni().setDisable(true);

		PrevozTab.getInstance().getTfSifra().requestFocus();
		
	}

}
