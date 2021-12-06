package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.ParkplatzDAO;
import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Parkplatz;

@Service
public class ParkplatzServiceImpl implements ParkplatzService {

	private ParkplatzDAO ParkplatzDAO;
	
	@Autowired
	public ParkplatzServiceImpl(ParkplatzDAO theParkplatzDAO) {
		ParkplatzDAO = theParkplatzDAO;
	}
	
	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Parkplatz> findAll() {
		return ParkplatzDAO.findAll();
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public Parkplatz findById(int theId) {
		return ParkplatzDAO.findById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void save(Parkplatz theParkplatz) {
		ParkplatzDAO.save(theParkplatz);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void deleteById(int theId) {
		ParkplatzDAO.deleteById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public Adresse findParkplatzAdresse(int theId) {
	    
	    return ParkplatzDAO.findParkplatzAdresse(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
	@Override
	public void addParkplatzAdresse(int parkplatzId, int adressId) {
	    
	     ParkplatzDAO.addParkplatzAdresse(parkplatzId, adressId);
	    
	}

}






