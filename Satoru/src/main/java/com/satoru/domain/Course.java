package com.satoru.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Course {
	@Id
	private String id;
	
	@Indexed(unique=true, direction=IndexDirection.DESCENDING, dropDups=true)
	private String name;
	
	private String description;
	
	@DBRef
	private List<Lesson> lessons;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		if (id != null && "".equals(id.trim())) {
			return null;
		}

		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
}
