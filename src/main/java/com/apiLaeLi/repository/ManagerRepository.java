package com.apiLaeLi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiLaeLi.entities.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	
	
}
