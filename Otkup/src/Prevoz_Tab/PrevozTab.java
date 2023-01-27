package Prevoz_Tab;

import java.util.Date;
import Prevoz_Tab.Prevoznik_ASCED.DodajKontroler;
import Prevoz_Tab.Prevoznik_ASCED.IzmeniKontroler;
import Prevoz_Tab.Prevoznik_ASCED.ObrisiKontroler;
import Prevoz_Tab.Prevoznik_ASCED.PonistiKontroler;
import Prevoz_Tab.Tools_K.ObracunajKontroler;
import Prevoz_Tab.Tools_K.PonistiPretraguPrevoznikaKontroler;
import Prevoz_Tab.Prevoznik_ASCED.SacuvajKontroler;
import Prevoz_Tab.Tools_K.StampajIzvestajKontroler;
import Prevoz_Tab.Tools_K.StampajSpisakPrevoznikaKOntroler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Firma;
import model.Prevoz;
import model.Prevoznik;
import model.Proizvodjac; 

public class PrevozTab extends VBox {
	
	private static PrevozTab instance;
	
	private HBox zajednickoHB;
	
	private VBox prevozniciVB;
	private VBox prevoziVB;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;
	
	private HBox unosHB;					 //inicijalizujem komponente za prevoznike
	private TextField tfSifra;    
	private TextField tfIme;
	private TextField tfPrezime;
	private TextField tfOpis;
	private TextField tfCenaPoKg;
	
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private TableView<Prevoznik> tabelaPrevoznika;
	
	private TextField tfPretragaPrevoznika;
	private RadioButton RBDanPretraga;
	private RadioButton RBPeriodPretraga;
	private ToggleGroup TGPretraga;
	private Label Lod;
	private Label Ldo;
	private int pozicija  = 6;
	private DatePicker DPPocetniPretraga;
	private DatePicker DPKrajnjiPretraga;
	private Button BPonistiPretraguPrevoznika;
	
	private RadioButton RBsviPrevozniciSpisak;
	private RadioButton RBFiltrriraniPrevozniciSpisak;
	private ToggleGroup TGSpisakPrevoznika;
	private RadioButton RBSamoImenaSpisak;
	private RadioButton RBPodaciSaSaldomSpisak;
	private ToggleGroup TGVrstaSpiska;
	private Button BStampajSpisakPrevoznika;
	
	private RadioButton RBSviObracun;
	private RadioButton RBFiltriraniObracun;
	private RadioButton RBSelektovaniObracun;
	private ToggleGroup TGObracun;
	private Button BObracunaj;
	
	private Button BStampajIzvestaj;
	private HBox stampaIzvestajaHB;
		
	private TableView<Prevoz> tabelaPrevoza; //inicijalizujem komponente za prevoze
	
	private PrevozTab() {       					//konstruktorrr
		
		zajednickoHB = new HBox(20);
		prevozniciVB = new VBox(15);
		prevozniciVB.setPrefWidth(1100);
		prevoziVB = new VBox(15);
		prevoziVB.setPrefWidth(1000);

		
		Label naslov2 = new Label("Prevoznici:");
		naslov2.setFont(new Font(35));
		prevozniciVB.getChildren().add(naslov2);
		
		Label naslov3 = new Label("Prevozi:");
		naslov3.setFont(new Font(35));
		prevoziVB.getChildren().add(naslov3);
			
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		
		trakaZaUnos();   //dodajem delove
		trakaKomandi();
		tabelaPrevoznika();	
		tabelaPrevoza();

		
		zajednickoHB.getChildren().addAll(prevozniciVB,prevoziVB);
		this.getChildren().add(zajednickoHB);
		
		pretragaPrevoznika();
		brzObracun();
		spisakPrevoznika();
		StampaIzvestaja();

	}
	
