package UnosUlaza_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;

public class POSPrintMikerPapiric {
	
	private static POSPrintMikerPapiric instance;
	
	public static POSPrintMikerPapiric getInstance() {
		if(instance == null) {
			instance = new POSPrintMikerPapiric();
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
    
    public void stampajMikerPapiric() {
    	VBox glavniVB = new VBox(0);
    	glavniVB.setPrefWidth(210);
	    glavniVB.setPrefHeight(600);
	    glavniVB.setAlignment(Pos.TOP_CENTER);
	    
	    Label naslov = new Label("MIKER");
	    naslov.setFont(Font.font("Verdana", FontWeight.BOLD, 33));
	    Label lajna1 = new Label("-------------------------------------------");
		Label lajna2 = new Label("-------------------------------------------");
		Label razmak1 = new Label(" ");
		Label razmak2 = new Label(". ");
		razmak2.setFont(new Font(8));
		glavniVB.getChildren().addAll(lajna1,naslov,lajna2,razmak1,razmak2);
		
		 try {
		    	printNode(glavniVB);
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

			}
    	
    	
    }
}
