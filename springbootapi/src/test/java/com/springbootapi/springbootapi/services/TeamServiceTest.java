package com.springbootapi.springbootapi.services;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springbootapi.springbootapi.models.DTO.TeamRequestDTO;
import com.springbootapi.springbootapi.models.DTO.TeamResponseDTO;
import com.springbootapi.springbootapi.models.entities.Team;
import com.springbootapi.springbootapi.models.entities.TechLeader;
import com.springbootapi.springbootapi.repositories.TeamRepository;
import com.springbootapi.springbootapi.services.ProjectService;
import com.springbootapi.springbootapi.services.TeamService;
import com.springbootapi.springbootapi.services.TechLeaderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

	@Mock
	private TeamRepository repository;

	@InjectMocks
	private TeamService services;

	@Mock
	private TechLeaderService serviceTechLeader;

	private TeamRequestDTO team;

	private TeamRequestDTO teamWrong;
	
	
	@BeforeEach
	public void setup() {
		TechLeader techLeader = new TechLeader(
	            1L,
	            "William Toloto"
	        );
		
		
		team = new TeamRequestDTO(
				"Time Back-End",
	    		5L,
	    		techLeader);
		
		teamWrong = new TeamRequestDTO(
				"",
	    		5L,
	    		techLeader);
				
		
	}
	
	@DisplayName("Given valid Team data, when save Team then Return Team JSON")
	@Test
	void givenValidTeamData_whenSaveTeam_thenReturnTeam() throws Exception {
		
		// Given
		Team teamData = new Team(team);
		given(repository.save(any(Team.class))).willReturn(teamData);

		// When
		Team result = services.saveTeam(team);

		// Then
		assertNotNull(result);
		assertEquals("Time Back-End", result.getDescription());
		assertEquals(5L, result.getTeamMembers());
	
	}
	
	@DisplayName("Given invalid Team data, when save Team then throw Exception")
	@Test
	void givenInvalidTeamData_whenSaveTeam_thenThrowException() {
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.saveTeam(teamWrong);
		});

		// Then
		assertEquals("Não foi possível salvar time! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Team.class));
	}
	
	
	@DisplayName("JUnit test for given Team List when find all Activities then Return Team list")
	@Test
	void testeGivenTeamList_WhenFindAll_thenReturnTeamList() {
		// Given
		TechLeader tLeader = new TechLeader();
		
		
		TeamRequestDTO teamA = new TeamRequestDTO(
				"Time Back-end",
				5L,
				tLeader
		);
		TeamRequestDTO teamB = new TeamRequestDTO(
				"Time front-end",
				6L,
				tLeader
		);
		
		List<TeamRequestDTO> requestDTOs = Arrays.asList(teamA, teamB);

		List<Team> activities = requestDTOs.stream()
					.map(Team::new)
					.collect(Collectors.toList());

		given(repository.findAll()).willReturn(activities);
		
		
		// When
		List<TeamResponseDTO> result = services.listAll();

		// Then
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Time Back-end", result.get(0).description());
		assertEquals("Time front-end", result.get(1).description());
		
	}
	
	@DisplayName("JUnit test for given Empty Team List when find all Teams then Return Empty List")
	@Test
	void testeGivenEmptyTeamList_WhenFindAll_thenReturnEmptyList() throws Exception {
		// Given
		given(repository.findAll()).willReturn(Collections.emptyList());

		// When
		List<TeamResponseDTO> result = services.listAll();

		// Then
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
	@DisplayName("JUnit test for given Team when data is updated then Return updated Team")
	@Test
	void testeGivenTeam_WhenUpdateTeamWithValidData_thenReturnUpdatedTeam() throws Exception {
		// Given
		TechLeader tLeader = new TechLeader();
		
		
		TeamRequestDTO teamNew = new TeamRequestDTO(
				"Time Back-end",
				5L,
				tLeader
		);
		
		Team existingTeam = new Team(
				1L,
				"Time front-end",
				6L,
				tLeader
		);
		
		
		given(repository.findById(existingTeam.getId())).willReturn(Optional.of(existingTeam));
		given(repository.save(any())).willReturn(existingTeam);

		// When
		Team updatedTeam = services.updateTeam(existingTeam.getId(), teamNew);
		
		// Then
		assertNotNull(updatedTeam);
		assertEquals("Time Back-end", updatedTeam.getDescription());
		assertEquals(5L, updatedTeam.getTeamMembers());
		
	}
	
	@DisplayName("JUnit test for given Team wrong data then Return updated Team")
	@Test
	void testeGivenTeam_WhenUpdateTeamWithWrongData_thenThrowException() throws Exception {
		// Given
		TechLeader tLeader = new TechLeader();
		
		
		TeamRequestDTO teamNew = new TeamRequestDTO(
				"",
				5L,
				tLeader
		);
		
		Team existingTeam = new Team(
				1L,
				"Time front-end",
				6L,
				tLeader
		);
		
		given(repository.findById(existingTeam.getId())).willReturn(Optional.of(existingTeam));
		
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.updateTeam(existingTeam.getId(), teamNew);
		});

		// Then
		assertEquals("Não foi possível atualizar o time! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Team.class));
		
	}
	
	@DisplayName("JUnit test for deleting an existing team")
	@Test
	void testDeleteExistingTeam() throws Exception {
		// Given
		Long id = 1L;
		Team existingTeam = new Team(team);
		existingTeam.setId(id);
		
		given(repository.findById(id)).willReturn(Optional.of(existingTeam));
		
		// When
		services.deleteById(id);
		
		// Then
		verify(repository, times(1)).deleteById(id);
	}
	
	@DisplayName("JUnit test for deleting a non-existing team")
	@Test
	void testeDeleteNonExistingTeam() throws Exception {
		// Given
		Long id = 1L;
		
		given(repository.findById(id)).willReturn(Optional.empty());
		
		// When
		Throwable exception = assertThrows(Exception.class, () -> {
			services.deleteById(id);
		});
		
		// Then
		assertEquals("Time não encontrado!", exception.getMessage());
		verify(repository, never()).deleteById(anyLong());
	}

}
