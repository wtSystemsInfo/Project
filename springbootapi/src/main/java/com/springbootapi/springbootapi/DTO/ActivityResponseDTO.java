package com.springbootapi.springbootapi.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.entities.Activity;
import com.springbootapi.springbootapi.entities.Activity.StatusActivity;


public record ActivityResponseDTO(Long id, String description, StatusActivity status, LocalDate deadLineDate,
		ProjectResponseDTO project, TeamResponseDTO team) {
		public ActivityResponseDTO(Activity activity) {
			this(activity.getId(), activity.getDescription(), activity.getStatus(), activity.getDeadLineDate(),
					(activity.getProject() != null) ? new ProjectResponseDTO(activity.getProject()) : null,
					(activity.getTeam() != null) ? new TeamResponseDTO(activity.getTeam()) : null);
		}
}
