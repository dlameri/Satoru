package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.satoru.annotation.Layout;
import com.satoru.domain.Course;
import com.satoru.service.CourseService;
import com.satoru.service.LessonService;

@Controller
@RequestMapping("/study")
@Layout(value = Layout.DEFAULT)
public class StudyController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private LessonService lessonService;
	
	@GetMapping("/course")
	public String editForm(Model model, @RequestParam("id") String courseId) {
		Course course = courseService.findOne(courseId);
		model.addAttribute("title", course.getName());
		model.addAttribute("models", lessonService.findByCourse(course));

		return "study/lessonList";
	}
}
