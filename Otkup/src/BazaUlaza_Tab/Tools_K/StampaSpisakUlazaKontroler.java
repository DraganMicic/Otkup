package BazaUlaza_Tab.Tools_K;

import java.util.ArrayList;

import BazaUlaza_Tab.BazaUlazaTab;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import model.Firma;
import model.Ulaz;
import BazaUlaza_Tab.PRINT.PrintSpisakUlaza;

public class StampaSpisakUlazaKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		ArrayList<Ulaz> zaStampu = new ArrayList<Ulaz>();
				
		if(BazaUlazaTab.getInstance().getRBSviUlaziSpisak().isSelected()) {
			zaStampu.addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
			PrintSpisakUlaza.getInstance().StampajSpisakUlaza(zaStampu);
		}
		
		if(BazaUlazaTab.getInstance().getRBFiltriraniUlaziSpisak().isSelected()) {
			zaStampu.addAll(BazaUlazaTab.getInstance().getTabelaUlaza().getItems());
			PrintSpisakUlaza.getInstance().StampajSpisakUlaza(zaStampu);
		}
		
	}

}
