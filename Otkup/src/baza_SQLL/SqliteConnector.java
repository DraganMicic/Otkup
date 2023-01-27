package baza_SQLL;

import java.sql.*;
import model.Firma;


public class SqliteConnector {
	
	public static Connection konektuj() {
		
	    String url = Firma.getInstance().getTrenutnaGodina().getLinkBaze();
		Connection conn = null;
		
		try {		
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			return conn;		

			
		} catch (Exception e) {
			return null;
		}
	}
}
