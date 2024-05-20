package Podesavanja_Tab;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.Firma;

public class PodesavanjaTab extends VBox {
	
	private static PodesavanjaTab instance;

	private HBox unosHb;

	private GridPane podesavanjaGp;
	private Label lime;
	private TextField tfIme;
	private Label ladresa;
	private TextField tfAdresa;
	private Label lbrracuna;
	private TextField tfRacun;
	private Label lpib;
	private TextField tfPib;
	private Label lmaticnibr;
	private TextField tfMaticniBr;
	private Label ltelefon1;
	private TextField tftelefon1;
	private Label ltelefon2;
	private TextField tftelefon2;
	private Label lregbr;
	private TextField tfRegBr;
	private Label lpzr;
	private TextField tfpzr;
	private Label lpzg;
	private TextField tfpzg;
	private Label ldirektor;
	private TextField tfDirektor;
	private Label lpecat;
	private TextField tfPecat;
	private Label lpecatskaliranje;
	private TextField tfPecatScale;
	private  Label lbojagore;
	private ColorPicker cpGornjaBoja;
	private Label lbojadole;
	private ColorPicker cpDonjaBoja;
	private ComboBox<String> cBPosPrinter;
	private ComboBox<String> cbA4Printer;
	private  Label lposprinter;
	private  Label la4printer;

	private Button BIzmeni;
	private Button BSacuvaj;
	private Button BOdustani;


	private PodesavanjaTab() {

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Podešavanja:");
		naslov.setFont(new Font(35));
		Separator separator = new Separator();
		this.getChildren().addAll(naslov,separator);

		//unos i tabela
		unosHb = new HBox(20);
		this.getChildren().add(unosHb);

		HBox prazno = new HBox();
		prazno.setPrefWidth(1300);

		//podesavanja grid panea
		podesavanjaGp = new GridPane();
		podesavanjaGp.setPadding(new Insets(10, 10, 10, 10));
		podesavanjaGp.setVgap(10);
		podesavanjaGp.setHgap(10);
		podesavanjaGp.setAlignment(Pos.BASELINE_LEFT);
		podesavanjaGp.setStyle("-fx-font: 20px \"Serif\";");
		podesavanjaGp.setMinWidth(690);

		unosHb.getChildren().addAll(podesavanjaGp, prazno);
		 
		unos();
		popuniPolja();

		updateCBA4Printer();
		updateCBPOSPrinter();
		
	}

