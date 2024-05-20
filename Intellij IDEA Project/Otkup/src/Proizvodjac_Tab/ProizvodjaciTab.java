package Proizvodjac_Tab;

import Main.ComboBoxAutoComplete;
import Proizvodjac_Tab.Proizvodjac_ASCED.DodajKontroler;
import Proizvodjac_Tab.Proizvodjac_ASCED.IzmeniKontroler;
import Proizvodjac_Tab.Proizvodjac_ASCED.ObrisiKOntroler;
import Proizvodjac_Tab.Proizvodjac_ASCED.PonistiKontroler;
import Proizvodjac_Tab.Proizvodjac_ASCED.SacuvajKontroler;
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
import model.Firma;
import model.Prevoznik;
import model.Proizvodjac;


public class ProizvodjaciTab extends VBox {

	private static ProizvodjaciTab instance;

	private final HBox unosITabelaHb;

	private boolean unosNovog = false;
	private boolean izmenaUToku = false;

	private final GridPane unosGp;  	//inicijalizujem komponente
	private TextField tfSifra;
	private Label lsifra;
	private TextField tfIme;
	private  Label lime;
	private TextField tfPrezime;
	private  Label lprezime;
	private TextField tfMesto;
	private  Label lmesto;
	private TextField tfKomentar;
	private  Label lkomentar;
	private TextField tfMaticnibroj;
	private Label lmaticnibroj;
	private TextField tfBrojGazdinstva;
	private  Label lbrojgazdinstva;
	private TextField tfTelefon;
	private Label ltelefon;
	private TextField tfBrojRacuna;
	private Label lbrojracuna;
	private ComboBox<Prevoznik> cbPodrazumevaniPrevoznik;
	private  Label lpodprevoznik;
	private HBox podPrevoznikHB;
	private  Label lbonus;
	private TextField tfCenaPlus;
	private Label ldin;
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private TableView<Proizvodjac> tabela;

	private HBox pretraga;
	private TextField tfPretraga1;     /////pretraga i stampa
	private TextField tfPretraga2;
	private TextField tfPretraga3;
	private Button BPonistiPretragu;

	private HBox brzObracun;
	private RadioButton RBsviObracun;
	private RadioButton RBprikazaniObracun;
	private RadioButton  RBselektovaniObracun;
	private ToggleGroup TGobracun;
	private Button BObracunaj;

	private HBox spisak;
	private RadioButton RBsviSpisak;
	private RadioButton RBprikazaniSpisak;
	private ToggleGroup TGspisak;
	private RadioButton RBosnovnipodaci;
	private RadioButton RBsvipodaci;
	private RadioButton RBosnovnipodaciisaldo;
	private ToggleGroup TGvrstaspiska;
	private Button BstampajSpisak;

	private HBox stampaj;
	private ComboBox<Proizvodjac> cbProizvodjacStampaSvega;
	private RadioButton RBizvestajProizvodjaca;
	private RadioButton RBodobrenjeGorivo;
	private RadioButton RBodobrenjePreparati;
	private RadioButton RBugovor;
	private ToggleGroup TGstampa;
	private Button BstampajStampu;

	
	private ProizvodjaciTab() {       					//konstruktorrr
			
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Baza proizvođača:");
		naslov.setFont(new Font(35));
		Separator separator = new Separator();
		this.getChildren().addAll(naslov,separator);

		//unos i tabela
		unosITabelaHb = new HBox(20);
		this.getChildren().add(unosITabelaHb);

		//podesavanja grid panea
		unosGp = new GridPane();
		unosGp.setPadding(new Insets(10, 10, 10, 10));
		unosGp.setVgap(10);
		unosGp.setHgap(10);
		unosGp.setAlignment(Pos.BASELINE_LEFT);
		unosGp.setStyle("-fx-font: 20px \"Serif\";");
		unosGp.setMinWidth(890);

		unos();
		tabela();
		tabela.setPrefHeight(530);
		tabela.setPrefWidth(1300);

		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		unosITabelaHb.getChildren().addAll(unosGp,separator2,tabela);

		//naslov2
		Label naslov2 = new Label("Alati:");
		naslov2.setFont(new Font(35));
		Separator separator1 = new Separator();
		this.getChildren().addAll(naslov2,separator1);

		//alati
		trakaZaPretragu();
		trakaZaObracun();
		trakaZaSpisak();
		trakaZaStampuSvega();
		this.getChildren().addAll(pretraga,brzObracun,spisak,stampaj);

		//pocetni update

		updateCbPodrazumevaniPrevoznik();
		updatecbProizvodjacStampaSvega();

		getBDodaj().requestFocus();
		
										
	}

