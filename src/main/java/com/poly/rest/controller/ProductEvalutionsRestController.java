package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.Product;
import com.poly.entity.ProductEvalution;
import com.poly.service.AccountService;
import com.poly.service.ProductEvalutionService;
import com.poly.service.ProductService;



@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/rating")
public class ProductEvalutionsRestController {

	@Autowired
	ProductEvalutionService ProductEvalution;

	
	@GetMapping("/rest/rating/{username}/{id}")
	public Boolean getCheckEvalution(@PathVariable("username") String username,@PathVariable("id") Integer id) {
		return ProductEvalution.checkEvalution(username, id) == null?false:true;
	}
	
	@PostMapping("/rest/rating")
	public ProductEvalution create(@RequestBody JsonNode ratingData) {
		return ProductEvalution.create(ratingData);
	}
	
	//duy
	@GetMapping("/rest/getProductEvalution")
	public List<ProductEvalution> getAll() {
		return ProductEvalution.findALl();
	}
	
	@PutMapping("/rest/upProductEvalution")
	public ProductEvalution update(@RequestBody ProductEvalution pEvalution) {
		return ProductEvalution.update(pEvalution);
	}
	
	@DeleteMapping("/rest/delProductEvalution/{id}")
	public void delete(@PathVariable("id") Integer id) {
		ProductEvalution.delete(id);
	}

}
