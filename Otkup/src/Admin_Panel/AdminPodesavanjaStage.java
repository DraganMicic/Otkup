package Admin_Panel;

import Admin_Panel.Godina_ASCED.*;
import Admin_Panel.Tools_K.Potvrdi_AdminSifra_K;
import Admin_Panel.Tools_K.Sacuvaj_LInkGlavneBaze_K;
import Main.PocetniStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Firma;
import model.Godina;

public class AdminPodesavanjaStage extends Stage{

	private static AdminPodesavanjaStage instance;
	
	private PasswordField sifraPF;
	private TextField glavanBazaTF;
	private Scene sc2;
	private Scene sc1;
	private Button BPotvrdi;
	private Button BSacuvaј;
	private VBox unosSifreVB;
	
	private VBox podesavanjaVB;
	private TableView<Godina> tabela;
	private TextField TFGodina;
	private TextField TFLinkGodine;
	private Button BDodaj;
	private Button BIzmeni;
	private Button BSacuvaj2;
	private Button BOdustani;
	private Button BObrisi;
	private HBox redZaUnos;
	private HBox redZaKomande;
	private boolean unosNovog=false;
	private boolean izmenaUToku = false;
	
	private AdminPodesavanjaStage() {
		
		this.setTitle("Otkup - admin panel");
		Image ikonicа = new Image(getClass().getResourceAsStream("/slike/ikonica.png"));
		getIcons().add(ikonicа);		
		
		scenaZaSifru();		
		sc1= new Scene(unosSifreVB,700,700);
		sc1.setFill(null);
		setScene(sc1);
		
		scenaZaLinkove();	
	}
	
