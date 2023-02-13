package com.poly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poly.intercreptor.GlobalInterceptor_Br;



@Configuration
public class InterceptorConfig_Br implements WebMvcConfigurer{

	@Autowired
	GlobalInterceptor_Br globalInterceptor_br;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalInterceptor_br)
		.addPathPatterns("/**")
		.excludePathPatterns("/rest/**", "/admin/**", "/assets/**");
	}
}
