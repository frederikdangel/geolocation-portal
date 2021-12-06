import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.sql.Timestamp;

/**
 * @author kersting
 * Klasse zur Verwaltung der Veranstaltungen
 */
public class Veranstaltung implements Serializable{
	//normale Attribute
	private int veranstaltungsID;
	private String beschreibung;
	private Date datefrom;
	private Date dateto;
	private String dateipfad;
	private boolean istfreigegeben;
	private boolean isthighlight;
	private double preis;
	private int plaetze;
	private double distanz;
	private String ticketlink;
	private String titel;
	//Attribute als Objekt anderer Klassen
	private Adresse adresse;
	private Oberkategorie oberkategorie;
	private Unterkategorie unterkategorie;
	private Veranstalter veranstalter;
	
	//Standart Konstruktor
	public Veranstaltung() {
		//Beim Anlegen der Veranstaltung werden direkt ein Objekt der Adresse, 
		this.adresse = new Adresse();
		//sowie vom Veranstalter erzeugt, um NullPointerExceptions zu verhindern
		this.veranstalter = new Veranstalter();
	}
	
	//static Methode, um aus einer Liste von Veranstaltungen, die größte Distanz zu ermitteln
	public static double getMaxKm(List<Veranstaltung> suche) {
		//Maximale Distanz aus der Liste ermitteln
		double maxdistanz = 0;
		for (Veranstaltung veranstaltung : suche) {
			if (veranstaltung.getDistanz() > maxdistanz) {
				maxdistanz = veranstaltung.getDistanz();
			}
		}
		return maxdistanz;
	}

	//static Methode, um aus einer Liste von Veranstaltungen, den höchsten Preis zu ermitteln
	public static double getMaxPreis(List<Veranstaltung> suche) {
		//Maximalen Preis aus der Liste ermitteln
		double maxpreis = 0;
		for (Veranstaltung veranstaltung : suche) {
			if (veranstaltung.getPreis() > maxpreis) {
				maxpreis = veranstaltung.getPreis();
			}
		}
		return maxpreis;
	}
	
	//Methode wird aufgerufen, wenn der User eine Datei hochlädt
	@SuppressWarnings("deprecation")
	public void fileUpload(FileUploadEvent event) {
		//Die Daten stehen in dem übergebenen Event, aus diesem wird die Datei geholt
		UploadedFile file = event.getFile();

		InputStream input = null;
		OutputStream output = null;

		try {
			//Die Datei wird in ein lokales Verzeichnis kopiert. Ein relativer Pfad funktionierte leider nicht. 
			//In Zukunft sollten Bilder direkt in der Datenbank gespeichert werden
			input = file.getInputstream();
			output = new FileOutputStream(new File("C:\\Users\\kersting\\git\\ODSemester52\\WebContent\\Images\\Veranstaltungsbilder", file.getFileName()));
			IOUtils.copy(input, output);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		
		//Um das Bild wiederzufinden wird der Pfad im Veranstaltungsobjekt und damit in der Datenbank gespeichert
		dateipfad = "Veranstaltungsbilder/" + file.getFileName();
	}

	
	//Standard Getter und Setter
	public Veranstalter getVeranstalter() {
		return veranstalter;
	}
	public void setVeranstalter(Veranstalter veranstalter) {
		this.veranstalter = veranstalter;
	}
	public int getPlaetze() {
		return plaetze;
	}
	public void setPlaetze(int plaetze) {
		this.plaetze = plaetze;
	}
	public String getTicketlink() {
		return ticketlink;
	}
	public void setTicketlink(String ticketlink) {
		this.ticketlink = ticketlink;
	}
	public Oberkategorie getOberkategorie() {
		return oberkategorie;
	}
	public void setOberkategorie(Oberkategorie kat) {
		this.oberkategorie = kat;
	}
	public Unterkategorie getUnterkategorie() {
		return unterkategorie;
	}
	public void setUnterkategorie(Unterkategorie unterkategorie) {
		this.unterkategorie = unterkategorie;
	}
	public long getDatefrom() {
		return datefrom.getTime();
	}
	public void setDatefrom(Timestamp datefrom) {
		this.datefrom = datefrom;
	}
	public Date getDatefromAsDate() {
		return datefrom;
	}
	public void setDatefromAsDate(Date datefromAsDate) {
		this.datefrom = datefromAsDate;
	}
	public long getDateto() {
		return dateto.getTime();
	}
	public void setDateto(Timestamp dateto) {
		this.dateto = dateto;
	}
	public Date getDatetoAsDate() {
		return dateto;
	}
	public void setDatetoAsDate(Date datetoAsDate) {
		this.dateto = datetoAsDate;
	}
	public double getPreis() {
		return preis;
	}
	public void setPreis(double preis) {
		this.preis = preis;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public String getDateipfad() {
		return dateipfad;
	}
	public void setDateipfad(String dateipfad) {
		//Wenn der Dateipfad für das Veranstaltungsbild null ist wird ein Default Bild gesetzt
		if (dateipfad == null) {
			dateipfad = "AlternativeVeranstaltungsBild.png";
		}
		this.dateipfad = dateipfad;
	}
	public int getVeranstaltungsID() {
		return veranstaltungsID;
	}
	public void setVeranstaltungsID(int veranstaltungsid) {
		this.veranstaltungsID = veranstaltungsid;
	}
	public boolean isIstfreigegeben() {
		return istfreigegeben;
	}
	public void setIstfreigegeben(boolean istfreigegeben) {
		this.istfreigegeben = istfreigegeben;
	}
	public boolean isIsthighlight() {
		return isthighlight;
	}
	public void setIsthighlight(boolean isthighlight) {
		this.isthighlight = isthighlight;
	}
	public boolean getMitTickets() {
		if (plaetze != 0) {
			return true;
		}
		if (preis != 0) {
			return true;
		}
		if (ticketlink != null) {
			return true;
		}
		return false;
	}
	public double getDistanz() {
		return distanz;
	}
	public void setDistanz(double distanz) {
		this.distanz = distanz;
	}

	//equals Methode, um List.contains möglich zu machen. 
	public boolean equals(Object obj) {
		//Veranstaltungen sind dann gleich wenn sie die gleiche ID haben
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veranstaltung other = (Veranstaltung) obj;
		if (veranstaltungsID != other.veranstaltungsID)
			return false;
		return true;
	}

}
