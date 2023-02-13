package com.poly.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	OrderDetailDAO odddao;

	@Override
	public List<Order> findAll() {
		return orderDAO.findAll();
	}

	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();

		Order order = mapper.convertValue(orderData, Order.class);
		orderDAO.save(order);
		 System.out.println(order);
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
				.peek(d -> d.setOrder(order)).collect(Collectors.toList());
		// System.out.println(details);
		odddao.saveAll(details);

		return order;
	}

	@Override
	public Object findById(Long id) {
		return orderDAO.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		List<Order> list = orderDAO.findByUsername(username);
		for(Order o: list) {
			String[] address = o.getAddress().split(",");
			String[] xa = address[0].split(":");
			String[] quan = address[1].split(":");
			String[] tp = address[2].split(":");
			
			String newAddress = address[3]+","+xa[1] + "," + quan[1] + "," + tp[1];
			o.setAddress(newAddress);
		}
		return list;
	}

	@Override
	public void delete(Long id) {
		orderDAO.deleteById(id);
	}

	
	
	//duy
//	@Override
//	public List<Order> findAll() {
//		return orderDAO.findAll();
//	}

//	@Override
//	public Order updateStatus(Order o) {
//		return orderDAO.save(o);
//	}
	
	@Override
	public void update_order(Long id) {
		 orderDAO.update_order(id);
	}

}
