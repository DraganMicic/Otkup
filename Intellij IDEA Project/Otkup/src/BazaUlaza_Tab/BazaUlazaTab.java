 package BazaUlaza_Tab;

import java.sql.Date;
import java.time.LocalDate;

import BazaUlaza_Tab.Tools_K.PonisiPretraguCenaKontroler;
import BazaUlaza_Tab.Tools_K.PonistiPretraguUlazaKKontroler;
import BazaUlaza_Tab.Tools_K.StampaCenaKontroler;
import BazaUlaza_Tab.Tools_K.StampaSpisakUlazaKontroler;
import Main.ComboBoxAutoComplete;
import BazaUlaza_Tab.Cena_ASCED.DodajCenuKontroler;
import BazaUlaza_Tab.Ulaz_ASCED.DodajUlazKontroler;
import BazaUlaza_Tab.Cena_ASCED.IzmeniCenuKontroler;
import BazaUlaza_Tab.Ulaz_ASCED.IzmeniUlazKontroler;
import BazaUlaza_Tab.Cena_ASCED.ObrisiCenuKontroler;
import BazaUlaza_Tab.Ulaz_ASCED.ObrisiUlazKontroler;
import BazaUlaza_Tab.Cena_ASCED.PonistiCenuKontroler;
import BazaUlaza_Tab.Ulaz_ASCED.PonistiUlazKontroler;
import BazaUlaza_Tab.Cena_ASCED.SacuvajCenuKontroler;
import BazaUlaza_Tab.Ulaz_ASCED.SacuvajUlazKontroler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.CenaUlaza;
import model.Firma;
import model.Ulaz;

public class BazaUlazaTab extends VBox{
	
	private static BazaUlazaTab instance;

	private final HBox unosITabelaUlazaHb;
	private final HBox unosITabelaCenaHb;

	
	private boolean unosNovogUlaza=false;
	private boolean izmenaUlazaUToku = false;
	
	private boolean unosNoveCene=false;
	private boolean izmenaCeneUToku = false;

	private final GridPane unosUlazaGp;
	private Label lsifraulaza;
	private TextField tfSifraUlaza;    ///za ulaze
	private Label lnazivulaza;
	private TextField tfNazivUlaza;
	private Label lopisulaza;
	private TextField tfOpisUlaza;

	private final GridPane unosCenaGp;
	private Button BDodajUlaz;
	private Button BSacuvajUlaz;
	private Button BPonistiUlaz;
	private Button BIzmeniUlaz;
	private Button BObrisiUlaz;

	private TableView<Ulaz> tabelaUlaza;

	private Label lsifracene ;
	private ComboBox<Ulaz> cbUlaz;  /////////////////////za cene
	private Label lulazl;
	private TextField tfSifraCene;
	private TextField tfCenaBezPrevoza;
	private TextField tfCenaSaPrevozom;
	private  Label lcena;
	private Label lcenabezprevoza;
	private Label lcenasaprevozom;
	private DatePicker dpPocetakVazenjaCene;
	private Label ldatumod;
	private DatePicker dpKrajVazenjaCene;
	private Label ldatumdo;

	private Button BDodajCenu;
	private Button BSacuvajCenu;
	private Button BPonistiCenu;
	private Button BIzmeniCenu;
	private Button BObrisiCenu;
	
	private TableView<CenaUlaza> tabelaCena;

	private HBox pretragaCenaHB;
	private TextField tfPretragaCena;
	private DatePicker dpPretragaCena;
	private Button BPonistiPretraguCena;

	private HBox stampaj;
	private RadioButton RBSveCene;
	private RadioButton RBFiltriraneCene;
	private ToggleGroup TGCenovnik;
	private Button BStampajCene;
	private RadioButton RBPredHladnjacom;
	private RadioButton RBNaOtkupnomMestu;
	private ToggleGroup TGvrstaCenovbnika;

