package Gajbe_Tab;

import java.awt.*;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import model.*;

public class GajbeTab extends VBox {
	
	private static GajbeTab instance;

	private HBox unosITabelaGajbeHb;
	private HBox unosITabelaUnosGajbiHb;
	
	private boolean unosNoveGajbe = false;
	private boolean izmenaGajbeUToku = false;
	
	private boolean unosUnosaGajbi = false;
	private boolean izmenaUnosaGajbi = false;

	private  GridPane unosGajbiGp;
	private Label lsifragajbe;
	private TextField tfSifraGajbe;   ////za gajbe
	private Label lnazivgajbe;
	private TextField tfNazivGajbe;
	private Label ltezinagajbe;
	private TextField tfTezinaGajbe;
	private Label lgajbinaraspolaganju;
	private TextField tfUkupnoNaRaspolaganjuGajbi;

	private Button BDodajGajbu;
	private Button BSacuvajGajbu;
	private Button BPonistiGajbu;
	private Button BIzmeniGajbu;
	private Button BObrisiGajbu;
	
	private TableView<Gajba> tabelaGajbe;

	private  GridPane unosZaduzenjaGajbiGp;
	private  Label lsifraunosagajbe;
	private TextField tfSifraUnosaGajbi;     ////za unose gajbi
	private  Label ldatumunosagajbi;
	private DatePicker dpDatumUnosaGajbi;
	private  Label lproizvodjac;
	private ComboBox<Proizvodjac> cbProizvodjac;
	private Label lprevoznik;
	private ComboBox<Prevoznik> cbPrevoznik;
	private Label lvrstagajbe;
	private ComboBox<Gajba> cbGajba;
	private Label lulazizlazgajbi;
	private Label lulazgajbi;
	private  Label lizlazgajbi;
	private TextField tfUlazGajbi;
	private TextField tfIzlazGajbi;

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
	//private Label lp2;

	private  HBox obracunGajbiHB;
	private RadioButton RBSveGajbeObracun;
	private RadioButton RBFiltriraneGajbeObrscun;
	private ToggleGroup TGGajbeObracun;
	private Button BObracunajGajbe;

	private HBox pretragaGajbiHB;
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

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Baza Ambalaže:");
		naslov.setFont(new Font(35));
		Separator separator = new Separator();
		this.getChildren().addAll(naslov,separator);

		//unos i tabela Gajbi
		unosITabelaGajbeHb = new HBox(20);
		this.getChildren().add(unosITabelaGajbeHb);

		//podesavanja grid panea
		unosGajbiGp = new GridPane();
		unosGajbiGp.setPadding(new Insets(10, 10, 10, 10));
		unosGajbiGp.setVgap(10);
		unosGajbiGp.setHgap(10);
		unosGajbiGp.setAlignment(Pos.BASELINE_LEFT);
		unosGajbiGp.setStyle("-fx-font: 20px \"Serif\";");
		unosGajbiGp.setMinWidth(690);

		unosGajbi();
		tabelaGajbi();
		tabelaGajbe.setPrefWidth(1300);
		tabelaGajbe.setPrefHeight(350);

		Separator separator2 = new Separator();
		separator2.setOrientation(Orientation.VERTICAL);
		unosITabelaGajbeHb.getChildren().addAll(unosGajbiGp,separator2,tabelaGajbe);

		//naslov 2
		Label naslov2 = new Label("Zaduženja ambalaže:");
		naslov2.setFont(new Font(35));
		Separator separator3 = new Separator();
		this.getChildren().addAll(naslov2,separator3);

		//unos i tabela zaduzenja
		unosITabelaUnosGajbiHb = new HBox(20);
		this.getChildren().add(unosITabelaUnosGajbiHb);

		//podesavanja grid panea za cene
		unosZaduzenjaGajbiGp = new GridPane();
		unosZaduzenjaGajbiGp.setPadding(new Insets(10, 10, 10, 10));
		unosZaduzenjaGajbiGp.setVgap(10);
		unosZaduzenjaGajbiGp.setHgap(10);
		unosZaduzenjaGajbiGp.setAlignment(Pos.BASELINE_LEFT);
		unosZaduzenjaGajbiGp.setStyle("-fx-font: 21px \"Serif\";");
		unosZaduzenjaGajbiGp.setMinWidth(690);

