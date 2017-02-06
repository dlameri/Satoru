package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.satoru.domain.User;
import com.satoru.repository.UserRepository;

@Service
public class UserService {

	@Autowired private PasswordEncoder encoder;
	@Autowired private UserRepository userRepository;
	
	public void save(User user) {
		if (user.getId() == null) {
			if (exists(user)) {
				throw new IllegalStateException("Duplicate username");
			}
			
			// encripting password
			user.setPassword(encoder.encode(user.getPassword()));
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

	public boolean exists(User user) {
		return getByEmail(user.getEmail()) != null;
	}
	
}