	private void scenaZaSifru() {
		
		unosSifreVB = new VBox(10);
		unosSifreVB.setPadding(new Insets(30,30,30,30));
		unosSifreVB.setAlignment(Pos.CENTER);
		Label l1 = new Label("Šifra za pristup:");
		sifraPF = new PasswordField();
		sifraPF.setPrefWidth(160);
		HBox pfHB = new HBox(10);
		pfHB.getChildren().add(sifraPF);
		Button BOdustani = new Button("nazad");
		ImageView cancel = new ImageView(Firma.getInstance().getBackIco());
		BOdustani.setGraphic(cancel);
		BPotvrdi = new Button("potvrdi");
		ImageView potvrdi = new ImageView(Firma.getInstance().getPotvrdiIco());
		BPotvrdi.setGraphic(potvrdi);
		BPotvrdi.setDisable(true);
		HBox komandeHB = new HBox(10);
		komandeHB.getChildren().addAll(BOdustani,BPotvrdi);		
		VBox sifraVB = new VBox(10);
		sifraVB.getChildren().addAll(l1,pfHB,komandeHB);	
		HBox sifraHB = new HBox();
		sifraHB.setAlignment(Pos.CENTER);
		sifraHB.getChildren().add(sifraVB);
		unosSifreVB.getChildren().add(sifraHB);
		
		BOdustani.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				hide();
				sifraPF.clear();
				BPotvrdi.setDisable(true);
				PocetniStage.getInstance().show();
			}
		});
		sifraPF.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!sifraPF.getText().equals(""))
					BPotvrdi.setDisable(false);
				else
					BPotvrdi.setDisable(true);
				
			}
		});	
		BPotvrdi.setOnAction(new Potvrdi_AdminSifra_K());
	}
	
	private void scenaZaLinkove() {
		podesavanjaVB = new VBox(10);
		podesavanjaVB.setPadding(new Insets(30,30,30,30));
		
		Label l2 = new Label("Glavana baza:");
		l2.setFont(new Font(20));
		podesavanjaVB.getChildren().add(l2);
			
		trakaZaLinkGlavne();
		
		Label l3 = new Label("Godine:");
		l3.setFont(new Font(20));
		podesavanjaVB.getChildren().add(l3);
		
		trakaZaUnos();
		trakaKomandi();
		tabela();

		podesavanjaVB.getChildren().addAll(redZaUnos,redZaKomande,tabela);
		
		Button BOdustani2 = new Button("nazad");
		ImageView cancel2 = new ImageView(Firma.getInstance().getBackIco());
		BOdustani2.setGraphic(cancel2);
		BOdustani2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				hide();
				PocetniStage.getInstance().getListaGodina().setItems(null);
				PocetniStage.getInstance().getListaGodina().setItems(FXCollections.observableList(Firma.getInstance().getGodine()));
				PostaviScenu1();				
				PocetniStage.getInstance().show();				
			}
		});			
		podesavanjaVB.getChildren().add(BOdustani2);
		
		sc2= new Scene(podesavanjaVB,700,700);
		sc2.setFill(null);
	}
	
	private void trakaZaLinkGlavne() {
		
		glavanBazaTF = new TextField();
		glavanBazaTF.setText(Firma.getInstance().getLinkGlavneBaze());
		glavanBazaTF.setPrefWidth(600);
		BSacuvaј = new Button("sačuvaj");
		ImageView sacuvaj = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaј.setGraphic(sacuvaj);
		BSacuvaј.setMinWidth(80);
		BSacuvaј.setDisable(true);
		BSacuvaј.setOnAction(new Sacuvaj_LInkGlavneBaze_K());
		glavanBazaTF.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.equals(Firma.getInstance().getLinkGlavneBaze())) 
					BSacuvaј.setDisable(false);				
				else 
					BSacuvaј.setDisable(true);
			}
		});
		HBox red1 = new HBox(10);
		red1.setAlignment(Pos.BASELINE_LEFT);
		red1.getChildren().addAll(glavanBazaTF,BSacuvaј); 
		podesavanjaVB.getChildren().add(red1);
	}
	
	private void trakaZaUnos() {
		redZaUnos = new HBox(10);
		Label l6 = new Label("godina:");
		TFGodina = new TextField();
		TFGodina.setPrefWidth(60);
		Label L7 = new Label("link baze:");
		TFLinkGodine = new TextField();
		TFLinkGodine.setPrefWidth(440);
		redZaUnos.getChildren().addAll(l6,TFGodina,L7,TFLinkGodine);	
		redZaUnos.setAlignment(Pos.CENTER_LEFT);
		redZaUnos.setDisable(true);
	}
	
	private void trakaKomandi() {
		redZaKomande = new HBox(10);
		
		BDodaj = new Button("dodaj");
		ImageView add = new ImageView(Firma.getInstance().getAddIco());
		BDodaj.setGraphic(add); 
		BDodaj.requestFocus();
		BDodaj.setOnAction(new Dodaj_Godina_K());
		
		BSacuvaj2 = new Button("sačuvaj");
		ImageView save = new ImageView(Firma.getInstance().getSaveIco());
		BSacuvaj2.setGraphic(save);
		BSacuvaj2.setOnAction(new Sacuvaj_Godina_K());
		BSacuvaj2.setDisable(true);
		
		BOdustani = new Button("odustani");
		ImageView cancel = new ImageView(Firma.getInstance().getCloseIco());
		BOdustani.setGraphic(cancel);
		BOdustani.setOnAction(new Odustani_Godina_K());
		BOdustani.setDisable(true);
			
		BIzmeni = new Button("izmeni");
		BIzmeni.setDisable(true);
		ImageView edit = new ImageView(Firma.getInstance().getEditIco());
		BIzmeni.setGraphic(edit);
		BIzmeni.setOnAction(new Izmeni_Godina_K());
		
		BObrisi = new Button("obriši");
		BObrisi.setDisable(true);
		ImageView delete = new ImageView(Firma.getInstance().getDeleteIco());
		BObrisi.setGraphic(delete);
		BObrisi.setOnAction(new Obrisi_Godina_K());
		
		redZaKomande.getChildren().addAll(BDodaj,BSacuvaj2,BOdustani,BIzmeni,BObrisi);
				
	}
	
	@SuppressWarnings("unchecked")
	private void tabela() {
		tabela = new TableView<Godina>();
		TableColumn <Godina, Integer> tcGodina = new TableColumn<Godina,Integer>("godina");		
		tcGodina.setCellValueFactory(new PropertyValueFactory<Godina, Integer>("godina"));		
		
		TableColumn <Godina, String> tcLinkBaze = new TableColumn<Godina,String>("link baze");		
		tcLinkBaze.setCellValueFactory(new PropertyValueFactory<Godina, String>("linkBaze"));
		tabela.getColumns().addAll(tcGodina,tcLinkBaze);
		updatetabele();
		tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {  //kad se selektuje nesto u tabeli da se updatuju dugmici
		    if (newSelection != null && unosNovog == false && izmenaUToku == false) {
		       BObrisi.setDisable(false);
		       BIzmeni.setDisable(false); 
		       BOdustani.setDisable(false);
		       BDodaj.setDisable(true);		
		    }
		});
	}
	
	public static AdminPodesavanjaStage getInstance() {
		if(instance == null) {
			instance = new AdminPodesavanjaStage();
		}
		return instance;
	}
	
	public void updatetabele() {
		tabela.getItems().clear();
		tabela.getItems().addAll(FXCollections.observableArrayList(Firma.getInstance().getGodine()));
	}
	
	public void ocistiPoljaZaUnos() {
		TFGodina.clear();
		TFLinkGodine.clear();
	}
	
	public PasswordField getSifraPF() {
		return sifraPF;
	}
	
	public void PostaviScenu2() {
		setScene(sc2);
	}
	
	public void PostaviScenu1() {
		setScene(sc1);
	}
	
	public Button getBPotvrdi() {
		return BPotvrdi;
	}
	
	public TextField getGlavanBazaTF() {
		return glavanBazaTF;
	}
	
	public Button getBSacuvaј() {
		return BSacuvaј;
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
	
	public HBox getRedZaUnos() {
		return redZaUnos;
	}

	public Button getBDodaj() {
		return BDodaj;
	}

	public void setBDodaj(Button bDodaj) {
		BDodaj = bDodaj;
	}

	public Button getBIzmeni() {
		return BIzmeni;
	}

	public void setBIzmeni(Button bIzmeni) {
		BIzmeni = bIzmeni;
	}

	public Button getBSacuvaj2() {
		return BSacuvaj2;
	}

	public void setBSacuvaj2(Button bSacuvaj2) {
		BSacuvaj2 = bSacuvaj2;
	}

	public Button getBOdustani() {
		return BOdustani;
	}

	public void setBOdustani(Button bOdustani) {
		BOdustani = bOdustani;
	}

	public Button getBObrisi() {
		return BObrisi;
	}

	public void setBObrisi(Button bObrisi) {
		BObrisi = bObrisi;
	}

	public void setBPotvrdi(Button bPotvrdi) {
		BPotvrdi = bPotvrdi;
	}

	public void setBSacuvaј(Button bSacuvaј) {
		BSacuvaј = bSacuvaј;
	}
	
	public TableView<Godina> getTabela() {
		return tabela;
	}
	
	public HBox getRedZaKomande() {
		return redZaKomande;
	}
	
	public TextField getTFGodina() {
		return TFGodina;
	}

	public Scene getSc2() {
		return sc2;
	}

	public void setSc2(Scene sc2) {
		this.sc2 = sc2;
	}

	public Scene getSc1() {
		return sc1;
	}

	public void setSc1(Scene sc1) {
		this.sc1 = sc1;
	}

	public VBox getUnosSifreVB() {
		return unosSifreVB;
	}

	public void setUnosSifreVB(VBox unosSifreVB) {
		this.unosSifreVB = unosSifreVB;
	}

	public VBox getPodesavanjaVB() {
		return podesavanjaVB;
	}

	public void setPodesavanjaVB(VBox podesavanjaVB) {
		this.podesavanjaVB = podesavanjaVB;
	}

	public TextField getTFLinkGodine() {
		return TFLinkGodine;
	}

	public void setTFLinkGodine(TextField tFLinkGodine) {
		TFLinkGodine = tFLinkGodine;
	}

	public static void setInstance(AdminPodesavanjaStage instance) {
		AdminPodesavanjaStage.instance = instance;
	}

	public void setSifraPF(PasswordField sifraPF) {
		this.sifraPF = sifraPF;
	}

	public void setGlavanBazaTF(TextField glavanBazaTF) {
		this.glavanBazaTF = glavanBazaTF;
	}

	public void setTabela(TableView<Godina> tabela) {
		this.tabela = tabela;
	}

	public void setTFGodina(TextField tFGodina) {
		TFGodina = tFGodina;
	}

	public void setRedZaUnos(HBox redZaUnos) {
		this.redZaUnos = redZaUnos;
	}

	public void setRedZaKomande(HBox redZaKomande) {
		this.redZaKomande = redZaKomande;
	}
	
	
	
}
