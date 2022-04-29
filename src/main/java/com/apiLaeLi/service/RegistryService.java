package com.apiLaeLi.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.apiLaeLi.entities.Registry;
import com.apiLaeLi.repository.RegistryRepository;

@Service
public class RegistryService {
	
	final RegistryRepository registryRepository;
		
	
	public RegistryService (RegistryRepository registryRepository) {
		this.registryRepository = registryRepository;
	}
	
	@Transactional 
	public Registry saveRegistry (Registry registry) {		
		return registryRepository.save(registry);
	}
	
	public List<Registry> findAll() {	
		return registryRepository.findAll();
	}

	public Optional<Registry> findRegistryById(Integer id) {
		return registryRepository.findById(id);
	}

	@Transactional
	public void deleteRegistry(Registry registry) {
		registryRepository.delete(registry);		
	}
	
	public List <Registry> findByemployee_id(Integer employee_id) {
		return registryRepository.findByemployee_id(employee_id);
	}
	
	public Collection <Registry> findByEmployeeIdByDay(Integer employee_id) {
		return registryRepository.findByEmployeeIdByDay(employee_id);
	}

	public Collection<Registry> findByEmployeeIdByMonth(Integer employee_id) {
		return registryRepository.findByEmployeeIdByMonth(employee_id);
	}
	//number of points
	public long findRegistriesByDay(Integer employee_id) { 
		return registryRepository.findRegistriesByDay(employee_id);
	}
}