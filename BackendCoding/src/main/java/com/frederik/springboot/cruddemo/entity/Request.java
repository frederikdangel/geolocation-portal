package com.frederik.springboot.cruddemo.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

public class Request {

	private List<Oberkategorie> oberkategorien;

	private List<Unterkategorie> unterkategorien;

	private Timestamp datefrom;

	private Timestamp dateto;

	private String suche;

	public Request() {
	}

	// Getter- und Setter-Methoden
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

	public String getSuche() {
		return suche;
	}

	public void setSuche(String suche) {
		this.suche = suche;
	}

	public List<Oberkategorie> getOberkategorien() {
		return oberkategorien;
	}

	public void setOberkategorien(List<Oberkategorie> oberkategorien) {
		this.oberkategorien = oberkategorien;
	}

	public List<Unterkategorie> getUnterkategorien() {
		return unterkategorien;
	}

	public void setUnterkategorien(List<Unterkategorie> unterkategorien) {
		this.unterkategorien = unterkategorien;
	};

}
