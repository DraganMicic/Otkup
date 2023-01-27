package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Gajba;
import model.Prevoz;
import model.Prevoznik;
import model.Proizvodjac;
import model.Ulaz;
import model.UnosGajbi;
import model.UnosUlaza;

public class SquliteUnosUlaza {
	
	private static SquliteUnosUlaza instance;
	
	public static SquliteUnosUlaza getInstance() {
		if(instance == null) {
			instance = new SquliteUnosUlaza();
		}
		return instance;
	}
	
	public void ucitajUnoseUlaza() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  unosUlaza";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {				
				int sifra = rs.getInt("sifra");
				String datum = rs.getString("datum");
				LocalDate ld = LocalDate.parse(datum);				
				Proizvodjac p = Firma.getInstance().getTrenutnaGodina().proizvodjacSaSifrom(rs.getInt("sifraProizvodjaca"));
				Ulaz u = Firma.getInstance().getTrenutnaGodina().UlazSaSifrom(rs.getInt("sifraUlaza"));
				UnosGajbi unosGajbi = Firma.getInstance().getTrenutnaGodina().UnosGajbiSaSifrom(rs.getInt("sifraUnosaGajbi"));
				Gajba gajba = Firma.getInstance().getTrenutnaGodina().GajbaSaSifrom(rs.getInt("sifraGajbe"));
				double kolicinaBruto = rs.getDouble("kolicinaBruto");
				double kolicinaNeto = rs.getDouble("kolicnaNeto");
				int gajbe = rs.getInt("gajbe");
				Prevoznik prevoznik = Firma.getInstance().getTrenutnaGodina().prevoznikSaSifrom(rs.getInt("sifraPrevoznika"));
				
				UnosUlaza uu = new UnosUlaza(sifra, ld, p, u, unosGajbi,gajba, kolicinaBruto, kolicinaNeto, gajbe, prevoznik);
				Firma.getInstance().getTrenutnaGodina().getUnosiUlaza().add(uu);
				
				if(prevoznik != Firma.getInstance().getTrenutnaGodina().getLicno()) {
					Prevoz pre = new Prevoz(ld, prevoznik, kolicinaNeto, p, sifra);
					Firma.getInstance().getTrenutnaGodina().dodajPrevoz(pre);
				}

			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju unosa ulaza!");
			a.show();
			return;			
		}finally{
			ps.close();
			rs.close();
		}		
	}
	
	public void upisiUnosUlaza(UnosUlaza u) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO unosUlaza VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
		
			ps.setInt(1, u.getSifra());
		
			ps.setString(2, u.getDatum().toString());
			ps.setInt(3, u.getProizvodjac().getSifra());
			ps.setInt(4, u.getUlaz().getSifra());
			
			if(u.getUnosGajbi()==null) {
				ps.setNull(5, 0);
			}else {
				ps.setInt(5, u.getUnosGajbi().getSifra());
			}
			
			ps.setInt(6, u.getGajba().getSifra());
			ps.setDouble(7, u.getKolicinaBruto());
			ps.setDouble(8, u.getKolicinaNeto());
			ps.setInt(9, u.getGajbe());
			if(u.getPrevoznik() == null || u.getPrevoznik().getSifra()==0) {
				ps.setNull(10, 0);
			}else {
				ps.setInt(10, u.getPrevoznik().getSifra());
			}
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa ulaza!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	public void obrisUnosUlaza(UnosUlaza uu) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM unosUlaza WHERE sifra = ? ";
			
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, uu.getSifra());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju unosa ulaza!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}

}
