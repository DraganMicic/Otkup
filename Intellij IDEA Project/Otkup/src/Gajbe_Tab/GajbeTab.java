package Gajbe_Tab;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;

import Main.ComboBoxAutoComplete;
import Gajbe_Tab.Gajba_ASCED.DodajGajbuKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.DodajUnosGajbiKontroler;
import Gajbe_Tab.Gajba_ASCED.IzmeniGajbuKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.IzmeniUnosGajbiKontroler;
import Gajbe_Tab.Tools_K.ObracunajGajbeKontroler;
import Gajbe_Tab.Tools_K.ObracunajUnoseGajbiKontroler;
import Gajbe_Tab.Gajba_ASCED.ObrisiGajbuKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.ObrisiUnosGajbiKontroler;
import Gajbe_Tab.Tools_K.PonisitiPretraguUnosaGajbi;
import Gajbe_Tab.Gajba_ASCED.PonistiGajbuKontroler;
import Gajbe_Tab.Tools_K.PonistiPretraguGajbiKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.PonistiUnosGajbiKontroler;
import Gajbe_Tab.Gajba_ASCED.SacuvajGajbuKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.SacuvajUnosGajbiKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.SacuvajiStampajUnsoGajbiKOntroler;
import Gajbe_Tab.Tools_K.StampajIzvestajUnosaGajbiKontroler;
import Gajbe_Tab.UnosGajbi_ASCEDP.StampajUnosGajbeKontroler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import model.Gajba;
import model.Prevoznik;
import model.Proizvodjac;
import model.UnosGajbi;

public class GajbeTab extends VBox {
	
	private static GajbeTab instance;
	
	private HBox zajednickoHB;
	
	private VBox gajbeVB;
	private VBox unosiGajbiVB;
	
	private boolean unosNoveGajbe = false;
	private boolean izmenaGajbeUToku = false;
	
	private boolean unosUnosaGajbi = false;
	private boolean izmenaUnosaGajbi = false;
	
	private TextField tfSifraGajbe;   ////za gajbe
	private TextField tfNazivGajbe;
	private TextField tfTezinaGajbe;
	private TextField tfUkupnoNaRaspolaganjuGajbi;
	
	private FlowPane unosGajbiFP;
	
	private Button BDodajGajbu;
	private Button BSacuvajGajbu;
	private Button BPonistiGajbu;
	private Button BIzmeniGajbu;
	private Button BObrisiGajbu;
	
	private TableView<Gajba> tabelaGajbe;
	
	private TextField tfSifraUnosaGajbi;     ////za unose gajbi
	private DatePicker dpDatumUnosaGajbi;
	private ComboBox<Proizvodjac> cbProizvodjac;
	private ComboBox<Prevoznik> cbPrevoznik;
	private ComboBox<Gajba> cbGajba;
	private TextField tfUlazGajbi;
	private TextField tfIzlazGajbi;
	private HBox prevoznikHB;
	private HBox gajbaHB;
	
	private FlowPane unosUnosaGajbiFP;
	
	private Button BDodajUnosGajbe;
	private Button BSacuvajUnosGajbe;
	private Button BPonistiUnosGajbe;
	private Button BIzmeniUnosGajbe;
	private Button BObrisiUnosGajbe;
	private Button BStampajUnosGajbe;
	private Button BSacuvajIStampajUnsoGajbe;
	
	private TableView<UnosGajbi> tabelaUnosGajbi;
	
	private RadioButton RBproizvodjac;
	private RadioButton RBprevoznik;
	private ToggleGroup TGizbor;
	private ComboBox<Prevoznik> CBprevoznikPretraga;
	private ComboBox<Proizvodjac> CBproizvodjacPretraga;
	private ComboBox<Gajba> CBGajbaPretraga;
	private HBox pretragaUnosaGajbiHB;
	private Button BPonistiPretragu;
	private Label lp1;
	private Label lp2;
	
	private RadioButton RBSveGajbeObracun;
	private RadioButton RBFiltriraneGajbeObrscun;
	private ToggleGroup TGGajbeObracun;
	private Button BObracunajGajbe;
	
	private TextField TFPretragaGajbi;
	private Button BPonisitPretraguGajbi;
	
	private RadioButton RBSviUnosiGajbiObracun;
	private RadioButton RBFiltriraniUnosiGajbiObracun;
	private ToggleGroup TRObracunUnosaGajbi;
	private Button BObracunajUnoseGajbi;		
	private HBox brzObracunHB;
	private Label lobracun;
	
	private RadioButton RBproizvodjacIzvestaj;
	private RadioButton RBPrevoznikIzvestaj;
	private ToggleGroup TGIzvestaj;
	private ComboBox<Prevoznik> CBPrevoznikIzvestaj;
	private ComboBox<Proizvodjac> CBProizvodjacIzvestaj;
	private HBox stampaIzvestajaHB;
	private Label lp;
	private Button BstamapjIzvestajUnosaGajbi;

	private GajbeTab() {
		
		zajednickoHB = new HBox(20);
		gajbeVB  = new VBox(15);
		gajbeVB.setPrefWidth(700);
		unosiGajbiVB = new VBox(15);
		unosiGajbiVB.setPrefWidth(1100);
		
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		
		Label naslov2 = new Label("Baza gajbi:");
		naslov2.setFont(new Font(35));
		gajbeVB.getChildren().add(naslov2);
		
		Label naslov3 = new Label("Zaduženja/razduženja gajbi:");
		naslov3.setFont(new Font(35));
		unosiGajbiVB.getChildren().add(naslov3);
		
		trakaZaUnosGajbi();
		trakaKomandiGajbi();
		tabelaGajbi();
		
		trakaZaUnosUnosaGajbi();
		trakaKomandiUnosaGajbi();
		tabelaUnosaGajbi();
		trakaZaPretraguUnosa();
		obracunUnosaGajbi();
		pretragaGajbi();
		obracunGajbi();
		izvestajUnosaGajbi();
		
		zajednickoHB.getChildren().add(gajbeVB);
		zajednickoHB.getChildren().add(unosiGajbiVB);
		this.getChildren().add(zajednickoHB);
		
	}
	
	private void trakaZaUnosGajbi() {
		
		tfSifraGajbe = new TextField();
		tfSifraGajbe.setPrefWidth(40);
		tfNazivGajbe = new TextField();
		tfNazivGajbe.setPrefWidth(150);
		tfTezinaGajbe = new TextField();
		tfTezinaGajbe.setPrefWidth(50);
		tfUkupnoNaRaspolaganjuGajbi = new TextField();
		tfUkupnoNaRaspolaganjuGajbi.setPrefWidth(100);
		
		
		unosGajbiFP = new FlowPane(10, 10);	
		unosGajbiFP.setAlignment(Pos.BASELINE_LEFT);
		
		unosGajbiFP.getChildren().add(new Label("šifra:"));
		unosGajbiFP.getChildren().add(tfSifraGajbe);
		unosGajbiFP.getChildren().add(new Label("naziv:"));
		unosGajbiFP.getChildren().add(tfNazivGajbe);
		unosGajbiFP.getChildren().add(new Label("težina:"));
		unosGajbiFP.getChildren().add(tfTezinaGajbe);
		HBox ukupnonrHB = new HBox(10);
		ukupnonrHB.setAlignment(Pos.BASELINE_LEFT);
		
		ukupnonrHB.getChildren().add(new Label("ukupno na raspolaganju:"));
		ukupnonrHB.getChildren().add(tfUkupnoNaRaspolaganjuGajbi);
		unosGajbiFP.getChildren().add(ukupnonrHB);
		
		gajbeVB.getChildren().addAll(unosGajbiFP);
		
		unosGajbiFP.setDisable(true);
	}
	
