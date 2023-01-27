package glavnaBaza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Firma;
import model.Godina;

public class SquliteGodina {
	
	private static SquliteGodina instance;
	
	public static SquliteGodina GetInstance() {
		if(instance == null)
			instance = new SquliteGodina();
		return instance;
	}
	
	public void ucitajGodine() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  godine";
		
		try {
			ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {				
				int sifra = rs.getInt("broj");
				String linkBaze = rs.getString("linkBaze");
				Godina g = new Godina(sifra, linkBaze);				
				Firma.getInstance().getGodine().add(g);
			}
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju godina!!!!");
			a.showAndWait();
			return;			
		}finally{

		}
	}
	
	public void upisiGodinu(Godina g) throws SQLException{
		PreparedStatement ps = null;
		String query = "INSERT INTO godine VALUES(?,?)";
		try {
			ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setInt(1, g.getGodina());
			ps.setString(2, g.getLinkBaze());
			ps.executeUpdate();			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu godine!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	public void obrisGodinu(Godina g) throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM godine WHERE broj = ? ";
			
			try {
				ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.setInt(1, g.getGodina());
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju godine ovde!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}
	
	
	
}
