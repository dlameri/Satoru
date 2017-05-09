package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Course;
import com.satoru.domain.Progress;
import com.satoru.domain.User;

public interface ProgressRepository extends MongoRepository<Progress, String> {
	Progress findByUserAndCourse(User user, Course course);
}
