package UnosIzlaza_Tab.Izlaz_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		UnosIzlazaTab.getInstance().setUnosNovog(false);
		UnosIzlazaTab.getInstance().setIzmenaUToku(false);
		
		UnosIzlazaTab.getInstance().SetUnosDisable();    //podesavam prikaz
		UnosIzlazaTab.getInstance().getBDodaj().setDisable(false);
		UnosIzlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosIzlazaTab.getInstance().getBSacuvaj().setDisable(true);
		UnosIzlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosIzlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosIzlazaTab.getInstance().getTabela().getSelectionModel().clearSelection();		
		UnosIzlazaTab.getInstance().ocistiPoljaZaUnos();
		UnosIzlazaTab.getInstance().getBDodaj().requestFocus();		
	}
}
