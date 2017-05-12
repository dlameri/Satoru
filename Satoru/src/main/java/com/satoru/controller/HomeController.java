package com.satoru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.satoru.annotation.Layout;
import com.satoru.domain.Audit;
import com.satoru.service.AuditService;

@Controller
@RequestMapping("/home")
@Layout(value=Layout.DEFAULT)
public class HomeController extends GenericController {
	
	@Autowired 
	private AuditService auditService;
	
	@GetMapping
	public String home(Model model) {		
		List<Audit> audits = auditService.listForDashboard(getLoggedUser());
		model.addAttribute("models", audits);
		
		
		model.addAttribute("totalStudies", auditService.getTotalStudies(audits));
		model.addAttribute("totalReviews", auditService.getTotalReviews(audits));

		return "home/dashboard";
	}
}
