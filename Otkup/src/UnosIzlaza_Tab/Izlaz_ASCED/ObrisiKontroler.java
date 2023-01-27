package UnosIzlaza_Tab.Izlaz_ASCED;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosIzlaza;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class ObrisiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje unosa izlaza");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		UnosIzlaza ui = UnosIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
		UnosIzlazaTab.getInstance().getTabela().getItems().remove(ui);
		
		Firma.getInstance().getTrenutnaGodina().obrisiUnosIzlaza(ui);
		
		UnosIzlazaTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		UnosIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosIzlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosIzlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();
	}


}
