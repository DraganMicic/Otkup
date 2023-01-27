package Gajbe_Tab.UnosGajbi_ASCEDP;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Gajbe_Tab.PRINT.POSPrintUosGajbi;
import Gajbe_Tab.GajbeTab;

public class StampajUnosGajbeKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		POSPrintUosGajbi.getInstance().stampajUnosGajbi(GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().getSelectedItem());
		GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().clearSelection();
		new PonistiUnosGajbiKontroler().handle(event);
	}

}
