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
@Table(name = "Oberkategorie")
public class Oberkategorie {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oberkategorieid")
    private int oberkategorieid;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @ManyToMany = n:n - Beziehung
     */
    @JsonIgnoreProperties("userOberkategorien")
    @ManyToMany(mappedBy = "userOberkategorien")
    private List<User> users;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @OneToMany = 1:n - Beziehung
     */
    @JsonIgnoreProperties("oberkategorie")
    @OneToMany(mappedBy = "oberkategorie")
    private List<Unterkategorie> unterkategorien;

    /*
     * @JsonIgnoreProperties = Json ignoriert den folgenden Eintrag
     * @OneToMany = 1:n - Beziehung
     */
    // TODO
    //@JsonIgnoreProperties("oberkategorie")
    @JsonIgnore
    @OneToMany(mappedBy = "oberkategorie")
    private List<Veranstaltung> veranstaltungen;

    @Column(name = "oberkategoriename")
    private String oberkategoriename;

    public Oberkategorie() {

    }

    public Oberkategorie(String oberkategoriename) {
	this.oberkategoriename = oberkategoriename;
    }

    
    // Getter- und Setter-Methoden
    public int getoberkategorieid() {
	return oberkategorieid;
    }

    public void setoberkategorieid(int oberkategorieid) {
	this.oberkategorieid = oberkategorieid;
    }

    public String getoberkategoriename() {
	return oberkategoriename;
    }

    public void setoberkategoriename(String oberkategoriename) {
	this.oberkategoriename = oberkategoriename;
    }

    public List<User> getUsers() {
	return users;
    }

    public void setUsers(List<User> users) {
	this.users = users;
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
	theUser.getUserOberkategorien().add(this);
    }

    // fügt Unterkategorie hinzu
    public void addUnterkategorie(Unterkategorie theUnterkategorie) {

	if (unterkategorien == null) {
	    unterkategorien = new ArrayList<>();
	}

	unterkategorien.add(theUnterkategorie);
    }

    // fügt Veranstaltung hinzu
    public void addVeranstaltung(Veranstaltung theVeranstaltung) {

	if (veranstaltungen == null) {
	    veranstaltungen = new ArrayList<>();
	}

	veranstaltungen.add(theVeranstaltung);
    }

    
    // Getter- und Setter-Methoden
    public List<Unterkategorie> getUnterkategorien() {
	return unterkategorien;
    }

    public void setUnterkategorien(List<Unterkategorie> unterkategorien) {
	this.unterkategorien = unterkategorien;
    }

    
    public Oberkategorie merge(Oberkategorie o) {

	if (o.getoberkategoriename() != null)
	    this.setoberkategoriename(o.getoberkategoriename());

	if (o.getUnterkategorien() != null)
	    this.setUnterkategorien(o.getUnterkategorien());

	if (o.getUsers() != null)
	    this.setUsers(o.getUsers());

	if (o.getVeranstaltungen() != null)
	    this.setVeranstaltungen(o.getVeranstaltungen());

	return this;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + oberkategorieid;
	return result;
    }

    public boolean equals(Object obj) {
   	if (this == obj)
   	    return true;
   	if (obj == null)
   	    return false;
   	if (getClass() != obj.getClass())
   	    return false;
   	Oberkategorie other = (Oberkategorie) obj;
   	if (oberkategorieid != other.oberkategorieid)
   	    return false;
   	return true;
       }
    
    

}
