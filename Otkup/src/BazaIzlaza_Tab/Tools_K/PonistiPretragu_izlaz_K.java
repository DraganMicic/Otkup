package BazaIzlaza_Tab.Tools_K;

import BazaIzlaza_Tab.BazaIzlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonistiPretragu_izlaz_K implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        BazaIzlazaTab.getInstance().getTfPretraga().clear();
        BazaIzlazaTab.getInstance().updateTabele();
        BazaIzlazaTab.getInstance().getBPonistiPretragu().setDisable(true);
        BazaIzlazaTab.getInstance().getBDodaj().requestFocus();
        BazaIzlazaTab.getInstance().getRBSviSpisak().setSelected(true);

    }
}
