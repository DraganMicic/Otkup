package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;

public class SqliteGajba {
	
	private static SqliteGajba instance;
	
	public static SqliteGajba getInstance() {
		if(instance == null) {
			instance = new SqliteGajba();
		}
		return instance;
	}
	
	public void ucitajGajbe() throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  gajbe";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Gajba g = new Gajba(rs.getInt("sifra"), rs.getString("naziv"), rs.getDouble("tezina"), rs.getInt("ukupno"));
				Firma.getInstance().getTrenutnaGodina().getGajbe().add(g);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju gajbi!");
			a.show();
			return;
			
		}finally{
			ps.close();
			rs.close();
		}		
	}
	
	public void upisiGajbu(Gajba g) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO gajbe VALUES(?,?,?,?)";
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, g.getSifra());
			ps.setString(2, g.getNaziv());
			ps.setDouble(3, g.getTezina());
			ps.setInt(4, g.getUkupnoNaRaspolaganju());
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu gajbe!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	public void obrisGajbu(Gajba g) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM gajbe WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, g.getSifra());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju gajbe!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}
	 

}
