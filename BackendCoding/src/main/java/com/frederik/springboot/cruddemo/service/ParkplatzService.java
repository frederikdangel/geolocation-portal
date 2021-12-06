package com.frederik.springboot.cruddemo.service;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.entity.Parkplatz;

public interface ParkplatzService {

	public List<Parkplatz> findAll();
	
	public Parkplatz findById(int theId);
	
	public void save(Parkplatz theParkplatz);
	
	public void deleteById(int theId);
	
	public Adresse findParkplatzAdresse (int theId);

	public void addParkplatzAdresse(int parkplatzId, int adressId);
	
}
