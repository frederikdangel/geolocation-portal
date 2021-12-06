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
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.cruddemo.service.VeranstalterService;

@RestController
@RequestMapping("/api")
public class VeranstalterRestController {

    private VeranstalterService VeranstalterService;

    @Autowired
    public VeranstalterRestController(VeranstalterService theVeranstalterService) {
	VeranstalterService = theVeranstalterService;
    }

    // expose "/Veranstalters" and return list of Veranstalters

    // gibt eine Liste aller vorhandener Veranstalter zurück
    @GetMapping("/Veranstalter")
    public List<Veranstalter> findAll() {
	return VeranstalterService.findAll();
    }

    // add mapping for GET /Veranstalters/{VeranstalterId}

    
    // gibt Veranstalter gemäß der ID aus der DB zurück
    // durch einen VeranstalterService Aufruf
    @GetMapping("/Veranstalter/{VeranstalterId}")
    public Veranstalter getVeranstalter(@PathVariable int VeranstalterId) {

	Veranstalter theVeranstalter = VeranstalterService.findById(VeranstalterId);

	if (theVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + VeranstalterId);
	}

	return theVeranstalter;
    }

    // add mapping for POST /Veranstalters - add new Veranstalter

    
    // im Requestbody mitgegebener theVeranstalter wird in der DB gespeichert
    // durch einen VeranstalterService Aufruf
    @PostMapping("/Veranstalter")
    public Veranstalter addVeranstalter(@RequestBody Veranstalter theVeranstalter) {

	// also just in case they pass an id in JSON ... set id to 0
	// this is to force a save of new item ... instead of update

	// theVeranstalter.setVeranstalterID(0);

	VeranstalterService.save(theVeranstalter);

	return theVeranstalter;
    }

    // add mapping for PUT /Veranstalters - update existing Veranstalter

    
    // im RequestBody mitgegebener theVerasntalter wird geupdated
    // durch einen VeranstalterService Aufruf
    @PutMapping("/Veranstalter")
    public Veranstalter updateVeranstalter(@RequestBody Veranstalter theVeranstalter) {
	Veranstalter tempVeranstalter = VeranstalterService.findById(theVeranstalter.getVeranstalterID());
	tempVeranstalter.merge(theVeranstalter);
	VeranstalterService.save(tempVeranstalter);

	return tempVeranstalter;
    }

    // add mapping for DELETE /Veranstalters/{VeranstalterId} - delete Veranstalter

    
    // Veranstalter wird mit zugehöriger Id gelöscht
    // durch einen VeranstalterService Aufruf
    @DeleteMapping("/Veranstalter/{VeranstalterId}")
    public String deleteVeranstalter(@PathVariable int VeranstalterId) {

	Veranstalter tempVeranstalter = VeranstalterService.findById(VeranstalterId);

	// throw exception if null

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + VeranstalterId);
	}

	VeranstalterService.deleteById(VeranstalterId);

	return "Deleted Veranstalter id - " + VeranstalterId;
    }

    // für einen Veranstalter wird eine Liste aler zugehöriger
    // User (als Veranstalter registriert)
    // zurückgegeben
    // durch einen VeranstalterService Aufruf
    @GetMapping("/Veranstalter/User/{VeranstalterId}")
    public List<User> getVeranstalterUsers(@PathVariable int VeranstalterId) {

	Veranstalter tempVeranstalter = VeranstalterService.findById(VeranstalterId);

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + VeranstalterId);
	} else {
	    List<User> users = VeranstalterService.findVeranstalterUser(VeranstalterId);

	    if (users == null) {
		throw new UserNotFoundException("Veranstalter  - " + VeranstalterId + "hat keine User");
	    }

	    return users;
	}
    }

    
    // vorhandene Veranstalter und User werden gemäß der in den
    // Pathvariablen mitgegebenen Ids verknüpft
    // durch einen VeranstalterService Aufruf
    @PostMapping("/Veranstalter/User/{veranstalterId}/{userId}")
    public List<User> addVeranstalterUser(@PathVariable int veranstalterId, @PathVariable int userId) {
	Veranstalter tempVeranstalter = VeranstalterService.findById(veranstalterId);

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + veranstalterId);
	} else {
	    VeranstalterService.addVeranstalterUser(veranstalterId, userId);
	}
	return VeranstalterService.findVeranstalterUser(veranstalterId);
    }

    // vorhandener Veranstalter wird mit vorhandener Veranstaltung verknüpft
    // gemäß mitgegebener Ids
    // durch einen VeranstalterService Aufruf
    @PostMapping("/Veranstalter/Veranstaltung/{veranstalterId}/{veranstaltungId}")
    public List<Veranstaltung> addVeranstalterVeranstaltungById(@PathVariable int veranstalterId,
	    @PathVariable int veranstaltungId) {
	Veranstalter tempVeranstalter = VeranstalterService.findById(veranstalterId);

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + veranstalterId);
	} else {
	    VeranstalterService.addVeranstalterVeranstaltung(veranstalterId, veranstaltungId);
	}
	return VeranstalterService.findVeranstalterVeranstaltung(veranstalterId);
    }

    // neue theVerasntaltung wird in der DB gespeichert und
    // mit vorhandenem Veranstalter verknüpft
    // durch einen VeranstalterService Aufruf
    @PostMapping("/Veranstalter/Veranstaltung/{veranstalterId}")
    public List<Veranstaltung> addVeranstalterVeranstaltung(@PathVariable int veranstalterId,
	    @RequestBody Veranstaltung theVeranstaltung) {
	Veranstalter tempVeranstalter = VeranstalterService.findById(veranstalterId);

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + veranstalterId);
	} else {
	    VeranstalterService.addVeranstalterVeranstaltung(veranstalterId, theVeranstaltung);
	}
	return VeranstalterService.findVeranstalterVeranstaltung(veranstalterId);
    }

    // für den Veranstalter mit der mitgegebenen Id
    // werden alle verknüpften Veranstaltungen
    // als Liste zurückgegeben
    // durch einen VeranstalterService Aufruf
    @GetMapping("/Veranstalter/Veranstaltung/{veranstalterId}")
    public List<Veranstaltung> findVeranstalterVeranstaltungen(int veranstalterId) {
	Veranstalter tempVeranstalter = VeranstalterService.findById(veranstalterId);

	if (tempVeranstalter == null) {
	    throw new VeranstalterNotFoundException("Veranstalter id not found - " + veranstalterId);
	} else {
	    return VeranstalterService.findVeranstalterVeranstaltung(veranstalterId);
	}
    }
}
