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
	
	private Double ef = 2.5;
	
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
		
		scheduleReview(0);
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

	public void scheduleReview(Integer quality) {
		if (quality < 3) {
			ef = 2.5;
			repetitions = 1;
		} else {
			updateEffort(quality);			
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, nextReview(repetitions));
		
		this.nextReview = calendar.getTime();		
	}

	private int nextReview(Integer repetitions) {
		if (repetitions == 1) {
			return 1;
		} else if (repetitions == 2) {
			return 6;
		} else {
			Double nextReview = Math.floor(nextReview(repetitions-1)*ef);

			return nextReview.intValue();
		}
	}

	private void updateEffort(Integer quality) {
		this.ef = this.ef + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
	}

	public void increaseRepetition() {
		this.repetitions++;		
	}
}
