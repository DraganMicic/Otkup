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
import model.CenaUlaza;
import model.Firma;
import BazaUlaza_Tab.BazaUlazaTab;

public class PrintCenovnik {
	
	private static PrintCenovnik instance;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	public static PrintCenovnik getInstance() {
		if(instance == null) {
			instance = new PrintCenovnik();
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

	public void stampajCenovnik(ArrayList<CenaUlaza> cene) {
		 
		strane.clear();
		
		Font mali = new Font(12);
		Font srednji = new Font(15);
		Font veliki = new Font(30); 
		Font boldVelik = Font.font("Verdana", FontWeight.BOLD, 30);
		Font boldMali = Font.font("Verdana", FontWeight.BOLD, 12);
		Font boldSrednji = Font.font("Verdana", FontWeight.BOLD, 15);
		glavniVB = new VBox(0);
		 	
		glavniVB.setAlignment(Pos.TOP_LEFT);
		glavniVB.setPadding(new Insets(15, 15, 15, 15));
		glavniVB.getChildren().add(Print.getInstance().zaglavlje());
		
		VBox nasovVB = new VBox(-5);
		nasovVB.setAlignment(Pos.CENTER);
		Label obavestenje = new Label("OBAVEŠTENJE");
		obavestenje.setFont(veliki);
		nasovVB.getChildren().add(obavestenje);
		Label l1 = new Label("Hladanjača \"" + Firma.getInstance().getIme() +"\" d.o.o. vrši otkup poljoprivrednih proizvoda roda " + Firma.getInstance().getTrenutnaGodina() + " godine"  );;
		l1.setFont(mali);
		Label l2 = new Label("po sledećim uslovima:");
		l2.setFont(mali);
		glavniVB.getChildren().addAll(nasovVB,l1,l2);
		
		Label lrazmak = new Label(" ");
		glavniVB.getChildren().add(lrazmak);
		
		VBox naslov2VB = new VBox(-5);
		naslov2VB.setAlignment(Pos.CENTER);
		Label cene2 = new Label("Akontne cene:");
		cene2.setFont(boldVelik);
		naslov2VB.getChildren().add(cene2);
		glavniVB.getChildren().add(naslov2VB);
		
		lrazmak = new Label(" ");
		glavniVB.getChildren().add(lrazmak);
		
		HBox predHHB = new HBox();
		Label predH = new Label();
		HBox red1 = new HBox();
		HBox red2 = new HBox();
		
		if(BazaUlazaTab.getInstance().getRBPredHladnjacom().isSelected()) {
		
			predHHB = new HBox();
			predHHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
			predHHB.setPrefWidth(525);
			predHHB.setMaxWidth(525);
			predH = new Label("Pred hladnjačom:");
			predH.setFont(boldSrednji);
			predHHB.getChildren().add(predH);
			glavniVB.getChildren().add(predHHB);
			
			lrazmak = new Label(" ");
			glavniVB.getChildren().add(lrazmak);
			
			red1 = new HBox();
			red2 = new HBox();
			
			for (CenaUlaza cu : cene) {
				red1 = new HBox();
				red1.setAlignment(Pos.BOTTOM_LEFT);
				l2 = new Label(cu.getUlaz().getNaziv());
				l2.setPrefWidth(240);
				l2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
				Label ll2 = new Label("(od: " + Print.getInstance().getDateFormater2().format(cu.getPocetakVazenja()) + " do: " + Print.getInstance().getDateFormater2().format(cu.getKrajVazenja())+")");
				ll2.setPrefWidth(200);
				ll2.setFont(mali);
				red1.getChildren().addAll(l2,ll2);
				glavniVB.getChildren().add(red1);
				
				red2 = new HBox();
				red2.setAlignment(Pos.BOTTOM_LEFT);
				double bezPDV = 95.0 * cu.getCenaSaPrevozom() / 100.0 ;
				Label l3 = new Label("                "+ bezPDV + " din + 5% PDV = " );
				l3.setFont(Font.font("Verdana", 15));
				l3.setPrefWidth(280);
				Label l4 = new Label(String.valueOf(cu.getCenaSaPrevozom())+" din");
				l4.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
				red2.getChildren().addAll(l3,l4);
				glavniVB.getChildren().add(red2);			
			}
		}
		
		lrazmak = new Label(" ");
		glavniVB.getChildren().add(lrazmak);
		
		if(BazaUlazaTab.getInstance().getRBNaOtkupnomMestu().isSelected()) {
			predHHB = new HBox();
			predHHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
			predHHB.setPrefWidth(525);
			predHHB.setMaxWidth(525);
			predH = new Label("Na otkupnom mestu:");
			predH.setFont(boldSrednji);
			predHHB.getChildren().add(predH);
			glavniVB.getChildren().add(predHHB);
			
			lrazmak = new Label(" ");
			glavniVB.getChildren().add(lrazmak);
			
			red1 = new HBox();
			red2 = new HBox();
			
			for (CenaUlaza cu : cene) {
				red1 = new HBox();
				red1.setAlignment(Pos.BOTTOM_LEFT);
				l2 = new Label(cu.getUlaz().getNaziv());
				l2.setPrefWidth(240);
				l2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
				Label ll2 = new Label("(od: " + Print.getInstance().getDateFormater2().format(cu.getPocetakVazenja()) + " do: " + Print.getInstance().getDateFormater2().format(cu.getKrajVazenja())+")");
				ll2.setPrefWidth(200);
				ll2.setFont(mali);
				red1.getChildren().addAll(l2,ll2);
				glavniVB.getChildren().add(red1);
				
				red2 = new HBox();
				red2.setAlignment(Pos.BOTTOM_LEFT);
				double bezPDV = 95.0 * cu.getCenaBezPrevoza() / 100.0 ;
				Label l3 = new Label("                "+ bezPDV + " din + 5% PDV = " );
				l3.setFont(Font.font("Verdana", 15));
				l3.setPrefWidth(280);
				Label l4 = new Label(String.valueOf(cu.getCenaBezPrevoza())+" din");
				l4.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
				red2.getChildren().addAll(l3,l4);
				glavniVB.getChildren().add(red2);			
			}
		}
		
		lrazmak = new Label(" ");
		glavniVB.getChildren().add(lrazmak);
		
		HBox konacnaHB = new HBox();		
		Label l8 = new Label("Konačna isplata: ");
		l8.setFont(boldMali);
		Label l9 = new Label("31/12/"+Firma.getInstance().getTrenutnaGodina());
		konacnaHB.getChildren().addAll(l8,l9);		
		HBox dinamikaHB = new HBox();
		Label l10 = new Label("Dinamika isplate: ");
		l10.setFont(boldMali);
		Label l11 = new Label("70% do kraja otkupa ; 30% 90 dana po završetku otkupa");
		dinamikaHB.getChildren().addAll(l10,l11);
		glavniVB.getChildren().addAll(konacnaHB,dinamikaHB);
		
		lrazmak = new Label(" ");
		glavniVB.getChildren().add(lrazmak);
		
		Label l12 = new Label("\"" + Firma.getInstance().getIme() + "\" d.o.o. " + Firma.getInstance().getAdresa());
		Label l13 = new Label("____________________________");
		glavniVB.getChildren().addAll(l12,l13);
				
		
		strane.add(glavniVB);
		
		try {
			printNode(strane);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

		}
	}


}
