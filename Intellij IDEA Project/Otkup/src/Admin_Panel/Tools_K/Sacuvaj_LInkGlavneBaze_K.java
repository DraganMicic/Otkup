package Admin_Panel.Tools_K;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import Main.PocetniStage;
import glavnaBaza.SquliteGodina;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import Admin_Panel.AdminPodesavanjaStage;

public class Sacuvaj_LInkGlavneBaze_K implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		Firma.getInstance().setLinkGlavneBaze(AdminPodesavanjaStage.getInstance().getGlavanBazaTF().getText());
		
		
		File myObj = new File("linkBaze.txt");
	    try {
			myObj.createNewFile();
		} catch (IOException e) {
			Alert a = new Alert(AlertType.ERROR, "Nema putanje do glavne baze!");
			a.showAndWait();
			return;
		}		      		
		try {
			PrintWriter writer = new PrintWriter(myObj);
			writer.print("");
			writer.print(Firma.getInstance().getLinkGlavneBaze());
			writer.close();
		} catch (Exception e) {

		}			
		
		AdminPodesavanjaStage.getInstance().getBSacuvaј().setDisable(true);		
	
		Firma.getInstance().getGodine().clear();		
		try {
			SquliteGodina.GetInstance().ucitajGodine();
		} catch (SQLException e) {

		}	
		
		PocetniStage.getInstance().getListaGodina().setItems(null);
		PocetniStage.getInstance().getListaGodina().setItems(FXCollections.observableList(Firma.getInstance().getGodine()));
				
	}

}
