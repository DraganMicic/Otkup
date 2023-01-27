package Main;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;
import glavnaBaza.SquliteGodina;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Firma;

public class Main extends Application {

	public void start(Stage primaryStage) throws InvocationTargetException {
		File myObj = new File("linkBaze.txt");
	    try {
			myObj.createNewFile();
		} catch (IOException e) {
			Alert a = new Alert(AlertType.ERROR, "Nema putanje do glavne baze!");
			a.show();
		}		      
		
		try {
			Scanner myReader = new Scanner(myObj);
			String data = myReader.nextLine();
		    Firma.getInstance().setLinkGlavneBaze(data);
		    myReader.close();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Nema putanje do glavne baze!");
			a.showAndWait();
		}
		
		try {
			SquliteGodina.GetInstance().ucitajGodine();
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Neuspešno učitavanje godina!");
			a.show();
		}			

		PocetniStage.getInstance().show();

	}
			
	public static void main(String[] args) {	
		
		launch(args);
	}
}
