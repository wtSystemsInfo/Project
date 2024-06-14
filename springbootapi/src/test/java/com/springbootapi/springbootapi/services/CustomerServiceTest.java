package com.springbootapi.springbootapi.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import com.springbootapi.springbootapi.DTO.CustomerRequestDTO;
import com.springbootapi.springbootapi.DTO.CustomerResponseDTO;
import com.springbootapi.springbootapi.entities.Customer;
import com.springbootapi.springbootapi.repositories.CustomerRepository;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository repository;

	@InjectMocks
	private CustomerService services;

	private CustomerRequestDTO customer;

	private CustomerRequestDTO customerWrong;
	
	
	@BeforeEach
	public void setup() {
		
		customer = new CustomerRequestDTO(
				"João Silva",
				"48744454798",
				"joao@gmail.com",
				"123456",
				"Rua Principal",
				"Apartamento 101",
				"Próximo ao parque",
				"+1234567890"
				);
		
		customerWrong = new CustomerRequestDTO(
				 "Maria Santos",
				 "",
				 "maria@gmail.com",
				 "55584", 
				 "Rua das Flores",
				 "Casa 10",
				 "Próximo à escola",
				 "+1234567890");
		
	}
	
	
	@DisplayName("JUnit test for given Customer when save Customer then Return Customer JSON")
	@Test
	void testeGivenCustomer_WhenSaveCustomer_thenReturnCustomer() throws Exception{
	    // Given
		Customer customerData = new Customer(customer);		
		given(repository.save(any(Customer.class))).willReturn(customerData);

	    // When
	    Customer savedCustomer = services.saveCustomer(customer);

	    // Then
	    assertNotNull(savedCustomer);
	    assertEquals("João Silva", savedCustomer.getName());
	}
	
	@DisplayName("JUnit test for given Customer with invalid data when save Customer then Throw Exception")
	@Test
	void testeGivenCustomerWithInvalidData_WhenSaveCustomer_thenThrowException() throws Exception{	
		
		
		// When 
		Throwable exception = assertThrows(Exception.class, () -> {
		    services.saveCustomer(customerWrong);
		});

		// Then
		assertEquals("Não foi possível salvar cliente! Existem campos inválidos!", exception.getMessage());
		verify(repository, never()).save(any(Customer.class));
	}
	
	
	@DisplayName("JUnit test for given Customer List when find all Customers then Return Customer list")
	@Test
	void testeGivenCustomerList_WhenFindAll_thenReturnCustomerList() throws Exception {
	    // Given
	    CustomerRequestDTO joaoSilva = new CustomerRequestDTO(
	        "João Silva",
	        "48744454798",
	        "joao@gmail.com",
	        "123456",
	        "Rua Principal",
	        "Apt 101",
	        "Próximo ao parque",
	        "+1234567890"
	    );

	    CustomerRequestDTO mariaSantos = new CustomerRequestDTO(
	        "Maria Santos",
	        "55698633212",
	        "maria@gmail.com",
	        "55584", 
	        "Rua das Flores",
	        "Casa 10",
	        "Próximo à escola",
	        "+1234567890"
	    );

	    List<CustomerRequestDTO> requestDTOs = Arrays.asList(joaoSilva, mariaSantos);
	    
	    List<Customer> customers = requestDTOs.stream()
	        .map(Customer::new)
	        .collect(Collectors.toList());
	    
	    given(repository.findAll()).willReturn(customers);

	    // When
	    List<CustomerResponseDTO> result = services.listAll();

	    // Then
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    assertEquals("João Silva", result.get(0).name());
	    assertEquals("Maria Santos", result.get(1).name());
	}
	
	
	@DisplayName("JUnit test for given Empty Customer List when find all Customers then Return Empty List")
	@Test
	void testeGivenEmptyCustomerList_WhenFindAll_thenReturnEmptyList() throws Exception {
	    // Given
	    given(repository.findAll()).willReturn(Collections.emptyList());

	    // When
	    List<CustomerResponseDTO> result = services.listAll();

	    // Then
	    assertNotNull(result);
	    assertTrue(result.isEmpty());
	}
	
	@DisplayName("JUnit test for given Customer when data is updated then Return updated Customer")
	@Test
	void testeGivenCustomer_WhenUpdateCustomerWithValidData_thenReturnUpdatedCustomer() throws Exception {
	    // Given
	    Long id = 1L;
	    CustomerRequestDTO newData = customer;
	    Customer existingCustomer = new Customer();
	    existingCustomer.setId(id);
	    existingCustomer.setName("João da Silva");
	    existingCustomer.setDocId("48744454798");
	    existingCustomer.setEmail("joao123456@example.com");
	    existingCustomer.setCityId("123456");
	    existingCustomer.setPostalCode("123 Main St");
	    existingCustomer.setHouseNumber("Apt 502");
	    existingCustomer.setReference("Near the park");
	    existingCustomer.setPhone("+1234567890");
	    
	    given(repository.findById(id)).willReturn(Optional.of(existingCustomer));
	    given(repository.save(any())).willReturn(existingCustomer);
	    
	    // When
	    Customer updatedCustomer = services.updateCustomer(id, newData);
	    
	    // Then 
	    assertNotNull(updatedCustomer);
	    assertEquals(id, updatedCustomer.getId());
	    assertEquals(newData.name(), updatedCustomer.getName());
	    assertEquals(newData.docId(), updatedCustomer.getDocId());
	    assertEquals(newData.email(), updatedCustomer.getEmail());
	    assertEquals(newData.cityId(), updatedCustomer.getCityId());
	    assertEquals(newData.postalCode(), updatedCustomer.getPostalCode());
	    assertEquals(newData.houseNumber(), updatedCustomer.getHouseNumber());
	    assertEquals(newData.reference(), updatedCustomer.getReference());
	    assertEquals(newData.phone(), updatedCustomer.getPhone());
	}


	@DisplayName("JUnit test for given Customer when invalid data is set then Throw an Exception")
	@Test
	void testeGivenCustomer_WhenUpdateCustomerWithInvalidData_thenThrowException() throws Exception {
	    // Given
	    Long id = 1L;
	    CustomerRequestDTO newData = customer;
	    Customer existingCustomer = new Customer();
	    existingCustomer.setId(id);
	    existingCustomer.setName("João da Silva");
	    existingCustomer.setDocId("48744454798");
	    existingCustomer.setEmail("joao123456@example.com");
	    existingCustomer.setCityId("123456");
	    existingCustomer.setPostalCode("123 Main St");
	    existingCustomer.setHouseNumber("Apt 502");
	    existingCustomer.setReference("Near the park");
	    existingCustomer.setPhone("+1234567890");
	    
	    given(repository.findById(id)).willReturn(Optional.of(existingCustomer));
	    
	    // When
	    Throwable exception = assertThrows(Exception.class, () -> {
	        services.updateCustomer(id, customerWrong);
	    });

	    // Then
	    assertEquals("Não foi possível atualizar cliente! Existem campos inválidos!", exception.getMessage());
	    verify(repository, never()).save(any(Customer.class));
	}
	
	@DisplayName("JUnit test for deleting an existing customer")
    @Test
    void testeDeleteExistingCustomer() throws Exception {
        // Given
        Long id = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(id);
        existingCustomer.setName("João da Silva");
        
        given(repository.findById(id)).willReturn(Optional.of(existingCustomer));
        
        // When
        services.deleteById(id);
        
        // Then
        verify(repository, times(1)).deleteById(id);
    }
	
	
	@DisplayName("JUnit test for deleting a non-existing customer")
    @Test
    void testeDeleteNonExistingCustomer() throws Exception {
        // Given
        Long id = 1L;
        
        given(repository.findById(id)).willReturn(Optional.empty());
        
        // When
        Throwable exception = assertThrows(Exception.class, () -> {
            services.deleteById(id);
        });
        
        // Then
        assertEquals("Cliente não encontrado!", exception.getMessage());
        verify(repository, never()).deleteById(anyLong());
    }
	

}
