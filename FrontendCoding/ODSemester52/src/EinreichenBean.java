import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import org.primefaces.model.UploadedFile;

/**
 * @author kersting
 * Bean für die "Veranstaltung einreichen"-Seite
 * Diese ist ViewScoped
 */
@ManagedBean
@ViewScoped
public class EinreichenBean implements Serializable{
	//Die meisten Eingabeparameter werden direkt in ein Veranstaltungsobjekt geschrieben
	private Veranstaltung veranstaltung = new Veranstaltung();
	//Alle weiteren werden zuerst im Bean verwaltet
	private List<Oberkategorie> moeglKategorien;
	private String selectedKategorie;
	private List<Unterkategorie> moeglUnterKategorien;
	private String selectedUnterkategorie;
	private String veranstalter;
	private boolean mitTickets = false;
	private UploadedFile file;
	//Einige Attribute sind zur Verwaltung der Karte zur Eingabe der Adresse nötig
	private MapModel emptyModel;
	private String mapcenter = "49.346739, 9.134492";
	private int mapzoom = 15;
	private String adressfeld;

	public EinreichenBean() {
		//Bei Aufruf der Seite werden mögliche Kategorien ermittelt und gesetzt
		moeglKategorien = BackendConnector.getKategorien();
		//Initialisierung der Map
		setEmptyModel(new DefaultMapModel());
	}
	
	public void onKategorienChange() {
		//Wenn eine Oberkategorie gewählt wurde wird das entsprechende Objekt ermittelt
		Oberkategorie kat = BackendConnector.getOberKategorieByName(selectedKategorie);
		veranstaltung.setOberkategorie(kat);
		//Und dann das Dropdown für die Unterkategorien befüllt
		if (kat == null) {
			moeglUnterKategorien = null;
		}else {
			moeglUnterKategorien = kat.getUnterkategorien();
		}
	}
	
