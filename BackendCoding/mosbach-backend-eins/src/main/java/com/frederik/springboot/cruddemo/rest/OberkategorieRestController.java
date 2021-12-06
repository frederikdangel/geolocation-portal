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

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.cruddemo.service.OberkategorieService;

@RestController
@RequestMapping("/api")
public class OberkategorieRestController {

    private OberkategorieService OberkategorieService;

    @Autowired
    public OberkategorieRestController(OberkategorieService theOberkategorieService) {
	OberkategorieService = theOberkategorieService;
    }

    // gibt alle Oberkategorien aus der DB als Liste aus durch einen OberkategorieService Aufruf
    // expose "/Oberkategories" and return list of Oberkategories
    @GetMapping("/Oberkategorie")
    public List<Oberkategorie> findAll() {
	return OberkategorieService.findAll();
    }

    // add mapping for GET /Oberkategories/{OberkategorieId}

    // gibt die zur Id gehörige Oberkategorie aus der DB
    // zurück durch einen OberkategorieService Aufruf
    @GetMapping("/Oberkategorie/{OberkategorieId}")
    public Oberkategorie getOberkategorie(@PathVariable int OberkategorieId) {

	Oberkategorie theOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (theOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	}

	return theOberkategorie;
    }

    // add mapping for POST /Oberkategories - add new Oberkategorie

    // legt die im RequestBody mitgegebene Oberkategorie in die DB ab
    // durch einen OberkategorieService Aufruf
    @PostMapping("/Oberkategorie")
    public Oberkategorie addOberkategorie(@RequestBody Oberkategorie theOberkategorie) {

	// also just in case they pass an id in JSON ... set id to 0
	// this is to force a save of new item ... instead of update

	//theOberkategorie.setOberkategorieID(0);

	OberkategorieService.save(theOberkategorie);

	return theOberkategorie;
    }

    // add mapping for PUT /Oberkategories - update existing Oberkategorie

