package Podesavanja_Tab;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OdustaniPodesavanjaKOntroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		PodesavanjaTab.getInstance().getPodesavanjaVb().setDisable(true);
		PodesavanjaTab.getInstance().getBSacuvaj().setDisable(true);
		PodesavanjaTab.getInstance().getBIzmeni().setDisable(false);
		PodesavanjaTab.getInstance().getBOdustani().setDisable(true);
		PodesavanjaTab.getInstance().popuniPolja();		
	}

}
