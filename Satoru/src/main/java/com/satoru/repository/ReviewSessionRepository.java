package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.ReviewSession;
import com.satoru.domain.User;

public interface ReviewSessionRepository extends MongoRepository<ReviewSession, String> {
	ReviewSession findByUser(User user);
}
