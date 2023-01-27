package Gajbe_Tab.UnosGajbi_ASCEDP;

import java.time.LocalDate;
import java.util.Optional;

import Gajbe_Tab.PRINT.POSPrintUosGajbi;
import Gajbe_Tab.Tools_K.PonisitiPretraguUnosaGajbi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import model.Prevoznik;
import model.Proizvodjac;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class SacuvajiStampajUnsoGajbiKOntroler implements EventHandler<ActionEvent> {

public void handle(ActionEvent event) {
		
		UnosGajbi selektovani;
		
		if(GajbeTab.getInstance().getTfSifraUnosaGajbi().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			GajbeTab.getInstance().getTfSifraUnosaGajbi().requestFocus();
			a.show();
			return;
		}
		if(GajbeTab.getInstance().getDpDatumUnosaGajbi().getValue() == null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali datum!");
			GajbeTab.getInstance().getDpDatumUnosaGajbi().requestFocus();
			a.show();
			return;
		}
		if(GajbeTab.getInstance().getCbPrevoznik().getSelectionModel().isEmpty() && GajbeTab.getInstance().getCbProizvodjac().getSelectionModel().isEmpty()) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Morate izabrati proizvođača ili prevoznika!");
			GajbeTab.getInstance().getCbProizvodjac().requestFocus();
			a.show();
			return;
		}		
		if(GajbeTab.getInstance().getCbGajba().getSelectionModel().isEmpty()) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu gajbe!");
			GajbeTab.getInstance().getCbGajba().requestFocus();
			a.show();
			return;
		}
		if(GajbeTab.getInstance().getTfUlazGajbi().getText().equals("") && GajbeTab.getInstance().getTfIzlazGajbi().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Morate uneti ulaz ili izlaz gajbi!");
			GajbeTab.getInstance().getTfUlazGajbi().requestFocus();
			a.show();
			return;
		}		
		
		int sifra = Integer.parseInt(GajbeTab.getInstance().getTfSifraUnosaGajbi().getText());
		LocalDate ld = GajbeTab.getInstance().getDpDatumUnosaGajbi().getValue();
		Proizvodjac proizvodjac = GajbeTab.getInstance().getCbProizvodjac().getSelectionModel().getSelectedItem();
		Prevoznik prevoznik = GajbeTab.getInstance().getCbPrevoznik().getSelectionModel().getSelectedItem();
		Gajba gajba = GajbeTab.getInstance().getCbGajba().getSelectionModel().getSelectedItem();
		
		int gajbeUlaz;
		int gajbeIzlaz;
		if(GajbeTab.getInstance().getTfUlazGajbi().getText().equals("")) {
			gajbeUlaz = 0;
		}else {
			try {
				gajbeUlaz = Integer.parseInt(GajbeTab.getInstance().getTfUlazGajbi().getText());  /////////////////////////////
			}catch (Exception e){
				Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
				GajbeTab.getInstance().getTfUlazGajbi().requestFocus();
				ada.show();
				return;
			}
		}
		if(GajbeTab.getInstance().getTfIzlazGajbi().getText().equals("")){
			gajbeIzlaz =0;
		}else {
			try {
				gajbeIzlaz = Integer.parseInt(GajbeTab.getInstance().getTfIzlazGajbi().getText());  /////////////
			}catch (Exception e){
				Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
				GajbeTab.getInstance().getTfIzlazGajbi().requestFocus();
				ada.show();
				return;
			}

		}
				
		UnosGajbi ug = new UnosGajbi(sifra, ld, proizvodjac, prevoznik, gajba, gajbeUlaz, gajbeIzlaz);
		
		if(GajbeTab.getInstance().isIzmenaUnosaGajbi()) {   	
			selektovani = GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().getSelectedItem();
			
			for(UnosGajbi pro : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(GajbeTab.getInstance().getTfSifraUnosaGajbi().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					GajbeTab.getInstance().getTfSifraUnosaGajbi().requestFocus();
					a.show();
					return;
				}
			}
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena unosa Gajbe uticaće na izmenu svih unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			} 		

			Firma.getInstance().getTrenutnaGodina().izmeniUnosGajbi(selektovani, ug);
			POSPrintUosGajbi.getInstance().stampajUnosGajbi(ug);
		}
		
		if(GajbeTab.getInstance().isUnosUnosaGajbi()) {
			for(UnosGajbi pro : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(GajbeTab.getInstance().getTfSifraUnosaGajbi().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					GajbeTab.getInstance().getTfSifraUnosaGajbi().requestFocus();
					a.show();
					return;
				}
			}
			Firma.getInstance().getTrenutnaGodina().dodajUnosGajbi(ug);
			POSPrintUosGajbi.getInstance().stampajUnosGajbi(ug);
		}



		Firma.getInstance().getTrenutnaGodina().sortrirajUnoseGajbi();;;
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
	
		GajbeTab.getInstance().setUnosUnosaGajbi(false);   //updetujem stanja 
		GajbeTab.getInstance().setIzmenaUnosaGajbi(false);
			
		GajbeTab.getInstance().ocitiPoljaZaUnosUnosaGajbi();   //podesavam prikaz
		GajbeTab.getInstance().getUnosUnosaGajbiFP().setDisable(true);
		GajbeTab.getInstance().getBDodajUnosGajbe().setDisable(false);
		GajbeTab.getInstance().getBSacuvajUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBPonistiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBIzmeniUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBObrisiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBSacuvajUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBSacuvajIStampajUnsoGajbe().setDisable(true);
		GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getBDodajUnosGajbe().requestFocus();		
		new PonisitiPretraguUnosaGajbi().handle(event);
		
		UnosUlazaTab.getInstance().updateTabele();
	}
}
