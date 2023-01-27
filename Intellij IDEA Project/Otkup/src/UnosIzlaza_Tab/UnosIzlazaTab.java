package UnosIzlaza_Tab;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import Main.ComboBoxAutoComplete;
import UnosIzlaza_Tab.Izlaz_ASCED.DodajKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.IzmeniKontroler;
import UnosIzlaza_Tab.Tools_K.ObracunajKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.ObrisiKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.PonistiKontroler;
import UnosIzlaza_Tab.Tools_K.PonistiPretraguKontroler;
import UnosIzlaza_Tab.Izlaz_ASCED.SacuvajKontroler;
import UnosIzlaza_Tab.Tools_K.StampajIzvestajKontroler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Firma;
import model.Izlaz;
import model.Proizvodjac;
import model.UnosIzlaza;

public class UnosIzlazaTab extends VBox {
	
	private static UnosIzlazaTab instance;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;
	
	private TextField tfSifra;
	private DatePicker dpDatum;
	private TextField tfSifraProizvodjaca;
	private ComboBox<Proizvodjac> cbProizvodjac;	
	private ComboBox<Izlaz> cbIzlaz;
	private TextField tfBrOtpremnice;
	private TextField tfKolicina;
	
	private FlowPane unosFp;
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private RadioButton RBDanPretraga;
	private RadioButton RBPeriodPretraga;
	private ToggleGroup TGDanPeriodPretraga;
	private DatePicker DPPocetniPretraga;
	private DatePicker DPKrajnjiPretraga;
	private ComboBox<Proizvodjac> CBProizvodjacPretraga;
	private ComboBox<Izlaz> CBIzlazPretraga;
	private Button BPonisitPretragu;
	private HBox trakaZaPretraguHB;
	private Label Lod;
	private Label Ldo;
	private int pozicija = 6;
	
	private RadioButton RBSviObracun;
	private RadioButton RBFiltriraniObracun;
	private ToggleGroup TGObracun;
	private Button BObracunaj;
	
	private DatePicker DPPocetniIzvestaj;
	private DatePicker DPKrajnjiIzvestaj;
	private Button BStampajIzvestaj;
	
	private TableView<UnosIzlaza> tabela;
	
	private UnosIzlazaTab() {
		
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		Label naslov = new Label("Unos izlaza (akontacija):");
		naslov.setFont(new Font(35));
		this.getChildren().add(naslov);
		
		trakZaUnos();
		trakaKomandi();
		tabela();
		trakaZaPretragu();
		trakaZaObracun();
		trakaZaStampuIzvestaja();
	}
	
