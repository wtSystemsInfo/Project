package com.springbootapi.springbootapi.models.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.models.entities.Activity;
import com.springbootapi.springbootapi.models.entities.Activity.StatusActivity;


public record ActivityResponseDTO(Long id, String description, StatusActivity status, LocalDate deadLineDate,
		ProjectResponseDTO project, TeamResponseDTO team) {
		public ActivityResponseDTO(Activity activity) {
			this(activity.getId(), activity.getDescription(), activity.getStatus(), activity.getDeadLineDate(),
					(activity.getProject() != null) ? new ProjectResponseDTO(activity.getProject()) : null,
					(activity.getTeam() != null) ? new TeamResponseDTO(activity.getTeam()) : null);
		}
}
