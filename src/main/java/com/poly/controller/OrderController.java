package com.poly.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.entity.Order;
import com.poly.service.OrderService;

 

@Controller
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/order/checkout")
	public String checkout(Model model,@RequestParam("pay") String pay, @RequestParam("price_pay") String price) {
		System.out.println(pay);
		if(pay.equals("normal"))
			return "order/checkout2";
		else
		System.out.println(price);
		model.addAttribute("price_view", price);
		return "order/checkout_paypal";
		
	}
	
	@RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}
	
	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id")Long id,Model model) {
		Order order = (Order) orderService.findById(id);
		
		String[] address = order.getAddress().split(",");
		String[] xa = address[0].split(":");
		String[] quan = address[1].split(":");
		String[] tp = address[2].split(":");
		
		String newAddress = address[3]+","+xa[1] + "," + quan[1] + "," + tp[1];
		System.out.println(newAddress);
		order.setAddress(newAddress);
		model.addAttribute("order", order);
		return "order/detail";
	}
}


