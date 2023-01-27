package Admin_Panel.Godina_ASCED;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Godina;
import Admin_Panel.AdminPodesavanjaStage;

public class Obrisi_Godina_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje godine");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Godina g = AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().getSelectedItem();
		AdminPodesavanjaStage.getInstance().getTabela().getItems().remove(g);
		
		Firma.getInstance().obrisiGodinu(g);
		
		AdminPodesavanjaStage.getInstance().getBDodaj().setDisable(false);
		AdminPodesavanjaStage.getInstance().getBIzmeni().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBOdustani().setDisable(true);
		AdminPodesavanjaStage.getInstance().getBObrisi().setDisable(true);
		AdminPodesavanjaStage.getInstance().getTabela().getSelectionModel().clearSelection();
		AdminPodesavanjaStage.getInstance().getBDodaj().requestFocus();
		
	}

}
