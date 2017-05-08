package com.satoru.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StudySession {
	private static Integer MAX_OPTIONS = 3;
	
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
	
	public Boolean hasFinished() {			
		return studied
			.stream()
			.filter(StudySessionWord::hasntFinished)			
			.count() == 0;
	}
	
	
	
	public StudySessionWord getNextWord() {
		Collections.shuffle(studied);
		
		return studied
			.stream()
			.filter(StudySessionWord::hasntFinished)
			.findAny()
			.get();
	}
	
	public List<StudySessionOption> generateOptions(StudySessionWord current) {
		String correctAnswer = current.getLessonWord().getRomanizedWord();
		
		List<StudySessionOption> options = studied
				.stream()
				.map(s -> s.getLessonWord().getRomanizedWord())
				.filter(w -> ! w.equals(correctAnswer))
				.limit(MAX_OPTIONS - 1)
				.map(w -> new StudySessionOption(w, false))
				.collect(Collectors.toList());
		
		options.add(new StudySessionOption(correctAnswer, true));
		
		Collections.shuffle(options);
		
		return options;
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
	
	private Integer getTotalStudies() {
		return studied.size() * StudySessionWord.MAX_STUDIES;
	}
	
	private Integer getTotalStudied() {
		return studied
			.stream()
			.mapToInt(s -> s.getQuantity())
			.sum();
	}
	
	public Double getStudiedPercentage() {
		return (getTotalStudied() * 100.0) / getTotalStudies(); 
	}

	public void increment(StudySessionWord studySessionWord) {
		studied
			.stream()
			.filter(s -> s.getLessonWord().getWord().equals(studySessionWord.getLessonWord().getWord()))
			.forEach(s -> s.increment());
	}
}
