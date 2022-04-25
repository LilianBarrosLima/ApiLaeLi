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

import com.apiLaeLi.dtos.UserDto;
import com.apiLaeLi.entities.User;
import com.apiLaeLi.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
	final UserService userService;
	
	public UserController(UserService userService){	
	
	this.userService = userService;
}	

//------------------------------Tela de perfil-----------(Alterar dados)------------------PUT		
	//http://localhost:8080/api/perfil/1
	@PutMapping("/perfil/{id}")
	public ResponseEntity <Object> updateUser(@PathVariable(value = "id") Integer id,
												@RequestBody @Valid UserDto userDto){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!"); 
		}		
		var user = userOptional.get();
		user.setEmail(user.getEmail());
		user.setName(user.getName());
		user.setPassword(user.getPassword());
		user.setManager(user.getManager());		
		//BeanUtils.copyProperties(userDto, userOptional);//se fosse setar todos de uma vez
		//user.setId(userOptional.get().getId()); //setar todos os atributos, para nao ser um a um
		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(user));
	}
	
//--------------------------------Tela de cadastro-----------------------------------------POST	
	@PostMapping("/cadastro")
	//http://localhost:8080/api/cadastro
	public ResponseEntity <Object> saveUser(@RequestBody @Valid UserDto userDto){
		if(userService.existsByEmail (userDto.getEmail())) { 
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");			
		}		
		var user = new User();
		BeanUtils.copyProperties(userDto, user); //converte userDto para user para salvar
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));		
	}	
		
//________________________________________CRUD______________________________________
		
//----------------------------------Ver todos users-----------------------------------------GET		
	//http://localhost:8080/api/
	@GetMapping("/")
	public ResponseEntity <List<User>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
//----------------------------------User por ID--------------------------------------------GET id	
	//http://localhost:8080/api/1
	@GetMapping("/{id}")
	public ResponseEntity <Object> findById(@PathVariable(value = "id") Integer id){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
	}
	
//----------------------------------Deletar user-----------------------------------------DELETE		
	//http://localhost:8080/api/1
	@DeleteMapping("/{id}")
	public ResponseEntity <Object> deleteUser(@PathVariable(value = "id") Integer id){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); 
		}
		userService.deleteUser(userOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("User deleted!");
	}		
}
