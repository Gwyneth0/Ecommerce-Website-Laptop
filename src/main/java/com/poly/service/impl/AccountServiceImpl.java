package com.poly.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountService accountsv;
	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Override
	public List<Account> findAll() {
		//System.out.println(accountDAO.findAll().size());
		return accountDAO.findAll();
	}

	@Override
	public Account findById(String username) {
		Account account = null;
		try {
			account = accountDAO.findById(username).get();
		} catch (Exception e) {
			account = null;
		}
		return account;
	}

	@Override
	public Account create(JsonNode accountData) {
		ObjectMapper mapper = new ObjectMapper();

		Account account = mapper.convertValue(accountData, Account.class);
		accountDAO.save(account);
		System.out.println(account);
		
		return account;
	}
	
	@Override
	public Account update(JsonNode accountData) {
		ObjectMapper mapper = new ObjectMapper();

		Account account = mapper.convertValue(accountData, Account.class);
		accountDAO.save(account);
		System.out.println(account);
		return account;
	}
	
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String password = Long.toHexString(System.currentTimeMillis());
		String fullname = oauth2.getPrincipal().getAttribute("name");
		//String picture = oauth2.getPrincipal().getAttribute("picture");
		//System.out.println(picture);
		
		UserDetails user = User.withUsername(email)
							   .password(pe.encode(password)).roles("DIRE").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		Account account =  accountsv.findById(email);
		 //tạo tài khoản cho người dùng
		if(account == null) {
			Account ac = new Account();
			ac.setUsername(email);
			ac.setPassword(password);
			ac.setFullname(fullname);
			ac.setPhoneNumber("");
			ac.setEmail(email);
			ac.setPhoto("");
			ac.setIsDeleted(true);
			ac.setAddress("");
			accountDAO.save(ac);
		}
		
		
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	public Account getByPhoneNumber(String phone) {
		return accountDAO.getByPhoneNumber(phone);
	}

	// duy
//	@Autowired
//	AccountDAO accountDAO;
	
//	@Override
//	public List<Account> findAll() {
//		//System.out.println(accountDAO.findAll().size());
//		return accountDAO.findAll();
//	}

	@Override
	public Account save(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public Account update(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public void delete(String username) {
		accountDAO.deleteById(username);
	}

	@Override
	public List<Account> getAllIsDeleted() {
		return accountDAO.getAllIsDeleted();
	}
	
	@Override
	public List<Account> getAdministrators() {
		return accountDAO.getAdministrators();
	}

}
