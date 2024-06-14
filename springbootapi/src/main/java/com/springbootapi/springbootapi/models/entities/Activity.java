package com.springbootapi.springbootapi.models.entities;

import java.time.LocalDate;

import com.springbootapi.springbootapi.models.DTO.ActivityRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

//Implementação da entidade Atividade

@Table(name = "activity")
@Entity(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Activity {
	
	public enum StatusActivity{
		ABERTO,
		PROGRESSO,
		FINALIZADO
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private StatusActivity status;
	private LocalDate deadLineDate;
	
	
	@ManyToOne
    @JoinColumn(name = "idProject")
	private Project project;
	
	
	@OneToOne
	@JoinColumn(name = "idTeam")
	private Team team;
	
	public Activity(ActivityRequestDTO data) {
		this.description = data.description();
		this.status = data.status();
		this.deadLineDate = data.deadLineDate();
		this.project = data.project();
		this.team = data.team();
	}
	
}
