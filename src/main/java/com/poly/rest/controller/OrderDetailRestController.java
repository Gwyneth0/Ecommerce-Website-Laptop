package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.OrderDetail;
import com.poly.service.OrderDetailService;
//duy
@RestController
public class OrderDetailRestController {
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/rest/getOrderDetail/{id}")
	public List<OrderDetail> getOrderDetail(@PathVariable("id") Long id) {
		return orderDetailService.findByid(id);
	}
	
}
