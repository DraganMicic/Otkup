package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import Gajbe_Tab.GajbeTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class SacuvajKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		
		UnosUlaza selektovani;

		if(BrzUnosUlazaTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			BrzUnosUlazaTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(BrzUnosUlazaTab.getInstance().getDpDatum().getValue() == null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli datum!");
			BrzUnosUlazaTab.getInstance().getDpDatum().requestFocus();
			a.show();
			return;
		}
		if(BrzUnosUlazaTab.getInstance().getCbProizvodjac().getSelectionModel().isEmpty()) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali proizvođača!");
			BrzUnosUlazaTab.getInstance().getCbProizvodjac().requestFocus();
			a.show();
			return;
		}	
		
		if(BrzUnosUlazaTab.getInstance().getCbIUlaz().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu ulaza!");
			BrzUnosUlazaTab.getInstance().getCbIUlaz().requestFocus();
			a.show();
			return;
		}
		
		if(BrzUnosUlazaTab.getInstance().getTfUlazGajbe().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ulaz gajbi!");
			BrzUnosUlazaTab.getInstance().getTfUlazGajbe().requestFocus();
			a.show();
			return;
		}	
		
		if(BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli izlaz gajbi!");
			BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
			a.show();
			return;
		}	
		
		if(BrzUnosUlazaTab.getInstance().getCbGajba().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu gajbe!");
			BrzUnosUlazaTab.getInstance().getCbGajba().requestFocus();
			a.show();
			return;
		}	
		
		if(BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli bruto količinu!");
			BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			a.show();
			return;
		}
		
		if(BrzUnosUlazaTab.getInstance().getCbPrevoznik().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali prevoznika!");
			BrzUnosUlazaTab.getInstance().getCbPrevoznik().requestFocus();
			a.show();
			return;
		}

		int poslednjaSifra = 0;
		for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {   //pravim preporuku sifre
			if(uu.getSifra() > poslednjaSifra)
				poslednjaSifra = uu.getSifra();
		}

		int sifra = Integer.parseInt(BrzUnosUlazaTab.getInstance().getTfSifra().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		LocalDate datum = BrzUnosUlazaTab.getInstance().getDpDatum().getValue();
		Proizvodjac proizvodjac = BrzUnosUlazaTab.getInstance().getCbProizvodjac().getSelectionModel().getSelectedItem();
		Ulaz ulaz = BrzUnosUlazaTab.getInstance().getCbIUlaz().getSelectionModel().getSelectedItem();
		int ulazGajbi;
		int izlazGajbi;
		Gajba gajba = BrzUnosUlazaTab.getInstance().getCbGajba().getSelectionModel().getSelectedItem();
		double bruto = 0;
		double neto = 0;
		Prevoznik prevoznik = BrzUnosUlazaTab.getInstance().getCbPrevoznik().getSelectionModel().getSelectedItem();
		try {
			ulazGajbi = Integer.valueOf(BrzUnosUlazaTab.getInstance().getTfUlazGajbe().getText());
		} catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			BrzUnosUlazaTab.getInstance().getTfUlazGajbe().requestFocus();
			ada.show();
			return;
		}
		try {
			izlazGajbi = Integer.valueOf(BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().getText());
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
			ada.show();
			return;
		}
		try {
			bruto = Double.valueOf(BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().getText());
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			ada.show();
			return;
		}

		double tezinaGajbe = gajba.getTezina();
		neto = bruto-(ulazGajbi*tezinaGajbe);

		ArrayList<Ulaz> ulazi = new ArrayList<Ulaz>();
		double ukupnoKg = 0.0;
		double ukupnoGajbi = 0.0;
		for(UnosUlaza uu : proizvodjac.getUnosiUlaza()) {
			if(!ulazi.contains(uu.getUlaz()))
				ulazi.add(uu.getUlaz());
			if(ulaz.equals(uu.getUlaz())) {
				ukupnoKg += uu.getKolicinaNeto();
				ukupnoGajbi += uu.getGajbe();
			}
		}

		double dosadasnjiProsek = ukupnoKg/ukupnoGajbi;
		double prosek = neto/ulazGajbi;
		
		if(!ulazi.isEmpty() && !ulazi.contains(ulaz)) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Izabrani proizvodjač do sad nije imao ulaz tipa: " + ulaz +". Nastavi?");
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.OK){
			    a.close();
			} else {
				BrzUnosUlazaTab.getInstance().getCbIUlaz().requestFocus();
			    return;
			}
		}
		if(prosek>dosadasnjiProsek+0.3 || prosek < dosadasnjiProsek-0.3) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Neuobičajna prosečna težina u gajbi. \n Dosadasnji prosek: " + dosadasnjiProsek  +"\nTrenutni prosek: " + prosek   );
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.OK){
			    a.close();
			} else {
				BrzUnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			    return;
			}
		}
				
		if(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(ulaz, datum)==null) {
			Alert a = new Alert(AlertType.ERROR, "Nema definisane cene za izabrani dantum.");
			BrzUnosUlazaTab.getInstance().getDpDatum().requestFocus();
			a.show();
			return;
		}
		
		UnosGajbi unosGajbi = null;
		
		if(ulazGajbi != izlazGajbi) {
			int poslednjaSifra2 = 1;
			for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				if(ug.getSifra() > poslednjaSifra2)
					poslednjaSifra2 = ug.getSifra();
			}		
			unosGajbi = new UnosGajbi(poslednjaSifra2+1, datum, proizvodjac, null, gajba, ulazGajbi, izlazGajbi);
		}
		
		UnosUlaza uu = new UnosUlaza(sifra, datum, proizvodjac, ulaz, unosGajbi, gajba, bruto, neto, ulazGajbi, prevoznik);
		
		if(BrzUnosUlazaTab.getInstance().isIzmenaUToku()) {
			
			selektovani = BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			
			for(UnosUlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BrzUnosUlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BrzUnosUlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}
			
			if(unosGajbi != null) {
				String tekst = "";
				if(ulazGajbi > izlazGajbi) 
					tekst = "Razdužiti " + proizvodjac.getIme() + " " + String.valueOf(ulazGajbi-izlazGajbi) + " gajbi?";
				if(izlazGajbi > ulazGajbi)
					tekst = "Zadužiti " + proizvodjac.getIme() + " " + String.valueOf(izlazGajbi - ulazGajbi) + " gajbi?";
				
				Alert a = new Alert(AlertType.CONFIRMATION, tekst);   //alert za potvrdu
				a.setTitle("Unos gajbi");
				Optional<ButtonType> result = a.showAndWait();		
				if (result.get() == ButtonType.CANCEL) {
					BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
					return;
				}
			}
								
			Firma.getInstance().getTrenutnaGodina().izmeniUnosUlaza(selektovani, uu);
		}
		
		if(BrzUnosUlazaTab.getInstance().isUnosNovog()) {
			
			for(UnosUlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BrzUnosUlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BrzUnosUlazaTab.getInstance().getTfSifra().requestFocus();
					a.show();
					return;
				}
			}

			if(unosGajbi != null) {
				String tekst = "";
				if(ulazGajbi > izlazGajbi) 
					tekst = "Razdužiti " + proizvodjac.getIme() + " " + String.valueOf(ulazGajbi-izlazGajbi) + " gajbi?";
				if(izlazGajbi > ulazGajbi)
					tekst = "Zadužiti " + proizvodjac.getIme() + " " + String.valueOf(izlazGajbi - ulazGajbi) + " gajbi?";
				Alert a = new Alert(AlertType.CONFIRMATION, tekst);   //alert za potvrdu
				a.setTitle("Unos gajbi");
				Optional<ButtonType> result = a.showAndWait();		
				if (result.get() == ButtonType.CANCEL){
					BrzUnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
					return;
				}

			}					
			Firma.getInstance().getTrenutnaGodina().dodajUnosUlaza(uu);
		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajUnoseUlaza();
		BrzUnosUlazaTab.getInstance().updateTabele();
		Firma.getInstance().getTrenutnaGodina().sortrirajUnoseGajbi();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();

		BrzUnosUlazaTab.getInstance().setUnosNovog(false);   //updetujem stanja
		BrzUnosUlazaTab.getInstance().setIzmenaUToku(false);

		BrzUnosUlazaTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		BrzUnosUlazaTab.getInstance().SetUnosDisable();
		BrzUnosUlazaTab.getInstance().getBDodaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}


}