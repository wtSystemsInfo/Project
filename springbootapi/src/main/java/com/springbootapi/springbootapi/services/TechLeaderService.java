package com.springbootapi.springbootapi.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootapi.springbootapi.models.DTO.TechLeaderRequestDTO;
import com.springbootapi.springbootapi.models.DTO.TechLeaderResponseDTO;
import com.springbootapi.springbootapi.models.entities.TechLeader;
import com.springbootapi.springbootapi.repositories.TechLeaderRepository;

/**
 * @author William Toloto
 */
 
@Service
public class TechLeaderService {
	
	@Autowired
	private TechLeaderRepository repository; 
	private Logger logger = Logger.getLogger(TechLeaderService.class.getName());
	
	public TechLeader saveTechLeader(TechLeaderRequestDTO data) throws Exception {
		TechLeader tLeader = new TechLeader(data);
		if(!validateTechLeader(tLeader)) {
			logger.log(Level.INFO, "Houve um erro ao salvar o Tech Leader!");
			throw new Exception("Não foi possível salvar Líder de Equipe! Existem campos inválidos!");
		}else {
			repository.save(tLeader);
			logger.log(Level.INFO, "Líder de Equipe salvo com sucesso!");
			return tLeader;
		}
	}
	
	
	public List<TechLeaderResponseDTO> listAll(){
		List<TechLeaderResponseDTO> tLeaderList = repository.findAll()
				.stream().map(TechLeaderResponseDTO::new)
				.toList();
		return tLeaderList;
	}
	
	public List<TechLeaderResponseDTO> findTechLeaderByName(String name){
		List<TechLeader> listTechLeader = repository.findTechLeaderByName(name);
	    List<TechLeaderResponseDTO> tLeaderList = listTechLeader.stream()
	            .map(TechLeaderResponseDTO::new)
	            .collect(Collectors.toList());
	    logger.log(Level.INFO, "Gerado lista de Líderes contendo o nome :" + name + ", com sucesso!");
	    return tLeaderList;
	}
	
	
	public TechLeader findTechLeaderById(Long id) throws Exception {
		TechLeader tLeader = repository.findById(id).orElseThrow(( )-> new Exception("Líder de Equipe não encontrado!"));
		logger.log(Level.INFO, "Líder de Equipe com o id : " + id + " localizado com sucesso!");
		return tLeader;
	}
	
	public void deleteTechLeaderById(Long id) throws Exception {
		TechLeader tLeader = findTechLeaderById(id);
		if(tLeader != null) {
			repository.deleteById(id);
			logger.log(Level.INFO, "Líder de Equipe deletado com sucesso!");
		}
	}
	
	public TechLeader updateTechLeader(Long id, TechLeaderRequestDTO data) throws Exception {
		TechLeader tLeader = findTechLeaderById(id);
		tLeader.setName(data.name());
		if(!validateTechLeader(tLeader)) {
			throw new Exception("Não foi possível atualizar Líder de Equipe! Existem campos inválidos!");
		}else {
			repository.save(tLeader);
			logger.log(Level.INFO, "Líder de Equipe atualizado com sucesso!");
			return tLeader;
		}
	}
	

	//validar campos obrigatórios
	public boolean validateTechLeader(TechLeader tLeader) {
		if(tLeader.getName().isEmpty() || tLeader.getName().isBlank()) {
			return false;
		}
		return true;
	}
	
}
