package BazaIzlaza_Tab;

import BazaIzlaza_Tab.Izlaz_ASCED.*;
import BazaIzlaza_Tab.Tools_K.PonistiPretragu_izlaz_K;
import BazaIzlaza_Tab.Tools_K.StampaSpiska_Izlaz_K;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import model.Firma;
import model.Izlaz;
import model.JedinicaMere;

public class BazaIzlazaTab extends VBox {
	
	private static BazaIzlazaTab instance;
	
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;

	private HBox unosITabelaHb;
	
	private GridPane unosGp;					 //inicijalizujem komponente
	private Label lsifra;
	private TextField tfSifra;
	private Label lnaziv;
	private TextField tfNaziv;
	private Label lopis;
	private TextField tfOpis;
	private Label lcenapokom;
	private TextField tfCenaPoKom;
	private Label ljedmere;
	private ComboBox<JedinicaMere> cbJedMere;
	private Button BDodaj;
	private Button BSacuvaj;
	private Button BPonisti;
	private Button BIzmeni;
	private Button BObrisi;

	private TableView<Izlaz> tabela;

	private HBox pretragaHB;
	private TextField tfPretraga;
	private Button BPonistiPretragu;

	private HBox spisak;
	private Button BSrampajSpisak;
	private RadioButton RBSviSpisak;
	private RadioButton RBFiltriraniSpisak;
	private ToggleGroup TGSpisak;
	private Button BStampajSpisak;
	
	
		
	private BazaIzlazaTab() {

		setPadding(new Insets(10,20,10,20));  //podesavam ceo tab
		setSpacing(15);
		this.setMinHeight(Screen.getPrimary().getBounds().getHeight()-80);

		//naslov 1
		Label naslov = new Label("Baza izlaza (akontacija):");
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
		filtriranje();
		stampaSpisak();
		this.getChildren().addAll(pretragaHB,spisak);

		//pocetni update

		getBDodaj().requestFocus();
	}

	private void unos(){
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

		tfNaziv = new TextField();
		tfNaziv.setPrefWidth(410);

		tfOpis = new TextField();
		tfOpis.setPrefWidth(410);

		cbJedMere = new ComboBox<JedinicaMere>();
		cbJedMere.getItems().addAll(Firma.getInstance().getJediniceMere());
		cbJedMere.setPrefWidth(200);
		cbJedMere.valueProperty().addListener(new ChangeListener<JedinicaMere>() {
			@Override
			public void changed(ObservableValue<? extends JedinicaMere> observable, JedinicaMere oldValue, JedinicaMere newValue) {
				if(newValue != null && newValue.toString().equals("din")) {
					tfCenaPoKom.setDisable(true);
					lcenapokom.setDisable(true);
					tfCenaPoKom.setPromptText("");
				}else {
					tfCenaPoKom.setDisable(false);
					lcenapokom.setDisable(false);
					tfCenaPoKom.setPromptText("[din]");
				}

			}
		});

		tfCenaPoKom = new TextField();
		tfCenaPoKom.setPrefWidth(200);
		tfCenaPoKom.setPromptText("[din]");

		lsifra = new Label("Šifra:");
		unosGp.add(lsifra,0,2,1,1);
		unosGp.add(tfSifra,1,2,1,1);

		lnaziv = new Label("Naziv:");
		unosGp.add(lnaziv,0,3,1,1);
		unosGp.add(tfNaziv,1,3,2,1);

		lopis = new Label("Opis:");
		unosGp.add(lopis,0,4,1,1);
		unosGp.add(tfOpis,1,4,2,1);

		ljedmere = new Label("Jed. mere:");
		unosGp.add(ljedmere,0,5,1,1);
		unosGp.add(cbJedMere,1,5,1,1);

		lcenapokom = new Label("Cena po jedinici mere:");
		unosGp.add(lcenapokom,0,6,2,1);
		unosGp.add(tfCenaPoKom,2,6,1,1);

		SetUnosDisable();

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

		pretragaHB = new HBox(10);
		pretragaHB.setStyle("-fx-font: 17px \"Serif\";");
		pretragaHB.setAlignment(Pos.BASELINE_LEFT);
		ImageView search = new ImageView(Firma.getInstance().getSearchIco());
		pretragaHB.getChildren().add(search);
		Label pl = new Label("Pretraga/filtriranje:  ");
		pl.setStyle("-fx-font: 25px \"System\";");
		pretragaHB.getChildren().add(pl);
		pretragaHB.getChildren().add(new Label("naziv/opis:"));
		tfPretraga = new TextField();
		tfPretraga.setPrefWidth(150);
		pretragaHB.getChildren().add(tfPretraga);
		ImageView close = new ImageView(Firma.getInstance().getCloseIco());
		BPonistiPretragu = new Button("Poništi");
		BPonistiPretragu.setGraphic(close);
		BPonistiPretragu.setOnAction(new PonistiPretragu_izlaz_K());
		BPonistiPretragu.setDisable(true);
		BPonistiPretragu.setPrefWidth(110);
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

	}
	
