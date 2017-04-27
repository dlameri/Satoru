package com.satoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document	
public class LessonWord {
	@Id
	private String id;
	
	private String word;
	
	private String romanizedWord;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getRomanizedWord() {
		return romanizedWord;
	}

	public void setRomanizedWord(String romanizedWord) {
		this.romanizedWord = romanizedWord;
	}
}
