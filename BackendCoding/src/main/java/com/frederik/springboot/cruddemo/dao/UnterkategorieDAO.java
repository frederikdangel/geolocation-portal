package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;;

public interface UnterkategorieDAO {

	public List<Unterkategorie> findAll();
	
	public Unterkategorie findById(int theId);
	
	public void save(Unterkategorie theUnterkategorie);
	
	public void deleteById(int theId);
	
	public List<User> findUnterkategorieUser(int theId);
	
	public Oberkategorie findUnterkategoriesOberkategorie(int theId);
	
	public void addUnterkategorieUser (int unterkategorieId, User theUser);
	
	public void addUnterkategorieUser (int unterkategorieId, int userId);
	
	public List<Veranstaltung> findUnterkategorieVeranstaltungen (int unterkategorieId);
	
	public void addUnterkategorieVeranstaltung(int unterkategorieId, int veranstaltungId);
	
	public void addUnterkategorieVeranstaltung(int unterkategorieId, Veranstaltung theVeranstaltung);
}
