package com.satoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StudySessionWord {
	private static Integer MAX_STUDIES = 3;
	@Id
	private String id;
	
	@DBRef
	private LessonWord lessonWord;
	
	private Integer quantity = 0;
	
	

	public StudySessionWord(LessonWord lessonWord) {
		this.lessonWord = lessonWord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LessonWord getLessonWord() {
		return lessonWord;
	}

	public void setLessonWord(LessonWord lessonWord) {
		this.lessonWord = lessonWord;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public boolean hasntFinished() {
		return quantity < MAX_STUDIES;
	}
	
	public boolean isLastSession() {
		return quantity == MAX_STUDIES - 1;
	}
}
