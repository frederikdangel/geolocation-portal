import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.primefaces.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kersting
 * Die Klasse BackendConnector ist für die gesamte Interaktion mit dem Backend zuständig 
 * und wickelt alle Abfragen mit dem Backend ab, 
 * sodass in anderen Klassen nicht direkt auf das Backend zugegriffen wird
 */
@ApplicationScoped
public class BackendConnector {
	private static Client client = ClientBuilder.newClient();
	private static WebTarget target;
	private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	private static List<Oberkategorie> alleKat;
	private static List<Veranstalter> alleVer;
	private static final String apiLinkStandardPraefix = "http://localhost:8080/api/";
	
	//standardisierter DELETE Request
	private static void deleteRequest(String api) {
		//generiert ein Ziel aus dem Präfix, der immer verwendet wird und der angegeben API
		target = client.target(apiLinkStandardPraefix + api);
		//Schickt den Request ab
		target.request(MediaType.APPLICATION_JSON).delete(String.class);
	}
	
	//standardisierter GET Request
	private static String getRequest(String api) {
		//generiert ein Ziel aus dem Präfix, der immer verwendet wird und der angegeben API
		target = client.target(apiLinkStandardPraefix + api);
		//Abschicken des Requests, das Ergebnis wird als String zurückgegeben
		return target.request(MediaType.APPLICATION_JSON).get(String.class);		
	}
	
	//standardisierter POST Request
	private static String postRequest(String api, JSONObject json) {
		//generiert ein Ziel aus dem Präfix, der immer verwendet wird und der angegeben API
		target = client.target(apiLinkStandardPraefix + api);
		//Abschicken des Requests, das Ergebnis wird als String zurückgegeben
		return target.request(MediaType.APPLICATION_JSON).method("POST", Entity.entity(json.toString(), MediaType.APPLICATION_JSON), String.class);
	}
	
	//standardisierter PUT Request
	private static String putRequest(String api, JSONObject json) {
		//generiert ein Ziel aus dem Präfix, der immer verwendet wird und der angegeben API
		target = client.target(apiLinkStandardPraefix + api);
		//Abschicken des Requests, das Ergebnis wird als String zurückgegeben
		return target.request(MediaType.APPLICATION_JSON).method("PUT", Entity.entity(json.toString(), MediaType.APPLICATION_JSON), String.class);
	}
	
	//Löschen einer offenen Verifizerung. Dies passiert, wenn eine Verifizierung bestätigt
	//und in eine User-Veranstalter Beziehung umgewandelt wird oder wenn eine Verifizierung
	//abgelehnt wird
	public static void deleteOffeneVerifizierung(Veranstalterverifizierung selectedVerifizierung) {
		deleteRequest("Veranstalterverifizierung/" + selectedVerifizierung.getUserid());
	}
	
	//Löschen einer Veranstaltung. Dies passiert wenn der User in seinem Profil eine eigene
	//Veranstaltung löschen möchte
	public static void veranstaltungLoeschen(Veranstaltung selectedVeranstaltung) {
		deleteRequest("Veranstaltung/" + selectedVeranstaltung.getVeranstaltungsID());
	}
	
	//Holen einer Liste aller Oberkategorien für das Dropdown-Menu der Suche
	public static List<Oberkategorie> getKategorien(){
		if (alleKat != null) {
			//Kategorien schon einmal gelesen, werden zurückgegeben
			return alleKat;
		}else {
			//ansonsten wird ein getRequest ausgelöst, das Ergebnis in eine Liste der Oberkategorien
			//umgewandelt und zurückgegeben
			String jsonAnswer = getRequest("Oberkategorie");
			try {
				alleKat = Arrays.asList(mapper.readValue(jsonAnswer, Oberkategorie[].class));
			} catch (JsonProcessingException e) {
				alleKat = new ArrayList<Oberkategorie>();
			}
			return alleKat;
		}
	}
	
