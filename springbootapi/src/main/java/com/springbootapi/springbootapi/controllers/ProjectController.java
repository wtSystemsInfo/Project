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

import com.springbootapi.springbootapi.DTO.ProjectRequestDTO;
import com.springbootapi.springbootapi.DTO.ProjectResponseDTO;
import com.springbootapi.springbootapi.entities.Project;
import com.springbootapi.springbootapi.entities.Project.Status;
import com.springbootapi.springbootapi.services.ProjectService;

/**
 * @author William Toloto
 */
 
@RestController
@RequestMapping("/project")
public class ProjectController {
	
	
	@Autowired
	private ProjectService service;

	@PostMapping
	public ResponseEntity<Project> saveProject(@RequestBody ProjectRequestDTO project) throws Exception{
		
		Project newProject = service.saveProject(project);
		return new ResponseEntity<>(newProject, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProjectResponseDTO>> getAllProject(){
		List<ProjectResponseDTO> listProject = service.listAll();
		return new ResponseEntity<>(listProject, HttpStatus.OK);
	}

	@GetMapping("/ProjectById") 
	public ResponseEntity<Project> getProjectById(@RequestParam("id") Long idProject) throws Exception{
		Project project = service.findProjectById(idProject);
		return new ResponseEntity<>(project, HttpStatus.OK);
	}

	@GetMapping("/ProjectByDescription") 
	public ResponseEntity<List<ProjectResponseDTO>> getProjectByDescription(@RequestParam("desc") String descProject){
		List<ProjectResponseDTO> listProject  = service.findProjectByDescription(descProject);
		return new ResponseEntity<>(listProject, HttpStatus.OK);
	}
	
	@GetMapping("/ProjectByStatus") 
	public ResponseEntity<List<ProjectResponseDTO>> getProjectByStatus(@RequestParam("status") Status status){
		List<ProjectResponseDTO> listProject  = service.findProjectByStatus(status);
		return new ResponseEntity<>(listProject, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO project) throws Exception{
		Project updatedLeader = service.updateProject(id, project);
		return new ResponseEntity<>(updatedLeader, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) throws Exception{
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
