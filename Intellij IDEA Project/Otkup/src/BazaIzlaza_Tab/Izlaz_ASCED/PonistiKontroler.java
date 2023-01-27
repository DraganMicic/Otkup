package BazaIzlaza_Tab.Izlaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import BazaIzlaza_Tab.BazaIzlazaTab;

public class PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		BazaIzlazaTab.getInstance().setUnosNovog(false);
		BazaIzlazaTab.getInstance().setIzmenaUToku(false);
		
		BazaIzlazaTab.getInstance().getUnosHB().setDisable(true);    //podesavam prikaz
		BazaIzlazaTab.getInstance().getBDodaj().setDisable(false);
		BazaIzlazaTab.getInstance().getBPonisti().setDisable(true);
		BazaIzlazaTab.getInstance().getBSacuvaj().setDisable(true);
		BazaIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		BazaIzlazaTab.getInstance().getBObrisi().setDisable(true);
		BazaIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();
		BazaIzlazaTab.getInstance().ocistiPoljaZaUnos();
		BazaIzlazaTab.getInstance().getBDodaj().requestFocus();		
	}

}

