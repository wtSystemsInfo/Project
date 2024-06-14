package com.springbootapi.springbootapi.models.entities;

import java.time.LocalDate;

import com.springbootapi.springbootapi.models.DTO.ProjectRequestDTO;

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

//Implementação da entidade Projeto 

@Table(name = "project")
@Entity(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Project {
	
	public enum Status{
		ABERTO,
		FECHADO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private LocalDate beginDate;
	private LocalDate deliveryDate;
	private Status status; 
	
	@OneToOne
    @JoinColumn(name = "idCustomer")
	private Customer customer;
	
	
	public Project(ProjectRequestDTO data) {
		this.description = data.description();
		this.beginDate = data.beginDate();
		this.deliveryDate = data.deliveryDate();
		this.status = data.status();
		this.customer = data.customer();
	}
}
