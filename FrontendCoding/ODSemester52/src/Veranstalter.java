import java.io.Serializable;
import java.util.List;

/**
 * @author kersting
 * Klasse zur Verwaltung der Veranstalter
 */
public class Veranstalter implements Serializable{
	private int veranstalterID;
	private String veranstalterName;
	private List<Veranstaltung> veranstaltungen;
	
	//Standard Konstruktor
	public Veranstalter() {
		super();
	}
	
	//Standard Getter und Setter
	public int getVeranstalterID() {
		return veranstalterID;
	}
	public void setVeranstalterID(int veranstalterID) {
		this.veranstalterID = veranstalterID;
	}
	public String getName() {
		return veranstalterName;
	}
	public void setVeranstalterName(String veranstalterName) {
		this.veranstalterName = veranstalterName;
	}
	public List<Veranstaltung> getVeranstaltungen() {
		return veranstaltungen;
	}
	public void deleteVeranstaltung(Veranstaltung selectedVeranstaltung) {
		veranstaltungen.remove(selectedVeranstaltung);
	}

	//Als toString wird nur der Name zurückgegeben
	public String toString() {
		return veranstalterName;
	}

}
