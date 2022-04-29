package com.apiLaeLi.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiLaeLi.dtos.EmployeeDto;
import com.apiLaeLi.entities.Employee;
import com.apiLaeLi.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")//http://localhost:8080/
public class LoginController {	 	
	final EmployeeService employeeService;
	
	public LoginController(EmployeeService employeeService){	
	
	this.employeeService = employeeService;	
	}		
	
    @GetMapping
    public String welcome(){ //http://localhost:8080/
        return "Welcome to Point Control API";
    }
    @GetMapping("api/login")//http://localhost:8080/api/login
    public String users() {
        return "Screen Login"; 
    }
    
 //--------------------------------Screen Registration user----------------------------------POST	
  	@PostMapping("/api/cadastro")	//http://localhost:8080/api/cadastro
  	public ResponseEntity <Object> saveEmployee(@RequestBody @Valid EmployeeDto employeeDto){
  		if(employeeService.existsByUsername (employeeDto.getUsername())) { 
  			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");			
  		}		
  		var employee = new Employee();
  		BeanUtils.copyProperties(employeeDto, employee); 
  		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employee));		
  	}	
}