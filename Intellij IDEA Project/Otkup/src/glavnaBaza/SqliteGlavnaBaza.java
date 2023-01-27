package glavnaBaza;

import java.sql.Connection;
import java.sql.SQLException;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SqliteGlavnaBaza {
	private static SqliteGlavnaBaza instance;
	Connection konekcija;
	
	public static SqliteGlavnaBaza getInstance() {
		if (instance == null) {
			instance = new SqliteGlavnaBaza();
		}
		return instance;	
	}
	
	private SqliteGlavnaBaza() {
		konekcija = SquliteConnector2.konektuj();
		
		if(konekcija == null) {
			Alert a = new Alert(AlertType.ERROR, "Neuspešna konekcija sa glavnom bazom!");
			a.showAndWait();
		}else {
			//System.out.println("laganaKonekcija");
		}
	}
	
	public boolean isKonektovano() {
		try {
			return !konekcija.isClosed();		
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Nema konekcija se glavnom bazom!");
			a.showAndWait();
			//e.printStackTrace();
			return false;
			}	
	}
	
	public Connection getKonekcija() {
		konekcija = SquliteConnector2.konektuj();
		
		if(konekcija == null) {
			Alert a = new Alert(AlertType.ERROR, "Neuspešna konekcija sa glavnom bazom!");
			a.showAndWait();
		}else {
			//System.out.println("laganaKonekcija");
		}
		return konekcija;
	}
}
