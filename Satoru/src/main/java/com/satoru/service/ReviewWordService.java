package com.satoru.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Lesson;
import com.satoru.domain.LessonWord;
import com.satoru.domain.ReviewSession;
import com.satoru.domain.ReviewSessionWord;
import com.satoru.domain.ReviewWord;
import com.satoru.domain.User;
import com.satoru.repository.ReviewWordRepository;

@Service
public class ReviewWordService extends GenericService<ReviewWord, String, ReviewWordRepository>{
	public ReviewWordService() {
		this.sort = new Sort(Direction.DESC, "lastReview");
	}

	public void saveLessonForReview(User user, Lesson lesson) {
		findAllLessonWords(lesson)
			.stream()
			.map(l -> new ReviewWord(user, l))
			.forEach(r -> {
				save(r);
			});
	}

	private List<LessonWord> findAllLessonWords(Lesson lesson) {
		List<LessonWord> lessonWordsToReview = new ArrayList<>();
		
		List<LessonWord> processList = lesson.getLessonWords();
		
		while (! processList.isEmpty()) {
			LessonWord lessonWord = processList.get(0);
			
			if (! lessonWordsToReview.contains(lessonWord)) {
				lessonWordsToReview.add(lessonWord);
			}
			
			for (LessonWord review : lessonWord.getReviewLinks()) {
				if (! (processList.contains(review) || lessonWordsToReview.contains(review))) {
					processList.add(review);
				}
			}
			
			processList.remove(lessonWord);
		}
		
		return lessonWordsToReview; 
	}

	public Integer getReviewQuantity(User user) {
		return getRepository().countByUserAndNextReviewLessThan(user, new Date());
	}
	
	public List<ReviewWord> getReviewWords(User user) {
		return getRepository().findByUserAndNextReviewLessThan(user, new Date());
	}

	public void saveReviewedWords(User loggedUser, ReviewSession studySession) {
		for (ReviewSessionWord reviewSessionWord : studySession.getToReview()) {
			ReviewWord reviewWord = reviewSessionWord.getReviewWord();
			
			reviewWord.setLastReview(new Date());
			reviewWord.increaseRepetition();
			reviewWord.scheduleReview(reviewSessionWord.getQuality());
			
			save(reviewWord);
		}

		
	}
}