	private  void unos(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodaj = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodaj.setGraphic(add);
		BDodaj.setPrefWidth(200);
		BDodaj.setMaxWidth(200);
		BDodaj.setOnAction(new DodajKontroler());

		BSacuvaj = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj.setGraphic(save);
		BSacuvaj.setPrefWidth(200);
		BSacuvaj.setMaxWidth(200);
		BSacuvaj.setOnAction(new SacuvajKontroler());
		BSacuvaj.setDisable(true);

		BPonisti = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonisti.setGraphic(cancel);
		getBPonisti().setPrefWidth(200);
		BPonisti.setMaxWidth(200);
		BPonisti.setOnAction(new PonistiKontroler());
		BPonisti.setDisable(true);

		BIzmeni = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setPrefWidth(200);
		BIzmeni.setMaxWidth(200);
		BIzmeni.setOnAction(new IzmeniKontroler());
		BIzmeni.setDisable(true);

		BObrisi = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setPrefWidth(200);
		BObrisi.setMaxWidth(200);
		BObrisi.setOnAction(new ObrisiKOntroler());
		BObrisi.setDisable(true);



		//dodavanje gornjih dugmica
		unosGp.add(BDodaj,0,0,1,1);
		unosGp.add(BSacuvaj,1,0,1,1);
		unosGp.add(BPonisti,2,0,1,1);
		unosGp.add(BIzmeni,3,0,1,1);
		unosGp.add(BObrisi,4,0,1,1);
		HBox Bkita = new HBox();
		Bkita.setPrefWidth(200);
		unosGp.add(Bkita,5,0,1,1);

		//POLJA ZA UNOS///////////////////////////////////////////////////////////////////////////////
		tfSifra = new TextField();               //podesavam komponente
		tfSifra.setPrefWidth(200);

		tfIme = new TextField();
		tfIme.setPrefWidth(410);

		tfMesto = new TextField();
		tfMesto.setPrefWidth(410);
		tfMesto.setPromptText("/");

		tfPrezime =  new TextField();
		tfPrezime.setPrefWidth(410);

		tfKomentar = new TextField();
		tfKomentar.setPrefWidth(410);
		tfKomentar.setPromptText("/");

		tfMaticnibroj = new TextField();
		tfMaticnibroj.setPrefWidth(410);
		tfMaticnibroj.setPromptText("/");

		tfBrojGazdinstva = new TextField();
		tfBrojGazdinstva.setPrefWidth(410);
		tfBrojGazdinstva.setPromptText("/");

		tfTelefon = new TextField();
		tfTelefon.setPrefWidth(410);
		tfTelefon.setPromptText("/");

		tfBrojRacuna = new TextField();
		tfBrojRacuna.setPrefWidth(410);
		tfBrojRacuna.setPromptText("/");

		cbPodrazumevaniPrevoznik = new ComboBox<Prevoznik>();
		cbPodrazumevaniPrevoznik.setPrefWidth(620);

		tfCenaPlus = new TextField();
		tfCenaPlus.setPrefWidth(200);
		tfCenaPlus.setPromptText("0");

		lsifra = new Label("Šifra:");
		unosGp.add(lsifra,0,2,1,1);
		unosGp.add(tfSifra,1,2,1,1);

		lime = new Label("Ime:");
		unosGp.add(lime,0,3,1,1);
		unosGp.add(tfIme,1,3,2,1);
		lprezime = new Label("Prezime:");
		unosGp.add(lprezime,3,3,1,1);
		unosGp.add(tfPrezime,4,3,2,1);

		lmesto = new Label("Mesto:");
		unosGp.add(lmesto,0,4,1,1);
		unosGp.add(tfMesto,1,4,2,1);
		lkomentar = new Label("Komentar:");
		unosGp.add(lkomentar,3,4,1,1);
		unosGp.add(tfKomentar,4,4,2,1);

		lmaticnibroj = new Label("JMBG:");
		unosGp.add(lmaticnibroj,0,5,1,1);
		unosGp.add(tfMaticnibroj,1,5,2,1);
		lbrojgazdinstva = new Label("Br. RPG:");
		unosGp.add(lbrojgazdinstva,3,5,1,1);
		unosGp.add(tfBrojGazdinstva,4,5,2,1);

		ltelefon = new Label("Telefon:");
		unosGp.add(ltelefon,0,6,1,1);
		unosGp.add(tfTelefon,1,6,2,1);
		lbrojracuna = new Label("Br. računa:");
		unosGp.add(lbrojracuna,3,6,1,1);
		unosGp.add(tfBrojRacuna,4,6,2,1);

		lpodprevoznik = new Label("Podrazumevani prevoznik:");
		unosGp.add(lpodprevoznik,0,7,2,1);
		unosGp.add(cbPodrazumevaniPrevoznik,2,7,3,1);

		lbonus = new Label("Bonus po kilogramu:");
		unosGp.add(lbonus,0,8,2,1);
		unosGp.add(tfCenaPlus,2,8,1,1);
		ldin = new Label("din/kg");
		unosGp.add(ldin,3,8,1,1);
		unosGp.setHalignment(ldin, HPos.LEFT);

		SetUnosDisable();

	}

	
	private void tabela() {                 ///////////////////////formiram tabelu
		
		tabela = new TableView<Proizvodjac>();
		
		TableColumn <Proizvodjac, Integer> tcSifra = new TableColumn<Proizvodjac,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Proizvodjac, Integer>("sifra"));
		
