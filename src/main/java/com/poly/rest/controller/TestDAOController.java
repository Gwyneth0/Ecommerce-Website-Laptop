package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.ProductDAO;
import com.poly.dao.ProductEvalutionDAO;
import com.poly.entity.Account;
import com.poly.entity.Authority;
import com.poly.entity.Brand;
import com.poly.entity.Category;
import com.poly.entity.Discount;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.entity.ProductEvalution;
import com.poly.entity.ProductLike;
import com.poly.entity.ProductPhoto;
import com.poly.entity.Role;
import com.poly.service.AccountService;
import com.poly.service.AuthorityService;
import com.poly.service.BrandService;
import com.poly.service.CategoryService;
import com.poly.service.DiscountService;
import com.poly.service.OrderDetailService;
import com.poly.service.OrderService;
import com.poly.service.ProductLikeService;
import com.poly.service.ProductPhotoService;
import com.poly.service.RoleService;

@CrossOrigin("*")
@RestController
@RequestMapping("t")
public class TestDAOController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	BrandService brandService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	ProductEvalutionDAO productEvalutionDAO;
	
	@Autowired
	ProductLikeService productLikeService;
	
	@Autowired
	ProductPhotoService productPhotoService;
	
	@Autowired
	RoleService roleService;

//	@RequestMapping
//	public List<Account> name() {
//		return accountService.findAll();
//	}
	
//	@RequestMapping
//	public List<Authority> name() {
//		return authorityService.findAll();
//	}
	
//	@RequestMapping
//	public List<Brand> name() {
//		return brandService.findAll();
//	}
	
//	@RequestMapping
//	public List<Category> name() {
//		return categoryService.findAll();
//	}
	
//	@RequestMapping
//	public List<Discount> name() {
//		return discountService.findAll();
//	}
	
//	@RequestMapping
//	public List<OrderDetail> name() {
//		return orderDetailService.findAll();
//	}
	
//	@RequestMapping
//	public List<Product> name() {
//		return productDAO.findAll();
//	}
	
//	@RequestMapping
//	public List<ProductLike> name() {
//		return productLikeService.findAll();
//	}
	
//	@RequestMapping
//	public List<ProductPhoto> name() {
//		return productPhotoService.findAll();
//	}
	
	@RequestMapping
	public List<Role> name() {
		return roleService.findAll();
	}
}
