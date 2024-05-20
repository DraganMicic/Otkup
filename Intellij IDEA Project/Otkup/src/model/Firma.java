package model;

import java.sql.SQLException;
import java.util.ArrayList;
import baza_SQLL.SqliteCena;
import baza_SQLL.SqliteGajba;
import baza_SQLL.SqlitePrevoznik;
import baza_SQLL.SqliteUlaz;
import baza_SQLL.SqliteUnosGajbi;
import baza_SQLL.SquliteIzlaz;
import baza_SQLL.SquliteProizvodjac;
import baza_SQLL.SquliteUnosIzlaza;
import baza_SQLL.SquliteUnosUlaza;
import glavnaBaza.SquliteGodina;
import javafx.print.Printer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Firma {
	
	private static Firma instance;
	
	private Godina trenutnaGodina;
	private String linkGlavneBaze;
	private ArrayList<Godina> godine = new ArrayList<Godina>();
	private ArrayList<JedinicaMere> jediniceMere = new ArrayList<>();

	private String ime ;
	private String adresa ;
	private String tekuciRacun;
	private String pib ;
	private String maticniBr ;
	private String telefon1;
	private String telefon2 ;
	private String regBroj;
	private String prodavnicaZaRobu;
	private String prodavnicaZaGorivo;
	private String direktor ;
	private String pecatLink  ;
	private String bojaDole;
	private String bojaGore;
	private double pecatScale;
	
	private Image addIco = new Image("/slike/add.png", 20, 20 , false, false);	
	private Image editIco = new Image("/slike/edit.png", 20, 20 , false, false);
	private Image closeIco = new Image("/slike/close.png", 20, 20 , false, false);	
	private Image saveIco = new Image("/slike/save.png", 20, 20 , false, false);	
	private Image deleteIco = new Image("/slike/delete.png", 20, 20 , false, false);	
	private Image searchIco = new Image("/slike/search.png", 20, 20 , false, false);
	private Image printIco = new Image("/slike/print.png", 20, 20 , false, false);	
	private Image obracunIco = new Image("/slike/obracun.png", 20, 20 , false, false);	
	private Image lisaIco = new Image("/slike/lista.png", 20, 20 , false, false);	
	private Image pdfIco = new Image("/slike/pdf.png", 20, 20 , false, false);	
	private Image settingsIco = new Image("/slike/podesavanja2.png",20,20,false,false);
	private Image potvrdiIco = new Image("/slike/potvrdi.png",20,20,false,false);
	private Image backIco = new Image("/slike/back.png",20,20,false,false);
	private Image pecat ;
	
	private String PosStampacNaziv;	
	private Printer posPrinter;
	
	private String A4StampacNaziv;
	private Printer A4printer;
	

	 
	private Firma(){			
		JedinicaMere kg = new JedinicaMere("kg");
		JedinicaMere kom = new JedinicaMere("kom");
		JedinicaMere din = new JedinicaMere("din");
		JedinicaMere sat = new JedinicaMere("sati");
		jediniceMere.add(kg);
		jediniceMere.add(kom);
		jediniceMere.add(din);
		jediniceMere.add(sat); 	
		
	}
		
	public static Firma getInstance() {
		if (instance == null) {
			instance = new Firma();
		}
		return instance;	
	}
	
	public void ucitajBazu() {		
		try {
			SqlitePrevoznik.getInstance().ucitajPrevoznike();
			SquliteProizvodjac.getInstance().ucitajProizvodjace();
			SqliteUlaz.getInstance().ucitajUlaze();
			SqliteCena.getInastance().ucitajCene();
			SquliteIzlaz.getInstance().ucitajIzlaze();
			SqliteGajba.getInstance().ucitajGajbe();
			SquliteUnosIzlaza.getInstance().ucitajUnoseIzlaza();
			SqliteUnosGajbi.getInstance().ucitajUnoseGajbi();
			SquliteUnosUlaza.getInstance().ucitajUnoseUlaza();
			
		} catch (SQLException e) {
			System.out.println("Neuspelo učitavanje baze");
		}
	}
	
	
	public void podesiPosPrinter() {
		for(Printer p : Printer.getAllPrinters()) {
			if(p.getName().equals(PosStampacNaziv)){
				posPrinter = p;
			}
		}
	}
	
	public void podesiA4Printer() {
		for(Printer p : Printer.getAllPrinters()) {
			if(p.getName().equals(A4StampacNaziv)){
				A4printer = p;
			}
		}
	}
	
	//////////godine///////////////////
	
	public ArrayList<Godina> getGodine() {
		return godine;
	}
	
	public Godina getTrenutnaGodina() {
		return trenutnaGodina;
	}
	
	public void setTrenutnaGodina(Godina trenutnaGodina) {
		this.trenutnaGodina = trenutnaGodina;
	}
	
	public void dodajGodinu(Godina g) {
		try {
			SquliteGodina.GetInstance().upisiGodinu(g);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri dodavanju godine!");
			a.show();
			return;
		}
		godine.add(g);
	}
	
	public void izmeniGodinu(Godina stara, Godina nova) {
		try {
			SquliteGodina.GetInstance().obrisGodinu(stara);
			SquliteGodina.GetInstance().upisiGodinu(nova);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni godine!");
			a.show();
			return;
		}		
		godine.set(godine.indexOf(stara),nova);		
	}
	
	public void obrisiGodinu(Godina g) {
		try {
			SquliteGodina.GetInstance().obrisGodinu(g);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju godine!");
			a.show();
			return;
		}
		godine.remove(godine.indexOf(g));
	}
	
	/////////jedinice mere///////////
	
	public ArrayList<JedinicaMere> getJediniceMere() {
		return jediniceMere;
	}
	
	public void setJediniceMere(ArrayList<JedinicaMere> jediniceMere) {
		this.jediniceMere = jediniceMere;
	}
	
	public JedinicaMere stringToJedinicaMere (String s) {
		for(JedinicaMere i : jediniceMere) {
			if(i.toString().equals(s)) {
				return i;
			}
		}
		return null;
	}
	
	////////////ikonice////////////
	
	public Image getAddIco() {
		return addIco;
	}
	public Image getCloseIco() {
		return closeIco;
	}
	public Image getDeleteIco() {
		return deleteIco;
	}
	
	public Image getSaveIco() {
		return saveIco;
	}
	public Image getEditIco() {
		return editIco;
	}
	public Image getSearchIco() {
		return searchIco;
	}
	public String getPecatLink() {
		return pecatLink;
	}

	public void setPecatLink(String pecatLink) {
		this.pecatLink = pecatLink;
	}	

	public String getA4StampacNaziv() {
		return A4StampacNaziv;
	}

	public void setA4StampacNaziv(String a4StampacNaziv) {
		A4StampacNaziv = a4StampacNaziv;
	}

	public Printer getA4printer() {
		return A4printer;
	}

	public void setA4printer(Printer a4printer) {
		A4printer = a4printer;
	}

	public Image getObracunIco() {
		return obracunIco;
	}
	public Image getPrintIco() {
		return printIco;
	}
	public Image getLisaIco() {
		return lisaIco;
	}
	public Image getPdfIco() {
		return pdfIco;
	}

	public String getIme() {
		return ime;
	}

	public String getPosStampacNaziv() {
		return PosStampacNaziv;
	}

	public void setPosStampacNaziv(String posStampacNaziv) {
		PosStampacNaziv = posStampacNaziv;
	}

	public Printer getPosPrinter() {
		return posPrinter;
	}

	public void setPosPrinter(Printer posPrinter) {
		this.posPrinter = posPrinter;
	}

	public String getAdresa() {
		return adresa;
	}

	public String getTekuciRacun() {
		return tekuciRacun;
	}

	public String getPib() {
		return pib;
	}

	public String getMaticniBr() {
		return maticniBr;
	}

	public String getTelefon1() {
		return telefon1;
	}

	public String getTelefon2() {
		return telefon2;
	}

	public String getRegBroj() {
		return regBroj;
	}
	
	public Image getSettingsIco() {
		return settingsIco;
	}
	
	public Image getPotvrdiIco() {
		return potvrdiIco;
	}

	public double getPecatScale() {
		return pecatScale;
	}

	public void setPecatScale(double pecatScale) {
		this.pecatScale = pecatScale;
	}

	public Image getBackIco() {
		return backIco;
	}
	
	public String getLinkGlavneBaze() {
		return linkGlavneBaze;
	}
	
	public void setLinkGlavneBaze(String linkGlavneBaze) {
		this.linkGlavneBaze = linkGlavneBaze;
	}
	
	public String getProdavnicaZaRobu() {
		return prodavnicaZaRobu;
	}
	
	public String getProdavnicaZaGorivo() {
		return prodavnicaZaGorivo;
	}
	
	public static void setInstance(Firma instance) {
		Firma.instance = instance;
	}

	public void setGodine(ArrayList<Godina> godine) {
		this.godine = godine;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public void setTekuciRacun(String tekuciRacun) {
		this.tekuciRacun = tekuciRacun;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public void setMaticniBr(String maticniBr) {
		this.maticniBr = maticniBr;
	}

	public void setTelefon1(String telefon1) {
		this.telefon1 = telefon1;
	}

	public void setTelefon2(String telefon2) {
		this.telefon2 = telefon2;
	}

	public void setRegBroj(String regBroj) {
		this.regBroj = regBroj;
	}

	public void setProdavnicaZaRobu(String prodavnicaZaRobu) {
		this.prodavnicaZaRobu = prodavnicaZaRobu;
	}

	public void setProdavnicaZaGorivo(String prodavnicaZaGorivo) {
		this.prodavnicaZaGorivo = prodavnicaZaGorivo;
	}

	public void setDirektor(String direktor) {
		this.direktor = direktor;
	}

	public void setPecat(Image pecat) {
		this.pecat = pecat;
	}

	public String getDirektor() {
		return direktor;
	}
	
	public Image getPecat() {
		return pecat;
	}

	public String getBojaDole() {
		return bojaDole;
	}

	public void setBojaDole(String bojaDole) {
		this.bojaDole = bojaDole;
	}

	public String getBojaGore() {
		return bojaGore;
	}

	public void setBojaGore(String bojaGore) {
		this.bojaGore = bojaGore;
	}

}
