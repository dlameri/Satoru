package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satoru.domain.Role;
import com.satoru.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired private RoleRepository roleRepository;
	
	public Role findRole(String role) {
		return roleRepository.findOne(role);
	}
	
	public void save(Role role) {
		roleRepository.save(role);
	}
}