	private void trakZaUnos() {
		
		tfSifra = new TextField();
		tfSifra.setPrefWidth(40);
		dpDatum = new DatePicker();
		tfSifraProizvodjaca = new TextField();
		tfSifraProizvodjaca.setPrefWidth(40);
		cbProizvodjac = new ComboBox<Proizvodjac>();				
		cbIzlaz = new ComboBox<Izlaz>();		
		tfBrOtpremnice = new TextField();
		tfBrOtpremnice.setPrefWidth(150);		
		tfKolicina = new TextField();
		tfKolicina.setPrefWidth(100);	
		
		unosFp = new FlowPane();
		unosFp.setVgap(10);
		unosFp.setHgap(10);
		unosFp.setAlignment(Pos.BASELINE_LEFT);
		unosFp.getChildren().add(new Label("šifra:"));
		unosFp.getChildren().add(tfSifra);
		unosFp.getChildren().add(new Label("datum:"));
		unosFp.getChildren().add(dpDatum);
		unosFp.getChildren().add(new Label("proizvođač:"));
		unosFp.getChildren().add(cbProizvodjac);
		unosFp.getChildren().add(new Label("šifra proizvođača:"));
		unosFp.getChildren().add(tfSifraProizvodjaca);
		unosFp.getChildren().add(new Label("vrsta izlaza:"));
		unosFp.getChildren().add(cbIzlaz);
		unosFp.getChildren().add(new Label("broj otpremnice/računa"));
		unosFp.getChildren().add(tfBrOtpremnice);
		HBox kolicinaHB = new HBox(10);
		kolicinaHB.getChildren().addAll(new Label("količina:"),tfKolicina);
		unosFp.getChildren().add(kolicinaHB);

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
						tfSifraProizvodjaca.clear();
						return;
					}
					Proizvodjac p = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(sifraProizvodjaca);
					if(p != null)
						cbProizvodjac.getSelectionModel().select(p);
					else
						updateCBProizvodjac();
				}else{
					updateCBProizvodjac();
				}
			}
		});
				
		getChildren().add(unosFp);
		
		unosFp.setDisable(true);
		
		updateCBProizvodjac();
		upodateCBIzlaz();
	}
	
	private void trakaKomandi(){
		
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
		BIzmeni.setDisable(true);
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setOnAction(new IzmeniKontroler());
		
		BObrisi = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setDisable(true);
		BObrisi.setOnAction(new ObrisiKontroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodaj,BSacuvaj,BPonisti,BIzmeni,BObrisi);
		getChildren().add(komandeHB);
		
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
		
		tabela.getColumns().addAll(tcSifra,tcDatum,tcProizvodjac,tcIzlaz,tcBrOtpremnice,tcKolicina);
		
		updateTabele();
		
		getChildren().add(tabela);		
		
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
		unosFp.getChildren().remove(cbProizvodjac);
		cbProizvodjac = new ComboBox<Proizvodjac>();
		unosFp.getChildren().add(5, cbProizvodjac);
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		cbProizvodjac.setTooltip(new Tooltip());



		//new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		cbProizvodjac.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null)
				tfSifraProizvodjaca.setText(String.valueOf(newValue.getSifra()));
			else
				tfSifraProizvodjaca.setText("");
			cbProizvodjac.requestFocus();

		});
	}

	public void upodateCBIzlaz() {
		unosFp.getChildren().remove(cbIzlaz);
		cbIzlaz = new ComboBox<Izlaz>();
		unosFp.getChildren().add(9,cbIzlaz);
		cbIzlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getIzlazi());
		cbIzlaz.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Izlaz>(cbIzlaz);
	}
	
	private void trakaZaPretragu() {
		trakaZaPretraguHB = new HBox(10);
		trakaZaPretraguHB.setAlignment(Pos.CENTER_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		trakaZaPretraguHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		trakaZaPretraguHB.getChildren().add(pl);
		RBDanPretraga = new RadioButton("dan");
		RBPeriodPretraga = new RadioButton("period");
		TGDanPeriodPretraga = new ToggleGroup();
		RBDanPretraga.setToggleGroup(TGDanPeriodPretraga);
		RBPeriodPretraga.setToggleGroup(TGDanPeriodPretraga);
		trakaZaPretraguHB.getChildren().addAll(RBDanPretraga,RBPeriodPretraga);
		RBDanPretraga.setSelected(true);
		DPPocetniPretraga = new DatePicker();
		DPKrajnjiPretraga = new DatePicker();
		DPPocetniPretraga.setPrefWidth(120);
		DPKrajnjiPretraga.setPrefWidth(120);
		Lod = new Label("datum:");
		Ldo = new Label("do:");
		trakaZaPretraguHB.getChildren().addAll(Lod,DPPocetniPretraga);
		CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
		Label l1 = new Label("proizvođač:");
		CBIzlazPretraga = new ComboBox<Izlaz>();
		Label l2 = new Label("izlaz:");
		trakaZaPretraguHB.getChildren().addAll(l1,CBProizvodjacPretraga,l2,CBIzlazPretraga);
		BPonisitPretragu = new Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonisitPretragu.setGraphic(close);
		BPonisitPretragu.setDisable(true);
		trakaZaPretraguHB.getChildren().add(BPonisitPretragu);		
		BPonisitPretragu.setOnAction(new PonistiPretraguKontroler());
		
		RBDanPretraga.setOnAction(event -> {
			trakaZaPretraguHB.getChildren().removeAll(Ldo, DPKrajnjiPretraga);
			DPPocetniPretraga.setValue(null);
			DPKrajnjiPretraga.setValue(null);
			pozicija = 6;
			Lod.setText("datum:");
		});
		
		RBPeriodPretraga.setOnAction(event -> {
			trakaZaPretraguHB.getChildren().add(pozicija, Ldo);
			trakaZaPretraguHB.getChildren().add(pozicija+1, DPKrajnjiPretraga);
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
				BPonisitPretragu.setDisable(false);
			}

		});
		
		DPKrajnjiPretraga.setOnAction(event -> {
			tabela.getItems().clear();
			ArrayList<UnosIzlaza> uilist = new ArrayList<UnosIzlaza>();
			uilist = null;
			uilist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseIzlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPKrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBIzlazPretraga.getSelectionModel().getSelectedItem());
			tabela.getItems().addAll(uilist);

			if(DPKrajnjiPretraga.getValue()!=null) {
				BPonisitPretragu.setDisable(false);
			}

		});
		
		updateCBIzlazPretraga();
		updateCBProizvodjacPretraga();
		
		getChildren().add(trakaZaPretraguHB);
	}
	
	public void updateCBProizvodjacPretraga() {
		trakaZaPretraguHB.getChildren().remove(CBProizvodjacPretraga);
		CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
		trakaZaPretraguHB.getChildren().add(pozicija+1, CBProizvodjacPretraga);
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
				BPonisitPretragu.setDisable(false);
		});
	}
	
	public void updateCBIzlazPretraga() {
		trakaZaPretraguHB.getChildren().remove(CBIzlazPretraga);
		CBIzlazPretraga = new ComboBox<Izlaz>();
		trakaZaPretraguHB.getChildren().add(pozicija+3, CBIzlazPretraga);
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
					BPonisitPretragu.setDisable(false);
				
			}
		});
	}

	private void trakaZaObracun() {
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
		RBSviObracun = new RadioButton("sve");
		RBFiltriraniObracun = new RadioButton("filtrirane");
		TGObracun = new ToggleGroup();
		RBSviObracun.setToggleGroup(TGObracun);
		RBFiltriraniObracun.setToggleGroup(TGObracun);
		RBSviObracun.setSelected(true);
		obracunHB.getChildren().addAll(RBSviObracun,RBFiltriraniObracun);
		BObracunaj = new Button("obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunaj.setGraphic(obracunaj);
		BObracunaj.setOnAction(new ObracunajKontroler());
		obracunHB.getChildren().add(BObracunaj);
		
		getChildren().add(obracunHB);		
	}
	
	private void trakaZaStampuIzvestaja() {
		HBox stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaIzvestajaHB.getChildren().add(st);
		Label l1 = new Label("Štampa izveštaja:   ");
		l1.setFont(new Font(20));
		stampaIzvestajaHB.getChildren().add(l1);
		DPPocetniIzvestaj = new DatePicker();
		DPPocetniIzvestaj.setPrefWidth(120);
		DPKrajnjiIzvestaj = new DatePicker();
		DPKrajnjiIzvestaj.setPrefWidth(120);
		Label l2 = new Label("za period od:");
		Label l3 = new Label("do:");
		stampaIzvestajaHB.getChildren().addAll(l2,DPPocetniIzvestaj,l3,DPKrajnjiIzvestaj);
		BStampajIzvestaj = new Button("štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStampajIzvestaj.setGraphic(ici);
		BStampajIzvestaj.setOnAction(new StampajIzvestajKontroler());
		stampaIzvestajaHB.getChildren().add(BStampajIzvestaj);
		this.getChildren().add(stampaIzvestajaHB);
		
		
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
	
	public FlowPane getUnosFp() { return unosFp; }
	
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

	public Button getBPonisitPretragu() {
		return BPonisitPretragu;
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
