package Gajbe_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;

import Main.Print;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;
import model.UnosGajbi;

import javax.swing.*;

public class POSPrintUosGajbi {
	
	private static POSPrintUosGajbi instance;
	
	public static POSPrintUosGajbi getInstance() {
		if(instance == null) {
			instance = new POSPrintUosGajbi();
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
    
    public void stampajUnosGajbi(UnosGajbi ug) {

		Font mali = new Font(10);
		
		VBox glavniVB = new VBox(-5);
	    glavniVB.setPrefWidth(210);
	    glavniVB.setPrefHeight(600);
	    glavniVB.setAlignment(Pos.TOP_LEFT);

	    glavniVB.getChildren().add(Print.getInstance().zaglavljePOS());

	    Label otkupniList = new Label();
	    VBox datumibrListaVB = new VBox(-3);
	    datumibrListaVB.setAlignment(Pos.TOP_LEFT);	    
	    if(ug.getProizvodjac()!=null) {
	    	otkupniList.setText("LIST AMBALAŽE br. " + ug.getProizvodjac().getSifra() +"/" + ug.getSifra());
	    }else if (ug.getPrevoznik()!=null) {
	    	otkupniList.setText("LIST AMBALAŽE br. " + ug.getPrevoznik().getSifra() +"/" + ug.getSifra());
	    }	
	    otkupniList.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    Label datum = new Label("Datum: " + Print.getInstance().getDateFormater2().format(ug.getDatum()));
	    Label lajna2 = new Label("-------------------------------------------");
	    datumibrListaVB.getChildren().addAll(otkupniList,datum,lajna2);
	    
	    VBox podaciProizvodjacaVB = new VBox(-3);
	    podaciProizvodjacaVB.setAlignment(Pos.TOP_LEFT);
		Label ime;
		Label jmbg = null;
		Label brGazdinstva = null;
		Label lajna3 = new Label("-------------------------------------------");
	    if(ug.getPrevoznik()!=null) {
			ime = new Label("Kooperant: " + ug.getPrevoznik().getIme() + " " + ug.getPrevoznik().getPrezime());
			ime.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
			podaciProizvodjacaVB.getChildren().addAll(ime,lajna3);
		}else {
	    	ime = new Label("Kooperant: " + ug.getProizvodjac().getIme() + " " + ug.getProizvodjac().getPrezime());
			jmbg = new Label("JMBG: " + ug.getProizvodjac().getMaticniBroj());
			brGazdinstva = new Label("Broj RPG: " + ug.getProizvodjac().getBrojGazdinstva());
			jmbg.setFont(new Font(12));
			brGazdinstva.setFont(new Font(12));
			ime.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
			podaciProizvodjacaVB.getChildren().addAll(ime,jmbg,brGazdinstva,lajna3);
		}


	    
	    VBox podaciGajbiVB = new VBox(-3);
	    podaciProizvodjacaVB.setAlignment(Pos.TOP_LEFT);
	    Label gajba = new Label("AMBALAŽA: " + ug.getGajba().getNaziv() + "(" + String.valueOf(ug.getGajba().getTezina()) + "kg) ");
	    gajba.setFont(Font.font("Verdana", FontWeight.BOLD, 12));	    
	    Label ulaz = new Label("Ulaz: " + String.valueOf(ug.getGajbeUlaz()) + "kom");
	    ulaz.setFont(new Font(12));	
	    Label izlazz = new Label("izlaz: " + String.valueOf(ug.getGajbeIzlaz()) + "kom");	  
	    izlazz.setFont(new Font(12));	
	    Label ukupnoZaduzenih = new Label();
	    ukupnoZaduzenih.setFont(new Font(12));	
	    Label status = new Label();	   
	    status.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    if(ug.getGajbeUlaz() > ug.getGajbeIzlaz()) {
	    	int a = ug.getGajbeUlaz() - ug.getGajbeIzlaz();
	    	status.setText("razdužio " + String.valueOf(a) + "kom"); 
	    }else if(ug.getGajbeIzlaz()>ug.getGajbeUlaz()) {
	    	int a = ug.getGajbeIzlaz() - ug.getGajbeUlaz();
	    	status.setText("zadužio " + String.valueOf(a) + "kom");
	    }else {
	    	status.setText(" ");
	    }	    
	    if(ug.getPrevoznik()!=null) {
	    	ukupnoZaduzenih.setText("Ukupno zaduženo: " + String.valueOf(Firma.getInstance().getTrenutnaGodina().gajbiKodPrevoznika(ug.getPrevoznik()))) ;
	    }else if (ug.getProizvodjac()!=null) {
	    	ukupnoZaduzenih.setText("Ukupno zaduženio: " +  String.valueOf(Firma.getInstance().getTrenutnaGodina().gajbiKodProizvodjaca(ug.getProizvodjac()))) ;
	    }	
	    Label lajna4 = new Label("-------------------------------------------");
	    podaciGajbiVB.getChildren().addAll(gajba,ulaz,izlazz,status,ukupnoZaduzenih,lajna4);
	    
	    VBox potpisiVB = new VBox(-5);
	    potpisiVB.setAlignment(Pos.TOP_LEFT);
	    Label potpisProizvodjaca = new Label("Kooperant: _________________________");
	    Label potpisOtkuplivača = new Label("Otkupljivač: ");
	    ImageView pecat = new ImageView(Firma.getInstance().getPecat());
	    double a= Firma.getInstance().getPecatScale();
	    double x = pecat.getImage().getWidth();
	    double y = pecat.getImage().getHeight();
	    pecat.setFitWidth(x/a);
	    pecat.setFitHeight(y/a);
	    HBox pecatHB = new HBox();
	    pecatHB.setAlignment(Pos.TOP_RIGHT);
	    pecatHB.setPrefWidth(200);
	    pecatHB.getChildren().add(pecat);
	    Label razmak = new Label(".");
	    razmak.setFont(new Font(8));
	    potpisiVB.getChildren().addAll(potpisProizvodjaca,potpisOtkuplivača,pecatHB,razmak);
	    
	    glavniVB.getChildren().addAll(datumibrListaVB,podaciProizvodjacaVB,podaciGajbiVB,potpisiVB);
	    
	    try {
	    	printNode(glavniVB);
			printNode(glavniVB);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

		}
    }
}
