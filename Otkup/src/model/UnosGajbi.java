package model;

import java.time.LocalDate;

public class UnosGajbi implements Comparable<UnosGajbi>{

	private int sifra;
	private LocalDate datum;
	private Proizvodjac proizvodjac;
	private Prevoznik prevoznik;
	private Gajba gajba;
	private int gajbeUlaz;
	private int gajbeIzlaz;	
	private String saldo;
	
	public UnosGajbi(int sifra, LocalDate datum, Proizvodjac proizvodjac, Prevoznik prevoznik, Gajba gajba,
			int gajbeUlaz, int gajbeIzlaz) {
		super();
		this.sifra = sifra;
		this.datum = datum;
		this.proizvodjac = proizvodjac;
		this.prevoznik = prevoznik;
		this.gajba = gajba;
		this.gajbeUlaz = gajbeUlaz;
		this.gajbeIzlaz = gajbeIzlaz;
		int s = gajbeIzlaz-gajbeUlaz;
		if(s>0)
			this.saldo = "+" + String.valueOf(s);
		if(s<=0)
			this.saldo = String.valueOf(s);
	}

	@Override
	public String toString() {
		return "UnosGajbi [sifra=" + sifra + ", datum=" + datum + ", proizvodjac=" + proizvodjac + ", prevoznik="
				+ prevoznik + ", gajba=" + gajba + ", gajbeUlaz=" + gajbeUlaz + ", gajbeIzlaz=" + gajbeIzlaz + "]";
	}

	@Override
	public int compareTo(UnosGajbi ug) {
		if(this.sifra > ug.sifra ) {
			return 1;
		}else if(this.sifra < ug.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof UnosGajbi) {
			UnosGajbi ug = (UnosGajbi)obj;
			return (this.sifra == ug.sifra);
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

	public Prevoznik getPrevoznik() {
		return prevoznik;
	}

	public void setPrevoznik(Prevoznik prevoznik) {
		this.prevoznik = prevoznik;
	}

	public int getGajbeUlaz() {
		return gajbeUlaz;
	}

	public void setGajbeUlaz(int gajbeUlaz) {
		this.gajbeUlaz = gajbeUlaz;
	}

	public int getGajbeIzlaz() {
		return gajbeIzlaz;
	}

	public void setGajbeIzlaz(int gajbeIzlaz) {
		this.gajbeIzlaz = gajbeIzlaz;
	}
	
	public void setGajba(Gajba gajba) {
		this.gajba = gajba;
	}
	
	public Gajba getGajba() {
		return gajba;
	}
	
	public String getSaldo() {
		return saldo;
	}
	
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
}
