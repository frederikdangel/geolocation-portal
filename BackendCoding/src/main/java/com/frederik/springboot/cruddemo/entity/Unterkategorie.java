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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Unterkategorie")
public class Unterkategorie {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unterkategorieid")
    private Integer unterkategorieID;

    @Column(name = "unterkategoriename")
    private String unterkategoriename;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToOne = n:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("unterkategorien")
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinColumn(name = "oberkategorieid")
    private Oberkategorie oberkategorie;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @OneToMany = 1:n - Beziehung
     */
    // TODO
    //@JsonIgnoreProperties("unterkategorie")
    @JsonIgnore
    @OneToMany(mappedBy = "unterkategorie")
    private List<Veranstaltung> veranstaltungen;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     * @JoinTable = Bereitstellung der Tabelle, die für den Fremdschlüssel verwendet wird
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @JsonIgnoreProperties("userUnterkategorien")
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
	    CascadeType.REFRESH })
    @JoinTable(name = "userunterkategorien", joinColumns = {
	    @JoinColumn(name = "unterkategorieid") }, inverseJoinColumns = { @JoinColumn(name = "userid") })
    private List<User> users;

    public List<User> getUsers() {
	return users;
    }

    public void setUsers(List<User> users) {
	this.users = users;
    }

    public Unterkategorie() {

    }

    public Unterkategorie(String unterkategoriename) {
	this.unterkategoriename = unterkategoriename;
    }

    
    // Getter- und Setter-Methoden
    public Integer getUnterkategorieID() {
	return unterkategorieID;
    }

    public void setUnterkategorieID(Integer unterkategorieID) {
	this.unterkategorieID = unterkategorieID;
    }

    public String getunterkategoriename() {
	return unterkategoriename;
    }

    public void setunterkategoriename(String unterkategoriename) {
	this.unterkategoriename = unterkategoriename;
    }

    public Oberkategorie getOberkategorie() {
	return oberkategorie;
    }

    public void setOberkategorie(Oberkategorie oberkategorie) {
	this.oberkategorie = oberkategorie;
    }

    public void addVeranstaltung(Veranstaltung theVeranstaltung) {

	if (veranstaltungen == null) {
	    veranstaltungen = new ArrayList<>();
	}

	veranstaltungen.add(theVeranstaltung);
    }

    public List<Veranstaltung> getVeranstaltungen() {
	return veranstaltungen;
    }

    public void setVeranstaltungen(List<Veranstaltung> veranstaltungen) {
	this.veranstaltungen = veranstaltungen;
    }

    
    // fügt Benutzer hinzu
    public void addUser(User theUser) {

	if (users == null) {
	    users = new ArrayList<>();
	}

	users.add(theUser);
    }
    
    // entfernt Benutzer
    public void removeUser(User theUser) {

	if (users.contains(theUser)) {
	    this.users.remove(theUser);
	    theUser.getUserUnterkategorien().remove(this);

	} else
	    throw new ArrayIndexOutOfBoundsException("User konnte nicht entfernt werden");
    }
 
    public Unterkategorie merge(Unterkategorie e) {

	if (e.getOberkategorie() != null)
	    this.setOberkategorie(e.getOberkategorie());

	if (e.getunterkategoriename() != null)
	    this.setunterkategoriename(e.getunterkategoriename());

	if (e.getUsers() != null)
	    this.setUsers(e.getUsers());

	if (e.getVeranstaltungen() != null)
	    this.setVeranstaltungen(e.getVeranstaltungen());

	return this;
    }
    
    public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Unterkategorie other = (Unterkategorie) obj;
	if (unterkategorieID == null) {
		if (other.unterkategorieID != null)
			return false;
	} else if (!unterkategorieID.equals(other.unterkategorieID))
		return false;
	return true;
}

}
