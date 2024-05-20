package Prevoz_Tab.Prevoznik_ASCED;

import java.util.Optional;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoznik;
import Gajbe_Tab.GajbeTab;
import Prevoz_Tab.PrevozTab;
import Proizvodjac_Tab.ProizvodjaciTab;


public class ObrisiKontroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem prevoznika obrisaće se i svi drugi unosi vezani za njega! Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje prevoznika");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Prevoznik p = PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().getSelectedItem();  //brisem selektvanog Proizvodjaca iz tabele i iz liste			
		PrevozTab.getInstance().getTabelaPrevoznika().getItems().remove(p);  		
		Firma.getInstance().getTrenutnaGodina().obrisiPrevoznika(p);
		
		PrevozTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		PrevozTab.getInstance().getBIzmeni().setDisable(true);
		PrevozTab.getInstance().getBPonisti().setDisable(true);
		PrevozTab.getInstance().getBObrisi().setDisable(true);
		PrevozTab.getInstance().getTabelaPrevoznika().getSelectionModel().clearSelection();
		PrevozTab.getInstance().getBDodaj().requestFocus();
		
		ProizvodjaciTab.getInstance().updateTabele();
		ProizvodjaciTab.getInstance().updateCbPodrazumevaniPrevoznik();
		BrzUnosUlazaTab.getInstance().updateTabele();
		BrzUnosUlazaTab.getInstance().updateCBPrevoznik();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBPrevoznik();
		GajbeTab.getInstance().updateCBPrevoznikIzvestaj();
		GajbeTab.getInstance().updateCBPrevoznikPretraga();
		PrevozTab.getInstance().updateTabelePrevoza();
		
	}

}