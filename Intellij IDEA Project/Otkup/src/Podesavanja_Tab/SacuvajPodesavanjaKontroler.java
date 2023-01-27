package Podesavanja_Tab;

import java.sql.SQLException;
import java.util.Optional;

import Main.MainStage;
import Prevoz_Tab.PrevozTab;
import glavnaBaza.SqlitePodesavanja;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Firma;


public class SacuvajPodesavanjaKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		if(PodesavanjaTab.getInstance().getTfIme().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ime!");
			PodesavanjaTab.getInstance().getTfIme().requestFocus();
			a.show();
			return;
		}			
		if(PodesavanjaTab.getInstance().getTfAdresa().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli adresu!");
			PodesavanjaTab.getInstance().getTfAdresa().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTfRacun().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli tekući račun!");
			PodesavanjaTab.getInstance().getTfRacun().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTfMaticniBr().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli matični broj!");
			PodesavanjaTab.getInstance().getTfMaticniBr().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTftelefon1().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli prvi broj telefona!");
			PodesavanjaTab.getInstance().getTftelefon1().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTftelefon2().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli drugi broj telefona!");
			PodesavanjaTab.getInstance().getTftelefon2().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTfDirektor().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli ime i prezime direktora!");
			PodesavanjaTab.getInstance().getTfDirektor().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTfPecat().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli putanju do slike pečata!");
			PodesavanjaTab.getInstance().getTfPecat().requestFocus();
			a.show();
			return;
		}
		if(PodesavanjaTab.getInstance().getTfPecatScale().getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Niste uneli koeficijent skaliranja pečata!");
			PodesavanjaTab.getInstance().getTfPecatScale().requestFocus();
			a.show();
			return;
		}
		
		Alert b = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		b.setTitle("Sačuvaj podešavanja");
		Optional<ButtonType> result = b.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		}

		
		String ime = PodesavanjaTab.getInstance().getTfIme().getText();
		Firma.getInstance().setIme(ime);
		String adresa = PodesavanjaTab.getInstance().getTfAdresa().getText();
		Firma.getInstance().setAdresa(adresa);
		String tekuciracun = PodesavanjaTab.getInstance().getTfRacun().getText();
		Firma.getInstance().setTekuciRacun(tekuciracun);
		String pib = PodesavanjaTab.getInstance().getTfPib().getText();
		Firma.getInstance().setPib(pib);
		String maticniBr = PodesavanjaTab.getInstance().getTfMaticniBr().getText();
		Firma.getInstance().setMaticniBr(maticniBr);
		String tel1 = PodesavanjaTab.getInstance().getTftelefon1().getText();
		Firma.getInstance().setTelefon1(tel1);
		String tel2 = PodesavanjaTab.getInstance().getTftelefon2().getText();
		Firma.getInstance().setTelefon2(tel2);
		String regbr = PodesavanjaTab.getInstance().getTfRegBr().getText();
		Firma.getInstance().setRegBroj(regbr);
		String pzr = PodesavanjaTab.getInstance().getTfpzr().getText();
		Firma.getInstance().setProdavnicaZaRobu(pzr);
		String pzg = PodesavanjaTab.getInstance().getTfpzg().getText();
		Firma.getInstance().setProdavnicaZaGorivo(pzg);
		String direktor = PodesavanjaTab.getInstance().getTfDirektor().getText();
		Firma.getInstance().setDirektor(direktor);
		String pecatLink = PodesavanjaTab.getInstance().getTfPecat().getText();
		Firma.getInstance().setPecatLink(pecatLink);
		Firma.getInstance().setPecat(new Image(pecatLink));
		Color gornja = PodesavanjaTab.getInstance().getCpGornjaBoja().getValue();
		Color donja = PodesavanjaTab.getInstance().getCpDonjaBoja().getValue();
		String bojaGore = toHexString(gornja);
		String bojaDole = toHexString(donja);
		MainStage.getInstance().podesiBoju(bojaDole, bojaGore);
		
		String posprint = PodesavanjaTab.getInstance().getcBPosPrinter().getSelectionModel().getSelectedItem();
		Firma.getInstance().setPosStampacNaziv(posprint);
		Firma.getInstance().podesiPosPrinter();
		
		String a4print = PodesavanjaTab.getInstance().getCbA4Printer().getSelectionModel().getSelectedItem();
		Firma.getInstance().setA4StampacNaziv(a4print);
		Firma.getInstance().podesiA4Printer();

		Double pecatScale;
		try {
			pecatScale = Double.valueOf(PodesavanjaTab.getInstance().getTfPecatScale().getText());
			Firma.getInstance().setPecatScale(pecatScale);
		}catch (Exception e) {
			Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
			PodesavanjaTab.getInstance().getTfPecatScale().requestFocus();
			ada.show();
			return;
		}


		
		try {
			SqlitePodesavanja.GetInstance().obrisiPodesavanja();
			SqlitePodesavanja.GetInstance().upisiPodesavanja(ime, adresa, tekuciracun, pib, maticniBr, tel1, tel2, regbr, pzr, pzg, direktor, pecatLink, bojaDole, bojaGore, posprint, a4print,pecatScale );
		} catch (SQLException er) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni podešavanja!");
			a.show();
			return;
		}
		
		PodesavanjaTab.getInstance().getPodesavanjaVb().setDisable(true);
		PodesavanjaTab.getInstance().getBSacuvaj().setDisable(true);
		PodesavanjaTab.getInstance().getBIzmeni().setDisable(false);
		PodesavanjaTab.getInstance().getBOdustani().setDisable(true);
		
	}
	
	private static String toHexString(Color color) {
		  int r = ((int) Math.round(color.getRed()     * 255)) << 24;
		  int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
		  int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
		  int a = ((int) Math.round(color.getOpacity() * 255));
		  return String.format("#%08X", (r + g + b + a));
		}

}
