package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.satoru.domain.Role;
import com.satoru.domain.UserAccount;
import com.satoru.repository.RoleRepository;
import com.satoru.repository.UserAccountRepository;

@Service
public class UserService {

	@Autowired private UserAccountRepository userRepository;
	
	@Autowired private RoleRepository roleRepository;
	
	public Role getRole(String role) {
		return roleRepository.findOne(role);
	}
	
	public void save(UserAccount user) {
		if (user.getId() == null) {
			if (userRepository.findByUsername(user.getUsername()) != null) {
				throw new IllegalStateException("Duplicate username");
			}
		} 
		
		userRepository.save(user);
	}
	
	public void delete(UserAccount user) {
		Assert.notNull(user.getId());
		userRepository.delete(user);
	}
	
	public UserAccount getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
}
