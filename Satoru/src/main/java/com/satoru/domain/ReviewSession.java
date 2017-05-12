package com.satoru.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReviewSession {
	private static Integer MAX_OPTIONS = 3;
	
	@Id
	private String id;
	
	@DBRef
	private User user;
	
	private List<ReviewSessionWord> toReview = new ArrayList<>();
	
	public ReviewSession() {}

	public ReviewSession(User user, List<ReviewWord> reviewWords) {
		this.user = user;
		
		initializeReview(reviewWords);
	}
	
	private void initializeReview(List<ReviewWord> reviewWords) {
		this.toReview = reviewWords
						.stream()
						.map(r -> new ReviewSessionWord(r))
						.collect(Collectors.toList());
	}

	public Boolean hasFinished() {			
		return toReview
			.stream()
			.filter(ReviewSessionWord::hasntFinished)			
			.count() == 0;
	}

	public ReviewSessionWord getNextWord() {
		Collections.shuffle(toReview);
		
		return toReview
			.stream()
			.filter(ReviewSessionWord::hasntFinished)
			.findAny()
			.get();
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

	public List<ReviewSessionWord> getToReview() {
		return toReview;
	}

	public void setToReview(List<ReviewSessionWord> toReview) {
		this.toReview = toReview;
	}
	
	private Integer getTotalStudies() {
		return toReview.size() * ReviewSessionWord.MAX_STUDIES;
	}
	
	private Integer getTotalStudied() {
		return toReview
			.stream()
			.mapToInt(s -> s.getQuantity())
			.sum();
	}
	
	public Double getStudiedPercentage() {
		return (getTotalStudied() * 100.0) / getTotalStudies(); 
	}

	public void increment(ReviewSessionWord sessionWord) {
		toReview
			.stream()
			.filter(s -> s.getReviewWord().getWord().equals(sessionWord.getReviewWord().getWord()))
			.forEach(s -> s.increment());
	}
	
	public List<SessionOption> generateOptions(ReviewSessionWord current) {
		String correctAnswer = current.getReviewWord().getRomanizedWord();
		
		List<SessionOption> options = toReview
				.stream()
				.map(s -> s.getReviewWord().getRomanizedWord())
				.filter(w -> ! w.equals(correctAnswer))
				.limit(MAX_OPTIONS - 1)
				.map(w -> new SessionOption(w, false))
				.collect(Collectors.toList());
		
		options.add(new SessionOption(correctAnswer, true));
		
		Collections.shuffle(options);
		
		return options;
	}

	public Object generateOptionsForSound(ReviewSessionWord current) {
		String correctAnswer = current.getReviewWord().getWord();
		
		List<SessionOption> options = toReview
				.stream()
				.map(s -> s.getReviewWord().getWord())
				.filter(w -> ! w.equals(correctAnswer))
				.limit(MAX_OPTIONS - 1)
				.map(w -> new SessionOption(w, false))
				.collect(Collectors.toList());
		
		options.add(new SessionOption(correctAnswer, true));
		
		Collections.shuffle(options);
		
		return options;
	}
}
