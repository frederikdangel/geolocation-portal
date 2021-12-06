package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Selection;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public UserDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // gibt alle User aus der DB als Liste zurück
    @Override
    public List<User> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<User> theQuery = currentSession.createQuery("from User", User.class);

	// execute query and get result list
	List<User> Usern = theQuery.getResultList();

	// return the results
	return Usern;
    }

    // gibt den User mit der ID theId aus der DB zurück
    @Override
    public User findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the User
	User theUser = currentSession.get(User.class, theId);

	// return the User
	return theUser;
    }

    // speichert den User theUser in der DB
    @Override
    public void save(User theUser) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save User
	currentSession.saveOrUpdate(theUser);
    }

    // löscht den User mit der ID theId aus der DB
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from User where id=:userid");
	theQuery.setParameter("userid", theId);

	theQuery.executeUpdate();
    }

    // findet für den User mit der ID userId alle Veranstaltungen
    // und gibt diese als Liste zurück
    @Override
    public List<Veranstaltung> findUserVeranstaltungen(int userId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the User
	User theUser = currentSession.get(User.class, userId);

	// get Users Veranstaltungen

	List<Veranstaltung> theVeranstaltungen = theUser.getVeranstaltungsBesucher();

	// return Users Veranstaltungen

	return theVeranstaltungen;
    }

    // findet für den User mit userId all Veranstalter, für die er verifiziert ist
    @Override
    public List<Veranstalter> findUserAlsVeranstalter(int userId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the User
	User theUser = currentSession.get(User.class, userId);

	// get Veranstalter, denen User zugeordnet ist
	List<Veranstalter> theVeranstalters = theUser.getVeranstaltendeUser();

	// return Veranstalter, denen User zugeordnet ist
	return theVeranstalters;
    }

    // findet für den User mit userId alle Oberkategorien
    // und gibt diese als Liste zurück
    @Override
    public List<Oberkategorie> findUserOberkategorien(int userId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the User
	User theUser = currentSession.get(User.class, userId);

	// get Users Oberkategorien
	List<Oberkategorie> usersOberkategories = theUser.getUserOberkategorien();
	return usersOberkategories;
    }

    // findet für den User mit userId alle Unterkategorien und gibt
    // diese als Liste zurück
    @Override
    public List<Unterkategorie> findUserUnterkategorien(int userId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the User
	User theUser = currentSession.get(User.class, userId);

	// get Users Oberkategorien
	List<Unterkategorie> usersUnterkategories = theUser.getUserUnterkategorien();
	return usersUnterkategories;
    }

    // der User mit userId wird mit der Veranstaltung veranstaltungId verknüpft
    // --> meldet sich an
    @Override
    public void addUserVeranstaltungById(int userId, int veranstaltungId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);
	
	User theUser = currentSession.get(User.class, userId);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// add the veranstaltung to the user and vice versa
	theUser.addVeranstaltung(theVeranstaltung);
	//theVeranstaltung.addUser(theUser);

	currentSession.saveOrUpdate(theUser);
	currentSession.saveOrUpdate(theVeranstaltung);

    }

    // User mit userId und Veranstalter mit veranstalterId werden verknüpft
    // --> User wird als Veranstalter verifiziert
    @Override
    public void addUserAlsVeranstalterById(int userId, int veranstalterId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);
	// get the User
	User theUser = currentSession.get(User.class, userId);
	// get the Veranstalter
	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);

	theUser.addVeranstalter(theVeranstalter);
	//theVeranstalter.addUser(theUser);

	currentSession.saveOrUpdate(theUser);
	currentSession.saveOrUpdate(theVeranstalter);

    }

    // User mit userId und Oberkategorie mit oberkategorieId werden verknüpft
    // 
    @Override
    public void addUserOberkategorieById(int userId, int oberkategorieId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);
	// get the User
	User theUser = currentSession.get(User.class, userId);
	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);

	theUser.addOberkategorie(theOberkategorie);
	//theOberkategorie.addUser(theUser);

	currentSession.saveOrUpdate(theUser);
	currentSession.saveOrUpdate(theOberkategorie);

    }

    //User mit userId und Unterkategorie mit unterkategorieId werden verknüpft
    @Override
    public void addUserUnterkategorieById(int userId, int unterkategorieId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);
	// get the User
	User theUser = currentSession.get(User.class, userId);
	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	theUser.addUnterkategorie(theUnterkategorie);
	//theUnterkategorie.addUser(theUser);

	currentSession.saveOrUpdate(theUser);
	currentSession.saveOrUpdate(theUnterkategorie);

    }

    // die neue Veranstaltung theVeranstaltung wird in der DB gespeichert
    // und mit dem User zur userId verknüpft
    @Override
    public void addUserVeranstaltung(int userId, Veranstaltung theVeranstaltung) {
	// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		User theUser = currentSession.get(User.class, userId);

		// add the veranstaltung to the user and vice versa
		theUser.addVeranstaltung(theVeranstaltung);
		//theVeranstaltung.addUser(theUser);

		currentSession.saveOrUpdate(theUser);
		currentSession.saveOrUpdate(theVeranstaltung);
	
    }

    // neuer Veranstalter theVeranstalter wird in der DB gespeichert
    // und der User mit userId als Veranstalter eingetragen
    @Override
    public void addUserAlsVeranstalter(int userId, Veranstalter theVeranstalter) {
	// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		// get the User
		User theUser = currentSession.get(User.class, userId);
		

		theUser.addVeranstalter(theVeranstalter);
		//theVeranstalter.addUser(theUser);

		currentSession.saveOrUpdate(theUser);
		currentSession.saveOrUpdate(theVeranstalter);
	
    }

    // neue Oberkategorie wird angelegt und mit vorhandenem
    // User zur userId verknüpft
    @Override
    public void addUserOberkategorie(int userId, Oberkategorie theOberkategorie) {
	// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		// get the User
		User theUser = currentSession.get(User.class, userId);
		

		theUser.addOberkategorie(theOberkategorie);
		//theOberkategorie.addUser(theUser);

		currentSession.saveOrUpdate(theUser);
		currentSession.saveOrUpdate(theOberkategorie);

	
    }

    // neue Unterkategorie theUnterkategorie angelbt und  mit vorhandenem
    // User zur userId verknüpft
    @Override
    public void addUserUnterkategorie(int userId, Unterkategorie theUnterkategorie) {
	// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		// get the User
		User theUser = currentSession.get(User.class, userId);
		

		theUser.addUnterkategorie(theUnterkategorie);
		//theUnterkategorie.addUser(theUser);

		currentSession.saveOrUpdate(theUser);
		currentSession.saveOrUpdate(theUnterkategorie);
	
    }
    
    // User dessen Email und Passwort mit der Eingabe übereinstimmen
    // wird zurückgegeben
    @Override
    public User findUserByMailAndPW (String email, String pw) {
	/// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<User> theQuery = currentSession.createQuery("select u from User u where u.email=:email and u.pw=:pw", User.class);
		
		theQuery.setParameter("email", email);
		theQuery.setParameter("pw", pw);
		// execute query and get result list
		User theUser= theQuery.getSingleResult();
		
		// return the results
		return theUser;
    }

    // es wird überprüft, ob ein User mit dieser E-Mail existiert
    @Override
    public User existiertMail(String email) {
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<User> theQuery;
	User theUser = null;
	try { theQuery = currentSession.createQuery("select u from User u where u.email=:email", User.class);
	theQuery.setParameter("email", email);
	// execute query and get result list
	theUser= theQuery.getSingleResult();}
	catch (NoResultException n) {
	    
	    return theUser;
	}
	
	
	return theUser;
    }
    
  

    

}
