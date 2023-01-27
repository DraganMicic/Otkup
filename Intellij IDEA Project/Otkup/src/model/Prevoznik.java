package model;

public class Prevoznik implements Comparable<Prevoznik> {
	
	private int sifra;
	private String ime;
	private String prezime;
	private String opis;
	private double cenaPoKg;
	
	public Prevoznik(int sifra, String ime, String prezime, String opis, double cenaPoKg) {
		super();
		this.sifra = sifra;
		this.ime = ime;
		this.prezime = prezime;
		this.opis = opis;
		this.cenaPoKg = cenaPoKg;
	}

	@Override
	public String toString() {
		String ss = new String();
		if(cenaPoKg == 0)
			ss = ime;
		else
			ss = ime + " " + prezime + " (" + cenaPoKg + "din/kg)";
		return ss;
	}

	@Override
	public int compareTo(Prevoznik p) {
		if(this.sifra > p.sifra) {
			return 1;
		}else if(this.sifra < p.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Prevoznik) {
			Prevoznik p = (Prevoznik)obj;
			return(this.sifra == p.sifra);
		}
		return false;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getCenaPoKg() {
		return cenaPoKg;
	}

	public void setCenaPoKg(double cenaPoKg) {
		this.cenaPoKg = cenaPoKg;
	}


	
}
