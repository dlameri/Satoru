package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.satoru.domain.User;
import com.satoru.repository.UserRepository;

@Service
public class UserService extends GenericService<User, String, UserRepository>{

	@Autowired @Lazy private PasswordEncoder encoder;
	
	public User save(User user) {
		if (user.getId() == null) {
			if (exists(user)) {
				throw new IllegalStateException("Duplicate username");
			}
			
			// encripting password
			user.setPassword(encriptPassword(user.getPassword()));
		}
		
		return super.save(user);
	}

	public String encriptPassword(String password) {
		return encoder.encode(password);
	}
	
	public User getByEmail(String email) {
		return getRepository().findByEmail(email);
	}

	public boolean exists(User user) {
		return getByEmail(user.getEmail()) != null;
	}
}
