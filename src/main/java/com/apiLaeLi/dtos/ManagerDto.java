package com.apiLaeLi.dtos;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDto {
	
	@NotNull	
	private String manager_name;

}
