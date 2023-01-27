package BazaUlaza_Tab.Ulaz_ASCED;

import java.util.Optional;

import StatistikaTab.StatistikaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.BazaUlazaTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class ObrisiUlazKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem ulaza obrisaće se i svi drugi unosi vezani za njega!Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje ulaza");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Ulaz u = BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().getSelectedItem();
		BazaUlazaTab.getInstance().getTabelaUlaza().getItems().remove(u);
		
		Firma.getInstance().getTrenutnaGodina().obrisiUlaz(u);
		
		BazaUlazaTab.getInstance().getBDodajUlaz().setDisable(false);    //podesavam stanje na prikazu
		BazaUlazaTab.getInstance().getBIzmeniUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBPonistiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().getBDodajUlaz().requestFocus();
		
		UnosUlazaTab.getInstance().updateTabele();
		UnosUlazaTab.getInstance().updateCBUlaz();
		BazaUlazaTab.getInstance().updateTabeleCena();
		BazaUlazaTab.getInstance().updateCbUlaz();
		StatistikaTab.getInstance().updateCbUlaz();
	}

}

