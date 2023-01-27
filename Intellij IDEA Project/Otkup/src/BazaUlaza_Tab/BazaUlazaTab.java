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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.CenaUlaza;
import model.Firma;
import model.Ulaz;

public class BazaUlazaTab extends VBox{
	
	private static BazaUlazaTab instance;
	
	private HBox zajednickoHB;
	
	private VBox ulaziVB;
	private VBox ceneVB;
	
	private boolean unosNovogUlaza=false;
	private boolean izmenaUlazaUToku = false;
	
	private boolean unosNoveCene=false;
	private boolean izmenaCeneUToku = false;
	
	private TextField tfSifraUlaza;    ///za ulaze
	private TextField tfNazivUlaza;
	private TextField tfOpisUlaza;
	
	private FlowPane unosUlazaHB;
	
	private Button BDodajUlaz;
	private Button BSacuvajUlaz;
	private Button BPonistiUlaz;
	private Button BIzmeniUlaz;
	private Button BObrisiUlaz;
	
	private TableView<Ulaz> tabelaUlaza;
	
	private ComboBox<Ulaz> cbUlaz;  /////////////////////za cene
	private TextField tfSifraCene;   
	private TextField tfCenaBezPrevoza;
	private TextField tfCenaSaPrevozom;
	private DatePicker dpPocetakVazenjaCene;
	private DatePicker dpKrajVazenjaCene;

	private FlowPane unosiCena1HB;
	private FlowPane unosiCena2HB;
	
	private Button BDodajCenu;
	private Button BSacuvajCenu;
	private Button BPonistiCenu;
	private Button BIzmeniCenu;
	private Button BObrisiCenu;
	
	private TableView<CenaUlaza> tabelaCena;
	
	private TextField tfPretragaCena;
	private DatePicker dpPretragaCena;
	private Button BPonistiPretraguCena;
	
	
	private RadioButton RBSveCene;
	private RadioButton RBFiltriraneCene;
	private ToggleGroup TGCenovnik;
	private Button BStampajCene;
	private RadioButton RBPredHladnjacom;
	private RadioButton RBNaOtkupnomMestu;
	private ToggleGroup TGvrstaCenovbnika;
	
	private TextField tfPretragaUlaza;
	private Button BPonistiPretraguUlaza;
	
	private Button BStampajSpisakUlaza;
	private RadioButton RBSviUlaziSpisak;
	private RadioButton RBFiltriraniUlaziSpisak;
	private ToggleGroup TGSpisakUlaza;
	
	private BazaUlazaTab() {
		
		zajednickoHB = new HBox(20);
		ulaziVB = new VBox(15);
		ulaziVB.setPrefWidth(650);
		ceneVB = new VBox(15);
		ceneVB.setPrefWidth(1050);
		
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		
		Label naslov2 = new Label("Ulazi:");
		naslov2.setFont(new Font(35));
		ulaziVB.getChildren().add(naslov2);
		
		Label naslov3 = new Label("Cene :");
		naslov3.setFont(new Font(35));
		ceneVB.getChildren().add(naslov3);
					
		trakZaUnosUlaza();
		trakaKomandiUlaza();
		tabelaUlaza();
		
		trakZaUnosCenaUlaza();
		trakaKomandiCena();
		tabelaCena();
		pretragaCena();
		stampaCenovnika();
		pretragaUlaza();
		stampaSpisakUlaza();
		
		zajednickoHB.getChildren().add(ulaziVB);
		zajednickoHB.getChildren().add(ceneVB);
		this.getChildren().add(zajednickoHB);
	
	}
	
	private void trakZaUnosUlaza() {
		
		tfSifraUlaza = new TextField();
		tfSifraUlaza.setPrefWidth(40);
		tfNazivUlaza = new TextField();
		tfNazivUlaza.setPrefWidth(160);
		tfOpisUlaza = new TextField();
		tfOpisUlaza.setPrefWidth(150);
		Pane razmak = new Pane();
		razmak.minHeightProperty().bind(tfSifraUlaza.heightProperty());
				
		unosUlazaHB = new FlowPane(10,10);
		unosUlazaHB.setAlignment(Pos.BASELINE_LEFT);
			
		unosUlazaHB.getChildren().add(new Label("šifra:"));
		unosUlazaHB.getChildren().add(tfSifraUlaza);
		unosUlazaHB.getChildren().add(new Label("naziv:"));
		unosUlazaHB.getChildren().add(tfNazivUlaza);
		unosUlazaHB.getChildren().add(new Label("opis:"));
		unosUlazaHB.getChildren().add(tfOpisUlaza);
		
		
		ulaziVB.getChildren().addAll(unosUlazaHB,razmak);
		
		unosUlazaHB.setDisable(true);		
	}
	
