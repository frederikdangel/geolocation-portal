package com.frederik.springboot.repository.DEPRECATED;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Oberkategorie;



public interface OberkategorieRepository extends CrudRepository<Oberkategorie, Integer>{

}
