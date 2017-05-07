package com.satoru.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Course;
import com.satoru.domain.Lesson;

public interface LessonRepository extends MongoRepository<Lesson, String> {
	Lesson findByNameAndCourse(final String name, final Course course);
	List<Lesson> findByCourse(Course course);
}
