package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Lesson;

public interface LessonRepository extends MongoRepository<Lesson, String> {
}
