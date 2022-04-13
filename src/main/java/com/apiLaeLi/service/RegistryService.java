package com.apiLaeLi.service;

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
	
	@Transactional //se algo dar errado garante o rollback e td volta ao normal
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

	public Object findAllPorUser() {
		return registryRepository.findAll();
	}

}