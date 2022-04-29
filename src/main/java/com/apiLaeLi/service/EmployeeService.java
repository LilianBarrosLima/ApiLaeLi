package com.apiLaeLi.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.apiLaeLi.entities.Employee;
import com.apiLaeLi.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	final EmployeeRepository employeeRepository;
	
	public EmployeeService (EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Transactional 
	public Employee saveEmployee (Employee employee) {		
		return employeeRepository.save(employee);
	}

	public boolean existsByUsername(String username) {
		return employeeRepository.existsByUsername(username);
	}

	public List<Employee> findAll() {	
		return employeeRepository.findAll();
	}

	public Optional<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}
	
	@Transactional
	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);		
	}
	
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	public Employee findByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}		

}