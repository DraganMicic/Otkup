package Proizvodjac_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Main.Print;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.Firma;
import model.Proizvodjac;

public class PrintOdobrenjeZaPreparate {
	
	private static PrintOdobrenjeZaPreparate instance;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	
	public static PrintOdobrenjeZaPreparate getInstance() {
		if(instance == null) {
			instance = new PrintOdobrenjeZaPreparate();
		}
		return instance;
	}
	
	 private void printNode(ArrayList<Node> nodes) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
	       
		 PrinterJob job = PrinterJob.createPrinterJob();	
		 Printer printer = Firma.getInstance().getA4printer(); 
		 job.setPrinter(printer);
		 PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 10,10,10,10 );         
		 if (job != null && job.showPrintDialog(null)){
			 boolean success = true;
			 for(Node n: nodes) {
				 success = job.printPage(pageLayout,n);
			 }
			 if (success) {
				 job.endJob();
			 }
		 }
	 }
	 
	 public void stampajOdobrenjeZaPreprate(Proizvodjac p) {
		 strane.clear();

		 Font mali = Print.getInstance().getMali_F();
		 Font srednji = Print.getInstance().getSrednji_F();
		 Font veliki = Print.getInstance().getVeliki_F();
		 Font bold = Print.getInstance().getBold_F();
		 Font bold_veliki = Print.getInstance().getBold_Veliki_F();
		 glavniVB = new VBox(0);		 	
		 glavniVB.setAlignment(Pos.TOP_LEFT);
		 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		 glavniVB.getChildren().add(Print.getInstance().zaglavlje());

		 VBox tekst1VB = new VBox(-5);
		 String s = "pp " + "\"" + Firma.getInstance().getIme() + "\" d.o.o. " + "prema obavezama koje proizilaze iz ugovora o koperaciji, proizvodnji i otkupu daje:  ";
		 Label l1 = new Label(s);
		 l1.setPrefWidth(525); 
		 l1.setWrapText(true);
		 l1.setFont(new Font(15));
		 l1.setTextAlignment(TextAlignment.JUSTIFY);		 
		 tekst1VB.getChildren().add(l1);
		 
		 VBox tekst2VB = new VBox(0);
		 tekst2VB.setAlignment(Pos.CENTER);
		 Label l2 = new Label("ODOBRENjE");
		 l2.setFont(veliki);
		 Label l123 = new Label(" ");
		 tekst2VB.getChildren().addAll(l2,l123);
	 
		 VBox tekst3VB = new VBox(-5);
		 HBox tekst3HB = new HBox(0);
		 tekst3HB.setAlignment(Pos.BASELINE_LEFT);
		 Label l3 = new Label("Poljoprivrednom proizvođaču: ");
		 l3.setFont(srednji);
		 Label l32 = new Label( p.getIme() + " " + p.getPrezime());
		 l32.setFont(bold_veliki);
		 tekst3HB.getChildren().addAll(l3,l32);
		 tekst3VB.setAlignment(Pos.BASELINE_LEFT);
		 tekst3VB.getChildren().add(tekst3HB);
		 Label l4 = new Label("Mesto: " + p.getMesto() + "   Matični br: " + p.getMaticniBroj());
		 l4.setFont(new Font(15));
		 Label r = new Label(" ");
		 Label r2 = new Label(" ");
		 tekst3VB.getChildren().addAll(l4,r,r2);

		 String s2 = "Da kao vid akontacije na račun pp \"" + Firma.getInstance().getIme() + "\" d.o.o. moze uzimati robu u prodavnici: " + Firma.getInstance().getProdavnicaZaRobu();
		 VBox tekst4VB = new VBox(-5);
		 Label l12 = new Label(s2);
		 l12.setPrefWidth(525);
		 l12.setWrapText(true);
		 l12.setFont(new Font(15));
		 l12.setTextAlignment(TextAlignment.JUSTIFY);
		 tekst4VB.getChildren().add(l12);

		 glavniVB.getChildren().addAll(tekst1VB,tekst2VB,tekst3VB,tekst4VB);
		 
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		Label l122 = new Label("\"" + Firma.getInstance().getIme() + "\" d.o.o. " + Firma.getInstance().getAdresa() + " dana: "  + dateFormat.format(cal.getTime()));
		Label l13 = new Label("____________________________");
		Label razmak2 = new Label(" ");
		Label razmak3 = new Label(" ");
		Label razmak5 = new Label(" ");		
		Label lajna3 = new Label("--------------------------------------------------------------------------------------------------------------");
		 
		glavniVB.getChildren().addAll(razmak2,razmak3,l122,l13,razmak5,lajna3);
		 
		 strane.add(glavniVB);
		 
		 try {
			   	printNode(strane);
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

			}
	 }
	 

}
