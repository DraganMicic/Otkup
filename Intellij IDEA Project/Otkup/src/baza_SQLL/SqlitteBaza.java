package baza_SQLL;

import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SqlitteBaza {
	
	private static SqlitteBaza instance;
	Connection konekcija;
	
	public static SqlitteBaza getInstance() {
		if (instance == null) {
			instance = new SqlitteBaza();
		}
		return instance;	
	}
	
	private SqlitteBaza() {
		konekcija = SqliteConnector.konektuj();
		
		if(konekcija == null) {
			Alert a = new Alert(AlertType.ERROR, "Neuspešna konekcija sa bazom!");
			a.showAndWait();
		}else {
			//System.out.println("laganaKonekcija");
		}
	}
	
	public boolean isKonektovano() {
		try {
			return !konekcija.isClosed();		
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Neuspešna konekcija sa bazom!");
			a.showAndWait();
			//e.printStackTrace();
			return false;
			}	
	}
	
	public Connection getKonekcija() {
		return konekcija;
	}
	
	public static void setInstance(SqlitteBaza instance) {
		SqlitteBaza.instance = instance;
	}
	
}
