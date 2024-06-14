package com.springbootapi.springbootapi.models.DTO;

import com.springbootapi.springbootapi.models.entities.Customer;

/**
* @author William Toloto
*/


public record CustomerResponseDTO(Long id,  String name, String docId, String email, String cityId, String postalCode,
		String houseNumber, String reference, String phone) {

	public CustomerResponseDTO(Customer customer) {
		this(customer.getId(), customer.getName(), customer.getDocId(), customer.getEmail(), 
				customer.getCityId(), customer.getPostalCode(), customer.getHouseNumber(), 
				customer.getReference(), customer.getPhone());
	}
		
}
