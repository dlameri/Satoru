package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;
import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.domain.User;
import com.satoru.service.CourseService;
import com.satoru.service.LessonService;
import com.satoru.service.ProgressService;
import com.satoru.service.StudySessionService;

@Controller
@RequestMapping("/study")
@Layout(value = Layout.DEFAULT)
public class StudyController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private ProgressService progressService;
	
	@Autowired
	private StudySessionService studySessionService;
	
	@GetMapping("/course/{courseId}")
	public String listLessons(Model model, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);

		model.addAttribute("title", course.getName());
		model.addAttribute("models", lessonService.findByCourse(course));
		model.addAttribute("progress", progressService.findByUserAndCourse(getLoggedUser(), course));

		return "study/lessonList";
	}
	
	@GetMapping("/course/{id}/lesson/{lessonId}")
	public String studyLesson(Model model, @PathVariable("courseId") String courseId, @PathVariable("lessonId") String lessonId) {
		Course course = courseService.findOne(courseId);
		Lesson lesson = lessonService.findOne(lessonId);
		
		model.addAttribute("courseName", course.getName());
		model.addAttribute("model", studySessionService.findByUserAndLesson(getLoggedUser(), lesson));

		return "study/lesson";
	}

	private User getLoggedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
