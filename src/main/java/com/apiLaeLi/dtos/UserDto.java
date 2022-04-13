package com.apiLaeLi.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.apiLaeLi.entities.Manager;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
		
	@NotNull
	private String name;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private Manager manager;
	
	//List<Registry> registry;

}
