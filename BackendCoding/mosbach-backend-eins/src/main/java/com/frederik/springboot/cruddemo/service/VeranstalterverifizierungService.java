package com.frederik.springboot.cruddemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.frederik.springboot.cruddemo.entity.Veranstalterverifizierung;


public interface VeranstalterverifizierungService {

    public List<Veranstalterverifizierung> findAll();

    public Veranstalterverifizierung findById(int theId);

    public void save(Veranstalterverifizierung theVeranstalterverifizierung);

    public void deleteById(int theId);
}
