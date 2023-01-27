package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import baza_SQLL.SqliteCena;
import baza_SQLL.SqliteGajba;
import baza_SQLL.SqlitePrevoznik;
import baza_SQLL.SqliteUlaz;
import baza_SQLL.SqliteUnosGajbi;
import baza_SQLL.SquliteIzlaz;
import baza_SQLL.SquliteProizvodjac;
import baza_SQLL.SquliteUnosIzlaza;
import baza_SQLL.SquliteUnosUlaza;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Prevoz_Tab.PrevozTab;

public class Godina {
	
	private int godina;
	private String linkBaze;
	
	private ArrayList<Proizvodjac> proizvodjaci = new ArrayList<Proizvodjac>();
	private ArrayList<Izlaz> izlazi = new ArrayList<Izlaz>();
	private ArrayList<UnosIzlaza> unosiIzlaza = new ArrayList<UnosIzlaza>();
	private ArrayList<Ulaz> ulazi = new ArrayList<Ulaz>();
	private ArrayList<Prevoznik> prevoznici = new ArrayList<Prevoznik>();
	private ArrayList<UnosUlaza> unosiUlaza = new ArrayList<UnosUlaza>();
	private ArrayList<Prevoz> prevozi = new ArrayList<Prevoz>();
	private ArrayList<UnosGajbi> unosiGajbi = new ArrayList<UnosGajbi>();
	private ArrayList<CenaUlaza> ceneUlaza = new ArrayList<CenaUlaza>();
	private ArrayList<Gajba> gajbe = new ArrayList<Gajba>();
	
	private Prevoznik licno;
		
	public Godina(int godina, String linkBaze) { 
		super();
		licno = new Prevoznik(0, "licno", "", "", 0);
		prevoznici.add(licno);
		this.godina = godina;
		this.linkBaze = linkBaze;
	}
	
	public String toString() {
		return " " + godina;
	}
	
	public String getLinkBaze() {
		return linkBaze;
	}
	
