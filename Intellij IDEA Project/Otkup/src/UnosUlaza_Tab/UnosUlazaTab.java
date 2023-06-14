 package UnosUlaza_Tab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import UnosUlaza_Tab.Tools_K.ObracunajKontroler;
import UnosUlaza_Tab.Tools_K.PonistiPretraguKontroler;
import UnosUlaza_Tab.Tools_K.StampajIzvestajKontroler;
import UnosUlaza_Tab.Tools_K.StampajKIKontroler;
import Main.ComboBoxAutoComplete;
import UnosUlaza_Tab.UnosUlaza_ASCED.DodajKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.IzmeniKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.ObrisiKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.PonistiKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.SacuvajIStampajKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.SacuvajKontroler;
import UnosUlaza_Tab.UnosUlaza_ASCED.StampajKontroler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import model.Izlaz;
import model.Prevoznik;
import model.Proizvodjac;
import model.Ulaz;
import model.UnosUlaza;

public class UnosUlazaTab extends VBox {
	
	
	private static UnosUlazaTab instance;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;
	
	private TextField tfSifra;
	private DatePicker dpDatum;
	private ComboBox<Proizvodjac> cbProizvodjac;
	private TextField tfSifraPrizvodjaca;
	private ComboBox<Ulaz> cbIUlaz;
	private TextField tfCena;
	private TextField tfUlazGajbe;
	private TextField tfIzlazGajbe;
	private ComboBox<Gajba> cbGajba;
	private TextField tfKolicinaBruto;
	private TextField tfKolicnaNeto;
	private ComboBox<Prevoznik> cbPrevoznik;
	private HBox vrstaGajbeHB;
	private HBox izlazGajbiHB;
	
	private FlowPane unosFP;
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	private Button BStampaj;
	private Button BSacuvajiStampaj;
	
	private RadioButton RBDanPretraga;
	private RadioButton RBPeriodPretraga;
	private ToggleGroup TGDanPeriodPretraga;
	private DatePicker DPPocetniPretraga;
	private DatePicker DPkrajnjiPretraga;
	private ComboBox<Proizvodjac> CBProizvodjacPretraga;
	private ComboBox<Ulaz> CBUlazPretraga;
	private ComboBox<Prevoznik> CBPrevoznikPretraga;
	private Button BPonistiPretragu;
	private HBox trakaZaPretraguHB;
	private Label Lod;
	private Label Ldo;
	private int pozicija = 6;
	
	private RadioButton RBSviObracun;
	private RadioButton RBFiltriraniObracun; 
	private ToggleGroup TGObracun;
	private Button BObracunaj;
	
	private RadioButton RBDnevniIzvestaj;
	private RadioButton RBPeriodicniIzvestaj;
	private RadioButton RBPeriodicniPoDanimaIzvestaj;
	private ToggleGroup TGIzvestaj;
	private DatePicker DPPocetniIzvestraj;
	private DatePicker DPKrajnjiIzvestaj;
	private Button BStamopajIzvestaj;
	private Label LodIzvestaj;
	private Label ldoIzvestaj;
	private int izvestajTip = 1;
	
	private Button BStamajPaletniList;
	private ComboBox<String> cbObeleživač;
	private RadioButton RBKartonIndentifikacije;
	private RadioButton RBMikerPapiric;
	private RadioButton RBFertodiPapiric;
	private ToggleGroup TGObelezivaci;
	
	private TableView<UnosUlaza> tabela;
	
	private UnosUlazaTab() {
		
		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		Label naslov = new Label("Unos ulaza (otkupnih listova):");
		naslov.setFont(new Font(35));
		this.getChildren().add(naslov);
		
		trakZaUnos();
		trakaKomandi();
		tabela();
		trakaZaPretragu();
		trakaZaObracun();
		trakaZaStampuIzvestaja();
		trakaZaStampuPaletnihListova();
	}
	
