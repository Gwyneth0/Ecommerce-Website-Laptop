package com.poly.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.BrandDAO;
import com.poly.dao.DiscountDAO;
import com.poly.entity.Discount;
import com.poly.entity.Product;
import com.poly.entity.ProductEvalution;
import com.poly.service.ProductEvalutionService;
import com.poly.service.ProductPhotoService;
import com.poly.service.ProductService;





@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	DiscountDAO discountService;
	
	@Autowired
	BrandDAO brandDAO;
	
	@Autowired
	ProductEvalutionService productEvalutionService;
	
	@Autowired
	ProductPhotoService productPhotoService;
	
	@Autowired
	HttpSession ss;
	
	@RequestMapping("/fpolystore.com")
	public String fpolystore(Model model ) {
		// load giam gia
		List<Discount> listdiscount = discountService.findAll();
		model.addAttribute("listdiscount", listdiscount);
		// load ban chay
		List<Product> listsellexpensive = productService.findBySellExpensive();
		model.addAttribute("listsellexpensive", listsellexpensive);
		return "product/list";
	}
	
	@RequestMapping("/product/seach")
	public String seach(Model model, @RequestParam("keywords") Optional<String> keywords, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 2, Sort.by(Direction.ASC, "price"));
		Page<Product> listp = productService.findByKeywords("%"+keywords.get()+"%", pageable);
		System.out.println(listp.getSize());
		model.addAttribute("items", listp);
		model.addAttribute("search",keywords.get());
		model.addAttribute("cid", cate_id);
		model.addAttribute("brands", brandDAO.brandsName(cate_id));
		return "product/list_product";
	}
	
	
//	@RequestMapping("/product/seach")
//	public String seach(Model model, @RequestParam("keywords") Optional<String> keywords, @RequestParam("p") Optional<Integer> p) {
//		Pageable pageable = PageRequest.of(p.orElse(0), 2, Sort.by(Direction.ASC, "price"));
//		Page<Product> listp = productService.findByKeywords("%"+keywords.get()+"%", pageable);
//		System.out.println(listp.getSize());
//		model.addAttribute("items", listp);
//		model.addAttribute("search",keywords.get());
//		model.addAttribute("cid", cate_id);
//		model.addAttribute("brands", brandDAO.brandsName(cate_id));
//		return "product/list_product";
//	}
	
//	@RequestMapping("/product/searchPrice")
//	public String seach(Model model, @RequestParam("getMin") Optional<Double> min,  
//			@RequestParam("getMax") Optional<Double> max , @RequestParam("p") Optional<Integer> p,
//			@RequestParam("sort") Optional<String> sort) {
//		
//		model.addAttribute("cid", cate_id);
//		model.addAttribute("brands", brandDAO.brandsName(cate_id));
//		
//		Double minPrice = min.orElse(Double.MIN_VALUE);
//		Double maxPrice = max.orElse(Double.MAX_VALUE);
//		Pageable pageable = PageRequest.of(p.orElse(0), 20);
//		if(sort.isPresent() && sort.get().equals("desc"))
//			pageable = PageRequest.of(p.orElse(0), 20, Sort.by(Direction.DESC, "price"));
//		else if(sort.isPresent() && sort.get().equals("asc"))
//			pageable = PageRequest.of(p.orElse(0), 20, Sort.by(Direction.ASC, "price"));
//		//field.orElse("price")
//		
//		Integer cid = 1;
//		Page<Product> listp = productService.findByPrice( cid,minPrice, maxPrice, pageable);
//		
//		
////		model.addAttribute("getMin", minPrice);
////		model.addAttribute("getMax", maxPrice);
//		model.addAttribute("items", listp);
//		model.addAttribute("searchPrice", minPrice);
//		return "product/list_product";
//	}
	
	int cate_id;
	@RequestMapping("/product/list")
	public String list(Model model, @RequestParam("cid") Optional<Integer> cid,
			@RequestParam("bid") Optional<Integer> bid, @RequestParam("p") Optional<Integer> p, @RequestParam("min") Optional<Double> min,
			@RequestParam("max") Optional<Double> max) {
		
		model.addAttribute("cid", cid.get());//cid hien tai
		cate_id = cid.get();
		
		if(cid.isPresent() && bid.isPresent()) {
			model.addAttribute("items", productService.loadProductByBrandIdAndCateId(cid.get(), bid.get())); // id hiện tại
		}else if(cid.isPresent()) {
			Pageable pageable = PageRequest.of(p.orElse(0), 12);
			Page<Product> listsp = productService.PageCategoryId(cid.get(), "desc",pageable);	
			
			model.addAttribute("idcate", cid.get()); // id hiện tại
			model.addAttribute("items", listsp); // danh sach sản phẩm
			model.addAttribute("listsp", listsp); // phan trang
		} else if(bid.isPresent()) {
			Pageable pageable = PageRequest.of(p.orElse(0), 12);
			Page<Product> listsp = productService.PageBrandId(bid.get(), pageable);
			
			model.addAttribute("idbrand", bid.get()); // id hiện tại
			model.addAttribute("items", listsp); // danh sach sản phẩm
			model.addAttribute("listsp", listsp); // phan trang
			
		}else {
			List<Product> list = productService.findAll();
			model.addAttribute("items", list);
		}
		
		// load hang
		model.addAttribute("brands", brandDAO.brandsName(cid.get()));
		return "product/list_product";
	}
	
	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		
		Discount dis =  discountService.findByProductId(id);
		if(dis != null) {
			model.addAttribute("dis", dis);	
		}else {
			model.addAttribute("dis", "null");
		}
		
		model.addAttribute("images", productPhotoService.getProductPhotoImage(id));
		
		model.addAttribute("item", item);	
		List<ProductEvalution> listproductEvalution = productEvalutionService.findAllByProductId(id);
		
		model.addAttribute("loadstart", listproductEvalution);
		
		model.addAttribute("num", new int [5]);
		
		return "product/detail";
	}
}
