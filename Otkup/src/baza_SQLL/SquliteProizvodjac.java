 package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Prevoznik;
import model.Proizvodjac;

public class SquliteProizvodjac {
	
	private static SquliteProizvodjac instance;
	
	public static SquliteProizvodjac getInstance() {  //get instance 
		if (instance == null) {
			instance = new SquliteProizvodjac();
		}
		return instance;
	}
	
	public void ucitajProizvodjace() throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  proizvodjaci";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Prevoznik pr = Firma.getInstance().getTrenutnaGodina().prevoznikSaSifrom(rs.getInt("pPrevoznik"));
				Proizvodjac p = new Proizvodjac(rs.getInt("sifra") ,rs.getString("ime") , rs.getString("prezime"), rs.getString("mesto"), rs.getString("maticniBroj"), rs.getString("brojGazdinstva"), rs.getString("telefon"), rs.getString("brojRacuna"),rs.getString("komentar"),pr,rs.getDouble("cenaPlus"));
				Firma.getInstance().getTrenutnaGodina().getProizvodjaci().add(p);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju proizvodjaca!");
			a.show();
			return;
			
		}finally{
			ps.close();
			rs.close();
		}

	}	
	
	public void upisiProizvodjaca(Proizvodjac p) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO proizvodjaci VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, p.getSifra());
			ps.setString(2, p.getIme());
			ps.setString(3, p.getPrezime());
			ps.setString(4, p.getMesto());
			ps.setString(5, p.getMaticniBroj());
			ps.setString(6, p.getBrojGazdinstva());
			ps.setString(7, p.getTelefon());
			ps.setString(8, p.getBrojRacuna());
			ps.setString(9, p.getKomentar());	
			if(p.getPrevoznik() == null || p.getPrevoznik().getSifra()==0) {
				ps.setNull(10, 0);
			}else {
				ps.setInt(10, p.getPrevoznik().getSifra());
			}
			ps.setDouble(11, p.getCenaPlus());
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu proizvodjaca ovde!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	public void obrisProizvodjaca(Proizvodjac p) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM proizvodjaci WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, p.getSifra());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju proizvodjaca!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}
		
	public void obrisSveProizvodjace() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM proizvodjaci ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih proizvodjaca!");
				a.show();
				return;
				
			}finally {
				ps.close();
			}	
	}
}
