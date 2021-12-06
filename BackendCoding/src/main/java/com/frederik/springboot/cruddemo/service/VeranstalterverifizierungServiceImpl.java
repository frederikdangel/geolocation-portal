package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frederik.springboot.cruddemo.dao.VeranstalterverifizierungDAO;
import com.frederik.springboot.cruddemo.entity.Veranstalterverifizierung;

@Service
public class VeranstalterverifizierungServiceImpl implements VeranstalterverifizierungService {

	// @Autowired = Verweise werden automatisch injiziert 
    @Autowired
    private VeranstalterverifizierungDAO veranstalterverifizierungDAO;

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public List<Veranstalterverifizierung> findAll() {
	return veranstalterverifizierungDAO.findAll();
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public Veranstalterverifizierung findById(int theId) {
	return veranstalterverifizierungDAO.findById(theId);
    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void save(Veranstalterverifizierung theVeranstalterverifizierung) {
	veranstalterverifizierungDAO.save(theVeranstalterverifizierung);

    }

    /*
     * @Override = Überschreibt die Methode aus der Oberklasse
     * @Transactional = Annotiert Methoden, die Geschäftslogik umsetzen
     */
    @Override
    @Transactional
    public void deleteById(int theId) {
	veranstalterverifizierungDAO.deleteById(theId);

    }

}
