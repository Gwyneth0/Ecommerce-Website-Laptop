package com.poly.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "Orderdetails")
public class OrderDetail implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Double price;
	Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "Orderid")
	Order order;
	
	@ManyToOne
	@JoinColumn(name = "Productid")
	Product product;
}
