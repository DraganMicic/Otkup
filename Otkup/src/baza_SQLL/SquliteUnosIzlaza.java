package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Izlaz;
import model.Proizvodjac;
import model.UnosIzlaza;

public class SquliteUnosIzlaza {
	
	private static SquliteUnosIzlaza instance;
	
	public static SquliteUnosIzlaza getInstance() {
		if(instance == null) {
			instance = new SquliteUnosIzlaza();
		}
		return instance;
	}
	
	public void ucitajUnoseIzlaza() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  unosiIzlaza";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {				
				int sifra = rs.getInt("sifra");
				String datum = rs.getString("datum");
				LocalDate ld = LocalDate.parse(datum);				
				Proizvodjac p = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(rs.getInt("sifraProizvodjaca"));
				Izlaz i = Firma.getInstance().getTrenutnaGodina().izlazSaSifrom(rs.getInt("sifraIzlaza"));			
				String brOtpremnice =rs.getString("brOtpremnice");
				double kolicina = rs.getDouble("kolicina");					
				UnosIzlaza ui = new UnosIzlaza(sifra, ld, p, i,brOtpremnice, kolicina);
				Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza().add(ui);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju unosa izlaza!");
			a.show();
			return;			
		}finally{
			ps.close();
			rs.close();
		}
		
	}
	
	public void upisiUnosIzlaza(UnosIzlaza ui) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO unosiIzlaza VALUES(?,?,?,?,?,?)";
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);			
			ps.setInt(1, ui.getSifra());
			ps.setString(2, ui.getDatum().toString());
			ps.setInt(3, ui.getProizvodjac().getSifra());				
			ps.setInt(4, ui.getIzlaz().getSifra());
			ps.setString(5, ui.getBrojOtpremnice());
			ps.setDouble(6, ui.getKolicina());	
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa izlaza!");
			a.show();
			return;
		}finally {
			ps.close();
		}
	}
	
	public void obrisUnosIzlaza(UnosIzlaza ui) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM unosiIzlaza WHERE sifra = ? ";		
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, ui.getSifra());
				ps.executeUpdate();			
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju unosa izlaza!");
				a.show();
				return;
			}finally {
				ps.close();
			}		
	}
	

	public void obrisSveUnoseIzlaza() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM unosiIzlaza ";
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih unosa Izlaza!");
				a.show();
				return;			
			}finally {
				ps.close();
			}		
	}
	
}
