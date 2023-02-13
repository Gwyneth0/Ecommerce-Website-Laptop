package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Account;
import com.poly.entity.ProductEvalution;

public interface ProductEvalutionService {
	List<ProductEvalution> findALl();

	ProductEvalution create(JsonNode ratingData);
	
	//Object findAllByProductId();

	List<ProductEvalution> findAllByProductId(Integer id);
	
	String checkEvalution(String username, Integer id); 
	
	//duy
	//List<ProductEvalution> findALl();
	
	ProductEvalution update(ProductEvalution productEvalution);
	
	void delete(Integer id);

}
