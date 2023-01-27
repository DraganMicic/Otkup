package Proizvodjac_Tab.Proizvodjac_ASCED;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Firma;
import model.Proizvodjac;
import Gajbe_Tab.GajbeTab;
import Proizvodjac_Tab.ProizvodjaciTab;
import UnosIzlaza_Tab.UnosIzlazaTab;
import UnosUlaza_Tab.UnosUlazaTab;

public class ObrisiKOntroler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Alert a = new Alert(AlertType.CONFIRMATION, "Brisanjem proizvođača obrisaće se i svi drugi unosi vezani za njega! Da li ste sigurni?");   //alert za potvrdu
		a.setTitle("Brisanje proizvođača");
		Optional<ButtonType> result = a.showAndWait();		
		if (result.get() == ButtonType.CANCEL){
		    return;
		} 
		
		Proizvodjac p = ProizvodjaciTab.getInstance().getTabela().getSelectionModel().getSelectedItem();  //brisem selektvanog Proizvodjaca iz tabele i iz liste			
		ProizvodjaciTab.getInstance().getTabela().getItems().remove(p);  	
		
		Firma.getInstance().getTrenutnaGodina().obrisiProizvodjaca(p);
		
		ProizvodjaciTab.getInstance().getBDodaj().setDisable(false);    //podesavam stanje na prikazu
		ProizvodjaciTab.getInstance().getBIzmeni().setDisable(true);
		ProizvodjaciTab.getInstance().getBPonisti().setDisable(true);
		ProizvodjaciTab.getInstance().getBObrisi().setDisable(true);
		ProizvodjaciTab.getInstance().getTabela().getSelectionModel().clearSelection();
		ProizvodjaciTab.getInstance().getBDodaj().requestFocus();
		
		UnosIzlazaTab.getInstance().updateCBProizvodjac();
		UnosIzlazaTab.getInstance().updateTabele();
		UnosUlazaTab.getInstance().updateCBProizvodjac();
		UnosUlazaTab.getInstance().updateTabele();
		GajbeTab.getInstance().updateTabeleUnosaGajbi();
		GajbeTab.getInstance().updateCBProizvodjac();
		GajbeTab.getInstance().updateCBProizvodjacPretraga();
		GajbeTab.getInstance().updateCBProizvodjacIzvestaj();
	}

}
