package com.satoru.service;

import org.springframework.stereotype.Service;

import com.satoru.domain.Role;
import com.satoru.repository.RoleRepository;

@Service
public class RoleService extends GenericService<Role, String, RoleRepository> {	
}