	//Wenn die VeranstaltungsDetails Seite aufgerufen wird, wird eine Veranstaltung benötigt
	public static Veranstaltung getVeranstaltungByID(int id) {
		//get mit der ID, danach Umwandlung in ein Objekt
		String jsonAnswer = getRequest("Veranstaltung/" + id);
		try {
			return mapper.readValue(jsonAnswer, Veranstaltung.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	//Für die Highlightsgalerie auf der Startseite werden die Highlight-Veranstaltungen 
	//aus dem Backend abgefragt
	public static List<Veranstaltung> getHighlightVeranstaltungen() {
		// REST Aufruf auf die Highlights API, das Ergebnis wird in eine Veranstaltungs-Liste umgewandelt
		String jsonAnswer = getRequest("Veranstaltung/Highlights");
		try {
			return Arrays.asList(mapper.readValue(jsonAnswer, Veranstaltung[].class));
		} catch (JsonProcessingException e) {
			return new ArrayList<Veranstaltung>();
		}
	}
	
	//Aus einem VeranstalterNamen wird ein Veranstalter-Objekt
	public static Veranstalter getVeranstalterByName(String veranstalterString) {
		//Dafür wird eine Liste aller Veranstalter aus dem Backend geholt, wenn die noch nicht passiert ist
//		if (alleVer == null || alleVer.size() == 0) {
//			String jsonAnswer = getRequest("Veranstalter");
//			try {
//				alleVer = Arrays.asList(mapper.readValue(jsonAnswer, Veranstalter[].class));
//			} catch (JsonProcessingException e) {
//				alleVer = new ArrayList<Veranstalter>();
//			}			
//		}
		alleVer = getAlleVeranstalter();
		
		//Dann wird die Liste der Veranstalter durchgegangen und bei einem Treffer das 
		//entsprechende Objekt zurückgegeben
		for (Veranstalter veranstalter : alleVer) {
			if (veranstalter.getName().equals(veranstalterString)) {
				return veranstalter;
			}
		}
		return null;
	}

	//Für die Sachbearbeiteroberfläche wird eine Liste aller offenen Veranstalterverifizierungen benötigt
	public static List<Veranstalterverifizierung> getOffeneVerifizierungen() {
		//getRequest auf die Veranstalterverifizeriung API und dann umwandeln in eine Liste
		String jsonAnswer = getRequest("Veranstalterverifizierung");
		try {
			return Arrays.asList(mapper.readValue(jsonAnswer, Veranstalterverifizierung[].class));
		} catch (JsonProcessingException e) {
			return new ArrayList<Veranstalterverifizierung>();
		}
	}
	
	//Ermitteln eines User-Objekts aus einer User-ID
	public static User getUserByID(int userid) {
		//getRequest, danach umwandeln in ein Objekt
		String jsonAnswer = getRequest("User/" + userid);
		try {
			return mapper.readValue(jsonAnswer, User.class);
		} catch (JsonProcessingException e) {
			return new User();
		}
	}
	
	//Ermitteln einer Liste mit allen Veranstaltern
	public static List<Veranstalter> getAlleVeranstalter() {
		//Wenn die Liste noch nicht abgespeichert ist, wird sie aus dem Backend abgefragt
//		if (alleVer == null || alleVer.size() == 0) {
			String jsonAnswer = getRequest("Veranstalter");
			//Umwandeln in eine Liste
			try {
				alleVer = Arrays.asList(mapper.readValue(jsonAnswer, Veranstalter[].class));
			} catch (JsonProcessingException e) {
				alleVer = new ArrayList<Veranstalter>();
			}			
//		}
		//Liste zurückgeben
		return alleVer;
	}
	
	//Für die Sachbearbeiteroberfläche wird eine Liste mit allen nicht freigegebenen Veranstaltungen benötigt
	public static List<Veranstaltung> getVeranstaltungenZurFreigabe() {
		//getRequest auf die entsprechende API
		String jsonAnswer = getRequest("Veranstaltung/Freigeben");
		//Umwandeln in eine Objektliste
		try {
			return Arrays.asList(mapper.readValue(jsonAnswer, Veranstaltung[].class));
		} catch (JsonProcessingException e) {
			return new ArrayList<Veranstaltung>();
		}
	}
	
	//Für die Benutzerverwaltung wird eine Liste aller User gebraucht
	public static List<User> getAlleUser() {
		//getRequest auf die User API
		String jsonAnswer = getRequest("User");
		//Umwandeln in eine Objektliste
		try {
			return Arrays.asList(mapper.readValue(jsonAnswer, User[].class));
		} catch (JsonProcessingException e) {
			return new ArrayList<User>();
		}
	}

	//Bei der Registrierung wird ein neuer User angelegt.
	public static User registriereNeuenUser(User newuser) {
		//Umwandeln der  eingegebenen Daten in JSON
		JSONObject json = new JSONObject(newuser);
		json.remove("userID");
		try {
			String jsonAnswer = postRequest("User", json);
			//Wenn es geklappt hat wird ein User Objekt zurückgegeben
			return mapper.readValue(jsonAnswer, User.class);
		} catch (Exception e) {
			//Ansonsten null. Dies ist der Fall, wenn die Email-Adresse schon vergeben ist
			return null;
		}
	}
	
	//User einloggen
	public static User login(User user) {
		//Generieren eines JSON-Objects mit den eingegebenen Daten
		JSONObject json = new JSONObject();
		json.put("email", user.getEmail());
		json.put("pw", user.getPw());

		try {
			String jsonAnswer = postRequest("User/ByPW", json);
			//Wenn der Login geklappt hat, wird ein User-Objekt zurückgegeben
			return mapper.readValue(jsonAnswer, User.class);
		} catch (Exception e) {
			//Ansonsten null, dann waren die Login-Daten nicht korrekt
			return null;
		}
	}
	
	//Für das Veranstaltung suchen, dass den Kern der Applikation bildet wird ein Request an das 
	//Backend geschickt. Die eigentliche Suche findet dort statt
	public static List<Veranstaltung> veranstaltungenSuchen(String text, List<String> selectedKategorien,
			List<String> selectedUnterKategorien, Date datefrom, Date dateto) {
		Object nullobject = null;
		List<Oberkategorie> oberkategorien = new ArrayList<Oberkategorie>();
		List<Unterkategorie> unterkategorien = new ArrayList<Unterkategorie>();
		Unterkategorie unterkat;
		
		//Die als String übergebenen Kategorien werden in Objekte umgewandelt
		if(selectedKategorien != null) {
			for (String kategorie : selectedKategorien) {
				Oberkategorie kat = BackendConnector.getOberKategorieByName(kategorie); 
				oberkategorien.add(kat);
				if(selectedUnterKategorien != null) {
					for (String unterkategorie : selectedUnterKategorien) {
						unterkat = kat.getUnterkategorieByName(unterkategorie);
						if (unterkat != null) {
							unterkategorien.add(unterkat);
						}
					}
				}
			}
		}
		
		//Aus allen Eingabeparametern wird ein JSON-Objekt zusammengebaut
		JSONObject json = new JSONObject();
		json.put("suche", text);
		if (datefrom != null) {
			json.put("datefrom", datefrom.getTime());
		}else {
			json.put("datefrom", nullobject);
		}
		
		if (dateto != null) {
			json.put("dateto", dateto.getTime());
		}else {
			json.put("dateto", nullobject);
		}
		json.put("unterkategorien", unterkategorien);
		json.put("oberkategorien", oberkategorien);

		//Die Eingabe Paramter werden über einen postRequest an das Backend geschickt um die 
		//gesuchten Veranstaltungen zu bekommen
		String jsonAnswer = postRequest("Veranstaltung/Request", json);
		try {
			//Das Ergebnis wird in eine Liste umgewandelt und zurückgegeben
			return Arrays.asList(mapper.readValue(jsonAnswer, Veranstaltung[].class));
		} catch (JsonProcessingException e) {
			return new ArrayList<Veranstaltung>();
		}
	}
	
	//Wenn der User eine Veranstaltung einreicht muss diese im Backend gespeichert werden
	public static void veranstaltungEinreichen(Veranstaltung veranstaltung) {
		//Durch unterschiedliche Datentypen entsteht eine Zeitverschiebung von einer Stunde, diese wird korrigiert
		veranstaltung.setDatefrom(new Timestamp(veranstaltung.getDatefromAsDate().getTime()+3600000));
		if (veranstaltung.getDatetoAsDate()!=null) {
			veranstaltung.setDateto(new Timestamp(veranstaltung.getDatetoAsDate().getTime()+3600000));
		}
		
		//Aus den eingegebenen Daten wird ein JSON erzeugt
		JSONObject json = new JSONObject(veranstaltung);
		json.remove("veranstaltungsID");
		
		try {
			//POST Request
			String jsonAnswer = postRequest("Veranstaltung", json);
			//Die angelegte Veranstaltung wird dem Veranstalter hinzugefügt, damit diese direkt im 
			//Profil des Users zu sehen ist
			veranstaltung.getVeranstalter().getVeranstaltungen().add(mapper.readValue(jsonAnswer, Veranstaltung.class));
		} catch (Exception e) {
		
		}
	}
	
	//Will der User sich als Verantstalter verifizieren, wird dies im Backend gespeichert
	public static void saveVerifizierungsanfrage(int userID, String veranstalterZurVerifizierung, String file) {
		//Die Eingegebenen Daten werden in ein JSON-Object geschrieben
		JSONObject json = new JSONObject();
		json.put("userid", userID);
		json.put("veranstalter", veranstalterZurVerifizierung);
		json.put("persoablage", file);
		
		//POST Request, Daten werden im Backend gespeichert
		postRequest("Veranstalterverifizierung", json);
	}

	//Wenn auf der Sachbearbeiteroberfläche eine Veranstalterverifizierung bestätigt wird, 
	//muss dies im Backend gespeichert werden
	public static void bestaetigeVerifizierung(Veranstalterverifizierung selectedVerifizierung,
			boolean neuerVeranstalter) {
		Veranstalter veranstalter;
		JSONObject json = new JSONObject();
		
		if (neuerVeranstalter) {
			//Wenn eine neuer Veranstalter angelegt werden soll, wird dies zuerst ausgeführt
			json.put("veranstalterName", selectedVerifizierung.getSelectedVeranstalter());
			//POST Request
			String jsonAnswer = postRequest("Veranstalter", json);
			try {
				//Der angelegte Veranstalter wird für den nächsten Schritt in ein Objekt umgewandelt
				veranstalter = mapper.readValue(jsonAnswer, Veranstalter.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				veranstalter = new Veranstalter();
			}
		}else {
			//Soll ein existierender Verantalter verwendet werden, wird dieser ermittelt
			veranstalter = getVeranstalterByName(selectedVerifizierung.getSelectedVeranstalter());
		}
		
		//Mit dem Post Request wird eine Verknüpfung zwischen einem User und einem Verantalter angelegt. 
		//Die IDs von beidem stehen im API Link, das JSON im Body ist daher leer
		json = new JSONObject();
		postRequest("Veranstalter/User/" + veranstalter.getVeranstalterID() + "/" + selectedVerifizierung.getUserid(), json);

		//Die Verifizierungsanfrage wird danach gelöscht
		deleteOffeneVerifizierung(selectedVerifizierung);
	}
	
	//Wenn der User sein Profil bearbeitet wird dies im Backend gespeichert
	public static User updateUser(User user) {
		//Erzeugen eines JSON-Objekts aus den User-Daten
		JSONObject json = new JSONObject(user);
		if (json.getString("pw").equals("")) {
			json.remove("pw");
		}
		json.remove("veranstaltendeUser");
		json.remove("userUnterkategorien");
		json.remove("userOberkategorien");
		json.remove("veranstaltungen");
		
		//PUT Request an das Backend zum Update der Daten
		String jsonAnswer = putRequest("User", json);
		try {
			//Das aktualisierte Objekt wird zurückgegeben
			return mapper.readValue(jsonAnswer, User.class);
		} catch (Exception e) {
			return null;
		}
	}
	
	//Wird eine Veranstaltung vom Sachbearbeiter freigegeben, muss dies um Backend vermerkt werden
	public static void veranstaltungFreigeben(int veranstaltungsID) {
		//Dafür wird ein JSON Objekt mit der VeranstaltungsID und der Kennzeichnung als freigegeben generiert
		JSONObject json = new JSONObject();
		json.put("veranstaltungsID", veranstaltungsID);
		json.put("istfreigegeben", true);
		
		//PUT Request
		putRequest("Veranstaltung", json);
	}
	
	//Ermitteln eines Oberkategorie-Objekts aus einem Namen
	public static Oberkategorie getOberKategorieByName(String kat) {
		//Schleife über alle Kategorien, bei einem Treffer wird dieser zurückgegeben
		for (Oberkategorie oberkategorie : alleKat) {
			if (oberkategorie.getOberkategoriename().equals(kat)) {
				return oberkategorie;
			}
		}
		return null;
	}
}