package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Ulaz;

public class SqliteUlaz {
	
	private static SqliteUlaz instance;
	
	public static SqliteUlaz getInstance() {  //get instance 
		if (instance == null) {
			instance = new SqliteUlaz();
		}
		return instance;
	}
	
	public void ucitajUlaze() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  bazaUlaza";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int sifra =  rs.getInt("sifra");
				String naziv = rs.getString("naziv");
				String opis = rs.getString("opis");			
				Ulaz u = new Ulaz(sifra,naziv,opis);
				Firma.getInstance().getTrenutnaGodina().getUlazi().add(u);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju ulaza!");
			a.show();
			return;		
		}finally{
			ps.close();
			rs.close();
		}
	}
	
	public void upisiUlaz(Ulaz u) throws SQLException{
		PreparedStatement ps = null;
		String query = "INSERT INTO bazaUlaza VALUES(?,?,?)";
			
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, u.getSifra());
			ps.setString(2, u.getNaziv());
			ps.setString(3, u.getOpis());
			ps.executeUpdate();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisivanju ulaza!");
			a.show();
			return;	
		}finally {
			ps.close();
		}
	}	
	
	public void obrisiUlaz(Ulaz u) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM bazaUlaza WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, u.getSifra());
				ps.executeUpdate();			
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju ulaza!");
				a.show();
				return;	
			}finally {
				ps.close();
			}	
	}
	
	public void obrisiSveUlaze() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM bazaUlaza ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih ulaza!");
				a.show();
				return;	
				
			}finally {
				ps.close();
			}	
	}

}