	private HBox pretragaUlazaHB;
	private TextField tfPretragaUlaza;
	private Button BPonistiPretraguUlaza;

	private HBox spisak;
	private Button BStampajSpisakUlaza;
	private RadioButton RBSviUlaziSpisak;
	private RadioButton RBFiltriraniUlaziSpisak;
	private ToggleGroup TGSpisakUlaza;

	private BazaUlazaTab() {

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Baza ulaza:");
		naslov.setFont(new Font(35));
		Separator separator = new Separator();
		this.getChildren().addAll(naslov,separator);

		//unos i tabela Ulaza
		unosITabelaUlazaHb = new HBox(20);
		this.getChildren().add(unosITabelaUlazaHb);

		//podesavanja grid panea
		unosUlazaGp = new GridPane();
		unosUlazaGp.setPadding(new Insets(10, 10, 10, 10));
		unosUlazaGp.setVgap(10);
		unosUlazaGp.setHgap(10);
		unosUlazaGp.setAlignment(Pos.BASELINE_LEFT);
		unosUlazaGp.setStyle("-fx-font: 20px \"Serif\";");
		unosUlazaGp.setMinWidth(690);
		
		unosUlaza();
		tabelaUlaza();
		tabelaUlaza.setPrefWidth(1300);
		tabelaUlaza.setPrefHeight(350);

		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		unosITabelaUlazaHb.getChildren().addAll(unosUlazaGp,separator2,tabelaUlaza);

		//naslov 2
		Label naslov2 = new Label("Cene  ulaza:");
		naslov2.setFont(new Font(35));
		Separator separator3 = new Separator();
		this.getChildren().addAll(naslov2,separator3);

		//unos i tabela Cena
		unosITabelaCenaHb = new HBox(20);
		this.getChildren().add(unosITabelaCenaHb);

		//podesavanja grid panea za cene
		unosCenaGp = new GridPane();
		unosCenaGp.setPadding(new Insets(10, 10, 10, 10));
		unosCenaGp.setVgap(10);
		unosCenaGp.setHgap(10);
		unosCenaGp.setAlignment(Pos.BASELINE_LEFT);
		unosCenaGp.setStyle("-fx-font: 20px \"Serif\";");
		unosCenaGp.setMinWidth(690);

		unosCena();
		tabelaCena();
		tabelaCena.setPrefWidth(1300);
		tabelaCena.setPrefHeight(400);

		Separator separator4 = new Separator();
		separator4.setOrientation(Orientation.VERTICAL);
		unosITabelaCenaHb.getChildren().addAll(unosCenaGp,separator4,tabelaCena);

		//naslov3
		Label naslov3 = new Label("Alati:");
		naslov3.setFont(new Font(35));
		Separator separator1 = new Separator();
		this.getChildren().addAll(naslov3,separator1);

		//alati
		pretragaCena();
		stampaCenovnika();
		stampaSpisakUlaza();
		this.getChildren().addAll(spisak,pretragaCenaHB,stampaj);

		//pocetni update
		updateCbUlaz();

		getBDodajCenu().requestFocus();

	}

