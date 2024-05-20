package UnosIzlaza_Tab.Tools_K;

import Main.Print;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class ObracunajKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		
		double ukupnaKolicina=0.0;
		double ukupnaVrednost=0.0;
		
		if(UnosIzlazaTab.getInstance().getRBSviObracun().isSelected()) {
			for(UnosIzlaza ui : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()) {
				ukupnaVrednost += ui.getKolicina() * ui.getIzlaz().getCenaPoKomadu();
			}
			String tekst = "UKUPNO \nvrednost izlaza: " + Print.getInstance().getFormatter().format( ukupnaVrednost) +"din" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za sve unose izlaza");
			a.show();
		}else if(UnosIzlazaTab.getInstance().getRBFiltriraniObracun().isSelected()) {
			for(UnosIzlaza ui : UnosIzlazaTab.getInstance().getTabela().getItems()) {
				ukupnaVrednost += ui.getKolicina() * ui.getIzlaz().getCenaPoKomadu();
				if(UnosIzlazaTab.getInstance().getCBIzlazPretraga().getSelectionModel().getSelectedItem() != null && !UnosIzlazaTab.getInstance().getCBIzlazPretraga().getSelectionModel().getSelectedItem().getJedinicaMere().equals("din")) {
					ukupnaKolicina += ui.getKolicina();
				}
			}
			if(ukupnaKolicina != 0.0) {
				String tekst = "UKUPNO \nvrednost izlaza: "
						+ Print.getInstance().getFormatter().format(ukupnaVrednost) +"din\nkolicina:"
						+ Print.getInstance().getFormatter().format(ukupnaKolicina) + UnosIzlazaTab.getInstance().getCBIzlazPretraga().getSelectionModel().getSelectedItem().getJedinicaMere() ;
				Alert a = new Alert(AlertType.INFORMATION, tekst);
				a.setTitle("Brz obračun");
				a.setHeaderText("Brz obračun za filtrirane unose izlaza");
				a.show();
			}else {
				String tekst = "UKUPNO \nvrednost izlaza: " + Print.getInstance().getFormatter().format( ukupnaVrednost) +"din" ;
				Alert a = new Alert(AlertType.INFORMATION, tekst);
				a.setTitle("Brz obračun");
				a.setHeaderText("Brz obračun za filtrirane unose izlaza");
				a.show();
			}
		}
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();
	}
	
}
