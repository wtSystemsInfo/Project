package com.springbootapi.springbootapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootapi.springbootapi.entities.Project;
import com.springbootapi.springbootapi.entities.Project.Status;

/**
 * @author William Toloto
 */

public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query("SELECT p FROM project p WHERE p.description LIKE %:description%")
	List<Project> findProjectByDescription(@Param("description") String description);
	
	@Query("SELECT p FROM project p WHERE p.status = :status")
	List<Project> findProjectByStatus(@Param("status") Status stauts);
}
