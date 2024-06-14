package com.springbootapi.springbootapi.entities;

import com.springbootapi.springbootapi.DTO.CustomerRequestDTO;

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

//Implementação da entidade Cliente

@Table(name = "customer")
@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String docId;
	private String email;
	private String cityId;
	private String postalCode;
	private String houseNumber;
	private String reference;
	private String phone;
	
	
	public Customer(CustomerRequestDTO data) {
		this.name = data.name();
		this.docId = data.docId();
		this.email = data.email();
		this.cityId = data.cityId();
		this.postalCode = data.postalCode();
		this.houseNumber = data.houseNumber();
		this.reference = data.reference();
		this.phone = data.phone();
	}
}
