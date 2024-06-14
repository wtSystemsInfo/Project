package com.springbootapi.springbootapi.entities;



import com.springbootapi.springbootapi.DTO.TeamRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author William Toloto
 */

//Implementação da entidade Time

@Table(name = "team")
@Entity(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Long teamMembers;
	
	@OneToOne
    @JoinColumn(name = "idTechLeader")
	private TechLeader techLeader;	
	
	public Team(TeamRequestDTO data) {
		this.description = data.description();
		this.teamMembers = data.teamMembers();
		this.techLeader = data.techLeader();
		
	}
}
