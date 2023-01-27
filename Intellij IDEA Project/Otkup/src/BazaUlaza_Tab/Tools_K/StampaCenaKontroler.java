package BazaUlaza_Tab.Tools_K;


import java.util.ArrayList;

import BazaUlaza_Tab.BazaUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CenaUlaza;
import model.Firma;
import BazaUlaza_Tab.PRINT.PrintCenovnik;

public class StampaCenaKontroler implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {

		
		ArrayList<CenaUlaza> cene = new ArrayList<CenaUlaza>();
		
		
		
		if(BazaUlazaTab.getInstance().getRBSveCene().isSelected()) {
			cene = Firma.getInstance().getTrenutnaGodina().getCeneUlaza();
		}
		
		if(BazaUlazaTab.getInstance().getRBFiltriraneCene().isSelected()) {			
			for(CenaUlaza cu: BazaUlazaTab.getInstance().getTabelaCena().getItems()) {
				cene.add(cu);				
			}
		}
				
		if(cene.isEmpty()) {	
			Alert a = new Alert(AlertType.ERROR, "Nema cena za štampu.");
			a.show();
			
		}
		
		PrintCenovnik.getInstance().stampajCenovnik(cene);
		//BazaUlazaTab.getInstance().getDpStampaCenovnika().setValue(null);
		
		BazaUlazaTab.getInstance().getBDodajCenu().requestFocus();  
		
	}
	
}
