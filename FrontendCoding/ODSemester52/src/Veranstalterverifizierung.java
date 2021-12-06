import java.io.Serializable;

/**
 * @author kersting
 * Klasse zur Verwaltung der Veranstalterverifizierungen
 */
public class Veranstalterverifizierung implements Serializable{
	private int userid;
	private String veranstalter;
	private String persoablage;
	private User user;
	private String selectedVeranstalter;
	
	//Standard Konstruktor
	public Veranstalterverifizierung() {
		super();
	}

	//Standard Getter und Setter
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
		//Wenn die UserID gesetzt wird, soll direkt das dazugehörige 
		//User Objekt ermittelt und gesetzt werden
		user = BackendConnector.getUserByID(userid);
	}
	public String getVeranstalter() {
		return veranstalter;
	}
	public void setVeranstalter(String veranstalter) {
		this.veranstalter = veranstalter;
	}
	public String getPersoablage() {
		return persoablage;
	}
	public void setPersoablage(String persoablage) {
		this.persoablage = persoablage;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSelectedVeranstalter() {
		return selectedVeranstalter;
	}
	public void setSelectedVeranstalter(String selectedVeranstalter) {
		this.selectedVeranstalter = selectedVeranstalter;
	}
}
