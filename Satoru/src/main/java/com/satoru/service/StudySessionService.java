package com.satoru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Lesson;
import com.satoru.domain.StudySession;
import com.satoru.domain.StudySessionWord;
import com.satoru.domain.User;
import com.satoru.repository.StudySessionRepository;

@Service
public class StudySessionService extends GenericService<StudySession, String, StudySessionRepository> {

	@Autowired
	private ProgressService progressService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private ReviewWordService reviewWordService;

	public StudySessionService() {
		this.sort = new Sort(Direction.DESC, "name");
	}

	public StudySession findByUserAndLesson(User user, Lesson lesson) {
		StudySession studySession = getRepository().findByUserAndLesson(user, lesson);

		if (studySession == null) {
			studySession = new StudySession(user, lesson);
		}

		return studySession;
	}

	public Boolean processAnswerAndCheckFinish(User loggedUser, Lesson lesson, StudySessionWord sessionWord) {
		auditService.auditAnswer(loggedUser, sessionWord.answerIsRight());
		
		StudySession studySession = findByUserAndLesson(loggedUser, lesson);
		studySession.increment(sessionWord);
		
		save(studySession);

		if (studySession.hasFinished()) {
			reviewWordService.saveLessonForReview(loggedUser, lesson);
			delete(studySession);

			progressService.increment(loggedUser, lesson);

			return true;
		}

		return false;
	}
}