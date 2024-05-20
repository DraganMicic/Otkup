package Prevoz_Tab;

import java.util.Date;

import Main.ComboBoxAutoComplete;
import Prevoz_Tab.Prevoznik_ASCED.*;
import Prevoz_Tab.Tools_K.ObracunajKontroler;
import Prevoz_Tab.Tools_K.PonistiPretraguPrevoznikaKontroler;
import Prevoz_Tab.Tools_K.StampajIzvestajKontroler;
import Prevoz_Tab.Tools_K.StampajSpisakPrevoznikaKOntroler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import model.*;

public class PrevozTab extends VBox {
	
	private static PrevozTab instance;

	private HBox unosITabelaHb;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;

	private GridPane unosGp;		//inicijalizujem komponente za prevoznike
	private  Label lsifra;
	private TextField tfSifra;
	private Label lime;
	private TextField tfIme;
	private Label lprezime;
	private TextField tfPrezime;
	private  Label lopis;
	private TextField tfOpis;
	private Label lcenapokg;
	private TextField tfCenaPoKg;

	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private TableView<Prevoznik> tabelaPrevoznika;

	private HBox pretragaPrevoznikaHB;
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

	private  HBox spisakPrevoznikaHB;
	private RadioButton RBsviPrevozniciSpisak;
	private RadioButton RBFiltrriraniPrevozniciSpisak;
	private ToggleGroup TGSpisakPrevoznika;
	private RadioButton RBSamoImenaSpisak;
	private RadioButton RBPodaciSaSaldomSpisak;
	private ToggleGroup TGVrstaSpiska;
	private Button BStampajSpisakPrevoznika;

	private HBox obracunHB;
	private RadioButton RBSviObracun;
	private RadioButton RBFiltriraniObracun;
	private RadioButton RBSelektovaniObracun;
	private ToggleGroup TGObracun;
	private Button BObracunaj;

	private  ComboBox<Prevoznik> cbPrevoznikIzvestaj;
	private Button BStampajIzvestaj;
	private HBox stampaIzvestajaHB;
		
	private TableView<Prevoz> tabelaPrevoza; //inicijalizujem komponente za prevoze
	
	private PrevozTab() {       					//konstruktorrr

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Baza prevoznika:");
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
		unosGp.setMinWidth(690);

		unos();
		tabelaPrevoznika();
		tabelaPrevoznika.setPrefWidth(1300);
		tabelaPrevoznika.setPrefHeight(350);

		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		unosITabelaHb.getChildren().addAll(unosGp,separator2,tabelaPrevoznika);

		tabelaPrevoza();
		tabelaPrevoza.setPrefWidth(1300);
		tabelaPrevoza.setPrefHeight(400);

		//naslov2
		Label naslov2 = new Label("Prevozi:");
		naslov2.setFont(new Font(35));
		Separator separator1 = new Separator();
		this.getChildren().addAll(naslov2,separator1,tabelaPrevoza);

		//naslov3
		Label naslov3 = new Label("Alati:");
		naslov3.setFont(new Font(35));
		Separator separator3 = new Separator();
		this.getChildren().addAll(naslov3,separator3);

		//alati
		pretragaPrevoznika();
		brzObracun();
		spisakPrevoznika();
		StampaIzvestaja();
		this.getChildren().addAll(pretragaPrevoznikaHB,obracunHB,spisakPrevoznikaHB,stampaIzvestajaHB);

		//pocetni update



	}

