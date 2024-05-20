package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import Gajbe_Tab.GajbeTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Firma;
import model.UnosUlaza;

import java.util.Optional;

public class ObrisiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje unosa ulaza");
		Optional<ButtonType> result = a.showAndWait();

		if (result.get() == ButtonType.CANCEL)
		    return;

		
		UnosUlaza uu = BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
		Firma.getInstance().getTrenutnaGodina().obrisiUnosUlaza(uu);

		BrzUnosUlazaTab.getInstance().getTabela().getItems().remove(uu);
		GajbeTab.getInstance().updateTabeleUnosaGajbi();

		BrzUnosUlazaTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		BrzUnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}


}