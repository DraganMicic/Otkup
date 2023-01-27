package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import model.Prevoznik;
import model.Proizvodjac;
import model.UnosGajbi;

public class SqliteUnosGajbi {
	
	private static SqliteUnosGajbi instance;
	
	public static SqliteUnosGajbi getInstance() {
		if(instance == null) {
			instance = new SqliteUnosGajbi();
		}
		return instance;
	}
	
	public void ucitajUnoseGajbi() throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  unosiGajbi";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int sifra = rs.getInt("sifra");
				String d = rs.getString("datum");
				LocalDate datum = LocalDate.parse(d);
				Proizvodjac proizvodjac = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(rs.getInt("sifraProizvodjaca"));
				Prevoznik prevoznik = Firma.getInstance().getTrenutnaGodina().prevoznikSaSifromBezLicno(rs.getInt("sifraPrevoznika"));
				Gajba gajba = Firma.getInstance().getTrenutnaGodina().GajbaSaSifrom(rs.getInt("sifraGajbe"));
				int gajbeUlaz = rs.getInt("gajbeUlaz");
				int gajbeIzlaz = rs.getInt("gajbeIzlaz");				
				UnosGajbi ug = new UnosGajbi(sifra, datum, proizvodjac, prevoznik,gajba, gajbeUlaz, gajbeIzlaz);	
				Firma.getInstance().getTrenutnaGodina().getUnosiGajbi().add(ug);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju unosa gajbi!");
			a.show();
			return;
			
		}finally{
			ps.close();
			rs.close();
		}
		
	}	
	
	public void upisiUnosGajbi(UnosGajbi ug) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO unosiGajbi VALUES(?,?,?,?,?,?,?)";
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, ug.getSifra());
			ps.setString(2, ug.getDatum().toString());			
			if(ug.getProizvodjac() == null) {
				ps.setNull(3, 0);
			}else {
				ps.setInt(3, ug.getProizvodjac().getSifra());
			}			
			if(ug.getPrevoznik() == null) {
				ps.setNull(4, 0);
			}else {
				ps.setInt(4, ug.getPrevoznik().getSifra());
			}	
			ps.setInt(5, ug.getGajba().getSifra());
			ps.setInt(6, ug.getGajbeUlaz());
			ps.setInt(7, ug.getGajbeIzlaz());
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa gajbi ovde!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	public void obrisUnosGajbi(UnosGajbi ug) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM unosiGajbi WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, ug.getSifra());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju unosa gajbi!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}

}
