package UnosIzlaza_Tab.Tools_K;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import UnosIzlaza_Tab.UnosIzlazaTab;

public class PonistiPretraguKontroler2 implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {

        UnosIzlazaTab.getInstance().updateCBProizvodjacPretraga();
        UnosIzlazaTab.getInstance().updateCBIzlazPretraga();

        if(UnosIzlazaTab.getInstance().getBPonisitPretragu1().isDisable()){
            UnosIzlazaTab.getInstance().updateTabele();
            UnosIzlazaTab.getInstance().getRBSviObracun().setSelected(true);
        }

        UnosIzlazaTab.getInstance().getBPonisitPretragu2().setDisable(true);

    }


}