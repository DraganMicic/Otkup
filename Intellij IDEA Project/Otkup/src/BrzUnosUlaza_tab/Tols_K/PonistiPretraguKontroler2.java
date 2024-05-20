package BrzUnosUlaza_tab.Tols_K;

import BrzUnosUlaza_tab.BrzUnosUlazaTab;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PonistiPretraguKontroler2 implements EventHandler<ActionEvent>{

    public void handle(ActionEvent event) {

        BrzUnosUlazaTab.getInstance().updateCBProizvodjacPretraga();
        BrzUnosUlazaTab.getInstance().updateCBUlazPretraga();
        BrzUnosUlazaTab.getInstance().updateCBPrevoznikPretraga();
        if(BrzUnosUlazaTab.getInstance().getBPonistiPretragu1().isDisable()) {
            BrzUnosUlazaTab.getInstance().updateTabele();
            BrzUnosUlazaTab.getInstance().getRBSviObracun().setSelected(true);
        }

        BrzUnosUlazaTab.getInstance().getBPonistiPretragu2().setDisable(true);

    }
}