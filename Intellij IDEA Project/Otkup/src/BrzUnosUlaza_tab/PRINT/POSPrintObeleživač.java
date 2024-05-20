package BrzUnosUlaza_tab.PRINT;

import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class POSPrintObeleživač {

    private static POSPrintObeleživač instance;

    public static POSPrintObeleživač getInstance() {
        if(instance == null) {
            instance = new POSPrintObeleživač();
        }
        return instance;
    }

    private void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Firma.getInstance().getPosPrinter();
        job.setPrinter(printer);
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0,0,0,0 );
        if (job != null ) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
        }
    }

    public void stampajObeleživač(String ceoTekstObeleživača) {
        VBox glavniVB = new VBox(0);
        glavniVB.setPrefWidth(210);
        glavniVB.setPrefHeight(600);
        glavniVB.setAlignment(Pos.TOP_CENTER);
        Label naslov = new Label(ceoTekstObeleživača.split(" ")[0].toUpperCase(Locale.ROOT));
        //Label naslov = new Label("MIKER");
        naslov.setFont(Font.font("Verdana", FontWeight.BOLD, 27));
        Label lajna1 = new Label("-------------------------------------------");
        Label lajna2 = new Label("-------------------------------------------");
        Label lajna3 = new Label("-------------------------------------------");
        glavniVB.getChildren().addAll(lajna1,new Label(" "),new Label(" "),new Label(" "),lajna2,naslov,lajna3,new Label(" "),new Label(" "));

        try {
            printNode(glavniVB);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

        }
    }
}
