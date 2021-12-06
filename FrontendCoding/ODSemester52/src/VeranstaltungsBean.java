import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

/**
 * @author kersting
 * Bean, dass alle Daten zur Veranstaltungssuche enthält
 * SessionScoped, weil diese von der Startseite zu Map erhalten bleiben sollen
 */
@ManagedBean(name = "vbean")
@SessionScoped
public class VeranstaltungsBean {
	//Suchen. Diese werden getrennt gespeichert, da eine Filteränderung lokal passiert und eine Suche im Backend
	private List<Veranstaltung> aktuelleSuche;
	private List<Veranstaltung> sucheOhneFilter;

	//Suchparameter
	private String text;
	private List<String> selectedKategorien;
	private List<Oberkategorie> moeglKategorien;
	private List<String> selectedUnterKategorien;
	private List<SelectItem> moeglUnterKategorien;
	private Date datefrom;
	private Date dateto;
	private String buttontext = "Alle Veranstaltungen anzeigen";
	private int anzahlVeranstaltungen;

	//Kartenparamter
	private MapModel model;
	private Marker marker;
	private String positionAsCoordinates = "49.346739, 9.134492";
	private int mapzoom = 14;
	private GeodeticCalculator geoCalc = new GeodeticCalculator();
	private Ellipsoid reference = Ellipsoid.WGS84;

	//Filter
	private double preis;
	private double distanz;
	private double maxpreis;
	private double maxdistanz;
	private double userPositionLat;
	private double userPositionLng;
	private String userPositionAdresse;

	//Im Kostruktor werden einige Initialisierungen vorgenommen
	public VeranstaltungsBean() {
		preis = Integer.MAX_VALUE;
		distanz = Integer.MAX_VALUE;
		//Als Standard-Adresse wird Mosbach angenommen
		userPositionAdresse = "Mosbach";
		geocodingAdresseToCoords();

		//Mögliche Kategorien befüllen
		moeglUnterKategorien = new ArrayList<SelectItem>();
		moeglKategorien = BackendConnector.getKategorien();

		//onAllChange löst die Suche aus
		onAllChange();
		//Zum Start werden die Distanz- und Preisfilter auf den maximalen Wert gesetzt 
		//(Dieser ermittelt sich durch den höchsten in der Suche enthaltenen, beim Initialisieren sind dies alle Veranstaltungen)
		distanz = maxdistanz;
		preis = maxpreis;
	}

	//Diese Methode wird aufgerufen, wenn der User seinen Standort ermitteln lässt
	//Das ermitteln des Standorts passiert im JavaScript Code, von welchem ein Primefaces Event
	//ausgelöst wird, das diese Methode aufruft
	public void reverseGeocodeUserPosition(ReverseGeocodeEvent event) {
		//Im Event sind dann bereits die Position des Users als Adresse und Koordinaten enthalten
		userPositionAdresse = event.getAddresses().get(0);
		userPositionLat = event.getLatlng().getLat();
		userPositionLng = event.getLatlng().getLng();
		positionAsCoordinates = userPositionLat + ", " + userPositionLng;
		//wenn sich die Ausgangsposition verändert, müssen die Filter-Distanzen neu berechnet werden
		onPositionChange();
	}

