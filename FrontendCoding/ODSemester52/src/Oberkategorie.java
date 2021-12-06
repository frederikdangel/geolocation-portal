import java.io.Serializable;
import java.util.List;

/**
 * @author kersting
 * Klasse zur Verwaltung der Oberkategorien
 */
public class Oberkategorie implements Serializable{
	private int oberkategorieid;
	private String oberkategoriename;
	private List<Unterkategorie> unterkategorien;
	
	//Standard Konstruktor
	public Oberkategorie() {
		super();
	}
	
	//Methode zur Ermittlung eines Unterkategorie Objekts aus einem Unterkategorie String
	public Unterkategorie getUnterkategorieByName(String unterkategorie) {
		//Dafür wird die Liste der zu dieser Oberkategorie gehörigen Unterkategorien durchgegangen
		//und bei einem Treffer das entsprechende Objekt zurückgegeben
		for (Unterkategorie unterkategori : unterkategorien) {
			if (unterkategori.getUnterkategoriename().equals(unterkategorie)) {
				return unterkategori;
			}
		}
		return null;
	}

	//Standard Getter und Setter
	public int getOberkategorieid() {
		return oberkategorieid;
	}
	public void setOberkategorieid(int oberkategorieid) {
		this.oberkategorieid = oberkategorieid;
	}
	public String getOberkategoriename() {
		return oberkategoriename;
	}
	public void setOberkategoriename(String oberkategoriename) {
		this.oberkategoriename = oberkategoriename;
	}
	public List<Unterkategorie> getUnterkategorien() {
		return unterkategorien;
	}
	public void setUnterkategorien(List<Unterkategorie> unterkategorien) {
		this.unterkategorien = unterkategorien;
	}
	
	//Als toString wird nur der Name zurückgegeben
	public String toString() {
		return oberkategoriename;
	}
}