package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.LessonWord;

public interface LessonWordRepository extends MongoRepository<LessonWord, String> {
	LessonWord findByWord(final String word);
}
