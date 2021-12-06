import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author kersting
 *	Bean zur Verwaltung aller Daten, die sich auf der Applikationsebene befinden
 */
@ManagedBean(name = "appBean")
@ApplicationScoped
public class ApplicationThingsBean {
	private List<Veranstaltung> highlightVeranstaltungen;
	
	//Für die Startseite werden die Highlight-Veranstaltungen gebraucht
	public List<Veranstaltung> getHighlightVeranstaltungen() {
		//Wenn diese noch nicht ermittelt wurden, werden sie aus dem Backend abgefragt
		if (highlightVeranstaltungen == null || highlightVeranstaltungen.size() == 0) {
			highlightVeranstaltungen = BackendConnector.getHighlightVeranstaltungen();
		}
		return highlightVeranstaltungen;
	}
	
}
