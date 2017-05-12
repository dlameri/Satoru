package com.satoru.config.initializer;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.satoru.domain.Course;
import com.satoru.domain.Role;

@Component
@Profile(value="development")
public class DevelopmentDataInitializer extends DataInitializer{
	@Autowired private MongoOperations operations;

	@Override
	public void init() {
		//clear all collections, but leave indexes intact
		//cleanUp();

		Role admin = createRoleIfNotExists("ROLE_ADMIN");
		Role user = createRoleIfNotExists("ROLE_USER");

		createUserIfNotExists("Dimitri","Lameri","admin@admin.com","admin", user, admin);
		createUserIfNotExists("Jéssica","Lameri","user@user.com","user", user);

		Course hiragana = createCourseIfNotExist("Hiragana", "Curso de Hiragana");		
				
		createLessonIfNotExist(hiragana, 1, "Quem sou eu?", "Pronomes pessoais", Arrays.asList(
			createLessonWordIfNotExist("わたし", "wa-ta-shi", "Eu (formal masculino, normal feminino)"),
			createLessonWordIfNotExist("ぼく", "bo-ku", "Eu (normal masculino)"),
			createLessonWordIfNotExist("かれ", "ka-re", "Ele"),
			createLessonWordIfNotExist("かのじょ", "ka-no-jyo", "Ela"),
			createLessonWordIfNotExist("あなた", "a-na-ta", "Você")
		));
		
		createLessonIfNotExist(hiragana, 2, "Obrigado e Tchau", "Palavras simples", Arrays.asList(				
			createLessonWordIfNotExist("ありがとう", "a-ri-ga-to-u", "Obrigado"),
			createLessonWordIfNotExist("こんにちは", "ko-n-ni-chi-wa", "Hello"),
			createLessonWordIfNotExist("さよなら", "sa-yo-na-ra", "Hello")
		));
	}
	
	private void cleanUp() {
		for (String collectionName : operations.getCollectionNames()) {
			if (!collectionName.startsWith("system")) {
				operations.execute(collectionName, new CollectionCallback<Void>() {
					@Override
					public Void doInCollection(DBCollection collection) throws MongoException, DataAccessException {
						collection.remove(new BasicDBObject());
						Assert.isTrue(!collection.find().hasNext());
						return null;
					}
				});
			}
		}
	}
}
