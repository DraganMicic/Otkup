package Prevoz_Tab.Prevoznik_ASCED;

import java.util.Optional;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoznik;
import Gajbe_Tab.GajbeTab;
import Prevoz_Tab.PrevozTab;
import Proizvodjac_Tab.ProizvodjaciTab;

public class SacuvajKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Prevoznik selektovani;
		
		if(PrevozTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			PrevozTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(PrevozTab.getInstance().getTfIme().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ime!");
			PrevozTab.getInstance().getTfIme().requestFocus();
			a.show();
			return;
		}
		if(PrevozTab.getInstance().getTfPrezime().getText().equals("")) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli prezime!");
			PrevozTab.getInstance().getTfPrezime().requestFocus();
			a.show();
			return;
		}	
		if(PrevozTab.getInstance().getTfCenaPoKg().getText().equals("")) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli cenu po kilu!");
			PrevozTab.getInstance().getTfCenaPoKg().requestFocus();
			a.show();
			return;
		}	
		
		int sifra ;      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		String ime = PrevozTab.getInstance().getTfIme().getText();
		String prezime = PrevozTab.getInstance().getTfPrezime().getText();
		String opis = PrevozTab.getInstance().getTfOpis().getText();
		double cenaPoKg;
		try {
			sifra = Integer.parseInt(PrevozTab.getInstance().getTfSifra().getText()); 
		} catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			PrevozTab.getInstance().getTfSifra().requestFocus();
			ada.show();
			return;
		}
		try {
			cenaPoKg = Double.valueOf(PrevozTab.getInstance().getTfCenaPoKg().getText());
		}catch (Exception e){
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			PrevozTab.getInstance().getTfCenaPoKg().requestFocus();
			ada.show();
			return;

		}
		Prevoznik p = new Prevoznik(sifra, ime, prezime, opis, cenaPoKg);
				
		if(PrevozTab.getInstance().isIzmenaUToku()) {   	
			selektovani = PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem();
			
			for(Prevoznik pro : Firma.getInstance().getTrenutnaGodina().getPrevoznici()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(PrevozTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					PrevozTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena prevoznika uticaće na izmenu svih unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			} 
			
			Firma.getInstance().getTrenutnaGodina().izmeniPrevoznika(selektovani, p);
		}
		
		if(PrevozTab.getInstance().isUnosNovog()) {
			for(Prevoznik pro : Firma.getInstance().getTrenutnaGodina().getPrevoznici()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(PrevozTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					PrevozTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			Firma.getInstance().getTrenutnaGodina().dodajPrevoznika(p);
	
		}
			
		Firma.getInstance().getTrenutnaGodina().sortirajPrevoznike();	
		PrevozTab.getInstance().updateTabelePrevoznika();
	
		PrevozTab.getInstance().setUnosNovog(false);   //updetujem stanja 
		PrevozTab.getInstance().setIzmenaUToku(false);
			
		PrevozTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		PrevozTab.getInstance().SetuUnosDisable();
		PrevozTab.getInstance().getBDodaj().setDisable(false);
		PrevozTab.getInstance().getBSacuvaj().setDisable(true);
		PrevozTab.getInstance().getBPonisti().setDisable(true);
		PrevozTab.getInstance().getBIzmeni().setDisable(true);
		PrevozTab.getInstance().getBObrisi().setDisable(true);	
		PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().clearSelection();
		PrevozTab.getInstance().getBDodaj().requestFocus();		
		
		ProizvodjaciTab.getInstance().updateTabele();
		ProizvodjaciTab.getInstance().updateCbPodrazumevaniPrevoznik();
		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBPrevoznik();
		BrzUnosUlazaTab.getInstance().updateCBPrevoznikPretraga();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBPrevoznik();
		GajbeTab.getInstance().updateCBPrevoznikPretraga();
		GajbeTab.getInstance().updateCBPrevoznikIzvestaj();
		PrevozTab.getInstance().updateTabelePrevoza();
		PrevozTab.getInstance().updatecbPrevoznikIzvestaj();
	}			
}