package com.satoru.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document	
public class LessonWord {
	@Id
	private String id;
	
	private String word;
	
	private String romanizedWord;
	
	private String meaning;
	
	private List<LessonWord> reviewLinks = new ArrayList<>();

	public LessonWord() {}
	
	public LessonWord(String word, String romanizedWord, String meaning) {
		this.word = word;
		this.romanizedWord = romanizedWord;
		this.setMeaning(meaning);
	}
	
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

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public List<LessonWord> getReviewLinks() {
		return reviewLinks;
	}

	public void setReviewLinks(List<LessonWord> reviewLinks) {
		this.reviewLinks = reviewLinks;
	}

	public void clearReviewLinks() {
		this.reviewLinks.clear();
	}

	public void addReviewLink(LessonWord review) {
		this.reviewLinks.add(review);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonWord other = (LessonWord) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
}
