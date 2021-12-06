import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.UploadedFile;

/**
 * @author kersting
 * Bean zur Userverwaltung. SessionScoped, damit ein User über seine gesamte Session eingeloggt
 */
@ManagedBean
@SessionScoped
public class UserBean {
	private User user = new User();
	private String password2;
	private boolean loggedIn = false;
	private boolean bearbeiten = false;
	private List<String> selectedVeranstalter;
	private List<Veranstaltung> userVeranstaltungen;
	private Veranstaltung selectedVeranstaltung;
	private String veranstalterZurVerifizierung;
	private UploadedFile persoFile;

	//User Login, Methode wird aufgerufen, sobald der User Login anklickt
	public void login() {
		FacesMessage message = null;
		//Daten werden ans Backend zur Prüfung weitergegeben
		user = BackendConnector.login(user);

		if(user != null) {
			//Wenn richtig ist der User eingeloggt und bekommt eine Erfolgsmeldung
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Willkommen", user.getEmail());
		} else {
			//Wenn nicht eine Fehlermeldung
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Falsche Login Daten");
			user = new User();
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
		user.setPw(null);
		password2 = null;

		//Im Benutzerprofil werden alle Verantaltungen von zugeordnete Veranstaltern angezeigt. 
		//Diese können nach Veranstalter gefiltert werden
		//als Default alle Veranstalter setzen
		selectedVeranstalter= new ArrayList<String>();
		if (user.getVeranstaltendeUser() != null && user.getVeranstaltendeUser().size() > 0) {
			for (Veranstalter veranstalter : user.getVeranstaltendeUser()) {
				selectedVeranstalter.add(veranstalter.toString());
			}
			//Die Methode füllt der Veranstaltungsliste für das Benutzerprofil, 
			//abhängig von den ausgewählten Veranstaltungen
			onVeranstalterChange();
		}
	}   

	//Diese Methode wird aufgerufen, wenn der User auf Logout klickt
	public void logout() {
		//loggedIn, sorgt dafür, dass auf der Oberfläche alles entsprechend behandelt wird, der User wird zurückgesetzt
		loggedIn = false;
		user = new User();
		try {
			//Weiterleitung auf die Startseite
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(context.getRequestContextPath() + "/Startseite.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Diese Methode wird aufgerufen von der Registrieren Seite, wenn der User auf Registrieren klickt
	public void registrieren() {
		//Zuerst werden alle Eingaben geprüft und wenn nötig eine Fehlermeldung angezeigt
		if (user.getEmail() == null || !user.getEmail().contains("@")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email fehlt oder falsch", "Bitte eine gültige E-Mail eingeben"));
		} else if (user.getPw() == null || user.getPw().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwort fehlt", "Bitte eine Passwort eingeben"));
		} else if (!user.getPw().equals(password2)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwort", "Passwort bestätigen entspricht nicht dem Passwort"));
		} else {
			//Der User wird im Backend registriert
			user = BackendConnector.registriereNeuenUser(user);
			if (user != null) {
				//War das erfolgreich wird er eingeloggt und auf die Startseite weitergeleitet
				user.setPw(null);
				loggedIn = true;
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				try {
					context.redirect(context.getRequestContextPath() + "/Startseite.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				//Wenn nicht, ist die E-Mail schon vergeben und der Benutzer erhäkt eine entsprechende Meldung
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "E-Mail vergeben", "Für die angegebene E-Mail existiert bereits ein Account"));
			}
		}
		password2 = null;
	}

	//Diese Methode wird aufgerufen, wenn der User im Benutzerprofil seine Daten ändert und dann speichert
	public void userSpeichern() {
		//Zuerst werden alle Eingaben geprüft und wenn nötig eine Fehlermeldung angezeigt
		if (user.getEmail() == null || !user.getEmail().contains("@")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Email fehlt oder falsch", "Bitte eine gültige E-Mail eingeben"));
		} else if (user.getPw() != null && user.getPw() != null && !user.getPw().equals(password2)) {
			//Fehler Passwort eingegeben, aber Passwort nicht gleich Passwort bestätigen
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwort", "Passwort bestätigen entspricht nicht dem Passwort"));
		} else {
			//Waren alle Eingabe korrekt werden die Änderungen im Backend gespeichert
			user = BackendConnector.updateUser(user);
			//Und der User bekommt eine Erfolgsmeldung
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert","Änderungen erfolgreich gespeichert"));
			bearbeiten = false;
		}
	}

	//Diese Methode wird aufgerufen, wenn der User bei eine Veranstaltung 
	//aus der Liste eigene Veranstaltungen auf löschen klickt
	public void veranstaltungLoeschen() {
		//Diese wird im Backend gelöscht
		BackendConnector.veranstaltungLoeschen(selectedVeranstaltung);
		List<Veranstalter> veranstalter = user.getVeranstaltendeUser();
		for (Veranstalter v : veranstalter) {
			if (v.getVeranstaltungen().contains(selectedVeranstaltung)) {
				//Und dann auch lokal, um die Veranstaltung direkt auch im Profil nicht mehr anzuzeigen
				v.deleteVeranstaltung(selectedVeranstaltung);
			}
		}
	}

	//Diese Methode wird aufgerufen, wenn der User im PopUp zum Verifizieren 
	//als Veranstalter auf den Bestätigungs Button geklickt hat
	@SuppressWarnings("deprecation")
	public void veranstalterVerifizierung() {
		InputStream input = null;
		OutputStream output = null;

		try {
			//Das hochgeladene Bild es Personalausweises wird in ein lokales Verzeichnis kopiert. Ein relativer Pfad 
			//funktionierte leider nicht. In Zukunft sollten Bilder direkt in der Datenbank gespeichert werden
			input = persoFile.getInputstream();
			output = new FileOutputStream(new File("C:\\Users\\kersting\\git\\ODSemester52\\WebContent\\Images\\Personalausweis\\", persoFile.getFileName()));
			IOUtils.copy(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}

		//Die Verifizierungsanfrage wird dann ans Backend weitergeleitet
		BackendConnector.saveVerifizierungsanfrage(user.getUserID(),veranstalterZurVerifizierung,"Personalausweis/" + persoFile.getFileName());
	}

	//Diese Methode wird aufgerufen, wenn der User im Benutzerprofil im 
	//Dropdown bei Eigene Veranstaltungen die Auswahl der Veranstalter ändert
	public void onVeranstalterChange() {
		//Dann wir die Liste der Veranstaltungen neu befüllt um diese nachher anzuzeigen
		userVeranstaltungen = new ArrayList<Veranstaltung>();
		for (String string : selectedVeranstalter) {
			userVeranstaltungen.addAll(BackendConnector.getVeranstalterByName(string).getVeranstaltungen());
		}
	}

	//Diese Methode wird aufgerufen, wenn der User auf Profildaten bearbeiten klickt.
	public void changeBearbeiten() {
		//Vom boolean bearbeiten hängt ab, ob die Eingabefelder deaktiviert sind
		bearbeiten = !bearbeiten;
	}


	//Getter und Setter

	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isBearbeiten() {
		return bearbeiten;
	}
	public void setBearbeiten(boolean bearbeiten) {
		this.bearbeiten = bearbeiten;
	}
	public Veranstaltung getSelectedVeranstaltung() {
		return selectedVeranstaltung;
	}
	public void setSelectedVeranstaltung(Veranstaltung selectedVeranstaltung) {
		this.selectedVeranstaltung = selectedVeranstaltung;
	}
	public String getVeranstalterZurVerifizierung() {
		return veranstalterZurVerifizierung;
	}
	public void setVeranstalterZurVerifizierung(String veranstalterZurVerifizierung) {
		this.veranstalterZurVerifizierung = veranstalterZurVerifizierung;
	}
	public UploadedFile getPersoFile() {
		return persoFile;
	}
	public void setPersoFile(UploadedFile persoFile) {
		this.persoFile = persoFile;
	}
	public List<String> getSelectedVeranstalter() {
		return selectedVeranstalter;
	}
	public void setSelectedVeranstalter(List<String> selectedVeranstalter) {
		this.selectedVeranstalter = selectedVeranstalter;
	}
	public List<Veranstaltung> getUserVeranstaltungen() {
		return userVeranstaltungen;
	}
	public void setUserVeranstaltungen(List<Veranstaltung> userVeranstaltungen) {
		this.userVeranstaltungen = userVeranstaltungen;
	}
}