		unosZaduzenja();
		tabelaUnosaGajbi();
		tabelaUnosGajbi.setPrefWidth(1300);
		tabelaUnosGajbi.setPrefHeight(400);

		Separator separator4 = new Separator();
		separator4.setOrientation(Orientation.VERTICAL);
		unosITabelaUnosGajbiHb.getChildren().addAll(unosZaduzenjaGajbiGp,separator4,tabelaUnosGajbi);

		//naslov3
		Label naslov3 = new Label("Alati:");
		naslov3.setFont(new Font(35));
		Separator separator1 = new Separator();
		this.getChildren().addAll(naslov3,separator1);

		//alati
		trakaZaPretraguUnosa();
		obracunUnosaGajbi();
		//pretragaGajbi();
		izvestajUnosaGajbi();

		this.getChildren().addAll(pretragaUnosaGajbiHB,brzObracunHB,stampaIzvestajaHB);


		//pocetni update
		updateCBPrevoznik();
		updateCBPrevoznikPretraga();
		updateCBProizvodjac();
		updateCBProizvodjacPretraga();
		updateCBGajba();
		updateCBGajbaPretraga();

		getBDodajUnosGajbe().requestFocus();
		
	}

	private void unosGajbi(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodajGajbu = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajGajbu.setGraphic(add);
		BDodajGajbu.setPrefWidth(200);
		BDodajGajbu.setOnAction(new DodajGajbuKontroler());

		BSacuvajGajbu = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajGajbu.setGraphic(save);
		BSacuvajGajbu.setPrefWidth(200);
		BSacuvajGajbu.setOnAction(new SacuvajGajbuKontroler());
		BSacuvajGajbu.setDisable(true);

		BPonistiGajbu = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiGajbu.setGraphic(cancel);
		BPonistiGajbu.setPrefWidth(200);
		BPonistiGajbu.setOnAction(new PonistiGajbuKontroler());
		BPonistiGajbu.setDisable(true);

		BIzmeniGajbu = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniGajbu.setGraphic(edit);
		BIzmeniGajbu.setDisable(true);
		BIzmeniGajbu.setPrefWidth(200);
		BIzmeniGajbu.setOnAction(new IzmeniGajbuKontroler());

		BObrisiGajbu = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiGajbu.setGraphic(delete);
		BObrisiGajbu.setDisable(true);
		BObrisiGajbu.setPrefWidth(200);
		BObrisiGajbu.setOnAction(new ObrisiGajbuKontroler());

		//dodavanje gornjih dugmica
		unosGajbiGp.add(BDodajGajbu,0,0,1,1);
		unosGajbiGp.add(BSacuvajGajbu,1,0,1,1);
		unosGajbiGp.add(BPonistiGajbu,2,0,1,1);
		unosGajbiGp.add(BIzmeniGajbu,3,0,1,1);
		unosGajbiGp.add(BObrisiGajbu,4,0,1,1);

		//POLJA ZA UNOS///////////////////////////////////////////////////////////////////////////////

		tfSifraGajbe = new TextField();
		tfSifraGajbe.setPrefWidth(200);

		tfNazivGajbe = new TextField();
		tfNazivGajbe.setPrefWidth(410);

		tfTezinaGajbe = new TextField();
		tfTezinaGajbe.setPrefWidth(200);
		tfTezinaGajbe.setPromptText("[kg]");

		tfUkupnoNaRaspolaganjuGajbi = new TextField();
		tfUkupnoNaRaspolaganjuGajbi.setPrefWidth(200);
		tfUkupnoNaRaspolaganjuGajbi.setPromptText("[kom.]");

		lsifragajbe = new Label("Šifra:");
		unosGajbiGp.add(lsifragajbe,0,2,1,1);
		unosGajbiGp.add(tfSifraGajbe,1,2,1,1);

		lnazivgajbe = new Label("Naziv:");
		unosGajbiGp.add(lnazivgajbe,0,3,1,1);
		unosGajbiGp.add(tfNazivGajbe,1,3,2,1);

		ltezinagajbe = new Label("Težina:");
		unosGajbiGp.add(ltezinagajbe,0,4,1,1);
		unosGajbiGp.add(tfTezinaGajbe,1,4,1,1);

		lgajbinaraspolaganju = new Label("Ukupno na raspolaganju:");
		unosGajbiGp.add(lgajbinaraspolaganju,0,5,2,1);
		unosGajbiGp.add(tfUkupnoNaRaspolaganjuGajbi,2,5,1,1);

		SetUnosGajbeDisable();

	}

	private  void unosZaduzenja(){

		//DUGMICI///////////////////////////////////////////////////////////////////////////////
		BDodajUnosGajbe = new Button("Dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajUnosGajbe.setGraphic(add);
		BDodajUnosGajbe.setPrefWidth(200);
		BDodajUnosGajbe.setOnAction(new DodajUnosGajbiKontroler());

		BSacuvajUnosGajbe = new Button("Sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajUnosGajbe.setGraphic(save);
		BSacuvajUnosGajbe.setPrefWidth(200);
		BSacuvajUnosGajbe.setOnAction(new SacuvajUnosGajbiKontroler());
		BSacuvajUnosGajbe.setDisable(true);

		BPonistiUnosGajbe = new Button("Stop");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiUnosGajbe.setGraphic(cancel);
		BPonistiUnosGajbe.setPrefWidth(200);
		BPonistiUnosGajbe.setOnAction(new PonistiUnosGajbiKontroler());
		BPonistiUnosGajbe.setDisable(true);

		BIzmeniUnosGajbe = new Button("Izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniUnosGajbe.setGraphic(edit);
		BIzmeniUnosGajbe.setDisable(true);
		BIzmeniUnosGajbe.setPrefWidth(200);
		BIzmeniUnosGajbe.setOnAction(new IzmeniUnosGajbiKontroler());

		BObrisiUnosGajbe = new Button("Obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiUnosGajbe.setGraphic(delete);
		BObrisiUnosGajbe.setDisable(true);
		BObrisiUnosGajbe.setPrefWidth(200);
		BObrisiUnosGajbe.setOnAction(new ObrisiUnosGajbiKontroler());

		//dodavanje gornjih dugmica
		unosZaduzenjaGajbiGp.add(BDodajUnosGajbe,0,0,1,1);
		unosZaduzenjaGajbiGp.add(BSacuvajUnosGajbe,1,0,1,1);
		unosZaduzenjaGajbiGp.add(BPonistiUnosGajbe,2,0,1,1);
		unosZaduzenjaGajbiGp.add(BIzmeniUnosGajbe,3,0,1,1);
		unosZaduzenjaGajbiGp.add(BObrisiUnosGajbe,4,0,1,1);

		//unos zaduzenja
		tfSifraUnosaGajbi = new TextField();
		tfSifraUnosaGajbi.setPrefWidth(200);

		dpDatumUnosaGajbi = new DatePicker();
		dpDatumUnosaGajbi.setPrefWidth(410);

		cbProizvodjac = new ComboBox<Proizvodjac>();
		cbProizvodjac.setPrefWidth(620);

		cbPrevoznik = new ComboBox<Prevoznik>();
		cbPrevoznik.setPrefWidth(620);

		cbGajba = new ComboBox<Gajba>();
		cbGajba.setPrefWidth(410);

		tfUlazGajbi = new TextField();
		tfUlazGajbi.setPrefWidth(100);
		tfUlazGajbi.setPromptText("[kom.]");

		tfIzlazGajbi = new TextField();
		tfIzlazGajbi.setPrefWidth(100);
		tfIzlazGajbi.setPromptText("[kom.]");

		//Dodavanje u grid pane///////////////////////////////////////////////////////////////////
		lsifraunosagajbe = new Label("Šifra:");
		unosZaduzenjaGajbiGp.add(lsifraunosagajbe,0,2,1,1);
		unosZaduzenjaGajbiGp.add(tfSifraUnosaGajbi,1,2,1,1);

		ldatumunosagajbi = new Label("Datum:");
		unosZaduzenjaGajbiGp.add(ldatumunosagajbi,0,3,1,1);
		unosZaduzenjaGajbiGp.add(dpDatumUnosaGajbi,1,3,2,1);

		lproizvodjac = new Label("Proizvođač:");
		unosZaduzenjaGajbiGp.add(lproizvodjac,0,4,1,1);
		unosZaduzenjaGajbiGp.add(cbProizvodjac,1,4,3,1);

		lprevoznik = new Label("Prevoznik:");
		unosZaduzenjaGajbiGp.add(lprevoznik,0,5,1,1);
		unosZaduzenjaGajbiGp.add(cbPrevoznik,1,5,3,1);

		lulazizlazgajbi = new Label("Amblaža:");
		unosZaduzenjaGajbiGp.add(lulazizlazgajbi,0,6,1,1);
		lulazgajbi = new Label("ulaz:");
		unosZaduzenjaGajbiGp.add(lulazgajbi,1,6,1,1);
		unosZaduzenjaGajbiGp.setHalignment(lulazgajbi, HPos.RIGHT);
		unosZaduzenjaGajbiGp.add(tfUlazGajbi,2,6,1,1);
		lizlazgajbi = new Label("izlaz:");
		unosZaduzenjaGajbiGp.add(lizlazgajbi,3,6,1,1);
		unosZaduzenjaGajbiGp.setHalignment(lizlazgajbi, HPos.RIGHT);
		unosZaduzenjaGajbiGp.add(tfIzlazGajbi,4,6,1,1);

		lvrstagajbe = new Label("Ambalaža:");
		unosZaduzenjaGajbiGp.add(lvrstagajbe,0,7,1,1);
		unosZaduzenjaGajbiGp.add(cbGajba,1,7,2,1);

		//DUGMICI DOLE///////////////////////////////////////////////////////////////////////////////

		BStampajUnosGajbe = new Button("Štampaj");
		BStampajUnosGajbe.setDisable(true);
		BStampajUnosGajbe.setPrefWidth(200);
		ImageView stampaj = new ImageView(Firma.getInstance().getPrintIco());
		BStampajUnosGajbe.setGraphic(stampaj);
		BStampajUnosGajbe.setOnAction(new StampajUnosGajbeKontroler());

		BSacuvajIStampajUnsoGajbe = new Button("Štampaj i Sačuvaj");
		BSacuvajIStampajUnsoGajbe.setDisable(true);
		BSacuvajIStampajUnsoGajbe.setPrefWidth(410);
		ImageView stampaj2 = new ImageView(Firma.getInstance().getPrintIco());
		ImageView sacuvaj2 = new ImageView(Firma.getInstance().getSaveIco());
		HBox slikeHB = new HBox(5);
		slikeHB.getChildren().addAll(stampaj2,sacuvaj2);
		BSacuvajIStampajUnsoGajbe.setGraphic(slikeHB);
		BSacuvajIStampajUnsoGajbe.setOnAction(new SacuvajiStampajUnsoGajbiKOntroler());

		unosZaduzenjaGajbiGp.add(BStampajUnosGajbe,0,9,1,1);
		unosZaduzenjaGajbiGp.add(BSacuvajIStampajUnsoGajbe,1,9,2,1);

		SetUnosZaduzenjaDisable();

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

		TableColumn <Gajba, String> naterenu = new TableColumn<Gajba,String>("na terenu:");
		naterenu.setCellValueFactory(cellData -> {
			int ukupnoZaduzenih=0;
			int ukupnoRazduzenih=0;
			for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				if(cellData.getValue().equals(ug.getGajba())) {
					ukupnoZaduzenih += ug.getGajbeIzlaz();
					ukupnoRazduzenih += ug.getGajbeUlaz();
				}
			}
			int naTerenu = ukupnoZaduzenih-ukupnoRazduzenih;
			return new SimpleStringProperty(String.valueOf(naTerenu));
		});

		TableColumn <Gajba, String> nastanju = new TableColumn<Gajba,String>("na stanju:");
		nastanju.setCellValueFactory(cellData -> {
			int ukupnoZaduzenih=0;
			int ukupnoRazduzenih=0;
			for(UnosGajbi ug : Firma.getInstance().getTrenutnaGodina().getUnosiGajbi()) {
				if(cellData.getValue().equals(ug.getGajba())) {
					ukupnoZaduzenih += ug.getGajbeIzlaz();
					ukupnoRazduzenih += ug.getGajbeUlaz();
				}
			}
			int naTerenu = ukupnoZaduzenih-ukupnoRazduzenih;
			return new SimpleStringProperty(String.valueOf(cellData.getValue().getUkupnoNaRaspolaganju()-naTerenu));
		});
		
		tabelaGajbe.getColumns().addAll(tcSifra,tcNaziv,tcTezina,tcUkupno,naterenu,nastanju);
		
		updateTabeleGajbi();
		
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

		cbProizvodjac.getItems().clear();
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		cbProizvodjac.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);
		updateCBProizvodjacPretraga();
		cbProizvodjac.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue, Proizvodjac newValue) {
				if(newValue != null) {
					cbPrevoznik.setDisable(true);		
					lprevoznik.setDisable(true);
				}else{
					cbPrevoznik.setDisable(false);
					lprevoznik.setDisable(false);
				}
			}
		});
	}
	
	public void updateCBPrevoznik() {

		cbPrevoznik.getItems().clear();
		cbPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPrevoznik);
		updateCBPrevoznikPretraga();
		cbPrevoznik.valueProperty().addListener(new ChangeListener<Prevoznik>() {
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				if(newValue != null) {
					cbProizvodjac.setDisable(true);		
					lproizvodjac.setDisable(true);
				}else{
					cbProizvodjac.setDisable(false);
					lproizvodjac.setDisable(false);
				}
			}
		});
	}
	
	public void updateCBGajba() {

		cbGajba.getItems().clear();
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

	private void pretragaGajbi() {

		pretragaGajbiHB = new HBox(10);
		pretragaGajbiHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaGajbiHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje gajbi:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaGajbiHB.getChildren().add(pl);
		pretragaGajbiHB.getChildren().add(new Label("naziv:"));
		TFPretragaGajbi = new TextField();
		TFPretragaGajbi.setPrefWidth(150);
		pretragaGajbiHB.getChildren().add(TFPretragaGajbi);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonisitPretraguGajbi = new Button("Poništi");
		BPonisitPretraguGajbi.setPrefWidth(110);
		BPonisitPretraguGajbi.setGraphic(close);
		BPonisitPretraguGajbi.setOnAction(new PonistiPretraguGajbiKontroler());
		BPonisitPretraguGajbi.setDisable(true);
		pretragaGajbiHB.getChildren().add(BPonisitPretraguGajbi);

		TFPretragaGajbi.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				RBFiltriraneGajbeObrscun.setSelected(true);
				BPonisitPretraguGajbi.setDisable(false);
				tabelaGajbe.getItems().clear();
				//tabelaGajbe.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajGajbe(TFPretragaGajbi.getText())));
				if(TFPretragaGajbi.getText().equals("")) {
					BPonisitPretraguGajbi.setDisable(true);
					RBSveGajbeObracun.setSelected(true);
				}

			}
		});
	}

	private void obracunGajbi() {

		obracunGajbiHB = new HBox(10);
		obracunGajbiHB.setStyle("-fx-font: 17px \"Serif\";");
		obracunGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		obracunGajbiHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun gajbi:  ");
		ol.setStyle("-fx-font: 25px \"System\";");
		obracunGajbiHB.getChildren().add(ol);
		Label lll = new Label("Obračunaj za:  ");
		obracunGajbiHB.getChildren().add(lll);
		RBSveGajbeObracun = new RadioButton("sve");
		RBSveGajbeObracun.setSelected(true);
		RBFiltriraneGajbeObrscun = new RadioButton("filtrirane");
		TGGajbeObracun = new ToggleGroup();
		RBSveGajbeObracun.setToggleGroup(TGGajbeObracun);
		RBFiltriraneGajbeObrscun.setToggleGroup(TGGajbeObracun);
		obracunGajbiHB.getChildren().addAll(RBSveGajbeObracun,RBFiltriraneGajbeObrscun);
		BObracunajGajbe = new Button("Obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunajGajbe.setGraphic(obracunaj);
		BObracunajGajbe.setPrefWidth(130);
		BObracunajGajbe.setOnAction(new ObracunajGajbeKontroler()  );
		obracunGajbiHB.getChildren().add(BObracunajGajbe);

	}

	private void trakaZaPretraguUnosa() {

		pretragaUnosaGajbiHB = new HBox(10);
		pretragaUnosaGajbiHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaUnosaGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaUnosaGajbiHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaUnosaGajbiHB.getChildren().add(pl);
		Label l2 = new Label("Gajba:");
		pretragaUnosaGajbiHB.getChildren().add(l2);
		CBGajbaPretraga = new ComboBox<Gajba>();
		pretragaUnosaGajbiHB.getChildren().add(CBGajbaPretraga);
		Label l1 = new Label("Prikži:");
		pretragaUnosaGajbiHB.getChildren().add(l1);
		RBproizvodjac = new RadioButton("proizvođače");
		RBprevoznik = new  RadioButton("prevoznike");
		TGizbor = new ToggleGroup();
		RBproizvodjac.setToggleGroup(TGizbor);
		RBprevoznik.setToggleGroup(TGizbor);

		pretragaUnosaGajbiHB.getChildren().addAll(RBproizvodjac,RBprevoznik);
		lp1 = new Label("");
		pretragaUnosaGajbiHB.getChildren().add(lp1);


		CBprevoznikPretraga = new ComboBox<Prevoznik>();
		CBproizvodjacPretraga = new ComboBox<Proizvodjac>();

		BPonistiPretragu = new Button("Poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setDisable(true);
		BPonistiPretragu.setPrefWidth(110);
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

		RBproizvodjac.setSelected(false);
		RBprevoznik.setSelected(false);
		updateCBGajbaPretraga();
		updateCBProizvodjacPretraga();
		updateCBPrevoznikPretraga();

	}

	private void obracunUnosaGajbi() {

		brzObracunHB = new HBox(10);             ///////////////obracunnnn//////////////
		brzObracunHB.setStyle("-fx-font: 17px \"Serif\";");
		brzObracunHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView obracun = new ImageView(Firma.getInstance().getObracunIco());
		brzObracunHB.getChildren().add(obracun);
		Label ol = new Label("Brz obračun zaduženja:  ");
		ol.setStyle("-fx-font: 25px \"System\";");
		Label lll = new Label("Obračunaj za:  ");
		brzObracunHB.getChildren().add(ol);
		brzObracunHB.getChildren().add(lll);
		RBSviUnosiGajbiObracun = new RadioButton("sve");
		RBFiltriraniUnosiGajbiObracun = new RadioButton("filtrirane");
		TRObracunUnosaGajbi = new ToggleGroup();
		RBSviUnosiGajbiObracun.setToggleGroup(TRObracunUnosaGajbi);
		RBFiltriraniUnosiGajbiObracun.setToggleGroup(TRObracunUnosaGajbi);
		TRObracunUnosaGajbi.selectToggle(null);
		brzObracunHB.getChildren().addAll(RBSviUnosiGajbiObracun,RBFiltriraniUnosiGajbiObracun);
		BObracunajUnoseGajbi = new Button("Obračunaj");
		ImageView obracunaj = new ImageView(Firma.getInstance().getObracunIco());
		BObracunajUnoseGajbi.setGraphic(obracunaj);
		BObracunajUnoseGajbi.setPrefWidth(130);
		BObracunajUnoseGajbi.setOnAction(new ObracunajUnoseGajbiKontroler());
		brzObracunHB.getChildren().add(BObracunajUnoseGajbi);
		TRObracunUnosaGajbi.selectToggle(RBSviUnosiGajbiObracun);

	}

	private void izvestajUnosaGajbi() {

		stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setStyle("-fx-font: 17px \"Serif\";");
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		stampaIzvestajaHB.getChildren().add(lista);
		Label sl = new Label("Štampa izveštaja zaduženja:   ");
		sl.setStyle("-fx-font: 25px \"System\";");
		stampaIzvestajaHB.getChildren().add(sl);
		Label kkk = new Label("Štampaj za:  ");
		stampaIzvestajaHB.getChildren().add(kkk);
		RBproizvodjacIzvestaj = new RadioButton("proizvođača");
		RBPrevoznikIzvestaj = new RadioButton("prevoznika");
		TGIzvestaj = new ToggleGroup();
		RBPrevoznikIzvestaj.setToggleGroup(TGIzvestaj);
		RBproizvodjacIzvestaj.setToggleGroup(TGIzvestaj);
		stampaIzvestajaHB.getChildren().addAll(RBPrevoznikIzvestaj,RBproizvodjacIzvestaj);
		lp = new Label("");
		stampaIzvestajaHB.getChildren().add(lp);

		CBPrevoznikIzvestaj = new ComboBox<Prevoznik>();
		CBProizvodjacIzvestaj = new ComboBox<Proizvodjac>();

		BstamapjIzvestajUnosaGajbi = new Button("Štampaj");
		BstamapjIzvestajUnosaGajbi.setPrefWidth(110);
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

	}


	public void updateCBGajbaPretraga () {

		CBGajbaPretraga.getItems().clear();
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

				tabelaGajbe.getItems().clear();
				tabelaGajbe.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajGajbe(newValue)));

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

	public void SetUnosGajbeDisable(){

		lsifragajbe.setDisable(true);
		tfSifraGajbe.setDisable(true);
		lnazivgajbe.setDisable(true);
		tfNazivGajbe.setDisable(true);
		ltezinagajbe.setDisable(true);
		tfTezinaGajbe.setDisable(true);
		lgajbinaraspolaganju.setDisable(true);
		tfUkupnoNaRaspolaganjuGajbi.setDisable(true);
	}

	public void SetUnosGajbeEnable(){

		lsifragajbe.setDisable(false);
		tfSifraGajbe.setDisable(false);
		lnazivgajbe.setDisable(false);
		tfNazivGajbe.setDisable(false);
		ltezinagajbe.setDisable(false);
		tfTezinaGajbe.setDisable(false);
		lgajbinaraspolaganju.setDisable(false);
		tfUkupnoNaRaspolaganjuGajbi.setDisable(false);
	}

	public void  SetUnosZaduzenjaEnable(){

		lsifraunosagajbe.setDisable(false);
		tfSifraUnosaGajbi.setDisable(false);
		ldatumunosagajbi.setDisable(false);
		dpDatumUnosaGajbi.setDisable(false);
		lproizvodjac.setDisable(false);
		cbProizvodjac.setDisable(false);
		lprevoznik.setDisable(false);
		cbPrevoznik.setDisable(false);
		lvrstagajbe.setDisable(false);
		cbGajba.setDisable(false);
		lulazizlazgajbi.setDisable(false);
		lulazgajbi.setDisable(false);
		lizlazgajbi.setDisable(false);
		tfUlazGajbi.setDisable(false);
		tfIzlazGajbi.setDisable(false);
	}

	public void  SetUnosZaduzenjaDisable(){

		lsifraunosagajbe.setDisable(true);
		tfSifraUnosaGajbi.setDisable(true);
		ldatumunosagajbi.setDisable(true);
		dpDatumUnosaGajbi.setDisable(true);
		lproizvodjac.setDisable(true);
		cbProizvodjac.setDisable(true);
		lprevoznik.setDisable(true);
		cbPrevoznik.setDisable(true);
		lvrstagajbe.setDisable(true);
		cbGajba.setDisable(true);
		lulazizlazgajbi.setDisable(true);
		lulazgajbi.setDisable(true);
		lizlazgajbi.setDisable(true);
		tfUlazGajbi.setDisable(true);
		tfIzlazGajbi.setDisable(true);
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

	public static GajbeTab getInstance() {
		if(instance == null) {
			instance = new GajbeTab();
		}
		return instance;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public RadioButton getRBSveGajbeObracun() {
		return RBSveGajbeObracun;
	}

	public RadioButton getRBSviUnosiGajbiObracun() {
		return RBSviUnosiGajbiObracun;
	}

	public RadioButton getRBFiltriraniUnosiGajbiObracun() {
		return RBFiltriraniUnosiGajbiObracun;
	}

	public RadioButton getRBFiltriraneGajbeObrscun() {
		return RBFiltriraneGajbeObrscun;
	} 

	public RadioButton getRBproizvodjacIzvestaj() {
		return RBproizvodjacIzvestaj;
	}

	public RadioButton getRBPrevoznikIzvestaj() {
		return RBPrevoznikIzvestaj;
	}

	public ComboBox<Prevoznik> getCBPrevoznikIzvestaj() {
		return CBPrevoznikIzvestaj;
	}

	public ComboBox<Proizvodjac> getCBProizvodjacIzvestaj() {
		return CBProizvodjacIzvestaj;
	}

	public Button getBStampajUnosGajbe() {
		return BStampajUnosGajbe;
	}

	public Button getBSacuvajIStampajUnsoGajbe() {
		return BSacuvajIStampajUnsoGajbe;
	}

	public TextField getTFPretragaGajbi() {
		return TFPretragaGajbi;
	}

	public Button getBPonisitPretraguGajbi() {
		return BPonisitPretraguGajbi;
	}

	public boolean isUnosNoveGajbe() {
		return unosNoveGajbe;
	}

	public RadioButton getRBproizvodjac() {
		return RBproizvodjac;
	}

	public RadioButton getRBprevoznik() {
		return RBprevoznik;
	}

	public ComboBox<Proizvodjac> getCBproizvodjacPretraga() {
		return CBproizvodjacPretraga;
	}

	public ComboBox<Gajba> getCBGajbaPretraga() {
		return CBGajbaPretraga;
	}

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
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

	public Button getBDodajGajbu() {
		return BDodajGajbu;
	}

	public Button getBSacuvajGajbu() {
		return BSacuvajGajbu;
	}

	public Button getBPonistiGajbu() {
		return BPonistiGajbu;
	}

	public Button getBIzmeniGajbu() {
		return BIzmeniGajbu;
	}

	public Button getBObrisiGajbu() {
		return BObrisiGajbu;
	}

	public ComboBox<Prevoznik> getCBprevoznikPretraga() {
		return CBprevoznikPretraga;
	}

	public TableView<Gajba> getTabelaGajbe() {
		return tabelaGajbe;
	}

	public static void setInstance(GajbeTab instance) {
		GajbeTab.instance = instance;
	}

	public TextField getTfSifraUnosaGajbi() {
		return tfSifraUnosaGajbi;
	}

	public DatePicker getDpDatumUnosaGajbi() {
		return dpDatumUnosaGajbi;
	}

	public ComboBox<Proizvodjac> getCbProizvodjac() {
		return cbProizvodjac;
	}

	public ComboBox<Prevoznik> getCbPrevoznik() {
		return cbPrevoznik;
	}

	public ComboBox<Gajba> getCbGajba() {
		return cbGajba;
	}

	public TextField getTfUlazGajbi() {
		return tfUlazGajbi;
	}

	public TextField getTfIzlazGajbi() {
		return tfIzlazGajbi;
	}

	public Button getBDodajUnosGajbe() {
		return BDodajUnosGajbe;
	}

	public Button getBSacuvajUnosGajbe() {
		return BSacuvajUnosGajbe;
	}

	public Button getBPonistiUnosGajbe() {
		return BPonistiUnosGajbe;
	}

	public Button getBIzmeniUnosGajbe() {
		return BIzmeniUnosGajbe;
	}

	public Button getBObrisiUnosGajbe() {
		return BObrisiUnosGajbe;
	}

	public TableView<UnosGajbi> getTabelaUnosGajbi() {
		return tabelaUnosGajbi;
	}

	public Label getLp1() {
		return lp1;
	}

	public TextField getTfNazivGajbe() {
		return tfNazivGajbe;
	}

	public TextField getTfUkupnoNaRaspolaganjuGajbi() {
		return tfUkupnoNaRaspolaganjuGajbi;
	}

	public TextField getTfTezinaGajbe() {
		return tfTezinaGajbe;
	}
}
