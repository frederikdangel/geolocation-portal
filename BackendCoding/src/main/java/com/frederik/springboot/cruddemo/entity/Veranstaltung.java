package com.frederik.springboot.cruddemo.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Veranstaltung")
public class Veranstaltung {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veranstaltungsid")
    private Integer veranstaltungsID;

    /*
     * @ManyToOne = n:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "oberkategorieid")
    private Oberkategorie oberkategorie;
    
    /*
     * @ManyToOne = n:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "veranstalterid")
    private Veranstalter veranstalter;

    /*
     * @ManyToOne = n:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "unterkategorieid")
    private Unterkategorie unterkategorie;

    /*
     * @OneToOne = 1:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adressid")
    private Adresse adresse;

    @Column(name = "titel")
    private String titel;

    @Column(name = "datefrom")
    private Timestamp datefrom;

    @Column(name = "dateto")
    private Timestamp dateto;

    @Column(name = "beschreibung")
    private String beschreibung;

    @Column(name = "preis")
    private double preis;

    @Column(name = "dateipfad")
    private String dateipfad;

    @Column(name = "plaetze")
    private int plaetze;

    @Column(name = "link")
    private String ticketlink;

    @Column(name = "isthighlight", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isthighlight;

    @Column(name = "istfreigegeben", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean istfreigegeben;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("veranstaltungsBesucher")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "veranstaltungsbesucher", joinColumns = {
	    @JoinColumn(name = "veranstaltungsid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
    private List<User> veranstaltungsBesucher;

    public Veranstaltung() {

    }

    public Integer getVeranstaltungsID() {
	return veranstaltungsID;
    }

    // Getter- und Setter-Methoden
    public void setVeranstaltungsID(Integer veranstaltungsID) {
	this.veranstaltungsID = veranstaltungsID;
    }

    public Veranstalter getVeranstalter() {
	return veranstalter;
    }

    public void setVeranstalter(Veranstalter veranstalter) {
	this.veranstalter = veranstalter;
    }

    public Oberkategorie getOberkategorie() {
	return oberkategorie;
    }

    public void setOberkategorie(Oberkategorie oberkategorie) {
	this.oberkategorie = oberkategorie;
    }

    public String getTitel() {
	return titel;
    }

    public void setTitel(String titel) {
	this.titel = titel;
    }

    public Unterkategorie getUnterkategorie() {
	return unterkategorie;
    }

    public void setUnterkategorie(Unterkategorie unterkategorie) {
	this.unterkategorie = unterkategorie;
    }

    public Adresse getAdresse() {
	return adresse;
    }

    public void setAdresse(Adresse adresse) {
	this.adresse = adresse;
    }

    public String getBeschreibung() {
	return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
	this.beschreibung = beschreibung;
    }

    public double getPreis() {
	return preis;
    }

    public void setPreis(double preis) {
	this.preis = preis;
    }

    public List<User> getVeranstaltungsBesucher() {
	return veranstaltungsBesucher;
    }

    public void setVeranstaltungsBesucher(List<User> veranstaltungsBesucher) {
	this.veranstaltungsBesucher = veranstaltungsBesucher;
    }

    public Timestamp getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Timestamp datefrom) {
        this.datefrom = datefrom;
    }

    public Timestamp getDateto() {
        return dateto;
    }

    public void setDateto(Timestamp dateto) {
        this.dateto = dateto;
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

    // fügt Benutzer hinzu
    public void addUser(User theUser) {

	if (veranstaltungsBesucher == null) {
	    veranstaltungsBesucher = new ArrayList<>();
	}

	veranstaltungsBesucher.add(theUser);
    }

    public String getDateipfad() {
	return dateipfad;
    }

    public void setDateipfad(String dateipfad) {
	this.dateipfad = dateipfad;
    }

    public boolean isIsthighlight() {
	return isthighlight;
    }

    public void setIsthighlight(boolean isthighlight) {
	this.isthighlight = isthighlight;
    }

    public boolean isIstfreigegeben() {
	return istfreigegeben;
    }

    public void setIstfreigegeben(boolean istfreigegeben) {
	this.istfreigegeben = istfreigegeben;
    }

    public Veranstaltung merge(Veranstaltung eingang) {

	if (eingang.getTitel() != null)
	    this.setTitel(eingang.getTitel());

	if (eingang.getAdresse() != null)
	    this.setAdresse(eingang.getAdresse());

	if (eingang.getBeschreibung() != null)
	    this.setBeschreibung(eingang.getBeschreibung());

	if (eingang.getDatefrom() != null)
	    this.setDatefrom((eingang.getDatefrom()));

	if (eingang.getDateto() != null)
	    this.setDateto((eingang.getDateto()));

	if (eingang.getDateipfad() != null)
	    this.setDateipfad(eingang.getDateipfad());

	if (eingang.getOberkategorie() != null)
	    this.setOberkategorie(eingang.getOberkategorie());

	if (eingang.getPreis() != 0)
	    this.setPreis(eingang.getPreis());

	if (eingang.getTicketlink() != null)
	    this.setTicketlink((eingang.getTicketlink()));

	if (eingang.getPlaetze() != 0)
	    this.setPlaetze(eingang.getPlaetze());
	

	if (eingang.getUnterkategorie() != null)
	    this.setUnterkategorie(eingang.getUnterkategorie());

	if (eingang.getVeranstalter() != null)
	    this.setVeranstalter(eingang.getVeranstalter());

	if (eingang.getVeranstaltungsBesucher() != null)
	    this.setVeranstaltungsBesucher(eingang.getVeranstaltungsBesucher());

	if (!(this.isIstfreigegeben()) == (eingang.isIstfreigegeben()))
	    this.setIstfreigegeben((eingang.isIstfreigegeben()));
	if (!(this.isIsthighlight()) == (eingang.isIsthighlight()))
	    this.setIsthighlight(eingang.isIsthighlight());

	return this;
    }

}
