package model;

import java.time.LocalDate;

public class CenaUlaza implements Comparable<CenaUlaza> {
	
	private int sifra;
	private Ulaz ulaz;
	private double cenaBezPrevoza;
	private double cenaSaPrevozom;
	private LocalDate pocetakVazenja;
	private LocalDate krajVazenja;

	public CenaUlaza(int sifra, Ulaz ulaz, double cenaBezPrevoza, double cenaSaPrevozom, LocalDate pocetakVazenja,
			LocalDate krajVazenja) {
		super();
		this.sifra = sifra;
		this.ulaz = ulaz;
		this.cenaBezPrevoza = cenaBezPrevoza;
		this.cenaSaPrevozom = cenaSaPrevozom;
		this.pocetakVazenja = pocetakVazenja;
		this.krajVazenja = krajVazenja;
	}
	
	@Override
	public String toString() {
		return "Cena [sifra=" + sifra + ", ulaz=" + ulaz + ", cenaBezPrevoza=" + cenaBezPrevoza + ", cenaSaPrevozom="
				+ cenaSaPrevozom + ", pocetakVazenja=" + pocetakVazenja + ", krajVazenja=" + krajVazenja + "]";
	}

	@Override
	public int compareTo(CenaUlaza o) {
		if(this.sifra > o.sifra) {
			return 1;
		}else if(this.sifra < o.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof CenaUlaza) {
			CenaUlaza c = (CenaUlaza)obj;
			return(this.sifra == c.sifra);
		}
		return false;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public Ulaz getUlaz() {
		return ulaz;
	}

	public void setUlaz(Ulaz ulaz) {
		this.ulaz = ulaz;
	}

	public double getCenaBezPrevoza() {
		return cenaBezPrevoza;
	}

	public void setCenaBezPrevoza(double cenaBezPrevoza) {
		this.cenaBezPrevoza = cenaBezPrevoza;
	}

	public double getCenaSaPrevozom() {
		return cenaSaPrevozom;
	}

	public void setCenaSaPrevozom(double cenaSaPrevozom) {
		this.cenaSaPrevozom = cenaSaPrevozom;
	}

	public LocalDate getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(LocalDate pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}

	public LocalDate getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(LocalDate krajVazenja) {
		this.krajVazenja = krajVazenja;
	}

}
