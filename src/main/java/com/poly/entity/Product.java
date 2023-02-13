package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String name;
	String image;
	Double price;
	Integer quantity;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	
	Boolean available;
	String discription;
	
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "Brandid")
	Brand brand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<ProductPhoto> productPhotos;
	
	//4 1-n ben phai
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Discount> discounts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<ProductEvalution> productEvalutions;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<ProductLike> productLikes;

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", image=" + image + ", price=" + price + ", quantity="
				+ quantity + ", createDate=" + createDate + ", available=" + available + ", discription=" + discription
				+ "]";
	}
	
}
