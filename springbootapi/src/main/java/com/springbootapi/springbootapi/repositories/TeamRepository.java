package com.springbootapi.springbootapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootapi.springbootapi.models.entities.Team;

/**
 * @author William Toloto
 */

public interface TeamRepository extends JpaRepository<Team, Long>{
	
	@Query("SELECT t FROM team t WHERE t.description LIKE %:description%")
	List<Team> findTeamByDescription(@Param("description") String description);
	
}