	private void unos(){

		BSacuvaj = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj.setGraphic(save);
		BSacuvaj.setPrefWidth(200);
		BSacuvaj.setOnAction(new SacuvajPodesavanjaKontroler());
		BSacuvaj.setDisable(true);

		BIzmeni = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setDisable(false);
		BIzmeni.setPrefWidth(200);
		BIzmeni.setOnAction(new IzmeniPodesavanjaKontroler());

		BOdustani = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BOdustani.setGraphic(cancel);
		BOdustani.setPrefWidth(200);
		BOdustani.setOnAction(new OdustaniPodesavanjaKOntroler());
		BOdustani.setDisable(true);

		HBox a = new HBox();
		HBox b = new HBox();
		a.setPrefWidth(200);
		b.setPrefWidth(200);
		//dodavanje gornjih dugmica
		podesavanjaGp.add(BSacuvaj,0,0,1,1);
		podesavanjaGp.add(BIzmeni,1,0,1,1);
		podesavanjaGp.add(BOdustani,2,0,1,1);
		podesavanjaGp.add(a,3,0,1,1);
		podesavanjaGp.add(b,4,0,1,1);

		lime = new Label("Ime firme:");
		tfIme = new TextField();
		tfIme.setPrefWidth(620);
		podesavanjaGp.add(lime,0,2,1,1);
		podesavanjaGp.add(tfIme,1,2,3,1);

		ladresa = new Label("Adresa:");
		tfAdresa = new TextField();
		tfAdresa.setPrefWidth(620);
		podesavanjaGp.add(ladresa,0,3,1,1);
		podesavanjaGp.add(tfAdresa,1,3,3,1);

		lbrracuna = new Label("Tekući račun:");
		tfRacun = new TextField();
		tfRacun.setPrefWidth(410);
		podesavanjaGp.add(lbrracuna,0,4,1,1);
		podesavanjaGp.add(tfRacun,1,4,2,1);

		lpib = new Label("PIB:");
		tfPib = new TextField();
		tfPib.setPrefWidth(410);
		podesavanjaGp.add(lpib,0,5,1,1);
		podesavanjaGp.add(tfPib,1,5,2,1);

		lmaticnibr = new Label("Matični broj:");
		tfMaticniBr = new TextField();
		tfMaticniBr.setPrefWidth(410);
		podesavanjaGp.add(lmaticnibr,0,6,1,1);
		podesavanjaGp.add(tfMaticniBr,1,6,2,1);

		ltelefon1 = new Label("Telefon 1:");
		tftelefon1 = new TextField();
		tftelefon1.setPrefWidth(410);
		podesavanjaGp.add(ltelefon1,0,7,1,1);
		podesavanjaGp.add(tftelefon1,1,7,2,1);

		ltelefon2 = new Label("Telefon 2:");
		tftelefon2 = new TextField();
		tftelefon2.setPrefWidth(410);
		podesavanjaGp.add(ltelefon2,0,8,1,1);
		podesavanjaGp.add(tftelefon2,1,8,2,1);

		lregbr = new Label("REG br.:");
		tfRegBr = new TextField();
		tfRegBr.setPrefWidth(410);
		podesavanjaGp.add(lregbr,0,9,1,1);
		podesavanjaGp.add(tfRegBr,1,9,2,1);

		ldirektor = new Label("Direktor:");
		tfDirektor = new TextField();
		tfDirektor.setPrefWidth(410);
		podesavanjaGp.add(ldirektor,0,10,1,1);
		podesavanjaGp.add(tfDirektor,1,10,2,1);

		lpzr = new Label("Saradnk za robu:");
		tfpzr = new TextField();
		tfpzr.setPrefWidth(620);
		podesavanjaGp.add(lpzr,0,11,2,1);
		podesavanjaGp.add(tfpzr,2,11,3,1);

		lpzg = new Label("Saradnik za gorivo:");
		tfpzg = new TextField();
		tfpzg.setPrefWidth(620);
		podesavanjaGp.add(lpzg,0,12,2,1);
		podesavanjaGp.add(tfpzg,2,12,3,1);


		lpecat = new Label("Pečat (putanja do slike):");
		tfPecat = new TextField();
		tfPecat.setPrefWidth(410);
		podesavanjaGp.add(lpecat,0,13,2,1);
		podesavanjaGp.add(tfPecat,2,13,3,1);

		lpecatskaliranje = new Label("Pečat(skaliranje slike):");
		tfPecatScale = new TextField();
		tfPecatScale.setPrefWidth(200);
		podesavanjaGp.add(lpecatskaliranje,0,14,2,1);
		podesavanjaGp.add(tfPecatScale,2,14,1,1);

		lbojagore = new Label("Boja 1:");
		cpGornjaBoja = new ColorPicker();
		cpGornjaBoja.setPrefWidth(410);
		podesavanjaGp.add(lbojagore,0,15,1,1);
		podesavanjaGp.add(cpGornjaBoja,1,15,2,1);

		lbojadole = new Label("Boja 2:");
		cpDonjaBoja = new ColorPicker();
		cpDonjaBoja.setPrefWidth(410);
		podesavanjaGp.add(lbojadole,0,16,1,1);
		podesavanjaGp.add(cpDonjaBoja,1,16,2,1);

		lposprinter = new Label("POS štampač:");
		cBPosPrinter = new ComboBox<String>();
		cBPosPrinter.setPrefWidth(620);
		podesavanjaGp.add(lposprinter,0,17,1,1);
		podesavanjaGp.add(cBPosPrinter,1,17,3,1);

		la4printer = new Label("A4 štampač:");
		cbA4Printer = new ComboBox<String>();
		cbA4Printer.setPrefWidth(620);
		podesavanjaGp.add(la4printer,0,18,1,1);
		podesavanjaGp.add(cbA4Printer,1,18,3,1);

		SetEditDisable();
	}

	public void updateCBPOSPrinter() {

		cBPosPrinter.getItems().clear();
		ArrayList<String> stampaci = new ArrayList<String>();
		for (Printer p : Printer.getAllPrinters()) {
			stampaci.add(p.getName());
		}
		cBPosPrinter.getItems().addAll(stampaci);	
		cBPosPrinter.setValue(Firma.getInstance().getPosStampacNaziv());
	}
	
