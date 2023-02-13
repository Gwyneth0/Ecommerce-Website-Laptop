package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Brand;
import com.poly.entity.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{

	@Query(value = "SELECT TOP 8 * FROM Categories ORDER BY NEWID()", 
			nativeQuery = true)
	List<Category> findRandomTop8();

	@Query(value = "SELECT TOP 8 * FROM Categories", 
			nativeQuery = true)
	List<Category> findTop8();
	
	//duy k co
}
