package Gajbe_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;

public class ObracunajGajbeKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		int ukupnoZaduzenih=0;
		int ukupnoRazduzenih=0;
		
		if(GajbeTab.getInstance().getRBSveGajbeObracun().isSelected()) {
			for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				ukupnoZaduzenih += ug.getGajbeIzlaz();
				ukupnoRazduzenih += ug.getGajbeUlaz();
			}
			int naTerenu = ukupnoZaduzenih-ukupnoRazduzenih;
			int ukupnoNaStanju =0;
			
			for(Gajba g: Firma.getInstance().getTrenutnaGodina().getGajbe()) {
				ukupnoNaStanju += g.getUkupnoNaRaspolaganju();
			}
			int naStanju = ukupnoNaStanju-naTerenu;
			String tekst = "UKUPNO \nna terenu: " + naTerenu + "kom\nna stanju: " + naStanju +"kom" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za sve gajbe");
			a.show();
			
			
		}else if(GajbeTab.getInstance().getRBFiltriraneGajbeObrscun().isSelected()) {
			int ukupnoNaStanju =0;			
			for(Gajba g: GajbeTab.getInstance().getTabelaGajbe().getItems()) {
				ukupnoNaStanju += g.getUkupnoNaRaspolaganju();
				for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi() ) {
					if(ug.getGajba().equals(g)) {
						ukupnoZaduzenih += ug.getGajbeIzlaz();
						ukupnoRazduzenih += ug.getGajbeUlaz();
					}
				}
			}
			int naTerenu = ukupnoZaduzenih-ukupnoRazduzenih;
			int naStanju = ukupnoNaStanju-naTerenu;
			String tekst = "UKUPNO \naa terenu: " + naTerenu + "kom\nna stanju: " + naStanju +"kom" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za filtrirane gajbe");
			a.show();
			
		}
	}

}
