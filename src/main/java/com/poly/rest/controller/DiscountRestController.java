package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Discount;
import com.poly.service.DiscountService;
//duy
@RestController
public class DiscountRestController {

	@Autowired
	DiscountService discountService;
	
	@GetMapping("/rest/discount")
	public List<Discount> findAll() {
		return discountService.findAll();
	}
	
	@PostMapping("/rest/discount")
	public Discount save(@RequestBody Discount discount) {
		return discountService.save(discount);
	}
	
	@DeleteMapping("/rest/discount/{id}")
	public void delete(@PathVariable("id") Integer id) {
		discountService.delete(id);
	}
	
	@PutMapping("/rest/discount")
	public Discount update(@RequestBody Discount discount) {
		return discountService.save(discount);
	}
}
