package com.springbootapi.springbootapi.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.entities.Customer;
import com.springbootapi.springbootapi.entities.Project.Status;

public record ProjectRequestDTO( String description, LocalDate beginDate, LocalDate deliveryDate,
		Status status, Customer customer) {

}
