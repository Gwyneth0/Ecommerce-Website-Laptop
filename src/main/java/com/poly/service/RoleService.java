package com.poly.service;

import java.util.List;

import com.poly.entity.Role;

public interface RoleService {
	List<Role> findAll();
	
	//duy
	//List<Role> findAll();
	
	List<Role> findAllIsDeleted();
	
	Role add(Role role);
	
	Role update(Role role);
	
	Role updateRoleIsDeleted(Role role);
}
