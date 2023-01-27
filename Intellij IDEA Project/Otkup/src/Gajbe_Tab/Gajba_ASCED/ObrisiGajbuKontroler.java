package Gajbe_Tab.Gajba_ASCED;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import Gajbe_Tab.GajbeTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class ObrisiGajbuKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem gajbe obrisaće se i svi drugi unosi vezani za nju! Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje vrste gajbe");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Gajba g = GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().getSelectedItem();
		GajbeTab.getInstance().getTabelaGajbe().getItems().remove(g);
		
		Firma.getInstance().getTrenutnaGodina().obrisiGajbu(g);
		
		GajbeTab.getInstance().getBDodajGajbu().setDisable(false);    //podesavam stanje na prikazu
		GajbeTab.getInstance().getBIzmeniGajbu().setDisable(true);
		GajbeTab.getInstance().getBPonistiGajbu().setDisable(true);
		GajbeTab.getInstance().getBObrisiGajbu().setDisable(true);
		GajbeTab.getInstance().getTabelaGajbe().getSelectionModel().clearSelection();
		GajbeTab.getInstance().getBDodajGajbu().requestFocus();
		
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBGajba();
		UnosUlazaTab.getInstance().updateCBGajba();
		UnosUlazaTab.getInstance().updateTabele();
		GajbeTab.getInstance().updateCBGajbaPretraga();
	}
}
