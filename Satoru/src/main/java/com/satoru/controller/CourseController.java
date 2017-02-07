package com.satoru.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;

@Controller
@RequestMapping("/course")
@Layout(value=Layout.DEFAULT)
public class CourseController {

	@GetMapping
	public String list(Map<String, Object> model) {
		return "course/list";
	}
}
