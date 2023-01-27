package Podesavanja_Tab;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Firma;

public class PodesavanjaTab extends VBox {
	
	private static PodesavanjaTab instance;
	 
	private VBox podesavanjaVb;
	
	private TextField tfIme;
	private TextField tfAdresa;
	private TextField tfRacun;
	private TextField tfPib;
	private TextField tfMaticniBr;
	private TextField tftelefon1;
	private TextField tftelefon2;
	private TextField tfRegBr;
	private TextField tfpzr;
	private TextField tfpzg; 
	private TextField tfDirektor;
	private TextField tfPecat;
	private TextField tfPecatScale;
	
	private Button BIzmeni;
	private Button BSacuvaj;
	private Button BOdustani;
	private ColorPicker cpGornjaBoja;
	private ColorPicker cpDonjaBoja;
	
	private HBox posPrinterHB;
	private ComboBox<String> cBPosPrinter;
	
	private HBox A4PrinterHB;
	private ComboBox<String> cbA4Printer;
	
	private PodesavanjaTab() {
		 
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		Label naslov = new Label("Podešavanja:");
		naslov.setFont(new Font(35));
		this.getChildren().add(naslov);
		
		dodavanjePolja();
		trakaKomandi();
		
	}
		
	private void dodavanjePolja() {
		
		podesavanjaVb = new VBox(10);
		podesavanjaVb.setAlignment(Pos.BASELINE_LEFT);
		
		HBox imeHB = new HBox(10);
		Label ime = new Label("ime preduzeća: ");
		ime.setPrefWidth(110);
		tfIme = new TextField();
		tfIme.setMinWidth(220);
		imeHB.getChildren().addAll(ime,tfIme);
		
		HBox adresaHB = new HBox(10);
		Label adresa = new Label("adresa: ");
		adresa.setPrefWidth(110);
		tfAdresa = new TextField();
		tfAdresa.setMinWidth(220);
		adresaHB.getChildren().addAll(adresa,tfAdresa);
		
		HBox  tekuciRacunHB = new HBox(10);
		Label tekuciRacun = new Label("tekući račun: ");
		tekuciRacun.setPrefWidth(110);
		tfRacun = new TextField();
		tfRacun.setMinWidth(220);
		tekuciRacunHB.getChildren().addAll(tekuciRacun,tfRacun);
		
		HBox pivHB = new HBox(10);
		Label pib = new Label("PIB: ");
		pib.setPrefWidth(110);
		tfPib = new TextField();
		tfPib.setMinWidth(100);
		pivHB.getChildren().addAll(pib,tfPib);
		
		HBox maticniHB = new HBox(10);
		Label maticni = new Label("matični broj: ");
		maticni.setPrefWidth(110);
		tfMaticniBr = new TextField();
		tfMaticniBr.setMinWidth(100);
		maticniHB.getChildren().addAll(maticni,tfMaticniBr);
		
		HBox tel1Hb = new HBox(10);
		Label tel1 = new Label("telefon 1: ");
		tel1.setPrefWidth(110);
		tftelefon1 = new TextField();
		tftelefon1.setMinWidth(100);
		tel1Hb.getChildren().addAll(tel1,tftelefon1);
		
		HBox tel2HB = new HBox(10);
		Label tel2 = new Label("telefon 2: ");
		tel2.setPrefWidth(110);
		tftelefon2 = new TextField();
		tftelefon2.setMinWidth(100);
		tel2HB.getChildren().addAll(tel2,tftelefon2);
		
		HBox regBrHB = new HBox(10);
		Label regBr = new Label("registarski broj:");
		regBr.setPrefWidth(110);
		tfRegBr = new TextField();
		tfRegBr.setMinWidth(100);
		regBrHB.getChildren().addAll(regBr,tfRegBr);
		
		HBox szrHB = new HBox(10);
		Label pzr = new Label("saradnik za robu: ");
		pzr.setPrefWidth(110);
		tfpzr = new TextField();
		tfpzr.setMinWidth(220);
		szrHB.getChildren().addAll(pzr,tfpzr);
		
		HBox pzgHB = new HBox(10);
		Label pzg = new Label("saradnik za gorivo: ");
		pzg.setPrefWidth(110);
		tfpzg = new TextField();
		tfpzg.setMinWidth(220);
		pzgHB.getChildren().addAll(pzg,tfpzg);
		
		HBox direktorHB = new HBox(10);
		Label direktor = new Label("direktor: ");
		direktor.setPrefWidth(110);
		tfDirektor = new TextField();
		tfDirektor.setMinWidth(100);
		direktorHB.getChildren().addAll(direktor,tfDirektor);
		
		HBox pecatHB = new HBox(10);
		Label pecat = new Label("pečat (putanja): ");
		pecat.setPrefWidth(110);
		tfPecat = new TextField();
		tfPecat.setMinWidth(220);
		pecatHB.getChildren().addAll(pecat,tfPecat);

		HBox pecatScaleHB = new HBox(10);
		Label pecatScale = new Label("pečat (skaliranje)");
		pecatScale.setPrefWidth(110);
		tfPecatScale = new TextField();
		tfPecatScale.setMinWidth(100);
		pecatScaleHB.getChildren().addAll(pecatScale,tfPecatScale);

		
		HBox bojaGoreHB = new HBox(10);
		bojaGoreHB.setMinHeight(25);
		Label bojaGore = new Label("boja pozadine 1: ");
		bojaGore.setPrefWidth(110);
		cpGornjaBoja = new ColorPicker();
		cpGornjaBoja.setPrefWidth(150);
		bojaGoreHB.getChildren().addAll(bojaGore,cpGornjaBoja);
		
		HBox bojaDoleHB = new HBox(10);
		bojaDoleHB.setMinHeight(25);
		Label bojaDole = new Label("boja pozadine 2: ");
		bojaDole.setPrefWidth(110);
		cpDonjaBoja = new ColorPicker();
		cpDonjaBoja.setPrefWidth(150);
		bojaDoleHB.getChildren().addAll(bojaDole,cpDonjaBoja);
		
		posPrinterHB = new HBox(10);
		Label pos = new Label("POS štampač: ");
		pos.setPrefWidth(110);
		cBPosPrinter = new ComboBox<String>();
		posPrinterHB.getChildren().addAll(pos,cBPosPrinter);
		updateCBPOSPrinter();
		
		A4PrinterHB = new HBox(10);
		Label a4 = new Label("A4 štampač: ");
		a4.setPrefWidth(110);
		cbA4Printer = new ComboBox<String>();
		A4PrinterHB.getChildren().addAll(a4,cbA4Printer);
		updateCBA4Printer();
		
		
		podesavanjaVb.getChildren().addAll(imeHB,adresaHB,tekuciRacunHB,pivHB,maticniHB,tel1Hb,tel2HB,regBrHB,szrHB,pzgHB,direktorHB,pecatHB,pecatScaleHB,bojaGoreHB,bojaDoleHB,posPrinterHB, A4PrinterHB);
		podesavanjaVb.setDisable(true);
		
		popuniPolja();
		
		this.getChildren().addAll(podesavanjaVb);
		
	}
	
