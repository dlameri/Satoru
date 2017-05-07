package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Lesson;
import com.satoru.domain.StudySession;
import com.satoru.domain.User;

public interface StudySessionRepository extends MongoRepository<StudySession, String> {
	StudySession findByUserAndLesson(User user, Lesson lesson);
}
