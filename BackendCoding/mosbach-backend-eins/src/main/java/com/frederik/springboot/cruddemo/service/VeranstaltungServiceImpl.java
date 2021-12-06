package com.frederik.springboot.cruddemo.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.OberkategorieDAO;
import com.frederik.springboot.cruddemo.dao.UnterkategorieDAO;
import com.frederik.springboot.cruddemo.dao.VeranstaltungDAO;
import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.repository.DEPRECATED.VeranstaltungRepository;

@Service
public class VeranstaltungServiceImpl implements VeranstaltungService {

    private VeranstaltungDAO VeranstaltungDAO;
    private UnterkategorieDAO unterkategorieDAO;
    private OberkategorieDAO oberkategorieDAO;

 // @Autowired = Verweise werden automatisch injiziert 
    @Autowired
    public VeranstaltungServiceImpl(VeranstaltungDAO theVeranstaltungDAO) {
	VeranstaltungDAO = theVeranstaltungDAO;
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findAll() {
	return VeranstaltungDAO.findAll();
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Veranstaltung findById(int theId) {
	return VeranstaltungDAO.findById(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void save(Veranstaltung theVeranstaltung) {
	VeranstaltungDAO.save(theVeranstaltung);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void deleteById(int theId) {
	VeranstaltungDAO.deleteById(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Veranstalter findVeranstalter(int theId) {
	return VeranstaltungDAO.findVeranstalter(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Oberkategorie findVeranstaltungsOberkategorie(int theId) {
	return VeranstaltungDAO.findVeranstaltungsOberkategorie(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Unterkategorie findVeranstaltungsUnterkategorie(int theId) {
	return VeranstaltungDAO.findVeranstaltungsUnterkategorie(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Adresse findVeranstaltungsAdresse(int theId) {
	return VeranstaltungDAO.findVeranstaltungsAdresse(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<User> findVeranstaltungsBesucher(int theId) {
	return VeranstaltungDAO.findVeranstaltungsBesucher(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsBesucherById(int veranstaltungId, int userId) {
	VeranstaltungDAO.addVeranstaltungsBesucherById(veranstaltungId, userId);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsBesucher(int veranstaltungId, User theUser) {
	VeranstaltungDAO.addVeranstaltungsBesucher(veranstaltungId, theUser);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsOberkategorie(int veranstaltungId, Oberkategorie theOberkategorie) {
	VeranstaltungDAO.addVeranstaltungsOberkategorie(veranstaltungId, theOberkategorie);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsUnterkategorie(int veranstaltungId, Unterkategorie theUnterkategorie) {
	VeranstaltungDAO.addVeranstaltungsUnterkategorie(veranstaltungId, theUnterkategorie);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsAdresse(int veranstaltungId, Adresse theAdresse) {
	VeranstaltungDAO.addVeranstaltungsAdresse(veranstaltungId, theAdresse);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsOberkategorieById(int veranstaltungId, int oberkategorieId) {
	VeranstaltungDAO.addVeranstaltungsOberkategorieById(veranstaltungId, oberkategorieId);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsUnterkategorieById(int veranstaltungId, int unterkategorieId) {
	VeranstaltungDAO.addVeranstaltungsUnterkategorieById(veranstaltungId, unterkategorieId);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsAdresseById(int veranstaltungId, int adressId) {
	VeranstaltungDAO.addVeranstaltungsAdresseById(veranstaltungId, adressId);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addVeranstaltungsVeranstalterById(int veranstaltungId, int veranstalterId) {
	VeranstaltungDAO.addVeranstaltungsVeranstalterById(veranstaltungId, veranstalterId);

    }

    @Override
    @Transactional
    public void addVeranstaltungsVeranstalter(int veranstaltungId, Veranstalter theVeranstalter) {
	VeranstaltungDAO.addVeranstaltungsVeranstalter(veranstaltungId, theVeranstalter);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findHighlights() {
	return VeranstaltungDAO.findHighlights();
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findFilterVeranstaltung(Request request) {

	
	return VeranstaltungDAO.findFilterVeranstaltung(request);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findFreigegebeneVeranstaltungen() {
	return VeranstaltungDAO.findFreigegebeneVeranstaltungen();
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findFreizugebendeVeranstaltungen() {
	

	return VeranstaltungDAO.findFreizugebendeVeranstaltungen();
    }

}
