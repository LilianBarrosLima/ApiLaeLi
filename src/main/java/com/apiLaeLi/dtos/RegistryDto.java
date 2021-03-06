package com.apiLaeLi.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.apiLaeLi.entities.Employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryDto {
	
	private Date pointRegistry; 
	
	private String justification;	
	
	@NotNull 
	private Employee employee;
}
