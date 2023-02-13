package com.poly.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Authority;
import com.poly.service.AuthorityService;
import com.poly.service.RoleService;
//duy
@CrossOrigin("*")
@RestController
public class AuthorityRestController {
	
	@Autowired
	AuthorityService authorityService;
	
	
//	@GetMapping("/rest/autho")
//	public List<Authority> getAll() {
//		return authorityService.findAll();
//	}
	@GetMapping("/rest/autho")
	public List<Authority> findAll(@RequestParam("admin") Optional<Boolean> admin) {

		if (admin.orElse(false)) {
			return authorityService.findAuthoritiesOfAdministrators();
		}
		return authorityService.findAll();
	}
	
	@GetMapping("/rest/getAuth/{username}")
	public Authority find(@PathVariable String username) {
		return authorityService.findByUsername(username);
	}
	
	@PostMapping("/rest/autho")
	public Authority save(@RequestBody Authority authority) {
		return authorityService.save(authority);
	}
	
	@DeleteMapping("/rest/autho/{id}")
	public void delete(@PathVariable Integer id) {
		authorityService.delete(id);
	}
}
