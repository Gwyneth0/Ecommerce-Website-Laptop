package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.OrderDAO;
import com.poly.dao.ProductEvalutionDAO;
import com.poly.entity.Account;
import com.poly.entity.Product;
import com.poly.entity.ProductEvalution;
import com.poly.service.ProductEvalutionService;

@Service
public class ProductEvalutionServiceImpl implements ProductEvalutionService{
	
	@Autowired
	ProductEvalutionDAO productEvalutionDAO;
	
	@Autowired
	OrderDAO orderDAO;

	@Override
	public List<ProductEvalution> findALl() {
		return productEvalutionDAO.findAll();
	}

	@Override
	public ProductEvalution create(JsonNode ratingData) {
		ObjectMapper mapper = new ObjectMapper();

		ProductEvalution startData = mapper.convertValue(ratingData, ProductEvalution.class);
		productEvalutionDAO.save(startData);
		System.out.println(startData);
		return startData;
	}

	@Override
	public List<ProductEvalution> findAllByProductId(Integer id) {
		return productEvalutionDAO.findAllByProductId(id);
	}

	@Override
	public String checkEvalution(String username, Integer id) {
		return orderDAO.check(username, id);
	}

	//duy
//	@Autowired
//	ProductEvalutionDAO productEvalutionDAO;
//
//	@Override
//	public List<ProductEvalution> findALl() {
//		return productEvalutionDAO.findAll();
//	}

	@Override
	public ProductEvalution update(ProductEvalution productEvalution) {
		return productEvalutionDAO.save(productEvalution);
	}

	@Override
	public void delete(Integer id) {
		productEvalutionDAO.deleteById(id);
	}

}
