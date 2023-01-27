package Gajbe_Tab.UnosGajbi_ASCEDP;

import java.util.Optional;

import Gajbe_Tab.Tools_K.PonisitiPretraguUnosaGajbi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosGajbi;
import Gajbe_Tab.GajbeTab;

public class ObrisiUnosGajbiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem unosaGajbe obrisaće se i svi drugi unosi vezani za njega! Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje unosa gajbi");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		UnosGajbi ug = GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().getSelectedItem();
		GajbeTab.getInstance().getTabelaUnosGajbi().getItems().remove(ug);

		Firma.getInstance().getTrenutnaGodina().obrisiUnosGajbi(ug);
		
		GajbeTab.getInstance().getBDodajUnosGajbe().setDisable(false);    //podesavam stanje na prikazu
		GajbeTab.getInstance().getBIzmeniUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBPonistiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBObrisiUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getTabelaUnosGajbi().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getBStampajUnosGajbe().setDisable(true);
		GajbeTab.getInstance().getBDodajUnosGajbe().requestFocus();
		new PonisitiPretraguUnosaGajbi().handle(event);

	}
}