	private void trakZaUnosCenaUlaza() {
		
		tfSifraCene = new TextField();
		tfSifraCene.setPrefWidth(40);
		
		cbUlaz = new ComboBox<Ulaz>();	
				
		tfCenaBezPrevoza = new TextField();
		tfCenaBezPrevoza.setPrefWidth(80);
		tfCenaSaPrevozom = new TextField();
		tfCenaSaPrevozom.setPrefWidth(80);
		dpPocetakVazenjaCene = new DatePicker();
		dpKrajVazenjaCene = new DatePicker();
				
		unosiCena1HB = new FlowPane(10,10);
		unosiCena1HB.setAlignment(Pos.BASELINE_LEFT);
		unosiCena2HB = new FlowPane(10,10);
		unosiCena2HB.setAlignment(Pos.BASELINE_LEFT);
			
		unosiCena1HB.getChildren().add(new Label("šifra:"));
		unosiCena1HB.getChildren().add(tfSifraCene);
		unosiCena1HB.getChildren().add(new Label("ulaz:"));
		unosiCena1HB.getChildren().add(cbUlaz);
		unosiCena1HB.getChildren().add(new Label("cena bez prevoza (din):"));
		unosiCena1HB.getChildren().add(tfCenaBezPrevoza);
		unosiCena1HB.getChildren().add(new Label("cena sa prevozom (din):"));
		unosiCena1HB.getChildren().add(tfCenaSaPrevozom);	
		unosiCena2HB.getChildren().add(new Label("početak važenja cene:"));
		unosiCena2HB.getChildren().add(dpPocetakVazenjaCene);
		unosiCena2HB.getChildren().add(new Label("kraj važenja cene:"));
		unosiCena2HB.getChildren().add(dpKrajVazenjaCene);
		
		ceneVB.getChildren().addAll(unosiCena1HB,unosiCena2HB);
		
		unosiCena1HB.setDisable(true);	
		unosiCena2HB.setDisable(true);
		
		updateCbUlaz();	
	}
	
	private void trakaKomandiUlaza(){
		
		BDodajUlaz = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajUlaz.setGraphic(add); 
		BDodajUlaz.setOnAction(new DodajUlazKontroler());
		
		BSacuvajUlaz = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajUlaz.setGraphic(save);
		BSacuvajUlaz.setOnAction(new SacuvajUlazKontroler());
		BSacuvajUlaz.setDisable(true);
		
		BPonistiUlaz = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiUlaz.setGraphic(cancel);
		BPonistiUlaz.setOnAction(new PonistiUlazKontroler());
		BPonistiUlaz.setDisable(true);
			
		BIzmeniUlaz = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniUlaz.setGraphic(edit);
		BIzmeniUlaz.setDisable(true);
		BIzmeniUlaz.setOnAction(new IzmeniUlazKontroler());
		
		BObrisiUlaz = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiUlaz.setGraphic(delete);
		BObrisiUlaz.setDisable(true);
		BObrisiUlaz.setOnAction(new ObrisiUlazKontroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodajUlaz,BSacuvajUlaz,BPonistiUlaz,BIzmeniUlaz,BObrisiUlaz);
		ulaziVB.getChildren().add(komandeHB);
	
	}
	
