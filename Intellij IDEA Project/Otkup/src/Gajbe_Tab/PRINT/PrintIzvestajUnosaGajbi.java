package Gajbe_Tab.PRINT;

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
import model.Proizvodjac;
import model.UnosGajbi;
import model.Firma;
import model.Prevoznik;

public class PrintIzvestajUnosaGajbi {
	private static PrintIzvestajUnosaGajbi instance;
	private int linija=12;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	public static PrintIzvestajUnosaGajbi getInstance() {
		if(instance == null) {
			instance = new PrintIzvestajUnosaGajbi();
		}
		return instance;
	}
	
	private void printNode(ArrayList<Node> nodes) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
	       
		 Printer printer = Firma.getInstance().getA4printer();
		 PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 10,10,10,10 );      
	       
		 PrinterJob job = PrinterJob.createPrinterJob();	        
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
	
	public void stamapjIzvestajUnosaGajbi(Prevoznik prevoznik, Proizvodjac proizvodjac) {
		strane.clear();
		linija = 10;

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
		 Label naslov = new Label("Izveštaj ulaza/izlaza gajbi: " );
		 naslov.setFont(veliki);
		 Label podnsalov = new Label();
		 if(proizvodjac != null) {
			 podnsalov.setText("Proizvođač: " + proizvodjac.getIme() + " " +  proizvodjac.getPrezime());
		 }else if(prevoznik != null) {
			 podnsalov.setText("Prevoznik: " + prevoznik.getIme() + " " + prevoznik.getPrezime());
		 }
		 podnsalov.setFont(srednji);
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
		 Label l1 = new Label("br lista gajbi");
		 l1.setPrefWidth(70);
		 Label l2 = new Label("datum");
		 l2.setPrefWidth(70);
		 Label l3 = new Label("gajba");
		 l3.setPrefWidth(80);
		 Label l5 = new Label("ulaz[kom]");
		 l5.setPrefWidth(80);
		 Label l6 = new Label("izlaz[kom]");
		 l6.setPrefWidth(80);
		 Label l7 = new Label("saldo[kom]");
		 l7.setPrefWidth(80);
		 red.getChildren().addAll(l1,l2,l3,l5,l6,l7);
		 glavniVB.getChildren().add(red);
		 novaLinija();
		 
		 int ukupnoUlaza = 0;
		 int ukupnoIzlaza = 0;
		
		 if(proizvodjac != null) {
			 for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().unosiGajbiZaProizvodjaca(proizvodjac)) {
				 ukupnoIzlaza += ug.getGajbeIzlaz();
				 ukupnoUlaza += ug.getGajbeUlaz();
				 red = new HBox(10);
				 red.setAlignment(Pos.BASELINE_LEFT);
				 red.setPrefWidth(525);
				 red.setMaxWidth(525);
				 l1 = new Label(proizvodjac.getSifra() + "/" + ug.getSifra());
				 l1.setPrefWidth(70);
				 l2 = new Label(ug.getDatum().toString());
				 l2.setPrefWidth(70);
				 l3 = new Label(ug.getGajba().toString());
				 l3.setPrefWidth(80);
				 l5 = new Label(String.valueOf(ug.getGajbeUlaz()));
				 l5.setPrefWidth(80);
				 l6 = new Label(String.valueOf(ug.getGajbeIzlaz()));
				 l6.setPrefWidth(80);
				 l7 = new Label(ug.getSaldo());
				 l7.setPrefWidth(80);
				 red.getChildren().addAll(l1,l2,l3,l5,l6,l7);
				 novaLinija();
				 glavniVB.getChildren().add(red);
			 }
		 }
		 
		 if(prevoznik != null) {
			 for(UnosGajbi ug: Firma.getInstance().getTrenutnaGodina().unosiGajBiZaPrevoznika(prevoznik)) {
				 ukupnoIzlaza += ug.getGajbeIzlaz();
				 ukupnoUlaza += ug.getGajbeUlaz();
				 red = new HBox(10);
				 red.setAlignment(Pos.BASELINE_LEFT);
				 red.setPrefWidth(525);
				 red.setMaxWidth(525);
				 l1 = new Label(prevoznik.getSifra() + "/" + ug.getSifra());
				 l1.setPrefWidth(70);
				 l2 = new Label(ug.getDatum().toString());
				 l2.setPrefWidth(70);
				 l3 = new Label(ug.getGajba().toString());
				 l3.setPrefWidth(80);
				 l5 = new Label(String.valueOf(ug.getGajbeUlaz()));
				 l5.setPrefWidth(80);
				 l6 = new Label(String.valueOf(ug.getGajbeIzlaz()));
				 l6.setPrefWidth(80);
				 l7 = new Label(ug.getSaldo());
				 l7.setPrefWidth(80);
				 red.getChildren().addAll(l1,l2,l3,l5,l6,l7);
				 novaLinija();
				 glavniVB.getChildren().add(red);
			 }
		 }
		 
		 red = new HBox(10);
		 red.setAlignment(Pos.BASELINE_LEFT);
		 red.setPrefWidth(525);
		 red.setMaxWidth(525);
		 red.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("UKUPNO:");
		 l1.setPrefWidth(240);
		 l2 = new Label(ukupnoUlaza + "kom");
		 l2.setPrefWidth(80);
		 l3 = new Label(ukupnoIzlaza + "kom");
		 l3.setPrefWidth(80);
		 l5 = new Label();
		 int s = ukupnoIzlaza - ukupnoUlaza;
			if(s>0)
				l5.setText( "+" + String.valueOf(s) );
			if(s<=0)
				l5.setText(String.valueOf(s) );
			
		 l5.setPrefWidth(60);		 
		 red.getChildren().addAll(l1,l2,l3,l5);
		 novaLinija();
		 glavniVB.getChildren().add(red);
		 
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
			 glavniVB = new VBox(0);
			 glavniVB.setAlignment(Pos.TOP_LEFT);
			 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		 }
	}
}
