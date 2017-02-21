package com.satoru.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Course;
import com.satoru.repository.CourseRepository;

@Service
public class CourseService extends GenericService<Course, String, CourseRepository>{
	public CourseService() {
		this.sort = new Sort(Direction.DESC, "name");
	}
	
	public Course findByName(String name) {
		return getRepository().findByName(name);
	}
}