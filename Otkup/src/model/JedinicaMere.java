package model;

public class JedinicaMere {
	
	private String naziv;

	public JedinicaMere(String naziv) {
		super();
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return naziv;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}