	public void updateCBPOSPrinter() {
		posPrinterHB.getChildren().remove(cBPosPrinter);
		cBPosPrinter = new ComboBox<String>();
		cBPosPrinter.setPrefWidth(220);
		posPrinterHB.getChildren().add(cBPosPrinter);
		ArrayList<String> stampaci = new ArrayList<String>();
		for (Printer p : Printer.getAllPrinters()) {
			stampaci.add(p.getName());
		}
		cBPosPrinter.getItems().addAll(stampaci);	
		cBPosPrinter.setValue(Firma.getInstance().getPosStampacNaziv());
	}
	
	public void updateCBA4Printer() {
		A4PrinterHB.getChildren().remove(cbA4Printer);
		cbA4Printer = new ComboBox<String>();
		cbA4Printer.setPrefWidth(220);
		A4PrinterHB.getChildren().add(cbA4Printer);
		ArrayList<String> stampaci = new ArrayList<String>();
		for (Printer p : Printer.getAllPrinters()) {
			stampaci.add(p.getName());
		}
		cbA4Printer.getItems().addAll(stampaci);
		cbA4Printer.setValue(Firma.getInstance().getA4StampacNaziv());

	}
	
	private void trakaKomandi() {
		
		HBox komandeHB = new HBox(10);
		komandeHB.setAlignment(Pos.BASELINE_LEFT);
		
		BSacuvaj = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj.setGraphic(save);
		BSacuvaj.setOnAction(new SacuvajPodesavanjaKontroler());
		BSacuvaj.setDisable(true);
		
		BIzmeni = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setDisable(false);
		BIzmeni.setOnAction(new IzmeniPodesavanjaKontroler());
		
		BOdustani = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BOdustani.setGraphic(cancel);
		BOdustani.setOnAction(new OdustaniPodesavanjaKOntroler());
		BOdustani.setDisable(true);
		
		komandeHB.getChildren().addAll(BIzmeni,BOdustani,BSacuvaj);
		this.getChildren().add(komandeHB);
	}
	
	public void popuniPolja() {
		
		tfIme.setText(Firma.getInstance().getIme());
		tfAdresa.setText(Firma.getInstance().getAdresa());
		tfRacun.setText(Firma.getInstance().getTekuciRacun());
		tfPib.setText(Firma.getInstance().getPib());
		tfMaticniBr.setText(Firma.getInstance().getMaticniBr());
		tftelefon1.setText(Firma.getInstance().getTelefon1());
		tftelefon2.setText(Firma.getInstance().getTelefon2());
		tfRegBr.setText(Firma.getInstance().getRegBroj());
		tfpzr.setText(Firma.getInstance().getProdavnicaZaRobu());
		tfpzg.setText(Firma.getInstance().getProdavnicaZaGorivo());
		tfDirektor.setText(Firma.getInstance().getDirektor());
		tfPecat.setText(Firma.getInstance().getPecatLink());
		Color gornja = Color.web(Firma.getInstance().getBojaGore());
		cpGornjaBoja.setValue(gornja);
		Color donja = Color.web(Firma.getInstance().getBojaDole());
		cpDonjaBoja.setValue(donja);
		cBPosPrinter.setValue(Firma.getInstance().getPosStampacNaziv());
		cbA4Printer.setValue(Firma.getInstance().getA4StampacNaziv());
		tfPecatScale.setText(String.valueOf(Firma.getInstance().getPecatScale()));
		
	}
	
