package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.satoru.domain.User;
import com.satoru.repository.UserRepository;

@Service
public class UserService {
	@Autowired private UserRepository userRepository;
	
	public void save(User user) {
		if (user.getId() == null) {
			if (userRepository.findByEmail(user.getEmail()) != null) {
				throw new IllegalStateException("Duplicate username");
			}
		} 
		
		userRepository.save(user);
	}
	
	public void delete(User user) {
		Assert.notNull(user.getId());
		userRepository.delete(user);
	}
	
	public User getByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
}
