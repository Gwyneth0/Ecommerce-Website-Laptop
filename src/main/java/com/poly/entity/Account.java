package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "Accounts")
public class Account implements Serializable{
	
	@Id
	String username;
	String password;
	String fullname;
	
	@Column(name = "Phonenumber")
	String phoneNumber;
	
	String email;
	String photo;
	String address;
	@Column(name = "Isdeleted")
	Boolean isDeleted;
	
	//5 1-n
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Discount> discounts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<ProductEvalution> productEvalutions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<ProductLike> productLikes;

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", fullname=" + fullname + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", photo=" + photo + ", address=" + address + ", isDeleted="
				+ isDeleted + "]";
	}
	
	
}