    // updated die Oberkategorie theOberkategorie mit der im RequestBody mitgegebenen Id
    // durch einen OberkategorieService Aufruf
    @PutMapping("/Oberkategorie")
    public Oberkategorie updateOberkategorie(@RequestBody Oberkategorie theOberkategorie) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(theOberkategorie.getoberkategorieid());
	tempOberkategorie.merge(theOberkategorie);
	OberkategorieService.save(tempOberkategorie);
	return tempOberkategorie;
    }

    // add mapping for DELETE /Oberkategories/{OberkategorieId} - delete
    // Oberkategorie

    // löscht die zur Id gehörige Oberkategorie aus der DB durch einen OberkategorieService Aufruf
    // und gibt einen String über Erfolg zurück
    
    @DeleteMapping("/Oberkategorie/{OberkategorieId}")
    public String deleteOberkategorie(@PathVariable int OberkategorieId) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	// throw exception if null

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	}

	OberkategorieService.deleteById(OberkategorieId);

	return "Deleted Oberkategorie id - " + OberkategorieId;
    }

    // gibt alle zur Oberkategorie mit der Id zugehörigen User 
    // als Liste zurück durch einen OberkategorieService Aufruf
    
    @GetMapping("/Oberkategorie/User/{OberkategorieId}")
    public List<User> getOberkategorieUsers(@PathVariable int OberkategorieId) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    List<User> users = OberkategorieService.findOberkategorieUser(OberkategorieId);

	    if (users == null) {
		throw new RuntimeException("Oberkategorie  - " + OberkategorieId + "hat keine User");
	    }

	    return users;
	}
    }

    // gibt die Liste zugehöriger Unterkategorien zur Oberkategorie mit der Id 
    // zurück durch einen OberkategorieService Aufruf
    
    @GetMapping("/Oberkategorie/Unterkategorie/{OberkategorieId}")
    public List<Unterkategorie> getOberkategorieUnterkategorien(@PathVariable int OberkategorieId) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    List<Unterkategorie> unterkategorien = OberkategorieService
		    .findOberkategorieUnterkategorien(OberkategorieId);

	    if (unterkategorien == null) {
		throw new RuntimeException("Oberkategorie  - " + OberkategorieId + "hat keine Unterkategorien");
	    }

	    return unterkategorien;
	}
    }

    // gibt alle zur Oberkategorie mit der Id zugehörigen
    // Veranstaltungen als Liste zurück
    // durch einen OberkategorieService Aufruf
    @GetMapping("/Oberkategorie/Veranstaltung/{OberkategorieId}")
    public List<Veranstaltung> getOberkategorieVeranstaltungen(@PathVariable int OberkategorieId) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    List<Veranstaltung> veranstaltungen = OberkategorieService
		    .findOberkategorieVeranstaltungen(OberkategorieId);

	    if (veranstaltungen == null) {
		throw new RuntimeException("Oberkategorie  - " + OberkategorieId + "hat keine Veranstaltungen");
	    }

	    return veranstaltungen;
	}
    }

    // legt den neuen theUser in der DB an und verknüpft ihn mit der vorhandenen
    // Oberkategorie durch einen OberkategorieService Aufruf
    
    @PostMapping("/Oberkategorie/User/{OberkategorieId}")
    public List<User> addOberkategorieUser(@PathVariable int OberkategorieId, @RequestBody User theUser) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    tempOberkategorie.addUser(theUser);
	    return tempOberkategorie.getUsers();
	}
    }

    
    // legt die neue theUnterkategorie in der DB an und verknüpft sie mit
    // der vorhandennen Oberkategorie 
    // durch einen OberkategorieService Aufruf
    
    @PostMapping("/Oberkategorie/Unterkategorie/{OberkategorieId}")
    public List<Unterkategorie> addOberkategorieUnterkategorie(@PathVariable int OberkategorieId,
	    @RequestBody Unterkategorie theUnterkategorie) {

	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    tempOberkategorie.addUnterkategorie(theUnterkategorie);

	    return tempOberkategorie.getUnterkategorien();
	}
    }

    // legt die im RequestBody mitgegebene theVeranstaltung in der DB ab 
    // und verknüpft sie mit der vorhandenen Oberkategorie
    // durch einen OberkategorieService Aufruf
    @PostMapping("/Oberkategorie/Veranstaltung/{OberkategorieId}")
    public List<Veranstaltung> addOberkategorieVeranstaltung(@PathVariable int OberkategorieId,
	    @RequestBody Veranstaltung theVeranstaltung) {
	Oberkategorie tempOberkategorie = OberkategorieService.findById(OberkategorieId);

	if (tempOberkategorie == null) {
	    throw new RuntimeException("Oberkategorie id not found - " + OberkategorieId);
	} else {
	    tempOberkategorie.addVeranstaltung(theVeranstaltung);

	    return tempOberkategorie.getVeranstaltungen();
	}
    }

    // verknüpft die in den PathVariablen mitgegebenen User und Oberkategorie
    @PostMapping("/Oberkategorie/User/{OberkategorieId}/{userId}")
    public List<User> addOberkategorieUserById(@PathVariable int OberkategorieId, @PathVariable int userId) {

	OberkategorieService.addOberkategorieUser(OberkategorieId, userId);
	return OberkategorieService.findOberkategorieUser(userId);
    }

    
    // verknüpft die in den Pathvariablen mitgegebenen Oberkategorie und Unterkategorie
    @PostMapping("/Oberkategorie/Unterkategorie/{oberkategorieId}/{unterkategorieId}")
    public List<Unterkategorie> addOberkategorieUnterkategorieById(@PathVariable int oberkategorieId,
	    @PathVariable int unterkategorieId) {

	OberkategorieService.addOberkategorieUnterkategorie(oberkategorieId, unterkategorieId);

	return OberkategorieService.findOberkategorieUnterkategorien(oberkategorieId);
    }

    
    // verknüpft die in den Pathvariablen mtigegebenen Oberkategorie und Veranstaltung
    @PostMapping("/Oberkategorie/Veranstaltung/{OberkategorieId}/{veranstaltungId}")
    public List<Veranstaltung> addOberkategorieVeranstaltungById(@PathVariable int OberkategorieId,
	   @PathVariable int veranstaltungId) {

	OberkategorieService.addOberkategorieVeranstaltung(OberkategorieId, veranstaltungId);
	return OberkategorieService.findOberkategorieVeranstaltungen(veranstaltungId);
    }
}