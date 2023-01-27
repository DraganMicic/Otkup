package BazaIzlaza_Tab.Tools_K;

import BazaIzlaza_Tab.BazaIzlazaTab;
import BazaIzlaza_Tab.PRINT.PrintSpisakIzlaza;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Firma;
import model.Izlaz;

import java.util.ArrayList;

public class StampaSpiska_Izlaz_K implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        ArrayList<Izlaz> zaStampu = new ArrayList<Izlaz>();

        if (BazaIzlazaTab.getInstance().getRBSviSpisak().isSelected()) {
            zaStampu.addAll(Firma.getInstance().getTrenutnaGodina().getIzlazi());
            PrintSpisakIzlaza.getInstance().StampajSpisakIzlaza(zaStampu);
        }

        if (BazaIzlazaTab.getInstance().getRBFiltriraniSpisak().isSelected()) {
            zaStampu.addAll(BazaIzlazaTab.getInstance().getTabela().getItems());
            PrintSpisakIzlaza.getInstance().StampajSpisakIzlaza(zaStampu);

        }
    }
}
