package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Brand;
import com.poly.entity.Product;

public interface BrandDAO extends JpaRepository<Brand, Integer>{

	@Query(value = "SELECT TOP 8 * FROM Brands ORDER BY NEWID()", 
			nativeQuery = true)
	List<Brand> findRandomTop8();
	
	@Query(value="select Brands.* from \n" + 
			"Categories join Products on Categories.Id=Products.CategoryId join \n" + 
			"Brands on Brands.Id=Products.BrandId where Categories.Id=?1 and Brands.IsDeleted = 0 group by Brands.Id, Brands.image,Brands.IsDeleted,Brands.Name", nativeQuery=true)
	List<Brand> brandsName(Integer id);
	
	// duy
	@Query("SELECT b FROM Brand b WHERE b.isDeleted = 0")
	List<Brand> getListIsDeleted();

}
