package com.frederik.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frederik.springboot.cruddemo.entity.Veranstalterverifizierung;
import com.frederik.springboot.cruddemo.service.VeranstalterverifizierungService;

@RestController
@RequestMapping("/api")
public class VeranstalterverifizierungRestController {

    private VeranstalterverifizierungService veranstalterverifizierungService;

    @Autowired
    public VeranstalterverifizierungRestController(
	    VeranstalterverifizierungService theVeranstalterverifizierungService) {
	this.veranstalterverifizierungService = theVeranstalterverifizierungService;
    }

    // expose "/Veranstalterverifizierungs" and return list of
    // Veranstalterverifizierung

    // gibt alle vorhandenen Veranstalterverifizierungen als Liste zurück
    @GetMapping("/Veranstalterverifizierung")
    public List<Veranstalterverifizierung> findAll() {
	return veranstalterverifizierungService.findAll();
    }

    // add mapping for GET /Veranstalterverifizierungs/{VeranstalterverifizierungId}

    // gibt gemäß der Id vorhandene Veranstlaterverifizierung zurück
    // ID entspricht zugehöriger UserID
    @GetMapping("/Veranstalterverifizierung/{VeranstalterverifizierungId}")
    public Veranstalterverifizierung getVeranstalterverifizierung(@PathVariable int VeranstalterverifizierungId) {

	Veranstalterverifizierung theVeranstalterverifizierung = veranstalterverifizierungService
		.findById(VeranstalterverifizierungId);

	if (theVeranstalterverifizierung == null) {
	    throw new RuntimeException("Veranstalterverifizierung id not found - " + VeranstalterverifizierungId);
	}

	return theVeranstalterverifizierung;
    }

    // add mapping for POST /Veranstalterverifizierungs - add new
    // Veranstalterverifizierung

    // im Requestbody mitgegebene Veranstalterverifizierung wird in der DB gespeichert
    @PostMapping("/Veranstalterverifizierung")
    public Veranstalterverifizierung addVeranstalterverifizierung(
	    @RequestBody Veranstalterverifizierung theVeranstalterverifizierung) {

	veranstalterverifizierungService.save(theVeranstalterverifizierung);

	return theVeranstalterverifizierung;
    }

    // add mapping for DELETE
    // /Veranstalterverifizierungs/{VeranstalterverifizierungId} - delete
    // Veranstalterverifizierung

    
    // löscht zur ID zugehörige Veranstalterverifizierung
    @DeleteMapping("/Veranstalterverifizierung/{VeranstalterverifizierungId}")
    public String deleteVeranstalterverifizierung(@PathVariable int VeranstalterverifizierungId) {

	Veranstalterverifizierung tempVeranstalterverifizierung = veranstalterverifizierungService
		.findById(VeranstalterverifizierungId);

	// throw exception if null

	if (tempVeranstalterverifizierung == null) {
	    throw new RuntimeException("Veranstalterverifizierung id not found - " + VeranstalterverifizierungId);
	}

	veranstalterverifizierungService.deleteById(VeranstalterverifizierungId);

	return "Deleted Veranstalterverifizierung id - " + VeranstalterverifizierungId;
    }
}
