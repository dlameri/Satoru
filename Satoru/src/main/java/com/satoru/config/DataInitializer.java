package com.satoru.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.satoru.domain.Role;
import com.satoru.domain.User;
import com.satoru.domain.UserStatus;
import com.satoru.service.DbService;
import com.satoru.service.RoleService;
import com.satoru.service.UserService;

@Component
@Profile(value="development")
public class DataInitializer {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired private UserService userService;
	@Autowired private RoleService roleService; 
    
    @Autowired protected DbService dbService;
	
	@PostConstruct
	public void init() {
		//clear all collections, but leave indexes intact
		dbService.cleanUp();
				
		Role admin = createRole("ROLE_ADMIN");
		Role user = createRole("ROLE_USER");
		
		createUser("Dimitri","Lameri","admin@admin.com","admin", user, admin);
		createUser("Jéssica","Lameri","user@user.com","user", user);	
	}
	
	private Role createRole(String roleName) {
		Role role = new Role(roleName);		
		roleService.save(role);
		logger.info("Role created: " + roleName);
		
		return role;
	}
	
	private void createUser(String firstName, String lastName, String email, String password, Role... roles) {
		User user = new User();
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPassword(password);
		user.setRoles(Arrays.asList(roles));
		user.setEmail(email);
		user.setEnabled(true);
		user.setStatus(UserStatus.STATUS_APPROVED);
		logger.info("User created: " + email);

		userService.save(user);
	}
}
