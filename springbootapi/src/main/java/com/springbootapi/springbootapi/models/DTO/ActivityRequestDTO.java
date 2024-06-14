package com.springbootapi.springbootapi.models.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.models.entities.Project;
import com.springbootapi.springbootapi.models.entities.Team;
import com.springbootapi.springbootapi.models.entities.Activity.StatusActivity;



public record ActivityRequestDTO(String description, StatusActivity status, LocalDate deadLineDate,
		Project project, Team team) {

}
