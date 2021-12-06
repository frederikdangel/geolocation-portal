import java.io.Serializable;
import java.util.List;

/**
 * @author kersting
 * Klasse zur Verwaltung der User
 */
public class User implements Serializable {
	private int userID;
	private String email;
	private String vorname;
	private String nachname;
	private String pw;
	private boolean istAngestellter;
	private List<Veranstalter> veranstaltendeUser;
	private boolean istVeranstalter = false;
	private boolean hatnews = false;

	//Standard Konstruktor
	public User() {
		super();
	}

	//Standard Getter und Setter
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public boolean isIstAngestellter() {
		return istAngestellter;
	}
	public void setIstAngestellter(boolean istAngestellter) {
		this.istAngestellter = istAngestellter;
	}
	public List<Veranstalter> getVeranstaltendeUser() {
		return veranstaltendeUser;
	}
	public boolean isHatnews() {
		return hatnews;
	}
	public void setHatnews(boolean hatnews) {
		this.hatnews= hatnews;
	}
	public boolean isIstVeranstalter() {
		return istVeranstalter;
	}
	public void setVeranstaltendeUser(List<Veranstalter> veranstaltendeUser) {
		//Wenn die Liste der zugeordneten Verantstalter gesetzt wird und mind. einen Veranstalter 
		//enthält, wird das Attribut istVeranstalter auf true gesetzt
		this.veranstaltendeUser = veranstaltendeUser;
		if (veranstaltendeUser == null || veranstaltendeUser.size() == 0) {
			istVeranstalter = false;
		}else {
			istVeranstalter = true;
		}
	}
}
