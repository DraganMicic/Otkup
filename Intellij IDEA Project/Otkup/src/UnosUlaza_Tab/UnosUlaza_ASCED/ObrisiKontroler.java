package UnosUlaza_Tab.UnosUlaza_ASCED;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.UnosUlaza;
import Gajbe_Tab.GajbeTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class ObrisiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje unosa ulaza");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 	
		
		UnosUlaza uu = UnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
		Firma.getInstance().getTrenutnaGodina().obrisiUnosUlaza(uu);
		
		UnosUlazaTab.getInstance().getTabela().getItems().remove(uu);		
		GajbeTab.getInstance().updateTabeleUnosaGajbi();

		UnosUlazaTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		UnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		UnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		UnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}


}