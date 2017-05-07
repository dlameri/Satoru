package com.satoru.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StudySession {
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Lesson lesson;
	
	private List<StudySessionWord> studied = new ArrayList<>();

	public StudySession(User user, Lesson lesson) {
		this.user = user;
		this.lesson = lesson;
		initializeStudy(lesson);
	}
	
	public Boolean isLast() {			
		return studied
			.stream()
			.filter(StudySessionWord::hasntFinished)
			.filter(StudySessionWord::isLastSession)
			.count() == 1;
	}
	
	public StudySessionWord getNextWord() {
		Collections.shuffle(studied);
		
		return studied
			.stream()
			.filter(StudySessionWord::hasntFinished)
			.findAny()
			.get();
	}

	private void initializeStudy(Lesson lesson) {
		for (LessonWord word : lesson.getLessonWords()) {
			getStudied().add(new StudySessionWord(word));
		}
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

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public List<StudySessionWord> getStudied() {
		return studied;
	}

	public void setStudied(List<StudySessionWord> studied) {
		this.studied = studied;
	}
}
