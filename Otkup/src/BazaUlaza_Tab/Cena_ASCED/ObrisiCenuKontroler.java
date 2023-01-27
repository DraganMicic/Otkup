package BazaUlaza_Tab.Cena_ASCED;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.CenaUlaza;
import model.Firma;
import BazaUlaza_Tab.BazaUlazaTab;

public class ObrisiCenuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje cene ulaza");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		CenaUlaza cu = BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().getSelectedItem();
		BazaUlazaTab.getInstance().getTabelaCena().getItems().remove(cu);

		Firma.getInstance().getTrenutnaGodina().obrisiCenuUlaza(cu);
		
		BazaUlazaTab.getInstance().getBDodajCenu().setDisable(false);    //podesavam stanje na prikazu
		BazaUlazaTab.getInstance().getBIzmeniCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBPonistiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().getBDodajCenu().requestFocus();
	}

}