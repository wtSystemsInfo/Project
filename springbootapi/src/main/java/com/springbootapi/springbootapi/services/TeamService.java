package com.springbootapi.springbootapi.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootapi.springbootapi.models.DTO.TeamRequestDTO;
import com.springbootapi.springbootapi.models.DTO.TeamResponseDTO;
import com.springbootapi.springbootapi.models.entities.Team;
import com.springbootapi.springbootapi.models.entities.TechLeader;
import com.springbootapi.springbootapi.repositories.TeamRepository;

/**
 * @author William Toloto
 */
 
@Service
public class TeamService {
	
	@Autowired
	private TeamRepository repository;
	@Autowired
	private TechLeaderService serviceLeader;
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	public Team saveTeam(TeamRequestDTO data) throws Exception{
		Team teamData = new Team(data);
		
		if(!validateTeam(teamData)) {
			throw new Exception("Não foi possível salvar time! Existem campos inválidos!");
		}else {
			repository.save(teamData);
			logger.log(Level.INFO, "Time salvo com sucesso!");
			return teamData;
		}
		
	}
	
	public List<TeamResponseDTO> listAll(){
		 List<TeamResponseDTO> teamList = repository.findAll().stream().map(TeamResponseDTO::new).toList();
		 return teamList;
	}
	
	public Team findTeamById(Long id) throws Exception {
		Team team= repository.findById(id).orElseThrow(( )-> new Exception("Time não encontrado!"));
		logger.log(Level.INFO, "Time id : " + id + " localizado com sucesso!");
		return team;
	}
	
	public List<TeamResponseDTO> findTeamByDescription(String desc){
		List<TeamResponseDTO> TeamList = repository.findTeamByDescription(desc).stream().map(TeamResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de times contendo a descrição :" + desc + ", com sucesso!");
		return TeamList;
	}
	
	public Team updateTeam(Long id, TeamRequestDTO data) throws Exception{ 
		Team teamData = findTeamById(id);
		teamData.setDescription(data.description());
		teamData.setTeamMembers(data.teamMembers());
		teamData.setTechLeader(data.techLeader());
		
		if(!validateTeam(teamData)) {
			throw new Exception("Não foi possível atualizar o time! Existem campos inválidos!");
		}else {
			repository.save(teamData);
			logger.log(Level.INFO, "Time atualizado com sucesso!");
			return teamData;
		}
	}
	
	public void deleteById(Long id) throws Exception {
		Team teamData = findTeamById(id);
		if(teamData != null) {
			repository.deleteById(id);
			logger.log(Level.INFO, "Time deletado com sucesso!");
		}		
	}
	
	
	//validar campos obrigatórios
		public boolean validateTeam( Team team ) throws Exception{
			if(team.getDescription().isBlank() || team.getDescription().isEmpty()) {
				return false;
			}
			
			TechLeader tLeader = serviceLeader.findTechLeaderById(team.getTechLeader().getId());
			if(tLeader != null) {
				tLeader = null;
			}
			return true;
		}
		
		public boolean isNumeric(String number) {
			String digits = "\\d+";
			
			return number.matches(digits);
		}
}
