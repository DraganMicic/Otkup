package model;

import java.time.LocalDate;

public class Prevoz implements Comparable<Prevoz> {
	
	private LocalDate datum;
	private Prevoznik prevoznik;
	private double kolicna;
	private Proizvodjac proizvodjac;
	private int sifraUnosaUlaza;
	
	
	public Prevoz( LocalDate datum, Prevoznik prevoznik, double kolicna, Proizvodjac proizvodjac, int sifraUnosaUlaza) {
		super();
		this.datum = datum;
		this.prevoznik = prevoznik;
		this.kolicna = kolicna;
		this.proizvodjac = proizvodjac;
		this.sifraUnosaUlaza = sifraUnosaUlaza;
	}

	@Override
	public String toString() {
		return "Prevoz [datum=" + datum + ", prevoznik=" + prevoznik + ", kolicna=" + kolicna
				+ ", proizvodjac=" + proizvodjac + "]";
	}

	@Override
	public int compareTo(Prevoz pr) {
		if(this.sifraUnosaUlaza > pr.sifraUnosaUlaza) {
			return 1;
		}else if(this.sifraUnosaUlaza < pr.sifraUnosaUlaza) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Prevoz) {
			Prevoz pr = (Prevoz)obj;
			return (this.sifraUnosaUlaza == pr.sifraUnosaUlaza);
		}
		return false;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Prevoznik getPrevoznik() {
		return prevoznik;
	}

	public void setPrevoznik(Prevoznik prevoznik) {
		this.prevoznik = prevoznik;
	}

	public double getKolicna() {
		return kolicna;
	}

	public void setKolicna(double kolicna) {
		this.kolicna = kolicna;
	}

	public Proizvodjac getProizvodjac() {
		return proizvodjac;
	}

	public void setProizvodjac(Proizvodjac proizvodjac) {
		this.proizvodjac = proizvodjac;
	}

	public int getSifraUnosaUlaza() {
		return sifraUnosaUlaza;
	}

	public void setSifraUnosaUlaza(int sifraUnosaUlaza) {
		this.sifraUnosaUlaza = sifraUnosaUlaza;
	}
	
	
	
}
