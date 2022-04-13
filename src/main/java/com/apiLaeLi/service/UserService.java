package com.apiLaeLi.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.apiLaeLi.entities.User;
import com.apiLaeLi.repository.UserRepository;

@Service
public class UserService {
	
	//injeção
	final UserRepository userRepository;
	
	public UserService (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional //se algo dar errado garante o rollback e td volta ao normal
	public User saveUser (User user) {		
		return userRepository.save(user);
	}

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public List<User> findAll() {	
		return userRepository.findAll();
	}

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);		
	}

}