package com.satoru.domain;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReviewWord extends LessonWord {		
	private Date lastReview;
	
	private Date nextReview;
	
	private Integer repetitions = 1;
	
	@DBRef
	private User user;

	public ReviewWord() {}
	
	public ReviewWord(User user, LessonWord lessonWord) {
		this.user = user;
		this.lastReview = null;
		this.repetitions = 1;
		this.setWord(lessonWord.getWord());
		this.setMeaning(lessonWord.getMeaning());
		this.setRomanizedWord(lessonWord.getRomanizedWord());
		
		scheduleReview();
	}

	public Date getLastReview() {
		return lastReview;
	}

	public void setLastReview(Date lastReview) {
		this.lastReview = lastReview;
	}

	public Date getNextReview() {
		return nextReview;
	}

	public void setNextReview(Date nextReview) {
		this.nextReview = nextReview;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(Integer repetitions) {
		this.repetitions = repetitions;
	}

	public void scheduleReview() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, nextReview(repetitions));
		
		this.nextReview = calendar.getTime();		
	}

	private int nextReview(Integer repetitions) {
		Double fib = Math.floor((Math.pow((1+Math.sqrt(5))/2,repetitions+1)) /Math.sqrt(5) + 1/2);

		return fib.intValue();
	}

	public void increaseRepetition() {
		this.repetitions++;		
	}
}
