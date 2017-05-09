package com.satoru.domain;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Audit implements Comparable<Audit> {
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	private Date date;
	
	private Integer studySuccess;
	
	private Integer studyErrors;
	
	private Integer reviewSuccess;
	
	private Integer reviewErrors;

	public Audit(User user, Date date) {
		this.user = user;
		this.date = date;
		this.studySuccess = 0;
		this.studyErrors = 0;
		this.reviewSuccess = 0;
		this.reviewErrors = 0;
	}

	public Date getDate() {
		return date;
	}

	public Integer getStudySuccess() {
		return studySuccess;
	}

	public Integer getStudyErrors() {
		return studyErrors;
	}

	public Integer getReviewSuccess() {
		return reviewSuccess;
	}

	public Integer getReviewErrors() {
		return reviewErrors;
	}

	public void incrementStudy(boolean answerIsRight) {
		if (answerIsRight) {
			this.studySuccess++;	
		} else {
			this.studyErrors++;	
		}		
	}
	
	public void incrementReview(boolean answerIsRight) {
		if (answerIsRight) {
			this.reviewSuccess++;	
		} else {
			this.reviewErrors++;	
		}		
	}

	public Boolean isSameDate(Date currentDate) {
		return DateUtils.isSameDay(this.date, currentDate);
	}

	@Override
	public int compareTo(Audit audit) {
		return this.getDate().compareTo(audit.getDate());
	}
}
