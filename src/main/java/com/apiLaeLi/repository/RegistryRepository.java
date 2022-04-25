package com.apiLaeLi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLaeLi.entities.Registry;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Integer> {	
		
	@Query(value = "select * from registry where user_id = :user_id and convert(point_registry, date) = convert(sysdate(), date)", nativeQuery = true)
	Collection<Registry> findByUserIdByDay(Integer user_id );
	
	@Query(value = "select * from registry where user_id = ?1 and year(point_registry) = year(sysdate()) and month(point_registry) = month(sysdate())", nativeQuery = true)
	Collection<Registry> findByUserIdByMonth(@Param("user_id")Integer user_id );
	
	List <Registry> findByuser_id(Integer user_id);
	
	@Query(value = "select COUNT(*) from registry where convert(point_registry, date) = convert(sysdate(), date)", nativeQuery = true)
	long findRegistriesByDay(Integer user_id);	
	 
}
