package com.wei.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wei.boot.interceptor.RequesHandlerInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean   //把我们的拦截器注入为bean
    public HandlerInterceptor getMyInterceptor(){
        return new RequesHandlerInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
