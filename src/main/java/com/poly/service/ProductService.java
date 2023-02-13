package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.poly.entity.Product;

public interface ProductService {
	List<Product> findAll();

	Product findById(Integer id);

	List<Product> findByCategoryId(Integer cid);

	List<Product> findByBrandId(Integer integer);
	
	Page<Product> PageCategoryId(Integer cid, String sort, Pageable pageable);
	
	//day
	List<Product> PageCategoryId(Integer cid);
	
	Page<Product> PageBrandId(Integer bid, Pageable pageable);
	
	List<Product> loadProductByBrandIdAndCateId(Integer cid, Integer bid);
	Page<Product> findByKeywords(String keywords, Pageable pageable);
	
	Page<Product> findByPrice(Integer cate_id ,Double minPrice, Double maxPrice, Pageable pageable);
	//duy
	//List<Product> findAll();

	Product create(Product p);

	Product update(Product p);

	void delete(Integer id);

	List<Product> getProductNotInDiscount();
	List<Product> findBySellExpensive();
}
