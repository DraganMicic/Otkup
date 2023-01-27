package Gajbe_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;

public class ObracunajUnoseGajbiKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		
		int ulaz = 0;
		int izlaz = 0;
		int saldo = 0;
		if(GajbeTab.getInstance().getRBSviUnosiGajbiObracun().isSelected()) {
			for(UnosGajbi ug: Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				ulaz += ug.getGajbeUlaz();
				izlaz += ug.getGajbeIzlaz();
			}
			saldo = ulaz - izlaz;	
			String tekst = "UKUPNO \nzaduženih: " + izlaz + "kom\nrazduženih: " + ulaz +"kom\nSALDO: "+ saldo+"kom" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za sve unose gajbi");
			a.show();
			
		}else if(GajbeTab.getInstance().getRBFiltriraniUnosiGajbiObracun().isSelected()) {
			for(UnosGajbi ug: GajbeTab.getInstance().getTabelaUnosGajbi().getItems()) {
				ulaz += ug.getGajbeUlaz();
				izlaz += ug.getGajbeIzlaz();
			}
			saldo = ulaz - izlaz;
			String tekst = "UKUPNO \nzaduženih: " + izlaz + "kom\nrazduženih: " + ulaz +"kom\nSALDO: "+ saldo+"kom" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za filtrirane unose gajbi");
			a.show();
			
		}
		
	}

}
