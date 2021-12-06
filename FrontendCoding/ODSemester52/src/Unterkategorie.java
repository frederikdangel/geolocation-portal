import java.io.Serializable;

/**
 * @author kersting
 * Klasse zur Verwaltung der Unterkategorien
 */
public class Unterkategorie implements Serializable{
	private int unterkategorieID;
	private String unterkategoriename;
	
	//Standard Konstruktor
	public Unterkategorie() {
		super();
	}
	
	//Standard Getter und Setter
	public int getUnterkategorieID() {
		return unterkategorieID;
	}
	public void setUnterkategorieID(int unterkategorieID) {
		this.unterkategorieID = unterkategorieID;
	}
	public String getUnterkategoriename() {
		return unterkategoriename;
	}
	public void setUnterkategoriename(String unterkategoriename) {
		this.unterkategoriename = unterkategoriename;
	}

	//Als toString wird nur der Name zurückgegeben
	public String toString() {
		return unterkategoriename;
	}
}
