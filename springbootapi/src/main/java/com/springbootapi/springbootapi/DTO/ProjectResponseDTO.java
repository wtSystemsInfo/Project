package com.springbootapi.springbootapi.DTO;

import java.time.LocalDate;


import com.springbootapi.springbootapi.entities.Project;
import com.springbootapi.springbootapi.entities.Project.Status;

public record ProjectResponseDTO(Long id, String description, LocalDate beginDate, LocalDate deliveryDate,
		Status status, CustomerResponseDTO customer) {
	public ProjectResponseDTO(Project project) {
		this(project.getId(), project.getDescription(), project.getBeginDate(), 
				project.getDeliveryDate(), project.getStatus(), 
				(project.getCustomer() != null) ? new CustomerResponseDTO(project.getCustomer()) : null);
	}
}
