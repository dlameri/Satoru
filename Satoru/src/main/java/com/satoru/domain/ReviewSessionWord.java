package com.satoru.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ReviewSessionWord {
	public static Integer MAX_STUDIES = 4;
	
	@DBRef
	private ReviewWord reviewWord;
	
	private Integer quantity = 0;
	
	private Integer errors = 0;
	
	@Transient
	private String answer;
	
	public ReviewSessionWord() {}

	public ReviewSessionWord(ReviewWord lessonWord) {
		this.reviewWord = lessonWord;
	}

	public ReviewWord getReviewWord() {
		return reviewWord;
	}

	public void setReviewWord(ReviewWord reviewWord) {
		this.reviewWord = reviewWord;
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
		return reviewWord.getRomanizedWord().equals(answer) || reviewWord.getWord().equals(answer);
	}
	
	public Integer getQuality() {
		if (errors == 0) {
			return 5;
		} else if (errors < MAX_STUDIES/2) {
			return 4;
		} if (errors == MAX_STUDIES/2) {
			return 3;
		} else {
			return 2;
		}
	}

	public void increment(boolean answerIsRight) {
		if (answerIsRight) {
			this.quantity++;
		} else {
			this.errors++;
		}
	}
}
