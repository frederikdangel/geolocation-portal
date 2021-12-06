package com.frederik.springboot.cruddemo.service;

import java.util.List;

import com.frederik.springboot.cruddemo.entity.Adresse;

public interface AdresseService {

    public List<Adresse> findAll();

    public Adresse findById(Integer theId);

    public void save(Adresse theAdresse);

    public void update(Adresse thAdresse);

    public void deleteById(Integer theId);

}
