package com.springbootapi.springbootapi.services;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springbootapi.springbootapi.DTO.CustomerRequestDTO;
import com.springbootapi.springbootapi.DTO.CustomerResponseDTO;
import com.springbootapi.springbootapi.entities.Customer;
import com.springbootapi.springbootapi.repositories.CustomerRepository;

/**
 * @author William Toloto
 */
 
@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repository;
	private Logger logger = Logger.getLogger(CustomerService.class.getName());
	
	public Customer saveCustomer(CustomerRequestDTO data) throws Exception{ 
		Customer customerData = new Customer(data);
		if(findCustomerByDoc(data.docId()) != null) {
			throw new Exception("já existe um cliente salvo com esse documento! Cliente não foi salvo!");
		}
		if(!validateCustomer(customerData)) {
			throw new Exception("Não foi possível salvar cliente! Existem campos inválidos!");
		}else {
			repository.save(customerData);
			logger.log(Level.INFO, "Cliente salvo com sucesso!");
			return customerData;			
		}
	}
	
	public List<CustomerResponseDTO> listAll(){
		 List<CustomerResponseDTO> customerList = repository.findAll().stream().map(CustomerResponseDTO::new).toList();
		 return customerList;
	}
	
	public Customer findCustomerById(Long id) throws Exception {
		Customer customer= repository.findById(id).orElseThrow(( )-> new Exception("Cliente não encontrado!"));
		logger.log(Level.INFO, "Cliente id : " + id + " localizado com sucesso!");
		return customer;
	}
	
	public Customer findCustomerByDoc(String doc){
		Optional<Customer> optionalCustomer = repository.findCustomerByDoc(doc);
	    if (optionalCustomer.isPresent()) {
	        Customer customer = optionalCustomer.get();
	        logger.log(Level.INFO, "Cliente portador do documento : " + doc + " localizado com sucesso!");
	        return customer;
	    } else {
	    	logger.log(Level.INFO, "Cliente portador do documento : " + doc + " não foi localizado!");
	        return null;
	    }
	}
	
	public List<CustomerResponseDTO> findCustomerByName(String name){
		List<CustomerResponseDTO> customerList = repository.findCustomerByName(name).stream().map(CustomerResponseDTO::new).toList();
		logger.log(Level.INFO, "Gerado lista de clientes contendo o nome :" + name + ", com sucesso!");
		return customerList;
	}
	
	
	public Customer updateCustomer(Long id, CustomerRequestDTO data) throws Exception{ 
		Customer customerData = findCustomerById(id);
		customerData.setName(data.name());
		customerData.setDocId(data.docId());
		customerData.setEmail(data.email());
		customerData.setCityId(data.cityId());
		customerData.setPostalCode(data.postalCode());
		customerData.setHouseNumber(data.houseNumber());
		customerData.setReference(data.reference());
		customerData.setPhone(data.phone());
		
		if(!validateCustomer(customerData)) {
			throw new Exception("Não foi possível atualizar cliente! Existem campos inválidos!");
		}else {
			repository.save(customerData);
			logger.log(Level.INFO, "Cliente atualizado com sucesso!");
			return customerData;
		}
	}
	
	public void deleteById(Long id) throws Exception {
		Customer customerData = findCustomerById(id);
		if(customerData != null) {
			repository.deleteById(id);
			logger.log(Level.INFO, "Cliente deletado com sucesso!");
		}
		
	}
	
	//validar campos obrigatórios
	public boolean validateCustomer(Customer customer) {
		if(customer.getName().isEmpty() || customer.getName().isBlank()) {
			return false;
		}
		
		if(customer.getEmail().isEmpty() || customer.getEmail().isBlank()) {
			return false;
		}
		
		if(customer.getPhone().isEmpty() || customer.getPhone().isBlank()) {
			return false;
		}
		
		if(customer.getDocId().isEmpty() || customer.getDocId().isBlank()) {
			return false;
		}else{
			if(!validateDoc(customer.getDocId())) {
				return false;
			}
		}
		return true;
	}
	
	
	//validação do documento
	public boolean validateDoc(String doc) {
		doc = doc.replaceAll("[^0-9]", "");
		
		if(doc.length()<11){
			return false;
		}
		
		if(doc.length()==11) {
			if(doc.matches("(\\d)\\1{10}")) {
				return false;
			}else {
				return true;
			}
		}
		
		if(doc.length()!=14) {
			return false;
		}else {
			if(doc.matches("(\\d)\\1{13}")) {
				return false;
			}else {
				return true;
			}
		}		
	}
	
	
}
