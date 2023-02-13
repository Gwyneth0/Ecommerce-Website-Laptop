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

import com.poly.entity.Brand;
import com.poly.entity.Category;
import com.poly.service.BrandService;
import com.poly.service.CategoryService;
// duy
@CrossOrigin("*")
@RestController
public class BrandRestController {
	@Autowired
	BrandService brandService;
	
	@RequestMapping("/rest/brand")
	public List<Brand> getAll() {
		return brandService.findAll();
	}
	
	//day
	@RequestMapping("/rest/brandName/{id_cate}")
	public List<Brand> getBrandByIdCate(@PathVariable ("id_cate") Integer id_cate) {
		return brandService.brandsName(id_cate);
	}
	
	@GetMapping("/rest/brandIsDeleted")
	public List<Brand> getAllIsDeleted() {
		return brandService.getListIsDeleted();
	}
	
	@PostMapping("/rest/addBrand")
	public Brand add(@RequestBody Brand brand) {
		return brandService.add(brand);
	}
	
	@PutMapping("/rest/updateBrandIsDeleted")
	public Brand updateBrandIsDeleted(@RequestBody Brand brand) {
		return brandService.updateIsDeleted(brand);
	}
	
	@PutMapping("/rest/updateBrand")
	public Brand update(@RequestBody Brand brand) {
		return brandService.update(brand);
	}
}
