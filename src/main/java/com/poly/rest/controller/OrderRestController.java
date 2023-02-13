package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/orders")
public class OrderRestController {
	
	@Autowired
	OrderService orderService;
	
	
	@PostMapping("/rest/orders")
	public Order create(@RequestBody JsonNode orderData) {
		//Order oder = orderService.create(orderData);
		return orderService.create(orderData);
	}
	
	@DeleteMapping("/rest/orders/{id}")
	public void delete(@PathVariable("id") 	Long id) {
		orderService.delete(id);
	}
	
	// duy
	@GetMapping("/rest/order")
	public List<Order> getList() {
		return orderService.findAll();
	}
	
	@PutMapping("/rest/orderUpdate")
	public void update(@RequestBody Order o) {
		Long idOrder = o.getId();
		 orderService.update_order(idOrder);
	}
}