	private void stampaSpisak() {

		spisak = new HBox(10);                       ///stampa spiska//////////
		spisak.setStyle("-fx-font: 17px \"Serif\";");
		spisak.setAlignment(Pos.BASELINE_LEFT);
		ImageView lista = new ImageView(Firma.getInstance().getLisaIco());
		spisak.getChildren().add(lista);
		Label sl = new Label("Štampa spiska ulaza:   ");
		sl.setStyle("-fx-font: 25px \"System\";");
		spisak.getChildren().add(sl);
		Label kkk = new Label("Uvrsti:  ");
		spisak.getChildren().add(kkk);
		RBSviSpisak = new RadioButton("sve");
		RBFiltriraniSpisak = new RadioButton("filtrirane");
		RBSviSpisak.setSelected(true);
		TGSpisak = new ToggleGroup();
		RBSviSpisak.setToggleGroup(TGSpisak);
		RBFiltriraniSpisak.setToggleGroup(TGSpisak);
		spisak.getChildren().addAll(RBSviSpisak,RBFiltriraniSpisak);
		BSrampajSpisak = new Button("Štampaj");
		ImageView ss = new ImageView(Firma.getInstance().getPrintIco());
		BSrampajSpisak.setGraphic(ss);
		BSrampajSpisak.setPrefWidth(110);
		spisak.getChildren().add(BSrampajSpisak);
		BSrampajSpisak.setOnAction(new StampaSpiska_Izlaz_K());

	}
	
	public void updateTabele () {//dodaje djuture u tabelu kao observabl listu

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

	public void SetUnosDisable(){

		lsifra.setDisable(true);
		tfSifra.setDisable(true);
		lnaziv.setDisable(true);
		tfNaziv.setDisable(true);
		lopis.setDisable(true);
		tfOpis.setDisable(true);
		lcenapokom.setDisable(true);
		tfCenaPoKom.setDisable(true);
		ljedmere.setDisable(true);
		cbJedMere.setDisable(true);
	}

	public void SetUnosEnable(){

		lsifra.setDisable(false);
		tfSifra.setDisable(false);
		lnaziv.setDisable(false);
		tfNaziv.setDisable(false);
		lopis.setDisable(false);
		tfOpis.setDisable(false);
		lcenapokom.setDisable(false);
		tfCenaPoKom.setDisable(false);
		ljedmere.setDisable(false);
		cbJedMere.setDisable(false);
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

	public TextField getTfSifra() {
		return tfSifra;
	}

	public TextField getTfNaziv() {
		return tfNaziv;
	}

	public TextField getTfOpis() {
		return tfOpis;
	}

	public TextField getTfCenaPoKom() {
		return tfCenaPoKom;
	}

	public ComboBox<JedinicaMere> getCbJedMere() {
		return cbJedMere;
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

	public Button getBPonistiPretragu() {
		return BPonistiPretragu;
	}

	public RadioButton getRBSviSpisak() {
		return RBSviSpisak;
	}

	public RadioButton getRBFiltriraniSpisak() {
		return RBFiltriraniSpisak;
	}

}
