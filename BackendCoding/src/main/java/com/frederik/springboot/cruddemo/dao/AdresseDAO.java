package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Adresse;


@Repository
public interface AdresseDAO {
    
 // findet alle Adresssen als Liste
 	public List<Adresse> findAll();
 	
 	// findet eine Adresse nach ID
 	public Adresse findById(int theId);
 	
 	// speichert eine Adresse
 	public void save(Adresse theAdresse);
 	
 	// löscht eine Adresse
 	public void deleteById(int theId);

    	
	
}
