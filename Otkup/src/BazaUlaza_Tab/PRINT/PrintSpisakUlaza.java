package BazaUlaza_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;
import model.Ulaz;

public class PrintSpisakUlaza {
	
	private static PrintSpisakUlaza instance;
	private int linija=0;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	public static PrintSpisakUlaza getInstance() {
		if(instance == null) {
			instance = new PrintSpisakUlaza();
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
	
	public void StampajSpisakUlaza(ArrayList<Ulaz> ulazi) {
		strane.clear();
		linija = 12;

		Font mali = Print.getInstance().getMali_F();
		Font srednji = Print.getInstance().getSrednji_F();
		Font veliki = Print.getInstance().getVeliki_F();
		Font bold = Print.getInstance().getBold_F();
		 glavniVB = new VBox(0);
		 	
		 glavniVB.setAlignment(Pos.TOP_LEFT);
		 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		 glavniVB.getChildren().add(Print.getInstance().zaglavlje());
		 
		 VBox naslovVB = new VBox(-5);
		 naslovVB.setAlignment(Pos.CENTER);
		 Label naslov = new Label("Spisak ulaza");
		 naslov.setFont(veliki);
		 Label sezona = new Label("Sezona: " + Firma.getInstance().getTrenutnaGodina().getGodina());
		 sezona.setFont(srednji);
		 Label vrstaSpiska = new Label("(proizvoda za otkup)");
		 vrstaSpiska.setFont(mali);
		 Label l0 = new Label(" ");
		 naslovVB.getChildren().addAll(naslov,vrstaSpiska,sezona,l0);	
		 glavniVB.getChildren().addAll(naslovVB);
		 
		 HBox red = new HBox(10);
		 red.setAlignment(Pos.BASELINE_LEFT);
		 red.setPrefWidth(525);
		 red.setMaxWidth(525);
		 red.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 Label l1 = new Label("šifra");
		 l1.setPrefWidth(40);
		 Label l2 = new Label("naziv");
		 l2.setPrefWidth(180);
		 Label l3 = new Label("opis");
		 l3.setPrefWidth(180);
		 red.getChildren().addAll(l1,l2,l3);
		 glavniVB.getChildren().add(red);
		 novaLinija();
		 
		 for(Ulaz u : ulazi) {
			 red = new HBox(10);
			 red.setAlignment(Pos.BASELINE_LEFT);
			 red.setPrefWidth(525);
			 red.setMaxWidth(525);
			 
			 l1 = new Label(String.valueOf(u.getSifra()));
			 l1.setPrefWidth(40);
			 l2 = new Label(u.getNaziv());
			 l2.setPrefWidth(180);
			 l3 = new Label(u.getOpis());
			 l3.setPrefWidth(180);
			 red.getChildren().addAll(l1,l2,l3);
			 glavniVB.getChildren().add(red);
			 novaLinija();
		 }
		 
		 strane.add(glavniVB);
		    
		 try {
		   	printNode(strane);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

		}
		 
		 
		 
		 
	}
	
	private void novaLinija() {
		 linija++;
		 if(linija > 45) {
			 linija =0;
			 strane.add(glavniVB);
		 }
	}
}
