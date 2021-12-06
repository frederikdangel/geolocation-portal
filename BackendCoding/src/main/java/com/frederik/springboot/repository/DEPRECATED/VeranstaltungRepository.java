package com.frederik.springboot.repository.DEPRECATED;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Request;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;

public interface VeranstaltungRepository extends JpaRepository<Veranstaltung, Integer> {

    @Query(value = "select v from veranstaltung v, oberkategorie o, unterkategorie u "
    	+ "where v.datefrom =: datefrom"
    	+ " or v.dateto =: dateto"
    	+ " or v.titel =: suche"
    	+ " or v.oberkategorieid = ( select oberkategorieid from oberkategorie where oberkategoriename =: oberkategorie) "
    	+ " or v.unterkategorieid = ( select unterkategorieid from unterkategorie where unterkategoriename =: unterkategorie)"
    	+ "", nativeQuery = true)
    List<Veranstaltung> findFilterVeranstaltung(@Param("oberkategorie") String oberkategorie,
	    @Param("unterkategorie") String unterkategorie, @Param("datefrom") Timestamp datefrom,
	    @Param("dateto") Timestamp dateto, @Param("suche") String suche);
}
