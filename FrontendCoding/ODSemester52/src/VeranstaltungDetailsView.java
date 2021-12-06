import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 * @author kersting
 * Bean, um die Seite Veranstaltungsdetails zu befüllen, 
 * ViewScoped, um mehrere Fenster mit Details parallel zuzulassen
 */
@ManagedBean(name = "vView")
@ViewScoped
public class VeranstaltungDetailsView implements Serializable{
	private Veranstaltung veranstaltung;
	private String position;
	private MapModel model;
	
	public VeranstaltungDetailsView() {
		//VeranstaltungsID wurde im Link mitgegeben, diese daraus ermitteln
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//Zu dieser VeranstaltungsID das Veranstaltungsobjekt holen
		veranstaltung = BackendConnector.getVeranstaltungByID(Integer.parseInt(params.get("vid")));
		//position aus der Veranstalung holen, um die Karte darauf zu zentrieren
		position = veranstaltung.getAdresse().getBreitengrad() + ", " + veranstaltung.getAdresse().getLaengengrad();
		//Marker hinzufügen
		model = new DefaultMapModel();
		model.addOverlay(new Marker(new LatLng(veranstaltung.getAdresse().getBreitengrad(), veranstaltung.getAdresse().getLaengengrad())));
	}
	
	//Getter
	public Veranstaltung getVeranstaltung() {
		return veranstaltung;
	}
	public String getPosition() {
		return position;
	}
	public MapModel getModel() {
		return model;
	}
}
