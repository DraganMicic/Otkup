 package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

 public class Proizvodjac implements Comparable<Proizvodjac> {
	
	private int  sifra;
	private String ime;
	private String prezime;
	private String mesto;
	private String maticniBroj;
	private String brojGazdinstva;
	private String telefon;
	private String brojRacuna;
	private String komentar;
	private Prevoznik prevoznik;
	private double cenaPlus;
	
	
	public Proizvodjac(int sifra, String ime, String prezime, String mesto, String maticniBroj, String brojGazdinstva,
			String telefon, String brojRacuna, String komentar, Prevoznik pPrevoznik, double cenaPlus) {
		super();
		this.sifra = sifra;
		this.ime = ime;
		this.prezime = prezime;
		this.mesto = mesto;
		this.maticniBroj = maticniBroj;
		this.brojGazdinstva = brojGazdinstva;
		this.telefon = telefon;
		this.brojRacuna = brojRacuna;
		this.komentar = komentar;
		this.prevoznik = pPrevoznik;
		this.cenaPlus = cenaPlus;
	}
	
	@Override
	public String toString() {
		String ss = new String();
		if(komentar.equals(""))
			ss = ime + " " + prezime;
		else
			ss = ime + " " + prezime + " (" + komentar +")";
		return ss;
	}
	
	@Override
	public int compareTo(Proizvodjac o) {
		if(this.sifra > o.sifra) {
			return 1;
		}else if(this.sifra < o.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Proizvodjac) {
			Proizvodjac p = (Proizvodjac)obj;
			return (this.sifra == p.sifra);
		}
		return false;
		
	}
 	
	public ArrayList<UnosIzlaza> getUnosiIzlza() {
		ArrayList<UnosIzlaza> rez = new ArrayList<UnosIzlaza>();
		for(UnosIzlaza ui : Firma.getInstance().getTrenutnaGodina().getUnosiIzlaza()) {
			if(ui.getProizvodjac().equals(this))
				rez.add(ui);
		}
		return rez;
	}
	
	public ArrayList<UnosUlaza> getUnosiUlaza() {
		ArrayList<UnosUlaza> rez = new ArrayList<UnosUlaza>();
		for (UnosUlaza uu : Firma.getInstance().getTrenutnaGodina().getUnosiUlaza()) {
			if (uu.getProizvodjac().equals(this))
				rez.add(uu);
		}
		return rez;
	}

	public double ukupnoUlazaNaDan (Ulaz u, LocalDate dan){
		double ukupno = 0.0;
		for(UnosUlaza uu: this.getUnosiUlaza()){
			if(uu.getUlaz().equals(u) && (uu.getDatum().isBefore(dan) || uu.getDatum().isEqual(dan))){
				ukupno+= uu.getKolicinaNeto();
			}
		}
		return ukupno;
	}
	
	public double ukupnoIzlaza() {
		double rez = 0.0;
		for(UnosIzlaza ui : this.getUnosiIzlza()) {
			rez += ui.getKolicina() * ui.getIzlaz().getCenaPoKomadu();
		}
		return rez;
	}
	
	public double ukupnoUlaza() {
		double rez = 0.0;
		for(UnosUlaza u : this.getUnosiUlaza()) {				
			if(u.getPrevoznik().equals(Firma.getInstance().getTrenutnaGodina().getLicno())) {
				rez += (u.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(u.getUlaz(), u.getDatum()).getCenaSaPrevozom() + cenaPlus));
			}else { 
				rez += (u.getKolicinaNeto() * (Firma.getInstance().getTrenutnaGodina().cenaZaDatum(u.getUlaz(), u.getDatum()).getCenaBezPrevoza() + cenaPlus));
			}	
		}
		return rez;
	}
	
	public double ukupnoKg() {
		double rez = 0.0;
		for(UnosUlaza u : this.getUnosiUlaza()) {					
			rez += u.getKolicinaNeto();
		}
		return rez;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getMaticniBroj() {
		return maticniBroj;
	}

	public void setMaticniBroj(String maticniBroj) {
		this.maticniBroj = maticniBroj;
	}

	public String getBrojGazdinstva() {
		return brojGazdinstva;
	}

	public void setBrojGazdinstva(String brojGazdinstva) {
		this.brojGazdinstva = brojGazdinstva;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}
	
	public String getKomentar() {
		return komentar;
	}
	
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	
	public Prevoznik getPrevoznik() {
		return prevoznik;
	}
	
	public void setPrevoznik(Prevoznik pPrevoznik) {
		this.prevoznik = pPrevoznik;
	}
	
	public double getCenaPlus() {
		if(cenaPlus == 0)
			return 0;
		return cenaPlus;
	}
	
	public void setCenaPlus(double cenaPlus) {
		this.cenaPlus = cenaPlus;
	}

}
