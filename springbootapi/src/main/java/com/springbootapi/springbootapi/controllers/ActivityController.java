package com.springbootapi.springbootapi.controllers;

import java.util.List;

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

import com.springbootapi.springbootapi.DTO.ActivityRequestDTO;
import com.springbootapi.springbootapi.DTO.ActivityResponseDTO;
import com.springbootapi.springbootapi.entities.Activity;
import com.springbootapi.springbootapi.entities.Activity.StatusActivity;
import com.springbootapi.springbootapi.services.ActivityService;

/**
 * @author William Toloto
 */
 
@RestController
@RequestMapping("/activity")
public class ActivityController {
	
	private ActivityService service;

	@PostMapping
	public ResponseEntity<Activity> saveActivity(@RequestBody ActivityRequestDTO activity) throws Exception{
		
		Activity newActivity = service.saveActivity(activity);
		return new ResponseEntity<>(newActivity, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ActivityResponseDTO>> getAllActivity(){
		List<ActivityResponseDTO> listActivity = service.listAll();
		return new ResponseEntity<>(listActivity, HttpStatus.OK);
	}

	@GetMapping("/ActivityById") 
	public ResponseEntity<Activity> getActivityById(@RequestParam("id") Long idActivity) throws Exception{
		Activity activity = service.findActivityById(idActivity);
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}

	@GetMapping("/ActivityByDescription") 
	public ResponseEntity<List<ActivityResponseDTO>> getActivityByDescription(@RequestParam("desc") String descActivity){
		List<ActivityResponseDTO> listActivity  = service.findActivityByDescription(descActivity);
		return new ResponseEntity<>(listActivity, HttpStatus.OK);
	}

	@GetMapping("/ActivityByStatus") 
	public ResponseEntity<List<ActivityResponseDTO>> getActivityByStatus(@RequestParam("status") StatusActivity status){
		List<ActivityResponseDTO> listActivity  = service.findActivityByStatus(status);
		return new ResponseEntity<>(listActivity, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody ActivityRequestDTO activity) throws Exception{
		Activity updatedLeader = service.updateActivity(id, activity);
		return new ResponseEntity<>(updatedLeader, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteActivity(@PathVariable Long id) throws Exception{
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
