package BrzUnosUlaza_tab.Tols_K;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import Main.Print;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosUlaza;

public class ObracunajKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		double ukupnoKg = 0.0;
		double ukupnodin = 0.0;
		
		if(BrzUnosUlazaTab.getInstance().getRBSviObracun().isSelected()) {
			for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {
				ukupnoKg += uu.getKolicinaNeto();
				if(uu.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno())) {
					ukupnodin += (uu.getKolicinaNeto()) * ((Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaSaPrevozom())+uu.getProizvodjac().getCenaPlus());
				}else {
					ukupnodin += (uu.getKolicinaNeto()) * ((Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaBezPrevoza())+uu.getProizvodjac().getCenaPlus());					
				}
			}
			String tekst = "UKUPNO \nkoličina ulaza: "
					+ Print.getInstance().getFormatter().format(ukupnoKg)
					+ "kg\nvrednost ulaza: " + Print.getInstance().getFormatter().format(ukupnodin) +"din" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za sve unose ulaza");
			a.show();
			
			
		}else if(BrzUnosUlazaTab.getInstance().getRBFiltriraniObracun().isSelected()) {
			for(UnosUlaza uu : BrzUnosUlazaTab.getInstance().getTabela().getItems()) {
				ukupnoKg += uu.getKolicinaNeto();
				if(uu.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno())) {
					ukupnodin += (uu.getKolicinaNeto()) * ((Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaSaPrevozom())+uu.getProizvodjac().getCenaPlus());
				}else {
					ukupnodin += (uu.getKolicinaNeto()) * ((Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaBezPrevoza())+uu.getProizvodjac().getCenaPlus());					
				}
			}
			String tekst = "UKUPNO \nkoličina ulaza: "
					+ Print.getInstance().getFormatter().format(ukupnoKg) + "kg\nvrednost ulaza: "
					+ Print.getInstance().getFormatter().format(ukupnodin) +"din" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za filtrirane unose ulaza");
			a.show();
		}
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}
}
