package com.frederik.springboot.cruddemo.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "User")
public class User {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Integer userID;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @Column(name = "email")
    private String email;

    @Column(name = "pw")
    private String pw;

    @Column(name = "istangestellter", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean istAngestellter;

    @Column(name = "hatnews", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean hatnews;

    public User() {

    }

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "userunterkategorien", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
	    @JoinColumn(name = "unterkategorieid") })
    private List<Unterkategorie> userUnterkategorien;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH})
    @JoinTable(name = "useroberkategorien", joinColumns = { @JoinColumn(name = "oberkategorieid") }, inverseJoinColumns = {
	    @JoinColumn(name = "userid") })
    private List<Oberkategorie> userOberkategorien;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("veranstaltendeUser")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "veranstaltendeuser", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
	    @JoinColumn(name = "veranstalterid") })
    private List<Veranstalter> veranstaltendeUser;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("veranstaltungsBesucher")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "veranstaltungsbesucher", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = {
	    @JoinColumn(name = "veranstaltungsid") })
    private List<Veranstaltung> veranstaltungsBesucher;

    
    
    
    public List<Unterkategorie> getUserUnterkategorien() {
	return userUnterkategorien;
    }

    
    // Getter- und Setter-Methoden
    public void setUserUnterkategorien(List<Unterkategorie> userUnterkategorien) {
	this.userUnterkategorien = userUnterkategorien;
    }

    public List<Oberkategorie> getUserOberkategorien() {
	return userOberkategorien;
    }

    public void setUserOberkategorien(List<Oberkategorie> userOberkategorien) {
	this.userOberkategorien = userOberkategorien;
    }

    public User(String vorname, String nName, String email, String pw, boolean istAngestellter) {
	this.vorname = vorname;
	this.nachname = nName;
	this.email = email;
	this.pw = pw;
	this.istAngestellter = istAngestellter;
    }

    public Integer getUserID() {
	return userID;
    }

    public void setUserID(Integer userid) {
	this.userID = userid;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
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

    public void setVeranstaltendeUser(List<Veranstalter> veranstaltendeUser) {
	this.veranstaltendeUser = veranstaltendeUser;
    }

    public List<Veranstaltung> getVeranstaltungsBesucher() {
	return veranstaltungsBesucher;
    }

    public void setVeranstaltungsBesucher(List<Veranstaltung> veranstaltungsBesucher) {
	this.veranstaltungsBesucher = veranstaltungsBesucher;
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

    public boolean isHatnews() {
	return hatnews;
    }

    public void setHatnews(boolean hatnews) {
	this.hatnews = hatnews;
    }

    
    // fügt Unterkategorie hinzu
    public void addUnterkategorie(Unterkategorie theUnterkategorie) {

	if (userUnterkategorien == null) {
	    userUnterkategorien = new ArrayList<>();
	}

	userUnterkategorien.add(theUnterkategorie);
    }

    // fügt Oberkategorie hinzu
    public void addOberkategorie(Oberkategorie theOberkategorie) {

	if (userOberkategorien == null) {
	    userOberkategorien = new ArrayList<>();
	}

	userOberkategorien.add(theOberkategorie);
    }

    // fügt Veranstalter hinzu
    public void addVeranstalter(Veranstalter theVeranstalter) {

	if (veranstaltendeUser == null) {
	    veranstaltendeUser = new ArrayList<>();
	}

	veranstaltendeUser.add(theVeranstalter);
    }

    public User merge(User eingang) {

	if (eingang.getEmail() != null)
	    this.setEmail(eingang.getEmail());

	if (eingang.getNachname() != null)
	    this.setNachname(eingang.getNachname());

	if (eingang.getPw() != null)
	    this.setPw(eingang.getPw());

	if (eingang.getUserOberkategorien() != null)
	    this.setUserOberkategorien(eingang.getUserOberkategorien());

	if (eingang.getUserUnterkategorien() != null)
	    this.setUserUnterkategorien(eingang.getUserUnterkategorien());

	if (eingang.getVeranstaltendeUser() != null)
	    this.setVeranstaltendeUser(eingang.getVeranstaltendeUser());

	if (eingang.getVeranstaltungsBesucher() != null)
	    this.setVeranstaltungsBesucher(eingang.getVeranstaltungsBesucher());

	if (eingang.getVorname() != null)
	    this.setVorname(eingang.getVorname());

	if (!(this.isHatnews()) == (eingang.isHatnews()))
	    this.setHatnews(eingang.isHatnews());
	if (!(this.isIstAngestellter()) == (eingang.isIstAngestellter()))
	    this.setIstAngestellter(eingang.isIstAngestellter());

	return this;

    }

    // fügt Veranstaltung hinzu
    public void addVeranstaltung(Veranstaltung theVeranstaltung) {
	if (veranstaltungsBesucher == null) {
	    veranstaltungsBesucher = new ArrayList<>();
	}

	veranstaltungsBesucher.add(theVeranstaltung);

    }

}
