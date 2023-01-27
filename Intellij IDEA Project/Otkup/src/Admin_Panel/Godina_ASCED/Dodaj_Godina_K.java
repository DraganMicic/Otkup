package Admin_Panel.Godina_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Godina;
import Admin_Panel.AdminPodesavanjaStage;

public class Dodaj_Godina_K implements EventHandler< ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		AdminPodesavanjaStage.getInstance().setUnosNovog(true);
		
		int poslednjeGodina = 0;
		for(Godina g : Firma.getInstance().getGodine()) {
			if(g.getGodina() > poslednjeGodina)
				poslednjeGodina = g.getGodina();
		}
		
		AdminPodesavanjaStage.getInstance().getTFGodina().setText(String.valueOf(poslednjeGodina+1));
		
		AdminPodesavanjaStage.getInstance().getRedZaUnos().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBDodaj().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBSacuvaj2().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBOdustani().setDisable(false);
		
		AdminPodesavanjaStage.getInstance().getTFGodina().requestFocus();
		
		
		
	}

}
