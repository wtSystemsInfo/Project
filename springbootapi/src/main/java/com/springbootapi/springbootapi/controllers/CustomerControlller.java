package com.springbootapi.springbootapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootapi.springbootapi.DTO.CustomerRequestDTO;
import com.springbootapi.springbootapi.DTO.CustomerResponseDTO;
import com.springbootapi.springbootapi.entities.Customer;
import com.springbootapi.springbootapi.services.CustomerService;

/**
 * @author William Toloto
 */
 
@RestController
@RequestMapping("/customer")
public class CustomerControlller {
	
	@Autowired
	private CustomerService service;
	
	@PostMapping
	public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerRequestDTO customer) throws Exception{
		
		Customer newCustomer = service.saveCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerResponseDTO>> getAllCustomer(){
		List<CustomerResponseDTO> listCustomer = service.listAll();
		return new ResponseEntity<>(listCustomer, HttpStatus.OK);
	}
	
	@GetMapping("/CustomerById") 
	public ResponseEntity<Customer> getCustomerById(@RequestParam("id") Long idCustomer) throws Exception{
		Customer customer = service.findCustomerById(idCustomer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping("/CustomerByName") 
	public ResponseEntity<List<CustomerResponseDTO>> getCustomerByName(@RequestParam("name") String nameCustomer){
		List<CustomerResponseDTO> listCustomer  = service.findCustomerByName(nameCustomer);
		return new ResponseEntity<>(listCustomer, HttpStatus.OK);
	}
	
	@GetMapping("/CustomerByDoc") 
	public ResponseEntity<Customer> getCustomerByDoc(@RequestParam("doc") String docCustomer) throws Exception{
		Customer customer = service.findCustomerByDoc(docCustomer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customer) throws Exception{
		Customer updatedLeader = service.updateCustomer(id, customer);
		return new ResponseEntity<>(updatedLeader, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws Exception{
	    service.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
}
