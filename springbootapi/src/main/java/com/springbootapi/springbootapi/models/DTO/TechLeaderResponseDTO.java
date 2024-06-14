package com.springbootapi.springbootapi.models.DTO;

import com.springbootapi.springbootapi.models.entities.TechLeader;

/**
* @author William Toloto
*/


public record TechLeaderResponseDTO(Long id,  String name) {
	public TechLeaderResponseDTO(TechLeader tLeader) {
		this(tLeader.getId(), tLeader.getName());
	}
}