	public static PodesavanjaTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new PodesavanjaTab();			
		}
		return instance;
	}

	public VBox getPodesavanjaVb() {
		return podesavanjaVb;
	}

	public void setPodesavanjaVb(VBox podesavanjaVb) {
		this.podesavanjaVb = podesavanjaVb;
	}

	public TextField getTfIme() {
		return tfIme;
	}

	public void setTfIme(TextField tfIme) {
		this.tfIme = tfIme;
	}

	public TextField getTfAdresa() {
		return tfAdresa;
	}

	public void setTfAdresa(TextField tfAdresa) {
		this.tfAdresa = tfAdresa;
	}

	public TextField getTfRacun() {
		return tfRacun;
	}

	public void setTfRacun(TextField tfRacun) {
		this.tfRacun = tfRacun;
	}

	public TextField getTfPib() {
		return tfPib;
	}
	
	public HBox getA4PrinterHB() {
		return A4PrinterHB;
	}

	public void setA4PrinterHB(HBox a4PrinterHB) {
		A4PrinterHB = a4PrinterHB;
	}

	public ComboBox<String> getCbA4Printer() {
		return cbA4Printer;
	}

	public void setCbA4Printer(ComboBox<String> cbA4Printer) {
		this.cbA4Printer = cbA4Printer;
	}

	public void setTfPib(TextField tfPib) {
		this.tfPib = tfPib;
	}
	
	public ColorPicker getCpGornjaBoja() {
		return cpGornjaBoja;
	}

	public void setCpGornjaBoja(ColorPicker cpGornjaBoja) {
		this.cpGornjaBoja = cpGornjaBoja;
	}

	public ColorPicker getCpDonjaBoja() {
		return cpDonjaBoja;
	}

	public void setCpDonjaBoja(ColorPicker cpDonjaBoja) {
		this.cpDonjaBoja = cpDonjaBoja;
	}

	public TextField getTfMaticniBr() {
		return tfMaticniBr;
	}

	public void setTfMaticniBr(TextField tfMaticniBr) {
		this.tfMaticniBr = tfMaticniBr;
	}

	public TextField getTftelefon1() {
		return tftelefon1;
	}

	public HBox getPosPrinterHB() {
		return posPrinterHB;
	}

	public void setPosPrinterHB(HBox posPrinterHB) {
		this.posPrinterHB = posPrinterHB;
	}

	public ComboBox<String> getcBPosPrinter() {
		return cBPosPrinter;
	}

	public void setcBPosPrinter(ComboBox<String> cBPosPrinter) {
		this.cBPosPrinter = cBPosPrinter;
	}

	public void setTftelefon1(TextField tftelefon1) {
		this.tftelefon1 = tftelefon1;
	}

	public TextField getTftelefon2() {
		return tftelefon2;
	}

	public void setTftelefon2(TextField tftelefon2) {
		this.tftelefon2 = tftelefon2;
	}

	public TextField getTfRegBr() {
		return tfRegBr;
	}

	public void setTfRegBr(TextField tfRegBr) {
		this.tfRegBr = tfRegBr;
	}

	public TextField getTfpzr() {
		return tfpzr;
	}

	public void setTfpzr(TextField tfpzr) {
		this.tfpzr = tfpzr;
	}

	public TextField getTfpzg() {
		return tfpzg;
	}

	public void setTfpzg(TextField tfpzg) {
		this.tfpzg = tfpzg;
	}

	public TextField getTfDirektor() {
		return tfDirektor;
	}

	public void setTfDirektor(TextField tfDirektor) {
		this.tfDirektor = tfDirektor;
	}

	public TextField getTfPecat() {
		return tfPecat;
	}

	public void setTfPecat(TextField tfPecat) {
		this.tfPecat = tfPecat;
	}

	public Button getBIzmeni() {
		return BIzmeni;
	}

	public void setBIzmeni(Button bIzmeni) {
		BIzmeni = bIzmeni;
	}

	public Button getBSacuvaj() {
		return BSacuvaj;
	}

	public void setBSacuvaj(Button bSacuvaj) {
		BSacuvaj = bSacuvaj;
	}

	public Button getBOdustani() {
		return BOdustani;
	}

	public void setBOdustani(Button bOdustani) {
		BOdustani = bOdustani;
	}

	public static void setInstance(PodesavanjaTab instance) {
		PodesavanjaTab.instance = instance;
	}

	public TextField getTfPecatScale() { return tfPecatScale; }
}
