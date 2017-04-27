package com.satoru.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Lesson;
import com.satoru.repository.LessonRepository;

@Service
public class LessonService extends GenericService<Lesson, String, LessonRepository>{
	public LessonService() {
		this.sort = new Sort(Direction.DESC, "name");
	}
}