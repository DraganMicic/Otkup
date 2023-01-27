package Admin_Panel.Godina_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Godina;
import Admin_Panel.AdminPodesavanjaStage;

public class Izmeni_Godina_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		AdminPodesavanjaStage.getInstance().setIzmenaUToku(true);
		
		Godina g = AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().getSelectedItem();
		
		AdminPodesavanjaStage.getInstance().getTFGodina().setText(String.valueOf(g.getGodina()));
		AdminPodesavanjaStage.getInstance().getTFLinkGodine().setText(g.getLinkBaze());
		
		AdminPodesavanjaStage.getInstance().getRedZaUnos().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBObrisi().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBSacuvaj2().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBIzmeni().setDisable(true);
	}

}
