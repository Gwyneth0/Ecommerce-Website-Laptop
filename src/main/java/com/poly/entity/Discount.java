package com.poly.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Discounts")
public class Discount implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	Integer percentage;

	// @Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	// Date createDate = new Date();
	Timestamp createDate;

	// @Temporal(TemporalType.DATE)
	@Column(name = "Enddate")
	// Date endDate = new Date();
	Timestamp endDate;

	@ManyToOne
	@JoinColumn(name = "Productid")
	Product product;

	@ManyToOne
	@JoinColumn(name = "Createby")
	Account account;
}
