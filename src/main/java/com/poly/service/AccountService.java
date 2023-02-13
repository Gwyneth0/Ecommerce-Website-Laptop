package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Account;


public interface AccountService {
	List<Account> findAll();
	
	Account findById(String username);

	Account create(JsonNode accountData);

	Account update(JsonNode accountData);
	
//	Object findByUsername(String username);
	Account getByPhoneNumber(String phone);
	
	
	// duy
	//List<Account> findAll();
	
	Account save(Account account);
	
	Account update(Account account);
	
	void delete (String username);
	
	List<Account> getAllIsDeleted();
	
	List<Account> getAdministrators();
}
