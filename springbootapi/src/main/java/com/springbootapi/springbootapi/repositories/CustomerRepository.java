package com.springbootapi.springbootapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.springbootapi.springbootapi.entities.Customer;

/**
 * @author William Toloto
 */

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query("SELECT c FROM customer c WHERE c.name LIKE %:name%")
	List<Customer> findCustomerByName(@Param("name") String name);
	
	@Query("SELECT c FROM customer c WHERE c.docId = :doc")
	Optional<Customer> findCustomerByDoc(@Param("doc") String doc);
	
}
