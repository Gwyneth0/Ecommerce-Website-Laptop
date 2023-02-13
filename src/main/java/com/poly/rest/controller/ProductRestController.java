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

import com.poly.entity.Product;
import com.poly.service.ProductService;


@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/products")
public class ProductRestController {

	@Autowired
	ProductService productService;
	
//	@GetMapping("")
//	public List<Product> getAll() {
//		return productService.findAll();
//	}
	
	@GetMapping("/rest/products/{id}")
	public Product getOne(@PathVariable("id") Integer id) {
		return productService.findById(id);
	}
	
	//day
	
	@GetMapping("/rest/productsByCateId/{id}")
	public List<Product> getProducts(@PathVariable("id") Integer id) {
		return productService.PageCategoryId(id);
	}
	
//	
//	@PostMapping()
//	public Product create(@RequestBody Product product) {
//		return productService.create(product);
//	}
//	
//	@PutMapping("{id}")
//	public Product update(@PathVariable("id") Integer id, @RequestBody Product product) {
//		return productService.update(product);
//	}
//	
//	@DeleteMapping("{id}")
//	public void delete(@PathVariable("id") Integer id) {
//		productService.delete(id);
//	}
	
	//duy
	
	@GetMapping("/rest/products")
	public List<Product> getAll() {
		return productService.findAll();
	}
	
	@PostMapping("/rest/products")
	public Product create(@RequestBody Product p) {
		return productService.create(p);
	}
	
	@PutMapping("/rest/products/{id}")
	public Product update(@PathVariable("id") Integer id, @RequestBody Product p) {
		return productService.update(p);
	}
	
	@DeleteMapping("/rest/products/{id}")
	public void delete(@PathVariable("id") Integer id) {
		productService.delete(id);
	}
	
	@GetMapping("/rest/productNotInDiscount")
	public List<Product> t() {
		return productService.getProductNotInDiscount();
	}
}
