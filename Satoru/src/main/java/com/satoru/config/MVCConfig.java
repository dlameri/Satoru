package com.satoru.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.satoru.interceptor.MenuInterceptor;
import com.satoru.interceptor.ThymeleafLayoutInterceptor;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {	
	
	@Autowired private List<HandlerInterceptorAdapter> customInterceptors;
	
	@Bean
	public List<HandlerInterceptorAdapter> customInterceptors(ThymeleafLayoutInterceptor thymeleafLayoutInterceptor, MenuInterceptor courseMenuInterceptor) {
		return Arrays.asList(
			courseMenuInterceptor,
			thymeleafLayoutInterceptor			
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