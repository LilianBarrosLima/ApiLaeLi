package com.apiLaeLi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiLaeLi.dtos.RegistryDto;
import com.apiLaeLi.entities.Registry;
import com.apiLaeLi.service.RegistryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/registros")
public class RegistryController {
	final RegistryService registryService;
	
	public RegistryController(RegistryService registryService){	
	
	this.registryService = registryService;
}
	
//----------------------------------Ver todos registros-----------------------------------------GET		
	@GetMapping("/")
	public ResponseEntity <List<Registry>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(registryService.findAll());
	}
//----------------------------------Ver registros por id--------------------------------------------GET id	
	@GetMapping("/{id}")
	public ResponseEntity <Object> findRegistryById(@PathVariable(value = "id") Integer id){
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(registryOptional.get());
	}
//----------------------------------Deletar registros-----------------------------------------DELETE		
	@DeleteMapping("/{id}")
	public ResponseEntity <Object> deleteRegistry(@PathVariable(value = "id") Integer id){
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}
		registryService.deleteRegistry(registryOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Registry deleted!");
	}
//----------------------------------Editar registros-----------------------------------------PUT		
	@PutMapping("/{id}")
	public ResponseEntity <Object> updateRegistry(@PathVariable(value = "id") Integer id,
												@RequestBody @Valid RegistryDto registryDto){
		
		Optional<Registry> registryOptional = registryService.findRegistryById(id);
		if (!registryOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registry not found!"); 
		}		
		var registry = new Registry(); 	
		BeanUtils.copyProperties(registryDto, registryOptional);
		registry.setId(registryOptional.get().getId());
		return ResponseEntity.status(HttpStatus.OK).body(registryService.saveRegistry(registry));
	}
	
//----------------------------------Incluir registros-----------------------------------------POST	
	@PostMapping("/")
	public ResponseEntity <Object> saveRegistry(@RequestBody @Valid RegistryDto registryDto){		
		var registry = new Registry();
		BeanUtils.copyProperties(registryDto, registry); //converte registryDto para registry para salvar
		return ResponseEntity.status(HttpStatus.CREATED).body(registryService.saveRegistry(registry));		
	}		
		
}
