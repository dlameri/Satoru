package com.satoru.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.satoru.domain.User;
import com.satoru.service.CourseService;
import com.satoru.service.ReviewWordService;

@Component
public class MenuInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired private CourseService courseService;
	@Autowired private ReviewWordService reviewWordService;
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        String originalViewName = modelAndView.getViewName();
        System.out.println(originalViewName); //TODO só esta funcionando com isso, depois ver melhor

        if (originalViewName.contains("layout/default")) {
        	modelAndView.addObject("courses", courseService.listAll());
        	modelAndView.addObject("reviewQuantity", reviewWordService.getReviewQuantity(getLoggedUser()));
        }
    }
	
	protected User getLoggedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	
}