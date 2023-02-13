package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Discount;
import com.poly.entity.ProductEvalution;

public interface DiscountDAO extends JpaRepository<Discount, Integer>{
	@Query(value = "SELECT * FROM Discounts WHERE ProductId = ?1", 
			nativeQuery = true)
	Discount findByProductId(Integer id);
	
	//duy k co
}
