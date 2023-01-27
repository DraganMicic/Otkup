package glavnaBaza;

import java.sql.Connection;
import java.sql.DriverManager;
import model.Firma;

public class SquliteConnector2 {
	
	public static Connection konektuj() {
		
	    String url = Firma.getInstance().getLinkGlavneBaze();
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
