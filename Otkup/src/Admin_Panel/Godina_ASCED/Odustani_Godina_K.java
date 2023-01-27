package Admin_Panel.Godina_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Admin_Panel.AdminPodesavanjaStage;

public class Odustani_Godina_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		AdminPodesavanjaStage.getInstance().setUnosNovog(false);
		AdminPodesavanjaStage.getInstance().setIzmenaUToku(false);
		
		AdminPodesavanjaStage.getInstance().getRedZaUnos().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBDodaj().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBOdustani().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBSacuvaj2().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBIzmeni().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBObrisi().setDisable(true);
		AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().clearSelection();
		AdminPodesavanjaStage.getInstance().ocistiPoljaZaUnos();
		AdminPodesavanjaStage.getInstance().getBDodaj().requestFocus();
	}

}