	private void unosUlaza(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodajUlaz = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajUlaz.setGraphic(add);
		BDodajUlaz.setPrefWidth(200);
		BDodajUlaz.setOnAction(new DodajUlazKontroler());

		BSacuvajUlaz = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajUlaz.setGraphic(save);
		BSacuvajUlaz.setPrefWidth(200);
		BSacuvajUlaz.setOnAction(new SacuvajUlazKontroler());
		BSacuvajUlaz.setDisable(true);

		BPonistiUlaz = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiUlaz.setGraphic(cancel);
		BPonistiUlaz.setPrefWidth(200);
		BPonistiUlaz.setOnAction(new PonistiUlazKontroler());
		BPonistiUlaz.setDisable(true);

		BIzmeniUlaz = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniUlaz.setGraphic(edit);
		BIzmeniUlaz.setDisable(true);
		BIzmeniUlaz.setPrefWidth(200);
		BIzmeniUlaz.setOnAction(new IzmeniUlazKontroler());

		BObrisiUlaz = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiUlaz.setGraphic(delete);
		BObrisiUlaz.setDisable(true);
		BObrisiUlaz.setPrefWidth(200);
		BObrisiUlaz.setOnAction(new ObrisiUlazKontroler());

		//dodavanje gornjih dugmica
		unosUlazaGp.add(BDodajUlaz,0,0,1,1);
		unosUlazaGp.add(BSacuvajUlaz,1,0,1,1);
		unosUlazaGp.add(BPonistiUlaz,2,0,1,1);
		unosUlazaGp.add(BIzmeniUlaz,3,0,1,1);
		unosUlazaGp.add(BObrisiUlaz,4,0,1,1);

		//POLJA ZA UNOS///////////////////////////////////////////////////////////////////////////////
		tfSifraUlaza = new TextField();
		tfSifraUlaza.setPrefWidth(200);

		tfNazivUlaza = new TextField();
		tfNazivUlaza.setPrefWidth(620);

		tfOpisUlaza = new TextField();
		tfOpisUlaza.setPrefWidth(620);

		lsifraulaza = new Label("Šifra:");
		unosUlazaGp.add(lsifraulaza,0,2,1,1);
		unosUlazaGp.add(tfSifraUlaza,1,2,1,1);

		lnazivulaza = new Label("Naziv:");
		unosUlazaGp.add(lnazivulaza,0,3,1,1);
		unosUlazaGp.add(tfNazivUlaza,1,3,3,1);


		lopisulaza = new Label("Opis:");
		unosUlazaGp.add(lopisulaza,0,4,1,1);
		unosUlazaGp.add(tfOpisUlaza,1,4,3,1);

		SetUnosUlazaDisable();

	}
	
	private void unosCena(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodajCenu = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajCenu.setGraphic(add);
		BDodajCenu.setPrefWidth(200);
		BDodajCenu.setOnAction(new DodajCenuKontroler());

		BSacuvajCenu = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajCenu.setGraphic(save);
		BSacuvajCenu.setPrefWidth(200);
		BSacuvajCenu.setOnAction(new SacuvajCenuKontroler());
		BSacuvajCenu.setDisable(true);

		BPonistiCenu = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiCenu.setGraphic(cancel);
		BPonistiCenu.setPrefWidth(200);
		BPonistiCenu.setOnAction(new PonistiCenuKontroler());
		BPonistiCenu.setDisable(true);

		BIzmeniCenu = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniCenu.setGraphic(edit);
		BIzmeniCenu.setDisable(true);
		BIzmeniCenu.setPrefWidth(200);
		BIzmeniCenu.setOnAction(new IzmeniCenuKontroler());

		BObrisiCenu = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiCenu.setGraphic(delete);
		BObrisiCenu.setDisable(true);
		BObrisiCenu.setPrefWidth(200);
		BObrisiCenu.setOnAction(new ObrisiCenuKontroler());

		//dodavanje gornjih dugmica
		unosCenaGp.add(BDodajCenu,0,0,1,1);
		unosCenaGp.add(BSacuvajCenu,1,0,1,1);
		unosCenaGp.add(BPonistiCenu,2,0,1,1);
		unosCenaGp.add(BIzmeniCenu,3,0,1,1);
		unosCenaGp.add(BObrisiCenu,4,0,1,1);

		tfSifraCene = new TextField();
		tfSifraCene.setPrefWidth(200);

		cbUlaz = new ComboBox<Ulaz>();
		cbUlaz.setPrefWidth(620);

		tfCenaBezPrevoza = new TextField();
		tfCenaBezPrevoza.setPrefWidth(200);
		tfCenaBezPrevoza.setPromptText("[din/kg]");

		tfCenaSaPrevozom = new TextField();
		tfCenaSaPrevozom.setPrefWidth(200);
		tfCenaSaPrevozom.setPromptText("[din/kg]");

		dpPocetakVazenjaCene = new DatePicker();
		dpPocetakVazenjaCene.setPrefWidth(410);

		dpKrajVazenjaCene = new DatePicker();
		dpKrajVazenjaCene.setPrefWidth(410);

		lsifracene = new Label("Šifra:");
		unosCenaGp.add(lsifracene,0,2,1,1);
		unosCenaGp.add(tfSifraCene,1,2,1,1);

		lulazl = new Label("Ulaz:");
		unosCenaGp.add(lulazl,0,3,1,1);
		unosCenaGp.add(cbUlaz,1,3,3,1);

		lcena = new Label("Cena:");
		unosCenaGp.add(lcena,0,4,1,1);
		lcenabezprevoza = new Label("bez prevoza:");
		unosCenaGp.add(lcenabezprevoza,1,4,1,1);
		unosCenaGp.setHalignment(lcenabezprevoza, HPos.LEFT);
		unosCenaGp.add(tfCenaBezPrevoza,2,4,1,1);
		lcenasaprevozom = new Label("sa prevozom:");
		unosCenaGp.add(lcenasaprevozom,3,4,1,1);
		unosCenaGp.setHalignment(lcenasaprevozom, HPos.LEFT);
		unosCenaGp.add(tfCenaSaPrevozom,4,4,1,1);

		ldatumod = new Label("Početak važenja cene:");
		unosCenaGp.add(ldatumod,0,5,2,1);
		unosCenaGp.add(dpPocetakVazenjaCene,2,5,2,1);

		ldatumdo = new Label("Kraj važenja cene:");
		unosCenaGp.add(ldatumdo,0,6,2,1);
		unosCenaGp.add(dpKrajVazenjaCene,2,6,2,1);

		SetuUnosCenaDisable();

	}

