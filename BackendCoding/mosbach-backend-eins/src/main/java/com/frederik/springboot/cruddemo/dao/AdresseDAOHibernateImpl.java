package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Adresse;

@Repository
public class AdresseDAOHibernateImpl implements AdresseDAO {

    	// Implementierung der Klasse AdresseDAO
    
    
	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public AdresseDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	// gibt eine Liste aller vorhandenen Adressen zurück
	@Override
	public List<Adresse> findAll() {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Adresse> theQuery =
				currentSession.createQuery("from Adresse", Adresse.class);
		
		// execute query and get result list
		List<Adresse> Adressen = theQuery.getResultList();
		
		// return the results		
		return Adressen;
	}


	// findet eine Adresse gemäß der AdressID und gibt die zugehörige Adresse zurück
	@Override
	public Adresse findById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the Adresse
		Adresse theAdresse =
				currentSession.get(Adresse.class, theId);
		
		// return the Adresse
		return theAdresse;
	}


	// speichert die mitgegebene Adresse
	@Override
	public void save(Adresse theAdresse) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// save Adresse
		currentSession.saveOrUpdate(theAdresse);
		
	}


	// löscht die Adresse gemäß der AdressID
	@Override
	public void deleteById(int theId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery(
						"delete from Adresse where id=:adresseid");
		theQuery.setParameter("adresseid", theId);
		
		theQuery.executeUpdate();
	}

}







