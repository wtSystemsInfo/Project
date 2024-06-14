package com.springbootapi.springbootapi.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootapi.springbootapi.models.entities.TechLeader;

/**
 * @author William Toloto
 */


public interface TechLeaderRepository extends JpaRepository<TechLeader, Long>{


	@Query("SELECT t FROM techleader t WHERE t.name LIKE %:name%")
	List<TechLeader> findTechLeaderByName(@Param("name") String name);
	
}
