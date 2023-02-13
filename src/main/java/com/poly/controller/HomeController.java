package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//duy
@Controller
public class HomeController {
	
	@RequestMapping("/admin")
	public String home() {
		return "redirect:/assets/admin/layout/index.html";
		//return "layout/index";
	}
}
