package com.example.emadpackage.models.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min=2, message = "Name must be 2 characters Long")
	private String name;

	private String slug;
	
	@Size(min=5, message = "Description must be 5 characters Long")
	private String description;

	private String image;
	
	@Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "Expected format: 5, 5.99, 15, 15.99")   //to match price format
	private String price;
	
	@Pattern(regexp = "^[1-9][0-9]*?", message = "Please chosse a category")	//category range starts from 1. It can't be 0
	@Column(name = "category_id")			//mapping category_id(of database) with categoryId. 
	private String categoryId;
	
	@CreationTimestamp						//This annotation automatically records date of creation
	@Column(name = "created_at", updatable = false)
	private LocalDateTime created_at;

	@UpdateTimestamp						//This annotation automatically records date updated.
	@Column(name = "updated_at")
	private LocalDateTime updated_at;

}
