package com.springbootapi.springbootapi.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.entities.Project;
import com.springbootapi.springbootapi.entities.Team;
import com.springbootapi.springbootapi.entities.Activity.StatusActivity;



public record ActivityRequestDTO(String description, StatusActivity status, LocalDate deadLineDate,
		Project project, Team team) {

}
