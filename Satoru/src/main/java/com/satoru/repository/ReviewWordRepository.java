package com.satoru.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.ReviewWord;
import com.satoru.domain.User;

public interface ReviewWordRepository extends MongoRepository<ReviewWord, String> {
	List<ReviewWord> findByUserAndNextReviewLessThan(User user, Date date);
	Integer countByUserAndNextReviewLessThan(User user, Date date);
}
