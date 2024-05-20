package BazaUlaza_Tab.Ulaz_ASCED;


import java.util.Optional;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import StatistikaTab.StatistikaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;

public class SacuvajUlazKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Ulaz selektovani;
		
		if(BazaUlazaTab.getInstance().getTfSifraUlaza().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			BazaUlazaTab.getInstance().getTfSifraUlaza().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getTfNazivUlaza().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli naziv!");
			BazaUlazaTab.getInstance().getTfNazivUlaza().requestFocus();
			a.show();
			return;
		}
		
		int sifra = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraUlaza().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		String naziv = BazaUlazaTab.getInstance().getTfNazivUlaza().getText();
		String opis = BazaUlazaTab.getInstance().getTfOpisUlaza().getText();	
		
		Ulaz u = new Ulaz(sifra, naziv, opis);

		if(BazaUlazaTab.getInstance().isIzmenaUlazaUToku()) {   	
			selektovani = BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().getSelectedItem();
			
			for(Ulaz pro : Firma.getInstance().getTrenutnaGodina().getUlazi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraUlaza().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BazaUlazaTab.getInstance().getTfSifraUlaza().requestFocus();
					a.show();
					return;
				}
			}	
			Alert omg = new Alert(AlertType.CONFIRMATION, "Izmena ulaza uticaće na izmenu svih unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
			omg.setTitle("Izmena proizvođača");
			Optional<ButtonType> result = omg.showAndWait();		
			if (result.get() == ButtonType.CANCEL){
			    return;
			}
			Firma.getInstance().getTrenutnaGodina().izmeniUlaz(selektovani, u);			
		}
		
		if(BazaUlazaTab.getInstance().isUnosNovogUlaza()) {
			for(Ulaz pro : Firma.getInstance().getTrenutnaGodina().getUlazi()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraUlaza().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BazaUlazaTab.getInstance().getTfSifraUlaza().requestFocus();
					a.show();
					return;
				}
			}			
			Firma.getInstance().getTrenutnaGodina().dodajUlaz(u);
		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajUlaze();		
		BazaUlazaTab.getInstance().updateTabeleUlaza();
	
		BazaUlazaTab.getInstance().setUnosNovogUlaza(false);   //updetujem stanja 
		BazaUlazaTab.getInstance().setIzmenaUlazaUToku(false);
			
		BazaUlazaTab.getInstance().ocistiPoljaZaUnosUlaza();		   //podesavam prikaz
		BazaUlazaTab.getInstance().SetUnosUlazaDisable();
		BazaUlazaTab.getInstance().getBDodajUlaz().setDisable(false);
		BazaUlazaTab.getInstance().getBSacuvajUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBPonistiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBIzmeniUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiUlaz().setDisable(true);	
		BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().getBDodajUlaz().requestFocus();

		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBUlaz();
		BrzUnosUlazaTab.getInstance().updateCBUlazPretraga();
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().updateCbUlaz();
		StatistikaTab.getInstance().updateCbUlaz();
	}			
}