package BazaIzlaza_Tab.Izlaz_ASCED;

import java.util.Optional;

import StatistikaTab.StatistikaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Izlaz;
import BazaIzlaza_Tab.BazaIzlazaTab;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class ObrisiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem izlaza obrisaće se i svi drugi unosa vezanih za njega! Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje izlaza");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Izlaz i = BazaIzlazaTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
		BazaIzlazaTab.getInstance().getTabela().getItems().remove(i);
	
		Firma.getInstance().getTrenutnaGodina().obrisiIzlaz(i);
		
		BazaIzlazaTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		BazaIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		BazaIzlazaTab.getInstance().getBPonisti().setDisable(true);
		BazaIzlazaTab.getInstance().getBObrisi().setDisable(true);
		BazaIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BazaIzlazaTab.getInstance().getBDodaj().requestFocus();
		
		UnosIzlazaTab.getInstance().updateTabele();
		UnosIzlazaTab.getInstance().upodateCBIzlaz();
		StatistikaTab.getInstance().updateCbIzlaz();
	}

}
