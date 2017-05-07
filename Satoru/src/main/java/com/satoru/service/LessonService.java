package com.satoru.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.repository.LessonRepository;

@Service
public class LessonService extends GenericService<Lesson, String, LessonRepository>{
	public LessonService() {
		this.sort = new Sort(Direction.DESC, "name");
	}

	public Lesson findByNameAndCourse(String name, Course course) {
		return getRepository().findByNameAndCourse(name, course);
	}

	public List<Lesson> findByCourse(Course course) {
		return getRepository().findByCourse(course);
	}
}