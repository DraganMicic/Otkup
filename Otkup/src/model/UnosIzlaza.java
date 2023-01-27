package model;

import java.time.LocalDate;

public class UnosIzlaza implements Comparable<UnosIzlaza> {

	private int sifra;
	private LocalDate datum;
	private Proizvodjac proizvodjac;
	private Izlaz izlaz;
	private String brojOtpremnice;
	private double kolicina;
		
	public UnosIzlaza(int sifra, LocalDate d, Proizvodjac proizvodjac, Izlaz izlaz,String brojOtpremnice, double kolicina) {
		super();
		this.sifra = sifra;
		this.datum = d;
		this.proizvodjac = proizvodjac;
		this.izlaz = izlaz;
		this.brojOtpremnice = brojOtpremnice;
		this.kolicina = kolicina;
	}
	
	@Override
	public String toString() {
		return "UnosIzlaza [sifra=" + sifra + ", datum=" + datum + ", proizvodjac=" + proizvodjac + ", izlaz=" + izlaz
				+ ", brojOtpremnice=" + brojOtpremnice + ", kolicina=" + kolicina + "]";
	}

	@Override
	public int compareTo(UnosIzlaza o) {
		if(this.sifra > o.sifra) {
			return 1;
		}else if(this.sifra < o.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof UnosIzlaza) {
			UnosIzlaza ui = (UnosIzlaza)obj;
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

	public Izlaz getIzlaz() {
		return izlaz;
	}

	public void setIzlaz(Izlaz izlaz) {
		this.izlaz = izlaz;
	}

	public double getKolicina() {
		return kolicina;
	}

	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}
	
	public String getBrojOtpremnice() {
		return brojOtpremnice;
	}
	
	public void setBrojOtpremnice(String brojOtpremnice) {
		this.brojOtpremnice = brojOtpremnice;
	}
}
