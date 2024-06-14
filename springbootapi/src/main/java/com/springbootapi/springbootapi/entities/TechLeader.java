package com.springbootapi.springbootapi.entities;

import com.springbootapi.springbootapi.DTO.TechLeaderRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


@Table(name = "techleader")
@Entity(name = "techleader")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TechLeader {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	
	public TechLeader(TechLeaderRequestDTO data) {
		this.name = data.name();
	}
	
}