	@SuppressWarnings({ "unchecked" })	
	private void tabelaUlaza() {
		
		tabelaUlaza = new TableView<Ulaz>();
		
		TableColumn <Ulaz, Integer> tcSifra = new TableColumn<Ulaz,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Ulaz, Integer>("sifra"));
		
		TableColumn <Ulaz, String> tcNaziv = new TableColumn<Ulaz,String>("naziv");		
		tcNaziv.setCellValueFactory(new PropertyValueFactory<Ulaz, String>("naziv"));
		
		TableColumn <Ulaz, String> tcOpis = new TableColumn<Ulaz,String>("opis");		
		tcOpis.setCellValueFactory(new PropertyValueFactory<Ulaz, String>("opis"));				
				
		tabelaUlaza.getColumns().addAll(tcSifra,tcNaziv,tcOpis);
		
		updateTabeleUlaza();

		
		tabelaUlaza.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovogUlaza == false && izmenaUlazaUToku == false) {
		       BObrisiUlaz.setDisable(false);
		       BIzmeniUlaz.setDisable(false);
		       BPonistiUlaz.setDisable(false);
		       BDodajUlaz.setDisable(true);		       
		    }
		});
		
		tabelaUlaza.scrollTo(tabelaUlaza.getItems().size()-1);
		
	}
	
	@SuppressWarnings({ "unchecked" })	
	private void tabelaCena() {
		
		tabelaCena = new TableView<CenaUlaza>();
		
		TableColumn <CenaUlaza, Integer> tcSifra = new TableColumn<CenaUlaza,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Integer>("sifra"));
		
		TableColumn <CenaUlaza, Ulaz> tcUlaz = new TableColumn<CenaUlaza,Ulaz>("ulaz");		
		tcUlaz.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Ulaz>("ulaz"));
		
		TableColumn <CenaUlaza, Double> tcCenaBezPrevoza = new TableColumn<CenaUlaza,Double>("cena bez prevoza");		
		tcCenaBezPrevoza.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Double>("cenaBezPrevoza"));
	
		TableColumn <CenaUlaza, Double> tcCenaSaPrevozom = new TableColumn<CenaUlaza,Double>("cena sa prevozom");		
		tcCenaSaPrevozom.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Double>("cenaSaPrevozom"));
		
		TableColumn <CenaUlaza, Date> tcPocetakVazenja = new TableColumn<CenaUlaza,Date>("početak važenja cene");		
		tcPocetakVazenja.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Date>("pocetakVazenja"));
		
		TableColumn <CenaUlaza, Date> tcKrajVazenja = new TableColumn<CenaUlaza,Date>("kraj važenja cene");		
		tcKrajVazenja.setCellValueFactory(new PropertyValueFactory<CenaUlaza, Date>("krajVazenja"));
							
		tabelaCena.getColumns().addAll(tcSifra,tcUlaz,tcCenaBezPrevoza,tcCenaSaPrevozom,tcPocetakVazenja,tcKrajVazenja);
		
		updateTabeleCena();

		
		tabelaCena.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNoveCene == false && izmenaCeneUToku == false) {
		       BObrisiCenu.setDisable(false);
		       BIzmeniCenu.setDisable(false);
		       BPonistiCenu.setDisable(false);
		       BDodajCenu.setDisable(true);		       
		    }
		});
		
		tabelaCena.scrollTo(tabelaCena.getItems().size()-1);
		
	}
	
	private void pretragaCena() {

		pretragaCenaHB = new HBox(10);
		pretragaCenaHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaCenaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaCenaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaCenaHB.getChildren().add(pl);
		pretragaCenaHB.getChildren().add(new Label("naziv ulaza:"));
		tfPretragaCena = new TextField();
		tfPretragaCena.setPrefWidth(150);
		pretragaCenaHB.getChildren().add(tfPretragaCena);
		pretragaCenaHB.getChildren().add(new Label("cena za datum:"));
		dpPretragaCena = new DatePicker();
		dpPretragaCena.setPrefWidth(160);
		pretragaCenaHB.getChildren().add(dpPretragaCena);
		BPonistiPretraguCena = new Button("Poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguCena.setGraphic(close);
		BPonistiPretraguCena.setPrefWidth(110);
		BPonistiPretraguCena.setOnAction(new PonisiPretraguCenaKontroler());
		pretragaCenaHB.getChildren().add(BPonistiPretraguCena);
		
		tfPretragaCena.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				BPonistiPretraguCena.setDisable(false);
				RBFiltriraneCene.setSelected(true);
				tabelaCena.getItems().clear();
				tabelaCena.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajCeneUlaza(tfPretragaCena.getText(), dpPretragaCena.getValue())));
				tabelaUlaza.getItems().clear();
				tabelaUlaza.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajUlaze(tfPretragaCena.getText())));

				if(tfPretragaCena.getText().equals("") && dpPretragaCena.getValue()==null) {
					BPonistiPretraguCena.setDisable(true);	
					RBSveCene.setSelected(true);
				}
					
			}
		});
		
		dpPretragaCena.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,LocalDate newValue) {
				BPonistiPretraguCena.setDisable(false);
				RBFiltriraniUlaziSpisak.setSelected(true);
				tabelaCena.getItems().clear();
				tabelaCena.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajCeneUlaza(tfPretragaCena.getText(), dpPretragaCena.getValue())));
				tabelaUlaza.getItems().clear();
				tabelaUlaza.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajUlaze(tfPretragaCena.getText())));

				if(tfPretragaCena.getText().equals("") && dpPretragaCena.getValue()==null) {
					BPonistiPretraguCena.setDisable(true);	
					RBSveCene.setSelected(true);
				}
								
			}			
		});

		BPonistiPretraguCena.setDisable(true);
	}
	
	private void stampaCenovnika(){

		stampaj = new HBox(10);            //////////stampa///////////
		stampaj.setStyle("-fx-font: 17px \"Serif\";");
		stampaj.setAlignment(Pos.BASELINE_LEFT); 
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaj.getChildren().add(st);
		Label sll = new Label("Štampa cenovnika:   ");
		sll.setStyle("-fx-font: 25px \"System\";");
		stampaj.getChildren().add(sll);
		Label l1 = new Label("Štampaj za:  ");
		stampaj.getChildren().add(l1);
		RBSveCene = new RadioButton("sve");
		RBFiltriraneCene = new RadioButton("filtrirane");
		RBSveCene.setSelected(true);
		TGCenovnik = new ToggleGroup();
		RBSveCene.setToggleGroup(TGCenovnik);
		RBFiltriraneCene.setToggleGroup(TGCenovnik);
		stampaj.getChildren().addAll(RBSveCene,RBFiltriraneCene);
		Label l2 = new Label("Cene:  ");
		stampaj.getChildren().add(l2);
		RBPredHladnjacom = new RadioButton("pred hladnjačom");
		RBNaOtkupnomMestu = new RadioButton("na otklupnom mestu");
		TGvrstaCenovbnika = new ToggleGroup();
		RBPredHladnjacom.setToggleGroup(TGvrstaCenovbnika);
		RBNaOtkupnomMestu.setToggleGroup(TGvrstaCenovbnika);
		RBPredHladnjacom.setSelected(true);
		stampaj.getChildren().addAll(RBPredHladnjacom,RBNaOtkupnomMestu);		
		BStampajCene = new Button("Štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStampajCene.setGraphic(ici);
		BStampajCene.setPrefWidth(110);
		stampaj.getChildren().add(BStampajCene);
		BStampajCene.setOnAction(new StampaCenaKontroler());
	}
	
	private void pretragaUlaza() {

		pretragaUlazaHB = new HBox(10);
		pretragaUlazaHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaUlazaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaUlazaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje ulaza:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaUlazaHB.getChildren().add(pl);
		pretragaUlazaHB.getChildren().add(new Label("naziv/opis:"));
		tfPretragaUlaza = new TextField();
		tfPretragaUlaza.setPrefWidth(150);
		pretragaUlazaHB.getChildren().add(tfPretragaUlaza);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguUlaza = new Button("Poništi");
		BPonistiPretraguUlaza.setGraphic(close);
		BPonistiPretraguUlaza.setOnAction(new PonistiPretraguUlazaKKontroler());
		BPonistiPretraguUlaza.setDisable(true);
		BPonistiPretraguUlaza.setPrefWidth(110);
		pretragaUlazaHB.getChildren().add(BPonistiPretraguUlaza);
		
		tfPretragaUlaza.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {				
				BPonistiPretraguUlaza.setDisable(false);
				RBFiltriraniUlaziSpisak.setSelected(true);
				tabelaUlaza.getItems().clear();
				tabelaUlaza.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajUlaze(tfPretragaUlaza.getText())));
				if(tfPretragaUlaza.getText().equals("")) {
					BPonistiPretraguUlaza.setDisable(true);
					RBSviUlaziSpisak.setSelected(true);
				}
			}
		});
	}
	
	private void stampaSpisakUlaza() {

		spisak = new HBox(10);                       ///stampa spiska//////////
		spisak.setStyle("-fx-font: 17px \"Serif\";");
		spisak.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisak.getChildren().add(lista);
		Label sl = new Label("Štampa spiska ulaza:   ");
		sl.setStyle("-fx-font: 25px \"System\";");
		spisak.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		spisak.getChildren().add(kkk);
		RBSviUlaziSpisak = new RadioButton("sve");
		RBFiltriraniUlaziSpisak = new RadioButton("filtrirane");
		RBSviUlaziSpisak.setSelected(true);
		TGSpisakUlaza = new ToggleGroup();
		RBSviUlaziSpisak.setToggleGroup(TGSpisakUlaza);
		RBFiltriraniUlaziSpisak.setToggleGroup(TGSpisakUlaza);
		spisak.getChildren().addAll(RBSviUlaziSpisak,RBFiltriraniUlaziSpisak);
		BStampajSpisakUlaza = new Button("Štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BStampajSpisakUlaza.setGraphic(ss);
		BStampajSpisakUlaza.setPrefWidth(110);
		spisak.getChildren().add(BStampajSpisakUlaza);
		BStampajSpisakUlaza.setOnAction(new StampaSpisakUlazaKontroler());
	}
	
	public void updateTabeleUlaza () {   				//dodaje djuture u tabelu kao observabl listu
		tabelaUlaza.getItems().clear();
		tabelaUlaza.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getUlazi()));
		tabelaUlaza.scrollTo(tabelaUlaza.getItems().size()-1);
	}
	
	public void updateTabeleCena () {   				//dodaje djuture u tabelu kao observabl listu
		tabelaCena.getItems().clear();
		tabelaCena.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getCeneUlaza()));
		tabelaCena.scrollTo(tabelaCena.getItems().size()-1);
	}
	
	public void updateCbUlaz() {
		cbUlaz.getItems().clear();
		cbUlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
		cbUlaz.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Ulaz>(cbUlaz);
	}
	
	public void ocistiPoljaZaUnosUlaza() {   //cisti sva polja za unos	
		tfSifraUlaza.clear();
		tfNazivUlaza.clear();
		tfOpisUlaza.clear();
	}
	
	public void ocistiPoljaZaUnosCena() {   //cisti sva polja za unos	
		tfSifraCene.clear();
		updateCbUlaz();
		tfCenaBezPrevoza.clear();
		tfCenaSaPrevozom.clear();
		dpPocetakVazenjaCene.setValue(null);
		dpKrajVazenjaCene.setValue(null);
	}

	public void  SetUnosCenaEnable(){

		lsifracene.setDisable(false);
		cbUlaz.setDisable(false);
		lulazl.setDisable(false);
		tfSifraCene.setDisable(false);
		tfCenaBezPrevoza.setDisable(false);
		tfCenaSaPrevozom.setDisable(false);
		lcena.setDisable(false);
		lcenabezprevoza.setDisable(false);
		lcenasaprevozom.setDisable(false);
		dpPocetakVazenjaCene.setDisable(false);
		ldatumdo.setDisable(false);
		dpKrajVazenjaCene.setDisable(false);
		ldatumod.setDisable(false);
	}

	public void SetuUnosCenaDisable(){

		lsifracene.setDisable(true);
		cbUlaz.setDisable(true);
		lulazl.setDisable(true);
		tfSifraCene.setDisable(true);
		tfCenaBezPrevoza.setDisable(true);
		tfCenaSaPrevozom.setDisable(true);
		lcena.setDisable(true);
		lcenabezprevoza.setDisable(true);
		lcenasaprevozom.setDisable(true);
		dpPocetakVazenjaCene.setDisable(true);
		ldatumdo.setDisable(true);
		dpKrajVazenjaCene.setDisable(true);
		ldatumod.setDisable(true);
	}

	public void SetUnosUlazaEnable() {
		lsifraulaza.setDisable(false);
		tfSifraUlaza.setDisable(false);
		lnazivulaza.setDisable(false);
		tfNazivUlaza.setDisable(false);
		lopisulaza.setDisable(false);
		tfOpisUlaza.setDisable(false);
	}

	public void SetUnosUlazaDisable() {
		lsifraulaza.setDisable(true);
		tfSifraUlaza.setDisable(true);
		lnazivulaza.setDisable(true);
		tfNazivUlaza.setDisable(true);
		lopisulaza.setDisable(true);
		tfOpisUlaza.setDisable(true);
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

	public static BazaUlazaTab getInstance() {
		if(instance == null) {
			instance = new BazaUlazaTab();
		}
		return instance;
	}

	public boolean isUnosNovogUlaza() {
		return unosNovogUlaza;
	}

	public void setUnosNovogUlaza(boolean unosNovogUlaza) {
		this.unosNovogUlaza = unosNovogUlaza;
	}

	public boolean isIzmenaUlazaUToku() {
		return izmenaUlazaUToku;
	}

	public void setIzmenaUlazaUToku(boolean izmenaUlazaUToku) {
		this.izmenaUlazaUToku = izmenaUlazaUToku;
	}

	public boolean isUnosNoveCene() {
		return unosNoveCene;
	}

	public void setUnosNoveCene(boolean unosNoveCene) {
		this.unosNoveCene = unosNoveCene;
	}

	public boolean isIzmenaCeneUToku() {
		return izmenaCeneUToku;
	}

	public void setIzmenaCeneUToku(boolean izmenaCeneUToku) {
		this.izmenaCeneUToku = izmenaCeneUToku;
	}

	public TextField getTfSifraUlaza() {
		return tfSifraUlaza;
	}

	public TextField getTfNazivUlaza() {
		return tfNazivUlaza;
	}

	public TextField getTfOpisUlaza() {
		return tfOpisUlaza;
	}


	public Button getBDodajUlaz() {
		return BDodajUlaz;
	}

	public Button getBSacuvajUlaz() {
		return BSacuvajUlaz;
	}


	public Button getBPonistiUlaz() {
		return BPonistiUlaz;
	}


	public Button getBIzmeniUlaz() {
		return BIzmeniUlaz;
	}

	public Button getBObrisiUlaz() {
		return BObrisiUlaz;
	}

	public TableView<Ulaz> getTabelaUlaza() {
		return tabelaUlaza;
	}

	public ComboBox<Ulaz> getCbUlaz() {
		return cbUlaz;
	}

	public TextField getTfSifraCene() {
		return tfSifraCene;
	}


	public TextField getTfCenaBezPrevoza() {
		return tfCenaBezPrevoza;
	}

	public TextField getTfCenaSaPrevozom() {
		return tfCenaSaPrevozom;
	}

	public DatePicker getDpPocetakVazenjaCene() {
		return dpPocetakVazenjaCene;
	}

	public DatePicker getDpKrajVazenjaCene() {
		return dpKrajVazenjaCene;
	}

	public Button getBDodajCenu() {
		return BDodajCenu;
	}

	public Button getBSacuvajCenu() {
		return BSacuvajCenu;
	}

	public Button getBPonistiCenu() {
		return BPonistiCenu;
	}

	public Button getBIzmeniCenu() {
		return BIzmeniCenu;
	}

	public Button getBObrisiCenu() {
		return BObrisiCenu;
	}

	public TableView<CenaUlaza> getTabelaCena() {
		return tabelaCena;
	}

	public static void setInstance(BazaUlazaTab instance) {
		BazaUlazaTab.instance = instance;
	}

	public TextField getTfPretragaCena() {
		return tfPretragaCena;
	}

	public DatePicker getDpPretragaCena() {
		return dpPretragaCena;
	}

	public Button getBPonistiPretraguCena() {
		return BPonistiPretraguCena;
	}

	public RadioButton getRBPredHladnjacom() {
		return RBPredHladnjacom;
	}

	public RadioButton getRBNaOtkupnomMestu() {
		return RBNaOtkupnomMestu;
	}

	public RadioButton getRBSveCene() {
		return RBSveCene;
	}

	public RadioButton getRBFiltriraneCene() {
		return RBFiltriraneCene;
	}

	public TextField getTfPretragaUlaza() {
		return tfPretragaUlaza;
	}


	public Button getBPonistiPretraguUlaza() {
		return BPonistiPretraguUlaza;
	}

	public RadioButton getRBSviUlaziSpisak() {
		return RBSviUlaziSpisak;
	}

	public RadioButton getRBFiltriraniUlaziSpisak() {
		return RBFiltriraniUlaziSpisak;
	}

}

