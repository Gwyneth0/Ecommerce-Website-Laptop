package com.poly.service;

import java.util.List;

import com.poly.entity.Account;
import com.poly.entity.Authority;

public interface AuthorityService {
	List<Authority> findAll();
	
	//duy
	//List<Authority> findAll();
	
	Authority save(Authority authority);
	
	void delete(Integer id);
	
	public List<Authority> findAuthoritiesOfAdministrators() ;
	
	public Authority findByUsername(String username);
	
}