	private void trakZaUnos() {
		
		tfSifra = new TextField();
		tfSifra.setPrefWidth(40);
		dpDatum = new DatePicker();		
		dpDatum.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,LocalDate newValue) {
				if(observable.getValue() != null && cbIUlaz.getValue()!= null && cbPrevoznik.getValue() != null && cbProizvodjac.getValue() != null) {
					if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
						try {
							tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));							
						} catch (Exception e) {
							tfCena.clear();
						}
					}
					else {
						try {
							tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
						} catch (Exception e) {
							tfCena.clear();
						}
					}
				}
			}		
		});
		
		cbProizvodjac = new ComboBox<Proizvodjac>();
		tfSifraPrizvodjaca = new TextField();
		tfSifraPrizvodjaca.setPrefWidth(40);

		tfSifraPrizvodjaca.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.equals("")){
					int sifraProizvodjaca;
					try {
						sifraProizvodjaca = Integer.valueOf(newValue);
					} catch (Exception e) {
						Alert ada = new Alert(Alert.AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
						ada.show();
						tfSifraPrizvodjaca.clear();
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

		cbIUlaz = new ComboBox<Ulaz>();		
		cbGajba = new ComboBox<Gajba>();
		
		tfCena = new TextField();
		tfCena.setPrefWidth(100); 
		tfCena.setEditable(false);
		tfKolicinaBruto = new TextField();
		tfKolicinaBruto.setPrefWidth(100);
		
		tfKolicinaBruto.textProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!(String.valueOf(newValue).equals("")) && cbGajba.getSelectionModel().getSelectedItem()!=null && !(tfUlazGajbe.getText().equals(""))) {
					int gajbeUlaz = 0;
					double tezinaGajbve =0;
					double bruto = 0;
					double neto = 0;
					
					try {
						gajbeUlaz = Integer.valueOf(tfUlazGajbe.getText());	
						 tezinaGajbve = cbGajba.getSelectionModel().getSelectedItem().getTezina();
						 bruto = Double.valueOf(tfKolicinaBruto.getText());
						 neto = bruto - (gajbeUlaz*tezinaGajbve);
					} catch (Exception e) {
						Alert ada = new Alert(AlertType.ERROR, "Neispravan format unosa (slovo/broj)!");
						ada.show();
						tfKolicinaBruto.clear();
						return;
					}					
					
					tfKolicnaNeto.setText(String.valueOf(neto));				
				}else {
					tfKolicnaNeto.clear();
				}
			}
		});
		
		tfKolicnaNeto = new TextField();
		tfKolicnaNeto.setPrefWidth(100);
		tfUlazGajbe = new TextField();
		tfUlazGajbe.setPrefWidth(100);
		tfIzlazGajbe = new TextField();
		tfIzlazGajbe.setPrefWidth(100);		
		cbPrevoznik = new ComboBox<Prevoznik>();						
		unosFP = new FlowPane(10,10);
		unosFP.setAlignment(Pos.BASELINE_LEFT);
			
		unosFP.getChildren().add(new Label("šifra:"));
		unosFP.getChildren().add(tfSifra);
		unosFP.getChildren().add(new Label("datum:"));
		unosFP.getChildren().add(dpDatum);
		unosFP.getChildren().add(new Label("proizvođač:"));
		unosFP.getChildren().add(cbProizvodjac);
		unosFP.getChildren().add(new Label("sifra proizvođača:"));
		unosFP.getChildren().add(tfSifraPrizvodjaca);
		unosFP.getChildren().add(new Label("vrsta ulaza:"));
		unosFP.getChildren().add(cbIUlaz);
		unosFP.getChildren().add(new Label("cena po kg (din):"));
		unosFP.getChildren().add(tfCena);
		unosFP.getChildren().add(new Label("gajbe ulaz:"));
		unosFP.getChildren().add(tfUlazGajbe);

		izlazGajbiHB = new HBox(10);
		izlazGajbiHB.setAlignment(Pos.BASELINE_LEFT);
		izlazGajbiHB.getChildren().addAll(new Label("gajbe izlaz:"));
		izlazGajbiHB.getChildren().add(tfIzlazGajbe);
		unosFP.getChildren().add(izlazGajbiHB);

		vrstaGajbeHB = new HBox(10);
		vrstaGajbeHB.setAlignment(Pos.BASELINE_LEFT);
		vrstaGajbeHB.getChildren().addAll(new Label("vrsta gajbe:"),cbGajba);
		unosFP.getChildren().add(vrstaGajbeHB);
		unosFP.getChildren().add(new Label("bruto količina (kg):"));
		unosFP.getChildren().add(tfKolicinaBruto);
		unosFP.getChildren().add(new Label("neto količina (kg):"));
		unosFP.getChildren().add(tfKolicnaNeto);
		unosFP.getChildren().add(new Label("prevoznik:"));
		unosFP.getChildren().add(cbPrevoznik);
		unosFP.setDisable(true);		
		
		getChildren().add(unosFP);	
		
		updateCBProizvodjac();
		updateCBUlaz();
		updateCBPrevoznik();
		updateCBGajba();
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
		BObrisi.setDisable(true);
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setOnAction(new ObrisiKontroler());
		
		BStampaj = new Button("Štampaj");
		BStampaj.setDisable(true);
		ImageView stampaj = new ImageView(Firma.getInstance().getPrintIco());
		BStampaj.setGraphic(stampaj);
		BStampaj.setOnAction(new StampajKontroler());
		
		BSacuvajiStampaj = new Button("Štampaj i Sačuvaj");
		BSacuvajiStampaj.setDisable(true);
		ImageView stampaj2 = new ImageView(Firma.getInstance().getPrintIco());
		ImageView sacuvaj2 = new ImageView(Firma.getInstance().getSaveIco());
		HBox slikeHB = new HBox(5);
		slikeHB.getChildren().addAll(stampaj2,sacuvaj2);
		BSacuvajiStampaj.setGraphic(slikeHB);
		BSacuvajiStampaj.setOnAction(new SacuvajIStampajKontroler());
		
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BDodaj,BSacuvaj,BPonisti,BIzmeni,BObrisi,BStampaj,BSacuvajiStampaj);
		getChildren().add(komandeHB);
		
	}
	
	@SuppressWarnings({ "unchecked" })	
	private void tabela() {

		tabela = new TableView<UnosUlaza>();
		
		TableColumn <UnosUlaza, Integer> tcSifra = new TableColumn<UnosUlaza,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Integer>("sifra"));
		
		TableColumn <UnosUlaza, Date> tcDatum = new TableColumn<UnosUlaza,Date>("datum:");		
		tcDatum.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Date>("datum"));
		
		TableColumn <UnosUlaza, Proizvodjac> tcProizvodjac = new TableColumn<UnosUlaza,Proizvodjac>("proizvođač");		
		tcProizvodjac.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Proizvodjac>("proizvodjac"));
		
		TableColumn <UnosUlaza, Izlaz> tcUlaz = new TableColumn<UnosUlaza,Izlaz>("vrsta ulaza");	
		tcUlaz.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Izlaz>("ulaz"));
		
		TableColumn <UnosUlaza, Gajba> tcGajba = new TableColumn<UnosUlaza,Gajba>("vrsta gajbe");	
		tcGajba.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Gajba>("gajba"));
		
		TableColumn <UnosUlaza, Integer> tcBrGajbi = new TableColumn<UnosUlaza,Integer>("br. gajbi");		
		tcBrGajbi.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Integer>("gajbe"));
			
		TableColumn <UnosUlaza, Double> tcKolicnaBruto = new TableColumn<UnosUlaza,Double>("količina bruto");		
		tcKolicnaBruto.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Double>("kolicinaBruto"));
		
		TableColumn <UnosUlaza, Double> tcKolicinaNeto = new TableColumn<UnosUlaza,Double>("količina neto");		
		tcKolicinaNeto.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Double>("kolicinaNeto"));
					
		TableColumn <UnosUlaza, Prevoznik> tcPrevoznik = new TableColumn<UnosUlaza,Prevoznik>("prevoznik");		
		tcPrevoznik.setCellValueFactory(new PropertyValueFactory<UnosUlaza, Prevoznik>("prevoznik"));
		
		tabela.getColumns().addAll(tcSifra,tcDatum,tcProizvodjac,tcUlaz,tcGajba,tcBrGajbi,tcKolicnaBruto,tcKolicinaNeto,tcPrevoznik);
		
		updateTabele();
		
		getChildren().add(tabela);		
		
		tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovog == false && izmenaUToku == false) {
		       BObrisi.setDisable(false);
		       BIzmeni.setDisable(false);
		       BPonisti.setDisable(false);
		       BDodaj.setDisable(true);		
		       BStampaj.setDisable(false);
		    }
		});
		
		tabela.scrollTo(tabela.getItems().size()-1);
		
	}

	
	public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()));
		tabela.scrollTo(tabela.getItems().size()-1);
	}
	
	public void updateCBProizvodjac() {	
		unosFP.getChildren().remove(cbProizvodjac);
		cbProizvodjac = new ComboBox<Proizvodjac>();		
		unosFP.getChildren().add(5, cbProizvodjac);
		cbProizvodjac.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		cbProizvodjac.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(cbProizvodjac);

		cbProizvodjac.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue,Proizvodjac newValue) {
				if(newValue != null) {
					cbPrevoznik.getSelectionModel().select(newValue.getPrevoznik());
					tfSifraPrizvodjaca.setText(String.valueOf(newValue.getSifra()));
				}else
					tfSifraPrizvodjaca.setText("");
				cbProizvodjac.requestFocus();
			}

		});

	}
	
	public void updateCBUlaz() {
		unosFP.getChildren().remove(cbIUlaz);
		cbIUlaz = new ComboBox<Ulaz>();
		unosFP.getChildren().add(9 , cbIUlaz);
		cbIUlaz.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
		cbIUlaz.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Ulaz>(cbIUlaz);

		cbIUlaz.valueProperty().addListener(new ChangeListener<Ulaz>() {
			public void changed(ObservableValue<? extends Ulaz> observable, Ulaz oldValue, Ulaz newValue) {
				if(observable != null && dpDatum.getValue()!= null && cbPrevoznik.getValue() != null && cbProizvodjac.getValue() != null) {
					if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
						try {
							tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));
						} catch (Exception e) {
							tfCena.clear();
						}
					}
					else {
						try {
							tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
						}catch (Exception e) {
							tfCena.clear();
						}
					}
				}
			}
		});

	}
	
	public void updateCBPrevoznik() {
		unosFP.getChildren().remove(cbPrevoznik);
		cbPrevoznik = new ComboBox<Prevoznik>();
		unosFP.getChildren().add(21, cbPrevoznik);
		cbPrevoznik.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		cbPrevoznik.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(cbPrevoznik);

		cbPrevoznik.valueProperty().addListener(new ChangeListener<Prevoznik>() {
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				if(observable != null && dpDatum.getValue()!= null && cbIUlaz.getValue() != null && cbProizvodjac.getValue() != null) {
					if(cbPrevoznik.getValue() == Firma.getInstance().getTrenutnaGodina().getLicno()) {
						tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaSaPrevozom()));
					}
					else {
						tfCena.setText(String.valueOf(Firma.getInstance().getTrenutnaGodina().cenaZaDatum(cbIUlaz.getValue(), dpDatum.getValue()).getCenaBezPrevoza()));
					}
				}				
			}
		});
		

	}

	public void updateCBGajba() {

		vrstaGajbeHB.getChildren().remove(cbGajba);
		cbGajba = new  ComboBox<Gajba>();
		vrstaGajbeHB.getChildren().add(1,cbGajba);
		cbGajba.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getGajbe());
		cbGajba.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Gajba>(cbGajba);
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
		DPkrajnjiPretraga = new DatePicker();
		DPPocetniPretraga.setPrefWidth(120);
		DPkrajnjiPretraga.setPrefWidth(120);
		Lod = new Label("datum:");
		Ldo = new Label("do:");
		trakaZaPretraguHB.getChildren().addAll(Lod,DPPocetniPretraga);
		CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
		Label l1 = new Label("proizvođač:");
		CBUlazPretraga = new ComboBox<Ulaz>();
		Label l2 = new Label("ulaz:");
		CBPrevoznikPretraga = new ComboBox<Prevoznik>();
		Label l3 = new Label("prevoznik:");
		trakaZaPretraguHB.getChildren().addAll(l1,CBProizvodjacPretraga,l2,CBUlazPretraga,l3,CBPrevoznikPretraga); 
		BPonistiPretragu = new Button("poništi");
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setDisable(true);
		trakaZaPretraguHB.getChildren().add(BPonistiPretragu);		
		BPonistiPretragu.setOnAction(new PonistiPretraguKontroler());
		
		
		RBDanPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBDanPretraga.isSelected()) {
					trakaZaPretraguHB.getChildren().removeAll(Ldo,DPkrajnjiPretraga);
					DPkrajnjiPretraga.setValue(null);
					DPPocetniPretraga.setValue(null);
					pozicija=6;
					Lod.setText("datum:");;
				}				
			}
		});
		
		RBPeriodPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBPeriodPretraga.isSelected()) {
					trakaZaPretraguHB.getChildren().add(pozicija, Ldo);
					trakaZaPretraguHB.getChildren().add(pozicija+1, DPkrajnjiPretraga);
					DPkrajnjiPretraga.setValue(null);
					DPPocetniPretraga.setValue(null);
					pozicija=8;
					Lod.setText("od:");
				}
				
			}
		});
		
		DPPocetniPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
				uulist = null;
				uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uulist);
				
				if(DPPocetniPretraga.getValue()!= null)
					BPonistiPretragu.setDisable(false);
			}
		});
		
		DPkrajnjiPretraga.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
				uulist = null;
				uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uulist);
				
				if(DPkrajnjiPretraga.getValue()!= null)
					BPonistiPretragu.setDisable(false);
				
			}
		});
		
		updateCBProizvodjacPretraga();
		updateCBUlazPretraga();
		updateCBPrevoznikPretraga();
		
		getChildren().add(trakaZaPretraguHB);
	}
	
	public void updateCBProizvodjacPretraga() {
		trakaZaPretraguHB.getChildren().remove(CBProizvodjacPretraga);
		CBProizvodjacPretraga = new ComboBox<Proizvodjac>();
		trakaZaPretraguHB.getChildren().add(pozicija+1, CBProizvodjacPretraga);
		CBProizvodjacPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getProizvodjaci());
		CBProizvodjacPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Proizvodjac>(CBProizvodjacPretraga);
		CBProizvodjacPretraga.valueProperty().addListener(new ChangeListener<Proizvodjac>() {
			public void changed(ObservableValue<? extends Proizvodjac> observable, Proizvodjac oldValue,Proizvodjac newValue) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
				uulist = null;
				uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uulist);
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);				
			}
		});
	}
	
	public void updateCBUlazPretraga() {
		trakaZaPretraguHB.getChildren().remove(CBUlazPretraga);
		CBUlazPretraga = new ComboBox<Ulaz>();
		trakaZaPretraguHB.getChildren().add(pozicija+3, CBUlazPretraga);
		CBUlazPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getUlazi());
		CBUlazPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Ulaz>(CBUlazPretraga);
		CBUlazPretraga.valueProperty().addListener(new ChangeListener<Ulaz>() {
			public void changed(ObservableValue<? extends Ulaz> observable, Ulaz oldValue, Ulaz newValue) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
				uulist = null;
				uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uulist);
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);				
			}
		});
	}
	
	public void updateCBPrevoznikPretraga() {
		trakaZaPretraguHB.getChildren().remove(CBPrevoznikPretraga);
		CBPrevoznikPretraga = new ComboBox<Prevoznik>();
		trakaZaPretraguHB.getChildren().add(pozicija+5, CBPrevoznikPretraga);
		CBPrevoznikPretraga.getItems().addAll(Firma.getInstance().getTrenutnaGodina().getPrevoznici());
		CBPrevoznikPretraga.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<Prevoznik>(CBPrevoznikPretraga);
		CBPrevoznikPretraga.valueProperty().addListener(new ChangeListener<Prevoznik>() {
			public void changed(ObservableValue<? extends Prevoznik> observable, Prevoznik oldValue,Prevoznik newValue) {
				RBFiltriraniObracun.setSelected(true);
				tabela.getItems().clear();
				ArrayList<UnosUlaza> uulist = new ArrayList<UnosUlaza>();
				uulist = null;
				uulist = Firma.getInstance().getTrenutnaGodina().filtrirajUnoseUlaza(RBDanPretraga.isSelected(), RBPeriodPretraga.isSelected(), DPPocetniPretraga.getValue(), DPkrajnjiPretraga.getValue(), CBProizvodjacPretraga.getSelectionModel().getSelectedItem(), CBUlazPretraga.getSelectionModel().getSelectedItem(), CBPrevoznikPretraga.getSelectionModel().getSelectedItem());
				tabela.getItems().addAll(uulist);
				
				if(newValue != null)
					BPonistiPretragu.setDisable(false);					
			}
		});
	}
	
	public void trakaZaObracun() {
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

	public void trakaZaStampuIzvestaja() {
		
		HBox stampaIzvestajaHB = new HBox(10);
		stampaIzvestajaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaIzvestajaHB.getChildren().add(st);
		Label l1 = new Label("Štampa izveštaja:   ");
		l1.setFont(new Font(20));
		stampaIzvestajaHB.getChildren().add(l1);
		Label l2 = new Label("Vrsta izveštaja:   ");
		l2.setFont(new Font(15));
		stampaIzvestajaHB.getChildren().add(l2);
		RBDnevniIzvestaj = new RadioButton("dnevni");
		RBPeriodicniIzvestaj = new RadioButton("periodični   ");
		RBPeriodicniPoDanimaIzvestaj = new RadioButton("perioodični po danima");
		TGIzvestaj = new ToggleGroup();
		RBDnevniIzvestaj.setToggleGroup(TGIzvestaj);
		RBPeriodicniIzvestaj.setToggleGroup(TGIzvestaj);
		RBPeriodicniPoDanimaIzvestaj.setToggleGroup(TGIzvestaj);
		RBDnevniIzvestaj.setSelected(true);
		stampaIzvestajaHB.getChildren().addAll(RBDnevniIzvestaj,RBPeriodicniIzvestaj,RBPeriodicniPoDanimaIzvestaj);
		DPPocetniIzvestraj = new DatePicker();
		DPPocetniIzvestraj.setPrefWidth(120);
		DPKrajnjiIzvestaj = new DatePicker();
		DPKrajnjiIzvestaj.setPrefWidth(120);
		LodIzvestaj = new Label("Za datum:");
		ldoIzvestaj = new Label("do:");
		ldoIzvestaj.setFont(new Font(15));
		LodIzvestaj.setFont(new Font(15));
		stampaIzvestajaHB.getChildren().addAll(LodIzvestaj,DPPocetniIzvestraj);
		BStamopajIzvestaj = new Button("štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStamopajIzvestaj.setGraphic(ici);
		BStamopajIzvestaj.setOnAction(new StampajIzvestajKontroler());
		stampaIzvestajaHB.getChildren().add(BStamopajIzvestaj);

		RBDnevniIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBDnevniIzvestaj.isSelected() && izvestajTip == 2) {
					izvestajTip = 1;
					stampaIzvestajaHB.getChildren().removeAll(ldoIzvestaj,DPKrajnjiIzvestaj);
					DPKrajnjiIzvestaj.setValue(null);
					DPPocetniIzvestraj.setValue(null);
					LodIzvestaj.setText("Za datum:");
				}
				
			}
		});
		
		RBPeriodicniIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBPeriodicniIzvestaj.isSelected() && izvestajTip == 1) {
					izvestajTip = 2;
					stampaIzvestajaHB.getChildren().add(8, ldoIzvestaj);
					stampaIzvestajaHB.getChildren().add(9, DPKrajnjiIzvestaj);
					DPKrajnjiIzvestaj.setValue(null);
					DPPocetniIzvestraj.setValue(null);
					LodIzvestaj.setText("od:");
					
				}
				
			}
		});

		RBPeriodicniPoDanimaIzvestaj.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(RBPeriodicniPoDanimaIzvestaj.isSelected() && izvestajTip == 1){
					izvestajTip = 2;
					stampaIzvestajaHB.getChildren().add(8, ldoIzvestaj);
					stampaIzvestajaHB.getChildren().add(9, DPKrajnjiIzvestaj);
					DPKrajnjiIzvestaj.setValue(null);
					DPPocetniIzvestraj.setValue(null);
					LodIzvestaj.setText("od:");
				}
			}
		});
		getChildren().add(stampaIzvestajaHB);
	}
	
	public void trakaZaStampuPaletnihListova() {
		HBox stampaHB = new HBox(10);
		stampaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView st = new ImageView(Firma.getInstance().getPrintIco());
		stampaHB.getChildren().add(st);
		Label l1 = new Label("Štampa obeleživača i kartona identifikacije:   ");
		l1.setFont(new Font(20));
		stampaHB.getChildren().add(l1);
		Label l2 = new Label("Štampaj:   ") ;
		l2.setFont(new Font(15));
		stampaHB.getChildren().add(l2);

		cbObeleživač = new ComboBox<String>();
		cbObeleživač.setPromptText("(izaberi)");
		cbObeleživač.getItems().addAll("Karton identifikacije", "Miker obeleživač", "Fertodi obeleživač", "Enrosadira obeleživač", "Tulamin obeleživač","Polka obeleživač", "Kupina obeleživač" );
		cbObeleživač.setTooltip(new Tooltip());
		new ComboBoxAutoComplete<String>(cbObeleživač);


		BStamajPaletniList = new Button("štampaj");
		ImageView ici = new ImageView(Firma.getInstance().getPrintIco());
		BStamajPaletniList.setGraphic(ici);
		BStamajPaletniList.setDisable(true);
		BStamajPaletniList.setOnAction(new StampajKIKontroler());

		cbObeleživač.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				BStamajPaletniList.setDisable(false);
			}
		});

		stampaHB.getChildren().addAll( cbObeleživač, BStamajPaletniList);
		getChildren().add(stampaHB);
	}
	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos	
		tfSifra.clear();
		dpDatum.setValue(null);
		cbIUlaz.getSelectionModel().clearSelection();
		updateCBUlaz();
		cbProizvodjac.getSelectionModel().clearSelection();
		updateCBProizvodjac();
		tfCena.clear();
		tfKolicinaBruto.clear();
		tfKolicnaNeto.clear();
		tfUlazGajbe.clear();
		tfIzlazGajbe.clear();
		cbPrevoznik.getSelectionModel().clearSelection();
		updateCBPrevoznik();
		cbGajba.getSelectionModel().clearSelection();
		updateCBGajba();
	}
	
	public static UnosUlazaTab getInstance() {
		if(instance == null) {
			instance = new UnosUlazaTab();
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

	public void setTfSifra(TextField tfSifra) {
		this.tfSifra = tfSifra;
	}

	public DatePicker getDpDatum() {
		return dpDatum;
	}

	public void setDpDatum(DatePicker dpDatum) {
		this.dpDatum = dpDatum;
	}

	public ComboBox<Proizvodjac> getCbProizvodjac() {
		return cbProizvodjac;
	}

	public void setCbProizvodjac(ComboBox<Proizvodjac> cbProizvodjac) {
		this.cbProizvodjac = cbProizvodjac;
	}

	public ComboBox<Ulaz> getCbIUlaz() {
		return cbIUlaz;
	}

	public void setCbIUlaz(ComboBox<Ulaz> cbIUlaz) {
		this.cbIUlaz = cbIUlaz;
	}

	public TextField getTfKolicinaBruto() {
		return tfKolicinaBruto;
	}

	public void setTfKolicinaBruto(TextField tfKolicinaBruto) {
		this.tfKolicinaBruto = tfKolicinaBruto;
	}

	public TextField getTfKolicnaNeto() {
		return tfKolicnaNeto;
	}

	public void setTfKolicnaNeto(TextField tfKolicnaNeto) {
		this.tfKolicnaNeto = tfKolicnaNeto;
	}

	public TextField getTfUlazGajbe() {
		return tfUlazGajbe;
	}
	
	public Button getBSacuvajiStampaj() {
		return BSacuvajiStampaj;
	}

	public void setTfUlazGajbe(TextField tfUlazGajbe) {
		this.tfUlazGajbe = tfUlazGajbe;
	}

	public TextField getTfIzlazGajbe() {
		return tfIzlazGajbe;
	}

	public void setTfIzlazGajbe(TextField tfIzlazGajbe) {
		this.tfIzlazGajbe = tfIzlazGajbe;
	}

	public ComboBox<Prevoznik> getCbPrevoznik() {
		return cbPrevoznik;
	}

	public void setCbPrevoznik(ComboBox<Prevoznik> cbPrevoznik) {
		this.cbPrevoznik = cbPrevoznik;
	}

	public FlowPane getUnosFP() {
		return unosFP;
	}

	public void setUnosFP(FlowPane unosFP) {
		this.unosFP = unosFP;
	}

	public void setBStampaj(Button bStampaj) {
		BStampaj = bStampaj;
	}

	public void setBSacuvajiStampaj(Button bSacuvajiStampaj) {
		BSacuvajiStampaj = bSacuvajiStampaj;
	}

	public Button getBDodaj() {
		return BDodaj;
	}

	public Button getBStampaj() {
		return BStampaj;
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

	public TableView<UnosUlaza> getTabela() {
		return tabela;
	}

	public static void setInstance(UnosUlazaTab instance) {
		UnosUlazaTab.instance = instance;
	}

	public ComboBox<Gajba> getCbGajba() {
		return cbGajba;
	}

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
	}

	public DatePicker getDPPocetniPretraga() {
		return DPPocetniPretraga;
	}

	public DatePicker getDPkrajnjiPretraga() {
		return DPkrajnjiPretraga;
	}

	public RadioButton getRBSviObracun() {
		return RBSviObracun;
	}

	public RadioButton getRBFiltriraniObracun() {
		return RBFiltriraniObracun;
	}

	public RadioButton getRBDnevniIzvestaj() {
		return RBDnevniIzvestaj;
	}

	public RadioButton getRBPeriodicniIzvestaj() {
		return RBPeriodicniIzvestaj;
	}

	public DatePicker getDPPocetniIzvestraj() {
		return DPPocetniIzvestraj;
	}

	public DatePicker getDPKrajnjiIzvestaj() {
		return DPKrajnjiIzvestaj;
	}

	public RadioButton getRBKartonIndentifikacije() {
		return RBKartonIndentifikacije;
	}

	public TextField getTfSifraPrizvodjaca() {
		return tfSifraPrizvodjaca;
	}

	public void setTfSifraPrizvodjaca(TextField tfSifraPrizvodjaca) {
		this.tfSifraPrizvodjaca = tfSifraPrizvodjaca;
	}

	public TextField getTfCena() {
		return tfCena;
	}

	public void setTfCena(TextField tfCena) {
		this.tfCena = tfCena;
	}

	public void setCbGajba(ComboBox<Gajba> cbGajba) {
		this.cbGajba = cbGajba;
	}

	public HBox getVrstaGajbeHB() {
		return vrstaGajbeHB;
	}

	public void setVrstaGajbeHB(HBox vrstaGajbeHB) {
		this.vrstaGajbeHB = vrstaGajbeHB;
	}

	public HBox getIzlazGajbiHB() {
		return izlazGajbiHB;
	}

	public void setIzlazGajbiHB(HBox izlazGajbiHB) {
		this.izlazGajbiHB = izlazGajbiHB;
	}

	public RadioButton getRBDanPretraga() {
		return RBDanPretraga;
	}

	public void setRBDanPretraga(RadioButton RBDanPretraga) {
		this.RBDanPretraga = RBDanPretraga;
	}

	public RadioButton getRBPeriodPretraga() {
		return RBPeriodPretraga;
	}

	public void setRBPeriodPretraga(RadioButton RBPeriodPretraga) {
		this.RBPeriodPretraga = RBPeriodPretraga;
	}

	public ToggleGroup getTGDanPeriodPretraga() {
		return TGDanPeriodPretraga;
	}

	public void setTGDanPeriodPretraga(ToggleGroup TGDanPeriodPretraga) {
		this.TGDanPeriodPretraga = TGDanPeriodPretraga;
	}

	public void setDPPocetniPretraga(DatePicker DPPocetniPretraga) {
		this.DPPocetniPretraga = DPPocetniPretraga;
	}

	public void setDPkrajnjiPretraga(DatePicker DPkrajnjiPretraga) {
		this.DPkrajnjiPretraga = DPkrajnjiPretraga;
	}

	public ComboBox<Proizvodjac> getCBProizvodjacPretraga() {
		return CBProizvodjacPretraga;
	}

	public void setCBProizvodjacPretraga(ComboBox<Proizvodjac> CBProizvodjacPretraga) {
		this.CBProizvodjacPretraga = CBProizvodjacPretraga;
	}

	public ComboBox<Ulaz> getCBUlazPretraga() {
		return CBUlazPretraga;
	}

	public void setCBUlazPretraga(ComboBox<Ulaz> CBUlazPretraga) {
		this.CBUlazPretraga = CBUlazPretraga;
	}

	public ComboBox<Prevoznik> getCBPrevoznikPretraga() {
		return CBPrevoznikPretraga;
	}

	public void setCBPrevoznikPretraga(ComboBox<Prevoznik> CBPrevoznikPretraga) {
		this.CBPrevoznikPretraga = CBPrevoznikPretraga;
	}

	public void setBPonistiPretragu(Button BPonistiPretragu) {
		this.BPonistiPretragu = BPonistiPretragu;
	}

	public HBox getTrakaZaPretraguHB() {
		return trakaZaPretraguHB;
	}

	public void setTrakaZaPretraguHB(HBox trakaZaPretraguHB) {
		this.trakaZaPretraguHB = trakaZaPretraguHB;
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

	public void setRBSviObracun(RadioButton RBSviObracun) {
		this.RBSviObracun = RBSviObracun;
	}

	public void setRBFiltriraniObracun(RadioButton RBFiltriraniObracun) {
		this.RBFiltriraniObracun = RBFiltriraniObracun;
	}

	public ToggleGroup getTGObracun() {
		return TGObracun;
	}

	public void setTGObracun(ToggleGroup TGObracun) {
		this.TGObracun = TGObracun;
	}

	public Button getBObracunaj() {
		return BObracunaj;
	}

	public void setBObracunaj(Button BObracunaj) {
		this.BObracunaj = BObracunaj;
	}

	public void setRBDnevniIzvestaj(RadioButton RBDnevniIzvestaj) {
		this.RBDnevniIzvestaj = RBDnevniIzvestaj;
	}

	public void setRBPeriodicniIzvestaj(RadioButton RBPeriodicniIzvestaj) {
		this.RBPeriodicniIzvestaj = RBPeriodicniIzvestaj;
	}

	public ToggleGroup getTGIzvestaj() {
		return TGIzvestaj;
	}

	public void setTGIzvestaj(ToggleGroup TGIzvestaj) {
		this.TGIzvestaj = TGIzvestaj;
	}

	public void setDPPocetniIzvestraj(DatePicker DPPocetniIzvestraj) {
		this.DPPocetniIzvestraj = DPPocetniIzvestraj;
	}

	public void setDPKrajnjiIzvestaj(DatePicker DPKrajnjiIzvestaj) {
		this.DPKrajnjiIzvestaj = DPKrajnjiIzvestaj;
	}

	public Button getBStamopajIzvestaj() {
		return BStamopajIzvestaj;
	}

	public void setBStamopajIzvestaj(Button BStamopajIzvestaj) {
		this.BStamopajIzvestaj = BStamopajIzvestaj;
	}

	public Label getLodIzvestaj() {
		return LodIzvestaj;
	}

	public void setLodIzvestaj(Label lodIzvestaj) {
		LodIzvestaj = lodIzvestaj;
	}

	public Label getLdoIzvestaj() {
		return ldoIzvestaj;
	}

	public void setLdoIzvestaj(Label ldoIzvestaj) {
		this.ldoIzvestaj = ldoIzvestaj;
	}

	public Button getBStamajPaletniList() {
		return BStamajPaletniList;
	}

	public void setBStamajPaletniList(Button BStamajPaletniList) {
		this.BStamajPaletniList = BStamajPaletniList;
	}

	public void setRBKartonIndentifikacije(RadioButton RBKartonIndentifikacije) {
		this.RBKartonIndentifikacije = RBKartonIndentifikacije;
	}

	public RadioButton getRBMikerPapiric() {
		return RBMikerPapiric;
	}

	public void setRBMikerPapiric(RadioButton RBMikerPapiric) {
		this.RBMikerPapiric = RBMikerPapiric;
	}

	public RadioButton getRBFertodiPapiric() {
		return RBFertodiPapiric;
	}

	public void setRBFertodiPapiric(RadioButton RBFertodiPapiric) {
		this.RBFertodiPapiric = RBFertodiPapiric;
	}

	public ToggleGroup getTGObelezivaci() {
		return TGObelezivaci;
	}

	public void setTGObelezivaci(ToggleGroup TGObelezivaci) {
		this.TGObelezivaci = TGObelezivaci;
	}

	public void setTabela(TableView<UnosUlaza> tabela) {
		this.tabela = tabela;
	}

	public RadioButton getRBPeriodicniPoDanimaIzvestaj() { return RBPeriodicniPoDanimaIzvestaj; }

	public ComboBox<String> getCbObeleživač() { return cbObeleživač; }
}
