package com.satoru.config.initializer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.satoru.domain.Role;

@Component
@Profile(value="production")
public class ProductionDataInitializer extends DataInitializer{

	@Override
	public void init() {
		Role admin = createRoleIfNotExists("ROLE_ADMIN");
		Role user = createRoleIfNotExists("ROLE_USER");
		
		createUserIfNotExists("Dimitri","Lameri","dimitrilameri@gmail.com","admin", user, admin);
		
		createCourseIfNotExist("Hiragana", "Curso de Hiragana");
		createCourseIfNotExist("Katakana", "Curso de Katakana");
	}

}
