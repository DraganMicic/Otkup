package Prevoz_Tab.Tools_K;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Prevoznik;
import Prevoz_Tab.PRINT.PrintOsnovniSpisakPrevoznika;
import Prevoz_Tab.PRINT.PrintSpisakPrevoznikaSaKolicinama;
import Prevoz_Tab.PrevozTab;

public class StampajSpisakPrevoznikaKOntroler implements EventHandler< ActionEvent> {

	public void handle(ActionEvent event) {
		
		if(PrevozTab.getInstance().getRBsviPrevozniciSpisak().isSelected()) {
			
			if(PrevozTab.getInstance().getRBSamoImenaSpisak().isSelected()) {
				 PrintOsnovniSpisakPrevoznika.getInstance().StampajOsnovniSpisakPrevoznika(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
			}else if(PrevozTab.getInstance().getRBPodaciSaSaldomSpisak().isSelected()) {
				 PrintSpisakPrevoznikaSaKolicinama.getInstance().StampajSpisakPrevoznikaSaKolicinama(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
			}			
		}
		
		if(PrevozTab.getInstance().getRBFiltrriraniPrevozniciSpisak().isSelected()) {
			ArrayList<Prevoznik> zaStampu = new ArrayList<Prevoznik>();
			zaStampu.addAll(PrevozTab.getInstance().getTabelaPrevoznika().getItems());
			
			if(PrevozTab.getInstance().getRBSamoImenaSpisak().isSelected()) {
				PrintOsnovniSpisakPrevoznika.getInstance().StampajOsnovniSpisakPrevoznika(zaStampu);
			}else if(PrevozTab.getInstance().getRBPodaciSaSaldomSpisak().isSelected()) {
				PrintSpisakPrevoznikaSaKolicinama.getInstance().StampajSpisakPrevoznikaSaKolicinama(zaStampu);
			}
		}
		
	}

}
