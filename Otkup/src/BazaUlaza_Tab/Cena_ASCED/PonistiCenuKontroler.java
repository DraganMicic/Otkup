package BazaUlaza_Tab.Cena_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import BazaUlaza_Tab.BazaUlazaTab;

public class PonistiCenuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setUnosNoveCene(false);
		BazaUlazaTab.getInstance().setIzmenaCeneUToku(false);
		
		BazaUlazaTab.getInstance().getUnosiCena1HB().setDisable(true);    //podesavam prikaz
		BazaUlazaTab.getInstance().getUnosiCena2HB().setDisable(true);
		BazaUlazaTab.getInstance().getBDodajCenu().setDisable(false);
		BazaUlazaTab.getInstance().getBPonistiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBSacuvajCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBIzmeniCenu().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiCenu().setDisable(true);
		BazaUlazaTab.getInstance().getTabelaCena().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().ocistiPoljaZaUnosCena();
		BazaUlazaTab.getInstance().getBDodajCenu().requestFocus();		
	}

}