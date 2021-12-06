import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 * @author kersting
 * Bean zur Verwaltung der Sachbearbeiteroberfläche
 */
@ManagedBean(name="sBean")
@ViewScoped
public class SachbearbeiterBean implements Serializable {
	//Attribute zur Veranstalterverifizierung
	private List<Veranstalterverifizierung> verifizierungen;
	private Veranstalterverifizierung selectedVerifizierung = new Veranstalterverifizierung();
	private List<Veranstalter> moeglVeranstalter;
	private boolean neuerVeranstalter = false;
	
	//Attribute zur Veranstaltungsfreigabe
	private List<Veranstaltung> offeneVeranstaltungen;
	private Veranstaltung selectedVeranstaltung;
	private String position = "49.346739, 9.134492";
	private MapModel model = new DefaultMapModel();
	
	//Attribute Userverwaltung
	private List<User> alleUser;

	public SachbearbeiterBean() {
		//Wird die Sachbearbeiteroberfläche aufgerufen werden die Offenen Verifizierungen 
		//und offenen Veranstaltungen ermittelt, sowie eine Liste aller Veranstalter
		verifizierungen = BackendConnector.getOffeneVerifizierungen();
		moeglVeranstalter = BackendConnector.getAlleVeranstalter();
		offeneVeranstaltungen = BackendConnector.getVeranstaltungenZurFreigabe();
		alleUser = BackendConnector.getAlleUser();
	}
	
	//Klickt der Sachbearbeiter bei einer Verifizierung auf Ablehnen, wird diese Methode aufgerufen
	public void verifizierungAblehnen() {
		//Die Verifizierungsabfrage wird dann gelöscht
		BackendConnector.deleteOffeneVerifizierung(selectedVerifizierung);
		//Und die Liste der offenen Verifizierungen neu gefüllt
		verifizierungen = BackendConnector.getOffeneVerifizierungen();
	}
	
	//Klickt der Sachbearbeiter bei einer Verifizierung auf Bestätigen, wird diese Methode aufgerufen
	public void verifizierungBestaetigen() {
		//Dann wird im Backend je nach Eingabe ein Veranstalter angelegt 
		//und die Verifizierung in eine Beziehung User <-> Veranstalter umgewandelt
		BackendConnector.bestaetigeVerifizierung(selectedVerifizierung, neuerVeranstalter);
		//Und die Liste der offenen Verifizierungen neu gefüllt
		verifizierungen = BackendConnector.getOffeneVerifizierungen();
	}

	//Klickt der Sachbearbeiter bei einer offenen Veranstaltung auf Ablehnen, wird diese Methode aufgerufen
	public void veranstaltungAblehnen() {
		//Die Veranstaltung wird dann gelöscht
		BackendConnector.veranstaltungLoeschen(selectedVeranstaltung);
		//Und die Liste der freizugebenden Veranstaltungen neu gefüllt
		offeneVeranstaltungen = BackendConnector.getVeranstaltungenZurFreigabe();
	}
	
	//Klickt der Sachbearbeiter bei einer Veranstaltung auf Freigeben, wird diese Methode aufgerufen
	public void veranstaltungFreigeben() {
		//Die Veranstaltung wird dann im Backend als freigegeben gekennzeichnet
		BackendConnector.veranstaltungFreigeben(selectedVeranstaltung.getVeranstaltungsID());
		//Und die Liste der freizugebenden Veranstaltungen neu gefüllt
		offeneVeranstaltungen = BackendConnector.getVeranstaltungenZurFreigabe();
	}
	
	//Klickt der Sachbearbeiter bei einer Veranstaltung auf Karte anzeigen, wird diese Methode aufgerufen
	public void setMapParameters() {
		//Auf der Oberfläche erscheint dann ein PopUp mit einer Karte, 
		//auf die hier ein Marker mit dem Veranstaltungsort gesetzt wird:
		position = selectedVeranstaltung.getAdresse().getBreitengrad() + ", " + selectedVeranstaltung.getAdresse().getLaengengrad();
		Marker m = new Marker(new LatLng(selectedVeranstaltung.getAdresse().getBreitengrad(), selectedVeranstaltung.getAdresse().getLaengengrad()));
		model = new DefaultMapModel();
		model.addOverlay(m);
	}
	
	
	//Getter und Setter:
	
	public List<Veranstalterverifizierung> getVerifizierungen() {
		return verifizierungen;
	}
	public void setVerifizierungen(List<Veranstalterverifizierung> verifizierungen) {
		this.verifizierungen = verifizierungen;
	}
	public Veranstalterverifizierung getSelectedVerifizierung() {
		return selectedVerifizierung;
	}
	public void setSelectedVerifizierung(Veranstalterverifizierung selectedVerifizierung) {
		this.selectedVerifizierung = selectedVerifizierung;
	}
	public List<Veranstalter> getMoeglVeranstalter() {
		return moeglVeranstalter;
	}
	public void setMoeglVeranstalter(List<Veranstalter> moeglVeranstalter) {
		this.moeglVeranstalter = moeglVeranstalter;
	}
	public boolean isNeuerVeranstalter() {
		return neuerVeranstalter;
	}
	public void setNeuerVeranstalter(boolean neuerVeranstalter) {
		this.neuerVeranstalter = neuerVeranstalter;
		if (neuerVeranstalter) {
			//Wenn der Sachbearbeiter den Radiobutton für neuen Veranstalter anlegen wählt wird
			//das Eingabefeld für den Namen mit dem von User angegebenen Veranstalter vorbefüllt
			selectedVerifizierung.setSelectedVeranstalter(selectedVerifizierung.getVeranstalter());
		}else {
			selectedVerifizierung.setSelectedVeranstalter(null);
		}
	}
	public List<Veranstaltung> getOffeneVeranstaltungen() {
		return offeneVeranstaltungen;
	}
	public void setOffeneVeranstaltungen(List<Veranstaltung> offeneVeranstaltungen) {
		this.offeneVeranstaltungen = offeneVeranstaltungen;
	}
	public Veranstaltung getSelectedVeranstaltung() {
		return selectedVeranstaltung;
	}
	public void setSelectedVeranstaltung(Veranstaltung selectedVeranstaltung) {
		this.selectedVeranstaltung = selectedVeranstaltung;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public MapModel getModel() {
		return model;
	}
	public void setModel(MapModel model) {
		this.model = model;
	}

	public List<User> getAlleUser() {
		return alleUser;
	}

	public void setAlleUser(List<User> alleUser) {
		this.alleUser = alleUser;
	}
}
