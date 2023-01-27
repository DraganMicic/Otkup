package Admin_Panel.Godina_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Godina;
import Admin_Panel.AdminPodesavanjaStage;

public class Sacuvaj_Godina_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Godina selektovana;
		
		if(AdminPodesavanjaStage.getInstance().getTFGodina().getText().equals("")) {  //ispisujem erore ako je polje za sifru prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli godinu!");
			a.show();
			return;
		}
		if(AdminPodesavanjaStage.getInstance().getTFLinkGodine().getText().equals("")) {  //ispisujem eror ako je polje za ime prazno
			Alert a = new Alert(AlertType.ERROR, "Niste uneli link baze!");
			a.show();
			return;
		}
		
		int godina = Integer.parseInt(AdminPodesavanjaStage.getInstance().getTFGodina().getText());
		String linkBaze = AdminPodesavanjaStage.getInstance().getTFLinkGodine().getText();
		
		Godina g = new Godina(godina, linkBaze);
		
		if(AdminPodesavanjaStage.getInstance().isIzmenaUToku()) {
			selektovana = AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().getSelectedItem();
			for(Godina god : Firma.getInstance().getGodine()) {
				int id = Integer.parseInt(AdminPodesavanjaStage.getInstance().getTFGodina().getText());
				if(god.getGodina() == id && god.getGodina() != selektovana.getGodina()) {
					Alert a = new Alert(AlertType.ERROR, "Uneta godina vec postoji!");
					a.show();
					return;			
				}
			}
			
			Firma.getInstance().izmeniGodinu(selektovana, g);
		}
		
		
		if(AdminPodesavanjaStage.getInstance().isUnosNovog()) {
			for(Godina god : Firma.getInstance().getGodine()) {
				int id = Integer.parseInt(AdminPodesavanjaStage.getInstance().getTFGodina().getText());
				if(god.getGodina() == id) {
					Alert a = new Alert(AlertType.ERROR, "Uneta godina vec postoji!");
					a.show();
					return;			
				}
			}				
			Firma.getInstance().dodajGodinu(g);
		}
		
		AdminPodesavanjaStage.getInstance().updatetabele();
		
		AdminPodesavanjaStage.getInstance().setUnosNovog(false);
		AdminPodesavanjaStage.getInstance().setIzmenaUToku(false);
		
		AdminPodesavanjaStage.getInstance().ocistiPoljaZaUnos();
		AdminPodesavanjaStage.getInstance().getRedZaUnos().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBDodaj().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBIzmeni().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBObrisi().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBOdustani().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBSacuvaj2().setDisable(true);
		AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().clearSelection();
		AdminPodesavanjaStage.getInstance().getBDodaj().requestFocus();
				
	}

}
