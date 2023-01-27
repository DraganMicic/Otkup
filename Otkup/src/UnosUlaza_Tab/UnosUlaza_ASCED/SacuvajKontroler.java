package UnosUlaza_Tab.UnosUlaza_ASCED;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import model.Prevoznik;
import model.Proizvodjac;
import model.Ulaz;
import model.UnosGajbi;
import model.UnosUlaza;
import Gajbe_Tab.GajbeTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class SacuvajKontroler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		
		UnosUlaza selektovani;
		
		if(UnosUlazaTab.getInstance().getTfSifra().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			UnosUlazaTab.getInstance().getTfSifra().requestFocus();
			a.show();
			return;
		}
		if(UnosUlazaTab.getInstance().getDpDatum().getValue() == null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli datum!");
			UnosUlazaTab.getInstance().getDpDatum().requestFocus();
			a.show();
			return;
		}
		if(UnosUlazaTab.getInstance().getCbProizvodjac().getSelectionModel().isEmpty()) {   //ispisujem eror ako je polje za prezime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali proizvođača!");
			UnosUlazaTab.getInstance().getCbProizvodjac().requestFocus();
			a.show();
			return;
		}	
		
		if(UnosUlazaTab.getInstance().getCbIUlaz().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu ulaza!");
			UnosUlazaTab.getInstance().getCbIUlaz().requestFocus();
			a.show();
			return;
		}
		
		if(UnosUlazaTab.getInstance().getTfUlazGajbe().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ulaz gajbi!");
			UnosUlazaTab.getInstance().getTfUlazGajbe().requestFocus();
			a.show();
			return;
		}	
		
		if(UnosUlazaTab.getInstance().getTfIzlazGajbe().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli izlaz gajbi!");
			UnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
			a.show();
			return;
		}	
		
		if(UnosUlazaTab.getInstance().getCbGajba().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali vrstu gajbe!");
			UnosUlazaTab.getInstance().getCbGajba().requestFocus();
			a.show();
			return;
		}	
		
		if(UnosUlazaTab.getInstance().getTfKolicinaBruto().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli bruto količinu!");
			UnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			a.show();
			return;
		}
		
		if(UnosUlazaTab.getInstance().getTfKolicnaNeto().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli neto količinu!");
			UnosUlazaTab.getInstance().getTfKolicnaNeto().requestFocus();
			a.show();
			return;
		}		
		
		if(UnosUlazaTab.getInstance().getCbPrevoznik().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali prevoznika!");
			UnosUlazaTab.getInstance().getCbPrevoznik().requestFocus();
			a.show();
			return;
		}	
		
		int sifra = Integer.parseInt(UnosUlazaTab.getInstance().getTfSifra().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		LocalDate datum = UnosUlazaTab.getInstance().getDpDatum().getValue();
		Proizvodjac proizvodjac = UnosUlazaTab.getInstance().getCbProizvodjac().getSelectionModel().getSelectedItem();
		Ulaz ulaz = UnosUlazaTab.getInstance().getCbIUlaz().getSelectionModel().getSelectedItem();
		int ulazGajbi;
		int izlazGajbi;
		Gajba gajba = UnosUlazaTab.getInstance().getCbGajba().getSelectionModel().getSelectedItem();
		double bruto = 0;
		double neto = 0;
		Prevoznik prevoznik = UnosUlazaTab.getInstance().getCbPrevoznik().getSelectionModel().getSelectedItem();
		try {
			ulazGajbi = Integer.valueOf(UnosUlazaTab.getInstance().getTfUlazGajbe().getText());
		} catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosUlazaTab.getInstance().getTfUlazGajbe().requestFocus();
			ada.show();
			return;
		}
		try {
			izlazGajbi = Integer.valueOf(UnosUlazaTab.getInstance().getTfIzlazGajbe().getText());
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
			ada.show();
			return;
		}
		try {
			bruto = Double.valueOf(UnosUlazaTab.getInstance().getTfKolicinaBruto().getText());
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			ada.show();
			return;
		}
		try {
			neto = Double.valueOf(UnosUlazaTab.getInstance().getTfKolicnaNeto().getText());
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			UnosUlazaTab.getInstance().getTfKolicnaNeto().requestFocus();
			ada.show();
			return;
		}
		

		
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
				UnosUlazaTab.getInstance().getCbIUlaz().requestFocus();
			    return;
			}
		}
		if(prosek>dosadasnjiProsek+0.3 || prosek < dosadasnjiProsek-0.3) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Neuobičajna prosečna težina u gajbi. \n Dosadasnji prosek: " + dosadasnjiProsek  +"\nTrenutni prosek: " + prosek   );
			Optional<ButtonType> result = a.showAndWait();
			if (result.get() == ButtonType.OK){
			    a.close();
			} else {
				UnosUlazaTab.getInstance().getTfKolicinaBruto().requestFocus();
			    return;
			}
		}
				
		if(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(ulaz, datum)==null) {
			Alert a = new Alert(AlertType.ERROR, "Nema definisane cene za izabrani dantum.");
			UnosUlazaTab.getInstance().getDpDatum().requestFocus();
			a.show();
			return;
		}
		
		UnosGajbi unosGajbi = null;
		
		if(ulazGajbi != izlazGajbi) {
			int poslednjaSifra = 1;
			for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				if(ug.getSifra() > poslednjaSifra)
					poslednjaSifra = ug.getSifra();
			}		
			unosGajbi = new UnosGajbi(poslednjaSifra+1, datum, proizvodjac, null, gajba, ulazGajbi, izlazGajbi);
		}
		
		UnosUlaza uu = new UnosUlaza(sifra, datum, proizvodjac, ulaz, unosGajbi, gajba, bruto, neto, ulazGajbi, prevoznik);
		
		if(UnosUlazaTab.getInstance().isIzmenaUToku()) {   	
			
			selektovani = UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			
			for(UnosUlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(UnosUlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id && pro.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					UnosUlazaTab.getInstance().getTfSifra().requestFocus();
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
					UnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
					return;
				}
			}
								
			Firma.getInstance().getTrenutnaGodina().izmeniUnosUlaza(selektovani, uu);
		}
		
		if(UnosUlazaTab.getInstance().isUnosNovog()) {
			
			for(UnosUlaza pro : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(UnosUlazaTab.getInstance().getTfSifra().getText());
				if(pro.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					UnosUlazaTab.getInstance().getTfSifra().requestFocus();
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
					UnosUlazaTab.getInstance().getTfIzlazGajbe().requestFocus();
					return;
				}

			}					
			Firma.getInstance().getTrenutnaGodina().dodajUnosUlaza(uu);
		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajUnoseUlaza();
		UnosUlazaTab.getInstance().updateTabele();
		Firma.getInstance().getTrenutnaGodina().sortrirajUnoseGajbi();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
	
		UnosUlazaTab.getInstance().setUnosNovog(false);   //updetujem stanja 
		UnosUlazaTab.getInstance().setIzmenaUToku(false);
			
		UnosUlazaTab.getInstance().ocistiPoljaZaUnos();		   //podesavam prikaz
		UnosUlazaTab.getInstance().getUnosFP().setDisable(true);
		UnosUlazaTab.getInstance().getBDodaj().setDisable(false);
		UnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		UnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(true);
		UnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		UnosUlazaTab.getInstance().getBDodaj().requestFocus();		
	}


}