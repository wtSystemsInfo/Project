package com.springbootapi.springbootapi.models.DTO;

import java.time.LocalDate;

import com.springbootapi.springbootapi.models.entities.Customer;
import com.springbootapi.springbootapi.models.entities.Project.Status;

public record ProjectRequestDTO( String description, LocalDate beginDate, LocalDate deliveryDate,
		Status status, Customer customer) {

}
