package BrzUnosUlaza_tab.UnosUlaza_ASCED;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class  PonistiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		BrzUnosUlazaTab.getInstance().setUnosNovog(false);
		BrzUnosUlazaTab.getInstance().setIzmenaUToku(false);

		BrzUnosUlazaTab.getInstance().getBDodaj().setDisable(false);
		BrzUnosUlazaTab.getInstance().getBPonisti().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBIzmeni().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBObrisi().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBStampaj().setDisable(true);
		BrzUnosUlazaTab.getInstance().getBSacuvajiStampaj().setDisable(true);

		BrzUnosUlazaTab.getInstance().ocistiPoljaZaUnos();
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();

		BrzUnosUlazaTab.getInstance().SetUnosDisable();

	}
}
