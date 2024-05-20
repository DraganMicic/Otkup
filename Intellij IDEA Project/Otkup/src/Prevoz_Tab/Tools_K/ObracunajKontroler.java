package Prevoz_Tab.Tools_K;

import Main.Print;
import Prevoz_Tab.Prevoznik_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoz;
import model.Prevoznik;
import Prevoz_Tab.PrevozTab;

public class ObracunajKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		
		double ukupnaKolicina = 0.0;
		double ukupnaVrednost = 0.0;
		
		if(PrevozTab.getInstance().getRBSviObracun().isSelected()) {
			for(Prevoz p : Firma.getInstance().getTrenutnaGodina().getPrevozi()) {
				ukupnaKolicina += p.getKolicna();
				ukupnaVrednost += p.getKolicna()*p.getPrevoznik().getCenaPoKg();
			}
			String tekst = "UKUPNO \nprevežena kolicina: "
					+ Print.getInstance().getFormatter().format( ukupnaKolicina) + "kg\nvrednost prevoza: "
					+ Print.getInstance().getFormatter().format(ukupnaVrednost) + "din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za selektovanog prevozmika");
			a.show();	
			
			
		}else if(PrevozTab.getInstance().getRBFiltriraniObracun().isSelected()) {
			for(Prevoz p : PrevozTab.getInstance().getTabelaPrevoza().getItems()) {
				ukupnaKolicina += p.getKolicna();
				ukupnaVrednost += p.getKolicna()*p.getPrevoznik().getCenaPoKg();
			}
			String tekst = "UKUPNO \nprevežena kolicina: "
					+ Print.getInstance().getFormatter().format(ukupnaKolicina) + "kg\nvrednost prevoza: "
					+ Print.getInstance().getFormatter().format(ukupnaVrednost) + "din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za selektovanog prevozmika");
			a.show();			
			
			
		}else if(PrevozTab.getInstance().getRBSelektovaniObracun().isSelected()) {
			if(PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR, "Prevoznik nije selektovan");
				a.show();
				return;	
			}
			Prevoznik pre = PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem();
			ukupnaKolicina = Firma.getInstance().getTrenutnaGodina().ukupnaKolicinaZaPrevoznika(pre);
			ukupnaVrednost = ukupnaKolicina * pre.getCenaPoKg();
			String tekst = "UKUPNO \nprevežena kolicina: "
					+ Print.getInstance().getFormatter().format(ukupnaKolicina) + "kg\nvrednost prevoza: "
					+ Print.getInstance().getFormatter().format(ukupnaVrednost) + "din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za selektovanog prevozmika");
			a.show();			
			new PonistiKontroler().handle(event);
			return;			
		}
		
	}

}
