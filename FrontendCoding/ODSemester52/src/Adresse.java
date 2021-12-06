import java.io.Serializable;

/**
 * @author kersting
 *Klasse zur Verwaltung der Adressen. Diese sind Teil einer Veranstaltung
 */
public class Adresse implements Serializable {
	private int adressid;
	private double laengengrad;
	private double breitengrad;
	private int hausnummer;
	private String stra�e;
	private String ort;
	
	//Standard Konstruktor
	public Adresse() {
		super();
	}
	
	//Standard Getter und Setter
	public int getAdressid() {
		return adressid;
	}
	public void setAdressid(int adressid) {
		this.adressid = adressid;
	}
	public double getLaengengrad() {
		return laengengrad;
	}
	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}
	public double getBreitengrad() {
		return breitengrad;
	}
	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getStra�e() {
		return stra�e;
	}
	public void setStra�e(String stra�e) {
		this.stra�e = stra�e;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
}
