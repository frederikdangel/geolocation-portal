package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Repository
public class OberkategorieDAOHibernateImpl implements OberkategorieDAO {

    // Implementierung der Klasse OberkategorieDAO

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public OberkategorieDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // gibt eine Liste aller vorhandener Oberkategorien zurück
    @Override
    public List<Oberkategorie> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Oberkategorie> theQuery = currentSession.createQuery("from Oberkategorie", Oberkategorie.class);

	// execute query and get result list
	List<Oberkategorie> Oberkategorien = theQuery.getResultList();

	// return the results
	return Oberkategorien;
    }

    // findet eine Oberkategorie gemäß der ID und gibt die entsprechende Oberkategorie zurück
    @Override
    public Oberkategorie findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, theId);

	// return the Oberkategorie
	return theOberkategorie;
    }

    // speichert die mitgegebene Oberkategorie in der Datenbank
    @Override
    public void save(Oberkategorie theOberkategorie) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Oberkategorie
	currentSession.saveOrUpdate(theOberkategorie);
    }

    // löscht die zugehörige Oberkategorie zur mitgegebenen ID
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from Oberkategorie where id=:oberkategorieid");
	theQuery.setParameter("oberkategorieid", theId);

	theQuery.executeUpdate();

    }

    // findet für die zugehörige Oberkategorie mit der mitgegebenen ID alle User und gibt diese als Liste zurück
    @Override
    public List<User> findOberkategorieUser(int theId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, theId);

	// get Oberkategories User
	List<User> theUsers = theOberkategorie.getUsers();

	// return the Oberkategorie
	return theUsers;
    }

    // findet für die zugehörige Oberkategorie mit der mitgegebenen ID alle Unterkategorien und gibt diese als Liste zurück
    @Override
    public List<Unterkategorie> findOberkategorieUnterkategorien(Integer theId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, theId);

	// get Oberkategories User
	List<Unterkategorie> unterkategorien = theOberkategorie.getUnterkategorien();

	// return the Oberkategorie
	return unterkategorien;
    }

    // verknüpft die Oberkategorie mit der oberkategorieId mit der Unterkategorie mit der unterkategorieId
    @Override
    public void addOberkategorieUnterkategorie(int oberkategorieId, int unterkategorieId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	theOberkategorie.addUnterkategorie(theUnterkategorie);
	//theUnterkategorie.setOberkategorie(theOberkategorie);

	// save Oberkategorie
	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theUnterkategorie);
    }

    // speichert die neue Unterkategorie theUnterkategorie und verknüpft sie mit der Oberkategorie zur oberkategorieId
    @Override
    public void addOberkategorieUnterkategorie(int oberkategorieId, Unterkategorie theUnterkategorie) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);

	theOberkategorie.addUnterkategorie(theUnterkategorie);
	//theUnterkategorie.setOberkategorie(theOberkategorie);

	// save Oberkategorie
	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theUnterkategorie);
    }

    // gibt für die Oberkategorie mit theId eine Liste aller zugehöriger Veranstaltungen zurück
    @Override
    public List<Veranstaltung> findOberkategorieVeranstaltungen(Integer theId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, theId);

	// get Oberkategories User
	List<Veranstaltung> veranstaltungen = theOberkategorie.getVeranstaltungen();
	return veranstaltungen;
    }

    // verknüpft die Veranstaltung mit veranstaltungId und die Oberkategorie mit oberkategorieId
    @Override
    public void addOberkategorieVeranstaltung(int oberkategorieId, int veranstaltungId) {
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);
	System.out.println(theOberkategorie.getoberkategoriename());
	

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);
	System.out.println(theVeranstaltung.getTitel());

	theOberkategorie.addVeranstaltung(theVeranstaltung);
	theVeranstaltung.setOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theVeranstaltung);

    }

    // speichert die Veranstaltung theVeranstaltung mit der Verknüpfung der Oberkategorie zur oberkategorieId in der
    // Datenbank
    @Override
    public void addOberkategorieVeranstaltung(int oberkategorieId, Veranstaltung theVeranstaltung) {
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);

	theOberkategorie.addVeranstaltung(theVeranstaltung);
	//theVeranstaltung.setOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theVeranstaltung);

    }

    // verknüpft den User mit userID und die Oberkategorie mit oberkategorieId
    @Override
    public void addOberkategorieUser(int oberkategorieId, int userId) {
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);
	User theUser = currentSession.get(User.class, userId);

	theOberkategorie.addUser(theUser);
	//theUser.addOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theUser);

    }

    // speichert den User theUser in der DB und verknüpft mit der Oberkategorie zur oberkategorieId
    @Override
    public void addOberkategorieUser(int oberkategorieId, User theUser) {
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);
	theOberkategorie.addUser(theUser);
	//theUser.addOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theOberkategorie);
	currentSession.saveOrUpdate(theUser);
    }

}
