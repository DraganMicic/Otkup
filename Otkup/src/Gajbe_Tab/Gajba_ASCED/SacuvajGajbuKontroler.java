package Gajbe_Tab.Gajba_ASCED;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import Gajbe_Tab.GajbeTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class SacuvajGajbuKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		
		Gajba selektovani;
		
		if(GajbeTab.getInstance().getTfSifraGajbe().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			GajbeTab.getInstance().getTfSifraGajbe().requestFocus();
			a.show();
			return;
		}
		if(GajbeTab.getInstance().getTfNazivGajbe().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli naziv!");
			GajbeTab.getInstance().getTfNazivGajbe().requestFocus();
			a.show();
			return;
		}
		if(GajbeTab.getInstance().getTfTezinaGajbe().getText().equals("")) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli težinu!");
			GajbeTab.getInstance().getTfTezinaGajbe().requestFocus();
			a.show();
			return;
		}	
		
		if(GajbeTab.getInstance().getTfUkupnoNaRaspolaganjuGajbi().getText().equals("")) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli broj gajbi na raspolaganju!");
			GajbeTab.getInstance().getTfUkupnoNaRaspolaganjuGajbi().requestFocus();
			a.show();
			return;
		}
		
		
		int sifra ;      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		String naziv = GajbeTab.getInstance().getTfNazivGajbe().getText();
		double tezina ;
		int knr ;
		
		try {
			sifra = Integer.parseInt(GajbeTab.getInstance().getTfSifraGajbe().getText());
		} catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			GajbeTab.getInstance().getTfSifraGajbe().requestFocus();
			ada.show();
			return;
		}try {
			tezina = Double.valueOf(GajbeTab.getInstance().getTfTezinaGajbe().getText());
		}catch (Exception e){
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			GajbeTab.getInstance().getTfTezinaGajbe().requestFocus();
			ada.show();
			return;
		}try {
			knr = Integer.parseInt(GajbeTab.getInstance().getTfUkupnoNaRaspolaganjuGajbi().getText());
		}catch (Exception e){
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			GajbeTab.getInstance().getTfUkupnoNaRaspolaganjuGajbi().requestFocus();
			ada.show();
			return;
		}
		
		
		Gajba g = new Gajba(sifra, naziv, tezina, knr);
		
		if(GajbeTab.getInstance().isIzmenaGajbeUToku()) {   	
			selektovani = GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().getSelectedItem();
			
			for(Gajba pro : Firma.getInstance().getTrenutnaGodina().getGajbe()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(GajbeTab.getInstance().getTfSifraGajbe().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					GajbeTab.getInstance().getTfSifraGajbe().requestFocus();
					a.show();
					return;
				}
			}
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena gajbe uticaće na izmenu svih unosa vezanih za nju! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			} 	
			Firma.getInstance().getTrenutnaGodina().izmeniGajbu(selektovani, g);		
		}
		
		if(GajbeTab.getInstance().isUnosNoveGajbe()) {
			for(Gajba pro : Firma.getInstance().getTrenutnaGodina().getGajbe()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(GajbeTab.getInstance().getTfSifraGajbe().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					GajbeTab.getInstance().getTfSifraGajbe().requestFocus();
					a.show();
					return;
				}
			}
			Firma.getInstance().getTrenutnaGodina().dodajGajbu(g);
		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajGajbe();;
		GajbeTab.getInstance().updateTabeleGajbi();
	
		GajbeTab.getInstance().setUnosNoveGajbe(false);   //updetujem stanja 
		GajbeTab.getInstance().setIzmenaGajbeUToku(false);
			
		GajbeTab.getInstance().ocistiPoljaZaUnosGajbe();	   //podesavam prikaz
		GajbeTab.getInstance().getUnosGajbiFP().setDisable(true);
		GajbeTab.getInstance().getBDodajGajbu().setDisable(false);
		GajbeTab.getInstance().getBSacuvajGajbu().setDisable(true);
		GajbeTab.getInstance().getBPonistiGajbu().setDisable(true);
		GajbeTab.getInstance().getBIzmeniGajbu().setDisable(true);
		GajbeTab.getInstance().getBObrisiGajbu().setDisable(true);	
		GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getBDodajGajbu().requestFocus();	
		
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBGajba();
		UnosUlazaTab.getInstance().updateCBGajba();
		UnosUlazaTab.getInstance().updateTabele();
		GajbeTab.getInstance().updateCBGajbaPretraga();
	}


}