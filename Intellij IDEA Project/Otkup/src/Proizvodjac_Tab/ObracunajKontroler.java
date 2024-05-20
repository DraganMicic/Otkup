package Proizvodjac_Tab;

import Main.Print;
import Proizvodjac_Tab.Proizvodjac_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Proizvodjac;

public class ObracunajKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		double ukupnoKg = 0.0;
		double ukupnoUlaza = 0.0;
		double ukupnoIzlaza = 0.0;
		double saldo = 0.0;
		
		if(ProizvodjaciTab.getInstance().getRBselektovaniObracun().isSelected()) {
			
			if(ProizvodjaciTab.getInstance().getTabela().getSelectionModel().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR, "Proizvodjač nije selektovan");
				a.show();
				return;	
			}
			
			Proizvodjac pro = ProizvodjaciTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
			
			ukupnoUlaza = pro.ukupnoUlaza();
			ukupnoKg = pro.ukupnoKg();		
			ukupnoIzlaza = pro.ukupnoIzlaza();
						
			saldo = ukupnoUlaza-ukupnoIzlaza;			
			String tekst = "UKUPNO: \nkoličina ulaza: " + Print.getInstance().getFormatter().format(ukupnoKg)
					+ "kg\nvrednost ulaza: " + Print.getInstance().getFormatter().format(ukupnoUlaza)
					+"din\nvrednost izlaza: " + Print.getInstance().getFormatter().format(ukupnoIzlaza) +"din\nsaldo: "
					+ Print.getInstance().getFormatter().format(saldo) +"din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za selektovanog proizvođača");
			a.show();
			new PonistiKontroler().handle(event);;
			
			return;	
		}else if(ProizvodjaciTab.getInstance().getRBprikazaniObracun().isSelected()) {			
			for(Proizvodjac p : ProizvodjaciTab.getInstance().getTabela().getItems()) {
				ukupnoUlaza += p.ukupnoUlaza();
				ukupnoKg += p.ukupnoKg();		
				ukupnoIzlaza += p.ukupnoIzlaza();
			}
			saldo = ukupnoUlaza-ukupnoIzlaza;			
			String tekst = "UKUPNO \nkoličina ulaza: " + Print.getInstance().getFormatter().format(ukupnoKg)
					+ "kg\nvrednost ulaza: " + Print.getInstance().getFormatter().format(ukupnoUlaza)
					+"din\nvrednost izlaza: " + Print.getInstance().getFormatter().format(ukupnoIzlaza) +"din\nsaldo: "
					+ Print.getInstance().getFormatter().format(saldo) +"din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za trenutno prikazane(filtrirane) prozivođače");
			a.show();
			return;
		}else if(ProizvodjaciTab.getInstance().getRBsviObracun().isSelected()) {
			for(Proizvodjac p : Firma.getInstance().getTrenutnaGodina().getProizvodjaci()) {
				ukupnoUlaza += p.ukupnoUlaza();
				ukupnoKg += p.ukupnoKg();		
				ukupnoIzlaza += p.ukupnoIzlaza();
			}
			saldo = ukupnoIzlaza-ukupnoUlaza;
			String tekst = "UKUPNO \nkoličina ulaza: " + ukupnoKg + "kg\nvrednost ulaza: " + ukupnoUlaza +"din\nvrednost izlaza: " + ukupnoIzlaza +"din\nsaldo: " + saldo +"din:" ;
			Alert a = new Alert(AlertType.INFORMATION, tekst);
			a.setTitle("Brz obračun");
			a.setHeaderText("Brz obračun za sve prozivođače");
			a.show();
			return;
		}
		
	}
	
}
