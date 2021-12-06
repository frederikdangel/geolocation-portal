package com.frederik.springboot.cruddemo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Parkplatz")
public class Parkplatz {

	/*
	 * @Id = Primärschlüssel
	 * @GeneratedValue = autpmatische Generierung des Primärschlüssels
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parkplatzid")
    private Integer parkplatzID;

    @Column(name = "parkplatzname")
    private String parkplatzName;

    /*
     * @OneToOne = 1:1 - Beziehung
     * @JoinColumn = Definiert eine Fremdschlüsselspalte
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adressid")
    private Adresse adresse;

    public Parkplatz() {

    }

    public Parkplatz(String parkplatzName) {
	this.parkplatzName = parkplatzName;
    }

    
    // Getter- und Setter-Methoden
    public Integer getParkplatzID() {
	return parkplatzID;
    }

    public String getParkplatzName() {
	return parkplatzName;
    }

    public void setParkplatzName(String parkplatzName) {
	this.parkplatzName = parkplatzName;
    }

    public Adresse getAdresse() {
	return adresse;
    }

    public void setAdresse(Adresse adresse) {
	this.adresse = adresse;
    }

    public void setParkplatzID(Integer parkplatzID) {
	this.parkplatzID = parkplatzID;
    }
    

    public Parkplatz merge(Parkplatz p) {

	if (p.getAdresse() != null)
	    this.setAdresse(p.getAdresse());

	if (p.getParkplatzName() != null)
	    this.setParkplatzName(p.getParkplatzName());

	return this;
    }

}
