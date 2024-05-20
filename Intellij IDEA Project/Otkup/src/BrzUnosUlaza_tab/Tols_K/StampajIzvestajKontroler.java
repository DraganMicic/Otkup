package BrzUnosUlaza_tab.Tols_K;

import BrzUnosUlaza_tab.PRINT.PrintDnevniIzvestajUnosUlaza;
import BrzUnosUlaza_tab.PRINT.PrintPeriodicniIzvestajUnosaUlaza;
import BrzUnosUlaza_tab.PRINT.PrintPeriodicniIzvestajUnosaUlazaPoDanima;
import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;

public class StampajIzvestajKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(BrzUnosUlazaTab.getInstance().getRBDnevniIzvestaj().isSelected()) {
			if(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;	
			}

			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaDatum(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}

			PrintDnevniIzvestajUnosUlaza.getInstance().stampajDnevniIzveštaj(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue());
		}
		
		if(BrzUnosUlazaTab.getInstance().getRBPeriodicniIzvestaj().isSelected()) {
			if(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali početni datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;	
			}
			
			if(BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali krajnji datum.");
				BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().requestFocus();
				a.show();
				return;	
			}
			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaPeriod(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}
			PrintPeriodicniIzvestajUnosaUlaza.getInstance().stampajPeriodicniIzveštaj(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue());
			
		}

		if(BrzUnosUlazaTab.getInstance().getRBPeriodicniPoDanimaIzvestaj().isSelected()) {
			if(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali početni datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}

			if(BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali krajnji datum.");
				BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().requestFocus();
				a.show();
				return;
			}
			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaPeriod(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}
			PrintPeriodicniIzvestajUnosaUlazaPoDanima.getInstance().stampajPeriodicniIzveštajPoDanima(BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue());

		}

		BrzUnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().setValue(null);
		BrzUnosUlazaTab.getInstance().getDPPocetniIzvestraj().setValue(null);
		BrzUnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}
	
}