	private void trakaZaUnos() {
		
		tfSifra = new TextField();               //podesavam komponente
		tfSifra.setPrefWidth(40);
		tfIme = new TextField();
		tfIme.setPrefWidth(120);
		tfPrezime = new TextField();
		tfPrezime.setPrefWidth(120);
		tfOpis = new TextField();
		tfOpis.setPrefWidth(120);
		tfCenaPoKg = new TextField();
		tfCenaPoKg.setPrefWidth(50);
		
		unosHB = new HBox(10);                     //pravim odeljak za unos nnovih i izmene
		unosHB.setAlignment(Pos.BASELINE_LEFT);
			 
		unosHB.getChildren().add(new Label("šifra:"));      //dodajem sta treba u blok za unos i izmene
		unosHB.getChildren().add(tfSifra);
		unosHB.getChildren().add(new Label("ime:"));
		unosHB.getChildren().add(tfIme);
		unosHB.getChildren().add(new Label("prezime:"));
		unosHB.getChildren().add(tfPrezime);
		unosHB.getChildren().add(new Label("opis:"));
		unosHB.getChildren().add(tfOpis);
		unosHB.getChildren().add(new Label("cena prevoza (din/kg):"));
		unosHB.getChildren().add(tfCenaPoKg);
				
		prevozniciVB.getChildren().add(unosHB);
		
		unosHB.setDisable(true);
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
		BObrisi.setOnAction(new ObrisiKontroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodaj,BSacuvaj,BPonisti,BIzmeni,BObrisi);
		prevozniciVB.getChildren().add(komandeHB);
	}
	
	@SuppressWarnings("unchecked")
	private void tabelaPrevoznika() {                 ///////////////////////formiram tabelu
		
		tabelaPrevoznika = new TableView<Prevoznik>();
		
		TableColumn <Prevoznik, Integer> tcSifra = new TableColumn<Prevoznik,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Prevoznik, Integer>("sifra"));
		
		TableColumn <Prevoznik, String> tcIme = new TableColumn<Prevoznik,String>("ime");		
		tcIme.setCellValueFactory(new PropertyValueFactory<Prevoznik, String>("ime"));
		
		TableColumn <Prevoznik, String> tcPrezime = new TableColumn<Prevoznik,String>("prezime");		
		tcPrezime.setCellValueFactory(new PropertyValueFactory<Prevoznik, String>("prezime"));
		
		TableColumn <Prevoznik, String> tcOpis = new TableColumn<Prevoznik,String>("opis");		
		tcOpis.setCellValueFactory(new PropertyValueFactory<Prevoznik, String>("opis"));
		
		TableColumn <Prevoznik, Double> tcCenaPoKg = new TableColumn<Prevoznik,Double>("cena prevoza po kilogramu");		
		tcCenaPoKg.setCellValueFactory(new PropertyValueFactory<Prevoznik, Double>("cenaPoKg"));
		
		tabelaPrevoznika.getColumns().addAll(tcSifra,tcIme,tcPrezime,tcOpis,tcCenaPoKg);
		
		updateTabelePrevoznika();
		
		prevozniciVB.getChildren().add(tabelaPrevoznika);
		
