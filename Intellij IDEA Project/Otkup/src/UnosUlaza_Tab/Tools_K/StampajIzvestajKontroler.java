package UnosUlaza_Tab.Tools_K;

import UnosUlaza_Tab.PRINT.PrintPeriodicniIzvestajUnosaUlazaPoDanima;
import UnosUlaza_Tab.UnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import UnosUlaza_Tab.PRINT.PrintDnevniIzvestajUnosUlaza;
import UnosUlaza_Tab.PRINT.PrintPeriodicniIzvestajUnosaUlaza;
import model.Firma;

public class StampajIzvestajKontroler implements EventHandler<ActionEvent>{

	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(UnosUlazaTab.getInstance().getRBDnevniIzvestaj().isSelected()) {
			if(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;	
			}

			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaDatum(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}

			PrintDnevniIzvestajUnosUlaza.getInstance().stampajDnevniIzveštaj(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue());
		}
		
		if(UnosUlazaTab.getInstance().getRBPeriodicniIzvestaj().isSelected()) {
			if(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali početni datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;	
			}
			
			if(UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali krajnji datum.");
				UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().requestFocus();
				a.show();
				return;	
			}
			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaPeriod(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}
			PrintPeriodicniIzvestajUnosaUlaza.getInstance().stampajPeriodicniIzveštaj(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue());
			
		}

		if(UnosUlazaTab.getInstance().getRBPeriodicniPoDanimaIzvestaj().isSelected()) {
			if(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali početni datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}

			if(UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()==null) {
				Alert a = new Alert(AlertType.ERROR, "Niste izabrali krajnji datum.");
				UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().requestFocus();
				a.show();
				return;
			}
			if(Firma.getInstance().getTrenutnaGodina().unosiUlazaZaPeriod(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue()).size() == 0){
				Alert a = new Alert(AlertType.ERROR, "Nema ulaza za izabrani datum.");
				UnosUlazaTab.getInstance().getDPPocetniIzvestraj().requestFocus();
				a.show();
				return;
			}
			PrintPeriodicniIzvestajUnosaUlazaPoDanima.getInstance().stampajPeriodicniIzveštajPoDanima(UnosUlazaTab.getInstance().getDPPocetniIzvestraj().getValue(), UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().getValue());

		}
		
		UnosUlazaTab.getInstance().getDPKrajnjiIzvestaj().setValue(null);
		UnosUlazaTab.getInstance().getDPPocetniIzvestraj().setValue(null);
		UnosUlazaTab.getInstance().getBDodaj().requestFocus();
	}
	
}
