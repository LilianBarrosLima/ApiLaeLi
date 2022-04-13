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
	@PutMapping("/perfil/{id}")
	public ResponseEntity <Object> updateUser(@PathVariable(value = "id") Integer id,
												@RequestBody @Valid UserDto userDto){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User nao encontrado"); 
		}		
		var user = new User();	
		BeanUtils.copyProperties(userDto, userOptional);
		user.setId(userOptional.get().getId()); //setar todos os atributos, poderia ser um a um user.setNome(userDto.getnome());
		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(user));
	}
	
//--------------------------------Tela de cadastro-----------------------------------------POST	
	@PostMapping("/cadastro")
	public ResponseEntity <Object> saveUser(@RequestBody @Valid UserDto userDto){
		if(userService.existsByEmail (userDto.getEmail())) { //seria mais facil colocar uma unique mas fiz p criar a regra
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Confito: Email ja existe!");			
		}		
		var user = new User();
		BeanUtils.copyProperties(userDto, user); //converte userDto para user para salvar
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));		
	}	
		
//________________________________________CRUD______________________________________
		
//----------------------------------Ver todos users-----------------------------------------GET		
	@GetMapping("/")
	public ResponseEntity <List<User>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
//----------------------------------User por ID--------------------------------------------GET id	
	@GetMapping("/{id}")
	public ResponseEntity <Object> findById(@PathVariable(value = "id") Integer id){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User nao encontrado"); 
		}
		return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
	}
	
//----------------------------------Deletar user-----------------------------------------DELETE		
	@DeleteMapping("/{id}")
	public ResponseEntity <Object> deleteUser(@PathVariable(value = "id") Integer id){
		Optional<User> userOptional = userService.findById(id);
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User nao encontrado"); 
		}
		userService.deleteUser(userOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("User deletado!");
	}		
}
