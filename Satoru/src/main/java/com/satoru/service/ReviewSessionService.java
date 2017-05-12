package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.ReviewSession;
import com.satoru.domain.ReviewSessionWord;
import com.satoru.domain.User;
import com.satoru.repository.ReviewSessionRepository;

@Service
public class ReviewSessionService extends GenericService<ReviewSession, String, ReviewSessionRepository>{
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private ReviewWordService reviewWordService;
	
	public ReviewSessionService() {
		this.sort = new Sort(Direction.DESC, "name");
	}
	
	public ReviewSession findByUser(User user) {
		ReviewSession studySession = getRepository().findByUser(user);
		
		if (studySession == null) {
			studySession = new ReviewSession(user, reviewWordService.getReviewWords(user));
		}
		
		return studySession; 
	}

	public Boolean processAnswerAndCheckFinish(User loggedUser, ReviewSessionWord sesionWord) {
		auditService.auditReview(loggedUser, sesionWord.answerIsRight());
		
		if (sesionWord.answerIsRight()) {
			ReviewSession studySession = findByUser(loggedUser);
			
			studySession.increment(sesionWord);
			
			if (! studySession.hasFinished()) {
				save(studySession);
			} else {
				// agendar proxima revisao
				delete(studySession);

				return true;
			}
		}

		return false;
	}
}