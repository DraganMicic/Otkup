package Proizvodjac_Tab.Proizvodjac_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Proizvodjac;
import Proizvodjac_Tab.ProizvodjaciTab;

public class IzmeniKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		ProizvodjaciTab.getInstance().setIzmenaUToku(true); 
		
		Proizvodjac p = ProizvodjaciTab.getInstance().getTabela().getSelectionModel().getSelectedItem();   //ucitavam selektovanog proizvodjaca
		
		ProizvodjaciTab.getInstance().getTfSifra().setText(String.valueOf(p.getSifra()));   //prepisujem njegove podatke 
		ProizvodjaciTab.getInstance().getTfIme().setText(p.getIme());
		ProizvodjaciTab.getInstance().getTfPrezime().setText(p.getPrezime());
		ProizvodjaciTab.getInstance().getTfMesto().setText(p.getMesto());
		ProizvodjaciTab.getInstance().getTfMaticnibroj().setText(p.getMaticniBroj());
		ProizvodjaciTab.getInstance().getTfBrojGazdinstva().setText(p.getBrojGazdinstva());
		ProizvodjaciTab.getInstance().getTfTelefon().setText(p.getTelefon());
		ProizvodjaciTab.getInstance().getTfBrojRacuna().setText(p.getBrojRacuna());
		ProizvodjaciTab.getInstance().getTfKomentar().setText(p.getKomentar());
		ProizvodjaciTab.getInstance().getCbPodrazumevaniPrevoznik().setValue(p.getPrevoznik());
		ProizvodjaciTab.getInstance().getTfCenaPlus().setText(String.valueOf(p.getCenaPlus()));
		
		ProizvodjaciTab.getInstance().SetUnosEnable();    //podesavam prikaz
		ProizvodjaciTab.getInstance().getBObrisi().setDisable(true);
		ProizvodjaciTab.getInstance().getBSacuvaj().setDisable(false);
		ProizvodjaciTab.getInstance().getBIzmeni().setDisable(true);

		ProizvodjaciTab.getInstance().getTfSifra().requestFocus();
		
	}

}
