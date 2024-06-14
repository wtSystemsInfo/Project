package com.springbootapi.springbootapi.services;

import java.util.List;
import java.util.logging.Level;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootapi.springbootapi.models.DTO.ActivityRequestDTO;
import com.springbootapi.springbootapi.models.DTO.ActivityResponseDTO;
import com.springbootapi.springbootapi.models.entities.Activity;
import com.springbootapi.springbootapi.models.entities.Project;
import com.springbootapi.springbootapi.models.entities.Team;
import com.springbootapi.springbootapi.models.entities.Activity.StatusActivity;
import com.springbootapi.springbootapi.repositories.ActivityRepository;

/**
 * @author William Toloto
 */
 
@Service
public class ActivityService {
	
	@Autowired
	private ActivityRepository repository;
	@Autowired
	private ProjectService serviceP;
	@Autowired
	private TeamService serviceT;
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	
	public Activity saveActivity(ActivityRequestDTO data) throws Exception{
		Activity activityData = new Activity(data);
		
		if(!validateActivity(activityData)) {
			throw new Exception("Não foi possível salvar atividade! Existem campos inválidos!");
		}else {
			repository.save(activityData);
			logger.log(Level.INFO, "Atividade salvo com sucesso!");
			return activityData;
		}
		
	}
	
	
	public List<ActivityResponseDTO> listAll(){
		 List<ActivityResponseDTO> activityList = repository.findAll().stream().map(ActivityResponseDTO::new).toList();
		 return activityList;
	}


	public Activity findActivityById(Long id) throws Exception {
		Activity activity= repository.findById(id).orElseThrow(( )-> new Exception("Atividade não encontrada!"));
		logger.log(Level.INFO, "Atividade id : " + id + " localizado com sucesso!");
		return activity;
	}


	public List<ActivityResponseDTO> findActivityByDescription(String desc){
		List<ActivityResponseDTO> ActivityList = repository.findActivitytByDescription(desc).stream().map(ActivityResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de atividades contendo a descrição :" + desc + ", com sucesso!");
		return ActivityList;
	}


	public List<ActivityResponseDTO> findActivityByStatus(StatusActivity status){
		List<ActivityResponseDTO> ActivityList = repository.findActivityByStatus(status).stream().map(ActivityResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de atividades contendo a descrição :" + status + ", com sucesso!");
		return ActivityList;
	}
	
	
	public Activity updateActivity(Long id, ActivityRequestDTO data) throws Exception{ 
		Activity activityData = findActivityById(id);
		activityData.setDescription(data.description());
		activityData.setDeadLineDate(data.deadLineDate());
		activityData.setStatus(data.status());
		activityData.setProject(data.project());
		activityData.setTeam(data.team());
		
		if(!validateActivity(activityData)) {
			throw new Exception("Não foi possível atualizar a atividade! Existem campos inválidos!");
		}else {
			repository.save(activityData);
			logger.log(Level.INFO, "Atividade atualizado com sucesso!");
			return activityData;
		}
	}
	
	public void deleteById(Long id) throws Exception {
		Activity activityData = findActivityById(id);
		if(activityData != null) {
			repository.deleteById(id);
			logger.log(Level.INFO, "Projeto deletado com sucesso!");
		}		
	}
	
	//validar campos obrigatórios
	public boolean validateActivity(Activity activity)  throws Exception{
		
		if(activity.getDescription().isEmpty() || activity.getDescription().isBlank()) {
			return false;
		}

		if(activity.getDeadLineDate() == null) {
			return false;
		}

		if(!validateDate(activity.getDeadLineDate())) {
			logger.log(Level.INFO, "Data informada em formato incorreto!");
			return false;
		}
		
		if(activity.getStatus() != StatusActivity.ABERTO && activity.getStatus() != StatusActivity.FINALIZADO 
				&& activity.getStatus() != StatusActivity.PROGRESSO) {
			return false;
		}
		
		Project project = serviceP.findProjectById(activity.getProject().getId());
		Team team = serviceT.findTeamById(activity.getTeam().getId());
		
		if(project != null) {
			project = null;
		}
		
		if(team != null) {
			team = null;
		}
		
		return true;
	}
	
	private boolean validateDate(LocalDate date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            LocalDate parsedDate = LocalDate.parse(dateString, formatter);
            return date.equals(parsedDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
