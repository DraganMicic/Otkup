package Proizvodjac_Tab;

import Proizvodjac_Tab.Proizvodjac_ASCED.PonistiKontroler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Proizvodjac;
import Proizvodjac_Tab.PRINT.PrintIzveštajProizvodjaca;
import Proizvodjac_Tab.PRINT.PrintOdobrenjeZaGorivo;
import Proizvodjac_Tab.PRINT.PrintOdobrenjeZaPreparate;
import Proizvodjac_Tab.PRINT.PrintUgovor;

public class StampaIzvestaja_K implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		if(ProizvodjaciTab.getInstance().getTabela().getSelectionModel().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Proizvodjač nije selektovan");
			a.show();
			return;	
		}
		Proizvodjac p = ProizvodjaciTab.getInstance().getTabela().getSelectionModel().getSelectedItem();
		
		
		if(ProizvodjaciTab.getInstance().getRBizvestajProizvodjaca().isSelected())
			PrintIzveštajProizvodjaca.getInstance().stampajIzvestajProizvodjaca(p);
		
		if(ProizvodjaciTab.getInstance().getRBodobrenjePreparati().isSelected())
			PrintOdobrenjeZaPreparate.getInstance().stampajOdobrenjeZaPreprate(p);
		
		if(ProizvodjaciTab.getInstance().getRBodobrenjeGorivo().isSelected())
			PrintOdobrenjeZaGorivo.getInstance().stampajOdobrenjeZaGorivo(p);
		
		if(ProizvodjaciTab.getInstance().getRBugovor().isSelected())
			PrintUgovor.getInstance().stampajUgovor(p);

		PonistiKontroler po = new PonistiKontroler();
		po.handle(event);
		
		
	}

}
