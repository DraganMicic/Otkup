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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Firma;
import model.Prevoznik;
import model.Proizvodjac;


public class ProizvodjaciTab extends VBox {

	private static ProizvodjaciTab instance;
	
	private boolean unosNovog = false;
	private boolean izmenaUToku = false;
	
	private FlowPane unosFP;					 //inicijalizujem komponente
	private TextField tfSifra;    
	private TextField tfIme;
	private TextField tfPrezime;
	private TextField tfMesto;
	private TextField tfKomentar;
	private TextField tfMaticnibroj;
	private TextField tfBrojGazdinstva;
	private TextField tfTelefon;
	private TextField tfBrojRacuna;
	private ComboBox<Prevoznik> cbPodrazumevaniPrevoznik;
	private HBox podPrevoznikHB;
	private TextField tfCenaPlus;
	
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private TableView<Proizvodjac> tabela;
	
	private TextField tfPretraga1;     /////pretraga i stampa
	private TextField tfPretraga2;
	private TextField tfPretraga3;
	private Button BPonistiPretragu;
	
	private RadioButton RBsviObracun;
	private RadioButton RBprikazaniObracun;
	private RadioButton  RBselektovaniObracun;
	private ToggleGroup TGobracun;
	private Button BObracunaj;
	
	private RadioButton RBsviSpisak;
	private RadioButton RBprikazaniSpisak;
	private ToggleGroup TGspisak;
	private RadioButton RBosnovnipodaci;
	private RadioButton RBsvipodaci;
	private RadioButton RBosnovnipodaciisaldo;
	private ToggleGroup TGvrstaspiska;
	private Button BstampajSpisak;
	
	private RadioButton RBizvestajProizvodjaca;
	private RadioButton RBodobrenjeGorivo;
	private RadioButton RBodobrenjePreparati;
	private RadioButton RBugovor;
	private ToggleGroup TGstampa;
	private Button BstampajStampu;

	
	private ProizvodjaciTab() {       					//konstruktorrr
			
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		Label naslov = new Label("Baza proizvođača:");
		naslov.setFont(new Font(35));
		this.getChildren().add(naslov);
		this.setScaleX(1);
		this.setScaleY(1);
		
		trakaZaUnos();   //dodajem delove
		trakaKomandi();
		tabela();
		
		trakaZaPretragu();
		trakaZaObracun();
		trakaZaSpisak();
		trakaZaStampuSvega();
		
										
	}
	
	private void trakaZaUnos() {
		
		tfSifra = new TextField();               //podesavam komponente
		tfSifra.setPrefWidth(40);
		tfIme = new TextField();
		tfIme.setPrefWidth(110);
		tfMesto = new TextField();
		tfMesto.setPrefWidth(110);
		tfPrezime =  new TextField();
		tfPrezime.setPrefWidth(110);
		tfKomentar = new TextField();
		tfKomentar.setPrefWidth(150);
		tfMaticnibroj = new TextField();
		tfMaticnibroj.setPrefWidth(110);
		tfBrojGazdinstva = new TextField();
		tfBrojGazdinstva.setPrefWidth(110);
		tfTelefon = new TextField();
		tfTelefon.setPrefWidth(110);
		tfBrojRacuna = new TextField();
		tfBrojRacuna.setPrefWidth(110);	
		cbPodrazumevaniPrevoznik = new ComboBox<Prevoznik>();		
		unosFP = new FlowPane(10,10);                     //pravim odeljak za unos nnovih i izmene
		unosFP.setAlignment(Pos.BASELINE_LEFT);					 
		unosFP.getChildren().add(new Label("šifra:"));      //dodajem sta treba u blok za unos i izmene
		unosFP.getChildren().add(tfSifra);
		unosFP.getChildren().add(new Label("ime:"));
		unosFP.getChildren().add(tfIme);
		unosFP.getChildren().add(new Label("prezime:"));
		unosFP.getChildren().add(tfPrezime);
		unosFP.getChildren().add(new Label("mesto:"));
		unosFP.getChildren().add(tfMesto);
		unosFP.getChildren().add(new Label("komentar:"));
		unosFP.getChildren().add(tfKomentar);
		unosFP.getChildren().add(new Label("jmbg:"));
		unosFP.getChildren().add(tfMaticnibroj);
		unosFP.getChildren().add(new Label("broj gazdinstva:"));
		unosFP.getChildren().add(tfBrojGazdinstva);
		unosFP.getChildren().add(new Label("telefon:"));
		unosFP.getChildren().add(tfTelefon);
		unosFP.getChildren().add(new Label("brojracuna:"));
		unosFP.getChildren().add(tfBrojRacuna);
		podPrevoznikHB = new HBox(10);
		podPrevoznikHB.setAlignment(Pos.BASELINE_LEFT);
		podPrevoznikHB.getChildren().addAll(new Label("podrazumevani prevoznik:"),cbPodrazumevaniPrevoznik);	
		unosFP.getChildren().add(podPrevoznikHB);
		unosFP.getChildren().add(new Label("bonus po kg:"));
		tfCenaPlus = new TextField();
		tfCenaPlus.setPrefWidth(50);
		unosFP.getChildren().add(tfCenaPlus);
		
		getChildren().add(unosFP);
		
		unosFP.setDisable(true);
		
		updateCbPodrazumevaniPrevoznik();
	}
	
