package BrzUnosUlaza_tab.PRINT;

import Main.Print;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Firma;
import model.UnosUlaza;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class POSPrintUlaz {
	
	private static POSPrintUlaz instance;
	
	public static POSPrintUlaz getInstance() {
		if(instance == null) {
			instance = new POSPrintUlaz();
		}
		return instance;
	}
	
    private void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
       
    	
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Firma.getInstance().getPosPrinter();  
        job.setPrinter(printer);
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0,0,0,0 );
        if (job != null ) {    
            boolean success = job.printPage(pageLayout,node);                 
            if (success) {
                job.endJob();
            }
        }
    }
	
	public void stampajUlazNaPOS(UnosUlaza uu) {
		
		Font mali = new Font(10);
		
		VBox glavniVB = new VBox(-5);

	    glavniVB.setPrefWidth(210);
	    glavniVB.setPrefHeight(600);
	    glavniVB.setAlignment(Pos.TOP_LEFT);
	    glavniVB.getChildren().add(Print.getInstance().zaglavljePOS());

	    
	    VBox datumibrListaVB = new VBox(-3);
	    datumibrListaVB.setAlignment(Pos.TOP_LEFT);
	    int godina = Firma.getInstance().getTrenutnaGodina().getGodina();
	    int redniBr = uu.getProizvodjac().getUnosiUlaza().indexOf(uu)+1;
	    Label otkupniList = new Label("OTKUPNI LIST br. " + redniBr +"/" + godina);
	    otkupniList.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    Label datum = new Label("Datum: " + Print.getInstance().getDateFormater2().format(uu.getDatum()));
	    Label otkupnoMesto = new Label("Otkupno (sabirno) mesto: Hladnjača");
	    otkupnoMesto.setFont(new Font(10));
	    Label lajna2 = new Label("-------------------------------------------");
	    datumibrListaVB.getChildren().addAll(otkupniList,datum,otkupnoMesto,lajna2);
	    
	    VBox podaciProizvodjacaVB = new VBox(-3);
	    podaciProizvodjacaVB.setAlignment(Pos.TOP_LEFT);
	    Label ime = new Label("Kooperant: " + uu.getProizvodjac().getIme() + " " + uu.getProizvodjac().getPrezime());
	    ime.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
	    Label jmbg = new Label("JMBG: " + uu.getProizvodjac().getMaticniBroj());
	    jmbg.setFont(new Font(12));
	    Label brGazdinstva = new Label("Broj RPG: " + uu.getProizvodjac().getBrojGazdinstva());
	    brGazdinstva.setFont(new Font(12));
	    Label lajna3 = new Label("-------------------------------------------");
	    podaciProizvodjacaVB.getChildren().addAll(ime,jmbg,brGazdinstva,lajna3);
	    
	    VBox proizvodVB = new VBox(-5);
	    proizvodVB.setAlignment(Pos.TOP_LEFT);
	    HBox proizvodHB = new HBox();
	    proizvodHB.setAlignment(Pos.BASELINE_LEFT);
	    Label proizvod = new Label("Proizvod: ");
	    proizvod.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    Label nazivProizvoda = new Label(uu.getUlaz().getNaziv());
	    proizvodHB.getChildren().addAll(proizvod,nazivProizvoda);
	    GridPane tabelaKilaza = new GridPane();
	    tabelaKilaza.setPrefWidth(210);
	    tabelaKilaza.setHgap(20);
	    Label brutoL = new Label("Bruto");
	    Label netoL = new Label("Neto");
	    Label taraL = new Label("Tara");
	    Label cenaL = new Label("Cena(din)");
	    Label bruto = new Label(Print.getInstance().getFormatter().format(uu.getKolicinaBruto()));
	    Label neto = new Label(Print.getInstance().getFormatter().format(uu.getKolicinaNeto()));
	    double taraint =  uu.getGajbe()*uu.getGajba().getTezina();
	    Label tara = new Label(Print.getInstance().getFormatter().format(taraint));
	    double cenaint;
	    if(uu.getPrevoznik() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
			cenaint = Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaSaPrevozom()+ uu.getProizvodjac().getCenaPlus();
		}
		else {
			cenaint = Firma.getInstance().getTrenutnaGodina().cenaZaDatum(uu.getUlaz(), uu.getDatum()).getCenaBezPrevoza()+ uu.getProizvodjac().getCenaPlus();
		}
	    Label cena = new Label(Print.getInstance().getFormatter().format(cenaint));
	    GridPane.setConstraints(brutoL, 0, 0);
	    GridPane.setConstraints(taraL, 1, 0);
	    GridPane.setConstraints(netoL, 2, 0);
	    GridPane.setConstraints(cenaL, 3, 0);	    
	    GridPane.setConstraints(bruto, 0, 1);
	    GridPane.setConstraints(tara, 1, 1);
	    GridPane.setConstraints(neto, 2, 1);
	    GridPane.setConstraints(cena, 3, 1);   
	    tabelaKilaza.getChildren().addAll(brutoL,taraL,netoL,cenaL,bruto,tara,neto,cena);
	    int gajbeUlaz = uu.getGajbe();
	    int gajbeIzlaz;
	    if(uu.getUnosGajbi() == null) {
	    	gajbeIzlaz = gajbeUlaz;
	    }else {
	    	gajbeIzlaz= uu.getUnosGajbi().getGajbeIzlaz();
	    }
	    Label jedinicamere = new Label("jedinica mere: kg");
	    jedinicamere.setFont(mali);
	    Label ukupnoKg1 = new Label("Ukupno " + uu.getUlaz().getNaziv());
	    Label ukupnoKg2 = new Label("na dan " + Print.getInstance().getDateFormater2().format( uu.getDatum()) + " : " + Print.getInstance().getFormatter().format(uu.getProizvodjac().ukupnoUlazaNaDan(uu.getUlaz(),uu.getDatum())) + "kg");
	    ukupnoKg1.setFont(mali);
	    ukupnoKg2.setFont(mali);
	    ukupnoKg1.setPadding(new Insets(3, 0, 0, 0));
	    Label lajnaaa = new Label("-------------------------------------------");
	    lajnaaa.setPadding(new Insets(0, 0, 0, 0));
	    proizvodVB.getChildren().addAll(proizvodHB,jedinicamere,tabelaKilaza,ukupnoKg1,ukupnoKg2,lajnaaa);
	    
	    VBox gajbeVB = new VBox(-5);
	    gajbeVB.setAlignment(Pos.TOP_LEFT);
	    gajbeVB.setPadding(new Insets(0, 0, 0, 0));
	    Label gajba0 = new Label("List ambalaže br. __");
	    if(uu.getUnosGajbi() != null) {
	    	gajba0 = new Label("List ambalaže br. " + String.valueOf(uu.getUnosGajbi().getSifra()));
	    }
	   
	    Label gajbe2 = new Label("Ulaz/izlaz: " + String.valueOf(gajbeUlaz) + "/" + String.valueOf(gajbeIzlaz));
	    Label gajbe1 = new Label("Ambalaža: ");
	    Label gajbe3 = new Label("(" + uu.getGajba().getNaziv() +" "+ String.valueOf(uu.getGajba().getTezina()) + "kg)");
	    gajbe1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
	    HBox gajbeNaslovHB = new HBox();
	    gajbeNaslovHB.setAlignment(Pos.BASELINE_LEFT);
	    gajbeNaslovHB.getChildren().addAll(gajbe1,gajbe3);	    
	    double p = uu.getKolicinaNeto()/uu.getGajbe();
	    DecimalFormat numberFormat2 = new DecimalFormat("#.00");
	    Label prosek = new Label("Prosečna težina: " +  numberFormat2.format(p));
	    Label ukupnoZaduzenih = new Label("Ukupno zaduženio: " + String.valueOf(Firma.getInstance().getTrenutnaGodina().gajbiKodProizvodjaca(uu.getProizvodjac())));
	    gajbeVB.getChildren().addAll(gajbeNaslovHB,gajba0,gajbe2,ukupnoZaduzenih,prosek);
	    
	    VBox napomenaVB = new VBox(-5);
	    Label lajna5 = new Label("-------------------------------------------");
	    Text napomena = new Text("NAPOMENA: Konačna cena će biti doneta do 31.10 tekuće godine u skladu sa kretanjem roda i cena na evropskom tržištu. Isplata će biti obavljena do 31.12. tekuće godine na tekući račun kooperanta. Na otkupnim (sabirnim) mestima će biti istaknuti normativi o kvalitetu proizvoda.");
	    napomena.setWrappingWidth(200);
	    napomena.setLineSpacing(-3);
	    napomena.setTextAlignment(TextAlignment.JUSTIFY);
	    napomena.setFont(new Font(7));
	    Label lajna7 = new Label("-------------------------------------------"); 
	    napomenaVB.getChildren().addAll(lajna5,napomena,lajna7);
	    
	    VBox potpisiVB = new VBox(-5);
	    potpisiVB.setAlignment(Pos.TOP_LEFT);
	    Label potpisProizvodjaca = new Label("Proizvođač: _________________________");
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
	    
	    glavniVB.getChildren().addAll(datumibrListaVB,podaciProizvodjacaVB,proizvodVB,gajbeVB,napomenaVB,potpisiVB);

	    try {
	    	printNode(glavniVB);
			printNode(glavniVB);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

		}
	}
 	

}
