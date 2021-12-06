package com.frederik.springboot.cruddemo.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Veranstalter")
public class Veranstalter {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veranstalterid")
    private Integer veranstalterID;

    @Column(name = "name")
    private String veranstalterName;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("veranstaltendeUser")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "veranstaltendeuser", joinColumns = {
	    @JoinColumn(name = "veranstalterid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
    private List<User> veranstaltendeUser;
    
    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     */
    @JsonIgnoreProperties("veranstalter")
    @OneToMany(mappedBy = "veranstalter")
    private List<Veranstaltung> veranstaltungen;

    public Veranstalter() {

    }

    public Veranstalter(String veranstalterName) {
	this.veranstalterName = veranstalterName;
    }

    // Getter- und Setter-Methoden
    public Integer getVeranstalterID() {
	return veranstalterID;
    }

    public void setVeranstalterID(Integer veranstalterID) {
	this.veranstalterID = veranstalterID;
    }

    public String getVeranstalterName() {
	return veranstalterName;
    }

    public void setVeranstalterName(String veranstalterName) {
	this.veranstalterName = veranstalterName;
    }

    public List<User> getVeranstaltendeUser() {
	return veranstaltendeUser;
    }

    public void setVeranstaltendeUser(List<User> veranstaltendeUser) {
	this.veranstaltendeUser = veranstaltendeUser;
    }
    
    public List<Veranstaltung> getVeranstaltungen() {
        return veranstaltungen;
    }

    public void setVeranstaltungen(List<Veranstaltung> veranstaltungen) {
        this.veranstaltungen = veranstaltungen;
    }

    // fügt Benutzer hinzu
    public void addUser(User theUser) {

	if (veranstaltendeUser == null) {
	    veranstaltendeUser = new ArrayList<>();
	}

	veranstaltendeUser.add(theUser);
    }
    
    // fügt Veranstaltung hinzu
    public void addVeranstaltung(Veranstaltung theVeranstaltung) {

	if (veranstaltungen == null) {
	    veranstaltungen = new ArrayList<>();
	}

	veranstaltungen.add(theVeranstaltung);
    }

    public Veranstalter merge(Veranstalter eingang) {

	if (eingang.getVeranstaltendeUser() != null)
	    this.setVeranstaltendeUser(eingang.getVeranstaltendeUser());
	
	if (eingang.getVeranstaltungen() != null)
	    this.setVeranstaltungen(eingang.getVeranstaltungen());

	if (eingang.getVeranstalterName() != null)
	    this.setVeranstalterName(eingang.getVeranstalterName());

	return this;
    }

}
