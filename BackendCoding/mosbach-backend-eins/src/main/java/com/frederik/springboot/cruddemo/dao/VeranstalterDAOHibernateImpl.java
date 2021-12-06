package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Repository
public class VeranstalterDAOHibernateImpl implements VeranstalterDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public VeranstalterDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // alle vorhandenen Veranstalter aus der DB werden als Liste zurück gegeben
    @Override
    public List<Veranstalter> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Veranstalter> theQuery = currentSession.createQuery("from Veranstalter", Veranstalter.class);

	// execute query and get result list
	List<Veranstalter> Veranstaltern = theQuery.getResultList();

	// return the results
	return Veranstaltern;
    }

    // der Veranstalter in der DB mit theId wird zurückgegeben
    @Override
    public Veranstalter findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstalter
	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, theId);

	// return the Veranstalter
	return theVeranstalter;
    }

    // der neue Veranstalter wird in der DB gespeichert
    @Override
    public void save(Veranstalter theVeranstalter) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Veranstalter
	currentSession.saveOrUpdate(theVeranstalter);
    }

    // der Veranstalter in der DB mit theId wird gelöscht
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from Veranstalter where id=:veranstalterid");
	theQuery.setParameter("veranstalterid", theId);

	theQuery.executeUpdate();
    }

    // für den Veranstalter mit theId werden alle zugewiesenen
    // User als Liste zurück gegeben
    @Override
    public List<User> findVeranstalterUser(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstalter

	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, theId);
	// get Unterkategories User
	List<User> theUsers = theVeranstalter.getVeranstaltendeUser();

	// return the Veranstalter
	return theUsers;
    }

    // zu vorhandenem Veranstalter wird vorhandener User zugewiesen
    @Override
    public void addVeranstaltendeUser(int veranstalterId, int userId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	User theUser = currentSession.get(User.class, userId);
	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);

	theUser.addVeranstalter(theVeranstalter);
	// theVeranstalter.addUser(theUser);

	currentSession.saveOrUpdate(theUser);
	currentSession.saveOrUpdate(theVeranstalter);

    }

    // neue theVeranstaltung wird in der DB gespeichert und
    // vorhandener Veranstalter mit veranstalterId wird verknüpft
    @Override
    public Veranstaltung addVeranstaltung(int veranstalterId, Veranstaltung theVeranstaltung) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);

	theVeranstalter.addVeranstaltung(theVeranstaltung);

	currentSession.saveOrUpdate(theVeranstalter);
	currentSession.saveOrUpdate(theVeranstaltung);

	return theVeranstaltung;

    }

    // vorhandene Veranstaltung mit veranstaltungId und vorhandener
    // Veranstalter mit veranstalterId werden verknüpft
    @Override
    public Veranstaltung addVeranstaltung(int veranstalterId, int veranstaltungId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstalter.addVeranstaltung(theVeranstaltung);

	currentSession.saveOrUpdate(theVeranstalter);
	currentSession.saveOrUpdate(theVeranstaltung);

	return theVeranstaltung;
    }

    // für den Veranstalter mit veranstalterId werden alle Veranstaltungen zurückgegeben
    @Override
    public List<Veranstaltung> findVeranstalterVeranstaltung(int veranstalterId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);

	return theVeranstalter.getVeranstaltungen();
    }

}
