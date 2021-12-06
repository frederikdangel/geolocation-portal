package com.frederik.springboot.cruddemo.rest;

import java.sql.Timestamp;
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
import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstalter;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.cruddemo.service.VeranstaltungService;
import com.frederik.springboot.repository.DEPRECATED.VeranstaltungRepository;

@RestController
@RequestMapping("/api")
public class VeranstaltungRestController {

    private VeranstaltungService VeranstaltungService;

    @Autowired
    public VeranstaltungRestController(VeranstaltungService theVeranstaltungService) {
	VeranstaltungService = theVeranstaltungService;
    }

    
    // gibt eine Liste aller vorhandener Veranstaltungen zurück
    // expose "/Veranstaltungs" and return list of Veranstaltungs
    @GetMapping("/Veranstaltung")
    public List<Veranstaltung> findAll() {
	return VeranstaltungService.findAll();
    }

    // add mapping for GET /Veranstaltungs/{VeranstaltungId}

    // gibt die zur mitgegebenen Id gehörende Veranstaltung zurück
    @GetMapping("/Veranstaltung/{VeranstaltungId}")
    public Veranstaltung getVeranstaltung(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	}

	return theVeranstaltung;
    }

    // add mapping for POST /Veranstaltungs - add new Veranstaltung

    // speichert die neue im Requestbody mitgegebene
    // theVeranstaltung in der DB 
    // durch einen VeranstaltungService Aufruf
    // 
    @PostMapping("/Veranstaltung")
    public Veranstaltung addVeranstaltung(@RequestBody Veranstaltung theVeranstaltung) {

	// also just in case they pass an id in JSON ... set id to 0
	// this is to force a save of new item ... instead of update

	// theVeranstaltung.setVeranstaltungsID(0);

	VeranstaltungService.save(theVeranstaltung);

	return theVeranstaltung;
    }

    // add mapping for PUT /Veranstaltungs - update existing Veranstaltung

    // updated die im Requestbody mitgegebene theVeranstaltung
    // durch einen VeranstaltungService Aufruf
    @PutMapping("/Veranstaltung")
    public Veranstaltung updateVeranstaltung(@RequestBody Veranstaltung theVeranstaltung) {

	Veranstaltung tempVeranstaltung = VeranstaltungService.findById(theVeranstaltung.getVeranstaltungsID());
	tempVeranstaltung.merge(theVeranstaltung);
	VeranstaltungService.save(tempVeranstaltung);

	return tempVeranstaltung;
    }

    // add mapping for DELETE /Veranstaltungs/{VeranstaltungId} - delete
    // Veranstaltung

    // löscht die zur VeranstaltungId gehörende in der 
    // Pathvariablen mitgegebenen Veranstaltung
    // aus der DB
    // durch einen VeranstaltungService Aufruf
    @DeleteMapping("/Veranstaltung/{VeranstaltungId}")
    public String deleteVeranstaltung(@PathVariable int VeranstaltungId) {

	Veranstaltung tempVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	// throw exception if null

	if (tempVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	}

	VeranstaltungService.deleteById(VeranstaltungId);

	return "Deleted Veranstaltung id - " + VeranstaltungId;
    }

    // gibt den Veranstalter einer Veranstaltung mit der mitgegebenen
    // Id zurück durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/Veranstalter/{VeranstaltungId}")
    public Veranstalter getVeranstaltungsVeranstalter(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    Veranstalter theVeranstalter = VeranstaltungService.findVeranstalter(VeranstaltungId);

	    if (theVeranstalter == null) {
		throw new VeranstaltungNotFoundException(
			"Veranstaltung id  - " + VeranstaltungId + "hat keinen Veranstalter");
	    } else
		return theVeranstalter;
	}

    }

    // gibt die Oberkategorie der Veranstaltung mit
    // der mitgegebenen Id zurück
    // durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/Oberkategorie/{VeranstaltungId}")
    public Oberkategorie getVeranstaltungOberkategorie(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    Oberkategorie theOberkategorie = VeranstaltungService.findVeranstaltungsOberkategorie(VeranstaltungId);

	    if (theOberkategorie == null) {
		throw new VeranstaltungNotFoundException(
			"Veranstaltung id  - " + VeranstaltungId + "hat keine Oberkategorie");
	    } else
		return theOberkategorie;
	}
    }

    // gibt die Unterkategorie der Veranstaltung zur mitgegebenen
    // Id zurück
    // durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/Unterkategorie/{VeranstaltungId}")
    public Unterkategorie getVeranstaltungUnterkategorie(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    Unterkategorie theUnterkategorie = VeranstaltungService.findVeranstaltungsUnterkategorie(VeranstaltungId);

	    if (theUnterkategorie == null) {
		throw new VeranstaltungNotFoundException(
			"Veranstaltung id  - " + VeranstaltungId + "hat keine Unterkategorie");
	    } else
		return theUnterkategorie;
	}
    }

    // gibt die Adresse der Veranstaltung der
    // mtigegebenen VeranstaltungId zurück
    // durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/Adresse/{VeranstaltungId}")
    public Adresse getVeranstaltungsAdresse(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    Adresse theAdresse = VeranstaltungService.findVeranstaltungsAdresse(VeranstaltungId);

	    if (theAdresse == null) {
		throw new VeranstaltungNotFoundException(
			"Veranstaltung id  - " + VeranstaltungId + "hat keine Adresse");
	    } else
		return theAdresse;
	}
    }

    // gibt alle User die mit der Veranstaltung  der mitgegebenen
    // Id verknüpft sind als Liste zurück
    // durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/User/{VeranstaltungId}")
    public List<User> getVeranstaltungsBesucher(@PathVariable int VeranstaltungId) {

	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    List<User> users = VeranstaltungService.findVeranstaltungsBesucher(VeranstaltungId);

	    if (users == null) {
		throw new VeranstaltungNotFoundException(
			"Veranstaltung id  - " + VeranstaltungId + "hat keine Besucher");
	    } else
		return users;
	}
    }

    
    // verknüpft vorhandene Veranstaltung mit vorhandenem Veranstalter
    // gemäß der in den Pathvariablen mitgegebenen IDs
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Veranstalter/{VeranstaltungId}/{veranstalterId}")
    public Veranstalter addVeranstaltungsVeranstalterById(@PathVariable int VeranstaltungId,
	    @PathVariable int veranstalterId) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsVeranstalterById(VeranstaltungId, veranstalterId);
	}
	return VeranstaltungService.findVeranstalter(VeranstaltungId);
    }

    
    // speichert den im Requestbody mtigegebenen neuen
    // theVeranstalter in der DB und verknüpft ihn
    // mit vorhandener Veranstaltung
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Veranstalter/{VeranstaltungId}")
    public Veranstalter addVeranstaltungsVeranstalter(@PathVariable int VeranstaltungId,
	    @RequestBody Veranstalter theVeranstalter) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(VeranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + VeranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsVeranstalter(VeranstaltungId, theVeranstalter);
	}
	return VeranstaltungService.findVeranstalter(VeranstaltungId);
    }

    // verknüpft vorhandenen Veranstaltung mit vorhandenem User
    // der in den Pathvariablen mitegebenen Ids
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/User/{veranstaltungId}/{userId}")
    public List<User> addVeranstaltungsBesucherById(@PathVariable int veranstaltungId, @PathVariable int userId) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsBesucherById(veranstaltungId, userId);
	}
	return VeranstaltungService.findVeranstaltungsBesucher(veranstaltungId);

    }

    // legt neuen im Requestbody mitgegebenen
    // theUser ind er DB an und verknüpft ihn 
    // mit vorhandener Veranstaltung
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/User/{veranstaltungId}")
    public List<User> addVeranstaltungsBesucher(@PathVariable int veranstaltungId, @RequestBody User theUser) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsBesucher(veranstaltungId, theUser);
	}
	return VeranstaltungService.findVeranstaltungsBesucher(veranstaltungId);
    }

    
    // verknüpft vorhandene Veranstaltung mit
    // vorhandeneer Oberkategorie
    // durhc in den Pathvariablen mitgegebenen Ids
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Oberkategorie/{veranstaltungId}/{oberkategorieId}")
    public Oberkategorie addVeranstaltungsOberkategorieById(@PathVariable int veranstaltungId,
	    @PathVariable int oberkategorieId) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsOberkategorieById(veranstaltungId, oberkategorieId);
	}
	return VeranstaltungService.findVeranstaltungsOberkategorie(veranstaltungId);
    }

    
    // speichert im Requestbody mitgegebenen
    // theOberkategorie in der DB und verknüpft
    // mit vorhandener Veranstaltung
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Oberkategorie/{veranstaltungId}")
    public Oberkategorie addVeranstaltungsOberkategorie(@PathVariable int veranstaltungId,
	    @RequestBody Oberkategorie theOberkategorie) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsOberkategorie(veranstaltungId, theOberkategorie);
	}
	return VeranstaltungService.findVeranstaltungsOberkategorie(veranstaltungId);
    }

    
    // verknüpft vorhandene Veranstaltung mit
    // vorhandener Unterkategorie
    // gemäß den ind en Pathvatiablen mitgegebenen Ids
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Unterkategorie/{veranstaltungId}/{unterkategorieId}")
    public Unterkategorie addVeranstaltungsUnterkategorieById(@PathVariable int veranstaltungId,
	    @PathVariable int unterkategorieId) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsUnterkategorieById(veranstaltungId, unterkategorieId);
	}
	return VeranstaltungService.findVeranstaltungsUnterkategorie(veranstaltungId);
    }

    
    // speichert die im Requestbody mitgegebene
    // neue theUnterkategorie in der DB und 
    // verknüpft mit vorhandener Veranstaltung
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Unterkategorie/{veranstaltungId}")
    public Unterkategorie addVeranstaltungsUnterkategorie(@PathVariable int veranstaltungId,
	    @RequestBody Unterkategorie theUnterkategorie) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsUnterkategorie(veranstaltungId, theUnterkategorie);
	}
	return VeranstaltungService.findVeranstaltungsUnterkategorie(veranstaltungId);
    }

    // verknüpft vorhandene Veranstaltung mit
    // vorhandener Adresse gemäß
    // in Pathvariblen mitgegebenen IDs
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Adresse/{veranstaltungId}/{adressId}")
    public Adresse addVeranstaltungsAdresseById(@PathVariable int veranstaltungId, @PathVariable int adressId) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsAdresseById(veranstaltungId, adressId);
	}
	return VeranstaltungService.findVeranstaltungsAdresse(veranstaltungId);
    }

    
    // speichert neue im requestbody mitgegebenee
    // theAdresse in der DB und verknüpft
    // mit vorhandener Veranstaltung
    // durch einen VeranstaltungService Aufruf
    @PostMapping("/Veranstaltung/Adresse/{veranstaltungId}")
    public Adresse addVeranstaltungsAdresse(@PathVariable int veranstaltungId, @RequestBody Adresse theAdresse) {
	Veranstaltung theVeranstaltung = VeranstaltungService.findById(veranstaltungId);

	if (theVeranstaltung == null) {
	    throw new VeranstaltungNotFoundException("Veranstaltung id not found - " + veranstaltungId);
	} else {
	    VeranstaltungService.addVeranstaltungsAdresse(veranstaltungId, theAdresse);
	}
	return VeranstaltungService.findVeranstaltungsAdresse(veranstaltungId);
    }

    
    // gibt Liste als Highlight markierter Veranstaltungen zurück
    @GetMapping("/Veranstaltung/Highlights")
    public List<Veranstaltung> findHighlights() {
	return VeranstaltungService.findHighlights();
    }

    //@PostMapping("/Veranstaltung/Request")
    public List<Veranstaltung> findFilterVeranstaltung(@RequestBody Request request) {
	
	if ((request.getDatefrom() == null) && (request.getDateto() == null) && (request.getOberkategorien() == null)
		&& (request.getUnterkategorien() == null) && request.getSuche() == null)
	    return VeranstaltungService.findAll();
	
	
	return VeranstaltungService.findFilterVeranstaltung(request);
    }
    
    // gibt request zurück
    // zum testen
    @PostMapping("/Request")
    public Request findRequest(@RequestBody Request request) {
	
	
	
	return request;
    }
    
    
    // gibt Liste aller freizugebenderVeranstaltungen zurück
    //durch einen VeranstaltungService Aufruf
    @GetMapping("/Veranstaltung/Freigeben")
    public List<Veranstaltung> findFreizugebendeVeranstaltungen(){
	return VeranstaltungService.findFreizugebendeVeranstaltungen();
    }

}
