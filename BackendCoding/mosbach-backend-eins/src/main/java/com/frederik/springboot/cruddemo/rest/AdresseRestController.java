package com.frederik.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frederik.springboot.cruddemo.entity.Adresse;
import com.frederik.springboot.cruddemo.service.AdresseService;
import com.frederik.springboot.cruddemo.service.FindNull;

@RestController
@RequestMapping("/api")
public class AdresseRestController {

    @Autowired
    private AdresseService AdresseService;

    @Autowired
    public AdresseRestController(AdresseService theAdresseService) {
	AdresseService = theAdresseService;
    }

    // expose "/Adresses" and return list of Adresses
    @GetMapping("/Adresse")
    public List<Adresse> findAll() {
	return AdresseService.findAll();
    }

    // add mapping for GET /Adresses/{AdresseId}

    // findet die zur AdresseId zugehörige Adresse durch einen AdresseService Aufruf
    @GetMapping("/Adresse/{AdresseId}")
    public Adresse getAdresse(@PathVariable int AdresseId) {

	Adresse theAdresse = AdresseService.findById(AdresseId);

	if (theAdresse == null) {
	    throw new AdresseNotFoundException("Adresse id not found - " + AdresseId);
	}

	return theAdresse;
    }

    // add mapping for POST /Adresses - add new Adresse

    // speichert die neue theAdresse aus dem Request Body in der DB durch einen AdresseService Aufruf
    @PostMapping("/Adresse")
    public Adresse addAdresse(@RequestBody Adresse theAdresse) {

	// also just in case they pass an id in JSON ... set id to 0
	// this is to force a save of new item ... instead of update

	//theAdresse.setAdressID(0);


	AdresseService.save(theAdresse);

	return theAdresse;
    }

    // add mapping for PUT /Adresses - update existing Adresse

    // updated die theAdresse durch mitgegebener Id im Request Body durch einen AdresseService Aufruf
    @PutMapping("/Adresse")
    public Adresse updateAdresse(@RequestBody Adresse theAdresse) {

	Adresse tempAdresse = AdresseService.findById(theAdresse.getAdressID());
	
	tempAdresse.merge(theAdresse);
	
	AdresseService.save(tempAdresse);
	return tempAdresse;
    }

    // add mapping for DELETE /Adresses/{AdresseId} - delete Adresse

    // löscht die zugehörige Adresse aus der DB durch einen AdresseService Aufruf
    @DeleteMapping("/Adresse/{AdresseId}")
    public String deleteAdresse(@PathVariable int AdresseId) {

	Adresse tempAdresse = AdresseService.findById(AdresseId);

	// throw exception if null

	if (tempAdresse == null) {
	    throw new RuntimeException("Adresse id not found - " + AdresseId);
	}

	AdresseService.deleteById(AdresseId);

	return "Deleted Adresse id - " + AdresseId;
    }

}
