package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductDAO;
import com.poly.entity.Product;
import com.poly.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductDAO productDAO;

	@Override
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return productDAO.findById(id).get();
	}

	@Override
	public List<Product> findByCategoryId(Integer cid) {
		return productDAO.findByCategoryId(cid);
	}

	@Override
	public List<Product> findByBrandId(Integer bid) {
		return  productDAO.findByBrandId(bid);
	}

	@Override
	public Page<Product> PageCategoryId(Integer cid,String sort, Pageable pageable) {
		return productDAO.PageCategoryId(cid, sort, pageable);
	}

	@Override
	public Page<Product> PageBrandId(Integer bid, Pageable pageable) {
		return productDAO.PageBrandId(bid, pageable);
	}

	@Override
	public List<Product> loadProductByBrandIdAndCateId(Integer cid,Integer bid ) {
		return productDAO.loadProductByBrandIdAndCateId(cid,bid);
	}

	@Override
	public Page<Product> findByKeywords(String keywords, Pageable pageable) {
		System.out.println(keywords);
		
		Page<Product> p = productDAO.findByKeywords(keywords, pageable);
		for (Product product : p) {
			System.out.println(product);
		}
		return p;
	}
	
	@Override
	public Page<Product> findByPrice(Integer cate_id ,Double minPrice, Double maxPrice, Pageable pageable) {
		return productDAO.findByPrice( cate_id,minPrice, maxPrice, pageable);
	}
	
	
	//day
	@Override
	public List<Product> PageCategoryId(Integer cid) {
		// TODO Auto-generated method stub
		return productDAO.PageCategoryId(cid);
	}
	
	//duy
//	@Override
//	public List<Product> findAll() {
//		return productDAO.findAll();
//	}

	@Override
	public Product create(Product p) {
		return productDAO.save(p);
	}

	@Override
	public Product update(Product p) {
		return productDAO.save(p);
	}

	@Override
	public void delete(Integer id) {
		productDAO.deleteById(id);
	}

	@Override
	public List<Product> getProductNotInDiscount() {
		return productDAO.getProductNotInDiscount();
	}

	@Override
	public List<Product> findBySellExpensive() {
		return productDAO.findBySellExpensive();
	}

	

	
	

}
