package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.ReviewWord;

public interface ReviewWordRepository extends MongoRepository<ReviewWord, String> {
}
