package com.apiLaeLi.dtos;

import com.apiLaeLi.entities.Manager;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDto {
	
	private int id;
		
	private String name;
	
	private String username;
	
	private String password;
	
	private Manager manager;
	
}
