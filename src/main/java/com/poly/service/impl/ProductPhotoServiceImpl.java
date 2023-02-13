package com.poly.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ProductPhotoDAO;
import com.poly.entity.ProductPhoto;
import com.poly.service.ProductPhotoService;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {
	
	@Autowired
	ProductPhotoDAO productPhotoDAO;

	@Override
	public List<ProductPhoto> findAll() {
		return productPhotoDAO.findAll();
	}

	@Override
	public List<ProductPhoto> getProductPhotoImage(Integer pid) {
		return productPhotoDAO.getProductPhotoImage(pid);
	}
	
	//duy
//	@Override
//	public List<ProductPhoto> findAll() {
//		return productPhotoDAO.findAll();
//	}

	@Override
	public List<String> getListIdProductPhotoByProductId(Integer idP) {
		List<ProductPhoto> list = productPhotoDAO.getListByProductId(idP);
		List<String> listIdProductPhoto = new ArrayList<>();
		for (ProductPhoto pp : list) {
			listIdProductPhoto.add(pp.getId());
		}
		
		return listIdProductPhoto;
	}

	@Override
	public void save(ProductPhoto pp) {
		productPhotoDAO.save(pp);
	}

	@Override
	public void delete(String id) {
		productPhotoDAO.deleteById(id);
	}

}
