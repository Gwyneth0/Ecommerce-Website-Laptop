package com.poly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poly.intercreptor.GlobalInterceptor_Ct;



@Configuration
public class InterceptorConfig_Ct implements WebMvcConfigurer{

	@Autowired
	GlobalInterceptor_Ct globalInterceptor_ct;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globalInterceptor_ct)
		.addPathPatterns("/**")
		.excludePathPatterns("/rest/**", "/admin/**", "/assets/**");
	}
}
