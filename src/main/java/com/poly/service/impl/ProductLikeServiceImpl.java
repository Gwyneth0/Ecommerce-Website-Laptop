package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductLikeDAO;
import com.poly.entity.ProductLike;
import com.poly.service.ProductLikeService;

@Service
public class ProductLikeServiceImpl implements ProductLikeService{
	
	@Autowired
	ProductLikeDAO productLikeDAO;

	@Override
	public List<ProductLike> findAll() {
		return productLikeDAO.findAll();
	}

}
