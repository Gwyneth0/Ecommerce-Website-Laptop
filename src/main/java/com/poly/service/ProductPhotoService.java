package com.poly.service;

import java.util.List;

import com.poly.entity.ProductPhoto;

public interface ProductPhotoService {
	List<ProductPhoto> findAll();
	
	List<ProductPhoto> getProductPhotoImage(Integer pid);
	
	//duy
	//List<ProductPhoto> findAll();
	
	List<String> getListIdProductPhotoByProductId(Integer idP);
	
	void save(ProductPhoto pp);
	
	void delete(String id);
}
