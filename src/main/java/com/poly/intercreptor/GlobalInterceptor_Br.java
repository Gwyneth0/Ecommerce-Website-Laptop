package com.poly.intercreptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.poly.service.BrandService;
import com.poly.service.CategoryService;



@Component
public class GlobalInterceptor_Br implements HandlerInterceptor{

	@Autowired
	BrandService brandService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		request.setAttribute("brands", brandService.findAll());
		request.setAttribute("brands", brandService.findRandomTop8());
	}
}
