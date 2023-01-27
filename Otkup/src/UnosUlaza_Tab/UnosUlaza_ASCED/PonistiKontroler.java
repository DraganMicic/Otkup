package UnosUlaza_Tab.UnosUlaza_ASCED;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosUlaza_Tab.UnosUlazaTab;

public class PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		UnosUlazaTab.getInstance().setUnosNovog(false);
		UnosUlazaTab.getInstance().setIzmenaUToku(false);
		
		UnosUlazaTab.getInstance().getUnosFP().setDisable(true);    //podesavam prikaz
		UnosUlazaTab.getInstance().getBDodaj().setDisable(false);
		UnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		UnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		UnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		UnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		UnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		UnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(true);
		
		UnosUlazaTab.getInstance().ocistiPoljaZaUnos();
		UnosUlazaTab.getInstance().getBDodaj().requestFocus();		
	}
}
