package com.satoru.domain;

import java.util.ArrayList;
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
	
	private Integer order;
	
	@DBRef
	private List<LessonWord> lessonWords = new ArrayList<>();
	
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void clearLessonWords() {
		this.lessonWords.clear();
	}

	public void addLessonWord(LessonWord lessonWord) {
		this.lessonWords.add(lessonWord);
	}
}