		TableColumn <Proizvodjac, String> tcIme = new TableColumn<Proizvodjac,String>("ime");		
		tcIme.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("ime"));
		
		TableColumn <Proizvodjac, String> tcPrezime = new TableColumn<Proizvodjac,String>("prezime");		
		tcPrezime.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("prezime"));
		
		TableColumn <Proizvodjac, String> tcMesto = new TableColumn<Proizvodjac,String>("mesto");		
		tcMesto.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("mesto"));
		
		TableColumn <Proizvodjac, String> tcKomentar = new TableColumn<Proizvodjac,String>("komentar");		
		tcKomentar.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("komentar"));
		
		TableColumn <Proizvodjac, String> tcMB = new TableColumn<Proizvodjac,String>("JMBG");		
		tcMB.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("maticniBroj"));
		
		TableColumn <Proizvodjac, String> tcBG = new TableColumn<Proizvodjac,String>("broj Gazdinstva");		
		tcBG.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("brojGazdinstva"));
		
		TableColumn <Proizvodjac, String> tcTelefon = new TableColumn<Proizvodjac,String>("telefon");		
		tcTelefon.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("telefon"));
		
		TableColumn <Proizvodjac, String> tcBR = new TableColumn<Proizvodjac,String>("broj računa");		
		tcBR.setCellValueFactory(new PropertyValueFactory<Proizvodjac, String>("brojRacuna"));
		
		TableColumn<Proizvodjac, Prevoznik> tcPrevoznik = new TableColumn<Proizvodjac, Prevoznik>("podrazumevani prevoznik:");
		tcPrevoznik.setCellValueFactory(new PropertyValueFactory<Proizvodjac, Prevoznik>("prevoznik"));
		
		TableColumn<Proizvodjac, Double> tcCenaPlus = new TableColumn<Proizvodjac, Double>("bonus po kg:");
		tcCenaPlus.setCellValueFactory(new PropertyValueFactory<Proizvodjac, Double>("cenaPlus"));
		
		tabela.getColumns().addAll(tcSifra,tcPrezime,tcIme,tcMesto,tcKomentar,tcMB,tcBG,tcTelefon,tcBR,tcPrevoznik,tcCenaPlus);

		//tabela.setPrefWidth(1400);
		//tabela.setPrefHeight(500);
		updateTabele();
		
		tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovog == false && izmenaUToku == false) {
		       BObrisi.setDisable(false);
		       BIzmeni.setDisable(false);
		       BPonisti.setDisable(false);
		       BDodaj.setDisable(true);	
		       RBselektovaniObracun.setSelected(true);
		    }
		});
		
		tabela.scrollTo(tabela.getItems().size()-1);
		
	}

	private void trakaZaPretragu() {
		
		pretraga = new HBox(10);
		pretraga.setStyle("-fx-font: 17px \"Serif\";");
		pretraga.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretraga.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretraga.getChildren().add(pl);
		pretraga.getChildren().add(new Label("ime i/ili prezime:"));
		tfPretraga1 = new TextField();
		tfPretraga1.setPrefWidth(150);
		pretraga.getChildren().add(tfPretraga1);
		pretraga.getChildren().add(new Label("mesto:"));
		tfPretraga2 = new TextField();
		tfPretraga2.setPrefWidth(100);
		pretraga.getChildren().add(tfPretraga2);
		pretraga.getChildren().add(new Label("podrazumevani prevoznik:"));
		tfPretraga3 = new TextField();
		tfPretraga3.setPrefWidth(100);
		pretraga.getChildren().add(tfPretraga3);
		BPonistiPretragu = new Button("Poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setPrefWidth(110);
		BPonistiPretragu.setOnAction(new PonistiPretraguKontroler());
		pretraga.getChildren().add(BPonistiPretragu);	
		
		tfPretraga1.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {				
				BPonistiPretragu.setDisable(false);
				RBprikazaniObracun.setSelected(true);
				RBprikazaniSpisak.setSelected(true);
				tabela.getItems().clear();	
				tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajProizvodjace(tfPretraga1.getText(), tfPretraga2.getText(), tfPretraga3.getText())));				
				if(tfPretraga1.getText().equals("") && tfPretraga2.getText().equals("") && tfPretraga3.getText().equals("")) {
					BPonistiPretragu.setDisable(true);
					RBsviObracun.setSelected(true);
					RBsviSpisak.setSelected(true);
				}
			}
		});
		
		tfPretraga2.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				BPonistiPretragu.setDisable(false);
				RBprikazaniObracun.setSelected(true);
				RBprikazaniSpisak.setSelected(true);
				tabela.getItems().clear();
				tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajProizvodjace(tfPretraga1.getText(), tfPretraga2.getText(), tfPretraga3.getText())));			
				if(tfPretraga1.getText().equals("") && tfPretraga2.getText().equals("") && tfPretraga3.getText().equals("")) {
					BPonistiPretragu.setDisable(true);
					RBsviObracun.setSelected(true);
					RBsviSpisak.setSelected(true);
				}
			}
		});
		
		
		tfPretraga3.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				BPonistiPretragu.setDisable(false);
				RBprikazaniObracun.setSelected(true);
				RBprikazaniSpisak.setSelected(true);
				tabela.getItems().clear();
				tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajProizvodjace(tfPretraga1.getText(), tfPretraga2.getText(), tfPretraga3.getText())));				
				if(tfPretraga1.getText().equals("") && tfPretraga2.getText().equals("") && tfPretraga3.getText().equals("")) {
					RBsviObracun.setSelected(true);
					RBsviSpisak.setSelected(true);
					BPonistiPretragu.setDisable(true);
				}
			}
		});
		
		BPonistiPretragu.setDisable(true);

	}
	
	private void trakaZaObracun() {
		
		brzObracun = new HBox(10);             ///////////////obracunnnn//////////////
		brzObracun.setStyle("-fx-font: 17px \"Serif\";");
		brzObracun.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		brzObracun.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setStyle("-fx-font: 25px \"System\";");
		brzObracun.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		brzObracun.getChildren().add(lll);
		RBselektovaniObracun = new RadioButton("selektovani");
		RBprikazaniObracun = new RadioButton("filtrirane");
		RBsviObracun = new RadioButton("sve   ");
		TGobracun = new ToggleGroup();
		RBselektovaniObracun.setToggleGroup(TGobracun);
		RBprikazaniObracun.setToggleGroup(TGobracun);
		RBsviObracun.setToggleGroup(TGobracun);
		RBsviObracun.setSelected(true);
		brzObracun.getChildren().addAll(RBselektovaniObracun,RBprikazaniObracun,RBsviObracun);
		BObracunaj = new Button("Obračunaj");
		BObracunaj.setPrefWidth(130);
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setOnAction(new ObracunajKontroler());
		brzObracun.getChildren().add(BObracunaj);	

	}
	
	private void trakaZaSpisak() {
		
		spisak = new HBox(10);                       ///stampa spiska//////////
		spisak.setStyle("-fx-font: 17px \"Serif\";");
		spisak.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisak.getChildren().add(lista);
		Label sl = new Label("Štampa spiska proizvođača:   ");
		sl.setStyle("-fx-font: 25px \"System\";");
		spisak.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		spisak.getChildren().add(kkk);
		RBsviSpisak = new RadioButton("sve");
		RBprikazaniSpisak = new RadioButton("filtrirane");
		RBsviSpisak.setSelected(true);
		TGspisak = new ToggleGroup();
		RBsviSpisak.setToggleGroup(TGspisak);
		RBprikazaniSpisak.setToggleGroup(TGspisak);
		spisak.getChildren().addAll(RBsviSpisak,RBprikazaniSpisak);
		Label ooo = new Label("Vrsta spiska:   ");
		spisak.getChildren().add(ooo);
		RBosnovnipodaci = new RadioButton("osnovni podaci");
		RBsvipodaci = new RadioButton("svi podaci");
		RBosnovnipodaciisaldo = new RadioButton("osnovni podaci sa saldom");
		TGvrstaspiska = new ToggleGroup();
		RBosnovnipodaci.setToggleGroup(TGvrstaspiska);
		RBsvipodaci.setToggleGroup(TGvrstaspiska);
		RBosnovnipodaciisaldo.setToggleGroup(TGvrstaspiska);
		RBosnovnipodaci.setSelected(true);
		spisak.getChildren().addAll(RBosnovnipodaci,RBsvipodaci,RBosnovnipodaciisaldo);
		BstampajSpisak = new Button("Štampaj");
		BstampajSpisak.setPrefWidth(110);
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BstampajSpisak.setGraphic(ss);
		BstampajSpisak.setOnAction(new StampaSpiskaKontroler());
		spisak.getChildren().add(BstampajSpisak);

	}
	
	private void  trakaZaStampuSvega() {  		
	
		stampaj = new HBox(10);            //////////stampa///////////
		stampaj.setStyle("-fx-font: 17px \"Serif\";");
		stampaj.setAlignment(Pos.BASELINE_LEFT); 
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaj.getChildren().add(st);
		Label sll = new Label("Štampaj:   ");
		sll.setStyle("-fx-font: 25px \"System\";");
		stampaj.getChildren().add(sll);

		RBizvestajProizvodjaca = new RadioButton("izveštaj");
		RBodobrenjeGorivo = new RadioButton("odobrenje za gorivo");
		RBodobrenjePreparati = new RadioButton("odobrenje za preparate");
		RBugovor = new RadioButton("ugovor");
		TGstampa = new ToggleGroup();
		RBizvestajProizvodjaca.setToggleGroup(TGstampa);
		RBodobrenjeGorivo.setToggleGroup(TGstampa);
		RBodobrenjePreparati.setToggleGroup(TGstampa);
		RBugovor.setToggleGroup(TGstampa);
		RBizvestajProizvodjaca.setSelected(true);
		stampaj.getChildren().addAll(RBizvestajProizvodjaca,RBodobrenjeGorivo,RBodobrenjePreparati,RBugovor);

		Label l1 = new Label("Za proizvođača:  ");
		stampaj.getChildren().add(l1);
		cbProizvodjacStampaSvega = new ComboBox<Proizvodjac>();
		cbProizvodjacStampaSvega.setPrefWidth(300);
		stampaj.getChildren().add(cbProizvodjacStampaSvega);

		BstampajStampu = new Button("Štampaj");
		BstampajStampu.setPrefWidth(110);
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BstampajStampu.setGraphic(ici);
		stampaj.getChildren().add(BstampajStampu);
		BstampajStampu.setOnAction(new StampaIzvestaja_K());

		updatecbProizvodjacStampaSvega();
	}
	
	public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getProizvodjaci()));
		tabela.scrollTo(tabela.getItems().size()-1);
	}
	
	public void updateCbPodrazumevaniPrevoznik() {

		cbPodrazumevaniPrevoznik.getItems().clear();
		cbPodrazumevaniPrevoznik.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getPrevoznici()));	
		cbPodrazumevaniPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPodrazumevaniPrevoznik);	
	}

	public void updatecbProizvodjacStampaSvega() {

		cbProizvodjacStampaSvega.getItems().clear();
		cbProizvodjacStampaSvega.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getProizvodjaci()));
		cbProizvodjacStampaSvega.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjacStampaSvega);
	}
	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos		
		tfSifra.clear();
		tfIme.clear();
		tfPrezime.clear();
		tfMesto.clear();
		tfBrojGazdinstva.clear();
		tfBrojRacuna.clear();
		tfTelefon.clear();
		tfMaticnibroj.clear();
		tfKomentar.clear();
		tfCenaPlus.clear();
		cbPodrazumevaniPrevoznik.getSelectionModel().clearSelection();
		updateCbPodrazumevaniPrevoznik();
	}

	public void SetUnosDisable(){
		lsifra.setDisable(true);
		tfSifra.setDisable(true);
		lime.setDisable(true);
		tfIme.setDisable(true);
		lprezime.setDisable(true);
		tfPrezime.setDisable(true);
		lmesto.setDisable(true);
		tfMesto.setDisable(true);
		lkomentar.setDisable(true);
		tfKomentar.setDisable(true);
		lmaticnibroj.setDisable(true);
		tfMaticnibroj.setDisable(true);
		lbrojgazdinstva.setDisable(true);
		tfBrojGazdinstva.setDisable(true);
		ltelefon.setDisable(true);
		tfTelefon.setDisable(true);
		lbrojracuna.setDisable(true);
		tfBrojRacuna.setDisable(true);
		cbPodrazumevaniPrevoznik.setDisable(true);
		lpodprevoznik.setDisable(true);
		lbonus.setDisable(true);
		tfCenaPlus.setDisable(true);
		ldin.setDisable(true);
	}

	public void  SetUnosEnable(){

		lsifra.setDisable(false);
		tfSifra.setDisable(false);
		lime.setDisable(false);
		tfIme.setDisable(false);
		lprezime.setDisable(false);
		tfPrezime.setDisable(false);
		lmesto.setDisable(false);
		tfMesto.setDisable(false);
		lkomentar.setDisable(false);
		tfKomentar.setDisable(false);
		lmaticnibroj.setDisable(false);
		tfMaticnibroj.setDisable(false);
		lbrojgazdinstva.setDisable(false);
		tfBrojGazdinstva.setDisable(false);
		ltelefon.setDisable(false);
		tfTelefon.setDisable(false);
		lbrojracuna.setDisable(false);
		tfBrojRacuna.setDisable(false);
		cbPodrazumevaniPrevoznik.setDisable(false);
		lpodprevoznik.setDisable(false);
		lbonus.setDisable(false);
		tfCenaPlus.setDisable(false);
		ldin.setDisable(false);

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

	public static ProizvodjaciTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new ProizvodjaciTab();			
		}
		return instance;
	}

	public TextField getTfSifra() {    //geteri i seteri vazdan
		return tfSifra; 
	}

	public TextField getTfIme() {
		return tfIme;
	}

	public ComboBox<Proizvodjac> getCbProizvodjacStampaSvega() {
		return cbProizvodjacStampaSvega;
	}

	public TextField getTfPrezime() {
		return tfPrezime;
	}

	public TextField getTfMesto() {
		return tfMesto;
	}

	public TextField getTfMaticnibroj() {
		return tfMaticnibroj;
	}

	public TextField getTfBrojGazdinstva() {
		return tfBrojGazdinstva;
	}

	public TextField getTfTelefon() {
		return tfTelefon;
	}

	public TextField getTfBrojRacuna() {
		return tfBrojRacuna;
	}

	public Button getBDodaj() {
		return BDodaj;
	}

	public Button getBSacuvaj() {
		return BSacuvaj;
	}

	public Button getBIzmeni() {
		return BIzmeni;
	}

	public Button getBObrisi() {
		return BObrisi;
	}

	public Button getBPonisti() {
		return BPonisti;
	}

	public void setBPonisti(Button bPonisti) {
		BPonisti = bPonisti;
	}

	public boolean isUnosNovog() {
		return unosNovog;
	}

	public void setUnosNovog(boolean unosNovog) {
		this.unosNovog = unosNovog;
	}
	
	public TextField getTfKomentar() {
		return tfKomentar;
	}

	public TableView<Proizvodjac> getTabela() {
		return tabela;
	}

	public boolean isIzmenaUToku() {
		return izmenaUToku;
	}

	public void setIzmenaUToku(boolean izmenaUToku) {
		this.izmenaUToku = izmenaUToku;
	}

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
	}

	public ComboBox<Prevoznik> getCbPodrazumevaniPrevoznik() {
		return cbPodrazumevaniPrevoznik;
	}

	public TextField getTfPretraga1() {
		return tfPretraga1;
	}
	public TextField getTfPretraga2() {
		return tfPretraga2;
	}
	public TextField getTfPretraga3() {
		return tfPretraga3;
	}

	public RadioButton getRBsviObracun() {
		return RBsviObracun;
	}

	public RadioButton getRBprikazaniObracun() {
		return RBprikazaniObracun;
	}

	public RadioButton getRBselektovaniObracun() {
		return RBselektovaniObracun;
	}

	public ToggleGroup getTGobracun() {
		return TGobracun;
	}

	public RadioButton getRBsviSpisak() {
		return RBsviSpisak;
	}

	public RadioButton getRBprikazaniSpisak() {
		return RBprikazaniSpisak;
	}

	public RadioButton getRBosnovnipodaci() {
		return RBosnovnipodaci;
	}

	public RadioButton getRBsvipodaci() {
		return RBsvipodaci;
	}

	public RadioButton getRBosnovnipodaciisaldo() {
		return RBosnovnipodaciisaldo;
	}

	public RadioButton getRBizvestajProizvodjaca() {
		return RBizvestajProizvodjaca;
	}

	public RadioButton getRBodobrenjeGorivo() {
		return RBodobrenjeGorivo;
	}

	public RadioButton getRBodobrenjePreparati() {
		return RBodobrenjePreparati;
	}

	public RadioButton getRBugovor() {
		return RBugovor;
	}

	public static void setInstance(ProizvodjaciTab instance) {
		ProizvodjaciTab.instance = instance;
	}

	public void setTabela(TableView<Proizvodjac> tabela) {
		this.tabela = tabela;
	}

	public TextField getTfCenaPlus() {
		return tfCenaPlus;
	}
}


