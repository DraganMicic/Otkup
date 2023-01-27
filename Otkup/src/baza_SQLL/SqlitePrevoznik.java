package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoznik;

public class SqlitePrevoznik {
	
	private static SqlitePrevoznik instance;
	
	public static SqlitePrevoznik getInstance() {  //get instance 
		if (instance == null) {
			instance = new SqlitePrevoznik();
		}
		return instance;
	}
	
	public void ucitajPrevoznike () throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  prevoznici";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int sifra =  rs.getInt("sifra");
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String opis = rs.getString("opis");
				double cenaPoKg = rs.getDouble("cenaPoKg");
				
				Prevoznik p = new Prevoznik(sifra, ime, prezime, opis, cenaPoKg);
				Firma.getInstance().getTrenutnaGodina().getPrevoznici().add(p);
			}
			
		} catch (Exception e) {
			return;			
		}finally{
			ps.close();
			rs.close();
		}
	}
	
	public void upisiPrevoznika(Prevoznik pr) throws SQLException{
		PreparedStatement ps = null;
		String query = "INSERT INTO prevoznici VALUES(?,?,?,?,?)";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, pr.getSifra());
			ps.setString(2, pr.getIme());
			ps.setString(3, pr.getPrezime());
			ps.setString(4, pr.getOpis());
			ps.setDouble(5, pr.getCenaPoKg());
			ps.executeUpdate();
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu prevoznika!");
			a.show();
			return;	
		}finally {
			ps.close();
		}
			
	}
	
	public void obrisiPrevoznika(Prevoznik pr) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM prevoznici WHERE sifra = ?";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, pr.getSifra());
				ps.executeUpdate();			
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju prevoznika!");
				a.show();
				return;	
			}finally {
				ps.close();
			}		
	}
	
	public void obrisiSvePrevoznike() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM prevoznici ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih prevoznika!");
				a.show();
				return;	
			}finally {
				ps.close();
			}		
	}

}
