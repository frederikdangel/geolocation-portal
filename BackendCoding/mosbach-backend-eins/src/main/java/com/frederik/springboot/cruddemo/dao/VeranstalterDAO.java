package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;;

public interface VeranstalterDAO {

	public List<Veranstalter> findAll();
	
	public Veranstalter findById(int theId);
	
	public void save(Veranstalter theVeranstalter);
	
	public void deleteById(int theId);
	
	public List<User> findVeranstalterUser (int theId);
	
	public void addVeranstaltendeUser (int veranstalterId, int userId);
	
	public Veranstaltung addVeranstaltung (int veranstalterId, Veranstaltung theVeranstaltung);
	
	public Veranstaltung addVeranstaltung (int veranstalterId, int veranstaltungId);
	
	public List<Veranstaltung> findVeranstalterVeranstaltung (int veranstalterId);
	
	
}