	private void trakaKomandi() {       //dodajem sta treba u traku komandi 
		
		BDodaj = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodaj.setGraphic(add); 
		BDodaj.setOnAction(new DodajKontroler());
		
		BSacuvaj = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj.setGraphic(save);
		BSacuvaj.setOnAction(new SacuvajKontroler());
		BSacuvaj.setDisable(true);
		
		BPonisti = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonisti.setGraphic(cancel);
		BPonisti.setOnAction(new PonistiKontroler());
		BPonisti.setDisable(true);
		
		BIzmeni = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setDisable(true);
		BIzmeni.setOnAction(new IzmeniKontroler());
		
		BObrisi = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setDisable(true);
		BObrisi.setOnAction(new ObrisiKOntroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodaj,BSacuvaj,BPonisti,BIzmeni,BObrisi);
		getChildren().add(komandeHB);
	}
	
	@SuppressWarnings("unchecked")
	
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

		updateTabele();
		
		getChildren().add(tabela);
		
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
		
		HBox pretraga = new HBox(10);
		pretraga.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretraga.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
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
		BPonistiPretragu = new Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu.setGraphic(close);
		//BPonistiPretragu.setDisable(true);
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
		
		
		getChildren().add(pretraga);
	}
	
	private void trakaZaObracun() {
		
		HBox brzObracun = new HBox(10);             ///////////////obracunnnn//////////////
		brzObracun.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		brzObracun.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setFont(new Font(20));
		brzObracun.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		lll.setFont(new Font(15));
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
		BObracunaj = new Button("obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setOnAction(new ObracunajKontroler());
		brzObracun.getChildren().add(BObracunaj);	
		
		getChildren().add(brzObracun);
	}
	
	private void trakaZaSpisak() {
		
		HBox spisak = new HBox(10);                       ///stampa spiska//////////
		spisak.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisak.getChildren().add(lista);
		Label sl = new Label("Štampa spiska proizvođača:   ");
		sl.setFont(new Font(20));
		spisak.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		kkk.setFont(new Font(15));
		spisak.getChildren().add(kkk);
		RBsviSpisak = new RadioButton("sve");
		RBprikazaniSpisak = new RadioButton("filtrirane");
		RBsviSpisak.setSelected(true);
		TGspisak = new ToggleGroup();
		RBsviSpisak.setToggleGroup(TGspisak);
		RBprikazaniSpisak.setToggleGroup(TGspisak);
		spisak.getChildren().addAll(RBsviSpisak,RBprikazaniSpisak);
		Label ooo = new Label("Vrsta spiska:   ");
		ooo.setFont(new Font(15));
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
		BstampajSpisak = new Button("štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BstampajSpisak.setGraphic(ss);
		BstampajSpisak.setOnAction(new StampaSpiskaKontroler());
		spisak.getChildren().add(BstampajSpisak);
		
		getChildren().add(spisak);
	}
	
	private void  trakaZaStampuSvega() {  		
	
		HBox stampaj = new HBox(10);            //////////stampa///////////
		stampaj.setAlignment(Pos.BASELINE_LEFT); 
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaj.getChildren().add(st);
		Label sll = new Label("Štampa izveštaja i odobrenja:   ");
		sll.setFont(new Font(20));
		stampaj.getChildren().add(sll);
		Label l1 = new Label("Štampaj za selektovanog proizvođača:  ");
		l1.setFont(new Font(15));
		stampaj.getChildren().add(l1);
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
		BstampajStampu = new Button("štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BstampajStampu.setGraphic(ici);
		stampaj.getChildren().add(BstampajStampu);
		BstampajStampu.setOnAction(new StampaIzvestaja_K());
		
		getChildren().add(stampaj);
				
	}
	
	public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getProizvodjaci()));
		tabela.scrollTo(tabela.getItems().size()-1);
	}
	
	public void updateCbPodrazumevaniPrevoznik() {
		podPrevoznikHB.getChildren().remove(cbPodrazumevaniPrevoznik);
		cbPodrazumevaniPrevoznik = new ComboBox<Prevoznik>();
		podPrevoznikHB.getChildren().add(1,cbPodrazumevaniPrevoznik);
		cbPodrazumevaniPrevoznik.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getPrevoznici()));	
		cbPodrazumevaniPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPodrazumevaniPrevoznik);	
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
		
	public static ProizvodjaciTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new ProizvodjaciTab();			
		}
		return instance;
	}

	public TextField getTfSifra() {    //geteri i seteri vazdan
		return tfSifra; 
	}

	public void setTfSifra(TextField tfSifra) {
		this.tfSifra = tfSifra;
	}

	public TextField getTfIme() {
		return tfIme;
	}

	public void setTfIme(TextField tfIme) {
		this.tfIme = tfIme;
	}

	public TextField getTfPrezime() {
		return tfPrezime;
	}

	public void setTfPrezime(TextField tfPrezime) {
		this.tfPrezime = tfPrezime;
	}

	public TextField getTfMesto() {
		return tfMesto;
	}

	public void setTfMesto(TextField tfMesto) {
		this.tfMesto = tfMesto;
	}

	public TextField getTfMaticnibroj() {
		return tfMaticnibroj;
	}

	public void setTfMaticnibroj(TextField tfMaticnibroj) {
		this.tfMaticnibroj = tfMaticnibroj;
	}

	public TextField getTfBrojGazdinstva() {
		return tfBrojGazdinstva;
	}

	public void setTfBrojGazdinstva(TextField tfBrojGazdinstva) {
		this.tfBrojGazdinstva = tfBrojGazdinstva;
	}

	public TextField getTfTelefon() {
		return tfTelefon;
	}

	public void setTfTelefon(TextField tfTelefon) {
		this.tfTelefon = tfTelefon;
	}

	public TextField getTfBrojRacuna() {
		return tfBrojRacuna;
	}

	public void setTfBrojRacuna(TextField tfBrojRacuna) {
		this.tfBrojRacuna = tfBrojRacuna;
	}

	public Button getBDodaj() {
		return BDodaj;
	}

	public void setBDodaj(Button bDodaj) {
		BDodaj = bDodaj;
	}

	public Button getBSacuvaj() {
		return BSacuvaj;
	}

	public void setBSacuvaj(Button bSacuvaj) {
		BSacuvaj = bSacuvaj;
	}

	public Button getBIzmeni() {
		return BIzmeni;
	}

	public void setBIzmeni(Button bIzmeni) {
		BIzmeni = bIzmeni;
	}

	public Button getBObrisi() {
		return BObrisi;
	}

	public void setBObrisi(Button bObrisi) {
		BObrisi = bObrisi;
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

	public FlowPane getUnosFP() {
		return unosFP;
	}
	
	public void setUnosFP(FlowPane unosFP) {
		this.unosFP = unosFP;
	}
	
	public TextField getTfKomentar() {
		return tfKomentar;
	}
	
	public void setTfKomentar(TextField tfKomentar) {
		this.tfKomentar = tfKomentar;
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

	public void setBPonistiPretragu(Button bPonistiPretragu) {
		BPonistiPretragu = bPonistiPretragu;
	}

	public ComboBox<Prevoznik> getCbPodrazumevaniPrevoznik() {
		return cbPodrazumevaniPrevoznik;
	}

	public void setCbPodrazumevaniPrevoznik(ComboBox<Prevoznik> cbPodrazumevaniPrevoznik) {
		this.cbPodrazumevaniPrevoznik = cbPodrazumevaniPrevoznik;
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

	public HBox getPodPrevoznikHB() {
		return podPrevoznikHB;
	}

	public void setPodPrevoznikHB(HBox podPrevoznikHB) {
		this.podPrevoznikHB = podPrevoznikHB;
	}

	public RadioButton getRBsviObracun() {
		return RBsviObracun;
	}

	public void setRBsviObracun(RadioButton rBsviObracun) {
		RBsviObracun = rBsviObracun;
	}

	public RadioButton getRBprikazaniObracun() {
		return RBprikazaniObracun;
	}

	public void setRBprikazaniObracun(RadioButton rBprikazaniObracun) {
		RBprikazaniObracun = rBprikazaniObracun;
	}

	public RadioButton getRBselektovaniObracun() {
		return RBselektovaniObracun;
	}

	public void setRBselektovaniObracun(RadioButton rBselektovaniObracun) {
		RBselektovaniObracun = rBselektovaniObracun;
	}

	public ToggleGroup getTGobracun() {
		return TGobracun;
	}

	public void setTGobracun(ToggleGroup tGobracun) {
		TGobracun = tGobracun;
	}

	public Button getBObracunaj() {
		return BObracunaj;
	}

	public void setBObracunaj(Button bObracunaj) {
		BObracunaj = bObracunaj;
	}

	public RadioButton getRBsviSpisak() {
		return RBsviSpisak;
	}

	public void setRBsviSpisak(RadioButton rBsviSpisak) {
		RBsviSpisak = rBsviSpisak;
	}

	public RadioButton getRBprikazaniSpisak() {
		return RBprikazaniSpisak;
	}

	public void setRBprikazaniSpisak(RadioButton rBprikazaniSpisak) {
		RBprikazaniSpisak = rBprikazaniSpisak;
	}

	public ToggleGroup getTGspisak() {
		return TGspisak;
	}

	public void setTGspisak(ToggleGroup tGspisak) {
		TGspisak = tGspisak;
	}

	public RadioButton getRBosnovnipodaci() {
		return RBosnovnipodaci;
	}

	public void setRBosnovnipodaci(RadioButton rBosnovnipodaci) {
		RBosnovnipodaci = rBosnovnipodaci;
	}

	public RadioButton getRBsvipodaci() {
		return RBsvipodaci;
	}

	public void setRBsvipodaci(RadioButton rBsvipodaci) {
		RBsvipodaci = rBsvipodaci;
	}

	public RadioButton getRBosnovnipodaciisaldo() {
		return RBosnovnipodaciisaldo;
	}

	public void setRBosnovnipodaciisaldo(RadioButton rBosnovnipodaciisaldo) {
		RBosnovnipodaciisaldo = rBosnovnipodaciisaldo;
	}

	public ToggleGroup getTGvrstaspiska() {
		return TGvrstaspiska;
	}

	public void setTGvrstaspiska(ToggleGroup tGvrstaspiska) {
		TGvrstaspiska = tGvrstaspiska;
	}

	public Button getBstampajSpisak() {
		return BstampajSpisak;
	}

	public void setBstampajSpisak(Button bstampajSpisak) {
		BstampajSpisak = bstampajSpisak;
	}

	public RadioButton getRBizvestajProizvodjaca() {
		return RBizvestajProizvodjaca;
	}

	public void setRBizvestajProizvodjaca(RadioButton rBizvestajProizvodjaca) {
		RBizvestajProizvodjaca = rBizvestajProizvodjaca;
	}

	public RadioButton getRBodobrenjeGorivo() {
		return RBodobrenjeGorivo;
	}

	public void setRBodobrenjeGorivo(RadioButton rBodobrenjeGorivo) {
		RBodobrenjeGorivo = rBodobrenjeGorivo;
	}

	public RadioButton getRBodobrenjePreparati() {
		return RBodobrenjePreparati;
	}

	public void setRBodobrenjePreparati(RadioButton rBodobrenjePreparati) {
		RBodobrenjePreparati = rBodobrenjePreparati;
	}

	public RadioButton getRBugovor() {
		return RBugovor;
	}

	public void setRBugovor(RadioButton rBugovor) {
		RBugovor = rBugovor;
	}

	public ToggleGroup getTGstampa() {
		return TGstampa;
	}

	public void setTGstampa(ToggleGroup tGstampa) {
		TGstampa = tGstampa;
	}

	public Button getBstampajStampu() {
		return BstampajStampu;
	}

	public void setBstampajStampu(Button bstampajStampu) {
		BstampajStampu = bstampajStampu;
	}

	public static void setInstance(ProizvodjaciTab instance) {
		ProizvodjaciTab.instance = instance;
	}

	public void setTabela(TableView<Proizvodjac> tabela) {
		this.tabela = tabela;
	}

	public void setTfPretraga1(TextField tfPretraga1) {
		this.tfPretraga1 = tfPretraga1;
	}

	public void setTfPretraga2(TextField tfPretraga2) {
		this.tfPretraga2 = tfPretraga2;
	}

	public void setTfPretraga3(TextField tfPretraga3) {
		this.tfPretraga3 = tfPretraga3;
	}
	
	public TextField getTfCenaPlus() {
		return tfCenaPlus;
	}
	
	public void setTfCenaPlus(TextField tfCenaPlus) {
		this.tfCenaPlus = tfCenaPlus;
	}
	
	
}


