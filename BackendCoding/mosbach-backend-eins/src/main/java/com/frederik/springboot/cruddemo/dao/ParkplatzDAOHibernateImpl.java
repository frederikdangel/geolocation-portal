package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Parkplatz;

@Repository
public class ParkplatzDAOHibernateImpl implements ParkplatzDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public ParkplatzDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // gibt alle vorhandenen Parkplätze aus der DB als Liste zurück
    @Override
    public List<Parkplatz> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Parkplatz> theQuery = currentSession.createQuery("from Parkplatz", Parkplatz.class);

	// execute query and get result list
	List<Parkplatz> Parkplatzn = theQuery.getResultList();

	// return the results
	return Parkplatzn;
    }

    // gibt den Parkplatz mit der ID theId zurück
    @Override
    public Parkplatz findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Parkplatz
	Parkplatz theParkplatz = currentSession.get(Parkplatz.class, theId);

	// return the Parkplatz
	return theParkplatz;
    }

    // speichert den neuen Parkplatz in der DB
    @Override
    public void save(Parkplatz theParkplatz) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Parkplatz
	currentSession.saveOrUpdate(theParkplatz);
    }

    // löscht den Parkplatz aus der DB mit der ID theId
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from Parkplatz where id=:parkplatzid");
	theQuery.setParameter("parkplatzid", theId);

	theQuery.executeUpdate();
    }

    // gibt für den Parkplatz mit der ID theID die Adresse zurück
    @Override
    public Adresse findParkplatzAdresse(int theId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Parkplatz
	Parkplatz theParkplatz = currentSession.get(Parkplatz.class, theId);

	// get Parkplatz´s Adresse
	Adresse theAdresse = theParkplatz.getAdresse();

	// return Adresse
	return theAdresse;

    }

    // speichert die Adresse
    @Override
    public void saveParkplatzAdresse(Adresse theAdresse) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Parkplatz
	currentSession.saveOrUpdate(theAdresse);

    }

    // verknüpft den Parkplatz mit parkplatzId mit der Adresse mit der adressId
    @Override
    public void addParkplatzAdresse(int parkplatzId, int adressId) {
	
	// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// get the Parkplatz
		Parkplatz theParkplatz = currentSession.get(Parkplatz.class, parkplatzId);

		// get Parkplatz´s Adresse
		Adresse theAdresse = currentSession.get(Adresse.class, adressId);
		
		theParkplatz.setAdresse(theAdresse);
		
		currentSession.saveOrUpdate(theParkplatz);
		currentSession.saveOrUpdate(theAdresse);
	
    }

}
