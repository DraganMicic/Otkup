package Main;

import Admin_Panel.AdminPodesavanjaStage;
import baza_SQLL.SqlitteBaza;
import glavnaBaza.SqlitePodesavanja;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Firma;
import model.Godina;

public class PocetniStage extends Stage{
	
	private static PocetniStage instance;
	
	private ListView<Godina> listaGodina;
	private VBox pocetniVB;
	
	private PocetniStage() {
		
		this.setTitle("Otkup");
		Image ikonicа = new Image(getClass().getResourceAsStream("/slike/ikonica.png"));
		getIcons().add(ikonicа);		
	
		//////////pocetna scena//////////
		
		pocetniVB = new VBox(10);
		pocetniVB.setPadding(new Insets(30,30,30,30));
		pocetniVB.setStyle("-fx-font: 20px \"Serif\";");
		
		VBox dobrodosliVB = new VBox();
		Label l1 = new Label("Dobrodošli u Otkup!");
		l1.setFont(new Font(25));
		Label l2 = new Label("v6.1 by D.Mićić");
		dobrodosliVB.getChildren().addAll(l1,l2);
		Label l3 = new Label("Izaberite godinu:");
		l3.setFont(new Font(25));
		listaGodina = new ListView<>();
		listaGodina.setItems(FXCollections.observableList(Firma.getInstance().getGodine()));
		listaGodina.scrollTo(FXCollections.observableList(Firma.getInstance().getGodine()).size()-1);
		listaGodina.setPrefHeight(200);
		
		listaGodina.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Firma.getInstance().setTrenutnaGodina(newValue);
			try {
				Firma.getInstance().ucitajBazu();
				SqlitePodesavanja.GetInstance().ucitajPodesavanja();
				hide();
				MainStage.getInstance().show();
			} catch (Exception e) {
				Alert a = new Alert(Alert.AlertType.ERROR, "Greska pri ucitavanju podesavanja SQL!" + "\n E: " + e);
				a.showAndWait();
				return;
			}
			hide();
			MainStage.getInstance().show();
		});
		
		Button podesavanjaButton = new Button();
		ImageView settings = new ImageView(Firma.getInstance().getSettingsIco());
		podesavanjaButton.setGraphic(settings);
		podesavanjaButton.setOnAction(event -> {
			hide();
			AdminPodesavanjaStage.getInstance().show();
		});
		
		HBox settingsHB = new HBox();
		settingsHB.setAlignment(Pos.BASELINE_RIGHT);
		settingsHB.getChildren().add(podesavanjaButton);
		
		pocetniVB.getChildren().addAll(dobrodosliVB,l3,listaGodina,settingsHB);
		
		Scene sc = new Scene(pocetniVB,400,400);
		sc.setFill(null);
		setScene(sc);	
	}
	
	public static PocetniStage getInstance() {
		if (instance == null) {
			instance = new PocetniStage();
		}
		return instance;
	}
	
	public void updateListeGodina() {
		listaGodina.getItems().clear();
		listaGodina.setItems(FXCollections.observableList(Firma.getInstance().getGodine()));
	}
	
	public ListView<Godina> getListaGodina() {
		return listaGodina;
	}
	

}
