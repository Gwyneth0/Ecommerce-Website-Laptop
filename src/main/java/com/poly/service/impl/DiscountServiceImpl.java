package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DiscountDAO;
import com.poly.entity.Discount;
import com.poly.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService{
	
	@Autowired
	DiscountDAO discountDAO;

	@Override
	public List<Discount> findAll() {
		return discountDAO.findAll();
	}

	@Override
	public Discount findByProductId(Integer id) {
		return discountDAO.findByProductId(id);
	}
	
	//duy
//	@Override
//	public List<Discount> findAll() {
//		return discountDAO.findAll();
//	}

	@Override
	public Discount save(Discount discount) {
		return discountDAO.save(discount);
	}

	@Override
	public void delete(Integer id) {
		discountDAO.deleteById(id);
	}

}
