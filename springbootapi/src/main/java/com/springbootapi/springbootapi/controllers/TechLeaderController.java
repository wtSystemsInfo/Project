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

import com.springbootapi.springbootapi.models.DTO.TechLeaderRequestDTO;
import com.springbootapi.springbootapi.models.DTO.TechLeaderResponseDTO;
import com.springbootapi.springbootapi.models.entities.TechLeader;
import com.springbootapi.springbootapi.services.TechLeaderService;

/**
 * @author William Toloto
 */
 
@RestController
@RequestMapping("/techleader")
public class TechLeaderController {
	
	@Autowired
	private TechLeaderService service;
	
	
	@PostMapping
	public ResponseEntity<TechLeader> saveTechLeader(@RequestBody TechLeaderRequestDTO tLeader) throws Exception{
		
		TechLeader newLeader = service.saveTechLeader(tLeader);
		return new ResponseEntity<>(newLeader, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<TechLeaderResponseDTO>> getAllTechLeads(){
		List<TechLeaderResponseDTO> listTechLeads = service.listAll();
		return new ResponseEntity<>(listTechLeads, HttpStatus.OK);
	}
	
	@GetMapping("/TechLeaderById") 
	public ResponseEntity<TechLeader> getTechLeaderById(@RequestParam("id") Long idTechLeader) throws Exception{
		TechLeader tLeader = service.findTechLeaderById(idTechLeader);
		return new ResponseEntity<>(tLeader, HttpStatus.OK);
	}
	
	@GetMapping("/TechLeaderByName") 
	public ResponseEntity<List<TechLeaderResponseDTO>> getTechLeaderByName(@RequestParam("name") String nameTechLeader){
		List<TechLeaderResponseDTO> listTechLeads  = service.findTechLeaderByName(nameTechLeader);
		return new ResponseEntity<>(listTechLeads, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<TechLeader> updateTechLeader(@PathVariable Long id, @RequestBody TechLeaderRequestDTO tLeader) throws Exception{
		TechLeader updatedLeader = service.updateTechLeader(id, tLeader);
		return new ResponseEntity<>(updatedLeader, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTechLeader(@PathVariable Long id) throws Exception{
	    service.deleteTechLeaderById(id);
	    return ResponseEntity.noContent().build();
	}
}
