package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satoru.annotation.Layout;
import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.service.CourseService;
import com.satoru.service.LessonService;

@Controller
@RequestMapping("/course/{courseId}/lesson")
@Layout(value = Layout.DEFAULT)
public class LessonController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private LessonService lessonService;

	@GetMapping
	public String list(Model model, @PathVariable("courseId") String courseId) {
		model.addAttribute("models", courseService.findOne(courseId).getLessons());

		return "lesson/list";
	}
	
	@GetMapping("/new")
	public String creationForm(Model model, @PathVariable("courseId") String courseId) {
		model.addAttribute("title", "Criação");
		model.addAttribute("model", new Lesson());

		return "lesson/form";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(Model model, @PathVariable("id") String id, @PathVariable("courseId") String courseId) {
		model.addAttribute("title", "Edição");
		model.addAttribute("model", lessonService.findOne(id));

		return "lesson/form";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(Model model, @PathVariable("id") String id, @PathVariable("courseId") String courseId) {
		lessonService.delete(lessonService.findOne(id));
		
		return "redirect:/course/" + courseId + "/lesson";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "model") Lesson lesson, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		lesson.setCourse(course);
		
		lessonService.save(lesson);
		
		course.addLesson(lesson);
		courseService.save(course);
		
		return "redirect:/course/" + courseId + "/lesson";
	}
}