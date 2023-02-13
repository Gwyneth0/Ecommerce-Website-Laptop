package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoryDAO;
import com.poly.entity.Category;
import com.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll();
	}

	@Override
	public Object findRandomTop8() {
		return categoryDAO.findRandomTop8();
	}

	@Override
	public Object findTop8() {
		return categoryDAO.findTop8();
	}

	@Override
	public Category create(Category cate) {
		return categoryDAO.save(cate);
	}

	@Override
	public Category update(Category cate) {
		return categoryDAO.save(cate);
	}

	@Override
	public void delete(Integer id) {
		categoryDAO.deleteById(id);
	}
	
	//duy
//	@Override
//	public List<Category> findAll() {
//		return categoryDAO.findAll();
//	}
}
