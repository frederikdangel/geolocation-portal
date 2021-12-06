package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.UserDAO;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO UserDAO;

    // @Autowired = Verweise werden automatisch injiziert 
    @Autowired
    public UserServiceImpl(UserDAO theUserDAO) {
	UserDAO = theUserDAO;
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<User> findAll() {
	return UserDAO.findAll();
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public User findById(int theId) {
	return UserDAO.findById(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void save(User theUser) {
	UserDAO.save(theUser);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void deleteById(int theId) {
	UserDAO.deleteById(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstaltung> findUserVeranstaltungen(int userId) {

	return UserDAO.findUserVeranstaltungen(userId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstalter> findUserAlsVeranstalter(int userId) {

	return UserDAO.findUserAlsVeranstalter(userId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Oberkategorie> findUserOberkategorien(int userId) {

	return UserDAO.findUserOberkategorien(userId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Unterkategorie> findUserUnterkategorien(int userId) {

	return UserDAO.findUserUnterkategorien(userId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserVeranstaltungById(int userId, int veranstaltungId) {
	UserDAO.addUserVeranstaltungById(userId, veranstaltungId);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserAlsVeranstalterById(int userId, int veranstalterId) {
	UserDAO.addUserAlsVeranstalterById( userId,  veranstalterId);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserOberkategorieById(int userId, int oberkategorieId) {
	UserDAO.addUserOberkategorieById(userId, oberkategorieId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserUnterkategorieById(int userId, int unterkategorieId) {
	UserDAO.addUserUnterkategorieById(userId, unterkategorieId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserVeranstaltung(int userId, Veranstaltung theVeranstaltung) {
	UserDAO.addUserVeranstaltung(userId, theVeranstaltung);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserAlsVeranstalter(int userId, Veranstalter theVeranstalter) {
	UserDAO.addUserAlsVeranstalter(userId, theVeranstalter);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserOberkategorie(int userId, Oberkategorie theOberkategorie) {
	UserDAO.addUserOberkategorie(userId, theOberkategorie);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void addUserUnterkategorie(int userId, Unterkategorie theUnterkategorie) {
	UserDAO.addUserUnterkategorie(userId, theUnterkategorie);
	
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
    @Override
    public User findUserByMailAndPW(String email, String pw) {
	

	return UserDAO.findUserByMailAndPW(email, pw);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     */
    @Override
    public boolean existiertMail(String email) {
	
	return (UserDAO.existiertMail(email)!=null);
    }

}
