package com.frederik.springboot.cruddemo.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Repository
public class VeranstaltungDAOHibernateImpl implements VeranstaltungDAO {

    // define field for entitymanager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired
    public VeranstaltungDAOHibernateImpl(EntityManager theEntityManager) {
	entityManager = theEntityManager;
    }

    // eine Liste aller Veranstaltungen in der DB wird zurückgegeben
    @Override
    public List<Veranstaltung> findAll() {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Veranstaltung> theQuery = currentSession.createQuery("from Veranstaltung", Veranstaltung.class);

	// execute query and get result list
	List<Veranstaltung> Veranstaltungn = theQuery.getResultList();

	// return the results
	return Veranstaltungn;
    }

    // die Veranstaltung mit theId wird zurückgegeben
    @Override
    public Veranstaltung findById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	// return the Veranstaltung
	return theVeranstaltung;
    }

    // die neue theVeranstaltung wird in der DB gespeichert
    @Override
    public void save(Veranstaltung theVeranstaltung) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// save Veranstaltung
	currentSession.saveOrUpdate(theVeranstaltung);
    }

    // die Veranstaltung mit theId wird aus der DB gelöscht
    @Override
    public void deleteById(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// delete object with primary key
	Query theQuery = currentSession.createQuery("delete from Veranstaltung where id=:veranstaltungid");
	theQuery.setParameter("veranstaltungid", theId);

	theQuery.executeUpdate();
    }

    // die Oberkategorie zur Veranstaltung mit theId wird zurückgegeben
    @Override
    public Oberkategorie findVeranstaltungsOberkategorie(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	// get VeranstaltungsOberkategorie
	Oberkategorie theOberkategorie = theVeranstaltung.getOberkategorie();

	return theOberkategorie;
    }

    // die Unterkategorie zur Veranstaltung mit theId wird zurückgegeben
    @Override
    public Unterkategorie findVeranstaltungsUnterkategorie(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	// get VeranstaltungsUnterkategorie

	Unterkategorie theUnterkategorie = theVeranstaltung.getUnterkategorie();

	return theUnterkategorie;
    }

    // die Adresse zur Veranstaltung mit theId wird zurückgegeben
    @Override
    public Adresse findVeranstaltungsAdresse(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	// get VeranstaltungsAdresse
	Adresse theAdresse = theVeranstaltung.getAdresse();

	return theAdresse;

    }

    // eine Liste von Usern, die mit der Veranstaltung zur theId verknüpft
    // sind wird zurückgegeben
    @Override
    public List<User> findVeranstaltungsBesucher(int theId) {

	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	// get VeranstaltungsBesucher
	List<User> users = theVeranstaltung.getVeranstaltungsBesucher();

	return users;
    }

    // vorhandene Veranstaltung zur veranstaltungId wird mit vorhandener Oberkategorie
    // zur oberkategorieId verknüpft
    @Override
    public void addVeranstaltungsOberkategorieById(int veranstaltungId, int oberkategorieId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// get the Oberkategorie
	Oberkategorie theOberkategorie = currentSession.get(Oberkategorie.class, oberkategorieId);

	theVeranstaltung.setOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theOberkategorie);

    }

    // vorhandene Veranstaltung mit veranstaltungId wird mit vorhandener Unterkategorie zur
    // unterkategorieId verknüpft
    @Override
    public void addVeranstaltungsUnterkategorieById(int veranstaltungId, int unterkategorieId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// get the Unterkategorie
	Unterkategorie theUnterkategorie = currentSession.get(Unterkategorie.class, unterkategorieId);

	theVeranstaltung.setUnterkategorie(theUnterkategorie);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theUnterkategorie);

    }

    
    //vorhandene Veranstaltung wird mit vorhandener Adresse verknüpft
    @Override
    public void addVeranstaltungsAdresseById(int veranstaltungId, int adressId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// get the Adresse
	Adresse theAdresse = currentSession.get(Adresse.class, adressId);

	theVeranstaltung.setAdresse(theAdresse);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theAdresse);

    }

    // vorhandene Veranstaltung wird mit vorhandenem User verknüpft
    // --> User meldet sich zur Veranstaltung an
    @Override
    public void addVeranstaltungsBesucherById(int veranstaltungId, int userId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// get the User
	User theUser = currentSession.get(User.class, userId);

	theVeranstaltung.addUser(theUser);
	// theUser.addVeranstaltung(theVeranstaltung);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theUser);

    }

    // neuer theUser meldet sich zur vorhandenen Veranstaltung an
    @Override
    public void addVeranstaltungsBesucher(int veranstaltungId, User theUser) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstaltung.addUser(theUser);
	// theUser.addVeranstaltung(theVeranstaltung);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theUser);

    }

    // finde eienn Veranstalter
    @Override
    public Veranstalter findVeranstalter(int theId) {
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, theId);

	return theVeranstaltung.getVeranstalter();
    }

    // vorhandener Veranstalter wird mit vorhandener Veranstaltung verknüpft
    @Override
    public void addVeranstaltungsVeranstalterById(int veranstaltungId, int veranstalterId) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	// get the Adresse
	Veranstalter theVeranstalter = currentSession.get(Veranstalter.class, veranstalterId);

	theVeranstaltung.setVeranstalter(theVeranstalter);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theVeranstalter);

    }

    
    // theVeranstalter wird in der DB angelegt und mit vorhandener Veranstaltung verknüpft
    @Override
    public void addVeranstaltungsVeranstalter(int veranstaltungId, Veranstalter theVeranstalter) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstaltung.setVeranstalter(theVeranstalter);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theVeranstalter);
    }

    // neue theOberkategorie wird angelegt und mit vorhandener Veranstaltung verknüpft
    @Override
    public void addVeranstaltungsOberkategorie(int veranstaltungId, Oberkategorie theOberkategorie) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstaltung.setOberkategorie(theOberkategorie);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theOberkategorie);

    }

    // neue theUnterkategorie wird in der DB angelegt und mit
    // vorhandener Veranstaltung verknüpft
    @Override
    public void addVeranstaltungsUnterkategorie(int veranstaltungId, Unterkategorie theUnterkategorie) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstaltung.setUnterkategorie(theUnterkategorie);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theUnterkategorie);

    }

    
    // neue theAdresse wird in der DB angelgt und mit vorhandenert Veranstaltung verknüpft
    @Override
    public void addVeranstaltungsAdresse(int veranstaltungId, Adresse theAdresse) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// get the Veranstaltung
	Veranstaltung theVeranstaltung = currentSession.get(Veranstaltung.class, veranstaltungId);

	theVeranstaltung.setAdresse(theAdresse);

	currentSession.saveOrUpdate(theVeranstaltung);
	currentSession.saveOrUpdate(theAdresse);
    }

    // alle als Highlight markierten Veranstaltungen werden als Liste zurückgegeben
    @Override
    public List<Veranstaltung> findHighlights() {
	/// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	// create a query
	Query<Veranstaltung> theQuery = currentSession.createQuery("from Veranstaltung where isthighlight = true",
		Veranstaltung.class);

	// execute query and get result list
	List<Veranstaltung> Veranstaltungn = theQuery.getResultList();

	// return the results
	return Veranstaltungn;

    }

    // eine Liste aller freigegebener Veranstaltungen wird zurückgegeben
    @Override
    public List<Veranstaltung> findFreigegebeneVeranstaltungen() {
	/// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	Timestamp datefrom = new Timestamp(System.currentTimeMillis());
	// create a query
	Query<Veranstaltung> theQuery = currentSession.createQuery(
		"select v from Veranstaltung v where v.datefrom >= :datefrom and v.istfreigegeben = true",
		Veranstaltung.class);

	theQuery.setParameter("datefrom", datefrom);
	// execute query and get result list
	List<Veranstaltung> Veranstaltungn = theQuery.getResultList();

	// return the results
	return Veranstaltungn;
    }

    	// alle Veranstaltungen werden nach den Kriterien
    
    	//List<Oberkategorie> oberkategorien 
	//List<Unterkategorie> unterkategorien 
	//String suche
	//Timestamp datefrom 
	//Timestamp dateto
	//List<Veranstaltung> veranstaltungen 
    
    	// durchsucht und alle Veranstaltungen, die den Kriterien entsprechen
    	// werden als Liste zurückgegeben
    @Override
    public List<Veranstaltung> findFilterVeranstaltung(Request request) {
	// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	List<Oberkategorie> oberkategorien = request.getOberkategorien();
	List<Unterkategorie> unterkategorien = request.getUnterkategorien();
	String suche = request.getSuche();
	Timestamp datefrom = request.getDatefrom();
	Timestamp dateto = request.getDateto();
	List<Veranstaltung> veranstaltungen = new ArrayList<Veranstaltung>();

	if (datefrom == null)
	    datefrom = new Timestamp(System.currentTimeMillis());
	if (dateto == null) {
	    Date date = new Date(2999, 12, 31);
	    dateto = new Timestamp(date.getTime());
	}
	if (suche == null) {
	    suche = "";
	}
	// System.out.println("moin");
	// get the Unterkategories Oberkategorie

	/*
	 * 
	 * System.out.println(request.getUnterkategorien().get(0)); //
	 * System.out.println(request.getUnterkategorien().get(1));
	 * System.out.println(request.getUnterkategorien());
	 * System.out.println(request.getUnterkategorien().get(0).getUnterkategorieID())
	 * ; System.out.println(unterkategorien.get(0).getUnterkategorieID());
	 * 
	 */
	// falls oberkategorien und unterkategorien null
	if ((request.getOberkategorien() == null) && (request.getUnterkategorien() == null)) {

	    Query<Veranstaltung> theQuery = currentSession.createQuery("select v from Veranstaltung v " + "where "
		    + "v.datefrom >= :datefrom" + " and (v.dateto <= :dateto or v.dateto is null) and v.istfreigegeben = true"
		    + " and v.titel LIKE CONCAT('%',:suche,'%')"
	    // + " or v.oberkategorieid =:oberkategorie) "
	    // + " and v.unterkategorieid =:unterkategorie)"
		    , Veranstaltung.class);

	    // theQuery.setParameter("oberkategorie", oberkategorie);
	    // theQuery.setParameter("unterkategorie",
	    // unterkategorien.get(i).getUnterkategorieID());
	    theQuery.setParameter("datefrom", datefrom);
	    theQuery.setParameter("dateto", dateto);
	    theQuery.setParameter("suche", suche);

	    List<Veranstaltung> tempveranstaltungen = theQuery.getResultList();

	    for (int j = 0; j < tempveranstaltungen.size(); j++) {
		veranstaltungen.add(tempveranstaltungen.get(j));
	    }
	}

	if (oberkategorien != null) {

	    if (unterkategorien != null) {
		// falls unterkategorien ungleich null....

		// lösche aus den oberkategorien die einträge, die in unterkategorien eh
		// vorhanden sind

		System.out.println("moin");

		for (int i = 0; i < unterkategorien.size(); i++) {

		    if (oberkategorien.contains(unterkategorien.get(i).getOberkategorie())) {
			oberkategorien.remove(unterkategorien.get(i).getOberkategorie());

		    }
		}

		// suche für alle unterkategorien die Veranstaltungen mit den 3 Einschränkungen
		for (int i = 0; i < unterkategorien.size(); i++) {

		    System.out.println(unterkategorien.get(i).getUnterkategorieID());
		    System.out.println(datefrom);
		    System.out.println(dateto);
		    System.out.println(suche);

		    Query<Veranstaltung> theQuery = currentSession.createNativeQuery("select * from Veranstaltung v "
			    + "where v.datefrom >=:datefrom" + " and (v.dateto <=:dateto or v.dateto is null)" 
			    + " and v.unterkategorieid =:unterkategorie and v.istfreigegeben = true"
			    + " and v.titel LIKE CONCAT('%',:suche,'%')"
		    // + " or v.oberkategorieid =:oberkategorie) "
			    , Veranstaltung.class);

		    // theQuery.setParameter("oberkategorie", oberkategorie);
		    theQuery.setParameter("unterkategorie", unterkategorien.get(i).getUnterkategorieID());
		    theQuery.setParameter("datefrom", datefrom);
		    theQuery.setParameter("dateto", dateto);
		    theQuery.setParameter("suche", suche);

		    // execute query and get result list
		    List<Veranstaltung> tempveranstaltungen = theQuery.getResultList();

		    for (int j = 0; j < tempveranstaltungen.size(); j++) {
			veranstaltungen.add(tempveranstaltungen.get(j));
		    }

		}
	    }

	    // falls oberkategorien ungleich null....

	    // suche für alle verbliebenen oberkategorien, für die keine unterkategorie
	    // mitgegeben wurde, die veranstaltungen mit den 3 einschränkungen
	    for (int i = 0; i < oberkategorien.size(); i++) {

		Query<Veranstaltung> theQuery = currentSession.createNativeQuery(
			"select * from Veranstaltung v " + "where v.datefrom >=:datefrom" + " and (v.dateto <=:dateto or v.dateto is null)"
				+ " and v.oberkategorieid =:oberkategorie and v.istfreigegeben = true"
				+ " and v.titel LIKE CONCAT('%',:suche,'%')"
			// + " or v.unterkategorieid = ( select u.unterkategorieid from unterkategorie u
			// where u.unterkategoriename =:unterkategorie)"
			, Veranstaltung.class);

		theQuery.setParameter("oberkategorie", oberkategorien.get(i).getoberkategorieid());
		// theQuery.setParameter("unterkategorie",
		// unterkategorien.get(i).getUnterkategorieID());
		theQuery.setParameter("datefrom", datefrom);
		theQuery.setParameter("dateto", dateto);
		theQuery.setParameter("suche", suche);

		// execute query and get result list
		List<Veranstaltung> tempveranstaltungen = theQuery.getResultList();

		for (int j = 0; j < tempveranstaltungen.size(); j++) {
		    veranstaltungen.add(tempveranstaltungen.get(j));
		}

	    }

	}

	return veranstaltungen;
    }

    // eine Liste aller Veranstaltungen, die noch nicht 
    // freiegegeben sind wird zurückgegeben
    @Override
    public List<Veranstaltung> findFreizugebendeVeranstaltungen() {
	/// get the current hibernate session
	Session currentSession = entityManager.unwrap(Session.class);

	Timestamp datefrom = new Timestamp(System.currentTimeMillis());
	// create a query
	Query<Veranstaltung> theQuery = currentSession.createQuery(
		"select v from Veranstaltung v where v.datefrom >= :datefrom and v.istfreigegeben = false",
		Veranstaltung.class);

	theQuery.setParameter("datefrom", datefrom);
	// execute query and get result list
	List<Veranstaltung> Veranstaltungn = theQuery.getResultList();

	// return the results
	return Veranstaltungn;
    }

}
