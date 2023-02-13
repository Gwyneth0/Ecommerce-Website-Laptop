package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AccountDAO;
import com.poly.dao.AuthorityDAO;
import com.poly.entity.Account;
import com.poly.entity.Authority;
import com.poly.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	
	@Autowired
	AuthorityDAO authorityDAO;
	
	@Autowired
	AccountDAO accDao;

	@Override
	public List<Authority> findAll() {
		return authorityDAO.findAll();
	}
	
	//duy
//	@Override
//	public List<Authority> findAll() {
//		return authorityDAO.findAll();
//	}

	@Override
	public Authority save(Authority authority) {
		return authorityDAO.save(authority);
	}

	@Override
	public void delete(Integer id) {
		authorityDAO.deleteById(id);
	}
	
	@Override
	public List<Authority> findAuthoritiesOfAdministrators() {
		List<Account> accounts = accDao.getAdministrators();
		return authorityDAO.authoritiesOf(accounts);
	}

	@Override
	public Authority findByUsername(String username) {
		// TODO Auto-generated method stub
		return authorityDAO.findByUsername(username);
	}

}
