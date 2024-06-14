package com.springbootapi.springbootapi.DTO;

import com.springbootapi.springbootapi.entities.TechLeader;

public record TeamRequestDTO( String description, Long teamMembers,  TechLeader techLeader) {

}
