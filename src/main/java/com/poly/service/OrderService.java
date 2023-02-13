package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;

public interface OrderService {
	List<Order> findAll();

	Order create(JsonNode orderData);

	Object findById(Long id);

	Object findByUsername(String username);

	void delete(Long id);
	
	// duy
	//List<Order> findAll();
	
	//Order updateStatus(Order o);
	
	void update_order(Long id);
}
