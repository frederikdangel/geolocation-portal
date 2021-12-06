package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

@Service
public interface VeranstalterService {

	public List<Veranstalter> findAll();
	
	public Veranstalter findById(int theId);
	
	public void save(Veranstalter theVeranstalter);
	
	public void deleteById(int theId);
	
	public List<User> findVeranstalterUser (int theId);
	
	public void addVeranstalterUser (int veranstalterId, int userId);
	
	public void addVeranstalterVeranstaltung (int veranstalterId, Veranstaltung theVeranstaltung);
	
	public void addVeranstalterVeranstaltung (int veranstalterId, int veranstaltungId);
	
	public List<Veranstaltung> findVeranstalterVeranstaltung (int veranstalterId);
	
}
