package com.satoru.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.satoru.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
}
