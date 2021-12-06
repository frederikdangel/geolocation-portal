package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

public interface UserDAO {

    public List<User> findAll();

    public User findById(int theId);

    public void save(User theUser);

    public void deleteById(int theId);

    public List<Veranstaltung> findUserVeranstaltungen(int userId);

    public List<Veranstalter> findUserAlsVeranstalter(int userId);

    public List<Oberkategorie> findUserOberkategorien(int userId);

    public List<Unterkategorie> findUserUnterkategorien(int userId);

    public void addUserVeranstaltung(int userId, Veranstaltung theVeranstaltung);

    public void addUserAlsVeranstalter(int userId, Veranstalter theVeranstalter);

    public void addUserOberkategorie(int userId, Oberkategorie theOberkategorie);

    public void addUserUnterkategorie(int userId, Unterkategorie theUnterkategorie);
    
    public void addUserVeranstaltungById(int userId, int veranstaltungId);

    public void addUserAlsVeranstalterById(int userId, int veranstalterId);

    public void addUserOberkategorieById(int userId, int oberkategorieId);

    public void addUserUnterkategorieById(int userId, int unterkategorieId);

    public User findUserByMailAndPW(String email, String pw);

    public User existiertMail(String email);
    
    

}
