package com.poly.service;

import java.util.List;

import com.poly.entity.Discount;

public interface DiscountService {
	List<Discount> findAll();
	Discount findByProductId(Integer id);
	
	// duy
	//List<Discount> findAll();
	
	Discount save(Discount discount);
	
	void delete(Integer id);
}
