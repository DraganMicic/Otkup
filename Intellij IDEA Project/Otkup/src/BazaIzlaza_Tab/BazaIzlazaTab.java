package BazaIzlaza_Tab;

import BazaIzlaza_Tab.Izlaz_ASCED.DodajKontroler;
import BazaIzlaza_Tab.Izlaz_ASCED.IzmeniKontroler;
import BazaIzlaza_Tab.Izlaz_ASCED.ObrisiKontroler;
import BazaIzlaza_Tab.Izlaz_ASCED.PonistiKontroler;
import BazaIzlaza_Tab.Izlaz_ASCED.SacuvajKontroler;
import BazaIzlaza_Tab.Tools_K.PonistiPretragu_izlaz_K;
import BazaIzlaza_Tab.Tools_K.StampaSpiska_Izlaz_K;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Firma;
import model.Izlaz;
import model.JedinicaMere;

public class BazaIzlazaTab extends VBox {
	
	private static BazaIzlazaTab instance;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;
	
	private HBox unosHB;					 //inicijalizujem komponente
	private TextField tfSifra;
	private TextField tfNaziv;
	private TextField tfOpis;
	private TextField tfCenaPoKom;
	private ComboBox<JedinicaMere> cbJedMere;
	
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;
	
	private TableView<Izlaz> tabela;
	
	private TextField tfPretraga;
	private Button BPonistiPretragu;
	
	private Button BSrampajSpisak;
	private RadioButton RBSviSpisak;
	private RadioButton RBFiltriraniSpisak;
	private ToggleGroup TGSpisak;
	private Button BStampajSpisak;
	
	
		
	private BazaIzlazaTab() {

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		Label naslov = new Label("Baza Izlaza:");
		naslov.setFont(new Font(35));
		this.getChildren().add(naslov);
		
		trakZaUnos();
		trakaKomandi();
		tabela();
		filtriranje();
		stampaSpisak();
	}
	
	private void trakZaUnos() {
		
		tfSifra = new TextField();
		tfSifra.setPrefWidth(40);
		tfNaziv = new TextField();
		tfNaziv.setPrefWidth(180);
		tfOpis = new TextField();
		tfOpis.setPrefWidth(220);				
		cbJedMere = new ComboBox<JedinicaMere>();			
		cbJedMere.getItems().addAll(Firma.getInstance().getJediniceMere());			
		tfCenaPoKom = new TextField();
		tfCenaPoKom.setPrefWidth(80);
				
		unosHB = new HBox(10);
		unosHB.setAlignment(Pos.BASELINE_LEFT);
			
		unosHB.getChildren().add(new Label("šifra:"));
		unosHB.getChildren().add(tfSifra);
		unosHB.getChildren().add(new Label("naziv:"));
		unosHB.getChildren().add(tfNaziv);
		unosHB.getChildren().add(new Label("opis:"));
		unosHB.getChildren().add(tfOpis);	
		unosHB.getChildren().add(new Label("jedinica mere:"));
		unosHB.getChildren().add(cbJedMere);
		Label ljm = new Label("cena po jedinici mere (din):");
		unosHB.getChildren().add(ljm);
		unosHB.getChildren().add(tfCenaPoKom);
		
		cbJedMere.valueProperty().addListener(new ChangeListener<JedinicaMere>() {
			@Override
			public void changed(ObservableValue<? extends JedinicaMere> observable, JedinicaMere oldValue, JedinicaMere newValue) {
				if(newValue != null && newValue.toString().equals("din")) {
					tfCenaPoKom.setDisable(true);
					ljm.setDisable(true);
				}else {
					tfCenaPoKom.setDisable(false);
					ljm.setDisable(false);
				}
				
			}
		});
				
		getChildren().add(unosHB);
		
		unosHB.setDisable(true);		
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
		getChildren().add(komandeHB);
		
	}
	
	@SuppressWarnings({ "unchecked" })
	
