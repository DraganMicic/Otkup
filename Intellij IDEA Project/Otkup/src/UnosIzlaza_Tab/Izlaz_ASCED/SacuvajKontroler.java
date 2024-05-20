package UnosIzlaza_Tab.Izlaz_ASCED;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Izlaz;
import model.Proizvodjac;
import model.UnosIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class SacuvajKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		
		UnosIzlaza selektovani;
		
		if(UnosIzlazaTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			UnosIzlazaTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(UnosIzlazaTab.getInstance().getDatePicker().getValue()==null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli datum!");
			UnosIzlazaTab.getInstance().getDpDatum().requestFocus();
			a.show();
			return;
		}
		if(UnosIzlazaTab.getInstance().getCbProizvodjac().getSelectionModel().isEmpty()) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali proizvođača!");
			UnosIzlazaTab.getInstance().getCbProizvodjac().requestFocus();
			a.show();
			return;
		}	
		
		if(UnosIzlazaTab.getInstance().getCbIzlaz().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu izlaza!");
			UnosIzlazaTab.getInstance().getCbIzlaz().requestFocus();
			a.show();
			return;
		}
		
		if(UnosIzlazaTab.getInstance().getTfKolicina().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli količinu");
			UnosIzlazaTab.getInstance().getTfKolicina().requestFocus();
			a.show();
			return;
		}	
		
		int sifra;      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		LocalDate ld = UnosIzlazaTab.getInstance().getDatePicker().getValue();
		Proizvodjac p = UnosIzlazaTab.getInstance().getCbProizvodjac().getSelectionModel().getSelectedItem();
		Izlaz i = UnosIzlazaTab.getInstance().getCbIzlaz().getSelectionModel().getSelectedItem();
		String brOtpremnice = UnosIzlazaTab.getInstance().getTfBrOtpremnice().getText();
		double kolicina ;
		try {
			sifra = Integer.parseInt(UnosIzlazaTab.getInstance().getTfSifra().getText());
		} catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosIzlazaTab.getInstance().getTfSifra().requestFocus();
			ada.show();
			return;
		}
		try {
			kolicina = Double.valueOf(UnosIzlazaTab.getInstance().getTfKolicina().getText());
		}catch (Exception e){
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosIzlazaTab.getInstance().getTfKolicina().requestFocus();
			ada.show();
			return;
		}

		UnosIzlaza ui = new UnosIzlaza(sifra, ld, p, i,brOtpremnice, kolicina );
		
		if(UnosIzlazaTab.getInstance().isIzmenaUToku()) {

			selektovani = UnosIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			
			for(UnosIzlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(UnosIzlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					UnosIzlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			Firma.getInstance().getTrenutnaGodina().izmeniUnosizlaza(selektovani, ui);
			 
		}
		
		if(UnosIzlazaTab.getInstance().isUnosNovog()) {
			for(UnosIzlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(UnosIzlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					UnosIzlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			Firma.getInstance().getTrenutnaGodina().dodajUnosIzlaza(ui);

		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajUnoseIzlaza();
		UnosIzlazaTab.getInstance().updateTabele();
	
		UnosIzlazaTab.getInstance().setUnosNovog(false);   //updetujem stanja 
		UnosIzlazaTab.getInstance().setIzmenaUToku(false);
			
		UnosIzlazaTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		UnosIzlazaTab.getInstance().SetUnosDisable();
		UnosIzlazaTab.getInstance().getBDodaj().setDisable(false);
		UnosIzlazaTab.getInstance().getBSacuvaj().setDisable(true);
		UnosIzlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosIzlazaTab.getInstance().getBObrisi().setDisable(true);	
		UnosIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();		
	}


}
