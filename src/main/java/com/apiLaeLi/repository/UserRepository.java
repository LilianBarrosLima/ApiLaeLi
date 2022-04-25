package com.apiLaeLi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiLaeLi.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByEmail (String email);	
	User findByEmail(String email);
		
}