	private void  unos(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodaj = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodaj.setGraphic(add);
		BDodaj.setPrefWidth(200);
		BDodaj.setOnAction(new DodajKontroler());

		BSacuvaj = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj.setGraphic(save);
		BSacuvaj.setPrefWidth(200);
		BSacuvaj.setOnAction(new SacuvajKontroler());
		BSacuvaj.setDisable(true);

		BPonisti = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonisti.setGraphic(cancel);
		BPonisti.setPrefWidth(200);
		BPonisti.setOnAction(new PonistiKontroler());
		BPonisti.setDisable(true);

		BIzmeni = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setPrefWidth(200);
		BIzmeni.setOnAction(new IzmeniKontroler());
		BIzmeni.setDisable(true);

		BObrisi = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setPrefWidth(200);
		BObrisi.setOnAction(new ObrisiKontroler());
		BObrisi.setDisable(true);

		//dodavanje gornjih dugmica
		unosGp.add(BDodaj,0,0,1,1);
		unosGp.add(BSacuvaj,1,0,1,1);
		unosGp.add(BPonisti,2,0,1,1);
		unosGp.add(BIzmeni,3,0,1,1);
		unosGp.add(BObrisi,4,0,1,1);

		tfSifra = new TextField();               //podesavam komponente
		tfSifra.setPrefWidth(200);

		tfIme = new TextField();
		tfIme.setPrefWidth(410);

		tfPrezime = new TextField();
		tfPrezime.setPrefWidth(410);

		tfOpis = new TextField();
		tfOpis.setPrefWidth(410);

		tfCenaPoKg = new TextField();
		tfCenaPoKg.setPrefWidth(200);
		tfCenaPoKg.setPromptText("[din]");

		lsifra = new Label("Šifra:");
		unosGp.add(lsifra,0,2,1,1);
		unosGp.add(tfSifra,1,2,1,1);

		lime = new Label("Ime:");
		unosGp.add(lime,0,3,1,1);
		unosGp.add(tfIme,1,3,2,1);

		lprezime = new Label("Prezime:");
		unosGp.add(lprezime,0,4,1,1);
		unosGp.add(tfPrezime,1,4,2,1);

		lopis = new Label("Opis:");
		unosGp.add(lopis,0,5,1,1);
		unosGp.add(tfOpis,1,5,2,1);

		lcenapokg = new Label("Cena po kg.:");
		unosGp.add(lcenapokg,0,6,1,1);
		unosGp.add(tfCenaPoKg,1,6,1,1);

		SetuUnosDisable();
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

		tcDatum.setPrefWidth(200);
		tcPrevoznik.setPrefWidth(350);
		tcKolicina.setPrefWidth(280);
		tcProizvodjac.setPrefWidth(350);
		
		tabelaPrevoza.getColumns().addAll(tcSifra,tcDatum,tcPrevoznik,tcKolicina,tcProizvodjac);
	
		tabelaPrevoza.setPrefHeight(482);
		
		updateTabelePrevoza();
		
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

		pretragaPrevoznikaHB = new HBox(10);
		pretragaPrevoznikaHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaPrevoznikaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaPrevoznikaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaPrevoznikaHB.getChildren().add(pl);
		Label l = new Label("  dan");
		l.setMinWidth(30);
		RBDanPretraga = new RadioButton();
		RBDanPretraga.setGraphic(l);
		RBPeriodPretraga = new RadioButton("period");
		TGPretraga = new ToggleGroup();
		RBDanPretraga.setToggleGroup(TGPretraga);
		RBPeriodPretraga.setToggleGroup(TGPretraga);
		pretragaPrevoznikaHB.getChildren().addAll(RBDanPretraga,RBPeriodPretraga);
		RBDanPretraga.setSelected(true);
		DPPocetniPretraga = new DatePicker();
		DPKrajnjiPretraga = new DatePicker();
		DPPocetniPretraga.setPrefWidth(160);
		DPKrajnjiPretraga.setPrefWidth(160);
		Lod = new Label("datum:");
		Ldo = new Label("do:");
		pretragaPrevoznikaHB.getChildren().addAll(Lod,DPPocetniPretraga);
		pretragaPrevoznikaHB.getChildren().add(new Label("prevoznik:"));
		tfPretragaPrevoznika = new TextField();
		tfPretragaPrevoznika.setPrefWidth(150);
		pretragaPrevoznikaHB.getChildren().add(tfPretragaPrevoznika);	
		BPonistiPretraguPrevoznika = new  Button("Poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguPrevoznika.setGraphic(close);
		BPonistiPretraguPrevoznika.setDisable(true);
		BPonistiPretraguPrevoznika.setPrefWidth(110);
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

	}
 
	private void brzObracun() {

		obracunHB = new HBox(10);
		obracunHB.setStyle("-fx-font: 17px \"Serif\";");
		obracunHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		obracunHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setStyle("-fx-font: 25px \"System\";");
		obracunHB.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
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
		BObracunaj = new Button("Obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setPrefWidth(130);
		BObracunaj.setOnAction(new ObracunajKontroler());
		obracunHB.getChildren().add(BObracunaj);
			
	}
		
	private void spisakPrevoznika() {

		spisakPrevoznikaHB = new HBox(10);
		spisakPrevoznikaHB.setStyle("-fx-font: 17px \"Serif\";");
		spisakPrevoznikaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisakPrevoznikaHB.getChildren().add(lista);
		Label sl = new Label("Štampa spiska prevoznika:   ");
		sl.setStyle("-fx-font: 25px \"System\";");
		spisakPrevoznikaHB.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		spisakPrevoznikaHB.getChildren().add(kkk);
		RBsviPrevozniciSpisak = new RadioButton("sve");
		RBFiltrriraniPrevozniciSpisak = new RadioButton("filtrirane");
		RBsviPrevozniciSpisak.setSelected(true);
		TGSpisakPrevoznika = new ToggleGroup();
		RBFiltrriraniPrevozniciSpisak.setToggleGroup(TGSpisakPrevoznika);
		RBsviPrevozniciSpisak.setToggleGroup(TGSpisakPrevoznika);
		spisakPrevoznikaHB.getChildren().addAll(RBsviPrevozniciSpisak,RBFiltrriraniPrevozniciSpisak);
		Label ooo = new Label("Vrsta spiska:   ");
		spisakPrevoznikaHB.getChildren().add(ooo);
		RBSamoImenaSpisak = new RadioButton("samo podaci");
		RBPodaciSaSaldomSpisak = new RadioButton("podaci sa ukupnim kolicinama");
		TGVrstaSpiska = new ToggleGroup();
		RBSamoImenaSpisak.setToggleGroup(TGVrstaSpiska);
		RBPodaciSaSaldomSpisak.setToggleGroup(TGVrstaSpiska);
		RBSamoImenaSpisak.setSelected(true);
		spisakPrevoznikaHB.getChildren().addAll(RBSamoImenaSpisak,RBPodaciSaSaldomSpisak);
		BStampajSpisakPrevoznika = new Button("Štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BStampajSpisakPrevoznika.setGraphic(ss);
		BStampajSpisakPrevoznika.setPrefWidth(110);
		spisakPrevoznikaHB.getChildren().add(BStampajSpisakPrevoznika);
		BStampajSpisakPrevoznika.setOnAction(new StampajSpisakPrevoznikaKOntroler());

	}

	private void StampaIzvestaja() {

		stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setStyle("-fx-font: 17px \"Serif\";");
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaIzvestajaHB.getChildren().add(st);
		Label l1 = new Label("Štampa izveštaja:   ");
		l1.setStyle("-fx-font: 25px \"System\";");
		stampaIzvestajaHB.getChildren().add(l1);
		Label l2 = new Label("Za prevoznika:   ");
		stampaIzvestajaHB.getChildren().add(l2);
		cbPrevoznikIzvestaj = new ComboBox<>();
		cbPrevoznikIzvestaj.setPrefWidth(300);
		stampaIzvestajaHB.getChildren().add(cbPrevoznikIzvestaj);
		ImageView close = new ImageView(Firma.getInstance().getPrintIco());
		BStampajIzvestaj = new Button("Štampaj");
		BStampajIzvestaj.setGraphic(close);
		BStampajIzvestaj.setOnAction(new StampajIzvestajKontroler());
		BStampajIzvestaj.setPrefWidth(110);
		stampaIzvestajaHB.getChildren().add(BStampajIzvestaj);

		updatecbPrevoznikIzvestaj();
		
	}

	public void updatecbPrevoznikIzvestaj(){

		cbPrevoznikIzvestaj.getItems().clear();
		cbPrevoznikIzvestaj.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getPrevoznici()));
		cbPrevoznikIzvestaj.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPrevoznikIzvestaj);

	}
	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos		
		tfSifra.clear();
		tfIme.clear();
		tfPrezime.clear();
		tfOpis.clear();
		tfCenaPoKg.clear();
	}

	public void SetuUnosEnable(){

		lsifra.setDisable(false);
		tfSifra.setDisable(false);
		lime.setDisable(false);
		tfIme.setDisable(false);
		lprezime.setDisable(false);
		tfPrezime.setDisable(false);
		lopis.setDisable(false);
		tfOpis.setDisable(false);
		lcenapokg.setDisable(false);
		tfCenaPoKg.setDisable(false);
	}

	public void SetuUnosDisable(){

		lsifra.setDisable(true);
		tfSifra.setDisable(true);
		lime.setDisable(true);
		tfIme.setDisable(true);
		lprezime.setDisable(true);
		tfPrezime.setDisable(true);
		lopis.setDisable(true);
		tfOpis.setDisable(true);
		lcenapokg.setDisable(true);
		tfCenaPoKg.setDisable(true);
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
		
	public static PrevozTab getInstance() {  //get instance 
		if (instance == null) {
			instance = new PrevozTab();
		}
		return instance;
	}

	public ComboBox<Prevoznik> getCbPrevoznikIzvestaj() {
		return cbPrevoznikIzvestaj;
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

	public RadioButton getRBSviObracun() {
		return RBSviObracun;
	}

	public RadioButton getRBFiltriraniObracun() {
		return RBFiltriraniObracun;
	}

	public RadioButton getRBSelektovaniObracun() {
		return RBSelektovaniObracun;
	}

	public DatePicker getDPPocetniPretraga() {
		return DPPocetniPretraga;
	}

	public DatePicker getDPKrajnjiPretraga() {
		return DPKrajnjiPretraga;
	}

	public TextField getTfSifra() {
		return tfSifra;
	}

	public RadioButton getRBsviPrevozniciSpisak() {
		return RBsviPrevozniciSpisak;
	}

	public RadioButton getRBFiltrriraniPrevozniciSpisak() {
		return RBFiltrriraniPrevozniciSpisak;
	}

	public RadioButton getRBSamoImenaSpisak() {
		return RBSamoImenaSpisak;
	}

	public RadioButton getRBPodaciSaSaldomSpisak() {
		return RBPodaciSaSaldomSpisak;
	}

	public TextField getTfIme() {
		return tfIme;
	}

	public TextField getTfPrezime() {
		return tfPrezime;
	}

	public TextField getTfOpis() {
		return tfOpis;
	}

	public TextField getTfCenaPoKg() {
		return tfCenaPoKg;
	}

	public Button getBDodaj() {
		return BDodaj;
	}

	public Button getBSacuvaj() {
		return BSacuvaj;
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

	public Button getBObrisi() {
		return BObrisi;
	}

	public TextField getTfPretragaPrevoznika() {
		return tfPretragaPrevoznika;
	}

	public Button getBPonistiPretraguPrevoznika() {
		return BPonistiPretraguPrevoznika;
	}

	public static void setInstance(PrevozTab instance) {
		PrevozTab.instance = instance;
	}

	public TableView<Prevoznik> getTabelaPrevoznika() {
		return tabelaPrevoznika;
	}

	public TableView<Prevoz> getTabelaPrevoza() {
		return tabelaPrevoza;
	}

}
