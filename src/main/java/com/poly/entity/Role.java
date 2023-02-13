package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "roles")
public class Role  implements Serializable{
	
	@Id
	private String id;
	private String name;
	@Column(name = "Isdeleted")
	Boolean isDeleted;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	List<Authority> authorities;
}
