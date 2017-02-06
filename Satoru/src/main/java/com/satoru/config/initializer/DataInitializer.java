package com.satoru.config.initializer;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.satoru.domain.Role;
import com.satoru.domain.User;
import com.satoru.domain.UserStatus;
import com.satoru.service.RoleService;
import com.satoru.service.UserService;

public abstract class DataInitializer {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired private UserService userService;
	@Autowired private RoleService roleService;    
	
	@PostConstruct
	public abstract void init();
	
	protected Role createRoleIfNotExists(String roleName) {
		Role role = roleService.findRole(roleName);
		
		if (role == null) {
			role = new Role(roleName);
			roleService.save(role);
			logger.info("Role created: " + roleName);
		}
		
		return role;
	}
	
	protected void createUserIfNotExists(String firstName, String lastName, String email, String password, Role... roles) {
		User user = new User();
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPassword(password);
		user.setRoles(Arrays.asList(roles));
		user.setEmail(email);
		user.setEnabled(true);
		user.setStatus(UserStatus.STATUS_APPROVED);
		
		if (! userService.exists(user)) {
			logger.info("User created: " + email);
			userService.save(user);
		}
	}
}