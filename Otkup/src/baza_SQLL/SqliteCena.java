package baza_SQLL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Ulaz;
import model.CenaUlaza;

public class SqliteCena {
	
	private static SqliteCena inastance;
	
	public static SqliteCena getInastance() {
		if(inastance == null) {
			inastance = new SqliteCena();
		}
		return inastance;
	}
	
	public void ucitajCene() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  ceneUlaza";
		
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {				
				int sifra = rs.getInt("sifra");
				Ulaz u = Firma.getInstance().getTrenutnaGodina().UlazSaSifrom(rs.getInt("sifraUlaza"));
				double cenaBezPrevoza = rs.getDouble("cenaBezPrevoza");
				double cenaSaPrevozom = rs.getDouble("cenaSaPrevozom");
				String pd = rs.getString("pocetakVazenja");
				LocalDate pocetniDatum = LocalDate.parse(pd);
				String kd = rs.getString("krajVazenja");
				LocalDate krajnjiDatum = LocalDate.parse(kd);
	
				CenaUlaza c = new CenaUlaza(sifra, u, cenaBezPrevoza, cenaSaPrevozom, pocetniDatum, krajnjiDatum);
				Firma.getInstance().getTrenutnaGodina().getCeneUlaza().add(c);
			}
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju cena ulaza!");
			a.show();
			return;			
		}finally{
			ps.close();
			rs.close();
		}
		
	}
	
	public void upisiCenuUlaza(CenaUlaza cu) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO ceneUlaza VALUES(?,?,?,?,?,?)";
		try {
			ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);			
			ps.setInt(1, cu.getSifra());
			ps.setInt(2, cu.getUlaz().getSifra());
			ps.setDouble(3, cu.getCenaBezPrevoza());
			ps.setDouble(4, cu.getCenaSaPrevozom());
			ps.setString(5, cu.getPocetakVazenja().toString());
			ps.setString(6, cu.getKrajVazenja().toString());
			ps.executeUpdate();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu cene ulaza!");
			a.show();
			return;
		}finally {
			ps.close();
		}
	}
	
	public void obrisCenuUlaza(CenaUlaza cu) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM ceneUlaza WHERE sifra = ? ";		
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, cu.getSifra());
				ps.executeUpdate();			
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju cene ulaza!");
				a.show();
				return;
			}finally {
				ps.close();
			}		
	}
	

	public void obrisSveCeneUlaza() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM ceneUlaza ";
			try {
				ps = SqlitteBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju svih cena ulaza!");
				a.show();
				return;			
			}finally {
				ps.close();
			}		
	}
	
	
}
