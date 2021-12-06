package com.frederik.springboot.cruddemo.dao;

import java.sql.Timestamp;
import java.util.List;

import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;;

public interface VeranstaltungDAO {

    public List<Veranstaltung> findAll();

    public Veranstaltung findById(int theId);

    public void save(Veranstaltung theVeranstaltung);

    public void deleteById(int theId);

    public Veranstalter findVeranstalter(int theId);

    public Oberkategorie findVeranstaltungsOberkategorie(int theId);

    public Unterkategorie findVeranstaltungsUnterkategorie(int theId);

    public Adresse findVeranstaltungsAdresse(int theId);

    public List<User> findVeranstaltungsBesucher(int theId);

    public void addVeranstaltungsBesucherById(int veranstaltungId, int userId);
    
    public void addVeranstaltungsBesucher(int veranstaltungId, User theUser);

    

    public void addVeranstaltungsOberkategorieById(int veranstaltungId, int oberkategorieId);

    public void addVeranstaltungsUnterkategorieById(int veranstaltungId, int unterkategorieId);

    public void addVeranstaltungsAdresseById(int veranstaltungId, int adressId);
    
    public void addVeranstaltungsOberkategorie(int veranstaltungId, Oberkategorie theOberkategorie);

    public void addVeranstaltungsUnterkategorie(int veranstaltungId, Unterkategorie theUnterkategorie);

    public void addVeranstaltungsAdresse(int veranstaltungId, Adresse theAdresse);

    public void addVeranstaltungsVeranstalterById(int veranstaltungId, int veranstalterId);

    public void addVeranstaltungsVeranstalter(int veranstaltungId, Veranstalter theVeranstalter);

    public List<Veranstaltung> findHighlights();

    public List<Veranstaltung> findFilterVeranstaltung(Request request);

    public List<Veranstaltung> findFreigegebeneVeranstaltungen();

    public List<Veranstaltung> findFreizugebendeVeranstaltungen();


   

    
}
