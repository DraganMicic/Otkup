package glavnaBaza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Main.MainStage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import model.Firma;
import Podesavanja_Tab.PodesavanjaTab;

public class SqlitePodesavanja {
	
	private static SqlitePodesavanja instance;
	
	public static SqlitePodesavanja GetInstance() {
		if(instance == null)
			instance = new SqlitePodesavanja();
		return instance;
	}
	
	public void ucitajPodesavanja() throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT * FROM  podesavanja";
		
		try {
			ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {

				try {
					String ime = rs.getString("ime");
					Firma.getInstance().setIme(ime);
					String adresa = rs.getString("adresa");
					Firma.getInstance().setAdresa(adresa);
					String tekuciRacun = rs.getString("tekuciRacun");
					Firma.getInstance().setTekuciRacun(tekuciRacun);
					String pib = rs.getString("pib");
					Firma.getInstance().setPib(pib);
					String maticniBr = rs.getString("maticniBr");
					Firma.getInstance().setMaticniBr(maticniBr);
					String tel1 = rs.getString("telefon1");
					Firma.getInstance().setTelefon1(tel1);
					String tel2 = rs.getString("telefon2");
					Firma.getInstance().setTelefon2(tel2);
					String regbr = rs.getString("regBroj");
					Firma.getInstance().setRegBroj(regbr);
					String pzr = rs.getString("pzr");
					Firma.getInstance().setProdavnicaZaRobu(pzr);
					String pzg = rs.getString("pzg");
					Firma.getInstance().setProdavnicaZaGorivo(pzg);
					String direktor = rs.getString("direktor");
					Firma.getInstance().setDirektor(direktor);

				}catch (Exception e){
					Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju podesavanja! (podaci)");
					a.showAndWait();
					return;
				}

				try {
					String bojaDole = rs.getString("bojaDole");
					Firma.getInstance().setBojaDole(bojaDole);
					String bojaGore = rs.getString("bojaGore");
					Firma.getInstance().setBojaGore(bojaGore);
					MainStage.getInstance().podesiBoju(bojaDole, bojaGore);
				}catch (Exception e){
					Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju podesavanja! (boje)");
					e.printStackTrace();
					a.showAndWait();
					return;
				}

				try {
					String posprint = rs.getString("posPrint");
					Firma.getInstance().setPosStampacNaziv(posprint);
					Firma.getInstance().podesiPosPrinter();
					PodesavanjaTab.getInstance().getcBPosPrinter().setValue(posprint);

					String a4print = rs.getString("a4print");
					Firma.getInstance().setA4StampacNaziv(a4print);
					Firma.getInstance().podesiA4Printer();
					PodesavanjaTab.getInstance().getCbA4Printer().setValue(a4print);

				}catch (Exception e){
					Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju podesavanja! (stampaci)");
					a.showAndWait();
					return;
				}

				try {
					String pecat = rs.getString("pecat");
					PodesavanjaTab.getInstance().getTfPecat().setText(pecat);
					Firma.getInstance().setPecatLink(pecat);
					Firma.getInstance().setPecat(new Image(Firma.getInstance().getPecatLink()));

					Double pecatSize = rs.getDouble("pecatSize");
					Firma.getInstance().setPecatScale(pecatSize);
					PodesavanjaTab.getInstance().getTfPecatScale().setText(String.valueOf(pecatSize));

				}catch (Exception e){
					Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju podesavanja! (pecat)");
					a.showAndWait();
					return;
				}
			}
			ps.close();
			rs.close();
			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri ucitavanju podesavanja SQL!" + "\n E: " + e);
			a.showAndWait();
			return;			
		}finally{

		}
	}
	
	public void obrisiPodesavanja() throws SQLException {
		PreparedStatement ps = null;
		String query = "DELETE FROM podesavanja";
			
			try {
				ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju podesavanja!");
				a.show();
				return;
			}finally {
				ps.close();
			}	
	}
	
	public void upisiPodesavanja(String ime, String adresa, String tekuciRacun, String pib, String maticniBr, String Tel1, String Tel2, String regBr, String pzr, String Pzg, String direktor, String pecat, String bojaDole, String bojaGore , String posprint, String a4print, Double pecatScale  ) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO podesavanja VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = SqliteGlavnaBaza.getInstance().getKonekcija().prepareStatement(query);
			ps.setString(1, ime);
			ps.setString(2, adresa);
			ps.setString(3, tekuciRacun);
			ps.setString(4, pib);
			ps.setString(5, maticniBr);
			ps.setString(6, Tel1);
			ps.setString(7, Tel2);
			ps.setString(8, regBr);
			ps.setString(9, pzr);
			ps.setString(10, Pzg);
			ps.setString(11, direktor);
			ps.setString(12, pecat);
			ps.setString(13, bojaDole);
			ps.setString(14, bojaGore);
			ps.setString(15, posprint);
			ps.setString(16, a4print);
			ps.setDouble(17, pecatScale);
						
			ps.executeUpdate();			
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upsiu podesavanja!");
			a.show();
			return;
		}finally {
			ps.close();
		}	
	}
	
	
}
