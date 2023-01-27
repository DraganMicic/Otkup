package UnosUlaza_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import model.Ulaz;
import model.UnosUlaza;

public class PrintDnevniIzvestajUnosUlaza {
	
	private static PrintDnevniIzvestajUnosUlaza instance;
	private int linija=0;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList();
	
	
	public static PrintDnevniIzvestajUnosUlaza getInstance() {
		if(instance == null) {
			instance = new PrintDnevniIzvestajUnosUlaza();
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

	 public void stampajDnevniIzveštaj(LocalDate datum) {
		 strane.clear();
		 linija=8;

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
		 Label naslov = new Label("Dnevni izveštaj ulaza");
		 naslov.setFont(veliki);
		 HBox naslovHB = new HBox();
		 naslovHB.setAlignment(Pos.CENTER);
		 naslovHB.getChildren().add(naslov);
		 Label podnaslov = new Label("za dan: " +  Print.getInstance().getDateFormater2().format(datum));
		 podnaslov.setFont(srednji);
		 HBox podnasloovHB = new HBox();
		 podnasloovHB.setAlignment(Pos.CENTER);
		 podnasloovHB.getChildren().add(podnaslov);
		 naslovVB.getChildren().addAll(naslovHB,podnasloovHB);
		 glavniVB.getChildren().addAll(naslovVB);
		 novaLinija();
		 
		 Label razmak = new Label(" ");
		 glavniVB.getChildren().addAll(razmak);
		 novaLinija();
		 
		
		 HBox redizlazaHB = new HBox(10);
		 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 redizlazaHB.setPrefWidth(525);
		 redizlazaHB.setMaxWidth(525);
		 redizlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 Label l1 = new Label("br");
		 l1.setPrefWidth(30);
		 Label l2 = new Label("ulaz");
		 l2.setPrefWidth(160);
		 Label l3 = new Label("količina[kg]");
		 l3.setPrefWidth(100);
		 Label l4 = new Label("ukupna vrednost[din]");
		 l4.setPrefWidth(120);
		 redizlazaHB.getChildren().addAll(l1,l2,l3,l4);
		 glavniVB.getChildren().add(redizlazaHB);
		 novaLinija();
		 
		 razmak = new Label(" ");
		 glavniVB.getChildren().addAll(razmak);
		 novaLinija();

		 int br=0;
		 double ukupnaKolicina = 0.0;
		 double ukupnaVrednost = 0.0;
		 for(Ulaz u : Firma.getInstance().getTrenutnaGodina().ulaziZaDatum(datum)) {
			 double kolicina = 0.0;
			 double vrednost = 0.0;
			 for(UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().unosiUlazaZaDatum(datum)) {
				 if(u.equals(uu.getUlaz())) {
					 kolicina += uu.getKolicinaNeto();
					 if(uu.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno())) {
						 vrednost += uu.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(u, datum).getCenaSaPrevozom() + uu.getProizvodjac().getCenaPlus());
					 }else {
						 vrednost += uu.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(u, datum).getCenaBezPrevoza() + uu.getProizvodjac().getCenaPlus());
					 }
				 }
			 }
			 ukupnaKolicina += kolicina;
			 ukupnaVrednost += vrednost;
			 
			 br++;
			 redizlazaHB = new HBox(10);
			 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
			 redizlazaHB.setPrefWidth(525);
			 redizlazaHB.setMaxWidth(525);
			 l1 = new Label(br+".");
			 l1.setPrefWidth(30);
			 l2 = new Label(u.getNaziv());
			 l2.setPrefWidth(160);
			 l3= new Label(Print.getInstance().getFormatter().format(kolicina));
			 l3.setPrefWidth(100);
			 l4 = new Label(Print.getInstance().getFormatter().format(vrednost));
			 l4.setPrefWidth(120);
			 redizlazaHB.getChildren().addAll(l1,l2,l3,l4);
			 glavniVB.getChildren().add(redizlazaHB);
			 novaLinija();		
			 
			 razmak = new Label(" ");
			 glavniVB.getChildren().addAll(razmak);
			 novaLinija();			 
		 }
		 
		 redizlazaHB = new HBox(10);
		 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 redizlazaHB.setPrefWidth(525);
		 redizlazaHB.setMaxWidth(525);
		 redizlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("UKUPNO:");
		 l1.setPrefWidth(200);
		 l2 = new Label( Print.getInstance().getFormatter().format(ukupnaKolicina) + "kg");
		 l2.setPrefWidth(100 );
		 l3 = new Label(Print.getInstance().getFormatter().format(ukupnaVrednost) + "din");
		 l3.setPrefWidth (120);
		
		 redizlazaHB.getChildren().addAll(l1,l2,l3);
		 glavniVB.getChildren().add(redizlazaHB);
		 novaLinija();
		 
		 while(linija <43) {
			 razmak = new Label(" ");
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
