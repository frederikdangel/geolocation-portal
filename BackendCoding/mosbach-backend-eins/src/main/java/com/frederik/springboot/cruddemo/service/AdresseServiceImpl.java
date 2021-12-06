package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.AdresseDAO;
import com.frederik.springboot.cruddemo.entity.Adresse;

@Service
public class AdresseServiceImpl implements AdresseService{

	// @Autowired = Verweise werden automatisch injiziert 
    @Autowired
	private AdresseDAO adresseDAO;
	
    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public List<Adresse> findAll() {
		return adresseDAO.findAll();
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public Adresse findById(Integer theId) {
		return adresseDAO.findById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void save(Adresse theAdresse) {
		adresseDAO.save(theAdresse);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void deleteById(Integer theId) {
		adresseDAO.deleteById(theId);
	}

	/*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
	@Override
	@Transactional
	public void update(Adresse theAdresse) {
	    adresseDAO.save(theAdresse);
	    
	}

}






