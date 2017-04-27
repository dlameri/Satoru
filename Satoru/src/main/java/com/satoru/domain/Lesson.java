package com.satoru.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Lesson {
	@Id
	private String id;

	private String name;
	
	private String description;
	
	private List<LessonWord> lessonWords;
	
	@DBRef
	private Course course;

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

	public List<LessonWord> getLessonWords() {
		return lessonWords;
	}

	public void setLessonWords(List<LessonWord> lessonWords) {
		this.lessonWords = lessonWords;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
