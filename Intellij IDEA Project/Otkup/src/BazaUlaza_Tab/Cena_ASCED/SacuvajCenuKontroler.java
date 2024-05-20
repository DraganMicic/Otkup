package BazaUlaza_Tab.Cena_ASCED;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CenaUlaza;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;

public class SacuvajCenuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		CenaUlaza selektovani;
		
		if(BazaUlazaTab.getInstance().getTfSifraCene().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli šifru!");
			BazaUlazaTab.getInstance().getTfSifraCene().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getCbUlaz().getSelectionModel().isEmpty()) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali ulaz!");
			BazaUlazaTab.getInstance().getCbUlaz().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getTfCenaBezPrevoza().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli cenu bez prevoza!");
			BazaUlazaTab.getInstance().getTfCenaBezPrevoza().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getTfCenaSaPrevozom().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli cenu sa prevozom!");
			BazaUlazaTab.getInstance().getTfCenaSaPrevozom().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().getValue() == null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali početak važenja cene!");
			BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().requestFocus();
			a.show();
			return;
		}
		if(BazaUlazaTab.getInstance().getDpKrajVazenjaCene().getValue() == null) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste izabrali kraj važenja cene!");
			BazaUlazaTab.getInstance().getDpKrajVazenjaCene().requestFocus();
			a.show();
			return;
		}
		
		double cenaBP = 0.0;
		double cenaSP = 0.0;
		int sifra = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraCene().getText());      //citam vrednosti iz polja za unos i dodajem novog proizvodjaca u listu
		Ulaz u = BazaUlazaTab.getInstance().getCbUlaz().getSelectionModel().getSelectedItem();
		try {
			 cenaBP = Double.valueOf(BazaUlazaTab.getInstance().getTfCenaBezPrevoza().getText());
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			BazaUlazaTab.getInstance().getTfCenaBezPrevoza().requestFocus();
			a.show();
			return;
		}
		try {
			cenaSP = Double.valueOf(BazaUlazaTab.getInstance().getTfCenaSaPrevozom().getText());
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			BazaUlazaTab.getInstance().getTfCenaSaPrevozom().requestFocus();
			a.show();
			return;
		}
		
		LocalDate dPocetak = BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().getValue();
		LocalDate dKraj = BazaUlazaTab.getInstance().getDpKrajVazenjaCene().getValue();

		/*for(CenaUlaza cu : Firma.getInstance().getTrenutnaGodina().getCeneUlaza()) {
			if(cu.getUlaz().equals(u)) {
				if(dPocetak.equals(cu.getKrajVazenja()) || dPocetak.equals(cu.getPocetakVazenja()) || (dPocetak.isAfter(cu.getPocetakVazenja()) && (dPocetak.isBefore(cu.getKrajVazenja()))  ) )  {
					Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
					a.show();
					return;
				}
				if(dKraj.equals(cu.getKrajVazenja()) || dKraj.equals(cu.getPocetakVazenja()) || (dKraj.isAfter(cu.getPocetakVazenja()) && (dKraj.isBefore(cu.getKrajVazenja()))  ) )  {
					Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
					a.show();
					return;
				}
			}
		}*/
		//CenaUlaza cu = new CenaUlaza(sifra, u, cenaBP, cenaSP, dPocetak, dKraj);

		if(BazaUlazaTab.getInstance().isIzmenaCeneUToku()) {
			selektovani = BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().getSelectedItem();
			
			for(CenaUlaza cu : Firma.getInstance().getTrenutnaGodina().getCeneUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraCene().getText());
				if(cu.getSifra() == id && cu.getSifra() !=  selektovani.getSifra()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BazaUlazaTab.getInstance().getTfSifraUlaza().requestFocus();
					a.show();
					return;
				}
				if(cu.getUlaz().equals(u) && !cu.equals(selektovani)) {
					if(dPocetak.equals(cu.getKrajVazenja()) || dPocetak.equals(cu.getPocetakVazenja()) || (dPocetak.isAfter(cu.getPocetakVazenja()) && (dPocetak.isBefore(cu.getKrajVazenja()))  ) )  {
						Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
						BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().requestFocus();
						a.show();
						return;
					}
					if(dKraj.equals(cu.getKrajVazenja()) || dKraj.equals(cu.getPocetakVazenja()) || (dKraj.isAfter(cu.getPocetakVazenja()) && (dKraj.isBefore(cu.getKrajVazenja()))  ) )  {
						Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
						BazaUlazaTab.getInstance().getDpKrajVazenjaCene().requestFocus();
						a.show();
						return;
					}
				}
			}
			CenaUlaza cu = new CenaUlaza(sifra, u, cenaBP, cenaSP, dPocetak, dKraj);
			Firma.getInstance().getTrenutnaGodina().izmeniCenuUlaza(selektovani, cu);			
		}
		
		if(BazaUlazaTab.getInstance().isUnosNoveCene()) {
			for(CenaUlaza cu : Firma.getInstance().getTrenutnaGodina().getCeneUlaza()) {    //proveravam da li sifra vec postoji
				int id = Integer.parseInt(BazaUlazaTab.getInstance().getTfSifraCene().getText());
				if(cu.getSifra() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta sifra vec postoji!");
					BazaUlazaTab.getInstance().getTfSifraCene().requestFocus();
					a.show();
					return;
				}
				if(cu.getUlaz().equals(u)) {
					if(dPocetak.equals(cu.getKrajVazenja()) || dPocetak.equals(cu.getPocetakVazenja()) || (dPocetak.isAfter(cu.getPocetakVazenja()) && (dPocetak.isBefore(cu.getKrajVazenja()))  ) )  {
						Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
						BazaUlazaTab.getInstance().getDpPocetakVazenjaCene().requestFocus();
						a.show();
						return;
					}
					if(dKraj.equals(cu.getKrajVazenja()) || dKraj.equals(cu.getPocetakVazenja()) || (dKraj.isAfter(cu.getPocetakVazenja()) && (dKraj.isBefore(cu.getKrajVazenja()))  ) )  {
						Alert a = new Alert(AlertType.ERROR, "Već postoji definisana cena za izabrani period.");
						BazaUlazaTab.getInstance().getDpKrajVazenjaCene().requestFocus();
						a.show();
						return;
					}
				}
			}
			CenaUlaza cu = new CenaUlaza(sifra, u, cenaBP, cenaSP, dPocetak, dKraj);
			Firma.getInstance().getTrenutnaGodina().dodajCenuUlaza(cu);

		}
		
		Firma.getInstance().getTrenutnaGodina().sortirajCeneUlaza();		
		BazaUlazaTab.getInstance().updateTabeleCena();
	
		BazaUlazaTab.getInstance().setUnosNoveCene(false);   //updetujem stanja 
		BazaUlazaTab.getInstance().setIzmenaCeneUToku(false);
			
		BazaUlazaTab.getInstance().ocistiPoljaZaUnosCena();		   //podesavam prikaz
		BazaUlazaTab.getInstance().SetuUnosCenaDisable();
		BazaUlazaTab.getInstance().getBDodajCenu().setDisable(false);
		BazaUlazaTab.getInstance().getBSacuvajCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBPonistiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBIzmeniCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiCenu().setDisable(true);	
		BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().getBDodajCenu().requestFocus();		
	}			
}