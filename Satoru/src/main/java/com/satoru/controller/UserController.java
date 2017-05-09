package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.satoru.annotation.Layout;
import com.satoru.domain.User;
import com.satoru.domain.UserStatus;
import com.satoru.service.RoleService;
import com.satoru.service.UserService;

@Controller
@Layout(value=Layout.NONE)
public class UserController extends GenericController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(path="/register", method = RequestMethod.GET)
	public String index(WebRequest request, Model model) {
	    User user = new User();
	    model.addAttribute("user", user);
	    
	    return "register/index";
	}
	
	@RequestMapping(path="/register", method = RequestMethod.POST)
	public String create(User user, BindingResult result, WebRequest request, Errors errors) {
		if (userService.exists(user)) {
			result.rejectValue("email", "email.exist");
						
			return "register/index";
		} else {
			user.setEnabled(true);
			user.setStatus(UserStatus.STATUS_APPROVED);
			user.addRole(roleService.findOne("ROLE_USER"));
			
			userService.save(user);

			return "redirect:/register/login?registerSuccess";
		}
	}
}
