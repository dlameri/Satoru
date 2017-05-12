package com.satoru.domain;

public class SessionOption {
	private String answer;
	
	private Boolean right;
	
	public SessionOption(String answer, Boolean right) {
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
