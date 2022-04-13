package com.apiLaeLi.dtos;

import java.util.Date;

import com.apiLaeLi.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryDto {
	
	private Date pointRegistry;
	
	private String justification;	
	
	private User user;

}
