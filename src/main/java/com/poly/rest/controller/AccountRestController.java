package com.poly.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.entity.Product;
import com.poly.service.AccountService;
import com.poly.service.ProductService;



@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/account")
public class AccountRestController {

	@Autowired
	AccountService accountService;

	
	@GetMapping("/rest/account/{username}")
	public Account getOne(@PathVariable("username") String username) {
		return accountService.findById(username);
	}
	
	@PostMapping("/rest/account")
	public Account create(@RequestBody JsonNode accountData) {
		//Order oder = orderService.create(orderData);
		return accountService.create(accountData);
	}

	@PutMapping("/rest/account")
	public Account update(@RequestBody JsonNode accountData) {
		//Order oder = orderService.create(orderData);
		return accountService.update(accountData);
	}
	
	@GetMapping("/rest/account/sdt/{sdt}")
	public Account getSdt(@PathVariable("sdt") String sdt) {
		return accountService.getByPhoneNumber("%"+sdt+"%");
	}
	
	//duy
	
	@GetMapping("/ad/rest/accountAdmin")
	public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
		if (admin.orElse(false)) {
			System.out.println(accountService.getAdministrators()+ "/n");
			return accountService.getAdministrators();
		}
		System.out.println("else");
		return accountService.getAllIsDeleted();
	}
	
	@GetMapping("/ad/rest/account")
	public List<Account> getAll() {
		return accountService.findAll();
	}
	
	@GetMapping("/ad/rest/accountIsDeleted")
	public List<Account> getAllIsDeleted() {
		return accountService.getAllIsDeleted();
	}

	@PostMapping("/ad/rest/account")
	public Account save(@RequestBody Account account) {
		return accountService.save(account);
	}
	
	@PutMapping("/ad/rest/account")
	public Account update(@RequestBody Account account) {
		return accountService.save(account);
	}
	
	@DeleteMapping("/ad/rest/account/{username}")
	public void delete(@PathVariable String username) {
		accountService.delete(username);
	}
}
