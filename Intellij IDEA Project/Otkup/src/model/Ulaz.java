package model;

public class Ulaz implements Comparable<Ulaz> {
	
	private int sifra;
	private String naziv;
	private String opis;


	public Ulaz(int sifra, String naziv, String opis) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.opis = opis;
	}

	@Override
	public String toString() {
		String ss = new String();
		if(!this.opis.equals(""))
			ss =  naziv + " (" + opis +")";
		else
			ss = naziv;
		return ss;
	}

	@Override
	public int compareTo(Ulaz u) {
		if(this.sifra > u.sifra) {
			return 1;
		}else if(this.sifra < u.sifra) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Ulaz) {
			Ulaz u = (Ulaz)obj;
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
}