	//Gibt der User eine Adresse ein, muss diese in Koordinaten umgewandelt werden
	public void geocodingAdresseToCoords() {
		//Dazu wird die Google Maps Geocoding API verwendet
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDtLxjOgNg_RaTnX3b4s2Vx-Z27ZF98zMA").build();
		try {
			GeocodingResult[] results = GeocodingApi.geocode(context, userPositionAdresse).await();
			userPositionLat = results[0].geometry.location.lat;
			userPositionLng = results[0].geometry.location.lng;
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		positionAsCoordinates = userPositionLat + ", " + userPositionLng;
	}

	//Wenn sich die Auswahl der Oberkategorien verändert wird diese Methode aufgerufen
	public void onKategorienChange() {
		//Die für die ausgewählten Oberkategorien möglichen Unterkategorien werden ermittelt
		moeglUnterKategorien = new ArrayList<SelectItem>();
		for (String kat : selectedKategorien) {
			//Die Unterkategorien werden im Dropdown als Oberkategoriegruppen dargestellt
			//Dafür wird pro Oberkategorie eine Select Item Group angelegt
			SelectItemGroup oberkat = new SelectItemGroup(kat);
			Oberkategorie o = BackendConnector.getOberKategorieByName(kat);

			List<Unterkategorie> unterkats = o.getUnterkategorien();
			SelectItem[] items = new SelectItem[unterkats.size()];
			int i = 0;
			for (Unterkategorie unterkategorie : unterkats) {
				items[i] = new SelectItem(unterkategorie);
				i++;
			}
			//Der SelectItemGroup werden dann die zur Oberkategorie gehörenden Unterkategorien hinzugefügt
			oberkat.setSelectItems(items);
			moeglUnterKategorien.add(oberkat);
		}
		//Da sich ein Suchparamter ändert muss mit onAllChange die Suche ausgelöst werden
		onAllChange();
	}

	//Diese Methode wird aufgerufen, wenn sich Suchparameter ändern, damit die Suche für den User Live passieren kann
	public void onAllChange() {
		//Dafür wird die Suche im Backend aufgerufen
		sucheOhneFilter = BackendConnector.veranstaltungenSuchen(text, selectedKategorien, selectedUnterKategorien, datefrom, dateto);
		//Nach jeder Suche müssen die Distanzen neu berechnet werden und entsprechend auch die Filter neu angewandt werden.
		//Dies passiert in der Methode onPositionChange
		onPositionChange();
	}

	//Diese Methode wird aus der Oberfläche aufgerufen, wenn sich die Position des Users verändert 
	//(Durch Eingabe einer Adresse oder ermitteln des eigenen Standorts)
	//Sie wird auch bei jeder Suche aufgerufen, um die Distanzen neu zu berechnen
	public void onPositionChange() {
		//Distanzen neu berechnen
		GlobalPosition userPosition = new GlobalPosition(userPositionLat, userPositionLng, 0.0);
		for (Veranstaltung veranstaltung : sucheOhneFilter) {
			GlobalPosition vPos = new GlobalPosition(veranstaltung.getAdresse().getBreitengrad(), veranstaltung.getAdresse().getLaengengrad(), 0.0);
			veranstaltung.setDistanz(geoCalc.calculateGeodeticCurve(reference, userPosition, vPos).getEllipsoidalDistance()/1000);
		}

		//Die maximalen in der Suche vorhandenen Preis und Distanz werden ermittelt. 
		//Davon hängt der Maximale Wert der Filter-Schieberegler ab. 
		//Auf den Wert wird 1 addiert, um Rundungsprobleme auf der Oberfläche zu vermeiden
		maxdistanz = Veranstaltung.getMaxKm(sucheOhneFilter) + 1;
		maxpreis = Veranstaltung.getMaxPreis(sucheOhneFilter) + 1;

		//Dann werden die Filter neu angewendet
		onFilterChange();
	}

	//Diese Methode wendet die Filter an und wir daher immer aufgerufen, 
	//wenn diese sich ändern oder eine Suche ausgeführt wurde
	public void onFilterChange() {
		//aktuelle Suche zurücksetzen
		aktuelleSuche = new ArrayList<Veranstaltung>();

		//Über die Suche ohne Filter wird drübergegangen
		for (Veranstaltung veranstaltung : sucheOhneFilter) {
			//Wenn eine Veranstaltung dem Filter entspricht wird sie zur aktuellen Suche hinzugefügt
			if (veranstaltung.getDistanz()<=distanz && veranstaltung.getPreis()<=preis) {
				aktuelleSuche.add(veranstaltung);
			}
		}

		//Die Anzahl Veranstaltungen wird ermittelt, um sie live auf dem Button anzeigen zu können
		anzahlVeranstaltungen = aktuelleSuche.size();
		//Der Text für den Suchen-Button wird ermittelt.
		if ((text == null || text.equals("")) 
				&& (selectedKategorien == null || selectedKategorien.isEmpty())
				&& datefrom == null
				&& dateto == null) {
			//Wenn keine Suchparameter eingegeben sind, steht auf dem Button "Alle Veranstaltungen anzeigen"
			buttontext = "Alle Veranstaltungen anzeigen (" + anzahlVeranstaltungen + ")";
		}else {
			//Ansonsten Suchen
			buttontext = "Suchen (" + anzahlVeranstaltungen + ")";
		}

		//Für die Map-Seite werden aus der aktuellen Suche Marker generiert
		veranstaltungenToMapMarkers();
	}

	//Methode um aus der Veranstaltungssuche Map-Marker zu generieren
	public void veranstaltungenToMapMarkers() {
		//Zurücksetzen der aktuellen Map-Marker
		model = new DefaultMapModel();
		for (Veranstaltung veranstaltung : aktuelleSuche) {
			//Hinzufügen von allen Veranstaltungen als Marker
			model.addOverlay(new Marker(new LatLng(veranstaltung.getAdresse().getBreitengrad(),veranstaltung.getAdresse().getLaengengrad()), veranstaltung.getTitel(), veranstaltung));
		}
	}

	//Diese Methode wird aufgerufen, wenn ein Marker angeklickt wird
	public void onMarkerSelect(OverlaySelectEvent event) {
		//dann wird dieser Marker in ein Objekt geschrieben, um auf der Oberfläche ein InfoWindow anzuzeigen
		marker = (Marker) event.getOverlay();
	}

	//Wenn der User die Karte schiebt werden die neue Position und der Zoom gespeichert, 
	//damit diese bei eimem update der Karte gleich bleiben
	public void onStateChange(StateChangeEvent event) {
		positionAsCoordinates = event.getCenter().getLat() + ", " + event.getCenter().getLng();
		setMapzoom(event.getZoomLevel());
	}

	
	//Getter und Setter

	public double getMaxdistanz() {
		return maxdistanz;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public double getDistanz() {
		return distanz;
	}
	public void setDistanz(double distanz) {
		this.distanz = distanz;
	}
	public String getPositionAsCoordinates() {
		return positionAsCoordinates;
	}
	public double getMaxpreis() {
		return maxpreis;
	}
	public MapModel getModel() {
		return model;
	}
	public Marker getMarker() {
		return marker;
	}
	public String getButtontext() {
		return buttontext;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getSelectedKategorien() {
		return selectedKategorien;
	}
	public void setSelectedKategorien(List<String> selectedKategorien) {
		this.selectedKategorien = selectedKategorien;
	}
	public List<Oberkategorie> getMoeglKategorien() {
		return moeglKategorien;
	}
	public void setMoeglKategorien(List<Oberkategorie> moeglKategorien) {
		this.moeglKategorien = moeglKategorien;
	}
	public List<String> getSelectedUnterKategorien() {
		return selectedUnterKategorien;
	}
	public void setSelectedUnterKategorien(List<String> selectedUnterKategorien) {
		this.selectedUnterKategorien = selectedUnterKategorien;
	}
	public List<SelectItem> getMoeglUnterKategorien() {
		return moeglUnterKategorien;
	}
	public void setMoeglUnterKategorien(List<SelectItem> moeglUnterKategorien) {
		this.moeglUnterKategorien = moeglUnterKategorien;
	}
	public Date getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}
	public Date getDateto() {
		return dateto;
	}
	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}
	public List<Veranstaltung> getAktuelleSuche() {
		return aktuelleSuche;
	}
	public void setAktuelleSuche(List<Veranstaltung> aktuelleSuche) {
		this.aktuelleSuche = aktuelleSuche;
	}
	public int getMapzoom() {
		return mapzoom;
	}
	public void setMapzoom(int mapzoom) {
		this.mapzoom = mapzoom;
	}
	public double getUserPositionLat() {
		return userPositionLat;
	}
	public void setUserPositionLat(double userPositionLat) {
		this.userPositionLat = userPositionLat;
	}
	public double getUserPositionLng() {
		return userPositionLng;
	}
	public void setUserPositionLng(double userPositionLng) {
		this.userPositionLng = userPositionLng;
	}
	public String getUserPositionAdresse() {
		return userPositionAdresse;
	}
	public void setUserPositionAdresse(String userPositionAdresse) {
		this.userPositionAdresse = userPositionAdresse;
	}
}
