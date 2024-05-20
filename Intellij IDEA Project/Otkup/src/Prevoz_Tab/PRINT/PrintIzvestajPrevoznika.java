package Prevoz_Tab.PRINT;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Firma;
import model.Prevoz;
import model.Prevoznik;
import model.Proizvodjac;

public class PrintIzvestajPrevoznika {

	private static PrintIzvestajPrevoznika instance;
	private int linija=0;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	public static PrintIzvestajPrevoznika getInstance() {
		if(instance == null) {
			instance = new PrintIzvestajPrevoznika();
		}
		return instance;
	}
	
	private void printNode(ArrayList<Node> nodes) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
	       
		 Printer printer = Firma.getInstance().getA4printer();  
		 PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 10,10,10,10 );      	       
		 PrinterJob job = PrinterJob.createPrinterJob();	
		 job.setPrinter(printer);
		 
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
	
	public void StampajIzvestajPrevoznika(Prevoznik pre) {
		strane.clear();
		linija = 10;

		Font mali = Print.getInstance().getMali_F();
		Font srednji = Print.getInstance().getSrednji_F();
		Font veliki = Print.getInstance().getVeliki_F();
		Font bold = Print.getInstance().getBold_F();
		 //Font bold = Font.font("Verdana", FontWeight.BOLD, 12);
		 glavniVB = new VBox(0);
		 	
		 glavniVB.setAlignment(Pos.TOP_LEFT);
		 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		glavniVB.getChildren().add(Print.getInstance().zaglavlje());
		 
		 VBox naslovVB = new VBox(-5);
		 naslovVB.setAlignment(Pos.CENTER);
		 Label naslov = new Label("Izveštaj prevoznika: " + pre.getIme() + " " + pre.getPrezime());
		 naslov.setFont(veliki);
		 Label podnsalov = new Label("cena prevoza po kg: " + Double.valueOf(pre.getCenaPoKg()) + "din");
		 podnsalov.setFont(mali);
		 Label sezona = new Label("Sezona: " + Firma.getInstance().getTrenutnaGodina().getGodina());
		 sezona.setFont(srednji);
		 Label l0 = new Label(" ");
		 naslovVB.getChildren().addAll(naslov,podnsalov,sezona,l0);	
		 glavniVB.getChildren().addAll(naslovVB);
		 
		 HBox red = new HBox(10);
		 red.setAlignment(Pos.BASELINE_LEFT);
		 red.setPrefWidth(525);
		 red.setMaxWidth(525);
		 red.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 Label l1 = new Label("br");
		 l1.setPrefWidth(40);
		 Label l2 = new Label("proizvođač");
		 l2.setPrefWidth(130);
		 Label l3 = new Label("prevezena količina[kg]");
		 l3.setPrefWidth(120);
		 Label l5 = new Label("vrednost prevoza[din]");
		 l5.setPrefWidth(150);
		 red.getChildren().addAll(l1,l2,l3,l5);
		 glavniVB.getChildren().add(red);
		 novaLinija();
		 
		 double ukupnaKolicina = 0.0;
		 
		 ////////////
		 ArrayList<Prevoz> prevozi = new ArrayList<Prevoz>();
		 for(Prevoz prevoz : Firma.getInstance().getTrenutnaGodina().getPrevozi()){
			 if(prevoz.getPrevoznik().equals(pre)) {
				 prevozi.add(prevoz);
			 }
		 }
		 
		 ArrayList<Proizvodjac> proizvodjaci = new ArrayList<Proizvodjac>();
		 for(Prevoz prevoz : prevozi) {
			 if(!proizvodjaci.contains(prevoz.getProizvodjac())) {
				 proizvodjaci.add(prevoz.getProizvodjac());
			 }
		 }
		 
		 int br=1;
		 for(Proizvodjac pro : proizvodjaci) {
			 double kolicina = 0.0;
			 for(Prevoz prevoz : prevozi) {
				 if(prevoz.getProizvodjac().equals(pro)) {
					 kolicina+=prevoz.getKolicna();
				 }
			 }
			 ukupnaKolicina+=kolicina;
			 
				 red = new HBox(10);
				 red.setAlignment(Pos.BASELINE_LEFT);
				 red.setPrefWidth(525);
				 red.setMaxWidth(525);
				 
				 l1 = new Label(String.valueOf(br));
				 br++;
				 l1.setPrefWidth(40);
				 l2 = new Label(pro.getIme() + " " + pro.getPrezime());
				 l2.setPrefWidth(130);
				 l3 = new Label(Print.getInstance().getFormatter().format(kolicina));
				 l3.setPrefWidth(120);
				 l5 = new Label(Print.getInstance().getFormatter().format( kolicina*pre.getCenaPoKg()));
				 l5.setPrefWidth(150);
				 red.getChildren().addAll(l1,l2,l3,l5);
				 glavniVB.getChildren().add(red);
				 novaLinija();
			 		 
		 }
		 
		 red = new HBox(10);
		 red.setAlignment(Pos.BASELINE_LEFT);
		 red.setPrefWidth(525);
		 red.setMaxWidth(525);
		 red.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("UKUPNO:");
		 l1.setPrefWidth(180);
		 l2 = new Label(Print.getInstance().getFormatter().format(ukupnaKolicina) );
		 l2.setPrefWidth(120);
		 l3 = new Label(Print.getInstance().getFormatter().format(ukupnaKolicina*pre.getCenaPoKg()) );
		 l3.setPrefWidth(100);
		 red.getChildren().addAll(l1,l2,l3);
		 glavniVB.getChildren().add(red);
		 novaLinija();
		 
		 while(linija <43) {
			 Label razmak = new Label(" ");
			 glavniVB.getChildren().addAll(razmak);
			 novaLinija();
		 }
		 
		 l1 = new Label("--------------------------------------------------------------------------------------------------------------");
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar cal = Calendar.getInstance();
		 l3 = new Label(Firma.getInstance().getAdresa() + "    datum: " + String.valueOf(dateFormat.format(cal.getTime())) );
		 glavniVB.getChildren().add(l1);
		 novaLinija();
		 glavniVB.getChildren().add(l3);
		 
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