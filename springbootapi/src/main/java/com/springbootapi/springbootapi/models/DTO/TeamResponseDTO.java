package com.springbootapi.springbootapi.models.DTO;

import com.springbootapi.springbootapi.models.entities.Team;


/*public record TeamResponseDTO(Long id, String description, Long teamMembers, TechLeader techLeader) {
	public TeamResponseDTO(Team team) {
		this(team.getId(), team.getDescription(), team.getTeamMembers(), team.getTechLeader());
	}
}*/

public record TeamResponseDTO(Long id, String description, Long teamMembers, TechLeaderResponseDTO techLeader) {
    public TeamResponseDTO(Team team) {
        this(team.getId(), team.getDescription(), team.getTeamMembers(),
        		(team.getTechLeader() != null) ? new TechLeaderResponseDTO(team.getTechLeader()) : null);
    }
}