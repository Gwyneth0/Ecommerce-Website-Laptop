package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Account;
import com.poly.entity.Product;


public interface AccountDAO extends JpaRepository<Account, String> {
	@Query("select o from Account o where o.email like ?1%")
	Account getByEmail(String email);
	
	@Query("select o from Account o where o.phoneNumber like ?1%")
	Account getByPhoneNumber(String phone);
	
	// duy
	@Query("SELECT a FROM Account a WHERE a.isDeleted = 0")
	List<Account> getAllIsDeleted();
	
	@Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.account.isDeleted = 0 and ar.role.id IN ('ADMIN', 'STAFF')")
	List<Account> getAdministrators();
}
