package com.frederik.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.cruddemo.service.OberkategorieService;
import com.frederik.springboot.cruddemo.service.UnterkategorieService;
import com.frederik.springboot.cruddemo.service.VeranstaltungService;

@RestController
@RequestMapping("/api")
public class RequestRestController {

    private VeranstaltungService theVeranstaltungService;
    private OberkategorieService theOberkategorieService;

    private UnterkategorieService theUnterkategorieService;

    @Autowired
    public RequestRestController(VeranstaltungService theVeranstaltungService,
	    OberkategorieService theOberkategorieService, UnterkategorieService theUnterkategorieService) {
	this.theOberkategorieService = theOberkategorieService;
	this.theUnterkategorieService = theUnterkategorieService;
	this.theVeranstaltungService = theVeranstaltungService;
    }

    // gibt für die Veranstaltung mit dem Request alle zugehörigen Veranstaltungen
    // als Liste zurück durch Aufrufe Von VeranstaltungSreivce-, OberkategorieService- &
    // UnterkategorieService Methoden
    @PostMapping("/Veranstaltung/Request")
    public List<Veranstaltung> findFilterVeranstaltung(@RequestBody Request request) {

	if ((request.getOberkategorien()) == null || request.getOberkategorien().size() == 0) {
	    request.setOberkategorien(null);
	}

	if ((request.getUnterkategorien()) == null || request.getUnterkategorien().size() == 0) {
	    request.setUnterkategorien(null);
	}

	
	if ((request.getDatefrom() == null) && (request.getDateto() == null) && (request.getSuche() == null)
		&& ((request.getOberkategorien()) == null) && ((request.getUnterkategorien()) == null))
	    return theVeranstaltungService.findFreigegebeneVeranstaltungen();

	
	List<Unterkategorie> tempUnterkategorien = request.getUnterkategorien();

	if (tempUnterkategorien != null) {

	    for (Unterkategorie unter : tempUnterkategorien) {
		unter.setOberkategorie(
			theUnterkategorieService.findUnterkategoriesOberkategorie(unter.getUnterkategorieID()));
	    }

	    request.setUnterkategorien(tempUnterkategorien);
	}

	return theVeranstaltungService.findFilterVeranstaltung(request);
    }

}
