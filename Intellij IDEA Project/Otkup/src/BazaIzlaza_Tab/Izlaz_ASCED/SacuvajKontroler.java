package BazaIzlaza_Tab.Izlaz_ASCED;

import java.util.Optional;

import StatistikaTab.StatistikaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Izlaz;
import BazaIzlaza_Tab.BazaIzlazaTab;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class SacuvajKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		Izlaz selektovani;		
		
		if(BazaIzlazaTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			BazaIzlazaTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(BazaIzlazaTab.getInstance().getTfNaziv().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ime!");
			BazaIzlazaTab.getInstance().getTfNaziv().requestFocus();
			a.show();
			return;
		}
		
		if(BazaIzlazaTab.getInstance().getCbJedMere().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali jedinicu mere");
			BazaIzlazaTab.getInstance().getCbJedMere().requestFocus();
			a.show();
			return;
		}	
		
		if(BazaIzlazaTab.getInstance().getTfCenaPoKom().getText().equals("") && !(BazaIzlazaTab.getInstance().getCbJedMere().getSelectionModel().getSelectedItem().toString().equals("din"))) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli cenu po komadu!");
			BazaIzlazaTab.getInstance().getTfCenaPoKom().requestFocus();
			a.show();
			return;
		}		
		
		int sifra = Integer.parseInt(BazaIzlazaTab.getInstance().getTfSifra().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		String naziv = BazaIzlazaTab.getInstance().getTfNaziv().getText();
		String opis = BazaIzlazaTab.getInstance().getTfOpis().getText();		
		String jedinicaMere = BazaIzlazaTab.getInstance().getCbJedMere().getSelectionModel().getSelectedItem().toString();
		double cenaPoKomadu;
		
		if(!(BazaIzlazaTab.getInstance().getCbJedMere().getSelectionModel().getSelectedItem().toString().equals("din"))) {
			try {
				cenaPoKomadu = Double.valueOf(BazaIzlazaTab.getInstance().getTfCenaPoKom().getText());
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
				BazaIzlazaTab.getInstance().getTfCenaPoKom().requestFocus();
				a.show();
				return;
			}
				
		}else {		
			cenaPoKomadu = 1.0;
		}	
		Izlaz i = new Izlaz(sifra, naziv, opis, jedinicaMere, cenaPoKomadu);
		
		if(BazaIzlazaTab.getInstance().isIzmenaUToku()) {   	
			selektovani = BazaIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			
			for(Izlaz iz : Firma.getInstance().getTrenutnaGodina().getIzlazi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaIzlazaTab.getInstance().getTfSifra().getText());
				if(iz.getSifra() == id && iz.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji izmena");
					BazaIzlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena izlaza uticaće na izmenu svih unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			} 		
			Firma.getInstance().getTrenutnaGodina().izmeniIzlaz(selektovani, i);
		}
		
		if(BazaIzlazaTab.getInstance().isUnosNovog()) {
			for(Izlaz iz : Firma.getInstance().getTrenutnaGodina().getIzlazi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaIzlazaTab.getInstance().getTfSifra().getText());
				if(iz.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BazaIzlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			Firma.getInstance().getTrenutnaGodina().dodajIzlaz(i);
		}		
		
		Firma.getInstance().getTrenutnaGodina().sortirajIzlaze();		
		BazaIzlazaTab.getInstance().updateTabele();
	
		BazaIzlazaTab.getInstance().setUnosNovog(false);   //updetujem stanja 
		BazaIzlazaTab.getInstance().setIzmenaUToku(false);
			
		BazaIzlazaTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		BazaIzlazaTab.getInstance().SetUnosDisable();
		BazaIzlazaTab.getInstance().getBDodaj().setDisable(false);
		BazaIzlazaTab.getInstance().getBSacuvaj().setDisable(true);
		BazaIzlazaTab.getInstance().getBPonisti().setDisable(true);
		BazaIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		BazaIzlazaTab.getInstance().getBObrisi().setDisable(true);	
		BazaIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BazaIzlazaTab.getInstance().getBDodaj().requestFocus();		
		
		UnosIzlazaTab.getInstance().updateTabele();
		UnosIzlazaTab.getInstance().upodateCBIzlaz();
		StatistikaTab.getInstance().updateCbIzlaz();
	}			
}