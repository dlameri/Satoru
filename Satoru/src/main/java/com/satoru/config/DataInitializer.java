package com.satoru.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.satoru.domain.Role;
import com.satoru.domain.UserAccount;
import com.satoru.domain.UserAccountStatus;
import com.satoru.service.DbService;
import com.satoru.service.UserService;

@Component
@Profile(value="development")
public class DataInitializer {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired private MongoOperations operations;
	
	@Autowired private UserService userService;
	
    @Autowired private PasswordEncoder encoder; 
    
    @Autowired protected DbService dbService;
	
	@PostConstruct
	public void init() {
		//clear all collections, but leave indexes intact
		dbService.cleanUp();
				
		Role admin = createRole("ROLE_ADMIN");
		Role user = createRole("ROLE_USER");
		
		createUser("Dimitri","Lameri","admin","admin", user, admin);
		createUser("Jéssica","Lameri","user","user", user);	
	}
	
	private Role createRole(String roleName) {
		Role role = new Role(roleName);		
		operations.insert(role, "role");
		logger.info("Role created :" + roleName);
		
		return role;
	}
	
	private void createUser(String firstName, String lastName, String userName, String password, Role... roles) {
		UserAccount user = new UserAccount();
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPassword(encoder.encode(password));
		user.setRoles(Arrays.asList(roles));
		user.setUsername(userName);
		user.setEnabled(true);
		user.setStatus(UserAccountStatus.STATUS_APPROVED);
		logger.info("User created :" + userName);

		userService.save(user);
	}
}
