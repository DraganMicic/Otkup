package UnosIzlaza_Tab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import Main.ComboBoxAutoComplete;
import UnosIzlaza_Tab.Izlaz_ASCED.DodajKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.IzmeniKontroler;
import UnosIzlaza_Tab.Tools_K.ObracunajKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.ObrisiKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.PonistiKontroler;
import UnosIzlaza_Tab.Tools_K.PonistiPretraguKontroler1;
import UnosIzlaza_Tab.Izlaz_ASCED.SacuvajKontroler;
import UnosIzlaza_Tab.Tools_K.PonistiPretraguKontroler2;
import UnosIzlaza_Tab.Tools_K.StampajIzvestajKontroler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.Firma;
import model.Izlaz;
import model.Proizvodjac;
import model.UnosIzlaza;

public class UnosIzlazaTab extends VBox {
	
	private static UnosIzlazaTab instance;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;

	private HBox unosITabelaHb;

	private GridPane unosGp;
	private Label lsifra;
	private TextField tfSifra;
	private Label ldatum;
	private DatePicker dpDatum;
	private TextField tfSifraProizvodjaca;
	private Label lproizvodjac;
	private ComboBox<Proizvodjac> cbProizvodjac;
	private Label lizlaz;
	private ComboBox<Izlaz> cbIzlaz;
	private Label ljedmere;
	private Label lbrojotpremnice;
	private TextField tfBrOtpremnice;
	private Label lkolicina;
	private TextField tfKolicina;
	private Label ldatumobavestenje;

	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;

	private HBox trakaZaPretragu1HB;
	private HBox trakaZaPretragu2HB;
	private RadioButton RBDanPretraga;
	private RadioButton RBPeriodPretraga;
	private ToggleGroup TGDanPeriodPretraga;
	private DatePicker DPPocetniPretraga;
	private DatePicker DPKrajnjiPretraga;
	private ComboBox<Proizvodjac> CBProizvodjacPretraga;
	private ComboBox<Izlaz> CBIzlazPretraga;
	private Button BPonisitPretragu1;
	private Button BPonisitPretragu2;
	private Label Lod;
	private Label Ldo;
	private int pozicija = 6;

	private HBox obracunHB;
	private RadioButton RBSviObracun;
	private RadioButton RBFiltriraniObracun;
	private ToggleGroup TGObracun;
	private Button BObracunaj;

	private HBox stampaIzvestajaHB;
	private DatePicker DPPocetniIzvestaj;
	private DatePicker DPKrajnjiIzvestaj;
	private Button BStampajIzvestaj;
	
	private TableView<UnosIzlaza> tabela;


