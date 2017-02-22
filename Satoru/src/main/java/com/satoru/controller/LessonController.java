package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;
import com.satoru.domain.Course;
import com.satoru.service.CourseService;

@Controller
@RequestMapping("/course/{courseId}/lessons")
@Layout(value = Layout.DEFAULT)
public class LessonController {
	@Autowired
	private CourseService courseService;

	@GetMapping
	public String list(Model model, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		
		model.addAttribute("course", course);

		
		return "course/list";
	}
}