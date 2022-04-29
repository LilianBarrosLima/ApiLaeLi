package com.apiLaeLi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiLaeLi.dtos.EmployeeDto;
import com.apiLaeLi.entities.Employee;
import com.apiLaeLi.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EmployeeController {
	final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService){	
	
	this.employeeService = employeeService;
}	

//------------------------------Profile Screen-----------(Update employee)------------------PUT		
	//http://localhost:8080/api/perfil/1
	@PutMapping("/perfil/{id}")
	public ResponseEntity <Object> updateEmployee(@PathVariable(value = "id") Integer id,
												@RequestBody @Valid EmployeeDto employeeDto){
		Optional<Employee> employeeOptional = employeeService.findById(id);
		if (!employeeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found!"); 
		}		
		var employee = employeeOptional.get();
		employee.setName(employeeDto.getName());
		employee.setUsername(employeeDto.getUsername());
		employee.setPassword(employeeDto.getPassword());
		employee.setManager(employeeDto.getManager());			
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.saveEmployee(employee));
	}			
//----------------------------------All employees-----------------------------------------GET		
	//http://localhost:8080/api/
	@GetMapping("/")
	public ResponseEntity <List<Employee>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
	}
//----------------------------------Employee by ID-------------------------------------GET id	
	//http://localhost:8080/api/1
	@GetMapping("/{id}")
	public ResponseEntity <Object> findById(@PathVariable(value = "id") Integer id){
		Optional<Employee> employeeOptional = employeeService.findById(id);
		if (!employeeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(employeeOptional.get());
	}	
//----------------------------------Delete employee-------------------------------------DELETE		
	//http://localhost:8080/api/1
	@DeleteMapping("/{id}")
	public ResponseEntity <Object> deleteEmployee(@PathVariable(value = "id") Integer id){
		Optional<Employee> employeeOptional = employeeService.findById(id);
		if (!employeeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found"); 
		}
		employeeService.deleteEmployee(employeeOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Employee deleted!");
	}		
}
