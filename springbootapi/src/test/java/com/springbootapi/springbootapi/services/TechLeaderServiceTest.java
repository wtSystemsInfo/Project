package com.springbootapi.springbootapi.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springbootapi.springbootapi.models.DTO.TechLeaderRequestDTO;
import com.springbootapi.springbootapi.models.DTO.TechLeaderResponseDTO;
import com.springbootapi.springbootapi.models.entities.TechLeader;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.springbootapi.springbootapi.repositories.TechLeaderRepository;

/**
 * @author William Toloto
 */
 

@ExtendWith(MockitoExtension.class)
public class TechLeaderServiceTest {
	
	@Mock
	private TechLeaderRepository repository;
	
	@InjectMocks
	private TechLeaderService services;
	
	private TechLeaderRequestDTO tLeader;
	
	private TechLeaderRequestDTO tLeaderWrong;
	
	
	@BeforeEach
	public void setup() {
		
		tLeader = new TechLeaderRequestDTO(
				"William Toloto");
		
		tLeaderWrong = new TechLeaderRequestDTO(
				"");
		
	}
	
	
	@DisplayName("JUnit test for given Tech Leader when save Tech Leader then Return Tech Leader JSON")
	@Test
	void testeGivenTechLeader_WhenSaveTechLeader_thenReturnTechLeader() throws Exception{
		
		//Given
		given(repository.save(any())).willReturn(new TechLeader());
		
		//When
		TechLeader savedTechLeader = services.saveTechLeader(tLeader);
		
		
		//Then 
		assertNotNull(savedTechLeader);
		assertEquals("William Toloto", savedTechLeader.getName());
		
	}
	
	@DisplayName("JUnit test for given Tech Leader with no name when save Tech Leader then will not save any")
	@Test
	void testeGivenTechLeaderNoName_WhenSaveTechLeader_thenWillNotSaveAny() throws Exception{
		
	    // When 
		Throwable exception = assertThrows(Throwable.class, () -> {
	        services.saveTechLeader(tLeaderWrong);
	    });
	    

	    // Then
	    assertEquals("Não foi possível salvar Líder de Equipe! Existem campos inválidos!", exception.getMessage());
	    verify(repository, never()).save(any(TechLeader.class));
		
	}
	
	
	@DisplayName("JUnit test for given Tech Leader List when find all Tech Leaders then Return Tech Leader list")
	@Test
	void testeGivenTechLeaderList_WhenFindAll_thenReturnTechLeaderList() throws Exception{

		//Given
		TechLeaderRequestDTO williamToloto= new TechLeaderRequestDTO("William Toloto");
	    TechLeaderRequestDTO sebstiaoCarneiro = new TechLeaderRequestDTO("Sebastião Carneiro");
	    List<TechLeaderRequestDTO> requestDTOs = Arrays.asList(williamToloto, sebstiaoCarneiro);
	    
	    List<TechLeader> techLeaders = requestDTOs.stream()
	            .map(TechLeader::new)
	            .collect(Collectors.toList());
	        
	        given(repository.findAll()).willReturn(techLeaders);
		
	        
    	// When
        List<TechLeaderResponseDTO> result = services.listAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("William Toloto", result.get(0).name());
        assertEquals("Sebastião Carneiro", result.get(1).name());
	}
	
	
	@DisplayName("JUnit test for given Tech Leader List when list is empty then Return empty Tech Leader List")
	@Test
	void testeGivenTechLeaderList_WhenListEmpty_thenReturnEmptyTechLeaderList() throws Exception{

		//Given
	    List<TechLeader> techLeaders = Collections.emptyList();
	    given(repository.findAll()).willReturn(techLeaders);
	    
	    // When
	    List<TechLeaderResponseDTO> result = services.listAll();

	    // Then
	    assertNotNull(result);
	    assertTrue(result.isEmpty());
	}
	
	@DisplayName("JUnit test for given Tech Leader when name is cahnged then Return updated Tech Leader")
	@Test
	void testeGivenTechLeader_WhenUpdateTechLeaderWithValidData_thenReturnUpdatedTechLeader() throws Exception {
		
		//Given
	    Long id = 1L;
	    TechLeaderRequestDTO newData = tLeader;
	    TechLeader existingLeader = new TechLeader();
	    existingLeader.setId(id);
	    existingLeader.setName("William Alves Toloto");
	    
	    given(repository.findById(id)).willReturn(Optional.of(existingLeader));
	    given(repository.save(any())).willReturn(existingLeader);
	    
	    //When
	    TechLeader updatedLeader = services.updateTechLeader(id, newData);
	    
	    
	    //Then 
	    assertNotNull(updatedLeader);
	    assertEquals(id, updatedLeader.getId());
	    assertEquals(newData.name(), updatedLeader.getName());
		
	}
	
	@DisplayName("JUnit test for given Tech Leader when invalid data is set then Throw an Exception")
	@Test
	void testeGivenTechLeader_WhenUpdateTechLeaderWithInvalidData_thenThrowException() throws Exception {
	    //Given
	    Long id = 1L;
	    TechLeaderRequestDTO invalidData = tLeaderWrong; // Invalid name
	    TechLeader existingLeader = new TechLeader();
	    existingLeader.setId(id);
	    existingLeader.setName("");
	    
	    given(repository.findById(id)).willReturn(Optional.of(existingLeader));
	    
	    //When
	    Throwable exception = assertThrows(Throwable.class, () -> {
	        services.updateTechLeader(id, invalidData);
	    });

	    //Then
	    assertEquals("Não foi possível atualizar Líder de Equipe! Existem campos inválidos!", exception.getMessage());
	    verify(repository, never()).save(any(TechLeader.class));
	}
	
	@DisplayName("JUnit test for deleting Tech Leader by ID")
	@Test
	void testeGivenTechLeaderId_WhenDeleteTechLeaderById_thenDeleteSuccessfully() throws Exception {
	    // Given
	    Long id = 1L;
	    TechLeader existingLeader = new TechLeader();
	    existingLeader.setId(id);

	    given(repository.findById(id)).willReturn(Optional.of(existingLeader));

	    // When
	    services.deleteTechLeaderById(id);

	    // Then
	    verify(repository, times(1)).deleteById(id);
	    
	}
	
	@DisplayName("JUnit test for deleting Tech Leader by ID when Tech Leader does not exist")
	@Test
	void testeGivenTechLeaderId_WhenDeleteTechLeaderById_thenThrowException() {
	    // Given
		Long id = 1L;
	    given(repository.findById(id)).willReturn(Optional.empty());

	    // When 
	    Exception exception = assertThrows(Exception.class, () -> {
	        services.deleteTechLeaderById(id);
	    });

	    // Then
	    assertEquals("Líder de Equipe não encontrado!", exception.getMessage());
	    verify(repository, never()).deleteById(id);
	}
	

}
