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
import javafx.scene.text.Text;
import model.Firma;
import model.Proizvodjac;

public class PrintUgovor {
	private static PrintUgovor instance;
	private VBox glavniVB;
	private VBox glavni2VB;
	private  ArrayList<Node> strane = new ArrayList<Node>();
	
	
	public static PrintUgovor getInstance() {
		if(instance == null) {
			instance = new PrintUgovor();
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
	 
	 public void stampajUgovor (Proizvodjac p) {
		 strane.clear();
		 
		 Font mali = new Font(12);
		 Font srednji = new Font(18);
		 //Font veliki = new Font(30); 
		 Font boldSrednji = Font.font("Verdana", FontWeight.BOLD, 18);
		 Font boldMali = Font.font("Verdana", FontWeight.BOLD, 12);
		 glavniVB = new VBox(0);		 	
		 glavniVB.setAlignment(Pos.TOP_LEFT);
		 glavniVB.setPadding(new Insets(15, 15, 15, 15));
		 glavniVB.getChildren().add(Print.getInstance().zaglavlje());
		 
		 VBox naslovVB = new VBox();
		 naslovVB.setMaxWidth(525);
		 naslovVB.setAlignment(Pos.CENTER);
		 Label l1 = new Label("UGOVOR br. " + p.getSifra() + "/" + Firma.getInstance().getTrenutnaGodina().getGodina());
		 l1.setFont(boldSrednji);
		 Label l2 = new Label("O KOOPERACIJI-PROIZVODNjI I OTKUPU");
		 l2.setFont(boldSrednji);
		 Label razmak = new Label(" ");
		 naslovVB.getChildren().addAll(l1,l2,razmak);
		 glavniVB.getChildren().add(naslovVB);
		 
		 VBox podaciVB = new VBox();
		 DateFormat dateFormat = new SimpleDateFormat("/yyyy");
		 Calendar cal = Calendar.getInstance();
		 Label l3 = new Label("Zaključen dana ____ /____ " + String.valueOf(dateFormat.format(cal.getTime())) + " između:");
		 Label l4 = new Label("     1. Preduzeća: \"" + Firma.getInstance().getIme() + "\" d.o.o. " + Firma.getInstance().getAdresa());
		 l4.setFont(boldMali);
		 Label l5 = new Label("           (u daljem tekstu kupac) koga zastupa direktor firme " + Firma.getInstance().getDirektor());
		 Label l6 = new Label("     2. Kooperanta: " + p.getIme() + " " + p.getPrezime());
		 l6.setFont(boldMali);
		 Label l7 = new Label("           šifra: " + p.getSifra() + " JMBG: " + p.getMaticniBroj() + " adresa: " + p.getMesto() + " tel: "  + p.getTelefon());
		 podaciVB.getChildren().addAll(l3,l4,l5,l6,l7);
		 glavniVB.getChildren().add(podaciVB);
		 
		 VBox predmetUgVB = new VBox();
		 predmetUgVB.setMaxWidth(525);
		 predmetUgVB.setAlignment(Pos.CENTER);
		 razmak = new Label(" ");
		 Label l8 = new Label("PREDMET UGOVORA: PROIZVODNjA _____________");
		 l8.setFont(boldMali);
		 Label l9 = new Label("Na površini od: ______ ari Područje zasada: " + p.getMesto());
		 predmetUgVB.getChildren().addAll(razmak,l8,l9);
		 glavniVB.getChildren().add(predmetUgVB);
		 
		 double a = -5;
		 
		 VBox obavezeKupcaVB = new VBox(-5);
		 razmak = new Label(" ");
		 Label l10 = new Label("Obaveze kupca: ");
		 l10.setFont(boldMali);
		 Text t1 = new Text("    - Da otkupi celokupnu proizvedenu količinu maline za koju je potpisan Ugovor, pod uslovom da kvalitet odgovara standardima koje propisuje kupac.");
		 t1.setWrappingWidth(525);t1.setLineSpacing(a);
		 Text t2 = new Text("    - Da poljoprivrednom proizvođaču obezbedi i isporuči odgovarajući repromaterijal kao i drugu robu (veštačka đubriva, sredstva za zaštitu maline.");
		 t2.setWrappingWidth(525);t2.setLineSpacing(a);
		 Text t3 = new Text("    - Da pre početka berbe upozna i vidno istakne akontne cene po kojima će otkupljivati poljoprivredne proizvode.");
		 t3.setWrappingWidth(525);t3.setLineSpacing(a);
		 Text t4 = new Text("    - Da obezbedi dovoljne količine potrebne ambalaže u kojoj je koperant isključivo dužan da bere i predaje proizvod u skladu sa upustvom za berbu.");
		 t4.setWrappingWidth(525);t4.setLineSpacing(a);
		 Text t5 = new Text("    - Da izda precizna uputstva o kvalitetu i specifikacije po kojima se vrši klasiranje maline.");
		 t5.setWrappingWidth(525);t5.setLineSpacing(a);
		 obavezeKupcaVB.getChildren().addAll(razmak,l10,t1,t2,t3,t4,t5);
		 glavniVB.getChildren().add(obavezeKupcaVB);
		 
		 VBox obavezeKoperantaVB = new VBox(-5);
		 razmak = new Label(" ");
		 l10 = new Label("Obaveze kooperanta: ");
		 l10.setFont(boldMali);
		 t1 = new Text("    - Da svu proizvedenu količinu preda kupcu po tada važećim tržišnim cenama.");
		 t1.setWrappingWidth(525);t1.setLineSpacing(a);
		 t2 = new Text("    - Da ukoliko ne preuzme odgovarajući repromaterijal (veštačka đubriva, sredstva za zaštitu) od kupca, izvrši prijavljivanje gde je i u kojoj količini nabavio i upotrebio ista uz obavezno dostavljanje uverenja o kvalitetu.");
		 t2.setWrappingWidth(525);t2.setLineSpacing(a);
		 t3 = new Text("    - ZBOG IZRAŽENOG INFLATORNOG KURSA, CENE VEŠTAČKIH ĐUBRIVA BIĆE VEZANE ZA EURO PO ODLUCI FIRME KOJA ĆE VAM BITI DOSTAVLjENA U USMENOJ ILI PISMENOJ FORMI, A HEMIJSKA SREDSTVA I OSTALI REPROMATERIJAL PO SREDNjEM KURSU NA DAN PREUZIMANjA ");
		 t3.setWrappingWidth(525);t3.setLineSpacing(a);
		 t4 = new Text("    - Da izvrši analizu zemljišta na kome je podigao zasad (hemijski sastav, teški metali) i kopiju analize dostavi kupcu.");
		 t4.setWrappingWidth(525);t4.setLineSpacing(a);
		 t5 = new Text("    - Da vodi kompletnu evidenciju o svojim radovima u DNEVNIKU PRIMARNE PROIZVODNjE koji će dobiti od kupca (zaštitu maline da vrši isključivo po uputstvu kupca, da evidentira izvršeno đubrenje zasada , da ukoliko navodnjava zasad mora izvršiti hemijsku i mikrobiološku analizu vode).");
		 t5.setWrappingWidth(525);t5.setLineSpacing(a);
		 Text t6 = new Text("    - Da se strogo pridržava UPUTSTVA ZA BERBU I UPUTSTVA ZA TRANSPORT koje će dobiti od kupca.");
		 t6.setWrappingWidth(525);t5.setLineSpacing(a);
		 obavezeKoperantaVB.getChildren().addAll(razmak,l10,t1,t2,t3,t4,t5,t6);
		 glavniVB.getChildren().add(obavezeKoperantaVB);
		 
		 VBox krajVB = new VBox(-5);
		 razmak = new Label(" ");
		 t1 = new Text("      Ovaj ugovor se zaključuje za sezonu otkupa " + Firma.getInstance().getTrenutnaGodina().getGodina() + ". godine i zajedno sa otpremnicama, magacinskim izlazima i priznanicama predstavlja verodostojnu ispravu u smislu člana 36. zakona o izvršnom postupku (\"SL. glasnik RS\", broj 125/2004) sa dospelošću potraživanja na strani kupca pa se u slučaju neispunjenja ugovornih obaveza od strane proizvođača ima pokrenuti izvršni postupak.");
		 t1.setWrappingWidth(525);t1.setLineSpacing(a);
		 t2 = new Text("      Eventualni sporovi rešavaće se sporazumno, a u protivnom nadležan je Osnovni sud u Požegi, sudska jedinica u Arilju.");
		 t2.setWrappingWidth(525);t2.setLineSpacing(a);
		 t3 = new Text("      Ovaj Ugovor sačinjen je u 2 (dva) istovetna primerka, od koji se jedan nalazi kod kooperanta, a drugi kod kupca.");
		 t3.setWrappingWidth(525);t3.setLineSpacing(a);
		 Label razmak2 = new Label(" ");
		 Label razmak3 = new Label("  "); 
		 l1 = new Label("\"" + Firma.getInstance().getIme() + "\"" + Firma.getInstance().getAdresa() + "                                 " + "KOOPERANT");
		 l2 = new Label("_______________________________                             ____________________________ L.K. broj ________________");
		 Label razmak4 = new Label(" ");
		 krajVB.getChildren().addAll(razmak,t1,t2,t3,razmak2,razmak3,l1,razmak4,l2);
		 glavniVB.getChildren().add(krajVB);
		 		 
		 //strane.add(glavniVB);
		 
		 glavni2VB = new VBox(0);		 	
		 glavni2VB.setAlignment(Pos.TOP_LEFT);
		 glavni2VB.setPadding(new Insets(15, 15, 15, 15));
		 glavni2VB.getChildren().add(Print.getInstance().zaglavlje());

		 VBox naslov2Vb = new VBox();
		 naslov2Vb.setMaxWidth(525);
		 naslov2Vb.setAlignment(Pos.CENTER);
		 razmak = new Label(" ");
		 l1 = new Label("MERE ZA SPREČAVANJE PRISUSTRVA NOROVIRUS-a");
		 l1.setFont(boldMali);
		 l2 = new Label("U PROIZVODIMA OD SMRZNUTOG VOĆA");
		 l2.setFont(boldMali);
		 razmak2 = new Label(" ");
		 naslov2Vb.getChildren().addAll(razmak, l1,l2, razmak2);
		 glavni2VB.getChildren().add(naslov2Vb);
		 
		 VBox mereVB = new VBox();
		 t1 = new Text("Poslednjih godina, u zamrznutom voću, pogotovu malini, pronađen je nosilac NOROVIRUSA što je rezultovalo oboljevanjem (teški oblici dijareje i povraćanje) nekoliko stotina ljudi koji su konzumirali kontaminirano voće.");
		 t1.setWrappingWidth(525);t1.setLineSpacing(a);
		 t2 = new Text("Izvori kontaminacije su:");
		 t2.setWrappingWidth(525);t2.setLineSpacing(a);
		 t3 = new Text("    1. Zaražena voda za navodnjavanje.");
		 t3.setWrappingWidth(525);t3.setLineSpacing(a);
		 t4 = new Text("    2. Osobe u direktnom kontaktu sa voćem, putem prenosa telesnih izlučevina.");
		 t4.setWrappingWidth(525);t4.setLineSpacing(a);
		 t5 = new Text("    3. Osooblje koje se oporavilo od bolesti, ali je i dalje nosilac.");
		 t5.setWrappingWidth(525);t5.setLineSpacing(a);
		 razmak3 = new Label(" ");
		 t6 = new Text("Primena sledećih procedura je obavezna za sve proizvođače zamrznutog voća:");
		 t6.setWrappingWidth(525);t6.setLineSpacing(a);
		 Text t7 = new Text("    1. Voda za navodnjavanje mora biti kvalitetom usklađena sa domaćim zakonodavstvom i regulativama EU (council Directive EC 98/83 EC)");
		 t7.setWrappingWidth(525);t7.setLineSpacing(a);
		 Text t8 = new Text("    2. Kontrola na terenu:");
		 t8.setWrappingWidth(525);t8.setLineSpacing(a);
		 Text t9 = new Text("        - Upotrebljavati pisane instrukcije za radnu hihijenu namenjene farmerima i radnicima u polju");
		 t9.setWrappingWidth(525);t9.setLineSpacing(a);
		 Text t10 = new Text("        - Organizovati obuku farmerima i radnicima u polju i radnoj higijeni, obukom obuhvatiti i objašnjenje pisanih procedura");
		 t10.setWrappingWidth(525);t10.setLineSpacing(a);
		 Text t11 = new Text("        - Radnici koji imaju stomačne tegobe ne smeju imati pristup u zasad voća");
		 t11.setWrappingWidth(525);t11.setLineSpacing(a);
		 Text t12 = new Text("        - Za sve radnike je obavezno pranje ruku pre ulaska u voćnjak, posle svake pauze i upotrebe toaleta");
		 t12.setWrappingWidth(525);t12.setLineSpacing(a);
		 mereVB.getChildren().addAll(t1,t2,t3,t4,t5,razmak3,t6,t7,t8,t9,t10,t11,t12);
		 glavni2VB.getChildren().add(mereVB);
		 
		 VBox naslov3Vb = new VBox();
		 naslov3Vb.setMaxWidth(525);
		 naslov3Vb.setAlignment(Pos.CENTER);
		 razmak = new Label(" ");
		 l1 = new Label("UPUSTVO O HIGIJENI BERBE VOĆA");
		 l1.setFont(boldMali);
		 razmak2 = new Label(" ");
		 naslov3Vb.getChildren().addAll(razmak, l1, razmak2);
		 glavni2VB.getChildren().add(naslov3Vb);
		 
		 VBox upustvoVB = new VBox();
		 t1 = new Text("Radnici koji učestvuju u berbi voća imaju obavezu da se pridržavaju sledećih pravila iz oblasti lične higijene: ");
		 t1.setWrappingWidth(525);t1.setLineSpacing(a);
		 t2 = new Text("    - Obavezno je pre početka branja oprati ruke, kao i nakon svakog prekida branja ili posle posete toaleta.");
		 t2.setWrappingWidth(525);t2.setLineSpacing(a);
		 t3 = new Text("    - Radnici sa otvorenim ranama (posekotinama, ekcemima i gljivičnim oboljenjima) kao i sa stomačnim tegobama ne smeju učestvovati u branju.");
		 t3.setWrappingWidth(525);t3.setLineSpacing(a);
		 razmak4 = new Label(" ");
		 t4 = new Text("Gore navedene predostrožnosti su neophodne radi sprečavanja uslova za razvoj i razmnožavanje virusa i bakterija koje mogu dovesti do štetnih posledica po zdravlje ljudi.");
		 t4.setWrappingWidth(525);t4.setLineSpacing(a);
		 t5 = new Text("    - Gajbe moraju biti potpuno čiste.");
		 t5.setWrappingWidth(525);t5.setLineSpacing(a);
		 razmak3 = new Label(" ");
		 t6 = new Text("    - U gajbama ne sme biti nikakvih stranih primesa (lišće, trava, zemlja, peteljke).");
		 t6.setWrappingWidth(525);t6.setLineSpacing(a);
		 t7 = new Text("    - U gajbi ne sme biti buđavih, prezrelih, nedozrelih plodova kao i plodova prikupljenih sa zemlje.");
		 t7.setWrappingWidth(525);t7.setLineSpacing(a);
		 Text t71 = new Text("    -Prosečna neto težina gajbe ne sme biti veća od 1,8 kg.");
		 t71.setWrappingWidth(525);t7.setLineSpacing(a);
		 Text t72 = new Text("    -Gajba sa plodovima se ne sme držati direktno na zemlji već se mora obezbediti stalak ili prazna gajba ispod (u koju se potom ne beru plodovi). ");
		 t72.setWrappingWidth(525);t7.setLineSpacing(a);

		 razmak = new Label(" ");
		 t8 = new Text("Prilikom transporta voditi računa da prevozna sredstva: traktorska prikolica, traktorska korpa ili automobil budu čisti. Pre isporuke obavezno očistiti vozilo. Ne prevoziti đubrivo, slamu, seno, stajnjak kako bi se izbeglo zagađenje voća. ");
		 t8.setWrappingWidth(525);t8.setLineSpacing(a);
		 razmak2 = new Label(" ");
		 t9 = new Text("UPOZNAT SA UPUSTVIMA I PRIHVATIO SVA NAVEDENA PRAVILA: ");
		 t9.setWrappingWidth(525);t9.setLineSpacing(a);
		 razmak3 = new Label(" ");
		 l1 = new Label("Proizvođač: ________________________");
		 upustvoVB.getChildren().addAll(t1,t2,t3,t4,t5,t6,t7,t71,t72,t8,razmak2, t9, razmak3,l1 );
		 glavni2VB.getChildren().add(upustvoVB);
		 
		 strane.add(glavniVB);
		 strane.add(glavni2VB);
		 
		 try {
			   	
			 printNode(strane);
			   	printNode(strane);
			   	
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

			}
		 
		 
		
	 }
}