	private void trakaKomandiGajbi() {
		BDodajGajbu = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajGajbu.setGraphic(add); 
		BDodajGajbu.setOnAction(new DodajGajbuKontroler());
		
		BSacuvajGajbu = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajGajbu.setGraphic(save);
		BSacuvajGajbu.setOnAction(new SacuvajGajbuKontroler());
		BSacuvajGajbu.setDisable(true);
		
		BPonistiGajbu = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiGajbu.setGraphic(cancel);
		BPonistiGajbu.setOnAction(new PonistiGajbuKontroler());
		BPonistiGajbu.setDisable(true);
			
		BIzmeniGajbu = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniGajbu.setGraphic(edit);
		BIzmeniGajbu.setDisable(true);
		BIzmeniGajbu.setOnAction(new IzmeniGajbuKontroler());
		
		BObrisiGajbu = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiGajbu.setGraphic(delete);
		BObrisiGajbu.setDisable(true);
		BObrisiGajbu.setOnAction(new ObrisiGajbuKontroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodajGajbu,BSacuvajGajbu,BPonistiGajbu,BIzmeniGajbu,BObrisiGajbu);
		gajbeVB.getChildren().add(komandeHB);
	}
	
	@SuppressWarnings("unchecked")
	private void tabelaGajbi(){
	
		tabelaGajbe = new TableView<Gajba>();
		
		TableColumn <Gajba, Integer> tcSifra = new TableColumn<Gajba,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Gajba, Integer>("sifra"));
		
		TableColumn <Gajba, String> tcNaziv = new TableColumn<Gajba,String>("naziv");		
		tcNaziv.setCellValueFactory(new PropertyValueFactory<Gajba, String>("naziv"));
		
		TableColumn <Gajba, Double> tcTezina = new TableColumn<Gajba,Double>("težina");		
		tcTezina.setCellValueFactory(new PropertyValueFactory<Gajba, Double>("tezina"));	
		
		TableColumn <Gajba, Integer> tcUkupno = new TableColumn<Gajba,Integer>("ukupno na raspolaganju");		
		tcUkupno.setCellValueFactory(new PropertyValueFactory<Gajba, Integer>("ukupnoNaRaspolaganju"));
		
		tabelaGajbe.getColumns().addAll(tcSifra,tcNaziv,tcTezina,tcUkupno);
		
		updateTabeleGajbi();
		
		gajbeVB.getChildren().add(tabelaGajbe);		
		
