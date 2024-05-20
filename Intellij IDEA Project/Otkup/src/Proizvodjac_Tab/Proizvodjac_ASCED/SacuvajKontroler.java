package Proizvodjac_Tab.Proizvodjac_ASCED;
import java.util.Optional;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoznik;
import model.Proizvodjac;
import Gajbe_Tab.GajbeTab;
import Proizvodjac_Tab.ProizvodjaciTab;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class SacuvajKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
				
		Proizvodjac selektovani;
		
		if(ProizvodjaciTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli sifru!");
			ProizvodjaciTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(ProizvodjaciTab.getInstance().getTfIme().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ime!");
			ProizvodjaciTab.getInstance().getTfIme().requestFocus();
			a.show();
			return;
		}
		if(ProizvodjaciTab.getInstance().getTfPrezime().getText().equals("")) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli prezime!");
			ProizvodjaciTab.getInstance().getTfPrezime().requestFocus();
			a.show();
			return;
		}	 
				
		int sifra = Integer.parseInt(ProizvodjaciTab.getInstance().getTfSifra().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		String ime = ProizvodjaciTab.getInstance().getTfIme().getText();
		String prezime = ProizvodjaciTab.getInstance().getTfPrezime().getText();
		String mesto = ProizvodjaciTab.getInstance().getTfMesto().getText();
		String maticniBroj = ProizvodjaciTab.getInstance().getTfMaticnibroj().getText();
		String brojGazdinstva = ProizvodjaciTab.getInstance().getTfBrojGazdinstva().getText();
		String telefon = ProizvodjaciTab.getInstance().getTfTelefon().getText();
		String brojRacuna = ProizvodjaciTab.getInstance().getTfBrojRacuna().getText();
		String komentar = ProizvodjaciTab.getInstance().getTfKomentar().getText();
		double cenaPlus = 0.0;
		if(!ProizvodjaciTab.getInstance().getTfCenaPlus().getText().isEmpty()) {
			try {
				cenaPlus = Double.valueOf(ProizvodjaciTab.getInstance().getTfCenaPlus().getText());
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
				ProizvodjaciTab.getInstance().getTfCenaPlus().requestFocus();
				a.show();
				return;
			}
			
		}
		
		Prevoznik pr = ProizvodjaciTab.getInstance().getCbPodrazumevaniPrevoznik().getValue();		
		Proizvodjac p = new Proizvodjac(sifra, ime, prezime, mesto, maticniBroj, brojGazdinstva, telefon, brojRacuna, komentar, pr,cenaPlus);  
		
		if(ProizvodjaciTab.getInstance().isIzmenaUToku()) { 		
			selektovani = ProizvodjaciTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			for(Proizvodjac pro : Firma.getInstance().getTrenutnaGodina().getProizvodjaci()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(ProizvodjaciTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id && pro.getSifra() != selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					ProizvodjaciTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena proizvođača uticaće na izmenu svih unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			} 		

			Firma.getInstance().getTrenutnaGodina().izmeniProizvodjaca(selektovani, p);	
		}
		
		if(ProizvodjaciTab.getInstance().isUnosNovog()) {
			for(Proizvodjac pro : Firma.getInstance().getTrenutnaGodina().getProizvodjaci()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(ProizvodjaciTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					ProizvodjaciTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			Firma.getInstance().getTrenutnaGodina().dodajProizviodjaca(p);
		}
		

		Firma.getInstance().getTrenutnaGodina().sortirajProizvodjace();				
		ProizvodjaciTab.getInstance().updateTabele();
	
		ProizvodjaciTab.getInstance().setUnosNovog(false);   //updetujem stanja 
		ProizvodjaciTab.getInstance().setIzmenaUToku(false);
			
		ProizvodjaciTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		ProizvodjaciTab.getInstance().SetUnosDisable();
		ProizvodjaciTab.getInstance().getBDodaj().setDisable(false);
		ProizvodjaciTab.getInstance().getBSacuvaj().setDisable(true);
		ProizvodjaciTab.getInstance().getBPonisti().setDisable(true);
		ProizvodjaciTab.getInstance().getBIzmeni().setDisable(true);
		ProizvodjaciTab.getInstance().getBObrisi().setDisable(true);	
		ProizvodjaciTab.getInstance().getTabela().getSelectionModel().clearSelection();
		ProizvodjaciTab.getInstance().getBDodaj().requestFocus();		
		
		
		BrzUnosUlazaTab.getInstance().updateCBProizvodjac();
		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBProizvodjac();
		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBProizvodjacPretraga();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBProizvodjac();
		GajbeTab.getInstance().updateCBProizvodjacPretraga();
		GajbeTab.getInstance().updateCBProizvodjacIzvestaj();
		ProizvodjaciTab.getInstance().updatecbProizvodjacStampaSvega();
	}			
	

}

