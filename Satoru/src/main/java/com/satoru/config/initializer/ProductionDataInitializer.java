package com.satoru.config.initializer;

import com.satoru.domain.Role;

public class ProductionDataInitializer extends DataInitializer{

	@Override
	public void init() {
		Role admin = createRoleIfNotExists("ROLE_ADMIN");
		Role user = createRoleIfNotExists("ROLE_USER");
		
		createUserIfNotExists("Dimitri","Lameri","dimitrilameri@gmail.com","admin", user, admin);
	}

}