	//Diese Methode wird aufgerufen, wenn der User auf den Button "Verantaltung einreichen" klickt
	public void abschicken() {
		//Zuerst wird überprüft ob alle benötigten Felder richtig gesetzt wurden
		//Und falls nicht wird der User mit einer Message darauf hingewiesen. 
		//Die Veranstaltung wird nicht eingereicht, bis die  Daten korrigiert sind
		if (veranstaltung.getTitel() == null || veranstaltung.getTitel().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Titel", "Bitte geben Sie einen Titel an"));
		} else if (veranstaltung.getVeranstalter() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Veranstalter", "Bitte bestimmen Sie einen Veranstalter"));
		} else if (veranstaltung.getOberkategorie() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Kategorie", "Bitte bestimmen Sie eine Kategorie"));
		} else if (veranstaltung.getUnterkategorie() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unterkategorie", "Bitte bestimmen Sie eine Unterkategorie"));
		} else if (veranstaltung.getDatefromAsDate() == null || veranstaltung.getDatefromAsDate().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Datum", "Bitte bestimmen Sie einen gültigen Startzeitpunkt"));
		} else if (veranstaltung.getDatetoAsDate() != null && veranstaltung.getDatefromAsDate().after(veranstaltung.getDatetoAsDate())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Datum bis", "Der Endzeitpunkt darf nicht vor dem Startzeitpunkt liegen"));
		} else if (veranstaltung.getAdresse().getStraße() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ort", "Bitte bestimmen Sie den Veranstaltungsort"));
		} else if (veranstaltung.getBeschreibung() == null || veranstaltung.getBeschreibung().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Beschreibung", "Bitte geben Sie eine Beschreibung der Veranstaltung"));
		} else {
			if (veranstaltung.getTicketlink() != null && !veranstaltung.getTicketlink().contains("http")) {
				veranstaltung.setTicketlink("https://" + veranstaltung.getTicketlink());
			}
			//Wenn alle Eingaben korrekt sind, wird das Objekt ans Backend übermittelt und in der Datenbank gespeichert
			BackendConnector.veranstaltungEinreichen(veranstaltung);
			veranstaltung = new Veranstaltung();
			
			//Erfolgsmeldung
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Vielen Dank!","Ihr Veranstaltung wurde eingereicht"));
			
			//Danach wird der User wieder auf die Startseite weitergeleitet
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + "/Startseite.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Diese Methode wird aufgerufen, wenn der User eine Adresse eingegeben hat
	public void onGeocode(GeocodeEvent event) {
		//Das Primefaces Framework liefert hier im Event gleich alle Geocoding Daten 
		//zu der eingegebenen Adresse mit, unter anderem die benötigten Koordinaten
		List<GeocodeResult> results = event.getResults();

		if (results != null && !results.isEmpty()) {
			GeocodeResult result = results.get(0);
			LatLng center = result.getLatLng();
			//Map auf die eingegebene Adresse zentrieren
			mapcenter = center.getLat() + ", " + center.getLng();

			//Die eingegebene Adresse wird auf der Karte markiert
			emptyModel = new DefaultMapModel();
			emptyModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
			//Die Daten werden in dem Veranstaltungs-Objekt gespeichert. 
			//Die Adresse landet provisorisch in der Straße, da das Result Objekt keine Methode für Straße Ort und Hausnummer bietet
			veranstaltung.getAdresse().setStraße(result.getAddress());
			veranstaltung.getAdresse().setBreitengrad(result.getLatLng().getLat());
			veranstaltung.getAdresse().setLaengengrad(result.getLatLng().getLng());
		}
	}

	//Diese Methode wird aufgerufen, wenn der User auf einen Punkt auf der Karte klickt
	public void addMarker(PointSelectEvent event) {
		//In dem übergebenen Event sind die Koordinaten des angeklickten Punkts enthalten
		veranstaltung.getAdresse().setBreitengrad(event.getLatLng().getLat());
		veranstaltung.getAdresse().setLaengengrad(event.getLatLng().getLng());
		//Auf der Karte wird ein Marker an die geklickte Stelle gesetzt
		Marker marker = new Marker(event.getLatLng());
		emptyModel = new DefaultMapModel();
		emptyModel.addOverlay(marker);
		
		//Die Koordinaten des angeklickte Punktes werden mittels der Google Maps Java GeocodingAPI in eine Adresse umgewandelt, 
		//die der User im Adresseingabefeld sieht und wieder in der Straße abgespeichert wird
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDtLxjOgNg_RaTnX3b4s2Vx-Z27ZF98zMA").build();
		GeocodingResult[] results = null;
		try {
			results =  GeocodingApi.reverseGeocode(context, new com.google.maps.model.LatLng(event.getLatLng().getLat(), event.getLatLng().getLng())).await();
			adressfeld = results[0].formattedAddress;
			veranstaltung.getAdresse().setStraße(adressfeld);
		} catch (ApiException | InterruptedException | IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	//Wenn der User die Karte bewegt werden die neue Position und der Zoom gespeichert, 
	//damit diese bei eimem Update der Karte gleich bleiben
	public void onStateChange(StateChangeEvent event) {
		mapcenter = event.getCenter().getLat() + ", " + event.getCenter().getLng();
		mapzoom = event.getZoomLevel();
	}

	
	//Getter und Setter
	
	public Veranstaltung getVeranstaltung() {
		return veranstaltung;
	}
	public void setVeranstaltung(Veranstaltung veranstaltung) {
		this.veranstaltung = veranstaltung;
	}
	public boolean isMitTickets() {
		return mitTickets;
	}
	public void setMitTickets(boolean mitTickets) {
		if(!mitTickets) {
			veranstaltung.setPlaetze(0);
			veranstaltung.setPreis(0);
			veranstaltung.setTitel(null);
		}
		this.mitTickets = mitTickets;
	}
	public String getSelectedKategorie() {
		return selectedKategorie;
	}
	public void setSelectedKategorie(String selectedKategorie) {
		this.selectedKategorie = selectedKategorie;
	}
	public List<Oberkategorie> getMoeglKategorien() {
		return moeglKategorien;
	}
	public List<Unterkategorie> getMoeglUnterKategorien() {
		return moeglUnterKategorien;
	}
	public MapModel getEmptyModel() {
		return emptyModel;
	}
	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}
	public String getMapcenter() {
		return mapcenter;
	}
	public void setMapcenter(String mapcenter) {
		this.mapcenter = mapcenter;
	}
	public int getMapzoom() {
		return mapzoom;
	}
	public void setMapzoom(int mapzoom) {
		this.mapzoom = mapzoom;
	}
	public String getSelectedUnterkategorie() {
		return selectedUnterkategorie;
	}
	public void setSelectedUnterkategorie(String selectedUnterkategorie) {
		//wenn die Unterkategorie gesetzt wird, wird diese direkt vom String in ein Objekt umgewandelt
		veranstaltung.setUnterkategorie(veranstaltung.getOberkategorie().getUnterkategorieByName(selectedUnterkategorie));
		this.selectedUnterkategorie = selectedUnterkategorie;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getVeranstalter() {
		return veranstalter;
	}
	public void setVeranstalter(String veranstalter) {
		this.veranstalter = veranstalter;
		veranstaltung.setVeranstalter(BackendConnector.getVeranstalterByName(veranstalter));
	}
	public String getAdressfeld() {
		return adressfeld;
	}
	public void setAdressfeld(String adressfeld) {
		this.adressfeld = adressfeld;
	}
}