		tabelaGajbe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNoveGajbe == false && izmenaGajbeUToku == false) {
		       BObrisiGajbu.setDisable(false);
		       BIzmeniGajbu.setDisable(false);
		       BPonistiGajbu.setDisable(false);
		       BDodajGajbu.setDisable(true);		       
		    }
		});
		
		tabelaGajbe.scrollTo(tabelaGajbe.getItems().size()-1);
	}
	
	public void updateTabeleGajbi() {
		tabelaGajbe.getItems().clear();
		tabelaGajbe.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().getGajbe()));
		tabelaGajbe.scrollTo(tabelaGajbe.getItems().size()-1);
	}
	
	public void ocistiPoljaZaUnosGajbe() {
		tfSifraGajbe.clear();
		tfNazivGajbe.clear();
		tfTezinaGajbe.clear();
		tfUkupnoNaRaspolaganjuGajbi.clear();
	}
	
	private void trakaZaUnosUnosaGajbi() {
		
		tfSifraUnosaGajbi = new TextField();
		tfSifraUnosaGajbi.setPrefWidth(30);
		dpDatumUnosaGajbi = new DatePicker();
		
		cbProizvodjac = new ComboBox<Proizvodjac>();
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		cbProizvodjac.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		cbProizvodjac.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue, Proizvodjac newValue) {
				if(newValue != null) {
					cbPrevoznik.setDisable(true);		
					lp2.setDisable(true);
				}
			}
		});
	
		cbPrevoznik = new ComboBox<Prevoznik>();
		cbPrevoznik.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		cbPrevoznik.getItems().remove(Firma.getInstance().getTrenutnaGodina().getLicno());
		cbPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPrevoznik);
		cbPrevoznik.valueProperty().addListener(new ChangeListener<Prevoznik>() {
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				if(newValue != null) {
					cbProizvodjac.setDisable(true);		
					lp1.setDisable(true);
				}
			}
		});
			
		cbGajba = new ComboBox<Gajba>();	
		cbGajba.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getGajbe());
		cbGajba.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Gajba>(cbGajba);
		
		tfUlazGajbi = new TextField();
		tfUlazGajbi.setPrefWidth(50);
		tfIzlazGajbi = new TextField();
		tfIzlazGajbi.setPrefWidth(50);
		
		unosUnosaGajbiFP = new FlowPane(10,10);
		unosUnosaGajbiFP.setAlignment(Pos.BASELINE_LEFT);
		
		unosUnosaGajbiFP.getChildren().add(new Label("šifra:"));
		unosUnosaGajbiFP.getChildren().add(tfSifraUnosaGajbi);
		unosUnosaGajbiFP.getChildren().add(new Label("datum:"));
		unosUnosaGajbiFP.getChildren().add(dpDatumUnosaGajbi);
		lp1 = new Label("proizvođač:");
		unosUnosaGajbiFP.getChildren().add(lp1);
		unosUnosaGajbiFP.getChildren().add(cbProizvodjac);
		prevoznikHB = new HBox(10);
		prevoznikHB.setAlignment(Pos.BASELINE_LEFT);
		lp2 = new Label("prevoznik:");
		prevoznikHB.getChildren().addAll(lp2,cbPrevoznik);
		unosUnosaGajbiFP.getChildren().add(prevoznikHB);
		gajbaHB = new HBox(10);
		gajbaHB.getChildren().addAll(new Label("gajba:"),cbGajba);
		gajbaHB.setAlignment(Pos.BASELINE_LEFT);
		unosUnosaGajbiFP.getChildren().add(gajbaHB);
		HBox gajbeUlazHB = new HBox(10);
		gajbeUlazHB.setAlignment(Pos.BASELINE_CENTER);
		gajbeUlazHB.getChildren().addAll(new Label("gajbe Ulaz:"),tfUlazGajbi);
		HBox gajbeIzlazHB = new HBox(10);
		gajbeIzlazHB.setAlignment(Pos.BASELINE_CENTER);
		gajbeIzlazHB.getChildren().addAll(new Label("gajbe Izlaz: "),tfIzlazGajbi);
		unosUnosaGajbiFP.getChildren().addAll(gajbeUlazHB,gajbeIzlazHB);
		
		unosiGajbiVB.getChildren().add(unosUnosaGajbiFP);
		
		unosUnosaGajbiFP.setDisable(true);	
		
	}
	
	private void trakaKomandiUnosaGajbi() {
		
		BDodajUnosGajbe = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajUnosGajbe.setGraphic(add); 
		BDodajUnosGajbe.setOnAction(new DodajUnosGajbiKontroler());
		
		BSacuvajUnosGajbe = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajUnosGajbe.setGraphic(save);
		BSacuvajUnosGajbe.setOnAction(new SacuvajUnosGajbiKontroler());
		BSacuvajUnosGajbe.setDisable(true);
		
		BPonistiUnosGajbe = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiUnosGajbe.setGraphic(cancel);
		BPonistiUnosGajbe.setOnAction(new PonistiUnosGajbiKontroler());
		BPonistiUnosGajbe.setDisable(true);
			
		BIzmeniUnosGajbe = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniUnosGajbe.setGraphic(edit);
		BIzmeniUnosGajbe.setDisable(true);
		BIzmeniUnosGajbe.setOnAction(new IzmeniUnosGajbiKontroler());
		
		BObrisiUnosGajbe = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiUnosGajbe.setGraphic(delete);
		BObrisiUnosGajbe.setDisable(true);
		BObrisiUnosGajbe.setOnAction(new ObrisiUnosGajbiKontroler());
		
		BStampajUnosGajbe = new Button("Štampaj");
		BStampajUnosGajbe.setDisable(true);
		ImageView stampaj = new ImageView(Firma.getInstance().getPrintIco());
		BStampajUnosGajbe.setGraphic(stampaj);
		BStampajUnosGajbe.setOnAction(new StampajUnosGajbeKontroler());
		
		BSacuvajIStampajUnsoGajbe = new Button("Štampaj i Sačuvaj");
		BSacuvajIStampajUnsoGajbe.setDisable(true);
		ImageView stampaj2 = new ImageView(Firma.getInstance().getPrintIco());
		ImageView sacuvaj2 = new ImageView(Firma.getInstance().getSaveIco());
		HBox slikeHB = new HBox(5);
		slikeHB.getChildren().addAll(stampaj2,sacuvaj2);
		BSacuvajIStampajUnsoGajbe.setGraphic(slikeHB);
		BSacuvajIStampajUnsoGajbe.setOnAction(new SacuvajiStampajUnsoGajbiKOntroler());
		
		HBox komandeUnosaGajbiHB = new HBox(10);
		komandeUnosaGajbiHB.getChildren().addAll(BDodajUnosGajbe,BSacuvajUnosGajbe,BPonistiUnosGajbe,BIzmeniUnosGajbe,BObrisiUnosGajbe,BStampajUnosGajbe,BSacuvajIStampajUnsoGajbe);
		unosiGajbiVB.getChildren().add(komandeUnosaGajbiHB);
	}
	
	@SuppressWarnings("unchecked")
	private void tabelaUnosaGajbi() {
		tabelaUnosGajbi = new TableView<UnosGajbi>();
		
		TableColumn <UnosGajbi, Integer> tcSifra = new TableColumn<UnosGajbi,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Integer>("sifra"));
		
		TableColumn <UnosGajbi, LocalDate> tcDatum = new TableColumn<UnosGajbi,LocalDate>("datum");		
		tcDatum.setCellValueFactory(new PropertyValueFactory<UnosGajbi, LocalDate>("datum"));
		
		TableColumn <UnosGajbi, Proizvodjac> tcProizvodjac = new TableColumn<UnosGajbi,Proizvodjac>("proizvođač");		
		tcProizvodjac.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Proizvodjac>("proizvodjac"));	
		
		TableColumn <UnosGajbi, Prevoznik> tcPrevoznik = new TableColumn<UnosGajbi,Prevoznik>("prevoznik");		
		tcPrevoznik.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Prevoznik>("prevoznik"));
		
		TableColumn <UnosGajbi, Gajba> tcGajba = new TableColumn<UnosGajbi,Gajba>("vrsta gjabe");		
		tcGajba.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Gajba>("gajba"));
		
		TableColumn <UnosGajbi, Integer> tcGajbeUlaz = new TableColumn<UnosGajbi,Integer>("ulaz");		
		tcGajbeUlaz.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Integer>("gajbeUlaz"));
		
		TableColumn <UnosGajbi, Integer> ttcGajbeIzlaz = new TableColumn<UnosGajbi,Integer>("izlaz");		
		ttcGajbeIzlaz.setCellValueFactory(new PropertyValueFactory<UnosGajbi, Integer>("gajbeIzlaz"));
		
		TableColumn <UnosGajbi, String> ttcGajbeSaldo = new TableColumn<UnosGajbi,String>("saldo");		
		ttcGajbeSaldo.setCellValueFactory(new PropertyValueFactory<UnosGajbi, String>("saldo"));
			
		tabelaUnosGajbi.getColumns().addAll(tcSifra,tcDatum,tcProizvodjac,tcPrevoznik,tcGajba,tcGajbeUlaz,ttcGajbeIzlaz,ttcGajbeSaldo);
		
		updateTabeleUnosaGajbi();
		
		unosiGajbiVB.getChildren().add(tabelaUnosGajbi);		
		
		tabelaUnosGajbi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosUnosaGajbi == false && izmenaUnosaGajbi == false) {
		       BObrisiUnosGajbe.setDisable(false);
		       BIzmeniUnosGajbe.setDisable(false);
		       BPonistiUnosGajbe.setDisable(false);
		       BDodajUnosGajbe.setDisable(true);	
		       BStampajUnosGajbe.setDisable(false);
		    }
		});
		
		tabelaUnosGajbi.scrollTo(tabelaUnosGajbi.getItems().size()-1);
	}
	
	public void updateTabeleUnosaGajbi() {
		tabelaUnosGajbi.getItems().clear();
		tabelaUnosGajbi.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()));
		tabelaUnosGajbi.scrollTo(tabelaUnosGajbi.getItems().size()-1);
	}
	
	public void updateCBProizvodjac() {
		unosUnosaGajbiFP.getChildren().remove(cbProizvodjac);
		cbProizvodjac = new ComboBox<Proizvodjac>();
		unosUnosaGajbiFP.getChildren().add(5, cbProizvodjac);
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		cbProizvodjac.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		updateCBProizvodjacPretraga();
		cbProizvodjac.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue, Proizvodjac newValue) {
				if(newValue != null) {
					cbPrevoznik.setDisable(true);		
					lp2.setDisable(true);
				}
			}
		});
	}
	
	public void updateCBPrevoznik() {
		prevoznikHB.getChildren().remove(cbPrevoznik);
		cbPrevoznik = new ComboBox<Prevoznik>();
		prevoznikHB.getChildren().add(1, cbPrevoznik);
		cbPrevoznik.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		cbPrevoznik.getItems().remove(Firma.getInstance().getTrenutnaGodina().getLicno());
		cbPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPrevoznik);
		updateCBPrevoznikPretraga();
		cbPrevoznik.valueProperty().addListener(new ChangeListener<Prevoznik>() {
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				if(newValue != null) {
					cbProizvodjac.setDisable(true);		
					lp1.setDisable(true);
				}
			}
		});
	}
	
	public void updateCBGajba() {
		gajbaHB.getChildren().remove(cbGajba);
		cbGajba = new ComboBox<Gajba>();
		gajbaHB.getChildren().add(1,cbGajba);
		cbGajba.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getGajbe());
		cbGajba.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Gajba>(cbGajba);
	}
	
	public void ocitiPoljaZaUnosUnosaGajbi() {
		tfSifraUnosaGajbi.clear();
		dpDatumUnosaGajbi.setValue(null);
		updateCBPrevoznik();
		updateCBProizvodjac();
		cbProizvodjac.getSelectionModel().clearSelection();
		cbPrevoznik.getSelectionModel().clearSelection();
		cbGajba.getSelectionModel().clearSelection();
		tfUlazGajbi.clear();
		tfIzlazGajbi.clear();
	}
	
	private void obracunUnosaGajbi() {
		brzObracunHB = new HBox(10);             ///////////////obracunnnn//////////////
		brzObracunHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		brzObracunHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setFont(new Font(20));
		Label lll = new Label("Obračunaj za:  ");
		lll.setFont(new Font(15));
		brzObracunHB.getChildren().add(ol);
		brzObracunHB.getChildren().add(lll);
		RBSviUnosiGajbiObracun = new RadioButton("sve");
		RBFiltriraniUnosiGajbiObracun = new RadioButton("filtrirane");
		TRObracunUnosaGajbi = new ToggleGroup();
		RBSviUnosiGajbiObracun.setToggleGroup(TRObracunUnosaGajbi);
		RBFiltriraniUnosiGajbiObracun.setToggleGroup(TRObracunUnosaGajbi);
		TRObracunUnosaGajbi.selectToggle(null);
		brzObracunHB.getChildren().addAll(RBSviUnosiGajbiObracun,RBFiltriraniUnosiGajbiObracun);
		BObracunajUnoseGajbi = new Button("obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunajUnoseGajbi.setGraphic(obracunaj);
		BObracunajUnoseGajbi.setOnAction(new ObracunajUnoseGajbiKontroler());
		brzObracunHB.getChildren().add(BObracunajUnoseGajbi);
		TRObracunUnosaGajbi.selectToggle(RBSviUnosiGajbiObracun);
		unosiGajbiVB.getChildren().add(brzObracunHB);
		
	}
	private void trakaZaPretraguUnosa() {
		pretragaUnosaGajbiHB = new HBox(10);
		pretragaUnosaGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaUnosaGajbiHB.getChildren().add(search);
		Label pl = new Label("Filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaUnosaGajbiHB.getChildren().add(pl);
		Label l2 = new Label("Gajba:");
		l2.setFont(new Font(15));
		pretragaUnosaGajbiHB.getChildren().add(l2);
		CBGajbaPretraga = new ComboBox<Gajba>();
		pretragaUnosaGajbiHB.getChildren().add(CBGajbaPretraga);
		Label l1 = new Label("Prikži:");
		l1.setFont(new Font(15));
		pretragaUnosaGajbiHB.getChildren().add(l1);
		RBproizvodjac = new RadioButton("proizvođače");
		RBprevoznik = new  RadioButton("prevoznike");
		TGizbor = new ToggleGroup();
		RBproizvodjac.setToggleGroup(TGizbor);
		RBprevoznik.setToggleGroup(TGizbor);

		pretragaUnosaGajbiHB.getChildren().addAll(RBproizvodjac,RBprevoznik);
		lp1 = new Label("");
		lp1.setFont(new Font(15));
		pretragaUnosaGajbiHB.getChildren().add(lp1);


		CBprevoznikPretraga = new ComboBox<Prevoznik>();
		CBproizvodjacPretraga = new ComboBox<Proizvodjac>();

		BPonistiPretragu = new Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setDisable(true);
		pretragaUnosaGajbiHB.getChildren().add(BPonistiPretragu);
		BPonistiPretragu.setOnAction(new PonisitiPretraguUnosaGajbi()) ;

		RBprevoznik.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if(RBprevoznik.isSelected()) {
					pretragaUnosaGajbiHB.getChildren().remove(CBproizvodjacPretraga);
					lp1.setText("prevoznik:");
					updateCBProizvodjacPretraga();
					pretragaUnosaGajbiHB.getChildren().add((pretragaUnosaGajbiHB.getChildren().indexOf(RBprevoznik)+2),CBprevoznikPretraga);
					BPonistiPretragu.setDisable(false);

					tabelaUnosGajbi.getItems().clear();
					ArrayList<UnosGajbi> unga = new ArrayList<UnosGajbi>();
					Gajba g = CBGajbaPretraga.getSelectionModel().getSelectedItem();
					Prevoznik pre =  CBprevoznikPretraga.getSelectionModel().getSelectedItem();
					unga = Firma.getInstance().getTrenutnaGodina().filtriraniUnosiGajbi(false, true, null, pre, g);
					tabelaUnosGajbi.getItems().addAll(unga);

				}
			}
		});

		RBproizvodjac.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBproizvodjac.isSelected()) {
					pretragaUnosaGajbiHB.getChildren().remove(CBprevoznikPretraga);
					lp1.setText("proizvodjac:");
					updateCBPrevoznikPretraga();
					pretragaUnosaGajbiHB.getChildren().add((pretragaUnosaGajbiHB.getChildren().indexOf(RBprevoznik)+2),CBproizvodjacPretraga);
					BPonistiPretragu.setDisable(false);

					tabelaUnosGajbi.getItems().clear();
					ArrayList<UnosGajbi> unga = new ArrayList<UnosGajbi>();
					Gajba g = CBGajbaPretraga.getSelectionModel().getSelectedItem();
					Proizvodjac pro = CBproizvodjacPretraga.getSelectionModel().getSelectedItem();
					unga = Firma.getInstance().getTrenutnaGodina().filtriraniUnosiGajbi(true, false, pro, null, g);
					tabelaUnosGajbi.getItems().addAll(unga);
				}
			}
		});

		unosiGajbiVB.getChildren().addAll(pretragaUnosaGajbiHB);
		RBproizvodjac.setSelected(false);
		RBprevoznik.setSelected(false);
		updateCBGajbaPretraga();
		updateCBProizvodjacPretraga();
		updateCBPrevoznikPretraga();

	}
	public void updateCBGajbaPretraga () {
		pretragaUnosaGajbiHB.getChildren().remove(CBGajbaPretraga);
		CBGajbaPretraga = new  ComboBox<Gajba>();
		pretragaUnosaGajbiHB.getChildren().add(3,CBGajbaPretraga);
		CBGajbaPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getGajbe());
		CBGajbaPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Gajba>(CBGajbaPretraga);
		CBGajbaPretraga.valueProperty().addListener(new ChangeListener<Gajba>() {
			@Override
			public void changed(ObservableValue<? extends Gajba> observable, Gajba oldValue, Gajba newValue) {
				RBFiltriraniUnosiGajbiObracun.setSelected(true);
				tabelaUnosGajbi.getItems().clear();
				ArrayList<UnosGajbi> unga = new ArrayList<UnosGajbi>();
				Boolean pro = RBproizvodjac.isSelected();
				Boolean pre = RBprevoznik.isSelected();
				Proizvodjac pro2 = CBproizvodjacPretraga.getSelectionModel().getSelectedItem();
				Prevoznik pre2 = CBprevoznikPretraga.getSelectionModel().getSelectedItem();
				unga = Firma.getInstance().getTrenutnaGodina().filtriraniUnosiGajbi(pro, pre, pro2, pre2, newValue);
				tabelaUnosGajbi.getItems().addAll(unga);	
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);
			}
		});
	}
	
	public void updateCBProizvodjacPretraga() {
		pretragaUnosaGajbiHB.getChildren().remove(CBproizvodjacPretraga);
		CBproizvodjacPretraga = new ComboBox<Proizvodjac>();
		CBproizvodjacPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		CBproizvodjacPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(CBproizvodjacPretraga);
		CBproizvodjacPretraga.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue,Proizvodjac newValue) {
				RBFiltriraniUnosiGajbiObracun.setSelected(true);
				tabelaUnosGajbi.getItems().clear();
				ArrayList<UnosGajbi> unga = new ArrayList<UnosGajbi>();
				Boolean pro = RBproizvodjac.isSelected();
				Boolean pre = RBprevoznik.isSelected();
				Proizvodjac pro2 = CBproizvodjacPretraga.getSelectionModel().getSelectedItem();
				Prevoznik pre2 = CBprevoznikPretraga.getSelectionModel().getSelectedItem();
				Gajba g = CBGajbaPretraga.getSelectionModel().getSelectedItem();
				unga = Firma.getInstance().getTrenutnaGodina().filtriraniUnosiGajbi(pro, pre, pro2, pre2, g);
				tabelaUnosGajbi.getItems().addAll(unga);	
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);
				
			}
		});
		
	}
	
	public void updateCBPrevoznikPretraga() {
		pretragaUnosaGajbiHB.getChildren().remove(CBprevoznikPretraga);
		CBprevoznikPretraga = new ComboBox<Prevoznik>();
		CBprevoznikPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		CBprevoznikPretraga.getItems().remove(Firma.getInstance().getTrenutnaGodina().getLicno());
		CBprevoznikPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(CBprevoznikPretraga);
		CBprevoznikPretraga.valueProperty().addListener(new ChangeListener<Prevoznik>() {

			@Override
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				RBFiltriraniUnosiGajbiObracun.setSelected(true);
				tabelaUnosGajbi.getItems().clear();
				ArrayList<UnosGajbi> unga = new ArrayList<UnosGajbi>();
				Boolean pro = RBproizvodjac.isSelected();
				Boolean pre = RBprevoznik.isSelected();
				Proizvodjac pro2 = CBproizvodjacPretraga.getSelectionModel().getSelectedItem();
				Prevoznik pre2 = CBprevoznikPretraga.getSelectionModel().getSelectedItem();
				Gajba g = CBGajbaPretraga.getSelectionModel().getSelectedItem();
				unga = Firma.getInstance().getTrenutnaGodina().filtriraniUnosiGajbi(pro, pre, pro2, pre2, g);
				tabelaUnosGajbi.getItems().addAll(unga);	
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);
				
			}
		});
		
	}
	
	private void pretragaGajbi() {
		HBox pretragaGajbiHB = new HBox(10);
		pretragaGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaGajbiHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaGajbiHB.getChildren().add(pl);
		pretragaGajbiHB.getChildren().add(new Label("naziv:"));
		TFPretragaGajbi = new TextField();
		TFPretragaGajbi.setPrefWidth(150);
		pretragaGajbiHB.getChildren().add(TFPretragaGajbi);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonisitPretraguGajbi = new Button("poništi");
		BPonisitPretraguGajbi.setGraphic(close);
		BPonisitPretraguGajbi.setOnAction(new PonistiPretraguGajbiKontroler());
		BPonisitPretraguGajbi.setDisable(true);
		pretragaGajbiHB.getChildren().add(BPonisitPretraguGajbi);
		
		TFPretragaGajbi.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				RBFiltriraneGajbeObrscun.setSelected(true);
				BPonisitPretraguGajbi.setDisable(false);
				tabelaGajbe.getItems().clear();
				tabelaGajbe.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajGajbe(TFPretragaGajbi.getText())));
				if(TFPretragaGajbi.getText().equals("")) {
					BPonisitPretraguGajbi.setDisable(true);
					RBSveGajbeObracun.setSelected(true);
				}
				
			}
		});
		
		gajbeVB.getChildren().add(pretragaGajbiHB);
	}
	
	private void obracunGajbi() {
		HBox obracunGajbiHB = new HBox(10);
		obracunGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		obracunGajbiHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun:  ");
		ol.setFont(new Font(20));
		obracunGajbiHB.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		lll.setFont(new Font(15));
		obracunGajbiHB.getChildren().add(lll);
		RBSveGajbeObracun = new RadioButton("sve");
		RBSveGajbeObracun.setSelected(true);
		RBFiltriraneGajbeObrscun = new RadioButton("filtrirane");
		TGGajbeObracun = new ToggleGroup();
		RBSveGajbeObracun.setToggleGroup(TGGajbeObracun);
		RBFiltriraneGajbeObrscun.setToggleGroup(TGGajbeObracun);
		obracunGajbiHB.getChildren().addAll(RBSveGajbeObracun,RBFiltriraneGajbeObrscun);
		BObracunajGajbe = new Button("obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunajGajbe.setGraphic(obracunaj);
		BObracunajGajbe.setOnAction(new ObracunajGajbeKontroler()  );
		obracunGajbiHB.getChildren().add(BObracunajGajbe);

		HBox prazan = new HBox(10);
		prazan.setAlignment(Pos.BASELINE_LEFT);
		prazan.setMinHeight(32);

		gajbeVB.getChildren().addAll(obracunGajbiHB,prazan);

	}
	
	private void izvestajUnosaGajbi() {
		stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		stampaIzvestajaHB.getChildren().add(lista);
		Label sl = new Label("Štampa izveštaja gajbi:   ");
		sl.setFont(new Font(20));
		stampaIzvestajaHB.getChildren().add(sl);
		Label kkk = new Label("Štampaj za:  ");
		kkk.setFont(new Font(15));
		stampaIzvestajaHB.getChildren().add(kkk);
		RBproizvodjacIzvestaj = new RadioButton("proizvođača");
		RBPrevoznikIzvestaj = new RadioButton("prevoznika");
		TGIzvestaj = new ToggleGroup();
		RBPrevoznikIzvestaj.setToggleGroup(TGIzvestaj);
		RBproizvodjacIzvestaj.setToggleGroup(TGIzvestaj);
		stampaIzvestajaHB.getChildren().addAll(RBPrevoznikIzvestaj,RBproizvodjacIzvestaj);
		lp = new Label("");
		lp.setFont(new Font(15));		
		stampaIzvestajaHB.getChildren().add(lp);
		
		CBPrevoznikIzvestaj = new ComboBox<Prevoznik>();
		CBProizvodjacIzvestaj = new ComboBox<Proizvodjac>();
		
		BstamapjIzvestajUnosaGajbi = new Button("štampaj");
		ImageView print = new ImageView(Firma.getInstance().getPrintIco());
		BstamapjIzvestajUnosaGajbi.setGraphic(print); 
		stampaIzvestajaHB.getChildren().add(BstamapjIzvestajUnosaGajbi);
		BstamapjIzvestajUnosaGajbi.setOnAction( new StampajIzvestajUnosaGajbiKontroler());
		
		RBPrevoznikIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBPrevoznikIzvestaj.isSelected()) {
					stampaIzvestajaHB.getChildren().remove(CBProizvodjacIzvestaj);
					lp.setText("prevoznik:");
					updateCBPrevoznikIzvestaj();
					stampaIzvestajaHB.getChildren().add((stampaIzvestajaHB.getChildren().indexOf(RBPrevoznikIzvestaj)+3),CBPrevoznikIzvestaj);				
				}				
			}
		});
		
		RBproizvodjacIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBproizvodjacIzvestaj.isSelected()) {
					stampaIzvestajaHB.getChildren().remove(CBPrevoznikIzvestaj);
					lp.setText("proizvođač:");
					updateCBProizvodjacIzvestaj();
					stampaIzvestajaHB.getChildren().add((stampaIzvestajaHB.getChildren().indexOf(RBPrevoznikIzvestaj)+3),CBProizvodjacIzvestaj);				
				}				
			}
		});
		
		RBPrevoznikIzvestaj.setSelected(true);
		stampaIzvestajaHB.getChildren().remove(CBProizvodjacIzvestaj);
		lp.setText("prevoznik:");
		updateCBPrevoznikIzvestaj();
		stampaIzvestajaHB.getChildren().add((stampaIzvestajaHB.getChildren().indexOf(RBPrevoznikIzvestaj)+3),CBPrevoznikIzvestaj);
				
		unosiGajbiVB.getChildren().add(stampaIzvestajaHB);
	}
	
	public void updateCBProizvodjacIzvestaj() {
		stampaIzvestajaHB.getChildren().remove(CBProizvodjacIzvestaj);
		CBProizvodjacIzvestaj = new ComboBox<Proizvodjac>();
		CBProizvodjacIzvestaj.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		CBProizvodjacIzvestaj.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(CBProizvodjacIzvestaj);
	}
	
	public void updateCBPrevoznikIzvestaj() {
		stampaIzvestajaHB.getChildren().remove(CBPrevoznikIzvestaj);
		CBPrevoznikIzvestaj = new ComboBox<Prevoznik>();
		CBPrevoznikIzvestaj.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		CBPrevoznikIzvestaj.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(CBPrevoznikIzvestaj);
	}
	
	public static GajbeTab getInstance() {
		if(instance == null) {
			instance = new GajbeTab();
		}
		return instance;
	}
	
	public RadioButton getRBSveGajbeObracun() {
		return RBSveGajbeObracun;
	}

	public void setRBSveGajbeObracun(RadioButton rBSveGajbeObracun) {
		RBSveGajbeObracun = rBSveGajbeObracun;
	}

	public RadioButton getRBSviUnosiGajbiObracun() {
		return RBSviUnosiGajbiObracun;
	}

	public void setRBSviUnosiGajbiObracun(RadioButton rBSviUnosiGajbiObracun) {
		RBSviUnosiGajbiObracun = rBSviUnosiGajbiObracun;
	}

	public RadioButton getRBFiltriraniUnosiGajbiObracun() {
		return RBFiltriraniUnosiGajbiObracun;
	}

	public void setRBFiltriraniUnosiGajbiObracun(RadioButton rBFiltriraniUnosiGajbiObracun) {
		RBFiltriraniUnosiGajbiObracun = rBFiltriraniUnosiGajbiObracun;
	}

	public ToggleGroup getTRObracunUnosaGajbi() {
		return TRObracunUnosaGajbi;
	}

	public Button getBstamapjIzvestajUnosaGajbi() {
		return BstamapjIzvestajUnosaGajbi;
	}

	public void setBstamapjIzvestajUnosaGajbi(Button bstamapjIzvestajUnosaGajbi) {
		BstamapjIzvestajUnosaGajbi = bstamapjIzvestajUnosaGajbi;
	}

	public void setTRObracunUnosaGajbi(ToggleGroup tRObracunUnosaGajbi) {
		TRObracunUnosaGajbi = tRObracunUnosaGajbi;
	}

	public Button getBObracunajUnoseGajbi() {
		return BObracunajUnoseGajbi;
	}

	public void setBObracunajUnoseGajbi(Button bObracunajUnoseGajbi) {
		BObracunajUnoseGajbi = bObracunajUnoseGajbi;
	}

	public HBox getBrzObracunHB() {
		return brzObracunHB;
	}

	public void setBrzObracunHB(HBox brzObracunHB) {
		this.brzObracunHB = brzObracunHB;
	}

	public RadioButton getRBFiltriraneGajbeObrscun() {
		return RBFiltriraneGajbeObrscun;
	} 

	public RadioButton getRBproizvodjacIzvestaj() {
		return RBproizvodjacIzvestaj;
	}

	public void setRBproizvodjacIzvestaj(RadioButton rBproizvodjacIzvestaj) {
		RBproizvodjacIzvestaj = rBproizvodjacIzvestaj;
	}

	public RadioButton getRBPrevoznikIzvestaj() {
		return RBPrevoznikIzvestaj;
	}

	public void setRBPrevoznikIzvestaj(RadioButton rBPrevoznikIzvestaj) {
		RBPrevoznikIzvestaj = rBPrevoznikIzvestaj;
	}

	public ToggleGroup getTGIzvestaj() {
		return TGIzvestaj;
	}

	public void setTGIzvestaj(ToggleGroup tGIzvestaj) {
		TGIzvestaj = tGIzvestaj;
	}

	public ComboBox<Prevoznik> getCBPrevoznikIzvestaj() {
		return CBPrevoznikIzvestaj;
	}

	public void setCBPrevoznikIzvestaj(ComboBox<Prevoznik> cBPrevoznikIzvestaj) {
		CBPrevoznikIzvestaj = cBPrevoznikIzvestaj;
	}

	public ComboBox<Proizvodjac> getCBProizvodjacIzvestaj() {
		return CBProizvodjacIzvestaj;
	}

	public void setCBProizvodjacIzvestaj(ComboBox<Proizvodjac> cBProizvodjacIzvestaj) {
		CBProizvodjacIzvestaj = cBProizvodjacIzvestaj;
	}

	public HBox getStampaIzvestajaHB() {
		return stampaIzvestajaHB;
	}

	public void setStampaIzvestajaHB(HBox stampaIzvestajaHB) {
		this.stampaIzvestajaHB = stampaIzvestajaHB;
	}

	public Label getLp() {
		return lp;
	}

	public void setLp(Label lp) {
		this.lp = lp;
	}

	public void setRBFiltriraneGajbeObrscun(RadioButton rBFiltriraneGajbeObrscun) {
		RBFiltriraneGajbeObrscun = rBFiltriraneGajbeObrscun;
	}

	public ToggleGroup getTGGajbeObracun() {
		return TGGajbeObracun;
	}

	public void setTGGajbeObracun(ToggleGroup tGGajbeObracun) {
		TGGajbeObracun = tGGajbeObracun;
	}

	public Button getBObracunajGajbe() {
		return BObracunajGajbe;
	}

	public void setBObracunajGajbe(Button bObracunajGajbe) {
		BObracunajGajbe = bObracunajGajbe;
	}

	public HBox getZajednickoHB() {
		return zajednickoHB;
	}

	public void setZajednickoHB(HBox zajednickoHB) {
		this.zajednickoHB = zajednickoHB;
	}

	public Button getBStampajUnosGajbe() {
		return BStampajUnosGajbe;
	}

	public void setBStampajUnosGajbe(Button bStampajUnosGajbe) {
		BStampajUnosGajbe = bStampajUnosGajbe;
	}

	public Button getBSacuvajIStampajUnsoGajbe() {
		return BSacuvajIStampajUnsoGajbe;
	}

	public void setBSacuvajIStampajUnsoGajbe(Button bSacuvajIStampajUnsoGajbe) {
		BSacuvajIStampajUnsoGajbe = bSacuvajIStampajUnsoGajbe;
	}

	public HBox getPretragaUnosaGajbiHB() {
		return pretragaUnosaGajbiHB;
	}

	public void setPretragaUnosaGajbiHB(HBox pretragaUnosaGajbiHB) {
		this.pretragaUnosaGajbiHB = pretragaUnosaGajbiHB;
	}

	public TextField getTFPretragaGajbi() {
		return TFPretragaGajbi;
	}

	public void setTFPretragaGajbi(TextField tFPretragaGajbi) {
		TFPretragaGajbi = tFPretragaGajbi;
	}

	public Button getBPonisitPretraguGajbi() {
		return BPonisitPretraguGajbi;
	}

	public void setBPonisitPretraguGajbi(Button bPonisitPretraguGajbi) {
		BPonisitPretraguGajbi = bPonisitPretraguGajbi;
	}

	public VBox getGajbeVB() {
		return gajbeVB;
	}

	public void setGajbeVB(VBox gajbeVB) {
		this.gajbeVB = gajbeVB;
	}

	public VBox getUnosiGajbiVB() {
		return unosiGajbiVB;
	}

	public void setUnosiGajbiVB(VBox unosiGajbiVB) {
		this.unosiGajbiVB = unosiGajbiVB;
	}

	public boolean isUnosNoveGajbe() {
		return unosNoveGajbe;
	}

	public HBox getPrevoznikHB() {
		return prevoznikHB;
	}

	public void setPrevoznikHB(HBox prevoznikHB) {
		this.prevoznikHB = prevoznikHB;
	}

	public HBox getGajbaHB() {
		return gajbaHB;
	}

	public void setGajbaHB(HBox gajbaHB) {
		this.gajbaHB = gajbaHB;
	}

	public RadioButton getRBproizvodjac() {
		return RBproizvodjac;
	}

	public void setRBproizvodjac(RadioButton rBproizvodjac) {
		RBproizvodjac = rBproizvodjac;
	}

	public RadioButton getRBprevoznik() {
		return RBprevoznik;
	}

	public void setRBprevoznik(RadioButton rBprevoznik) {
		RBprevoznik = rBprevoznik;
	}

	public ToggleGroup getTGizbor() {
		return TGizbor;
	}

	public void setTGizbor(ToggleGroup tGizbor) {
		TGizbor = tGizbor;
	}

	public ComboBox<Prevoznik> getCBprevoznikPretraga() {
		return CBprevoznikPretraga;
	}

	public void setCBprevoznikPretraga(ComboBox<Prevoznik> cBprevoznikPretraga) {
		CBprevoznikPretraga = cBprevoznikPretraga;
	}

	public ComboBox<Proizvodjac> getCBproizvodjacPretraga() {
		return CBproizvodjacPretraga;
	}

	public void setCBproizvodjacPretraga(ComboBox<Proizvodjac> cBproizvodjacPretraga) {
		CBproizvodjacPretraga = cBproizvodjacPretraga;
	}

	public ComboBox<Gajba> getCBGajbaPretraga() {
		return CBGajbaPretraga;
	}

	public void setCBGajbaPretraga(ComboBox<Gajba> cBGajbaPretraga) {
		CBGajbaPretraga = cBGajbaPretraga;
	}

	public HBox getPretragaHB() {
		return pretragaUnosaGajbiHB;
	}

	public void setPretragaHB(HBox pretragaHB) {
		this.pretragaUnosaGajbiHB = pretragaHB;
	}

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
	}

	public void setBPonistiPretragu(Button bPonistiPretragu) {
		BPonistiPretragu = bPonistiPretragu;
	}

	public Label getLobracun() {
		return lobracun;
	}

	public void setLobracun(Label lobracun) {
		this.lobracun = lobracun;
	}

	public void setLp1(Label lp1) {
		this.lp1 = lp1;
	}

	public void setLp2(Label lp2) {
		this.lp2 = lp2;
	}

	public void setUnosNoveGajbe(boolean unosNoveGajbe) {
		this.unosNoveGajbe = unosNoveGajbe;
	}

	public boolean isIzmenaGajbeUToku() {
		return izmenaGajbeUToku;
	}

	public void setIzmenaGajbeUToku(boolean izmenaGajbeUToku) {
		this.izmenaGajbeUToku = izmenaGajbeUToku;
	}

	public boolean isUnosUnosaGajbi() {
		return unosUnosaGajbi;
	}

	public void setUnosUnosaGajbi(boolean unosUnosaGajbi) {
		this.unosUnosaGajbi = unosUnosaGajbi;
	}

	public boolean isIzmenaUnosaGajbi() {
		return izmenaUnosaGajbi;
	}

	public void setIzmenaUnosaGajbi(boolean izmenaUnosaGajbi) {
		this.izmenaUnosaGajbi = izmenaUnosaGajbi;
	}

	public TextField getTfSifraGajbe() {
		return tfSifraGajbe;
	}

	public void setTfSifraGajbe(TextField tfSifraGajbe) {
		this.tfSifraGajbe = tfSifraGajbe;
	}

	public TextField getTfNazivGajbe() {
		return tfNazivGajbe;
	}

	public void setTfNazivGajbe(TextField tfNazivGajbe) {
		this.tfNazivGajbe = tfNazivGajbe;
	}

	public TextField getTfTezinaGajbe() {
		return tfTezinaGajbe;
	}

	public void setTfTezinaGajbe(TextField tfTezinaGajbe) {
		this.tfTezinaGajbe = tfTezinaGajbe;
	}

	public TextField getTfUkupnoNaRaspolaganjuGajbi() {
		return tfUkupnoNaRaspolaganjuGajbi;
	}

	public void setTfUkupnoNaRaspolaganjuGajbi(TextField tfUkupnoNaRaspolaganjuGajbi) {
		this.tfUkupnoNaRaspolaganjuGajbi = tfUkupnoNaRaspolaganjuGajbi;
	}

	public Button getBDodajGajbu() {
		return BDodajGajbu;
	}

	public void setBDodajGajbu(Button bDodajGajbu) {
		BDodajGajbu = bDodajGajbu;
	}

	public Button getBSacuvajGajbu() {
		return BSacuvajGajbu;
	}

	public void setBSacuvajGajbu(Button bSacuvajGajbu) {
		BSacuvajGajbu = bSacuvajGajbu;
	}

	public Button getBPonistiGajbu() {
		return BPonistiGajbu;
	}

	public void setBPonistiGajbu(Button bPonistiGajbu) {
		BPonistiGajbu = bPonistiGajbu;
	}

	public Button getBIzmeniGajbu() {
		return BIzmeniGajbu;
	}

	public void setBIzmeniGajbu(Button bIzmeniGajbu) {
		BIzmeniGajbu = bIzmeniGajbu;
	}

	public Button getBObrisiGajbu() {
		return BObrisiGajbu;
	}

	public void setBObrisiGajbu(Button bObrisiGajbu) {
		BObrisiGajbu = bObrisiGajbu;
	}

	public TableView<Gajba> getTabelaGajbe() {
		return tabelaGajbe;
	}

	public void setTabelaGajbe(TableView<Gajba> tabelaGajbe) {
		this.tabelaGajbe = tabelaGajbe;
	}

	public static void setInstance(GajbeTab instance) {
		GajbeTab.instance = instance;
	}

	public TextField getTfSifraUnosaGajbi() {
		return tfSifraUnosaGajbi;
	}

	public void setTfSifraUnosaGajbi(TextField tfSifraUnosaGajbi) {
		this.tfSifraUnosaGajbi = tfSifraUnosaGajbi;
	}

	public DatePicker getDpDatumUnosaGajbi() {
		return dpDatumUnosaGajbi;
	}

	public void setDpDatumUnosaGajbi(DatePicker dpDatumUnosaGajbi) {
		this.dpDatumUnosaGajbi = dpDatumUnosaGajbi;
	}

	public ComboBox<Proizvodjac> getCbProizvodjac() {
		return cbProizvodjac;
	}

	public void setCbProizvodjac(ComboBox<Proizvodjac> cbProizvodjac) {
		this.cbProizvodjac = cbProizvodjac;
	}

	public ComboBox<Prevoznik> getCbPrevoznik() {
		return cbPrevoznik;
	}

	public void setCbPrevoznik(ComboBox<Prevoznik> cbPrevoznik) {
		this.cbPrevoznik = cbPrevoznik;
	}

	public ComboBox<Gajba> getCbGajba() {
		return cbGajba;
	}

	public void setCbGajba(ComboBox<Gajba> cbGajba) {
		this.cbGajba = cbGajba;
	}

	public TextField getTfUlazGajbi() {
		return tfUlazGajbi;
	}

	public void setTfUlazGajbi(TextField tfUlazGajbi) {
		this.tfUlazGajbi = tfUlazGajbi;
	}

	public TextField getTfIzlazGajbi() {
		return tfIzlazGajbi;
	}

	public void setTfIzlazGajbi(TextField tfIzlazGajbi) {
		this.tfIzlazGajbi = tfIzlazGajbi;
	}

	public Button getBDodajUnosGajbe() {
		return BDodajUnosGajbe;
	}

	public void setBDodajUnosGajbe(Button bDodajUnosGajbe) {
		BDodajUnosGajbe = bDodajUnosGajbe;
	}

	public Button getBSacuvajUnosGajbe() {
		return BSacuvajUnosGajbe;
	}

	public void setBSacuvajUnosGajbe(Button bSacuvajUnosGajbe) {
		BSacuvajUnosGajbe = bSacuvajUnosGajbe;
	}

	public Button getBPonistiUnosGajbe() {
		return BPonistiUnosGajbe;
	}

	public void setBPonistiUnosGajbe(Button bPonistiUnosGajbe) {
		BPonistiUnosGajbe = bPonistiUnosGajbe;
	}

	public Button getBIzmeniUnosGajbe() {
		return BIzmeniUnosGajbe;
	}

	public void setBIzmeniUnosGajbe(Button bIzmeniUnosGajbe) {
		BIzmeniUnosGajbe = bIzmeniUnosGajbe;
	}

	public Button getBObrisiUnosGajbe() {
		return BObrisiUnosGajbe;
	}

	public void setBObrisiUnosGajbe(Button bObrisiUnosGajbe) {
		BObrisiUnosGajbe = bObrisiUnosGajbe;
	}

	public TableView<UnosGajbi> getTabelaUnosGajbi() {
		return tabelaUnosGajbi;
	}

	public void setTabelaUnosGajbi(TableView<UnosGajbi> tabelaUnosGajbi) {
		this.tabelaUnosGajbi = tabelaUnosGajbi;
	}

	public FlowPane getUnosGajbiFP() {
		return unosGajbiFP;
	}

	public void setUnosGajbiFP(FlowPane unosGajbiFP) {
		this.unosGajbiFP = unosGajbiFP;
	}

	public FlowPane getUnosUnosaGajbiFP() {
		return unosUnosaGajbiFP;
	}

	public void setUnosUnosaGajbiFP(FlowPane unosUnosaGajbiFP) {
		this.unosUnosaGajbiFP = unosUnosaGajbiFP;
	}
	
	public Label getLp1() {
		return lp1;
	}
	
	public Label getLp2() {
		return lp2;
	}
	
	
	
	
	
	
	
	
}
