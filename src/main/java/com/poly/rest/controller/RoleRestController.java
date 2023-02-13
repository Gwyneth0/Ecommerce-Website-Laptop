package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.Role;
import com.poly.service.RoleService;
//duy
@CrossOrigin("*")
@RestController
public class RoleRestController {
	
	@Autowired
	RoleService roleService;

	@GetMapping("/rest/role")
	public List<Role> getAll() {
		return roleService.findAll();
	}
	
	@GetMapping("/rest/roleIsDeleted")
	public List<Role> getAllIsDeleted() {
		return roleService.findAllIsDeleted();
	}
	
	@PostMapping("/rest/addRole")
	public Role add(@RequestBody Role role) {
		return roleService.add(role);
	}
	
	@PutMapping("/rest/updateRole")
	public Role update(@RequestBody Role role) {
		return roleService.update(role);
	}
	
	@PutMapping("/rest/updateRoleIsDeleted")
	public Role updateIsDeleted(@RequestBody Role role) {
		return roleService.updateRoleIsDeleted(role);
	}
}
