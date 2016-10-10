package com.satoru.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;

@Controller
@RequestMapping("/dashboard")
@Layout(value=Layout.DEFAULT)
public class DashboardsController {
	
	@GetMapping
	public String dashboard(Map<String, Object> model) {
		return "dashboard/dashboard";
	}
}
