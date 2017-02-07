package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Course;

public interface CourseRepository extends MongoRepository<Course, String> {
	

}
