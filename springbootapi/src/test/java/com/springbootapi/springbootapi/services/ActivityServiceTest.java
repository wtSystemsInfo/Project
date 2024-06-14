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

import com.springbootapi.springbootapi.models.DTO.ActivityRequestDTO;
import com.springbootapi.springbootapi.models.DTO.ActivityResponseDTO;
import com.springbootapi.springbootapi.models.entities.Activity;
import com.springbootapi.springbootapi.models.entities.Customer;
import com.springbootapi.springbootapi.models.entities.Project;
import com.springbootapi.springbootapi.models.entities.Team;
import com.springbootapi.springbootapi.models.entities.TechLeader;
import com.springbootapi.springbootapi.models.entities.Activity.StatusActivity;
import com.springbootapi.springbootapi.models.entities.Project.Status;
import com.springbootapi.springbootapi.repositories.ActivityRepository;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {


	@Mock
	private ActivityRepository repository;

	@InjectMocks
	private ActivityService services;
	
	@Mock
    private ProjectService serviceProject;
	
	@Mock
    private TeamService serviceT; 

	private ActivityRequestDTO activity;

	private ActivityRequestDTO activityWrong;
	
		
	
	
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
		
		
	    Project project = new Project(
	    		1L,
		        "Projeto A",
		        LocalDate.of(2024, 6, 1), 
		        LocalDate.of(2024, 12, 31), 
		        Status.ABERTO, 
		        customer 
	    );
	    
	    TechLeader techLeader = new TechLeader(
	            1L,
	            "William Toloto"
	        );
	    
	    Team team = new Team(
	    		1L,
	    		"Time Back-End",
	    		5L,
	    		techLeader
	    		);
	    
	    
	    activity = new ActivityRequestDTO(
                "Desenvolver teste unitário",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
	    
	    
	    activityWrong = new ActivityRequestDTO(
                "",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
	}
	
	
	@DisplayName("Given valid Activity data, when save Activity then Return Activity JSON")
    @Test
    void givenValidActivityData_whenSaveActivity_thenReturnActivity() throws Exception {
		
		// Given
        Activity activityData = new Activity(activity);
        given(repository.save(any(Activity.class))).willReturn(activityData);

        // When
        Activity result = services.saveActivity(activity);

        // Then
        assertNotNull(result);
        assertEquals("Desenvolver teste unitário", result.getDescription());
        assertEquals(StatusActivity.ABERTO, result.getStatus());
        assertEquals(LocalDate.of(2024, 6, 14), result.getDeadLineDate());	
	
	}
	
	@DisplayName("Given invalid Activity data, when save Activity then throw Exception")
	@Test
	void givenInvalidActivityData_whenSaveActivity_thenThrowException() {
		
	    // When
		Throwable exception = assertThrows(Exception.class, () -> {
		    services.saveActivity(activityWrong);
		});

		// Then
		assertEquals("Não foi possível salvar atividade! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Activity.class));
	}
	
	@DisplayName("JUnit test for given Activity List when find all Activities then Return Activity list")
	@Test
	void testeGivenActivityList_WhenFindAll_thenReturnActivityList() {
	    // Given
		Team team = new Team();
		
		Project project = new Project();
		
		ActivityRequestDTO activityA = new ActivityRequestDTO(
                "Desenvolver teste unitário classe A",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
		ActivityRequestDTO activityB = new ActivityRequestDTO(
                "Desenvolver teste unitário classe B",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
		
		List<ActivityRequestDTO> requestDTOs = Arrays.asList(activityA, activityB);

		List<Activity> activities = requestDTOs.stream()
			        .map(Activity::new)
			        .collect(Collectors.toList());

	    given(repository.findAll()).willReturn(activities);
	    
	    
	    // When
	    List<ActivityResponseDTO> result = services.listAll();

	    // Then
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals("Desenvolver teste unitário classe A", result.get(0).description());
	    assertEquals("Desenvolver teste unitário classe B", result.get(1).description());
		
	}
	
	
	@DisplayName("JUnit test for given Empty Activity List when find all Activitys then Return Empty List")
	@Test
	void testeGivenEmptyActivityList_WhenFindAll_thenReturnEmptyList() throws Exception {
		// Given
		given(repository.findAll()).willReturn(Collections.emptyList());

		// When
		List<ActivityResponseDTO> result = services.listAll();

		// Then
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
	@DisplayName("JUnit test for given Activity when data is updated then Return updated Activity")
	@Test
	void testeGivenActivity_WhenUpdateActivityWithValidData_thenReturnUpdatedActivity() throws Exception {
		// Given
		Team team = new Team();
		
		Project project = new Project();
		
		ActivityRequestDTO activityNew = new ActivityRequestDTO(
                "Desenvolver teste unitário classe A",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
		
		Activity existingActivity = new Activity(
				1L,
				"Desenvolver nova funcionalidade",
				Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 22),
                project,
                team);
		
		
		given(repository.findById(existingActivity.getId())).willReturn(Optional.of(existingActivity));
		given(repository.save(any())).willReturn(existingActivity);

		// When
		Activity updatedActivity = services.updateActivity(existingActivity.getId(), activityNew);
		
		// Then
        assertNotNull(updatedActivity);
        assertEquals("Desenvolver teste unitário classe A", updatedActivity.getDescription());
        assertEquals(StatusActivity.ABERTO, updatedActivity.getStatus());
        assertEquals(LocalDate.of(2024, 6, 14), updatedActivity.getDeadLineDate());	
		
	}
	
	@DisplayName("JUnit test for given Activity wrong data then Return updated Activity")
	@Test
	void testeGivenActivity_WhenUpdateActivityWithWrongData_thenThrowException() throws Exception {
		// Given
		Team team = new Team();
		
		Project project = new Project();
		
		ActivityRequestDTO activityNew = new ActivityRequestDTO(
                "",
                Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 14),
                project,
                team
        );
		
		Activity existingActivity = new Activity(
				1L,
				"Desenvolver nova funcionalidade",
				Activity.StatusActivity.ABERTO,
                LocalDate.of(2024, 6, 22),
                project,
                team);
		
		given(repository.findById(existingActivity.getId())).willReturn(Optional.of(existingActivity));
		
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.updateActivity(existingActivity.getId(), activityNew);
		});

		// Then
		assertEquals("Não foi possível atualizar a atividade! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Activity.class));
		
	}

	@DisplayName("JUnit test for deleting an existing activity")
	@Test
	void testDeleteExistingActivity() throws Exception {
		// Given
		Long id = 1L;
		Activity existingActivity = new Activity(activity);
		existingActivity.setId(id);
		
		given(repository.findById(id)).willReturn(Optional.of(existingActivity));
		
		// When
		services.deleteById(id);
		
		// Then
		verify(repository, times(1)).deleteById(id);
	}
	
	@DisplayName("JUnit test for deleting a non-existing activity")
	@Test
	void testeDeleteNonExistingActivity() throws Exception {
		// Given
		Long id = 1L;
		
		given(repository.findById(id)).willReturn(Optional.empty());
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.deleteById(id);
		});
		
		// Then
		assertEquals("Atividade não encontrada!", exception.getMessage());
		verify(repository, never()).deleteById(anyLong());
	}

}
