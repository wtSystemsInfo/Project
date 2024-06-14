package com.springbootapi.springbootapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootapi.springbootapi.models.entities.Activity;
import com.springbootapi.springbootapi.models.entities.Activity.StatusActivity;



/**
 * @author William Toloto
 */


public interface ActivityRepository extends JpaRepository<Activity, Long>{
	
	@Query("SELECT a FROM activity a WHERE a.description LIKE %:description%")
	List<Activity> findActivitytByDescription(@Param("description") String description);
	
	@Query("SELECT a FROM activity a WHERE a.status = :status")
	List<Activity> findActivityByStatus(@Param("status") StatusActivity stauts);
}
