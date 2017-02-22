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
import com.satoru.service.CourseService;

@Controller
@RequestMapping("/course")
@Layout(value = Layout.DEFAULT)
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("models", courseService.listAll());
		
		return "course/list";
	}

	@GetMapping("/new")
	public String creationForm(Model model) {
		model.addAttribute("title", "Criação");
		model.addAttribute("model", new Course());

		return "course/form";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(Model model, @PathVariable("id") String id) {
		model.addAttribute("title", "Edição");
		model.addAttribute("model", courseService.findOne(id));

		return "course/form";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(Model model, @PathVariable("id") String id) {
		courseService.delete(courseService.findOne(id));
		
		return "redirect:/course";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "model") Course course) {		
		courseService.save(course);
		
		return "redirect:/course";
	}
}
