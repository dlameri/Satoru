package com.satoru.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.satoru.domain.User;

public class GenericController {
	protected User getLoggedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
