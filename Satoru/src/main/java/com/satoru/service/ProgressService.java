package com.satoru.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.domain.User;
import com.satoru.domain.Progress;
import com.satoru.repository.ProgressRepository;

@Service
public class ProgressService extends GenericService<Progress, String, ProgressRepository>{
	public ProgressService() {
		this.sort = new Sort(Direction.DESC, "id");
	}
	
	public Progress findByUserAndCourse(User user, Course course) {
		Progress progress = getRepository().findByUserAndCourse(user, course);
		
		if (progress == null) {
			progress = new Progress(user, course);
		}
		
		return progress;
	}

	public void increment(User loggedUser, Lesson lesson) {
		Progress progress = findByUserAndCourse(loggedUser, lesson.getCourse());
		progress.increment(lesson);
		
		save(progress);
	}
}