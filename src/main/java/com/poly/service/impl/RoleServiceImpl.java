package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.RoleDAO;
import com.poly.entity.Role;
import com.poly.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleDAO roleDAO;

	@Override
	public List<Role> findAll() {
		return roleDAO.findAll();
	}
	
	//duy
//	@Override
//	public List<Role> findAll() {
//		return roleDAO.findAll();
//	}
	
	@Override
	public List<Role> findAllIsDeleted() {
		return roleDAO.getAll();
	}

	@Override
	public Role add(Role role) {
		return roleDAO.save(role);
	}

	@Override
	public Role update(Role role) {
		return roleDAO.save(role);
	}

	@Override
	public Role updateRoleIsDeleted(Role role) {
		return roleDAO.save(role);
	}

}
