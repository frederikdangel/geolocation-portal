package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Repository
public class UnterkategorieDAOHibernateImpl implements UnterkategorieDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public UnterkategorieDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // gibt alle Unterkategorien aus der DB zurück
    @Override
    public List<Unterkategorie> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Unterkategorie> theQuery = currentSession.createQuery("from Unterkategorie", Unterkategorie.class);

	// execute query and get result list
	List<Unterkategorie> Unterkategorien = theQuery.getResultList();

	// return the results
	return Unterkategorien;
    }

    // gibt die Unterkategorie mit der ID theId zurück
    @Override
    public Unterkategorie findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, theId);

	// return the Unterkategorie
	return theUnterkategorie;
    }

    // speichert die Unterkategorie theUnterkategorie in der DB
    @Override
    public void save(Unterkategorie theUnterkategorie) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Unterkategorie

	currentSession.saveOrUpdate(theUnterkategorie);

    }

    // löscht die zugehörige Unterkategorie zu theId
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from Unterkategorie where id=:unterkategorieid");
	theQuery.setParameter("unterkategorieid", theId);

	theQuery.executeUpdate();
    }

    // findet für die Unterkategorie zur theId alle User aus der DB und gibt diese als Liste zurück
    @Override
    public List<User> findUnterkategorieUser(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, theId);

	// get Unterkategories User
	List<User> theUsers = theUnterkategorie.getUsers();

	// return the Unterkategorie
	return theUsers;
    }

    // gibt für die Unterkategorie mit theId die Oberkategorie zurück
    @Override
    public Oberkategorie findUnterkategoriesOberkategorie(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, theId);

	// get Unterkategories Oberkategorie
	Oberkategorie theOberkategorie = theUnterkategorie.getOberkategorie();

	// return the Unterkategorie
	return theOberkategorie;
    }

    // gibt für die Unterkategorie mit UnterkategorieId alle zugehörigen Veranstaltungen als Liste zurück
    @Override
    public List<Veranstaltung> findUnterkategorieVeranstaltungen(int unterkategorieId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	return theUnterkategorie.getVeranstaltungen();
    }

    // verknüpft die Unterkategorie mit unterkategorieId und die 
    // Veranstaltung mit veranstaltungId
    @Override
    public void addUnterkategorieVeranstaltung(int unterkategorieId, int veranstaltungId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theUnterkategorie.addVeranstaltung(theVeranstaltung);
	theVeranstaltung.setUnterkategorie(theUnterkategorie);
	theVeranstaltung.setOberkategorie(theUnterkategorie.getOberkategorie());
	

	currentSession.saveOrUpdate(theUnterkategorie);
	currentSession.saveOrUpdate(theVeranstaltung);

    }

    // legt die neue Veranstaltung theVeranstaltung an und verknüpft sie mit der Unterkategorie
    // zur unterkategorieId
    @Override
    public void addUnterkategorieVeranstaltung(int unterkategorieId, Veranstaltung theVeranstaltung) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	theUnterkategorie.addVeranstaltung(theVeranstaltung);
	theVeranstaltung.setUnterkategorie(theUnterkategorie);
	theVeranstaltung.setOberkategorie(theUnterkategorie.getOberkategorie());

	currentSession.saveOrUpdate(theUnterkategorie);
	currentSession.saveOrUpdate(theVeranstaltung);

    }

    // speichert den neuen User theUser in der DB und verknüpft ihn mit der Unterkategorie
    // zur unterkategorieId
    @Override
    public void addUnterkategorieUser(int unterkategorieId, User theUser) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	theUnterkategorie.addUser(theUser);
	//theUser.addUnterkategorie(theUnterkategorie);

	currentSession.saveOrUpdate(theUnterkategorie);
	currentSession.saveOrUpdate(theUser);

    }

    // verknüpft den User mit userId und die Unterkategorie mit unterkategorieId
    @Override
    public void addUnterkategorieUser(int unterkategorieId, int userId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	// get the Veranstaltung
	User theUser = currentSession.get(User.class, userId);

	theUnterkategorie.addUser(theUser);
	//theUser.addUnterkategorie(theUnterkategorie);

	currentSession.saveOrUpdate(theUnterkategorie);
	currentSession.saveOrUpdate(theUser);

    }

}
