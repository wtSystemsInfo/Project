package com.springbootapi.springbootapi.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootapi.springbootapi.DTO.ProjectRequestDTO;
import com.springbootapi.springbootapi.DTO.ProjectResponseDTO;
import com.springbootapi.springbootapi.entities.Customer;
import com.springbootapi.springbootapi.entities.Project;
import com.springbootapi.springbootapi.entities.Project.Status;
import com.springbootapi.springbootapi.repositories.ProjectRepository;

/**
 * @author William Toloto
 */
 
@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository repository;
	@Autowired
	private CustomerService serviceCustomer;
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	
	public Project saveProject(ProjectRequestDTO data) throws Exception{
		Project projectData = new Project(data);
		
		if(!validateProject(projectData)) {
			throw new Exception("Não foi possível salvar projeto! Existem campos inválidos!");
		}else {
			repository.save(projectData);
			logger.log(Level.INFO, "Projeto salvo com sucesso!");
			return projectData;
		}
		
	}
	
	public List<ProjectResponseDTO> listAll(){
		 List<ProjectResponseDTO> projectList = repository.findAll().stream().map(ProjectResponseDTO::new).toList();
		 return projectList;
	}
	
	
	public Project findProjectById(Long id) throws Exception {
		Project project= repository.findById(id).orElseThrow(( )-> new Exception("Projeto não encontrado!"));
		logger.log(Level.INFO, "Projeto id : " + id + " localizado com sucesso!");
		return project;
	}
	
	
	public List<ProjectResponseDTO> findProjectByDescription(String desc){
		List<ProjectResponseDTO> ProjectList = repository.findProjectByDescription(desc).stream().map(ProjectResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de projetos contendo a descrição :" + desc + ", com sucesso!");
		return ProjectList;
	}
	
	
	public List<ProjectResponseDTO> findProjectByStatus(Status status){
		List<ProjectResponseDTO> ProjectList = repository.findProjectByStatus(status).stream().map(ProjectResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de projetos contendo a descrição :" + status + ", com sucesso!");
		return ProjectList;
	}
	
	public Project updateProject(Long id, ProjectRequestDTO data) throws Exception{ 
		Project projectData = findProjectById(id);
		projectData.setDescription(data.description());
		projectData.setBeginDate(data.beginDate());
		projectData.setDeliveryDate(data.deliveryDate());
		projectData.setStatus(data.status());
		projectData.setCustomer(data.customer());
		
		if(!validateProject(projectData)) {
			throw new Exception("Não foi possível atualizar o projeto! Existem campos inválidos!");
		}else {
			repository.save(projectData);
			logger.log(Level.INFO, "Projeto atualizado com sucesso!");
			return projectData;
		}
	}

	public void deleteById(Long id) throws Exception {
		Project projectData = findProjectById(id);
		if(projectData != null) {
			repository.deleteById(id);
			logger.log(Level.INFO, "Projeto deletado com sucesso!");
		}		
	}
	
	
	//validar campos obrigatórios
	public boolean validateProject(Project project)  throws Exception{
		if(project.getDescription().isEmpty() || project.getDescription().isBlank()) {
			return false;
		}
		
		if(project.getBeginDate() == null &&  project.getDeliveryDate() == null) {
			return false;
		}
		
		if(!validateDate(project.getBeginDate()) || !validateDate(project.getDeliveryDate())) {
			logger.log(Level.INFO, "Data informada em formato incorreto!");
			return false;
		}
		
		if(project.getBeginDate().isAfter(project.getDeliveryDate())) {	
			logger.log(Level.INFO, "Data de início do projeto posterior a data de entrega do projeto!");
			return false;
		}
		
		if(project.getStatus() != Status.ABERTO && project.getStatus() != Status.FECHADO) {
			return false;
		}
		
		Customer customer = serviceCustomer.findCustomerById(project.getCustomer().getId());
		if(customer !=null) {
			customer = null;
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