	public int compareTo(Godina g) {
		if(this.godina > g.godina) {
			return 1;
		}else if(this.godina < g.godina) {
			return -1;
		}
		return 0;
	}
	
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Godina) {
			Godina g = (Godina)obj;
			return(this.godina == g.godina);
		}
		return false;
	}
	
	//proizvodjaci akcije  /////////////////////////////////////////////////
	
	public void dodajProizviodjaca(Proizvodjac p) {
		try {         
			SquliteProizvodjac.getInstance().upisiProizvodjaca(p);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu proizvodjaca u bazu!");
			a.show();
			return;
		}
		proizvodjaci.add(p);
	}
	
	public void obrisiProizvodjaca(Proizvodjac p) {
		try {         
			SquliteProizvodjac.getInstance().obrisProizvodjaca(p);;
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri brisanju proizvodjaca iz baze!");
			a.show();
			return;
		}
		proizvodjaci.remove(proizvodjaci.indexOf(p));

		ArrayList<UnosUlaza> zaBrisanje1 = new ArrayList<UnosUlaza>();
		for (UnosUlaza uu : unosiUlaza) 
			if (uu.getProizvodjac().equals(p)) 
				zaBrisanje1.add(uu);
				
		for(UnosUlaza uu : zaBrisanje1) 
			obrisiUnosUlaza(uu);
		

		ArrayList<UnosIzlaza> zaBrisanje2 = new ArrayList<UnosIzlaza>();
		for(UnosIzlaza ui : unosiIzlaza) 
			if(ui.getProizvodjac().equals(p)) 
				zaBrisanje2.add(ui);
			
		for(UnosIzlaza ui : zaBrisanje2)
			obrisiUnosIzlaza(ui);
		
		ArrayList<UnosGajbi> zaBrisanje3 = new ArrayList<UnosGajbi>();
		for(UnosGajbi ug : unosiGajbi) 
			if(ug.getProizvodjac()!= null && ug.getProizvodjac().equals(p))
				zaBrisanje3.add(ug);
		
		for(UnosGajbi ug : zaBrisanje3)
			obrisiUnosGajbi(ug);		
	}
	
	public void izmeniProizvodjaca(Proizvodjac stari, Proizvodjac novi) {
		try {         
			SquliteProizvodjac.getInstance().obrisProizvodjaca(stari);
			SquliteProizvodjac.getInstance().upisiProizvodjaca(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni proizvodjaca u bazi!");
			a.show();
			return;
		}
		proizvodjaci.set(proizvodjaci.indexOf(stari), novi);
		
		for (UnosUlaza uu : unosiUlaza) {			
			if(uu.getProizvodjac().equals(stari)) {
				UnosUlaza uuNovi = uu;
				uuNovi.setProizvodjac(novi);			
				izmeniUnosUlaza(uu, uuNovi);
			}				
		}
		
		if(unosiIzlaza!= null) {
			for (UnosIzlaza ui : unosiIzlaza) {
				System.out.println("STIGO DOVDE");
				if(ui.getProizvodjac().equals(stari)) {
					UnosIzlaza uiNovi = ui;
					uiNovi.setProizvodjac(novi);
					
					izmeniUnosizlaza(ui, uiNovi);
				}
			}		
		}		
		if(unosiGajbi!=null) {
			for (UnosGajbi ug : unosiGajbi) {
				if(	ug.getProizvodjac()!= null && ug.getProizvodjac().equals(stari)) {
					UnosGajbi ugNovi = ug;
					ugNovi.setProizvodjac(novi);
					izmeniUnosGajbi(ug, ugNovi);
				}
			}			
		}

		
	}
	
	public void sortirajProizvodjace() {
		Collections.sort(proizvodjaci);
	}
	
	public ArrayList<Proizvodjac> getProizvodjaci() {
		return proizvodjaci;
	}
	
	public Proizvodjac proizvodjacSaSifrom (int sifra) {
		for(Proizvodjac p : proizvodjaci) {
			if(p.getSifra()==sifra) {
				return (p);
			}
		}
		return null;
	}
	
	public ArrayList<Proizvodjac> filtrirajProizvodjace(String imeIliPrezime, String mesto, String prevoznik){
		
		ArrayList<Proizvodjac> rezultati = new ArrayList<Proizvodjac>();
		rezultati.addAll(proizvodjaci);
		
		ArrayList<Proizvodjac> zaObrisi1 = new ArrayList<Proizvodjac>();
		for(Proizvodjac p : rezultati) {
			String imeIPrezime = p.getIme() + " " + p.getPrezime();
			if(!imeIliPrezime.equals("")  && !(imeIPrezime.toLowerCase().contains(imeIliPrezime.toLowerCase()))) {
				zaObrisi1.add(p);
			}
		}
		rezultati.removeAll(zaObrisi1);
		
		ArrayList<Proizvodjac> zaObrisi2 = new ArrayList<Proizvodjac>();	
		for(Proizvodjac p2 : rezultati) {
			if(!mesto.equals("")  && !(p2.getMesto().toLowerCase().contains(mesto.toLowerCase()))) {
				zaObrisi2.add(p2);
				continue;
			}
		}
		rezultati.removeAll(zaObrisi2);
		
		ArrayList<Proizvodjac> zaObrisi3 = new ArrayList<Proizvodjac>();	
		for(Proizvodjac p3 : rezultati) {
			if(!prevoznik.equals("") && !(p3.getPrevoznik() != null && p3.getPrevoznik().toString().toLowerCase().contains(prevoznik.toLowerCase()))) {
				zaObrisi3.add(p3);
				continue;
			}
		}
		rezultati.removeAll(zaObrisi3);
	
			
		return rezultati;
	}
	
	//izlazi akcije ////////////////////////////////////////////////
	
	public void dodajIzlaz(Izlaz i) {
		try {           
			SquliteIzlaz.getInstance().upisiIzlaz(i);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu izlaza!");
			a.show();
			return;
		}
		izlazi.add(i);
	}

	public void izmeniIzlaz(Izlaz stari, Izlaz novi) {
		try {           
			SquliteIzlaz.getInstance().obrisiIzlaz(stari);
			SquliteIzlaz.getInstance().upisiIzlaz(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni izlaza!");
			a.show();
			return;
		}
		izlazi.set(izlazi.indexOf(stari), novi);
		
		for(UnosIzlaza ui : unosiIzlaza) {
			if(ui.getIzlaz().equals(stari)) {
				UnosIzlaza uiNovi = ui;
				uiNovi.setIzlaz(novi);
				izmeniUnosizlaza(ui, uiNovi);
			}
		}
	}
	
	public void obrisiIzlaz(Izlaz i) {
		try {
			SquliteIzlaz.getInstance().obrisiIzlaz(i);;  //update baze
		} catch (SQLException e) {
			Alert b = new Alert(AlertType.ERROR, "Greska pri brisanju izlaza!");
			b.show();
			return;
		}
		izlazi.remove(izlazi.indexOf(i));
		
		ArrayList<UnosIzlaza> zaBrisanje1 = new ArrayList<UnosIzlaza>();
		for(UnosIzlaza ui : unosiIzlaza)
			if (ui.getIzlaz().equals(i))
				zaBrisanje1.add(ui);
		for(UnosIzlaza ui : zaBrisanje1)
			obrisiUnosIzlaza(ui);
	}
	
	public ArrayList<Izlaz> izlaziZaPeriod (LocalDate dPocetni, LocalDate dKrajnji) {
		ArrayList<Izlaz> rezultati = new ArrayList<Izlaz>();
		for(UnosIzlaza ui : unosiIzlazaZaPeriod(dPocetni, dKrajnji)) {
			if(!rezultati.contains(ui.getIzlaz())) {
				rezultati.add(ui.getIzlaz());
			}
		}
		return rezultati;
	}
	
	public void sortirajIzlaze() {
		Collections.sort(izlazi);
	}
	
	public ArrayList<Izlaz> getIzlazi() {
		return izlazi;
	}
	
	public Izlaz izlazSaSifrom(int sifra) {
		for(Izlaz i : izlazi) {
			if(i.getSifra()==sifra) {
				return(i);
			}
		}
		return null;
	}
	
	public ArrayList<Izlaz> filtrirajIzlaze(String termin){
		ArrayList<Izlaz> rezultati = new ArrayList<Izlaz>();
		for(Izlaz i : izlazi) {
			if(i.getNaziv().toLowerCase().contains(termin.toLowerCase())) {
				rezultati.add(i);
				continue;
			}
			if(i.getOpis().toLowerCase().contains(termin.toLowerCase())) {
				rezultati.add(i);
				continue;
			}
		}
		return rezultati;
	}
	
	//unosi izlaza akcije /////////////////////////

	public void dodajUnosIzlaza (UnosIzlaza ui) {
		try {         
			SquliteUnosIzlaza.getInstance().upisiUnosIzlaza(ui);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa izlaza!");
			a.show();
			return;
		}
		unosiIzlaza.add(ui);
	}
	
	public void izmeniUnosizlaza(UnosIzlaza stari, UnosIzlaza novi) {
		try {           
			SquliteUnosIzlaza.getInstance().obrisUnosIzlaza(stari);
			SquliteUnosIzlaza.getInstance().upisiUnosIzlaza(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni unosa izlaza!");
			a.show();
			return;
		}
		unosiIzlaza.set(unosiIzlaza.indexOf(stari), novi);
	}
	
	public void obrisiUnosIzlaza(UnosIzlaza ui) {
		try {
			SquliteUnosIzlaza.getInstance().obrisUnosIzlaza(ui);;
		} catch (SQLException e) {
			Alert ab = new Alert(AlertType.ERROR, "Greska pri brisanju unosa izlaza!");
			ab.show();
			return;
		}
		unosiIzlaza.remove(ui);
	}
	
	public ArrayList<UnosIzlaza> unosiIzlazaZaDatum(LocalDate datum){
		ArrayList<UnosIzlaza> rezultati = new ArrayList<UnosIzlaza>();
		for(UnosIzlaza ui : unosiIzlaza) {
			if(ui.getDatum().equals(datum)) {
				rezultati.add(ui);
			}
		}
		return rezultati;
	}
	
	public ArrayList<UnosIzlaza> unosiIzlazaZaPeriod(LocalDate Dpocetni, LocalDate Dkrajnji){
		ArrayList<UnosIzlaza> rezultati = new ArrayList<UnosIzlaza>();
		for(UnosIzlaza ui : unosiIzlaza) {
			if(ui.getDatum().equals(Dpocetni) || ui.getDatum().equals(Dkrajnji) || (ui.getDatum().isAfter(Dpocetni) && ui.getDatum().isBefore(Dkrajnji))) {
				rezultati.add(ui);
			}
		}
		return rezultati;
	}
	
	public void sortirajUnoseIzlaza() {
		Collections.sort(unosiIzlaza);
	}
	
	public ArrayList<UnosIzlaza> getUnosiIzlaza() {
		return unosiIzlaza;
	}
	
	public ArrayList<UnosIzlaza> filtrirajUnoseIzlaza(Boolean dan, Boolean period, LocalDate pocetniDatum, LocalDate krajnjiDatum, Proizvodjac pro, Izlaz izlaz){
		ArrayList<UnosIzlaza> rezultati = new ArrayList<UnosIzlaza>();
		rezultati.addAll(unosiIzlaza);
		
		ArrayList<UnosIzlaza> zaObris1 = new ArrayList<UnosIzlaza>();
		if(pro != null) {
			for(UnosIzlaza ui : rezultati) {
				if(!ui.getProizvodjac().equals(pro)) {
					zaObris1.add(ui);
				}
			}
			rezultati.removeAll(zaObris1);
		}
		
		ArrayList<UnosIzlaza> zaObris2 = new ArrayList<UnosIzlaza>();
		if(izlaz != null) {
			for(UnosIzlaza ui : rezultati) {
				if(!ui.getIzlaz().equals(izlaz)) {
					zaObris2.add(ui);
				}
			}
			rezultati.removeAll(zaObris2);
		}
		
		ArrayList<UnosIzlaza> zaObris3 = new ArrayList<UnosIzlaza>();
		if(dan && pocetniDatum != null) {
			for(UnosIzlaza ui : rezultati) {
				if(!ui.getDatum().equals(pocetniDatum)) {
					zaObris3.add(ui);
				}
			}
			rezultati.removeAll(zaObris3);
		}
		
		ArrayList<UnosIzlaza> zaObris4 = new ArrayList<UnosIzlaza>();
		if(period && pocetniDatum != null && krajnjiDatum  != null) {
			for(UnosIzlaza ui : rezultati) {
				if(!(ui.getDatum().equals(pocetniDatum) || ui.getDatum().equals(krajnjiDatum) || (ui.getDatum().isAfter(pocetniDatum)&& ui.getDatum().isBefore(krajnjiDatum)))) {
					zaObris4.add(ui);
				}
			}
			rezultati.removeAll(zaObris4);
		}
		
		return rezultati;
	}
	
	/////// ulazi akcije //////////
	
	public void dodajUlaz (Ulaz u) {
		try {         
			SqliteUlaz.getInstance().upisiUlaz(u);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu ulaza!");
			a.show();
			return;
		}
		ulazi.add(u);
	}

	public void izmeniUlaz (Ulaz stari, Ulaz novi) {
		try {           
			SqliteUlaz.getInstance().obrisiUlaz(stari);
			SqliteUlaz.getInstance().upisiUlaz(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni ulaza!");
			a.show();
			return;
		}
		ulazi.set(ulazi.indexOf(stari), novi);
		
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getUlaz().equals(stari)) {
				UnosUlaza uuNovi = uu;
				uuNovi.setUlaz(novi);
				izmeniUnosUlaza(uu, uuNovi);
			}
		}
		
		for(CenaUlaza cu : ceneUlaza) {
			if(cu.getUlaz().equals(stari)) {
				CenaUlaza cuNoovi = cu;
				cuNoovi.setUlaz(novi);
				izmeniCenuUlaza(cu, cuNoovi);
			}
		}
	}

	public void obrisiUlaz(Ulaz u) {	
		try {
			SqliteUlaz.getInstance().obrisiUlaz(u);;
		} catch (SQLException e) {
			Alert b = new Alert(AlertType.ERROR, "Greska pri brisanju ulaza!");
			b.show();
			return;
		}
		ulazi.remove(ulazi.indexOf(u));
		
		ArrayList<UnosUlaza> zaBrisanje1 = new ArrayList<UnosUlaza>();
		for(UnosUlaza uu : unosiUlaza)
			if(uu.getUlaz().equals(u))
				zaBrisanje1.add(uu);
		
		for(UnosUlaza uu : zaBrisanje1)
			obrisiUnosUlaza(uu);
		
		ArrayList<CenaUlaza> zaBrisanje2 = new ArrayList<CenaUlaza>();
		for(CenaUlaza cu : ceneUlaza)
			if(cu.getUlaz().equals(u))
				zaBrisanje2.add(cu);
		
		for(CenaUlaza cu : zaBrisanje2)
			obrisiCenuUlaza(cu);
	}

	public ArrayList<Ulaz> ulaziZaDatum(LocalDate datum){
		ArrayList<Ulaz> rezultati = new ArrayList<Ulaz>();
		for(UnosUlaza uu : unosiUlazaZaDatum(datum)) {
			if(!rezultati.contains(uu.getUlaz())) {
				rezultati.add(uu.getUlaz());
			}
		}
		return rezultati;
	}
	
	public ArrayList<Ulaz> ulaziZaPeriod(LocalDate dPocetni, LocalDate dKrajnji){
		ArrayList<Ulaz> rezultati = new ArrayList<Ulaz>();
		for(UnosUlaza uu : unosiUlazaZaPeriod(dPocetni, dKrajnji)) {
			if(!rezultati.contains(uu.getUlaz())){
				rezultati.add(uu.getUlaz());
			}
		}
		return rezultati;
	}
	
	public ArrayList<Ulaz> filtrirajUlaze (String txt){
		ArrayList<Ulaz> rezultati = new ArrayList<Ulaz>();
		for(Ulaz u : ulazi) {
			if(u.getNaziv().toLowerCase().contains(txt.toLowerCase())) {
				rezultati.add(u);
				continue;
			}
			if(u.getOpis().toLowerCase().contains(txt.toLowerCase())) {
				rezultati.add(u);
				continue;
			}
		}
		return rezultati;
	}
	
	public void sortirajUlaze() {
		Collections.sort(ulazi);
	}
	
	public ArrayList<Ulaz> getUlazi() {
		return ulazi;
	}
	
	public Ulaz UlazSaSifrom(int sifra) {
		for(Ulaz u : ulazi) {
			if(u.getSifra()==sifra) {
				return(u);
			}
		}
		return null;
	}
	
	//////prevoznici akcije /////////
	
	public Prevoznik getLicno() {
		return licno;
	}
	
	public void dodajPrevoznika (Prevoznik p) {		
		try {         
			SqlitePrevoznik.getInstance().upisiPrevoznika(p);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu prevoznika u bazu!");
			a.show();
			return;
		}
		prevoznici.add(p);
	}
	
	public void obrisiPrevoznika(Prevoznik p) {		
		try {
			SqlitePrevoznik.getInstance().obrisiPrevoznika(p);;
		} catch (SQLException e) {
			Alert ab = new Alert(AlertType.ERROR, "Greska pri brisanju prevoznika iz baze!");
			ab.show();
			return;
		}
		prevoznici.remove(prevoznici.indexOf(p));
		
		for(Proizvodjac pro : proizvodjaci)
			if(pro.getPrevoznik().equals(p))
				pro.setPrevoznik(licno);
		
		for(UnosUlaza uu : unosiUlaza)
			if(uu.getPrevoznik().equals(p)) {
				UnosUlaza uuNovi = uu;
				uu.setPrevoznik(licno);
				izmeniUnosUlaza(uu, uuNovi);
			}
				
		
		ArrayList<UnosGajbi> zaBrisanje1 = new ArrayList<UnosGajbi>();
		for(UnosGajbi ug : unosiGajbi)
			if(ug.getPrevoznik() != null && ug.getPrevoznik().equals(p))
				zaBrisanje1.add(ug);
		
		for(UnosGajbi ug : zaBrisanje1)
			obrisiUnosGajbi(ug);
		
		ArrayList<Prevoz> zaBrisanje2 = new ArrayList<Prevoz>();
		for(Prevoz pre : prevozi) 
			if(pre.getPrevoznik().equals(p)) 
				zaBrisanje2.add(pre);
			
		
		for(Prevoz pre : zaBrisanje2) 
			obrisiPrevoz(pre);
		
	}

	public void izmeniPrevoznika(Prevoznik stari, Prevoznik novi) {
		try {       
			SqlitePrevoznik.getInstance().obrisiPrevoznika(stari);
			SqlitePrevoznik.getInstance().upisiPrevoznika(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni prevoznika!");
			a.show();
			return;
		}
		prevoznici.set(prevoznici.indexOf(stari), novi);
		
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getPrevoznik().equals(stari)) {
				UnosUlaza uuNovi = uu;
				uu.setPrevoznik(novi);
				izmeniUnosUlaza(uu, uuNovi);
			}
		}
		
		for (Proizvodjac p : proizvodjaci) {
			if(p.getPrevoznik().equals(stari)) {
				Proizvodjac pNovi = p;
				pNovi.setPrevoznik(novi);
				izmeniProizvodjaca(p, pNovi);
			}
		}
		
		for(UnosGajbi ug : unosiGajbi) {
			if(ug.getPrevoznik() != null && ug.getPrevoznik().equals(stari)) {
				UnosGajbi ugNovi = ug;
				ugNovi.setPrevoznik(novi);
				izmeniUnosGajbi(ug, ugNovi);
			}
		}
		
		for(Prevoz pre : prevozi) {
			if(pre.getPrevoznik().equals(stari)) {
				Prevoz noviPrevoz = pre;
				noviPrevoz.setPrevoznik(novi);
				izmeniPrevoz(pre, noviPrevoz);
			}
		}
	}
	
	public void sortirajPrevoznike() {
		Collections.sort(prevoznici);
	}
	public ArrayList<Prevoznik> getPrevoznici() {
		return prevoznici;
	}
	
	public Prevoznik prevoznikSaSifromBezLicno(int sifra) {
		for(Prevoznik p : prevoznici) {
			if(sifra == 0) {
				return null;
			}else if(p.getSifra() == sifra){
				return (p);
			}
		}
		return null;
	}
	
	public Prevoznik prevoznikSaSifrom (int sifra) {
		for(Prevoznik p : prevoznici) {
			if(p.getSifra()==sifra) {
				return (p);
			}
		}
		return null;
	}
	
	public ArrayList<Prevoznik> filtrirajPrevoznike(String termin){
		ArrayList<Prevoznik> rezultati = new ArrayList<Prevoznik>();
		rezultati.addAll(prevoznici);
		rezultati.remove(licno);
		
		ArrayList<Prevoznik> zaObris = new ArrayList<Prevoznik>();
		for(Prevoznik pr : rezultati) {
			String imeIprezime = pr.getIme() + " " + pr.getPrezime();
			if(!imeIprezime.toLowerCase().contains(termin.toLowerCase())) {
				zaObris.add(pr);
			}
		}
		rezultati.removeAll(zaObris);
		return rezultati;
	}
	
	///////unosi ulaza ////////
	
	public void dodajUnosUlaza (UnosUlaza uu) {
		if(uu.getUnosGajbi() != null) {
			dodajUnosGajbi(uu.getUnosGajbi());
		}
		try {         
			SquliteUnosUlaza.getInstance().upisiUnosUlaza(uu);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa ulaza u bazu!");
			a.show();
			return;
		}
		unosiUlaza.add(uu);
		
		if(uu.getPrevoznik() != Firma.getInstance().getTrenutnaGodina().getLicno()) {
			Prevoz pre = new Prevoz(uu.getDatum(), uu.getPrevoznik(), uu.getKolicinaNeto(), uu.getProizvodjac(), uu.getSifra());
			Firma.getInstance().getTrenutnaGodina().dodajPrevoz(pre);
		}
	}
	
	public void obrisiUnosUlaza(UnosUlaza uu) {
		UnosGajbi ug = uu.getUnosGajbi();			
		if(ug != null) {
			obrisiUnosGajbi(ug);
		}
		try {
			SquliteUnosUlaza.getInstance().obrisUnosUlaza(uu);			
		} catch (SQLException e) {
			Alert ab = new Alert(AlertType.ERROR, "Greska pri brisanju unosa ulaza!");
			ab.show();
			return;
		}
		unosiUlaza.remove(unosiUlaza.indexOf(uu));
		
		if(uu.getPrevoznik() != Firma.getInstance().getTrenutnaGodina().getLicno()) {
			obrisiPrevozSaSifrom(uu.getSifra());
			PrevozTab.getInstance().updateTabelePrevoza();
		}		
	}
	 
	public void izmeniUnosUlaza(UnosUlaza stari, UnosUlaza novi) {

		if(stari.getUnosGajbi() != null) {
			obrisiUnosGajbi(stari.getUnosGajbi());
		}	
		if(novi.getUnosGajbi() != null) {
			dodajUnosGajbi(novi.getUnosGajbi());
		}
		
		try {         
			SquliteUnosUlaza.getInstance().obrisUnosUlaza(stari);
			SquliteUnosUlaza.getInstance().upisiUnosUlaza(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa ulaza u bazu!");
			a.show();
			return;
		}		
		unosiUlaza.set(unosiUlaza.indexOf(stari),novi);	
		
		
		if(stari.getPrevoznik() != Firma.getInstance().getTrenutnaGodina().getLicno()) {
			obrisiPrevozSaSifrom(stari.getSifra());
			PrevozTab.getInstance().updateTabelePrevoza();				
		}
		if(novi.getPrevoznik() != Firma.getInstance().getTrenutnaGodina().getLicno()) {
			Prevoz pre = new Prevoz(novi.getDatum(), novi.getPrevoznik(), novi.getKolicinaNeto(), novi.getProizvodjac(), novi.getSifra());
			dodajPrevoz(pre);
		}

	}
	
	public ArrayList<UnosUlaza> filtrirajUnoseUlaza(Boolean dan, Boolean period, LocalDate pocetniDatum, LocalDate krajnjiDatum, Proizvodjac proizvodjac, Ulaz ulaz, Prevoznik prevoznik){
		ArrayList<UnosUlaza> rezultati = new ArrayList<UnosUlaza>();
		rezultati.addAll(unosiUlaza);
		
		ArrayList<UnosUlaza> zaObris1 = new ArrayList<UnosUlaza>();
		if(proizvodjac !=null) {
			for(UnosUlaza uu : rezultati) {
				if(!uu.getProizvodjac().equals(proizvodjac)) {
					zaObris1.add(uu);
				}
			}
			rezultati.removeAll(zaObris1);
		}
		
		ArrayList<UnosUlaza> zaObris2 = new ArrayList<UnosUlaza>();
		if(ulaz != null) {
			for(UnosUlaza uu : rezultati) {
				if(!uu.getUlaz().equals(ulaz)) {
					zaObris2.add(uu);
				}
			}
			rezultati.removeAll(zaObris2);
		}
		
		ArrayList<UnosUlaza> zaObris3 = new ArrayList<UnosUlaza>();
		if(prevoznik != null) {
			for(UnosUlaza uu : rezultati) {
				if(!uu.getPrevoznik().equals(prevoznik)) {
					zaObris3.add(uu);
				}
			}
			rezultati.removeAll(zaObris3);
		}
		
		ArrayList<UnosUlaza> zaObris4 = new ArrayList<UnosUlaza>();
		if(dan && pocetniDatum != null){
			for(UnosUlaza uu : rezultati) {
				if(!uu.getDatum().equals(pocetniDatum)) {
					zaObris4.add(uu);
				}
			}
			rezultati.removeAll(zaObris4);
		}
		if(period && pocetniDatum != null && krajnjiDatum !=null) {
			for(UnosUlaza uu : rezultati) {
				if(!(uu.getDatum().equals(pocetniDatum) || uu.getDatum().equals(krajnjiDatum) || (uu.getDatum().isBefore(krajnjiDatum)&& uu.getDatum().isAfter(pocetniDatum)))) {
					zaObris4.add(uu);
				}
			}
			rezultati.removeAll(zaObris4);
		}		
		return rezultati;
	}
	
	public ArrayList<UnosUlaza> unosiUlazaZaDatum(LocalDate datum){
		ArrayList<UnosUlaza> rezultati = new ArrayList<UnosUlaza>();
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getDatum().equals(datum)) {
				rezultati.add(uu);
			}
		}
		return rezultati;
	}
	
	public ArrayList<UnosUlaza> unosiUlazaZaPeriod(LocalDate Dpocetni, LocalDate  Dkrajnji){
		ArrayList<UnosUlaza> rezultati = new ArrayList<UnosUlaza>();
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getDatum().equals(Dpocetni) || uu.getDatum().equals(Dkrajnji) || (uu.getDatum().isAfter(Dpocetni) && uu.getDatum().isBefore(Dkrajnji))){
				rezultati.add(uu);
			}
		}		
		return rezultati;
	}
	
	
	public void sortirajUnoseUlaza() {
		Collections.sort(unosiUlaza);
	}
	
	public ArrayList<UnosUlaza> getUnosiUlaza() {
		return unosiUlaza;
	}
	 
	public UnosUlaza UnosUlazaSaSifrom (int sifra) {
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getSifra()==sifra) {
				return uu;
			}
		}
		return null;
	}
			
	//////////prevozi/////////
	
	public void dodajPrevoz (Prevoz pr) {
		prevozi.add(pr);
		PrevozTab.getInstance().updateTabelePrevoza();
	}
	
	public void sortirajPrevoze() {
		Collections.sort(prevozi);
	}
	
	public void obrisiPrevoz(Prevoz pre) {
		prevozi.remove(pre);
	}
	
	public void izmeniPrevoz(Prevoz stari, Prevoz noci) {
		prevozi.set(prevozi.indexOf(stari), noci);
	}
	
	public void obrisiPrevozSaSifrom(int sifra) {
		Prevoz zaObris = null;
		for (Prevoz pre : prevozi) {
			if(pre.getSifraUnosaUlaza() == sifra) {
				zaObris = pre;
			}
		}
		prevozi.remove(zaObris);
	}
	
	public double ukupnaKolicinaZaPrevoznika(Prevoznik p) {
		double ukupno = 0;
		for(Prevoz pre : prevozi) {
			if(pre.getPrevoznik().equals(p)) {
				ukupno += pre.getKolicna();
			}
		}
		return ukupno;
	}
	
	public ArrayList<Prevoz> filtrirajPrevoze(Boolean dan, Boolean period, LocalDate dPocetni, LocalDate dKrajnji, String ime){
		ArrayList<Prevoz> rezultati = new ArrayList<Prevoz>();
		rezultati.addAll(prevozi);
		
		if(ime != null) {
			ArrayList<Prevoz> zaObris1 = new ArrayList<Prevoz>();
			ArrayList<Prevoznik> prevoznici = new ArrayList<Prevoznik>();
			prevoznici = Firma.getInstance().getTrenutnaGodina().filtrirajPrevoznike(ime);
			for(Prevoz p : rezultati) {
				if(!prevoznici.contains(p.getPrevoznik())) {
					zaObris1.add(p);
				}
			}
			rezultati.removeAll(zaObris1);
		}
		
		ArrayList<Prevoz> zaObris2 = new ArrayList<Prevoz>();
		if(dan && dPocetni != null) {
			for(Prevoz p : rezultati) {
				if(!p.getDatum().equals(dPocetni)) {
					zaObris2.add(p);
				}
			}
			rezultati.removeAll(zaObris2);
		}
		
		ArrayList<Prevoz> zaObris3 = new ArrayList<Prevoz>();
		if(period && dPocetni != null && dKrajnji != null) {
			for(Prevoz p : rezultati) {
				if(!(p.getDatum().equals(dPocetni) || p.getDatum().equals(dKrajnji)  || (p.getDatum().isAfter(dPocetni) && p.getDatum().isBefore(dKrajnji)))) {
					zaObris3.add(p);
				}
			}
			rezultati.removeAll(zaObris3);
		}
		
		return rezultati;
	}
	
	public ArrayList<Prevoz> getPrevozi() {
		return prevozi;
	}
		
	//////////unosi gajbi//////
	
	public void dodajUnosGajbi(UnosGajbi ug) {
		try {         
			SqliteUnosGajbi.getInstance().upisiUnosGajbi(ug);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu unosa gajbi!");
			a.show();
			return;
		}
		unosiGajbi.add(ug);
	}
	
	public void izmeniUnosGajbi(UnosGajbi stari, UnosGajbi novi) {		
		try {           
			SqliteUnosGajbi.getInstance().obrisUnosGajbi(stari);
			SqliteUnosGajbi.getInstance().upisiUnosGajbi(novi);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni unosa gajbi!");
			a.show();
			return;
		}
		unosiGajbi.set(unosiGajbi.indexOf(stari), novi);
		
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getUnosGajbi() != null && uu.getUnosGajbi().equals(stari)) {
				System.out.println("evo me");
				uu.setUnosGajbi(novi);

			}
		}		
	}
	
	public void obrisiUnosGajbi(UnosGajbi ug) {	
		try {
			SqliteUnosGajbi.getInstance().obrisUnosGajbi(ug);
		} catch (SQLException e) {
			Alert ab = new Alert(AlertType.ERROR, "Greska pri brisanju unosa gajbi!");
			ab.show();
			return;
		}
		unosiGajbi.remove(ug);
		
		for(UnosUlaza uu : unosiUlaza)
			if(uu.getUnosGajbi()!= null && uu.getUnosGajbi().equals(ug))
				uu.setUnosGajbi(null);
	}
	
	public void sortrirajUnoseGajbi() {
		Collections.sort(unosiGajbi);
	}
	
	public ArrayList<UnosGajbi> getUnosiGajbi() {
		return unosiGajbi;
	}
	
	public ArrayList<UnosGajbi> filtriraniUnosiGajbi(Boolean proizvodjac, Boolean prevoznik, Proizvodjac pro, Prevoznik pre, Gajba g) {
		ArrayList<UnosGajbi> rezultati = new ArrayList<UnosGajbi>();
		rezultati.addAll(unosiGajbi);
		
		ArrayList<UnosGajbi> zaObris1 = new ArrayList<UnosGajbi>(); 
		for(UnosGajbi ug : rezultati) 
			if(g != null && ! ug.getGajba().equals(g)) 
				zaObris1.add(ug);
							
		rezultati.removeAll(zaObris1);
				
		ArrayList<UnosGajbi> zaObris2 = new ArrayList<UnosGajbi>(); 
		if(proizvodjac) {
			for(UnosGajbi ug : rezultati) {
				if(ug.getProizvodjac() == null)
					zaObris2.add(ug);
				else if(pro != null && !ug.getProizvodjac().equals(pro))
					zaObris2.add(ug);			
			}
		}
		rezultati.removeAll(zaObris2);
		
		ArrayList<UnosGajbi> zaObris3 = new ArrayList<UnosGajbi>(); 
		if(prevoznik) {
			for(UnosGajbi ug : rezultati) {
				if(ug.getPrevoznik() == null) 
					zaObris3.add(ug);
				else if(pre != null && !ug.getPrevoznik().equals(pre))
					zaObris3.add(ug);			
			}
		}
		rezultati.removeAll(zaObris3);
	
		return rezultati;
	}
	
	public UnosGajbi UnosGajbiSaSifrom(int sifra) {
		for(UnosGajbi ug : unosiGajbi) {
			if(sifra == 0) {
				return null;
			}else if(ug.getSifra()==sifra) {
				return(ug);
			}
		}
		return null;
	}
	
	public int gajbiKodProizvodjaca (Proizvodjac p) {
		int gajbi = 0;
		for(UnosGajbi ug : unosiGajbi) {
			if(ug.getProizvodjac() == p) {
				gajbi = gajbi + ug.getGajbeIzlaz() - ug.getGajbeUlaz();
			}
		}
		return gajbi;
	}
	
	public ArrayList<UnosGajbi> unosiGajbiZaProizvodjaca (Proizvodjac p){
		ArrayList<UnosGajbi> rezultati = new ArrayList<UnosGajbi>();
		for (UnosGajbi ug : unosiGajbi) {
			if(ug.getProizvodjac() != null && ug.getProizvodjac().equals(p)) {
				rezultati.add(ug);
			}
		}
		return rezultati;
	}
	
	public ArrayList<UnosGajbi> unosiGajBiZaPrevoznika (Prevoznik p){
		ArrayList<UnosGajbi> rezultati = new ArrayList<UnosGajbi>();
		for (UnosGajbi ug : unosiGajbi) {
			if(ug.getPrevoznik() != null && ug.getPrevoznik().equals(p)) {
				rezultati.add(ug);
			}
		}
		return rezultati;
	}
	
	public int gajbiKodPrevoznika (Prevoznik p) {
		int gajbi = 0;
		for(UnosGajbi ug : unosiGajbi) {
			if(ug.getPrevoznik() == p) {
				gajbi = gajbi + ug.getGajbeIzlaz() - ug.getGajbeUlaz();
			}
		}
		return gajbi;
	}
	
	///////////cene////////
	
	public void dodajCenuUlaza(CenaUlaza c) {
		try {         
			SqliteCena.getInastance().upisiCenuUlaza(c);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu cene ulaza!");
			a.show();
			return;
		}
		ceneUlaza.add(c);
	}
	
	public void izmeniCenuUlaza(CenaUlaza stara, CenaUlaza nova) {
		try {           
			SqliteCena.getInastance().obrisCenuUlaza(stara);
			SqliteCena.getInastance().upisiCenuUlaza(nova);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni cene ulaza");
			a.show();
			return;
		}
		ceneUlaza.set(ceneUlaza.indexOf(stara), nova);
	}

	public void obrisiCenuUlaza(CenaUlaza c) {	
		try {
			SqliteCena.getInastance().obrisCenuUlaza(c);
		} catch (SQLException e) {
			Alert b = new Alert(AlertType.ERROR, "Greska pri brisanju cene ulaza!");
			b.show();
			return;
		}
		ceneUlaza.remove(c);
	}
	
	public void sortirajCeneUlaza() {
		Collections.sort(ceneUlaza);
	}
	
	public ArrayList<CenaUlaza> getCeneUlaza() {
		return ceneUlaza;
	}
	
	public ArrayList<CenaUlaza> filtrirajCeneUlaza(String nazivUlaza, LocalDate datum){
		ArrayList<CenaUlaza> rezultati = new ArrayList<CenaUlaza>();
		rezultati.addAll(ceneUlaza);
		
		ArrayList<CenaUlaza> zaObris1 = new ArrayList<CenaUlaza>();
		for(CenaUlaza cu : rezultati) {
			if(!nazivUlaza.equals("") && !(cu.getUlaz().getNaziv().toLowerCase().contains(nazivUlaza.toLowerCase()))) {
				zaObris1.add(cu);
			}
		}
		rezultati.removeAll(zaObris1);
		
		ArrayList<CenaUlaza> zaObris2 = new ArrayList<CenaUlaza>();
		for(CenaUlaza cu : rezultati) {
			if(datum != null && (datum.isBefore(cu.getPocetakVazenja()) || datum.isAfter(cu.getKrajVazenja()))) {
				zaObris2.add(cu);
			}
		}
		rezultati.removeAll(zaObris2);
		
		return rezultati;
	}
	
	public CenaUlaza CenaUlazaSaSifrom(int sifra) {
		for(CenaUlaza c : ceneUlaza) {
			if(c.getSifra()==sifra) {
				return(c);
			}
		}
		return null;
	}
	
	public CenaUlaza cenaZaDatum(Ulaz u , LocalDate datum) {
		for(CenaUlaza cu : ceneUlaza) {
			if(cu.getUlaz() == u) {
				if( (datum.isAfter(cu.getPocetakVazenja()) && datum.isBefore(cu.getKrajVazenja())) || cu.getPocetakVazenja().isEqual(datum) || cu.getKrajVazenja().isEqual(datum))
					return cu;
			}
		}
		return null;
	}
	
	/////gajbe//////
	
	public void dodajGajbu(Gajba g) {
		try {         
			SqliteGajba.getInstance().upisiGajbu(g);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri upisu gajbe!");
			a.show();
			return;
		}
		gajbe.add(g);
	}

	public void izmeniGajbu(Gajba stara, Gajba nova) {	
		try {           
			SqliteGajba.getInstance().obrisGajbu(stara);
			SqliteGajba.getInstance().upisiGajbu(nova);
		} catch (SQLException e) {
			Alert a = new Alert(AlertType.ERROR, "Greska pri izmeni gajbe!");
			a.show();
			return;
		}
		gajbe.set(gajbe.indexOf(stara),nova);
		
		for(UnosGajbi ug : unosiGajbi) {
			if(ug.getGajba().equals(stara)) {
				UnosGajbi ugNovi  = ug;
				ugNovi.setGajba(nova);
				izmeniUnosGajbi(ug, ugNovi);
			}
		}
		
		for(UnosUlaza uu : unosiUlaza) {
			if(uu.getGajba().equals(stara)) {
				UnosUlaza uuNovi = uu;
				uuNovi.setGajba(nova);
				uuNovi.setKolicinaNeto(uuNovi.getKolicinaBruto()-uuNovi.getGajbe()*nova.getTezina());
				izmeniUnosUlaza(uu, uuNovi);
			}
		}
	}

	public void obrisiGajbu(Gajba g) {	
		try {
			SqliteGajba.getInstance().obrisGajbu(g);
		} catch (SQLException e) {
			Alert ab = new Alert(AlertType.ERROR, "Greska pri brisanju gajbe!");
			ab.show();
			return;
		}
		gajbe.remove(gajbe.indexOf(g));

		for(UnosUlaza uu : unosiUlaza)
			if(uu.getGajba().equals(g))
				uu.setGajba(null);
		
		ArrayList<UnosGajbi> zaBrisanje1 = new ArrayList<UnosGajbi>();
		for(UnosGajbi ug : unosiGajbi)
			if(ug.getGajba().equals(g))
				zaBrisanje1.add(ug);
		
		for(UnosGajbi ug : zaBrisanje1)
			obrisiUnosGajbi(ug);
		
	}
	
	public ArrayList<Gajba> filtrirajGajbe (String termin) {
		ArrayList<Gajba> rezultati = new ArrayList<Gajba>();
		rezultati.addAll(gajbe);
		
		ArrayList<Gajba> zaObris = new ArrayList<Gajba>();
		for(Gajba g : rezultati) {
			if(!g.getNaziv().toLowerCase().contains(termin.toLowerCase())) {
				zaObris.add(g);
			}
		}
		rezultati.removeAll(zaObris);		
		return rezultati;
	}
	
	public void sortirajGajbe() {
		Collections.sort(gajbe);
	}
	
	public ArrayList<Gajba> getGajbe() {
		return gajbe;
	}
	
	public Gajba GajbaSaSifrom(int sifra) {
		for(Gajba g: gajbe) {
			if(g.getSifra() == sifra) {
				return g;
			}
		}
		return null;
	}
	
	 ////godine///
	
	public int getGodina() {
		return godina;
	}
	
	
}


