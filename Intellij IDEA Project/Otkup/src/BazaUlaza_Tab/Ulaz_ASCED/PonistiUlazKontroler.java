package BazaUlaza_Tab.Ulaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import BazaUlaza_Tab.BazaUlazaTab;

public class PonistiUlazKontroler  implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BazaUlazaTab.getInstance().setUnosNovogUlaza(false);
		BazaUlazaTab.getInstance().setIzmenaUlazaUToku(false);
		
		BazaUlazaTab.getInstance().SetUnosUlazaDisable();    //podesavam prikaz
		BazaUlazaTab.getInstance().getBDodajUlaz().setDisable(false);
		BazaUlazaTab.getInstance().getBPonistiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBSacuvajUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBIzmeniUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getBObrisiUlaz().setDisable(true);
		BazaUlazaTab.getInstance().getTabelaUlaza().getSelectionModel().clearSelection();
		BazaUlazaTab.getInstance().ocistiPoljaZaUnosUlaza();
		BazaUlazaTab.getInstance().getBDodajUlaz().requestFocus();		
	}

}