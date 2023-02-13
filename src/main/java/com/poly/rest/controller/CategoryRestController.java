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
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Category;
import com.poly.service.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryRestController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/rest/categories")
	public List<Category> getAll() {
		return categoryService.findAll();
	}
	
	@PostMapping("/rest/categories")
	public Category create(@RequestBody Category cate) {
		return categoryService.create(cate);
	}
	
	@PutMapping("/rest/categories/{id}")
	public Category update(@PathVariable("id") Integer id, @RequestBody Category cate) {
		return categoryService.update(cate);
	}
	
	@DeleteMapping("/rest/categories/{id}")
	public void delete(@PathVariable("id") Integer id) {
		categoryService.delete(id);
	}
	
	@PutMapping("/rest/updateCateIsDeleted")
	public Category updateCateIsDeleted(@RequestBody Category category) {
		return categoryService.update(category);
	}
}
