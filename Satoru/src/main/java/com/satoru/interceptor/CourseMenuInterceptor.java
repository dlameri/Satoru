package com.satoru.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.satoru.service.CourseService;

@Component
public class CourseMenuInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired private CourseService courseService;
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        String originalViewName = modelAndView.getViewName();
        if ("layout/default".equals(originalViewName)) {
        	modelAndView.addObject("courses", courseService.listAll());
        }         
    }
}
