package com.poly.service;

import java.util.List;

import com.poly.entity.Brand;

public interface BrandService {
	List<Brand> findAll();

	Object findRandomTop8();
	
	//day
	List<Brand> brandsName(Integer id_cate);
	
	//duy
	//List<Brand> findAll();
	
	Brand add(Brand brand);
	
	Brand updateIsDeleted(Brand brand);
	
	Brand update(Brand brand);
	
	List<Brand> getListIsDeleted();
}