	private void tabela() {
		tabela = new TableView<Izlaz>();
		
		TableColumn <Izlaz, Integer> tcSifra = new TableColumn<Izlaz,Integer>("šifra");		
		tcSifra.setCellValueFactory(new PropertyValueFactory<Izlaz, Integer>("sifra"));
		
		TableColumn <Izlaz, String> tcNaziv = new TableColumn<Izlaz,String>("naziv");		
		tcNaziv.setCellValueFactory(new PropertyValueFactory<Izlaz, String>("naziv"));
		
		TableColumn <Izlaz, String> tcOpis = new TableColumn<Izlaz,String>("opis");		
		tcOpis.setCellValueFactory(new PropertyValueFactory<Izlaz, String>("opis"));
		
		TableColumn <Izlaz, JedinicaMere> tcJedMere = new TableColumn<Izlaz,JedinicaMere>("jedinica mere");	
		tcJedMere.setCellValueFactory(new PropertyValueFactory<Izlaz, JedinicaMere>("jedinicaMere"));
		
		TableColumn <Izlaz, Double> tcCenaPoKomadu = new TableColumn<Izlaz,Double>("cena po jedinici mere");		
		tcCenaPoKomadu.setCellValueFactory(new PropertyValueFactory<Izlaz, Double>("cenaPoKomadu"));
		
		tabela.getColumns().addAll(tcSifra,tcNaziv,tcOpis,tcJedMere,tcCenaPoKomadu);
		
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
	
	private void filtriranje() {
		HBox pretragaHB = new HBox(10);
		pretragaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setFont(new Font(20));
		pretragaHB.getChildren().add(pl);
		pretragaHB.getChildren().add(new Label("naziv/opis:"));
		tfPretraga = new TextField();
		tfPretraga.setPrefWidth(150);
		pretragaHB.getChildren().add(tfPretraga);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu = new Button("poništi");
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setOnAction(new PonistiPretragu_izlaz_K());
		BPonistiPretragu.setDisable(true);
		pretragaHB.getChildren().add(BPonistiPretragu);
		
		tfPretraga.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {				
				BPonistiPretragu.setDisable(false);
				RBFiltriraniSpisak.setSelected(true);
				tabela.getItems().clear();
				tabela.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getTrenutnaGodina().filtrirajIzlaze(tfPretraga.getText())));
				if(tfPretraga.getText().equals("")) {
					BPonistiPretragu.setDisable(true);
					RBSviSpisak.setSelected(true);
				}
			}
		});
		
		getChildren().add(pretragaHB);
	}
	
	private void stampaSpisak() {
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
		RBSviSpisak = new RadioButton("sve");
		RBFiltriraniSpisak = new RadioButton("filtrirane");
		RBSviSpisak.setSelected(true);
		TGSpisak = new ToggleGroup();
		RBSviSpisak.setToggleGroup(TGSpisak);
		RBFiltriraniSpisak.setToggleGroup(TGSpisak);
		spisak.getChildren().addAll(RBSviSpisak,RBFiltriraniSpisak);
		BSrampajSpisak = new Button("štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BSrampajSpisak.setGraphic(ss);
		spisak.getChildren().add(BSrampajSpisak);
		BSrampajSpisak.setOnAction(new StampaSpiska_Izlaz_K());
		getChildren().add(spisak);		
	}
	
	public void updateTabele () {   				//dodaje djuture u tabelu kao observabl listu
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableList(Firma.getInstance().getTrenutnaGodina().getIzlazi()));
		tabela.scrollTo(tabela.getItems().size()-1);
	}
	
	public void ocistiPoljaZaUnos() {   //cisti sva polja za unos	
		tfSifra.clear();
		tfNaziv.clear();
		tfOpis.clear();
		tfCenaPoKom.clear();
		cbJedMere.getSelectionModel().clearSelection();

	}
	
	public static BazaIzlazaTab getInstance() {
		if(instance == null) {
			instance = new BazaIzlazaTab();
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

	public TextField getTfNaziv() {
		return tfNaziv;
	}

	public void setTfNaziv(TextField tfNaziv) {
		this.tfNaziv = tfNaziv;
	}

	public TextField getTfOpis() {
		return tfOpis;
	}

	public void setTfOpis(TextField tfOpis) {
		this.tfOpis = tfOpis;
	}

	public TextField getTfCenaPoKom() {
		return tfCenaPoKom;
	}

	public void setTfCenaPoKom(TextField tfCenaPoKom) {
		this.tfCenaPoKom = tfCenaPoKom;
	}

	public ComboBox<JedinicaMere> getCbJedMere() {
		return cbJedMere;
	}

	public void setCbJedMere(ComboBox<JedinicaMere> cbJedMere) {
		this.cbJedMere = cbJedMere;
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

	public TableView<Izlaz> getTabela() {
		return tabela;
	}

	public void setTabela(TableView<Izlaz> tabela) {
		this.tabela = tabela;
	}

	public static void setInstance(BazaIzlazaTab instance) {
		BazaIzlazaTab.instance = instance;
	}

	public TextField getTfPretraga() {
		return tfPretraga;
	}

	public void setTfPretraga(TextField tfPretraga) {
		this.tfPretraga = tfPretraga;
	}

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
	}

	public void setBPonistiPretragu(Button bPonistiPretragu) {
		BPonistiPretragu = bPonistiPretragu;
	}

	public Button getBSrampajSpisak() {
		return BSrampajSpisak;
	}

	public void setBSrampajSpisak(Button bSrampajSpisak) {
		BSrampajSpisak = bSrampajSpisak;
	}

	public RadioButton getRBSviSpisak() {
		return RBSviSpisak;
	}

	public void setRBSviSpisak(RadioButton rBSviSpisak) {
		RBSviSpisak = rBSviSpisak;
	}

	public RadioButton getRBFiltriraniSpisak() {
		return RBFiltriraniSpisak;
	}

	public void setRBFiltriraniSpisak(RadioButton rBFiltriraniSpisak) {
		RBFiltriraniSpisak = rBFiltriraniSpisak;
	}

	public Button getBStampajSpisak() {
		return BStampajSpisak;
	}

	public void setBStampajSpisak(Button bStampajSpisak) {
		BStampajSpisak = bStampajSpisak;
	}
	
	
	
	
}
