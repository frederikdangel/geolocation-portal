package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Veranstalterverifizierung;

public interface VeranstalterverifizierungDAO {

    public List<Veranstalterverifizierung> findAll();

    // findet eine Veranstalterverifizierung nach ID
    public Veranstalterverifizierung findById(int theId);

    // speichert eine Veranstalterverifizierung
    public void save(Veranstalterverifizierung theVeranstalterverifizierung);

    // löscht eine Adresse
    public void deleteById(int theId);
}
