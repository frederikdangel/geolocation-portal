package com.frederik.springboot.cruddemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Veranstalterverifizierung")
public class Veranstalterverifizierung {

	/*
	 * @Id = Primärschlüssel
	 */
    @Id
    @Column(name="userid")
    private int userid;
    
    @Column(name="veranstalter")
    private String veranstalter;
    
    @Column(name="persoablage")
    private String persoablage;
    
    public Veranstalterverifizierung() {
	
    }

    // Getter- und Setter-Methoden
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getVeranstalter() {
        return veranstalter;
    }

    public void setVeranstalter(String veranstalter) {
        this.veranstalter = veranstalter;
    }

    public String getPersoablage() {
        return persoablage;
    }

    public void setPersoablage(String persoablage) {
        this.persoablage = persoablage;
    }
    
    
}
