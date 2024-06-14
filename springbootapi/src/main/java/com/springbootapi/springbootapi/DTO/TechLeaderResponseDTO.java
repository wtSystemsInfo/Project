package com.springbootapi.springbootapi.DTO;

import com.springbootapi.springbootapi.entities.TechLeader;

/**
* @author William Toloto
*/


public record TechLeaderResponseDTO(Long id,  String name) {
	public TechLeaderResponseDTO(TechLeader tLeader) {
		this(tLeader.getId(), tLeader.getName());
	}
}
