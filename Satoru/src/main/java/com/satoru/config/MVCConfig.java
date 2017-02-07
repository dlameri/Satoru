package com.satoru.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.satoru.interceptor.ThymeleafLayoutInterceptor;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {
	
	List<HandlerInterceptorAdapter> customInterceptors = new ArrayList<>();
	
	public MVCConfig() {
		customInterceptors = Arrays.asList(
			new ThymeleafLayoutInterceptor()
		);
	}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register/login").setViewName("login");
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	customInterceptors.stream().forEach(registry::addInterceptor);
    }

}