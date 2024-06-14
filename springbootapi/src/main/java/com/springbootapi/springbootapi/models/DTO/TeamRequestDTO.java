package com.springbootapi.springbootapi.models.DTO;

import com.springbootapi.springbootapi.models.entities.TechLeader;

public record TeamRequestDTO( String description, Long teamMembers,  TechLeader techLeader) {

}