	private void trakaKomandiCena(){
		
		BDodajCenu = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodajCenu.setGraphic(add); 
		BDodajCenu.setOnAction(new DodajCenuKontroler());
		
		BSacuvajCenu = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvajCenu.setGraphic(save);
		BSacuvajCenu.setOnAction(new SacuvajCenuKontroler());
		BSacuvajCenu.setDisable(true);
		
		BPonistiCenu = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiCenu.setGraphic(cancel);
		BPonistiCenu.setOnAction(new PonistiCenuKontroler());
		BPonistiCenu.setDisable(true);
			
		BIzmeniCenu = new Button("izmeni");
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeniCenu.setGraphic(edit);
		BIzmeniCenu.setDisable(true);
		BIzmeniCenu.setOnAction(new IzmeniCenuKontroler());
		
		BObrisiCenu = new Button("obriši");
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisiCenu.setGraphic(delete);
		BObrisiCenu.setDisable(true);
		BObrisiCenu.setOnAction(new ObrisiCenuKontroler());
		
		HBox komandeCenaHB = new HBox(10);
		komandeCenaHB.getChildren().addAll(BDodajCenu,BSacuvajCenu,BPonistiCenu,BIzmeniCenu,BObrisiCenu);
		ceneVB.getChildren().add(komandeCenaHB);
	
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
		
		ulaziVB.getChildren().add(tabelaUlaza);		
		
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
		
		ceneVB.getChildren().add(tabelaCena);		
		
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
		HBox pretragaCenaHB = new HBox(10);
		pretragaCenaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaCenaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaCenaHB.getChildren().add(pl);
		pretragaCenaHB.getChildren().add(new Label("naziv ulaza:"));
		tfPretragaCena = new TextField();
		tfPretragaCena.setPrefWidth(150);
		pretragaCenaHB.getChildren().add(tfPretragaCena);
		pretragaCenaHB.getChildren().add(new Label("cena za datum:"));
		dpPretragaCena = new DatePicker();
		dpPretragaCena.setPrefWidth(120);
		pretragaCenaHB.getChildren().add(dpPretragaCena);
		BPonistiPretraguCena = new Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguCena.setGraphic(close);
		BPonistiPretraguCena.setOnAction(new PonisiPretraguCenaKontroler());
		pretragaCenaHB.getChildren().add(BPonistiPretraguCena);
		
		tfPretragaCena.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				BPonistiPretraguCena.setDisable(false);
				RBFiltriraneCene.setSelected(true);
				tabelaCena.getItems().clear();
				tabelaCena.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().filtrirajCeneUlaza(tfPretragaCena.getText(), dpPretragaCena.getValue())));
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
				if(tfPretragaCena.getText().equals("") && dpPretragaCena.getValue()==null) {
					BPonistiPretraguCena.setDisable(true);	
					RBSveCene.setSelected(true);
				}
								
			}			
		});
		
		
		BPonistiPretraguCena.setDisable(true);
		ceneVB.getChildren().add(pretragaCenaHB);
		
	}
	
	private void stampaCenovnika(){
		HBox stampaj = new HBox(10);            //////////stampa///////////
		stampaj.setAlignment(Pos.BASELINE_LEFT); 
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaj.getChildren().add(st);
		Label sll = new Label("Štampa cenovnika:   ");
		sll.setFont(new Font(20));
		stampaj.getChildren().add(sll);
		Label l1 = new Label("Štampaj za:  ");
		l1.setFont(new Font(15));
		stampaj.getChildren().add(l1);
		RBSveCene = new RadioButton("sve");
		RBFiltriraneCene = new RadioButton("filtrirane"); 
		RBSveCene.setSelected(true);
		TGCenovnik = new ToggleGroup();
		RBSveCene.setToggleGroup(TGCenovnik);
		RBFiltriraneCene.setToggleGroup(TGCenovnik);
		stampaj.getChildren().addAll(RBSveCene,RBFiltriraneCene);	
		Label l2 = new Label("Cene:  ");
		l2.setFont(new Font(15));
		stampaj.getChildren().add(l2);
		RBPredHladnjacom = new RadioButton("pred hladnjačom");
		RBNaOtkupnomMestu = new RadioButton("na otklupnom mestu");
		TGvrstaCenovbnika = new ToggleGroup();
		RBPredHladnjacom.setToggleGroup(TGvrstaCenovbnika);
		RBNaOtkupnomMestu.setToggleGroup(TGvrstaCenovbnika);
		RBPredHladnjacom.setSelected(true);
		stampaj.getChildren().addAll(RBPredHladnjacom,RBNaOtkupnomMestu);		
		BStampajCene = new Button("štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStampajCene.setGraphic(ici);
		stampaj.getChildren().add(BStampajCene);
		BStampajCene.setOnAction(new StampaCenaKontroler());
		ceneVB.getChildren().add(stampaj);
	}
	
	private void pretragaUlaza() {
		HBox pretragaUlazaHB = new HBox(10);
		pretragaUlazaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaUlazaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaUlazaHB.getChildren().add(pl);
		pretragaUlazaHB.getChildren().add(new Label("naziv/opis:"));
		tfPretragaUlaza = new TextField();
		tfPretragaUlaza.setPrefWidth(150);
		pretragaUlazaHB.getChildren().add(tfPretragaUlaza);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretraguUlaza = new Button("poništi");
		BPonistiPretraguUlaza.setGraphic(close);
		BPonistiPretraguUlaza.setOnAction(new PonistiPretraguUlazaKKontroler());
		BPonistiPretraguUlaza.setDisable(true);
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
		
		ulaziVB.getChildren().add(pretragaUlazaHB);
		
	}
	
	private void stampaSpisakUlaza() {
		HBox spisak = new HBox(10);                       ///stampa spiska//////////
		spisak.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisak.getChildren().add(lista);
		Label sl = new Label("Štampa spiska ulaza:   ");
		sl.setFont(new Font(20));
		spisak.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		kkk.setFont(new Font(15));
		spisak.getChildren().add(kkk);
		RBSviUlaziSpisak = new RadioButton("sve");
		RBFiltriraniUlaziSpisak = new RadioButton("filtrirane");
		RBSviUlaziSpisak.setSelected(true);
		TGSpisakUlaza = new ToggleGroup();
		RBSviUlaziSpisak.setToggleGroup(TGSpisakUlaza);
		RBFiltriraniUlaziSpisak.setToggleGroup(TGSpisakUlaza);
		spisak.getChildren().addAll(RBSviUlaziSpisak,RBFiltriraniUlaziSpisak);
		BStampajSpisakUlaza = new Button("štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BStampajSpisakUlaza.setGraphic(ss);
		spisak.getChildren().add(BStampajSpisakUlaza);
		BStampajSpisakUlaza.setOnAction(new StampaSpisakUlazaKontroler());
		ulaziVB.getChildren().add(spisak);		
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
		unosiCena1HB.getChildren().remove(cbUlaz);
		cbUlaz = new ComboBox<Ulaz>();
		unosiCena1HB.getChildren().add(3,cbUlaz);
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

	public void setTfSifraUlaza(TextField tfSifraUlaza) {
		this.tfSifraUlaza = tfSifraUlaza;
	}

	public TextField getTfNazivUlaza() {
		return tfNazivUlaza;
	}

	public void setTfNazivUlaza(TextField tfNazivUlaza) {
		this.tfNazivUlaza = tfNazivUlaza;
	}

	public TextField getTfOpisUlaza() {
		return tfOpisUlaza;
	}

	public void setTfOpisUlaza(TextField tfOpisUlaza) {
		this.tfOpisUlaza = tfOpisUlaza;
	}

	public Button getBDodajUlaz() {
		return BDodajUlaz;
	}

	public void setBDodajUlaz(Button bDodajUlaz) {
		BDodajUlaz = bDodajUlaz;
	}

	public Button getBSacuvajUlaz() {
		return BSacuvajUlaz;
	}

	public void setBSacuvajUlaz(Button bSacuvajUlaz) {
		BSacuvajUlaz = bSacuvajUlaz;
	}

	public Button getBPonistiUlaz() {
		return BPonistiUlaz;
	}

	public void setBPonistiUlaz(Button bPonistiUlaz) {
		BPonistiUlaz = bPonistiUlaz;
	}

	public Button getBIzmeniUlaz() {
		return BIzmeniUlaz;
	}

	public void setBIzmeniUlaz(Button bIzmeniUlaz) {
		BIzmeniUlaz = bIzmeniUlaz;
	}

	public Button getBObrisiUlaz() {
		return BObrisiUlaz;
	}

	public void setBObrisiUlaz(Button bObrisiUlaz) {
		BObrisiUlaz = bObrisiUlaz;
	}

	public TableView<Ulaz> getTabelaUlaza() {
		return tabelaUlaza;
	}

	public void setTabelaUlaza(TableView<Ulaz> tabelaUlaza) {
		this.tabelaUlaza = tabelaUlaza;
	}

	public ComboBox<Ulaz> getCbUlaz() {
		return cbUlaz;
	}

	public void setCbUlaz(ComboBox<Ulaz> cbUlaz) {
		this.cbUlaz = cbUlaz;
	}

	public TextField getTfSifraCene() {
		return tfSifraCene;
	}

	public void setTfSifraCene(TextField tfSifraCene) {
		this.tfSifraCene = tfSifraCene;
	}

	public TextField getTfCenaBezPrevoza() {
		return tfCenaBezPrevoza;
	}

	public void setTfCenaBezPrevoza(TextField tfCenaBezPrevoza) {
		this.tfCenaBezPrevoza = tfCenaBezPrevoza;
	}

	public TextField getTfCenaSaPrevozom() {
		return tfCenaSaPrevozom;
	}

	public void setTfCenaSaPrevozom(TextField tfCenaSaPrevozom) {
		this.tfCenaSaPrevozom = tfCenaSaPrevozom;
	}

	public DatePicker getDpPocetakVazenjaCene() {
		return dpPocetakVazenjaCene;
	}

	public void setDpPocetakVazenjaCene(DatePicker dpPocetakVazenjaCene) {
		this.dpPocetakVazenjaCene = dpPocetakVazenjaCene;
	}

	public DatePicker getDpKrajVazenjaCene() {
		return dpKrajVazenjaCene;
	}

	public void setDpKrajVazenjaCene(DatePicker dpKrajVazenjaCene) {
		this.dpKrajVazenjaCene = dpKrajVazenjaCene;
	}

	public HBox getZajednickoHB() {
		return zajednickoHB;
	}

	public void setZajednickoHB(HBox zajednickoHB) {
		this.zajednickoHB = zajednickoHB;
	}

	public VBox getUlaziVB() {
		return ulaziVB;
	}

	public void setUlaziVB(VBox ulaziVB) {
		this.ulaziVB = ulaziVB;
	}

	public VBox getCeneVB() {
		return ceneVB;
	}

	public void setCeneVB(VBox ceneVB) {
		this.ceneVB = ceneVB;
	}

	public FlowPane getUnosUlazaHB() {
		return unosUlazaHB;
	}

	public void setUnosUlazaHB(FlowPane unosUlazaHB) {
		this.unosUlazaHB = unosUlazaHB;
	}

	public Button getBDodajCenu() {
		return BDodajCenu;
	}

	public FlowPane getUnosiCena1HB() {
		return unosiCena1HB;
	}

	public void setUnosiCena1HB(FlowPane unosiCena1HB) {
		this.unosiCena1HB = unosiCena1HB;
	}

	public FlowPane getUnosiCena2HB() {
		return unosiCena2HB;
	}

	public void setUnosiCena2HB(FlowPane unosiCena2HB) {
		this.unosiCena2HB = unosiCena2HB;
	}

	public void setBDodajCenu(Button bDodajCenu) {
		BDodajCenu = bDodajCenu;
	}

	public Button getBSacuvajCenu() {
		return BSacuvajCenu;
	}

	public void setBSacuvajCenu(Button bSacuvajCenu) {
		BSacuvajCenu = bSacuvajCenu;
	}

	public Button getBPonistiCenu() {
		return BPonistiCenu;
	}

	public void setBPonistiCenu(Button bPonistiCenu) {
		BPonistiCenu = bPonistiCenu;
	}

	public Button getBIzmeniCenu() {
		return BIzmeniCenu;
	}

	public void setBIzmeniCenu(Button bIzmeniCenu) {
		BIzmeniCenu = bIzmeniCenu;
	}

	public Button getBObrisiCenu() {
		return BObrisiCenu;
	}

	public void setBObrisiCenu(Button bObrisiCenu) {
		BObrisiCenu = bObrisiCenu;
	}

	public TableView<CenaUlaza> getTabelaCena() {
		return tabelaCena;
	}

	public void setTabelaCena(TableView<CenaUlaza> tabelaCena) {
		this.tabelaCena = tabelaCena;
	}

	public static void setInstance(BazaUlazaTab instance) {
		BazaUlazaTab.instance = instance;
	}

	public TextField getTfPretragaCena() {
		return tfPretragaCena;
	}

	public void setTfPretragaCena(TextField tfPretragaCena) {
		this.tfPretragaCena = tfPretragaCena;
	}

	public DatePicker getDpPretragaCena() {
		return dpPretragaCena;
	}

	public void setDpPretragaCena(DatePicker dpPretragaCena) {
		this.dpPretragaCena = dpPretragaCena;
	}

	public Button getBPonistiPretraguCena() {
		return BPonistiPretraguCena;
	}

	public void setBPonistiPretraguCena(Button bPonistiPretraguCena) {
		BPonistiPretraguCena = bPonistiPretraguCena;
	}

	public Button getBStampajCene() {
		return BStampajCene;
	}

	public void setBStampajCene(Button bStampajCene) {
		BStampajCene = bStampajCene;
	}

	public ToggleGroup getTGvrstaCenovbnika() {
		return TGvrstaCenovbnika;
	}

	public void setTGvrstaCenovbnika(ToggleGroup tGvrstaCenovbnika) {
		TGvrstaCenovbnika = tGvrstaCenovbnika;
	}

	public RadioButton getRBPredHladnjacom() {
		return RBPredHladnjacom;
	}

	public void setRBPredHladnjacom(RadioButton rBPredHladnjacom) {
		RBPredHladnjacom = rBPredHladnjacom;
	}

	public RadioButton getRBNaOtkupnomMestu() {
		return RBNaOtkupnomMestu;
	}

	public void setRBNaOtkupnomMestu(RadioButton rBNaOtkupnomMestu) {
		RBNaOtkupnomMestu = rBNaOtkupnomMestu;
	}

	public RadioButton getRBSveCene() {
		return RBSveCene;
	}

	public void setRBSveCene(RadioButton rBSveCene) {
		RBSveCene = rBSveCene;
	}

	public RadioButton getRBFiltriraneCene() {
		return RBFiltriraneCene;
	}

	public void setRBFiltriraneCene(RadioButton rBFiltriraneCene) {
		RBFiltriraneCene = rBFiltriraneCene;
	}

	public ToggleGroup getTGCenovnik() {
		return TGCenovnik;
	}

	public void setTGCenovnik(ToggleGroup tGCenovnik) {
		TGCenovnik = tGCenovnik;
	}

	public TextField getTfPretragaUlaza() {
		return tfPretragaUlaza;
	}

	public void setTfPretragaUlaza(TextField tfPretragaUlaza) {
		this.tfPretragaUlaza = tfPretragaUlaza;
	}

	public Button getBPonistiPretraguUlaza() {
		return BPonistiPretraguUlaza;
	}

	public void setBPonistiPretraguUlaza(Button bPonistiPretraguUlaza) {
		BPonistiPretraguUlaza = bPonistiPretraguUlaza;
	}

	public Button getBStampajSpisakUlaza() {
		return BStampajSpisakUlaza;
	}

	public void setBStampajSpisakUlaza(Button bStampajSpisakUlaza) {
		BStampajSpisakUlaza = bStampajSpisakUlaza;
	}

	public RadioButton getRBSviUlaziSpisak() {
		return RBSviUlaziSpisak;
	}

	public void setRBSviUlaziSpisak(RadioButton rBSviUlaziSpisak) {
		RBSviUlaziSpisak = rBSviUlaziSpisak;
	}

	public RadioButton getRBFiltriraniUlaziSpisak() {
		return RBFiltriraniUlaziSpisak;
	}

	public void setRBFiltriraniUlaziSpisak(RadioButton rBFiltriraniUlaziSpisak) {
		RBFiltriraniUlaziSpisak = rBFiltriraniUlaziSpisak;
	}

	public ToggleGroup getTGSpisakUlaza() {
		return TGSpisakUlaza;
	}

	public void setTGSpisakUlaza(ToggleGroup tGSpisakUlaza) {
		TGSpisakUlaza = tGSpisakUlaza;
	}
	
	
	
	
	
	
}
