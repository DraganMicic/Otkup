package BrzUnosUlaza_tab.PRINT;

import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;
import model.UnosUlaza;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class POSPrintKartonIdentifikacije {
	
	private static POSPrintKartonIdentifikacije instance;
	
	public static POSPrintKartonIdentifikacije getInstance() {
		if(instance == null) {
			instance = new POSPrintKartonIdentifikacije();
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
    
    public void stampajKartonIdentifikacije(UnosUlaza uu) {
    	VBox glavniVB = new VBox(0);
    	glavniVB.setPrefWidth(210);
	    glavniVB.setPrefHeight(600);
	    glavniVB.setAlignment(Pos.TOP_LEFT);
	    
	    Label naslov = new Label("KARTON IDENTIFIKACIJE");
	    naslov.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
	    Label lajna1 = new Label("-------------------------------------------");
	    Label proizvod = new Label("Proizvod: " + uu.getUlaz().getNaziv());
	    Label proizvodjac = new Label("Proizvođač: " + uu.getProizvodjac().getIme() + " " + uu.getProizvodjac().getPrezime());
	    proizvodjac.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Label datum = new Label("Datum: " + String.valueOf(dateFormat.format(cal.getTime())));
		datum.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
		Label lajna2 = new Label("-------------------------------------------");
		Label razmak1 = new Label(" ");
		Label razmak2 = new Label(". ");
		razmak2.setFont(new Font(8));
		glavniVB.getChildren().addAll(naslov,lajna1,proizvod,proizvodjac,datum,lajna2,razmak1,razmak2);
		
		 try {
		    	printNode(glavniVB);
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

		 }
    }
}
