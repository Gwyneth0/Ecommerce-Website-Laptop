package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.BrandDAO;
import com.poly.entity.Brand;
import com.poly.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
	
	@Autowired
	BrandDAO brandDAO;

	@Override
	public List<Brand> findAll() {
		return brandDAO.findAll();
	}

	@Override
	public Object findRandomTop8() {
		return brandDAO.findRandomTop8();
	}
	
	//duy
//	@Override
//	public List<Brand> findAll() {
//		return brandDAO.findAll();
//	}

	@Override
	public Brand add(Brand brand) {
		return brandDAO.save(brand);
	}

	@Override
	public Brand updateIsDeleted(Brand brand) {
		return brandDAO.save(brand);
	}

	@Override
	public Brand update(Brand brand) {
		return brandDAO.save(brand);
	}

	@Override
	public List<Brand> getListIsDeleted() {
		return brandDAO.getListIsDeleted();
	}

	//day
	@Override
	public List<Brand> brandsName(Integer id_cate) {
		// TODO Auto-generated method stub
		return brandDAO.brandsName(id_cate);
	}


}
