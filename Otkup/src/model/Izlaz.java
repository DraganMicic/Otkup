package model;

public class Izlaz implements Comparable<Izlaz>{
	
	private int sifra;
	private String naziv;
	private String opis;
	private String jedinicaMere;
	private double cenaPoKomadu;
	
	public Izlaz(int sifra, String naziv, String opis, String jedinicaMere, Double cenaPoKomadu) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.opis = opis;
		this.jedinicaMere = jedinicaMere;
		this.cenaPoKomadu = cenaPoKomadu;
	}

	@Override
	public String toString() {
		String ss = new String();
		if(opis.equals(""))
			ss = naziv;
		else 
			ss = naziv + " (" + opis +")";
		return ss;
	}

	@Override
	public int compareTo(Izlaz o) {
		if(this.sifra > o.sifra) {
			return 1;
		}else if(this.sifra < o.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Izlaz) {
			Izlaz u = (Izlaz)obj;
			return (this.sifra == u.sifra);
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void String(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public double getCenaPoKomadu() {
		return cenaPoKomadu;
	}

	public void setCenaPoKomadu(int cenaPoKomadu) {
		this.cenaPoKomadu = cenaPoKomadu;
	}
	
}
