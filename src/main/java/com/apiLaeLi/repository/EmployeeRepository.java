package com.apiLaeLi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiLaeLi.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	boolean existsByUsername(String username);
	
	Employee findByUsername(String username);
		
}
