package com.frederik.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.frederik.springboot.cruddemo.entity.Veranstalterverifizierung;
@Repository
public class VeranstalterverifizierungDAOHibernateImpl implements VeranstalterverifizierungDAO{

 // define field for entitymanager	
 	private EntityManager entityManager;
 		
 	// set up constructor injection
 	@Autowired
 	public VeranstalterverifizierungDAOHibernateImpl(EntityManager theEntityManager) {
 		entityManager = theEntityManager;
 	}
 	
 	
 	// eine Liste mit allen beantragten Veranstalterverifizierungen wird zurückgegeben
 	@Override
 	public List<Veranstalterverifizierung> findAll() {

 		// get the current hibernate session
 		Session currentSession = entityManager.unwrap(Session.class);
 		
 		// create a query
 		Query<Veranstalterverifizierung> theQuery =
 				currentSession.createQuery("from Veranstalterverifizierung", Veranstalterverifizierung.class);
 		
 		// execute query and get result list
 		List<Veranstalterverifizierung> Veranstalterverifizierungn = theQuery.getResultList();
 		
 		// return the results		
 		return Veranstalterverifizierungn;
 	}


 	// eine Veranstalterverifizierung für einen User mit ID theId wird zurückgegeben
 	@Override
 	public Veranstalterverifizierung findById(int theId) {

 		// get the current hibernate session
 		Session currentSession = entityManager.unwrap(Session.class);
 		
 		// get the Veranstalterverifizierung
 		Veranstalterverifizierung theVeranstalterverifizierung =
 				currentSession.get(Veranstalterverifizierung.class, theId);
 		
 		// return the Veranstalterverifizierung
 		return theVeranstalterverifizierung;
 	}


 	// eine Veranstalterverifizierungsanfrage wird in der DB gepseichert
 	@Override
 	public void save(Veranstalterverifizierung theVeranstalterverifizierung) {

 		// get the current hibernate session
 		Session currentSession = entityManager.unwrap(Session.class);

 		// save Veranstalterverifizierung
 		currentSession.saveOrUpdate(theVeranstalterverifizierung);
 		
 	}

 	

 	// eine Veranstalterverifizeriung mit der Id theId wird aus der DB gelöscht
 	@Override
 	public void deleteById(int theId) {
 		
 		// get the current hibernate session
 		Session currentSession = entityManager.unwrap(Session.class);
 				
 		// delete object with primary key
 		Query theQuery = 
 				currentSession.createQuery(
 						"delete from Veranstalterverifizierung where id=:theId");
 		theQuery.setParameter("theId", theId);
 		
 		theQuery.executeUpdate();
 	}

}
