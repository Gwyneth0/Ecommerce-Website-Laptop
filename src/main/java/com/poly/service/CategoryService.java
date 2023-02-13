package com.poly.service;

import java.util.List;

import com.poly.entity.Category;

public interface CategoryService {
	List<Category> findAll();

	Object findRandomTop8();
	
	Object findTop8();
	
	Category create(Category cate);
	Category update(Category cate);

	void delete(Integer id);
}
