package BazaIzlaza_Tab.PRINT;

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
import model.Izlaz;

public class PrintSpisakIzlaza {

	private static PrintSpisakIzlaza instance;
	private int linija=0;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	public static PrintSpisakIzlaza getInstance() {
		if(instance == null) {
			instance = new PrintSpisakIzlaza();
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
	
	public void StampajSpisakIzlaza(ArrayList<Izlaz> izlazi) {
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
		 Label naslov = new Label("Spisak (cenovnik) izlaza");
		 naslov.setFont(veliki);
		 Label sezona = new Label("Sezona: " + Firma.getInstance().getTrenutnaGodina().getGodina());
		 sezona.setFont(srednji);
		 Label l0 = new Label(" ");
		 naslovVB.getChildren().addAll(naslov,sezona,l0);	
		 glavniVB.getChildren().addAll(naslovVB);
		 
		 HBox red = new HBox(10);
		 red.setAlignment(Pos.BASELINE_LEFT);
		 red.setPrefWidth(525);
		 red.setMaxWidth(525);
		 red.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 Label l1 = new Label("šifra");
		 l1.setPrefWidth(40);
		 Label l2 = new Label("naziv");
		 l2.setPrefWidth(160);
		 Label l3 = new Label("opis");
		 l3.setPrefWidth(130);
		 Label l4 = new Label("JM");
		 l4.setPrefWidth(40);
		 Label l5 = new Label("cena/JM [din]");
		 l5.setPrefWidth(100);
		 red.getChildren().addAll(l1,l2,l3,l4,l5);
		 glavniVB.getChildren().add(red);
		 novaLinija();
		 
		 for(Izlaz i : izlazi) {
			 red = new HBox(10);
			 red.setAlignment(Pos.BASELINE_LEFT);
			 red.setPrefWidth(525);
			 red.setMaxWidth(525);
			 
			 l1 = new Label(String.valueOf(i.getSifra()));
			 l1.setPrefWidth(40);
			 l2 = new Label(i.getNaziv());
			 l2.setPrefWidth(160);
			 l3 = new Label(i.getOpis());
			 l3.setPrefWidth(130);
			 l4 = new Label(i.getJedinicaMere().toString());
			 l4.setPrefWidth(40);
			 l5 = new Label();
			 if(i.getJedinicaMere().equals("din"))
				 l5.setText(" ");
			 else
				 l5.setText(String.valueOf(i.getCenaPoKomadu()));
			 l5.setPrefWidth(70);
			 red.getChildren().addAll(l1,l2,l3,l4,l5);
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
