package com.satoru.domain;

public class StudySessionOption {
	private String answer;
	
	private Boolean right;
	
	public StudySessionOption(String answer, Boolean right) {
		this.answer = answer;
		this.right = right;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public Boolean getRight() {
		return right;
	}
	
}