	public void updateCBA4Printer() {

		cbA4Printer.getItems().clear();
		ArrayList<String> stampaci = new ArrayList<String>();
		for (Printer p : Printer.getAllPrinters()) {
			stampaci.add(p.getName());
		}
		cbA4Printer.getItems().addAll(stampaci);
		cbA4Printer.setValue(Firma.getInstance().getA4StampacNaziv());

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

	public void  SetEditEnable(){

		lime.setDisable(false);
		tfIme.setDisable(false);
		ladresa.setDisable(false);
		tfAdresa.setDisable(false);
		lbrracuna.setDisable(false);
		tfRacun.setDisable(false);
		lpib.setDisable(false);
		tfPib.setDisable(false);
		lmaticnibr.setDisable(false);
		tfMaticniBr.setDisable(false);
		ltelefon1.setDisable(false);
		tftelefon1.setDisable(false);
		ltelefon2.setDisable(false);
		tftelefon2.setDisable(false);
		lregbr.setDisable(false);
		tfRegBr.setDisable(false);
		lpzr.setDisable(false);
		tfpzr.setDisable(false);
		lpzg.setDisable(false);
		tfpzg.setDisable(false);
		ldirektor.setDisable(false);
		tfDirektor.setDisable(false);
		lpecat.setDisable(false);
		tfPecat.setDisable(false);
		lpecatskaliranje.setDisable(false);
		tfPecatScale.setDisable(false);
		lbojadole.setDisable(false);
		lbojagore.setDisable(false);
		lposprinter.setDisable(false);
		la4printer.setDisable(false);
		cpDonjaBoja.setDisable(false);
		cpGornjaBoja.setDisable(false);
		cbA4Printer.setDisable(false);
		cBPosPrinter.setDisable(false);

	}

	public void  SetEditDisable() {

		lime.setDisable(true);
		tfIme.setDisable(true);
		ladresa.setDisable(true);
		tfAdresa.setDisable(true);
		lbrracuna.setDisable(true);
		tfRacun.setDisable(true);
		lpib.setDisable(true);
		tfPib.setDisable(true);
		lmaticnibr.setDisable(true);
		tfMaticniBr.setDisable(true);
		ltelefon1.setDisable(true);
		tftelefon1.setDisable(true);
		ltelefon2.setDisable(true);
		tftelefon2.setDisable(true);
		lregbr.setDisable(true);
		tfRegBr.setDisable(true);
		lpzr.setDisable(true);
		tfpzr.setDisable(true);
		lpzg.setDisable(true);
		tfpzg.setDisable(true);
		ldirektor.setDisable(true);
		tfDirektor.setDisable(true);
		lpecat.setDisable(true);
		tfPecat.setDisable(true);
		lpecatskaliranje.setDisable(true);
		tfPecatScale.setDisable(true);
		lbojadole.setDisable(true);
		lbojagore.setDisable(true);
		lposprinter.setDisable(true);
		la4printer.setDisable(true);
		cpDonjaBoja.setDisable(true);
		cpGornjaBoja.setDisable(true);
		cbA4Printer.setDisable(true);
		cBPosPrinter.setDisable(true);
	}

	public void  setColor(String gore, String dole){

		LinearGradient gradient = new LinearGradient(
				0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
				new javafx.scene.paint.Stop(0, Color.web(gore)),
				new javafx.scene.paint.Stop(1, Color.web(dole))
		);

		// Creating a BackgroundFill with the linear gradient
		BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);

		// Creating a Background with the BackgroundFill
		Background background = new Background(backgroundFill);

		this.setBackground(background);
	}
	
	public static PodesavanjaTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new PodesavanjaTab();			
		}
		return instance;
	}

	public TextField getTfIme() {
		return tfIme;
	}

	public TextField getTfAdresa() {
		return tfAdresa;
	}

	public TextField getTfRacun() {
		return tfRacun;
	}

	public TextField getTfPib() {
		return tfPib;
	}

	public ComboBox<String> getCbA4Printer() {
		return cbA4Printer;
	}

	public ColorPicker getCpGornjaBoja() {
		return cpGornjaBoja;
	}

	public ColorPicker getCpDonjaBoja() {
		return cpDonjaBoja;
	}

	public TextField getTfMaticniBr() {
		return tfMaticniBr;
	}

	public TextField getTftelefon1() {
		return tftelefon1;
	}


	public ComboBox<String> getcBPosPrinter() {
		return cBPosPrinter;
	}

	public TextField getTftelefon2() {
		return tftelefon2;
	}

	public TextField getTfRegBr() {
		return tfRegBr;
	}

	public TextField getTfpzr() {
		return tfpzr;
	}

	public TextField getTfpzg() {
		return tfpzg;
	}

	public TextField getTfDirektor() {
		return tfDirektor;
	}

	public TextField getTfPecat() {
		return tfPecat;
	}

	public Button getBIzmeni() {
		return BIzmeni;
	}

	public Button getBSacuvaj() {
		return BSacuvaj;
	}

	public Button getBOdustani() {
		return BOdustani;
	}

	public static void setInstance(PodesavanjaTab instance) {
		PodesavanjaTab.instance = instance;
	}

	public TextField getTfPecatScale() { return tfPecatScale; }
}
