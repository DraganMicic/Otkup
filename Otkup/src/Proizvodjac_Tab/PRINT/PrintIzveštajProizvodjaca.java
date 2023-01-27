package Proizvodjac_Tab.PRINT;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import model.*;


public class PrintIzveštajProizvodjaca {
	
	private static PrintIzveštajProizvodjaca instance;
	private int linija=0;
	private VBox glavniVB;
	private  ArrayList<Node> strane = new ArrayList<Node>();

	public static PrintIzveštajProizvodjaca getInstance() {
		if(instance == null) {
			instance = new PrintIzveštajProizvodjaca();
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
				 success = job.printPage(pageLayout, n);
			 }
			 if (success) {
				 job.endJob();
			 }
		 }
	 }
	 
	 public void stampajIzvestajProizvodjaca(Proizvodjac p) {
		 
		 strane.clear();
		 
		 Font mali = Print.getInstance().getMali_F();
		 Font srednji = Print.getInstance().getSrednji_F();
		 Font veliki = Print.getInstance().getVeliki_F();
		 Font bold = Print.getInstance().getBold_F();

		 glavniVB = new VBox(0);                                     //glavni boks u kojiu sve ide
		 glavniVB.setAlignment(Pos.TOP_LEFT);
		 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		 glavniVB.getChildren().add(Print.getInstance().zaglavlje());

		 //Naslov //////////////////////////////////

		 VBox naslovVB = new VBox(-5);
		 naslovVB.setAlignment(Pos.CENTER);
		 Label naslov = new Label("List proizvođača ");
		 naslov.setFont(veliki);
		 Label sezona = new Label("Sezona: " + Firma.getInstance().getTrenutnaGodina().getGodina());
		 sezona.setFont(srednji);
		 HBox proizvodjacHB = new HBox(10);
		 proizvodjacHB.setAlignment(Pos.CENTER);
		 Label ProizvodjacIme = new Label(p.getIme()+" " + p.getPrezime());
		 ProizvodjacIme.setFont(srednji);
		 Label ProizvodjacPodaci = new Label("JMBG: " + p.getMaticniBroj() + "  Broj RPG: " + p.getBrojGazdinstva() );
		 ProizvodjacPodaci.setFont(mali);
		 proizvodjacHB.getChildren().addAll(ProizvodjacIme,ProizvodjacPodaci);
		 naslovVB.getChildren().addAll(naslov,sezona,proizvodjacHB);		 
		 glavniVB.getChildren().addAll(naslovVB);
		 Label razmak = new Label(" ");
		 glavniVB.getChildren().addAll(razmak);

		 linija=11;                //pocetno stanje linijeeee

		 //    zaglavlje tabele izlazaaa/////////////////////////

		 Label izlazi = new Label("IZLAZI:");   //izlazi naslov
		 izlazi.setFont(bold);	 
		 glavniVB.getChildren().addAll(izlazi);
		 novaLinija();

		 HBox redizlazaHB = new HBox(10);
		 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 redizlazaHB.setPrefWidth(535);
		 redizlazaHB.setMaxWidth(535);
		 redizlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 Label l1 = new Label("br");
		 l1.setPrefWidth(35);
		 Label l2 = new Label("datum");
		 l2.setPrefWidth(60);
		 Label l3 = new Label("naziv");
		 l3.setPrefWidth(100);
		 Label l4 = new Label("opis");
		 l4.setPrefWidth(60);
		 Label l5 = new Label("JM");
		 l5.setPrefWidth(25);
		 Label l6 = new Label("kolicina");
		 l6.setPrefWidth(70);
		 Label l7 = new Label("cena/JM");
		 l7.setPrefWidth(50);
		 Label l8 = new Label("ukupno[din]");
		 l8.setPrefWidth(90);
		 redizlazaHB.getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8);
		 glavniVB.getChildren().add(redizlazaHB);
		 novaLinija();

		 ///dodavanje izlazaaaa///////////////////////////////////////

		 double ukupnoIzlaza = 0.0;
		 for(UnosIzlaza ui : p.getUnosiIzlza()) {
			 redizlazaHB = new HBox(10);
			 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
			 redizlazaHB.setPrefWidth(535);
			 redizlazaHB.setMaxWidth(535);
			 l1 = new Label(String.valueOf(ui.getSifra()));
			 l1.setPrefWidth(35);
			 l2 = new Label(  Print.getInstance().getDateFormater().format(ui.getDatum()));
			 l2.setPrefWidth(60);
			 l3= new Label(ui.getIzlaz().getNaziv());
			 l3.setPrefWidth(100);
			 l4 = new Label(ui.getIzlaz().getOpis());
			 l4.setPrefWidth(60);
			 l5 = new Label(ui.getIzlaz().getJedinicaMere());
			 l5.setPrefWidth(25);

			 if(ui.getIzlaz().getJedinicaMere().equals("din")) {
				 l7 = new Label(" ");
				 l6 = new Label(" ");
			 }
			 else {
				 l6 = new Label(Print.getInstance().getFormatter().format(ui.getKolicina()));
				 l7 = new Label(String.valueOf(ui.getIzlaz().getCenaPoKomadu()));
			 }
			 l7.setPrefWidth(50);
			 l6.setPrefWidth(70);
			 double vrednost = ui.getKolicina()*ui.getIzlaz().getCenaPoKomadu();
			 ukupnoIzlaza += vrednost;
			 l8 = new Label(Print.getInstance().getFormatter().format(vrednost));
			 l8.setPrefWidth(90);
			 redizlazaHB.getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8);
			 glavniVB.getChildren().add(redizlazaHB);
			 novaLinija();			 
		 }

		 //ukupno izlazaaaa/////////////////////////////////////////////////////////

		 redizlazaHB = new HBox(10);
		 redizlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 // redizlazaHB.setPrefWidth(525);
		 //redizlazaHB.setMaxWidth(525);
		 redizlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("UKUPNO:");
		 l1.setPrefWidth(440);
		 l2 = new Label(Print.getInstance().getFormatter().format(ukupnoIzlaza));
		 redizlazaHB.getChildren().addAll(l1,l2);
		 glavniVB.getChildren().add(redizlazaHB);
		 novaLinija();
		 
		 razmak = new Label(" ");
		 glavniVB.getChildren().addAll(razmak);
		 novaLinija();

		 ////////////////zaglavlje tabele ulaza////////

		 Label ulazi = new Label("ULAZI:");
		 ulazi.setFont(bold);	 
		 glavniVB.getChildren().addAll(ulazi);
		 novaLinija();
		 
		 HBox redUlazaHB = new HBox(10);
		 redUlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 //redUlazaHB.setPrefWidth(525);
		 ///redUlazaHB.setMaxWidth(525);
		 redUlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("br");
		 l1.setPrefWidth(40);
		 l2 = new Label("datum");
		 l2.setPrefWidth(65);
		 l3 = new Label("proizvod");
		 l3.setPrefWidth(145);
		 l4 = new Label("cena/kg");
		 l4.setPrefWidth(45);
		 l5 = new Label("gajbi");
		 l5.setPrefWidth(40);
		 l7 = new Label("neto[kg]");
		 l7.setPrefWidth(55);
		 l8 = new Label("ukupno[din]");
		 l8.setPrefWidth(75);
		 redUlazaHB.getChildren().addAll(l1,l2,l3,l4,l5,l7,l8);
		 glavniVB.getChildren().add(redUlazaHB);
		 novaLinija();

		 ///////////////////dodavanje ulaza i sabiranje istih usput //////////////

		 ArrayList<Ulaz> ulaziii = Firma.getInstance().getTrenutnaGodina().getUlazi();
		 double[] ukupnoKg = new double[ulaziii.size()];
		 double[] ukupnaVrednost = new double[ulaziii.size()];

		 for(UnosUlaza uu : p.getUnosiUlaza()) {

			 redUlazaHB = new HBox(10);
			 redUlazaHB.setAlignment(Pos.BASELINE_LEFT);
			 //redUlazaHB.setPrefWidth(530);
			 ///redUlazaHB.setMaxWidth(530);
			 l1 = new Label(String.valueOf(uu.getSifra()));
			 l1.setPrefWidth(40);
			 l2 = new Label(Print.getInstance().getDateFormater().format(uu.getDatum()));
			 l2.setPrefWidth(65);
			 l3= new Label(uu.getUlaz().getNaziv());
			 l3.setPrefWidth(145);
			 double cena;
			 if(uu.getPrevoznik() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
			 	cena = (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaSaPrevozom()) + uu.getProizvodjac().getCenaPlus();
			 }
			 else {
			 	cena = (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaBezPrevoza()) + uu.getProizvodjac().getCenaPlus();
			 }
			 l4 = new Label(String.valueOf(cena));
			 l4.setPrefWidth(45);
			 l5 = new Label(String.valueOf(uu.getGajbe()));
			 l5.setPrefWidth(40);
			 l7 = new Label(String.valueOf(uu.getKolicinaNeto()));
			 l7.setPrefWidth(55);
			 int a = ulaziii.indexOf(uu.getUlaz());
			 ukupnoKg[a] += uu.getKolicinaNeto();
			 double vrednost = uu.getKolicinaNeto()*cena;
			 ukupnaVrednost[a] += vrednost;
			 l8 = new Label( Print.getInstance().getFormatter().format(vrednost));
			 l8.setPrefWidth(75);
			 redUlazaHB.getChildren().addAll(l1,l2,l3,l4,l5,l7,l8);
			 glavniVB.getChildren().add(redUlazaHB);
			 novaLinija();			 
		 }

		 ////////////////ukupno ulazaa///////////////////////////////

		 redUlazaHB = new HBox(10);
		 redUlazaHB.setAlignment(Pos.BASELINE_LEFT);
		 //redUlazaHB.setPrefWidth(530);
		 //redUlazaHB.setMaxWidth(530);
		 redUlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("UKUPNO:");
		 l1.setPrefWidth(145);
		 redUlazaHB.getChildren().addAll(l1);
		 glavniVB.getChildren().add(redUlazaHB);
		 novaLinija();

		 Label l0;
		 for(int a = 0 ; a<ukupnoKg.length; a++ ){
		 	if(ukupnoKg[a]!=0) {
				redUlazaHB = new HBox(10);
				redUlazaHB.setAlignment(Pos.BASELINE_LEFT);
				//redUlazaHB.setPrefWidth(530);
				//redUlazaHB.setMaxWidth(530);
				redUlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
				l0 = new Label(" ");
				l0.setPrefWidth(115);
				l1 = new Label(ulaziii.get(a).getNaziv());
				l1.setPrefWidth(250);
				l2 = new Label(Print.getInstance().getFormatter().format(ukupnoKg[a]));
				l2.setPrefWidth(55);
				l3 = new Label(Print.getInstance().getFormatter().format(ukupnaVrednost[a]));
				l3.setPrefWidth(80);
				redUlazaHB.getChildren().addAll(l0,l1, l2, l3);
				glavniVB.getChildren().add(redUlazaHB);
				novaLinija();
			}
		 }

		 redUlazaHB = new HBox(10);
		 redUlazaHB.setAlignment(Pos.BASELINE_RIGHT);
		 //redUlazaHB.setPrefWidth(530);
		 //redUlazaHB.setMaxWidth(530);
		 redUlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, Insets.EMPTY)));
		 l1 = new Label("-------------------------------------------------------------------------------------- ");
		 redUlazaHB.getChildren().add(l1);
		 glavniVB.getChildren().add(redUlazaHB);
		 novaLinija();

		 double ukupnoKgSvega = 0.0;
		 double ukupaVrednostSvega = 0.0;
		 for (double i : ukupnoKg)
		 	ukupnoKgSvega += i;
		 for (double i : ukupnaVrednost)
		 	ukupaVrednostSvega += i;

		 l2 = new Label(Print.getInstance().getFormatter().format(ukupnoKgSvega));
		 l2.setPrefWidth(55);
		 l4 = new Label(Print.getInstance().getFormatter().format(ukupaVrednostSvega));
		 l4.setPrefWidth(80);
		 l0 = new Label(" ");
		 l0.setPrefWidth(375);
		 redUlazaHB = new HBox(10);
		 redUlazaHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 redUlazaHB.getChildren().addAll(l0,l2,l4);
		 glavniVB.getChildren().add(redUlazaHB);
		 novaLinija();
		 
		 
		 razmak = new Label(" ");
		 glavniVB.getChildren().addAll(razmak);
		 novaLinija();
		 
		 HBox ukupnoHB = new HBox(10);
		 ukupnoHB.setAlignment(Pos.BASELINE_LEFT);
		 //ukupnoHB.setPrefWidth(530);
		 //ukupnoHB.setMaxWidth(530);
		 ukupnoHB.setBackground(new Background(new BackgroundFill(Color.rgb(200,200,200),CornerRadii.EMPTY,Insets.EMPTY)));
		 l1 = new Label("SALDO(ulaz-izlaz)[din]:");
		 l1.setPrefWidth(440);
		 l2 = new Label(Print.getInstance().getFormatter().format(ukupaVrednostSvega-ukupnoIzlaza));
		 ukupnoHB.getChildren().addAll(l1,l2);
		 glavniVB.getChildren().add(ukupnoHB);
		 novaLinija();

		 ///////ostatak papiraaa//////////////
		 
		 while(linija <44) {
			 razmak = new Label(" ");
			 glavniVB.getChildren().addAll(razmak);
			 novaLinija();
		 }
		 
		 l1 = new Label("----------------------------------------------------------------------------------------------------------------");
		 DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar cal = Calendar.getInstance();
		 l3 = new Label(Firma.getInstance().getAdresa() + "     datum: " + String.valueOf(dateFormat.format(cal.getTime())) );
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
