package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.ProductEvalution;

public interface ProductEvalutionDAO extends JpaRepository<ProductEvalution, Integer>{
	@Query(value = "SELECT * FROM ProductEvalutions WHERE ProductId = ?1 and Status = 'True'", 
			nativeQuery = true)
	List<ProductEvalution> findAllByProductId(Integer id);
	
	// duy k co
}