		tabelaPrevoznika.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovog == false && izmenaUToku == false) {
		       BObrisi.setDisable(false);
		       BIzmeni.setDisable(false);
		       BPonisti.setDisable(false);
		       BDodaj.setDisable(true);		
		       RBSelektovaniObracun.setSelected(true);
		    }
		});
		
		tabelaPrevoznika.scrollTo(tabelaPrevoznika.getItems().size()-1);
		
	
	}
	
	@SuppressWarnings("unchecked")
	private void tabelaPrevoza() {
		tabelaPrevoza = new TableView<Prevoz>();
		
		TableColumn <Prevoz, Integer> tcSifra = new TableColumn<Prevoz,Integer>("šifra unosa ulaza");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Prevoz, Integer>("sifraUnosaUlaza"));
		
		TableColumn <Prevoz, Date> tcDatum = new TableColumn<Prevoz,Date>("datum");		
		tcDatum.setCellValueFactory(new PropertyValueFactory<Prevoz, Date>("datum"));
		
		TableColumn <Prevoz, Prevoznik> tcPrevoznik = new TableColumn<Prevoz,Prevoznik>("prevoznik");		
		tcPrevoznik.setCellValueFactory(new PropertyValueFactory<Prevoz, Prevoznik>("prevoznik"));
		
		TableColumn <Prevoz, Double> tcKolicina = new TableColumn<Prevoz,Double>("količina");		
		tcKolicina.setCellValueFactory(new PropertyValueFactory<Prevoz, Double>("kolicna"));
		
		TableColumn <Prevoz, Proizvodjac> tcProizvodjac = new TableColumn<Prevoz,Proizvodjac>("proizvodjac");		
		tcProizvodjac.setCellValueFactory(new PropertyValueFactory<Prevoz, Proizvodjac>("proizvodjac"));
		
		tabelaPrevoza.getColumns().addAll(tcSifra,tcDatum,tcPrevoznik,tcKolicina,tcProizvodjac);
	
		tabelaPrevoza.setPrefHeight(482);
		
		updateTabelePrevoza();
		
		prevoziVB.getChildren().add(tabelaPrevoza);
		
		tabelaPrevoza.scrollTo(tabelaPrevoza.getItems().size());
		
	}
	
	public void updateTabelePrevoznika () {   				//dodaje djuture u tabelu kao observabl listu
		tabelaPrevoznika.getItems().clear();
		tabelaPrevoznika.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getPrevoznici()));
		tabelaPrevoznika.getItems().remove(Firma.getInstance().getTrenutnaGodina().getLicno());
		tabelaPrevoznika.scrollTo(tabelaPrevoznika.getItems().size()-1);
	}
	
	public void updateTabelePrevoza () {   				//dodaje djuture u tabelu kao observabl listu
		tabelaPrevoza.getItems().clear();
		tabelaPrevoza.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getPrevozi()));
		tabelaPrevoza.scrollTo(tabelaPrevoza.getItems().size());
	}
	
	private void pretragaPrevoznika() {
		HBox pretragaPrevoznikaHB = new HBox(10);
		pretragaPrevoznikaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaPrevoznikaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaPrevoznikaHB.getChildren().add(pl);			
		RBDanPretraga = new RadioButton("dan");
		RBPeriodPretraga = new RadioButton("period");
		TGPretraga = new ToggleGroup();
		RBDanPretraga.setToggleGroup(TGPretraga);
		RBPeriodPretraga.setToggleGroup(TGPretraga);
		pretragaPrevoznikaHB.getChildren().addAll(RBDanPretraga,RBPeriodPretraga);
		RBDanPretraga.setSelected(true);
		DPPocetniPretraga = new DatePicker();
		DPKrajnjiPretraga = new DatePicker();
		DPPocetniPretraga.setPrefWidth(120);
		DPKrajnjiPretraga.setPrefWidth(120);
		Lod = new Label("datum:");
		Ldo = new Label("do:");
		pretragaPrevoznikaHB.getChildren().addAll(Lod,DPPocetniPretraga);
		pretragaPrevoznikaHB.getChildren().add(new Label("ime i/ili prezime prevoznika:"));
		tfPretragaPrevoznika = new TextField();
		tfPretragaPrevoznika.setPrefWidth(150);
		pretragaPrevoznikaHB.getChildren().add(tfPretragaPrevoznika);	
		BPonistiPretraguPrevoznika = new  Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguPrevoznika.setGraphic(close);
		BPonistiPretraguPrevoznika.setDisable(true);
		pretragaPrevoznikaHB.getChildren().add(BPonistiPretraguPrevoznika);
		BPonistiPretraguPrevoznika.setOnAction(new PonistiPretraguPrevoznikaKontroler());
		
		RBDanPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBDanPretraga.isSelected()) {
					pretragaPrevoznikaHB.getChildren().removeAll(Ldo,DPKrajnjiPretraga);
					DPKrajnjiPretraga.setValue(null);
					DPPocetniPretraga.setValue(null);
					pozicija = 6;
					Lod.setText("datum:");
				}				
			}
		});
		 
		RBPeriodPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBPeriodPretraga.isSelected()) {
					pretragaPrevoznikaHB.getChildren().add(pozicija, Ldo);
					pretragaPrevoznikaHB.getChildren().add(pozicija+1, DPKrajnjiPretraga);
					DPKrajnjiPretraga.setValue(null);
					DPPocetniPretraga.setValue(null);
					pozicija=8;
					Lod.setText("od:");
				}
				
			}
		});
		
		tfPretragaPrevoznika.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				BPonistiPretraguPrevoznika.setDisable(false);
				RBFiltriraniObracun.setSelected(true);
				RBFiltrriraniPrevozniciSpisak.setSelected(true);
				tabelaPrevoznika.getItems().clear();
				tabelaPrevoznika.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajPrevoznike(tfPretragaPrevoznika.getText())));				
				tabelaPrevoza.getItems().clear();
				tabelaPrevoza.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajPrevoze(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), tfPretragaPrevoznika.getText())));				
				if(tfPretragaPrevoznika.getText().equals("") && DPPocetniPretraga.getValue()==null && DPKrajnjiPretraga.getValue() == null) {
					BPonistiPretraguPrevoznika.setDisable(true);
					RBsviPrevozniciSpisak.setSelected(true);
					RBSviObracun.setSelected(true);
				}
			}
		});
		
		DPPocetniPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	
				RBFiltriraniObracun.setSelected(true);
				RBFiltrriraniPrevozniciSpisak.setSelected(true);
				tabelaPrevoza.getItems().clear();
				tabelaPrevoza.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajPrevoze(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), tfPretragaPrevoznika.getText())));				
				if(DPPocetniPretraga.getValue() != null) {
					BPonistiPretraguPrevoznika.setDisable(false);

				}
				

			}
		});
		
		
		DPKrajnjiPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {	
				RBFiltriraniObracun.setSelected(true);
				RBFiltrriraniPrevozniciSpisak.setSelected(true);
				tabelaPrevoza.getItems().clear();
				tabelaPrevoza.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajPrevoze(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), tfPretragaPrevoznika.getText())));		
				if(DPKrajnjiPretraga.getValue() != null) {
					BPonistiPretraguPrevoznika.setDisable(false);

				}

			}
		});
		
		getChildren().add(pretragaPrevoznikaHB);
	}
 
	private void brzObracun() {
		HBox obracunHB = new HBox(10);
		obracunHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		obracunHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setFont(new Font(20));
		obracunHB.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		lll.setFont(new Font(15));
		obracunHB.getChildren().add(lll);
		RBSelektovaniObracun = new RadioButton("selektovanog prevoznika");
		RBSviObracun = new RadioButton("sve prvoze");
		RBFiltriraniObracun = new RadioButton("filtrirane prevoze");
		TGObracun = new ToggleGroup();
		RBSviObracun.setToggleGroup(TGObracun);
		RBFiltriraniObracun.setToggleGroup(TGObracun);
		RBSelektovaniObracun.setToggleGroup(TGObracun);
		RBSviObracun.setSelected(true);
		obracunHB.getChildren().addAll(RBSelektovaniObracun,RBFiltriraniObracun,RBSviObracun );
		BObracunaj = new Button("obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setOnAction(new ObracunajKontroler());
		obracunHB.getChildren().add(BObracunaj);
		
		getChildren().add(obracunHB);
			
	}
		
	private void spisakPrevoznika() {
		HBox spisakPrevoznikaHB = new HBox(10);
		spisakPrevoznikaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisakPrevoznikaHB.getChildren().add(lista);
		Label sl = new Label("Štampa spiska prevoznika:   ");
		sl.setFont(new Font(20));
		spisakPrevoznikaHB.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		kkk.setFont(new Font(15));
		spisakPrevoznikaHB.getChildren().add(kkk);
		RBsviPrevozniciSpisak = new RadioButton("sve");
		RBFiltrriraniPrevozniciSpisak = new RadioButton("filtrirane");
		RBsviPrevozniciSpisak.setSelected(true);
		TGSpisakPrevoznika = new ToggleGroup();
		RBFiltrriraniPrevozniciSpisak.setToggleGroup(TGSpisakPrevoznika);
		RBsviPrevozniciSpisak.setToggleGroup(TGSpisakPrevoznika);
		spisakPrevoznikaHB.getChildren().addAll(RBsviPrevozniciSpisak,RBFiltrriraniPrevozniciSpisak);
		Label ooo = new Label("Vrsta spiska:   ");
		ooo.setFont(new Font(15));
		spisakPrevoznikaHB.getChildren().add(ooo);
		RBSamoImenaSpisak = new RadioButton("samo podaci");
		RBPodaciSaSaldomSpisak = new RadioButton("podaci sa ukupnim kolicinama");
		TGVrstaSpiska = new ToggleGroup();
		RBSamoImenaSpisak.setToggleGroup(TGVrstaSpiska);
		RBPodaciSaSaldomSpisak.setToggleGroup(TGVrstaSpiska);
		RBSamoImenaSpisak.setSelected(true);
		spisakPrevoznikaHB.getChildren().addAll(RBSamoImenaSpisak,RBPodaciSaSaldomSpisak);
		BStampajSpisakPrevoznika = new Button("štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BStampajSpisakPrevoznika.setGraphic(ss);
		spisakPrevoznikaHB.getChildren().add(BStampajSpisakPrevoznika);
		BStampajSpisakPrevoznika.setOnAction(new StampajSpisakPrevoznikaKOntroler());
		getChildren().add(spisakPrevoznikaHB);				
	}

	private void StampaIzvestaja() {
		stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaIzvestajaHB.getChildren().add(st);
		Label l1 = new Label("Štampa izveštaja:   ");
		l1.setFont(new Font(20));
		stampaIzvestajaHB.getChildren().add(l1);
		Label l2 = new Label("Za selektovanog prevoznika   ");
		l2.setFont(new Font(15));
		stampaIzvestajaHB.getChildren().add(l2);
		ImageView close = new ImageView(Firma.getInstance().getPrintIco());
		BStampajIzvestaj = new Button("štampaj");
		BStampajIzvestaj.setGraphic(close);
		BStampajIzvestaj.setOnAction(new StampajIzvestajKontroler());
		stampaIzvestajaHB.getChildren().add(BStampajIzvestaj);
		getChildren().add(stampaIzvestajaHB);
		
	}


	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos		
		tfSifra.clear();
		tfIme.clear();
		tfPrezime.clear();
		tfOpis.clear();
		tfCenaPoKg.clear();
	}
		
	public static PrevozTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new PrevozTab();
		}
		return instance;
	}

	public boolean isUnosNovog() {
		return unosNovog;
	}

	public void setUnosNovog(boolean unosNovog) {
		this.unosNovog = unosNovog;
	}

	public boolean isIzmenaUToku() {
		return izmenaUToku;
	}

	public void setIzmenaUToku(boolean izmenaUToku) {
		this.izmenaUToku = izmenaUToku;
	}

	public RadioButton getRBDanPretraga() {
		return RBDanPretraga;
	}

	public void setRBDanPretraga(RadioButton rBDanPretraga) {
		RBDanPretraga = rBDanPretraga;
	}

	public RadioButton getRBPeriodPretraga() {
		return RBPeriodPretraga;
	}

	public Button getBStampajIzvestaj() {
		return BStampajIzvestaj;
	}

	public void setBStampajIzvestaj(Button bStampajIzvestaj) {
		BStampajIzvestaj = bStampajIzvestaj;
	}

	public HBox getStampaIzvestajaHB() {
		return stampaIzvestajaHB;
	}

	public void setStampaIzvestajaHB(HBox stampaIzvestajaHB) {
		this.stampaIzvestajaHB = stampaIzvestajaHB;
	}

	public void setRBPeriodPretraga(RadioButton rBPeriodPretraga) {
		RBPeriodPretraga = rBPeriodPretraga;
	}

	public ToggleGroup getTGPretraga() {
		return TGPretraga;
	}

	public RadioButton getRBSviObracun() {
		return RBSviObracun;
	}

	public void setRBSviObracun(RadioButton rBSviObracun) {
		RBSviObracun = rBSviObracun;
	}

	public RadioButton getRBFiltriraniObracun() {
		return RBFiltriraniObracun;
	}

	public void setRBFiltriraniObracun(RadioButton rBFiltriraniObracun) {
		RBFiltriraniObracun = rBFiltriraniObracun;
	}

	public RadioButton getRBSelektovaniObracun() {
		return RBSelektovaniObracun;
	}

	public void setRBSelektovaniObracun(RadioButton rBSelektovaniObracun) {
		RBSelektovaniObracun = rBSelektovaniObracun;
	}

	public ToggleGroup getTGObracun() {
		return TGObracun;
	}

	public void setTGObracun(ToggleGroup tGObracun) {
		TGObracun = tGObracun;
	}

	public Button getBObracunaj() {
		return BObracunaj;
	}

	public void setBObracunaj(Button bObracunaj) {
		BObracunaj = bObracunaj;
	}

	public void setTGPretraga(ToggleGroup tGPretraga) {
		TGPretraga = tGPretraga;
	}

	public Label getLod() {
		return Lod;
	}

	public void setLod(Label lod) {
		Lod = lod;
	}

	public Label getLdo() {
		return Ldo;
	}

	public void setLdo(Label ldo) {
		Ldo = ldo;
	}

	public int getPozicija() {
		return pozicija;
	}

	public void setPozicija(int pozicija) {
		this.pozicija = pozicija;
	}

	public DatePicker getDPPocetniPretraga() {
		return DPPocetniPretraga;
	}

	public void setDPPocetniPretraga(DatePicker dPPocetniPretraga) {
		DPPocetniPretraga = dPPocetniPretraga;
	}

	public DatePicker getDPKrajnjiPretraga() {
		return DPKrajnjiPretraga;
	}

	public void setDPKrajnjiPretraga(DatePicker dPKrajnjiPretraga) {
		DPKrajnjiPretraga = dPKrajnjiPretraga;
	}

	public HBox getUnosHB() {
		return unosHB;
	}

	public void setUnosHB(HBox unosHB) {
		this.unosHB = unosHB;
	}

	public TextField getTfSifra() {
		return tfSifra;
	}

	public void setTfSifra(TextField tfSifra) {
		this.tfSifra = tfSifra;
	}	

	public RadioButton getRBsviPrevozniciSpisak() {
		return RBsviPrevozniciSpisak;
	}

	public void setRBsviPrevozniciSpisak(RadioButton rBsviPrevozniciSpisak) {
		RBsviPrevozniciSpisak = rBsviPrevozniciSpisak;
	}

	public RadioButton getRBFiltrriraniPrevozniciSpisak() {
		return RBFiltrriraniPrevozniciSpisak;
	}

	public void setRBFiltrriraniPrevozniciSpisak(RadioButton rBFiltrriraniPrevozniciSpisak) {
		RBFiltrriraniPrevozniciSpisak = rBFiltrriraniPrevozniciSpisak;
	}

	public RadioButton getRBSamoImenaSpisak() {
		return RBSamoImenaSpisak;
	}

	public void setRBSamoImenaSpisak(RadioButton rBSamoImenaSpisak) {
		RBSamoImenaSpisak = rBSamoImenaSpisak;
	}

	public RadioButton getRBPodaciSaSaldomSpisak() {
		return RBPodaciSaSaldomSpisak;
	}

	public void setRBPodaciSaSaldomSpisak(RadioButton rBPodaciSaSaldomSpisak) {
		RBPodaciSaSaldomSpisak = rBPodaciSaSaldomSpisak;
	}

	public ToggleGroup getTGVrstaSpiska() {
		return TGVrstaSpiska;
	}

	public void setTGVrstaSpiska(ToggleGroup tGVrstaSpiska) {
		TGVrstaSpiska = tGVrstaSpiska;
	}

	public ToggleGroup getTGSpisakPrevoznika() {
		return TGSpisakPrevoznika;
	}

	public void setTGSpisakPrevoznika(ToggleGroup tGSpisakPrevoznika) {
		TGSpisakPrevoznika = tGSpisakPrevoznika;
	}

	public Button getBStampajSpisakPrevoznika() {
		return BStampajSpisakPrevoznika;
	}

	public void setBStampajSpisakPrevoznika(Button bStampajSpisakPrevoznika) {
		BStampajSpisakPrevoznika = bStampajSpisakPrevoznika;
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

	public TextField getTfOpis() {
		return tfOpis;
	}

	public void setTfOpis(TextField tfOpis) {
		this.tfOpis = tfOpis;
	}

	public TextField getTfCenaPoKg() {
		return tfCenaPoKg;
	}

	public void setTfCenaPoKg(TextField tfCenaPoKg) {
		this.tfCenaPoKg = tfCenaPoKg;
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

	public Button getBPonisti() {
		return BPonisti;
	}

	public void setBPonisti(Button bPonisti) {
		BPonisti = bPonisti;
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

	public TextField getTfPretragaPrevoznika() {
		return tfPretragaPrevoznika;
	}

	public void setTfPretragaPrevoznika(TextField tfPretragaPrevoznika) {
		this.tfPretragaPrevoznika = tfPretragaPrevoznika;
	}

	public Button getBPonistiPretraguPrevoznika() {
		return BPonistiPretraguPrevoznika;
	}

	public void setBPonistiPretraguPrevoznika(Button bPonistiPretraguPrevoznika) {
		BPonistiPretraguPrevoznika = bPonistiPretraguPrevoznika;
	}

	public static void setInstance(PrevozTab instance) {
		PrevozTab.instance = instance;
	}

	public HBox getZajednickoHB() {
		return zajednickoHB;
	}

	public void setZajednickoHB(HBox zajednickoHB) {
		this.zajednickoHB = zajednickoHB;
	}

	public VBox getPrevozniciVB() {
		return prevozniciVB;
	}

	public void setPrevozniciVB(VBox prevozniciVB) {
		this.prevozniciVB = prevozniciVB;
	}

	public VBox getPrevoziVB() {
		return prevoziVB;
	}

	public void setPrevoziVB(VBox prevoziVB) {
		this.prevoziVB = prevoziVB;
	}

	public TableView<Prevoznik> getTabelaPrevoznika() {
		return tabelaPrevoznika;
	}

	public void setTabelaPrevoznika(TableView<Prevoznik> tabelaPrevoznika) {
		this.tabelaPrevoznika = tabelaPrevoznika;
	}

	public TableView<Prevoz> getTabelaPrevoza() {
		return tabelaPrevoza;
	}

	public void setTabelaPrevoza(TableView<Prevoz> tabelaPrevoza) {
		this.tabelaPrevoza = tabelaPrevoza;
	}
	
	
	
	
}
