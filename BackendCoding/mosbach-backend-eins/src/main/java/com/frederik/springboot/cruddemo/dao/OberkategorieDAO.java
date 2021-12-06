package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;;

public interface OberkategorieDAO {

    // gibt eine Liste aller Oberkategorien zurück
    public List<Oberkategorie> findAll();

    // gibt eine Oberkategorie entsprechend der ID zurück
    public Oberkategorie findById(int theId);

    // speichert eine Oberkategorie
    public void save(Oberkategorie theOberkategorie);

    // löscht eine Oberkategorie gemäß der ID
    public void deleteById(int theId);

    public List<User> findOberkategorieUser(int theId);

    public List<Unterkategorie> findOberkategorieUnterkategorien(Integer theId);

    public List<Veranstaltung> findOberkategorieVeranstaltungen(Integer theId);

    public void addOberkategorieVeranstaltung(int oberkategorieId, int veranstaltungId);
    
    public void addOberkategorieUnterkategorie(int oberkategorieId, int unterkategorieId);
    
    public void addOberkategorieUser(int oberkategorieId, int userId);
    

    public void addOberkategorieUnterkategorie(int oberkategorieId, Unterkategorie theUnterkategorie);

    public void addOberkategorieVeranstaltung(int oberkategorieId, Veranstaltung theVeranstaltung);
       
    public void addOberkategorieUser(int oberkategorieId, User theUser);

}
