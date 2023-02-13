package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.ProductPhoto;

public interface ProductPhotoDAO extends JpaRepository<ProductPhoto, String>{
	@Query("select pp from ProductPhoto pp where pp.product.id=?1")
	List<ProductPhoto> getProductPhotoImage(Integer pid);
	
	//duy
	@Query("SELECT pp FROM ProductPhoto pp WHERE pp.product.id = ?1")
	List<ProductPhoto> getListByProductId(Integer id);
}
