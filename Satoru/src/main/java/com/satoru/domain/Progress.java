package com.satoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Progress {
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Course course;
	
	private Integer currentOrder;
	
	public Progress() {}
	
	public Progress(User user, Course course) {
		this.user = user;
		this.course = course;
		this.currentOrder = 1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Integer currentOrder) {
		this.currentOrder = currentOrder;
	}
	
	public void increment(Lesson lesson) {
		if (lesson.getOrder() == currentOrder) {
			this.currentOrder++;
		}
	}
}