	private UnosIzlazaTab() {

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Unos izlaza (akontacija):");
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
		tabela();
		tabela.setPrefWidth(1300);
		tabela.setPrefHeight(530);

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
		trakaZaStampuIzvestaja();
		this.getChildren().addAll(trakaZaPretragu1HB,trakaZaPretragu2HB,obracunHB,stampaIzvestajaHB);
		
		//pocetni update
		
		updateCBProizvodjac();
		upodateCBIzlaz();
		updateCBProizvodjacPretraga();
		updateCBIzlazPretraga();
		
		getBDodaj().requestFocus();
		
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


		tfSifra = new TextField();
		tfSifra.setPrefWidth(200);

		ldatumobavestenje = new Label("");
		dpDatum = new DatePicker();
		dpDatum.setPrefWidth(410);
		dpDatum.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,LocalDate newValue) {
				LocalDate currentDate = LocalDate.now();
				if(observable.getValue()==null)
					ldatumobavestenje.setText("");
				else
				if(!observable.getValue().isEqual(currentDate))
					ldatumobavestenje.setText("(nije današnji!)");
				else
					ldatumobavestenje.setText("(današnji)");

			}
		});

		tfSifraProizvodjaca = new TextField();
		tfSifraProizvodjaca.setPrefWidth(200);
		tfSifraProizvodjaca.setPromptText("(šifra)");
		tfSifraProizvodjaca.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.equals("")){
					int sifraProizvodjaca;
					try {
						sifraProizvodjaca = Integer.valueOf(newValue);
					} catch (Exception e) {
						Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
						ada.show();
						Platform.runLater(() -> {
							tfSifraProizvodjaca.clear();
						});
						return;
					}
					Proizvodjac p = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(sifraProizvodjaca);
					if(p != null) {
						cbProizvodjac.getSelectionModel().select(p);
					}else{
						Alert adda = new Alert(Alert.AlertType.ERROR, "Ne postoji proizvođač sa izabranom šifrom!");
						adda.show();
						Platform.runLater(() -> {
							tfSifraProizvodjaca.clear();
						});
						return;
					}
				}else{
					cbProizvodjac.getSelectionModel().clearSelection();
				}
			}
		});

		ljedmere = new Label("");
		cbProizvodjac = new ComboBox<Proizvodjac>();
		cbProizvodjac.setPrefWidth(620);

		cbIzlaz = new ComboBox<Izlaz>();
		cbIzlaz.setPrefWidth(620);

		tfBrOtpremnice = new TextField();
		tfBrOtpremnice.setPrefWidth(410);

		tfKolicina = new TextField();
		tfKolicina.setPrefWidth(200);

		lsifra = new Label("Šifra unosa:");
		unosGp.add(lsifra,0,2,1,1);
		unosGp.add(tfSifra,1,2,1,1);

		ldatum = new Label("Datum:");
		unosGp.add(ldatum,0,3,1,1);
		unosGp.add(dpDatum,1,3,2,1);
		unosGp.add(ldatumobavestenje,3,3,2,1);
		unosGp.setHalignment(ldatumobavestenje, HPos.LEFT);

		lproizvodjac = new Label("Proizvođač:");
		unosGp.add(lproizvodjac,0,4,1,1);
		unosGp.add(cbProizvodjac,1,4,3,1);
		unosGp.add(tfSifraProizvodjaca,4,4,1,1);

		lizlaz = (new Label("Vrsta izlaza:"));
		unosGp.add(lizlaz,0,5,1,1);
		unosGp.add(cbIzlaz,1,5,3,1);

		lbrojotpremnice = new Label("Broj otpremnice/računa:");
		unosGp.add(lbrojotpremnice,0,6,2,1);
		unosGp.add(tfBrOtpremnice,2,6,2,1);

		lkolicina = new Label("Količina:");
		unosGp.add(lkolicina,0,7,1,1);
		unosGp.add(tfKolicina,1,7,1,1);
		unosGp.add(ljedmere,2,7,1,1);
		unosGp.setHalignment(ljedmere, HPos.LEFT);

		SetUnosDisable();

	}

	@SuppressWarnings({ "unchecked" })	
	private void tabela() {
		tabela = new TableView<>();
		
		TableColumn <UnosIzlaza, Integer> tcSifra = new TableColumn<UnosIzlaza,Integer>("šifra:");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, Integer>("sifra"));
		
		TableColumn <UnosIzlaza, Date> tcDatum = new TableColumn<UnosIzlaza,Date>("datum:");		
		tcDatum.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, Date>("datum"));
		
		TableColumn <UnosIzlaza, Proizvodjac> tcProizvodjac = new TableColumn<UnosIzlaza,Proizvodjac>("proizvođač:");		
		tcProizvodjac.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, Proizvodjac>("proizvodjac"));
		
		TableColumn <UnosIzlaza, Izlaz> tcIzlaz = new TableColumn<UnosIzlaza,Izlaz>("vrsta izlaza:");	
		tcIzlaz.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, Izlaz>("izlaz"));
		
		TableColumn <UnosIzlaza, String> tcBrOtpremnice = new TableColumn<UnosIzlaza,String>("broj otpremnice/računa");		
		tcBrOtpremnice.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, String>("brojOtpremnice"));
		
		TableColumn <UnosIzlaza, Double> tcKolicina = new TableColumn<UnosIzlaza,Double>("količina:");		
		tcKolicina.setCellValueFactory(new PropertyValueFactory<UnosIzlaza, Double>("kolicina"));

		TableColumn <UnosIzlaza, String> jedmere = new TableColumn<UnosIzlaza,String>("jed. mere:");
		jedmere.setCellValueFactory(cellData -> {
			UnosIzlaza u = cellData.getValue();
			String j = u.getIzlaz().getJedinicaMere();
			return new SimpleStringProperty(j);
		});

		
		tabela.getColumns().addAll(tcSifra,tcDatum,tcProizvodjac,tcIzlaz,tcBrOtpremnice,tcKolicina,jedmere);

		updateTabele();
		
		tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovog == false && izmenaUToku == false) {
		       BObrisi.setDisable(false);
		       BIzmeni.setDisable(false);
		       BPonisti.setDisable(false);
		       BDodaj.setDisable(true);		       
		    }
		});
		
		tabela.scrollTo(tabela.getItems().size()-1);
		
	}
	
	public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()));
		tabela.scrollTo(tabela.getItems().size()-1);
	}
	
	public void updateCBProizvodjac(){

		cbProizvodjac.getItems().clear();
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		cbProizvodjac.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		cbProizvodjac.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null)
				tfSifraProizvodjaca.setText(String.valueOf(newValue.getSifra()));
			else
				tfSifraProizvodjaca.clear();
		});
	}

	public void upodateCBIzlaz() {

		cbIzlaz.getItems().clear();
		cbIzlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getIzlazi());
		cbIzlaz.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Izlaz>(cbIzlaz);
		cbIzlaz.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue==null)
				ljedmere.setText("");
			else
				ljedmere.setText(observable.getValue().getJedinicaMere());
		});
	}
	
	private void trakaZaPretragu() {

		trakaZaPretragu1HB = new HBox(10);
		trakaZaPretragu1HB.setStyle("-fx-font: 17px \"Serif\";");
		trakaZaPretragu1HB.setAlignment(Pos.CENTER_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		trakaZaPretragu1HB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		trakaZaPretragu1HB.getChildren().add(pl);
		RBDanPretraga = new RadioButton("dan");
		RBPeriodPretraga = new RadioButton("period");
		TGDanPeriodPretraga = new ToggleGroup();
		RBDanPretraga.setToggleGroup(TGDanPeriodPretraga);
		RBPeriodPretraga.setToggleGroup(TGDanPeriodPretraga);
		trakaZaPretragu1HB.getChildren().addAll(RBDanPretraga,RBPeriodPretraga);
		RBDanPretraga.setSelected(true);
		DPPocetniPretraga = new DatePicker();
		DPKrajnjiPretraga = new DatePicker();
		DPPocetniPretraga.setPrefWidth(160);
		DPKrajnjiPretraga.setPrefWidth(160);
		Lod = new Label("datum:");
		Ldo = new Label("do:");
		trakaZaPretragu1HB.getChildren().addAll(Lod,DPPocetniPretraga);
		BPonisitPretragu1 = new Button("Poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonisitPretragu1.setGraphic(close);
		BPonisitPretragu1.setDisable(true);
		BPonisitPretragu1.setPrefWidth(110);
		trakaZaPretragu1HB.getChildren().add(BPonisitPretragu1);
		BPonisitPretragu1.setOnAction(new PonistiPretraguKontroler1());

		trakaZaPretragu2HB = new HBox(10);
		trakaZaPretragu2HB.setStyle("-fx-font: 17px \"Serif\";");
		trakaZaPretragu2HB.setAlignment(Pos.CENTER_LEFT);
		ImageView search2 = new ImageView(Firma.getInstance().getSearchIco());
		trakaZaPretragu2HB.getChildren().add(search2);
		Label pl2 = new Label("Pretraga/filtriranje:  ");
		pl2.setStyle("-fx-font: 25px \"System\";");
		trakaZaPretragu2HB.getChildren().add(pl2);
		CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
		CBProizvodjacPretraga.setPrefWidth(300);
		Label l1 = new Label("proizvođač:");
		CBIzlazPretraga = new ComboBox<Izlaz>();
		CBIzlazPretraga.setPrefWidth(300);
		Label l2 = new Label("izlaz:");
		trakaZaPretragu2HB.getChildren().addAll(l1,CBProizvodjacPretraga,l2,CBIzlazPretraga);
		BPonisitPretragu2 = new Button("Poništi");
		ImageView close2 = new ImageView(Firma.getInstance().getCloseIco());
		BPonisitPretragu2.setGraphic(close2);
		BPonisitPretragu2.setPrefWidth(110);
		BPonisitPretragu2.setDisable(true);
		trakaZaPretragu2HB.getChildren().add(BPonisitPretragu2);
		BPonisitPretragu2.setOnAction(new PonistiPretraguKontroler2());
		
		RBDanPretraga.setOnAction(event -> {
			trakaZaPretragu2HB.getChildren().removeAll(Ldo, DPKrajnjiPretraga);
			DPPocetniPretraga.setValue(null);
			DPKrajnjiPretraga.setValue(null);
			pozicija = 6;
			Lod.setText("datum:");
		});
		
		RBPeriodPretraga.setOnAction(event -> {
			trakaZaPretragu1HB.getChildren().add(pozicija, Ldo);
			trakaZaPretragu1HB.getChildren().add(pozicija+1, DPKrajnjiPretraga);
			DPPocetniPretraga.setValue(null);
			DPKrajnjiPretraga.setValue(null);
			pozicija=8;
			Lod.setText("od:");
		});
		
		DPPocetniPretraga.setOnAction(event -> {
			tabela.getItems().clear();
			RBFiltriraniObracun.setSelected(true);
			ArrayList<UnosIzlaza> uilist = new ArrayList<UnosIzlaza>();
			uilist = null;
			uilist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseIzlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBIzlazPretraga.getSelectionModel().getSelectedItem());
			tabela.getItems().addAll(uilist);

			if(DPPocetniPretraga.getValue()!=null) {
				BPonisitPretragu1.setDisable(false);
			}

		});
		
		DPKrajnjiPretraga.setOnAction(event -> {
			tabela.getItems().clear();
			ArrayList<UnosIzlaza> uilist = new ArrayList<UnosIzlaza>();
			uilist = null;
			uilist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseIzlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBIzlazPretraga.getSelectionModel().getSelectedItem());
			tabela.getItems().addAll(uilist);

			if(DPKrajnjiPretraga.getValue()!=null) {
				BPonisitPretragu1.setDisable(false);
			}

		});
		
		updateCBIzlazPretraga();
		updateCBProizvodjacPretraga();

	}
	
	public void updateCBProizvodjacPretraga() {

		CBProizvodjacPretraga.getItems().clear();
		CBProizvodjacPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		CBProizvodjacPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(CBProizvodjacPretraga);
		CBProizvodjacPretraga.valueProperty().addListener((observable, oldValue, newValue) -> {
			RBFiltriraniObracun.setSelected(true);
			tabela.getItems().clear();
			ArrayList<UnosIzlaza> uilist = new ArrayList<UnosIzlaza>();
			uilist = null;
			uilist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseIzlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBIzlazPretraga.getSelectionModel().getSelectedItem());
			tabela.getItems().addAll(uilist);

			if(newValue != null)
				BPonisitPretragu2.setDisable(false);
		});
	}
	
	public void updateCBIzlazPretraga() {

		CBIzlazPretraga.getItems().clear();
		CBIzlazPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getIzlazi());
		CBIzlazPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Izlaz>(CBIzlazPretraga);
		CBIzlazPretraga.valueProperty().addListener(new ChangeListener<Izlaz>() {
			public void changed(ObservableValue<? extends Izlaz> observable, Izlaz oldValue, Izlaz newValue) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosIzlaza> uilist = new ArrayList<UnosIzlaza>();
				uilist = null;
				uilist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseIzlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBIzlazPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uilist);
				
				if(newValue != null)
					BPonisitPretragu2.setDisable(false);
				
			}
		});
	}

	private void trakaZaObracun() {

		obracunHB = new HBox(10);
		obracunHB.setStyle("-fx-font: 17px \"Serif\";");
		obracunHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		obracunHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setStyle("-fx-font: 25px \"System\";");
		ol.setFont(new Font(20));
		obracunHB.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		obracunHB.getChildren().add(lll);
		RBSviObracun = new RadioButton("sve");
		RBFiltriraniObracun = new RadioButton("filtrirane");
		TGObracun = new ToggleGroup();
		RBSviObracun.setToggleGroup(TGObracun);
		RBFiltriraniObracun.setToggleGroup(TGObracun);
		RBSviObracun.setSelected(true);
		obracunHB.getChildren().addAll(RBSviObracun,RBFiltriraniObracun);
		BObracunaj = new Button("Obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setPrefWidth(130);
		BObracunaj.setOnAction(new ObracunajKontroler());
		obracunHB.getChildren().add(BObracunaj);

	}
	
	private void trakaZaStampuIzvestaja() {

		stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setStyle("-fx-font: 17px \"Serif\";");
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaIzvestajaHB.getChildren().add(st);
		Label l1 = new Label("Štampa izveštaja:   ");
		l1.setStyle("-fx-font: 25px \"System\";");
		stampaIzvestajaHB.getChildren().add(l1);
		DPPocetniIzvestaj = new DatePicker();
		DPPocetniIzvestaj.setPrefWidth(160);
		DPKrajnjiIzvestaj = new DatePicker();
		DPKrajnjiIzvestaj.setPrefWidth(160);
		Label l2 = new Label("za period od:");
		Label l3 = new Label("do:");
		stampaIzvestajaHB.getChildren().addAll(l2,DPPocetniIzvestaj,l3,DPKrajnjiIzvestaj);
		BStampajIzvestaj = new Button("Štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStampajIzvestaj.setGraphic(ici);
		BStampajIzvestaj.setPrefWidth(110);
		BStampajIzvestaj.setOnAction(new StampajIzvestajKontroler());
		stampaIzvestajaHB.getChildren().add(BStampajIzvestaj);
		
	}
	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos

		tfSifra.clear();
		dpDatum.setValue(null);
		cbIzlaz.getSelectionModel().clearSelection();
		cbProizvodjac.getSelectionModel().clearSelection();
		tfSifraProizvodjaca.clear();
		updateCBProizvodjac();
		upodateCBIzlaz();
		tfBrOtpremnice.clear();
		tfKolicina.clear();
	}

	public void SetUnosDisable(){

		lsifra.setDisable(true);
		tfSifra.setDisable(true);
		ldatum.setDisable(true);
		ldatumobavestenje.setDisable(true);
		dpDatum.setDisable(true);
		lproizvodjac.setDisable(true);
		cbProizvodjac.setDisable(true);
		tfSifraProizvodjaca.setDisable(true);
		lizlaz.setDisable(true);
		cbIzlaz.setDisable(true);
		lbrojotpremnice.setDisable(true);
		tfBrOtpremnice.setDisable(true);
		lkolicina.setDisable(true);
		tfKolicina.setDisable(true);
	}

	public void SetUnosEnable(){

		lsifra.setDisable(false);
		tfSifra.setDisable(false);
		ldatum.setDisable(false);
		ldatumobavestenje.setDisable(false);
		dpDatum.setDisable(false);
		lproizvodjac.setDisable(false);
		cbProizvodjac.setDisable(false);
		tfSifraProizvodjaca.setDisable(false);
		lizlaz.setDisable(false);
		cbIzlaz.setDisable(false);
		lbrojotpremnice.setDisable(false);
		tfBrOtpremnice.setDisable(false);
		lkolicina.setDisable(false);
		tfKolicina.setDisable(false);
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
	
	public static UnosIzlazaTab getInstance() {
		if(instance == null) {
			instance = new UnosIzlazaTab();
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

	public TextField getTfSifra() {
		return tfSifra;
	}

	public DatePicker getDatePicker() {
		return dpDatum;
	}

	public ComboBox<Proizvodjac> getCbProizvodjac() {
		return cbProizvodjac;
	}

	public ComboBox<Izlaz> getCbIzlaz() {
		return cbIzlaz;
	}

	public TextField getTfKolicina() {
		return tfKolicina;
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

	public TableView<UnosIzlaza> getTabela() {
		return tabela;
	}

	public static void setInstance(UnosIzlazaTab instance) {
		UnosIzlazaTab.instance = instance;
	}
	
	public TextField getTfBrOtpremnice() {
		return tfBrOtpremnice;
	}

	public DatePicker getDPPocetniPretraga() {
		return DPPocetniPretraga;
	}

	public DatePicker getDPKrajnjiPretraga() {
		return DPKrajnjiPretraga;
	}

	public ComboBox<Izlaz> getCBIzlazPretraga() {
		return CBIzlazPretraga;
	}

	public Button getBPonisitPretragu1() {
		return BPonisitPretragu1;
	}

	public Button getBPonisitPretragu2() {
		return BPonisitPretragu2;
	}

	public RadioButton getRBSviObracun() {
		return RBSviObracun;
	}

	public RadioButton getRBFiltriraniObracun() {
		return RBFiltriraniObracun;
	}

	public DatePicker getDPPocetniIzvestaj() {
		return DPPocetniIzvestaj;
	}

	public DatePicker getDPKrajnjiIzvestaj() {
		return DPKrajnjiIzvestaj;
	}

	public DatePicker getDpDatum() {
		return dpDatum;
	}
}
