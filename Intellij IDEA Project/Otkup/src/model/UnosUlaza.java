package model;

import java.time.LocalDate;

public class UnosUlaza implements Comparable<UnosUlaza>{
	
	private int sifra;
	private LocalDate datum;
	private Proizvodjac proizvodjac;
	private Ulaz ulaz;
	private UnosGajbi unosGajbi;
	private Gajba gajba;
	private double kolicinaBruto;
	private double kolicinaNeto;
	private int gajbe;
	private Prevoznik prevoznik;

	public UnosUlaza(int sifra, LocalDate datum, Proizvodjac proizvodjac, Ulaz ulaz, UnosGajbi unosGajbi, Gajba gajba,
			double kolicinaBruto, double kolicinaNeto, int gajbe, Prevoznik prevoznik) {
		super();
		this.sifra = sifra;
		this.datum = datum;
		this.proizvodjac = proizvodjac;
		this.ulaz = ulaz;
		this.unosGajbi = unosGajbi;
		this.gajba = gajba;
		this.kolicinaBruto = kolicinaBruto;
		this.kolicinaNeto = kolicinaNeto;
		this.gajbe = gajbe;
		this.prevoznik = prevoznik;
	}

	@Override
	public String toString() {
		return "UnosUlaza [sifra=" + sifra + ", datum=" + datum + ", proizvodjac=" + proizvodjac + ", ulaz=" + ulaz
				+ ", unosGajbi=" + unosGajbi + ", gajba=" + gajba + ", kolicinaBruto=" + kolicinaBruto
				+ ", kolicinaNeto=" + kolicinaNeto + ", gajbe=" + gajbe + ", prevoznik=" + prevoznik + "]";
	}

	@Override
	public int compareTo(UnosUlaza o) {
		if(this.sifra > o.sifra) {
			return 1;
		}else if(this.sifra < o.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof UnosUlaza) {
			UnosUlaza ui = (UnosUlaza)obj;
			return (this.sifra == ui.sifra);
		}
		return false;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Proizvodjac getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(Proizvodjac proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public Ulaz getUlaz() {
		return ulaz;
	}

	public void setUlaz(Ulaz ulaz) {
		this.ulaz = ulaz;
	}

	public double getKolicinaBruto() {
		return kolicinaBruto;
	}

	public void setKolicinaBruto(double kolicinaBruto) {
		this.kolicinaBruto = kolicinaBruto;
	}

	public double getKolicinaNeto() {
		return kolicinaNeto;
	}

	public void setKolicinaNeto(double kolicinaNeto) {
		this.kolicinaNeto = kolicinaNeto;
	}

	public Prevoznik getPrevoznik() {
		return prevoznik;
	}

	public void setPrevoznik(Prevoznik prevoznik) {
		this.prevoznik = prevoznik;
	}

	public UnosGajbi getUnosGajbi() {
		return unosGajbi;
	}

	public void setUnosGajbi(UnosGajbi unosGajbi) {
		this.unosGajbi = unosGajbi;
	}

	public int getGajbe() {
		return gajbe; 
	}

	public void setGajbe(int gajbe) {
		this.gajbe = gajbe;
	}
	
	public Gajba getGajba() {
		return gajba;
	}
	
	public void setGajba(Gajba gajba) {
		this.gajba = gajba;
	}
	
	
	

}
