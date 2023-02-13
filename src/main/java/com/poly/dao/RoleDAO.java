package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Role;

public interface RoleDAO extends JpaRepository<Role, String>{
	//duy
	@Query("SELECT r FROM Role r WHERE r.isDeleted = 0")
	List<Role> getAll();
}
