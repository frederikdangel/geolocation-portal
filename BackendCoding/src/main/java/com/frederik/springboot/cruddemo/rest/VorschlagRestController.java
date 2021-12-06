package com.frederik.springboot.cruddemo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;
import com.frederik.springboot.cruddemo.entity.Unterkategorie;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;
import com.frederik.springboot.cruddemo.service.OberkategorieService;
import com.frederik.springboot.cruddemo.service.UnterkategorieService;
import com.frederik.springboot.cruddemo.service.UserService;
import com.frederik.springboot.cruddemo.service.VeranstaltungService;

@RestController
@RequestMapping("/api")
public class VorschlagRestController {

    private VeranstaltungService theVeranstaltungService;
    private OberkategorieService theOberkategorieService;
    private UserService theUserService;
    private UnterkategorieService theUnterkategorieService;

    @Autowired
    public VorschlagRestController(VeranstaltungService theVeranstaltungService,
	    OberkategorieService theOberkategorieService, UserService theUserService,
	    UnterkategorieService theUnterkategorieService) {

	this.theOberkategorieService = theOberkategorieService;
	this.theUnterkategorieService = theUnterkategorieService;
	this.theUserService = theUserService;
	this.theVeranstaltungService = theVeranstaltungService;
    }

    @GetMapping("/User/Vorschlag/{userID}")
    public List<Veranstaltung> findUserVeranstaltungVorschlaege(@PathVariable int userID) {

	List<Veranstaltung> seineVeranstaltungen = theUserService.findUserVeranstaltungen(userID);
	List<Unterkategorie> seineUnterkategorien = theUserService.findUserUnterkategorien(userID);
	List<Oberkategorie> seineOberkategorien = theUserService.findUserOberkategorien(userID);

	List<Veranstaltung> vorschlag = new ArrayList<Veranstaltung>();

	vorschlag
		.add(seineVeranstaltungen.get(0).getVeranstaltungsBesucher().get(0).getVeranstaltungsBesucher().get(0));
	vorschlag.add(seineUnterkategorien.get(0).getVeranstaltungen().get(0));
	vorschlag.add(seineVeranstaltungen.get(0).getUnterkategorie().getVeranstaltungen().get(1));
	vorschlag.add(seineOberkategorien.get(0).getVeranstaltungen().get(0));

	return vorschlag;

    }
}
