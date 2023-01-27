package Main;

import BazaIzlaza_Tab.BazaIzlazaTab;
import BazaUlaza_Tab.BazaUlazaTab;
import Gajbe_Tab.GajbeTab;
import Podesavanja_Tab.PodesavanjaTab;
import Prevoz_Tab.PrevozTab;
import Proizvodjac_Tab.ProizvodjaciTab;
import StatistikaTab.StatistikaTab;
import UnosIzlaza_Tab.UnosIzlazaTab;
import UnosUlaza_Tab.UnosUlazaTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Firma;

public class MainStage extends Stage {
	
	private static MainStage instance;	
	private TabPane tabPane;
	
	private MainStage() {
			
		this.setTitle("Otkup" + Firma.getInstance().getTrenutnaGodina().toString());
		Image ikonicа = new Image(getClass().getResourceAsStream("/slike/ikonica.png"));
		this.setMaximized(true);
		getIcons().add(ikonicа);	
			
		tabPane = new TabPane();   //napravim tab pane globalni i podesavam ga malo
		tabPane.setSide(Side.TOP);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.setTabMinWidth(100);
		tabPane.setTabMinHeight(20);
		tabPane.setTabMaxWidth(150);
		tabPane.setTabMaxHeight(220);
		
				
		Label l1 = new Label("PROIZVOĐAČI");   //proizvodjaci tab
		VBox v1 = new VBox();
		v1.setPadding(new Insets(5,5,5,5));
		Image proizvodjaci = new Image("/slike/proizvodjaci.png", 30, 30 , false, false);
		ImageView iv1 = new ImageView();
		iv1.setImage(proizvodjaci);		
		v1.getChildren().add(iv1);
		v1.getChildren().add(l1);
		v1.setAlignment(Pos.BASELINE_CENTER);
		Tab proizvodjaciTab = new Tab();
		proizvodjaciTab.setGraphic(v1);
		proizvodjaciTab.setContent(ProizvodjaciTab.getInstance());
		
		Label l2 = new Label("BAZA ULAZA");   //ulazi tab	
		VBox v2 = new VBox();
		v2.setPadding(new Insets(5,5,5,5));
		Image baazauUlaza = new Image("/slike/bazaulaza.png", 30, 30 , false, false);
		ImageView iv2 = new ImageView();
		iv2.setImage(baazauUlaza);		
		v2.getChildren().add(iv2);
		v2.getChildren().add(l2);
		v2.setAlignment(Pos.BASELINE_CENTER);
		Tab ulaziTab = new Tab();
		ulaziTab.setGraphic(v2);
		ulaziTab.setContent(BazaUlazaTab.getInstance());
		
		Label l3 = new Label("BAZA IZLAZA");   //izlazi tab	
		VBox v3 = new VBox();
		v3.setPadding(new Insets(5,5,5,5));
		Image bazaIzlaza = new Image("/slike/bazaizlaza.png", 30, 30 , false, false);
		ImageView iv3 = new ImageView();
		iv3.setImage(bazaIzlaza);		
		v3.getChildren().add(iv3);
		v3.getChildren().add(l3);
		v3.setAlignment(Pos.BASELINE_CENTER);
		Tab izlaziTab = new Tab();
		izlaziTab.setGraphic(v3);
		izlaziTab.setContent(BazaIzlazaTab.getInstance());
		
		Label l4 = new Label("UNOS ULAZA");   //Unos ulaza tab	
		VBox v4 = new VBox();
		v4.setPadding(new Insets(5,5,5,5));
		Image unosUlaza = new Image("/slike/unosulaza.png", 30, 30 , false, false);
		ImageView iv4 = new ImageView();
		iv4.setImage(unosUlaza);		
		v4.getChildren().add(iv4);
		v4.getChildren().add(l4);
		v4.setAlignment(Pos.BASELINE_CENTER);
		Tab unosUlazaTab = new Tab();
		unosUlazaTab.setGraphic(v4);
		unosUlazaTab.setContent(UnosUlazaTab.getInstance());
		
		Label l5 = new Label("UNOS IZLAZA");   //Unos izlaza tab
		VBox v5 = new VBox();
		v5.setPadding(new Insets(5,5,5,5));
		Image unosIzlaza = new Image("/slike/unosizlaza.png", 30, 30 , false, false);
		ImageView iv5 = new ImageView();
		iv5.setImage(unosIzlaza);		
		v5.getChildren().add(iv5);
		v5.getChildren().add(l5);
		v5.setAlignment(Pos.BASELINE_CENTER);	
		Tab unosIzlazaTab = new Tab();
		unosIzlazaTab.setGraphic(v5);
		unosIzlazaTab.setContent(UnosIzlazaTab.getInstance());
		
		Label l6 = new Label("PREVOZ");
		VBox v6 = new VBox();
		v6.setPadding(new Insets(5,5,5,5));
		Image prevoz = new Image("/slike/prevoz.png", 30, 30 , false, false);
		ImageView iv6 = new ImageView();
		iv6.setImage(prevoz);		
		v6.getChildren().add(iv6);
		v6.getChildren().add(l6);
		v6.setAlignment(Pos.BASELINE_CENTER);	
		Tab PrevoziTab = new Tab();
		PrevoziTab.setGraphic(v6);
		PrevoziTab.setContent(PrevozTab.getInstance());
		
		Label l7 = new Label("GAJBE");
		VBox v7 = new VBox();
		v7.setPadding(new Insets(5,5,5,5));
		Image gajba = new Image("/slike/gajba.png", 30, 30 , false, false);
		ImageView iv7 = new ImageView();
		iv7.setImage(gajba);		
		v7.getChildren().add(iv7);
		v7.getChildren().add(l7);
		v7.setAlignment(Pos.BASELINE_CENTER);	
		Tab gajbeTab = new Tab();
		gajbeTab.setGraphic(v7);
		gajbeTab.setContent(GajbeTab.getInstance());
		
		Label l8 = new Label("PODEŠAVANjA");
		VBox v8 = new VBox();
		v8.setPadding(new Insets(5,5,5,5));
		Image podesavanja = new Image("/slike/podesavanja.png", 30, 30 , false, false);
		ImageView iv8 = new ImageView();
		iv8.setImage(podesavanja);		
		v8.getChildren().add(iv8);
		v8.getChildren().add(l8);
		v8.setAlignment(Pos.BASELINE_CENTER);	
		Tab podesavanjaTab = new Tab();
		podesavanjaTab.setGraphic(v8);
		podesavanjaTab.setContent(PodesavanjaTab.getInstance());

		Label l9 = new Label("STATISTIKA");
		VBox v9 = new VBox();
		v9.setPadding(new Insets(5,5,5,5));
		Image statistika = new Image("/slike/statistic.png" + "", 30, 30 , false, false);
		ImageView iv9 = new ImageView();
		iv9.setImage(statistika);
		v9.getChildren().add(iv9);
		v9.getChildren().add(l9);
		v9.setAlignment(Pos.BASELINE_CENTER);
		Tab statistikaTab = new Tab();
		statistikaTab.setGraphic(v9);
		statistikaTab.setContent(StatistikaTab.getInstance());
				
		tabPane.getTabs().addAll(proizvodjaciTab, ulaziTab, izlaziTab, unosUlazaTab,unosIzlazaTab,PrevoziTab,gajbeTab,statistikaTab,podesavanjaTab);
		
		Scene sc = new Scene(tabPane,1500,810);
		sc.setFill(null);
		//setFullScreen(true);
		setScene(sc);	
		
	}
	
	public static MainStage getInstance() {
		if (instance == null) {
			instance = new MainStage();
		}
		return instance;
	}
	
	public void podesiBoju(String bojaDole, String bojaGore) {
		tabPane.setStyle("-fx-background-color: linear-gradient(" + bojaGore + ", " + bojaDole +")");
	}
}