package com.satoru.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Audit;
import com.satoru.domain.User;

public interface AuditRepository extends MongoRepository<Audit, String> {
	Audit findByUserAndDate(User user, Date date);

	List<Audit> findByUserAndDateBetween(User loggedUser, Date start, Date end);
}
