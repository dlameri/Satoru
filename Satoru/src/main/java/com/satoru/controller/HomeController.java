package com.satoru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;

@Controller
@RequestMapping("/home")
@Layout(value=Layout.DEFAULT)
public class HomeController {
	
	@GetMapping
	public String home(Model model) {
		return "home/dashboard";
	}
}
