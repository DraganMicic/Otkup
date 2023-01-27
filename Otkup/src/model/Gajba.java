package model;

public class Gajba implements Comparable<Gajba> {
	
	private int sifra;
	private String naziv;
	private double tezina;
	private int ukupnoNaRaspolaganju;
	
	public Gajba(int sifra, String naziv, double tezina, int ukupnoNaRaspolaganju) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.tezina = tezina;
		this.ukupnoNaRaspolaganju = ukupnoNaRaspolaganju;
	}

	@Override
	public String toString() {
		return naziv + " (" + tezina + "kg)";
	}

	@Override
	public int compareTo(Gajba g) {
		if(this.sifra > g.sifra) {
			return 1;
		}else if(this.sifra < g.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Gajba) {
			Gajba g = (Gajba)obj;
			return(this.sifra == g.sifra);
		}
		return false;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public int getUkupnoNaRaspolaganju() {
		return ukupnoNaRaspolaganju;
	}

	public void setUkupnoNaRaspolaganju(int ukupnoNaRaspolaganju) {
		this.ukupnoNaRaspolaganju = ukupnoNaRaspolaganju;
	}

}
