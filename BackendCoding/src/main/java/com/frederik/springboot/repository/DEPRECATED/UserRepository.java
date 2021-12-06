package com.frederik.springboot.repository.DEPRECATED;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.User;
import com.frederik.springboot.cruddemo.entity.Veranstaltung;




public interface UserRepository extends CrudRepository<User, Integer>{


   
    
    @Query(value="Select * from veranstaltung where u.email = :mail")
    List<User> getUserByMail (@Param("mail") String mail);

   
   
}
