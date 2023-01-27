package Proizvodjac_Tab;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Proizvodjac;
import Proizvodjac_Tab.PRINT.PrintSpisakProizvodjacaOP;
import Proizvodjac_Tab.PRINT.PrintSpisakProizvodjacaOPIS;
import Proizvodjac_Tab.PRINT.PrintSpisakProizvodjacaSP;

public class StampaSpiskaKontroler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		
		ArrayList<Proizvodjac> proizvodjaci = new ArrayList<Proizvodjac>();
		
		if(ProizvodjaciTab.getInstance().getRBsviSpisak().isSelected()) {
			proizvodjaci = Firma.getInstance().getTrenutnaGodina().getProizvodjaci();
		}
		
		if(ProizvodjaciTab.getInstance().getRBprikazaniSpisak().isSelected()) {
			for(Proizvodjac p : ProizvodjaciTab.getInstance().getTabela().getItems()) {
				proizvodjaci.add(p);
			}
		}
		
		if(ProizvodjaciTab.getInstance().getRBosnovnipodaci().isSelected()) {
			PrintSpisakProizvodjacaOP.getInstance().StampajSpisakProizvojacaSaOsnovnimPodacima(proizvodjaci);
		}
		
		if(ProizvodjaciTab.getInstance().getRBsvipodaci().isSelected()) {
			PrintSpisakProizvodjacaSP.getInstance().StampajSpisakProizvojacaSaSvimPodacima(proizvodjaci);
		}
		
		if(ProizvodjaciTab.getInstance().getRBosnovnipodaciisaldo().isSelected()) {
			PrintSpisakProizvodjacaOPIS.getInstance().StampajSpisakProizvojacaSaSaldom(proizvodjaci);
		}
	}

}
