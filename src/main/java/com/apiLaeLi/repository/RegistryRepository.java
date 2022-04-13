package com.apiLaeLi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiLaeLi.entities.Registry;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Integer> {	
	
}
