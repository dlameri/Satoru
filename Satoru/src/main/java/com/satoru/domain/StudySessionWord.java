package com.satoru.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class StudySessionWord {
	public static Integer MAX_STUDIES = 5;
	
	@DBRef
	private LessonWord lessonWord;
	
	private Integer quantity = 0;
	
	@Transient
	private String answer;
	
	public StudySessionWord() {}

	public StudySessionWord(LessonWord lessonWord) {
		this.lessonWord = lessonWord;
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
	
	public boolean isFirstTime() {
		return quantity == 0;
	}
	
	public Integer getMaxStudies() {
		return MAX_STUDIES;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean answerIsRight() {
		return lessonWord.getRomanizedWord().equals(answer);
	}

	public void increment() {
		this.quantity++;
	}
}
