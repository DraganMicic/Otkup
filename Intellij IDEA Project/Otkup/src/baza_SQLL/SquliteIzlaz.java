package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Izlaz;

public class SquliteIzlaz {
	
	private static SquliteIzlaz instance;
	
	public static SquliteIzlaz getInstance() {  //get instance 
		if (instance == null) {
			instance = new SquliteIzlaz();
		}
		return instance;
	}
	
	public void ucitajIzlaze() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  bazaIzlaza";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Izlaz i = new Izlaz(rs.getInt("sifra"), rs.getString("naziv"), rs.getString("opis"), rs.getString("jedinicaMere"), rs.getDouble("cenaPoKomadu"));
				Firma.getInstance().getTrenutnaGodina().getIzlazi().add(i);
			}
			
		} catch (Exception e) {
			System.out.println("greska u ucitavanju izlaza");
			
		}finally{
			ps.close();
			rs.close();
		}
	}
	
	public void upisiIzlaz(Izlaz i) throws SQLException{
		PreparedStatement ps = null;
		String query = "INSERT INTO bazaIzlaza VALUES(?,?,?,?,?)";
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, i.getSifra());
				ps.setString(2, i.getNaziv());
				ps.setString(3, i.getOpis());
				ps.setString(4, i.getJedinicaMere());	
				ps.setDouble(5, i.getCenaPoKomadu());
				ps.executeUpdate();
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri upisu izlaza!");
				a.show();
				return;
			}finally {
				ps.close();
			}
	}
	
	public void obrisiIzlaz(Izlaz i) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM bazaIzlaza WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, i.getSifra());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju izlaza!");
				a.show();
				return;			
			}finally {
				ps.close();
			}
		
	}
			
	public void obrisiSveIzlaze() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM bazaIzlaza ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih izlaza!");
				a.show();
				return;			
			}finally {
				ps.close();
			}
		
	}

}
