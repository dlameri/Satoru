package com.satoru.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.satoru.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmail(final String email);
	List<User> findByEmailAndPassword(final String email, final String password);
	List<User> findByPasswordAndEmail(final String password, final String email);
	List<User> findByEmailLike(final String email);
	@Query("{ 'email' : ?0, 'password' : ?1 }")
	List<User> findByEmailAndPasswordQuery(final String email, final String password);
}
