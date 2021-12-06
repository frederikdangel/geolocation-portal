package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.VeranstalterDAO;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Service
public class VeranstalterServiceImpl implements VeranstalterService {

	private VeranstalterDAO VeranstalterDAO;
	
	// @Autowired = Verweise werden automatisch injiziert 
	@Autowired
	public VeranstalterServiceImpl(VeranstalterDAO theVeranstalterDAO) {
		VeranstalterDAO = theVeranstalterDAO;
	}
	
	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Veranstalter> findAll() {
		return VeranstalterDAO.findAll();
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public Veranstalter findById(int theId) {
		return VeranstalterDAO.findById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void save(Veranstalter theVeranstalter) {
		VeranstalterDAO.save(theVeranstalter);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		VeranstalterDAO.deleteById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<User> findVeranstalterUser(int theId) {
	    
	    return VeranstalterDAO.findVeranstalterUser(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void addVeranstalterUser(int veranstalterId, int userId) {
	    VeranstalterDAO.addVeranstaltendeUser(veranstalterId, userId);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
	@Override
	public void addVeranstalterVeranstaltung(int veranstalterId, Veranstaltung theVeranstaltung) {
	    VeranstalterDAO.addVeranstaltung(veranstalterId, theVeranstaltung);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
	@Override
	public void addVeranstalterVeranstaltung(int veranstalterId, int veranstaltungId) {
	    VeranstalterDAO.addVeranstaltung(veranstalterId, veranstaltungId);
	    
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
	@Override
	public List<Veranstaltung> findVeranstalterVeranstaltung(int veranstalterId) {
	    return VeranstalterDAO.findVeranstalterVeranstaltung(veranstalterId); }

}






