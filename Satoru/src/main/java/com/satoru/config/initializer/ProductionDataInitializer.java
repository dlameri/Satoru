package com.satoru.config.initializer;

import java.util.Arrays;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.satoru.domain.Course;
import com.satoru.domain.LessonWord;
import com.satoru.domain.Role;

@Component
@Profile(value="production")
public class ProductionDataInitializer extends DataInitializer{

	@Override
	public void init() {
		Role admin = createRoleIfNotExists("ROLE_ADMIN");
		Role user = createRoleIfNotExists("ROLE_USER");
		
		createUserIfNotExists("Dimitri","Lameri","dimitrilameri@gmail.com","admin", user, admin);		
		
		Course hiragana = createCourseIfNotExist("Hiragana", "Curso de Hiragana");		
		
		createLessonIfNotExist(hiragana, 1, "Quem sou eu?", "Pronomes pessoais", Arrays.asList(
				new LessonWord("わたし", "wa-ta-shi", "Eu (formal masculino, normal feminino)"),
				new LessonWord("ぼく", "bo-ku", "Eu (normal masculino)"),
				new LessonWord("かれ", "ka-re", "Ele"),
				new LessonWord("かのじょ", "ka-no-jyo", "Ela"),
				new LessonWord("あなた", "a-na-ta", "Você")
		));
		
		createLessonIfNotExist(hiragana, 2, "Obrigado e Tchau", "Palavras simples", Arrays.asList(				
				new LessonWord("ありがとう", "a-ri-ga-to-u", "Obrigado"),
				new LessonWord("こんにちは", "ko-n-ni-chi-wa", "Hello"),
				new LessonWord("さよなら", "sa-yo-na-ra", "Hello")
		));
	}

}
