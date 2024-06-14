package com.springbootapi.springbootapi.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.springbootapi.springbootapi.models.DTO.CustomerRequestDTO;
import com.springbootapi.springbootapi.models.DTO.ProjectRequestDTO;
import com.springbootapi.springbootapi.models.DTO.ProjectResponseDTO;
import com.springbootapi.springbootapi.models.entities.Customer;
import com.springbootapi.springbootapi.models.entities.Project;
import com.springbootapi.springbootapi.models.entities.Project.Status;
import com.springbootapi.springbootapi.repositories.ProjectRepository;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

	@Mock
	private ProjectRepository repository;

	@InjectMocks
	private ProjectService services;
	
	@Mock
    private CustomerService serviceCustomer;

	private ProjectRequestDTO project;

	private ProjectRequestDTO projectWrong;
	
		
	
	
	@BeforeEach
	public void setup() {
		
		Customer customer = new Customer(
				1L,
				"João Silva",
				"48744454798",
				"joao@gmail.com",
				"123456",
				"87013060",
				"1480",
				"Próximo ao parque",
				"+1234567890"
				);
		
		
	    project = new ProjectRequestDTO(
	        "Projeto A",
	        LocalDate.of(2024, 6, 1), 
	        LocalDate.of(2024, 12, 31), 
	        Status.ABERTO, 
	        customer 
	    );

	    projectWrong = new ProjectRequestDTO(
	        "Projeto B",
	        LocalDate.of(2024, 12, 31), 
	        LocalDate.of(2024, 6, 1), 
	        Status.ABERTO, 
	        customer 
	    );
	}
	
	
	@DisplayName("Given valid Project data, when save Project then Return Project JSON")
    @Test
    void givenValidProjectData_whenSaveProject_thenReturnProject() throws Exception {
        // Given
        Project projectData = new Project(project);
        given(repository.save(any(Project.class))).willReturn(projectData);

        // When
        Project result = services.saveProject(project);

        // Then
        assertNotNull(result);
        assertEquals("Projeto A", result.getDescription());
        assertEquals(LocalDate.of(2024, 6, 1), result.getBeginDate());
        assertEquals(LocalDate.of(2024, 12, 31), result.getDeliveryDate());
        assertEquals(Status.ABERTO, result.getStatus());
        assertEquals(1L, result.getCustomer().getId());
        assertEquals("João Silva", result.getCustomer().getName());
        assertEquals("48744454798", result.getCustomer().getDocId());
        assertEquals("joao@gmail.com", result.getCustomer().getEmail());
        assertEquals("123456", result.getCustomer().getCityId());
        assertEquals("87013060", result.getCustomer().getPostalCode());
        assertEquals("1480", result.getCustomer().getHouseNumber());
        assertEquals("Próximo ao parque", result.getCustomer().getReference());
        assertEquals("+1234567890", result.getCustomer().getPhone());
    }
	
	@DisplayName("Given invalid Project data, when save Project then throw Exception")
	@Test
	void givenInvalidProjectData_whenSaveProject_thenThrowException() {
		
	    // When
		Throwable exception = assertThrows(Exception.class, () -> {
		    services.saveProject(projectWrong);
		});

		// Then
		assertEquals("Não foi possível salvar projeto! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Project.class));
	}
	
	
	@DisplayName("JUnit test for given Project List when find all Projects then Return Project list")
	@Test
	void testeGivenProjectList_WhenFindAll_thenReturnProjectList() {
	    // Given
		
		Customer customer = new Customer(
				1L,
				"João Silva",
				"48744454798",
				"joao@gmail.com",
				"123456",
				"87013060",
				"1480",
				"Próximo ao parque",
				"+1234567890"
				);
		
		ProjectRequestDTO projectA = new ProjectRequestDTO(
	        "Projeto A",
	        LocalDate.of(2024, 6, 1),
	        LocalDate.of(2024, 12, 31),
	        Status.ABERTO,
	        customer 
	    );

		ProjectRequestDTO projectB = new ProjectRequestDTO(
	        "Projeto B",
	        LocalDate.of(2024, 4, 20), 
	        LocalDate.of(2024, 8, 16),
	        Status.ABERTO, 
	        customer
	    );
		
		
		
	    List<ProjectRequestDTO> requestDTOs = Arrays.asList(projectA, projectB);

		List<Project> projects = requestDTOs.stream()
			        .map(Project::new)
			        .collect(Collectors.toList());

	    given(repository.findAll()).willReturn(projects);

	    // When
	    List<ProjectResponseDTO> result = services.listAll();

	    // Then
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals("Projeto A", result.get(0).description());
	    assertEquals("Projeto B", result.get(1).description());
	}
	
	@DisplayName("JUnit test for given Empty Project List when find all Projects then Return Empty List")
	@Test
	void testeGivenEmptyProjectList_WhenFindAll_thenReturnEmptyList() throws Exception {
		// Given
		given(repository.findAll()).willReturn(Collections.emptyList());

		// When
		List<ProjectResponseDTO> result = services.listAll();

		// Then
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
	
	@DisplayName("JUnit test for given Project when data is updated then Return updated Project")
	@Test
	void testeGivenProject_WhenUpdateProjectWithValidData_thenReturnUpdatedProject() throws Exception {
		// Given
		Customer customer = new Customer(
				1L,
				"Rafael Souza",
				"55604856387",
				"rafael@gmail.com",
				"123456",
				"87013060",
				"236",
				"Próximo ao posto",
				"+1233265874"
				);
		
		
	   
	    ProjectRequestDTO newProject = new ProjectRequestDTO(
	        "Projeto A atualizado",
	        LocalDate.of(2024, 5, 19), 
	        LocalDate.of(2024, 9, 21), 
	        Status.ABERTO, 
	        customer
	    );
	    
	    
	    
	    Project existingProject = new Project(
	    		 1L,
	            "Projeto Original",
	            LocalDate.of(2024, 6, 1),
	            LocalDate.of(2024, 12, 31),
	            Status.ABERTO,
	            new Customer()
	        );
	    
	    given(repository.findById(existingProject.getId())).willReturn(Optional.of(existingProject));
	    given(repository.save(any())).willReturn(existingProject);
	    
	    // When
	    Project updatedProject = services.updateProject(existingProject.getId(), newProject);
	    
	    assertNotNull(updatedProject);
	    assertEquals(existingProject.getId(), updatedProject.getId());
	    assertEquals(newProject.description(), updatedProject.getDescription());
	    assertEquals(newProject.beginDate(), updatedProject.getBeginDate());
	    assertEquals(newProject.deliveryDate(), updatedProject.getDeliveryDate());
	    assertEquals(newProject.status(), updatedProject.getStatus());
        assertEquals(1L, newProject.customer().getId());
        assertEquals("Rafael Souza", newProject.customer().getName());
        assertEquals("55604856387", newProject.customer().getDocId());
        assertEquals("rafael@gmail.com", newProject.customer().getEmail());
        assertEquals("123456", newProject.customer().getCityId());
        assertEquals("87013060", newProject.customer().getPostalCode());
        assertEquals("236", newProject.customer().getHouseNumber());
        assertEquals("Próximo ao posto", newProject.customer().getReference());
        assertEquals("+1233265874", newProject.customer().getPhone());

	}
	
	
	@DisplayName("JUnit test for given Project wrong data then Return updated Project")
	@Test
	void testeGivenProject_WhenUpdateProjectWithWrongData_thenThrowException() throws Exception {
		// Given
	   
	    ProjectRequestDTO newProject = new ProjectRequestDTO(
	        "",
	        LocalDate.of(2024, 5, 19), 
	        LocalDate.of(2024, 9, 21), 
	        Status.ABERTO, 
	        new Customer()
	    );
	    
	    
	    
	    Project existingProject = new Project(
	    		 1L,
	            "Projeto Original",
	            LocalDate.of(2024, 6, 1),
	            LocalDate.of(2024, 12, 31),
	            Status.ABERTO,
	            new Customer()
	        );
	    
	    given(repository.findById(existingProject.getId())).willReturn(Optional.of(existingProject));
	    
	    
	    // When
	    Throwable exception = assertThrows(Exception.class, () -> {
	        services.updateProject(existingProject.getId(), newProject);
	    });

	    // Then
	    assertEquals("Não foi possível atualizar o projeto! Existem campos inválidos!", exception.getMessage());
	    verify(repository, never()).save(any(Project.class));
	    
	}
	
	@DisplayName("JUnit test for deleting an existing project")
	@Test
	void testDeleteExistingProject() throws Exception {
		// Given
		Long id = 1L;
		Project existingProject = new Project(project);
		existingProject.setId(id);
		
		given(repository.findById(id)).willReturn(Optional.of(existingProject));
		
		// When
		services.deleteById(id);
		
		// Then
		verify(repository, times(1)).deleteById(id);
	}


	@DisplayName("JUnit test for deleting a non-existing project")
	@Test
	void testeDeleteNonExistingProject() throws Exception {
		// Given
		Long id = 1L;
		
		given(repository.findById(id)).willReturn(Optional.empty());
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.deleteById(id);
		});
		
		// Then
		assertEquals("Projeto não encontrado!", exception.getMessage());
		verify(repository, never()).deleteById(anyLong());
	}
	
	
}
