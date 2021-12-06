package com.frederik.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Adresse")
public class Adresse {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adressid")
    private Integer adressID;

    @Column(name = "straße")
    private String straße;

    @Column(name = "hausnummer")
    private int hausnummer;

    @Column(name = "ort")
    private String ort;

    @Column(name = "laengengrad")
    private double laengengrad;

    @Column(name = "breitengrad")
    private double breitengrad;

    public Adresse() {

    }

    public Adresse(String straße, int hausnummer, String ort, double laengengrad, double breitengrad) {
	this.straße = straße;
	this.hausnummer = hausnummer;
	this.ort = ort;
	this.laengengrad = laengengrad;
	this.breitengrad = breitengrad;
    }

    
    // Getter- und Setter-Methoden
    public Integer getAdressID() {
	return adressID;
    }

    public void setAdressID(Integer adressID) {
	this.adressID = adressID;
    }

    public String getStraße() {
	return straße;
    }

    public void setStraße(String straße) {
	this.straße = straße;
    }

    public int getHausnummer() {
	return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
	this.hausnummer = hausnummer;
    }

    public String getOrt() {
	return ort;
    }

    public void setOrt(String ort) {
	this.ort = ort;
    }

    public double getLaengengrad() {
	return laengengrad;
    }

    public void setLaengengrad(double laengengrad) {
	this.laengengrad = laengengrad;
    }

    public double getBreitengrad() {
	return breitengrad;
    }

    public void setBreitengrad(double breitengrad) {
	this.breitengrad = breitengrad;
    }
    
    
    
    public Adresse merge(Adresse eingang) {

	if (eingang.getBreitengrad() != 0)
	    this.setBreitengrad(eingang.getBreitengrad());

	if (eingang.getHausnummer() != 0)
	    this.setHausnummer(eingang.getHausnummer());

	if (eingang.getLaengengrad() != 0)
	    this.setLaengengrad(eingang.getLaengengrad());

	if (eingang.getOrt() != null)
	    this.setOrt(eingang.getOrt());

	if (eingang.getStraße() != null)
	    this.setStraße(eingang.getStraße());

	return this;
    }

    // Gibt Adresse mit Details zurück
    @Override
    public String toString() {
	return "Adresse [adressID=" + adressID + ", straße=" + straße + ", hausnummer=" + hausnummer + ", ort=" + ort
		+ ", laengengrad=" + laengengrad + ", breitengrad=" + breitengrad + "]";
    }

}
