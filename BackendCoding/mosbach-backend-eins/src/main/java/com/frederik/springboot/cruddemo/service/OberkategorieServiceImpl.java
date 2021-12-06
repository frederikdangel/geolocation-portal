package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.OberkategorieDAO;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Service
public class OberkategorieServiceImpl implements OberkategorieService {

	private OberkategorieDAO OberkategorieDAO;
	
	// @Autowired = Verweise werden automatisch injiziert 
	@Autowired
	public OberkategorieServiceImpl(OberkategorieDAO theOberkategorieDAO) {
		OberkategorieDAO = theOberkategorieDAO;
	}
	
	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Oberkategorie> findAll() {
		return OberkategorieDAO.findAll();
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public Oberkategorie findById(int theId) {
		return OberkategorieDAO.findById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void save(Oberkategorie theOberkategorie) {
		OberkategorieDAO.save(theOberkategorie);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void deleteById(int theId) {
		OberkategorieDAO.deleteById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<User> findOberkategorieUser(int theId) {
	    
	    return OberkategorieDAO.findOberkategorieUser(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Unterkategorie> findOberkategorieUnterkategorien(Integer theId) {
	    
	    return OberkategorieDAO.findOberkategorieUnterkategorien(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Veranstaltung> findOberkategorieVeranstaltungen(int oberkategorieId) {
	    return OberkategorieDAO.findOberkategorieVeranstaltungen(oberkategorieId);}

	@Override
	@Transactional
	public void addOberkategorieUser(int oberkategorieId, int userId) {
	    OberkategorieDAO.addOberkategorieUser(oberkategorieId, userId);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addOberkategorieUnterkategorie(int oberkategorieId, int unterkategorieId) {
	    OberkategorieDAO.addOberkategorieUnterkategorie(oberkategorieId, unterkategorieId);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addOberkategorieVeranstaltung(int oberkategorieId, int veranstaltungId) {
	    OberkategorieDAO.addOberkategorieVeranstaltung(oberkategorieId, veranstaltungId);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addOberkategorieUnterkategorie(int oberkategorieId, Unterkategorie theUnterkategorie) {
	    OberkategorieDAO.addOberkategorieUnterkategorie(oberkategorieId, theUnterkategorie);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addOberkategorieVeranstaltung(int oberkategorieId, Veranstaltung theVeranstaltung) {
	    OberkategorieDAO.addOberkategorieVeranstaltung(oberkategorieId, theVeranstaltung);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addOberkategorieUser(int oberkategorieId, User theUser) {
	OberkategorieDAO.addOberkategorieUser(oberkategorieId, theUser);
	    
	}

}






