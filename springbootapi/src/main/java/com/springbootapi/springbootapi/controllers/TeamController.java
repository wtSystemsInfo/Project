package com.springbootapi.springbootapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapi.springbootapi.DTO.TeamRequestDTO;
import com.springbootapi.springbootapi.DTO.TeamResponseDTO;
import com.springbootapi.springbootapi.entities.Team;
import com.springbootapi.springbootapi.services.TeamService;

/**
 * @author William Toloto
 */
 
@RestController
@RequestMapping("/team")
public class TeamController {
	
	@Autowired
	private TeamService service;

	@PostMapping
	public ResponseEntity<Team> saveTeam(@RequestBody TeamRequestDTO team) throws Exception{
		
		Team newTeam = service.saveTeam(team);
		return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TeamResponseDTO>> getAllTeam(){
		List<TeamResponseDTO> listTeam = service.listAll();
		return new ResponseEntity<>(listTeam, HttpStatus.OK);
	}

	@GetMapping("/TeamById") 
	public ResponseEntity<Team> getTeamById(@RequestParam("id") Long idTeam) throws Exception{
		Team team = service.findTeamById(idTeam);
		return new ResponseEntity<>(team, HttpStatus.OK);
	}

	@GetMapping("/TeamByDescription") 
	public ResponseEntity<List<TeamResponseDTO>> getTeamByDescription(@RequestParam("desc") String descTeam){
		List<TeamResponseDTO> listTeam  = service.findTeamByDescription(descTeam);
		return new ResponseEntity<>(listTeam, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody TeamRequestDTO team) throws Exception{
		Team updatedLeader = service.updateTeam(id, team);
		return new ResponseEntity<>(updatedLeader, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTeam(@PathVariable Long id) throws Exception{
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
